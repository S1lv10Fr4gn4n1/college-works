package projetc;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import jpvm.jpvmBuffer;
import jpvm.jpvmEnvironment;
import jpvm.jpvmException;
import jpvm.jpvmMessage;
import jpvm.jpvmTaskId;

public class FrameMestre extends JFrame {
	private static final long	serialVersionUID	= -1583109990187446471L;
	private JLabel				jLabel3;
	private JProgressBar		jProgressBarMestre;
	private JScrollPane			jScrollPane2;
	private JTextArea			jTextAreaMestre;

	private jpvmEnvironment		jpvm;
	private int					numWorkers;
	private List<JPVMData>		dataIn;
	private List<JPVMData>		dataOut				= new ArrayList<JPVMData>();
	private jpvmTaskId[]		tids;
	private int					qtdnumWorked;

	public FrameMestre() {
		this.initComponents();
		this.setVisible(true);
	}

	private void initComponents() {
		this.setTitle("Mestre");
		this.setResizable(false);

		jScrollPane2 = new JScrollPane();
		jTextAreaMestre = new JTextArea();
		jLabel3 = new JLabel();
		jProgressBarMestre = new JProgressBar();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		jTextAreaMestre.setColumns(20);
		jTextAreaMestre.setRows(5);
		jTextAreaMestre.setEditable(false);
		jScrollPane2.setViewportView(jTextAreaMestre);

		jLabel3.setText("Processos");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 446, GroupLayout.PREFERRED_SIZE)).addGroup(
								layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(jProgressBarMestre, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jScrollPane2, GroupLayout.Alignment.LEADING,
										GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 327, GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jProgressBarMestre,
						GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE).addContainerGap()));

		pack();
	}

	public void receiverMessage() throws jpvmException {
		for (int i = 0; i < this.numWorkers; i++) {

			if (!this.jpvm.pvm_probe(this.tids[i]))
				continue;

			jpvmMessage message = this.jpvm.pvm_recv(this.tids[i]);

			this.checkReturnMessage(message);
		}
	}

	private void checkReturnMessage(jpvmMessage message) throws jpvmException {
		String msg = message.buffer.upkstr();

		JPVMData jpvmData = new JPVMData(msg);

		this.jTextAreaMestre.append("Email de: " + message.sourceTid.toString() + "\n");
		this.jTextAreaMestre.append("\t" + jpvmData.toString() + "\n");

		if (message.messageTag == 0)
			this.dataOut.add(jpvmData);
		else
			this.dataIn.add(jpvmData);

		// pode mandar fazer outra tarefa, conforme a tag
		// talvez colocar um white os invez do for	

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

		this.jTextAreaMestre.append("\n18hs programador " + tid.getHost() + " indo embora!\n");

		this.qtdnumWorked--;
	}

	private void sendMessage(jpvmTaskId tid) throws jpvmException {
		// manda as tarefas
		jpvmBuffer buf = new jpvmBuffer();

		if (this.dataIn.size() == 0) {
			this.sendSignalTerminate(tid);
			return;
		}

		this.jTextAreaMestre.append("\nMandando tarefa para o Programadores " + tid.getHost() + "\n");

		JPVMData jpvmData = this.dataIn.remove(0);
		this.jProgressBarMestre.setValue(this.jProgressBarMestre.getValue() + 1);

		int tag = Integer.parseInt(jpvmData.getEffect());

		// verificar qual efeito ira processar, se nao tiver sendo usado
		/*if (!this.jpvm.pvm_probe(tid, tag)) {
			for (int iTid = 0; iTid < this.numWorkers; iTid++) {
				for (int iTag = 1; iTag < 4; iTag++) {
					if (!this.jpvm.pvm_probe(this.tids[iTid], iTag)) {
						System.out.println("teste: " + iTid + ", tag: " + tag);

						tag = iTag;
						break;
					}
				}
			}
		}*/
	
		jpvmData.removeEffect(String.valueOf(tag));

		buf.pack(jpvmData.toString());
		this.jpvm.pvm_send(buf, tid, tag);
	}

	public void start() {
		try {
			this.tids = new jpvmTaskId[this.numWorkers];

			this.jpvm.pvm_spawn("MainJPVM", this.numWorkers, this.tids);

			// mostra os escravos que estao trabalhando
			this.jTextAreaMestre.append("Programadores trabalhando ....\n");

			for (int i = 0; i < this.numWorkers; i++)
				this.jTextAreaMestre.append("\t" + this.tids[i].toString() + "\n");

			// manda as tarefas iniciais para os Programadores (escravos)
			for (int i = 0; i < this.numWorkers; i++) {
				this.sendMessage(this.tids[i]);
			}

			// coloca o GP (mestre) para ler emails (aguardar a resposta das tarefas)
			while (this.qtdnumWorked != 0) {
				if (this.jpvm.pvm_probe())
					this.receiverMessage();
			}
			
			this.jTextAreaMestre.append("\n total saida: " + this.dataOut.size());
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
		
		this.jProgressBarMestre.setMaximum(this.dataIn.size() * 3);
	}
}
