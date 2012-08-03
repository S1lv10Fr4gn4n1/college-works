import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class FrameMaster extends JFrame {
	private static final long	serialVersionUID	= -1583109990187446471L;
	private JLabel				jLabelMaster;
	private JProgressBar		jProgressBarMaster;
	private JScrollPane			jScrollPaneMaster;
	private JTextArea			jTextAreaMaster;

	private jpvmEnvironment		jpvm;
	private int					numWorkers;
	private List<JPVMData>		dataIn;
	private List<JPVMData>		dataOut				= new ArrayList<JPVMData>();
	private jpvmTaskId[]		tids;
	private int					qtdnumWorked;

	public FrameMaster() {
		this.initComponents();
		this.setVisible(true);
	}

	private void initComponents() {
		this.setTitle("Mestre");
		this.setResizable(false);

		this.jScrollPaneMaster = new JScrollPane();
		this.jTextAreaMaster = new JTextArea();
		this.jLabelMaster = new JLabel();
		this.jProgressBarMaster = new JProgressBar();

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.jTextAreaMaster.setColumns(20);
		this.jTextAreaMaster.setRows(5);
		this.jTextAreaMaster.setEditable(false);
		this.jScrollPaneMaster.setViewportView(this.jTextAreaMaster);

		this.jLabelMaster.setText("Processos");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabelMaster).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 446, GroupLayout.PREFERRED_SIZE)).addGroup(
								layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jProgressBarMaster, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(this.jScrollPaneMaster,
										GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(this.jLabelMaster).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPaneMaster, GroupLayout.PREFERRED_SIZE, 327, GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(
						this.jProgressBarMaster, GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE).addContainerGap()));

		pack();
	}

	public void receiverMessage() throws jpvmException {
		if (this.qtdnumWorked == 0)
			return;

		jpvmMessage message = this.jpvm.pvm_recv();

		this.checkReturnMessage(message);

		this.receiverMessage();
	}

	private void checkReturnMessage(jpvmMessage message) throws jpvmException {
		// consiste as mensagem recebidas

		String msg = message.buffer.upkstr();

		JPVMData jpvmData = new JPVMData(msg);

		this.jTextAreaMaster.append("Email de: " + message.sourceTid.toString() + "\n");
		this.jTextAreaMaster.append("\t" + jpvmData.toString() + "\n");

		if (message.messageTag == 0)
			this.dataOut.add(jpvmData);
		else
			this.dataIn.add(jpvmData);

		if (this.dataIn.size() > 0)
			this.sendMessage(message.sourceTid);
		else if (this.dataIn.size() == 0)
			this.sendSignalTerminate(message.sourceTid);
	}

	private void sendSignalTerminate(jpvmTaskId tid) throws jpvmException {
		// manda sinal para desligar

		jpvmBuffer buf = new jpvmBuffer();
		buf.pack("terminate");

		this.jpvm.pvm_send(buf, tid, -1); //tag para terminar os escravos

		this.jTextAreaMaster.append("\n18hs programador " + tid.getHost() + " indo embora!\n");

		this.qtdnumWorked--;
	}

	private void sendMessage(jpvmTaskId tid) throws jpvmException {
		// manda as tarefas
		jpvmBuffer buf = new jpvmBuffer();

		if (this.dataIn.size() == 0) {
			this.sendSignalTerminate(tid);
			return;
		}

		this.jTextAreaMaster.append("\nMandando tarefa para o programador " + tid.getHost() + "\n");

		JPVMData jpvmData = this.dataIn.remove(0);
		this.jProgressBarMaster.setValue(this.jProgressBarMaster.getValue() + 1);

		int tag = Integer.parseInt(jpvmData.getTaskProgrammer());

		jpvmData.removeTaskProgrammer(String.valueOf(tag));

		buf.pack(jpvmData.toString());

		this.jpvm.pvm_send(buf, tid, tag);
	}

	public void start() {
		try {
			this.jTextAreaMaster.append("Total de JPVMData: " + this.dataIn.size() + "\n");

			this.tids = new jpvmTaskId[this.numWorkers];

			this.jpvm.pvm_spawn("MainJPVM", this.numWorkers, this.tids);

			// mostra os escravos que estao trabalhando
			this.jTextAreaMaster.append("Programadores iniciando expediente ....\n");

			for (int i = 0; i < this.numWorkers; i++)
				this.jTextAreaMaster.append("\t" + this.tids[i].toString() + "\n");

			// manda as tarefas iniciais para os Programadores (escravos)
			for (int i = 0; i < this.numWorkers; i++)
				this.sendMessage(this.tids[i]);

			this.receiverMessage();

			this.jTextAreaMaster.append("\n total saida: " + this.dataOut.size());

			if (JOptionPane.showConfirmDialog(null, "Deseja salvar o arquivo processado ?", null, JOptionPane.YES_NO_OPTION) == 0)
				this.saveToFile();
		} catch (jpvmException e) {
			e.printStackTrace();
		}
	}

	public void setNumWorjers(int numWorkers) {
		this.numWorkers = numWorkers;
		this.qtdnumWorked = this.numWorkers;
	}

	public void setJVPM(jpvmEnvironment jpvm) {
		this.jpvm = jpvm;
	}

	public void setDataIn(List<JPVMData> dataIn) {
		this.dataIn = dataIn;

		this.jProgressBarMaster.setMaximum(this.dataIn.size() * 3);
	}

	private void saveToFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("taskOut.task"));

			for (int i = 0; i < this.dataOut.size(); i++) {
				JPVMData jpvmData = this.dataOut.get(i);

				for (int j = 0; j < jpvmData.getListMessage().size(); j++) {
					out.write(jpvmData.getListMessage().get(j) + "\n");
				}
			}

			out.flush();
			out.close();

		} catch (IOException e) {
			this.jTextAreaMaster.append(e.getMessage());
		}
	}

}
