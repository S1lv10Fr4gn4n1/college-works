import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

public class FrameSlave extends JFrame {
	private static final long	serialVersionUID	= 6169812021049212844L;

	private JProgressBar		jProgressBarSlave;
	private JScrollPane			jScrollPaneSlaave;
	private JTextArea			jTextAreaSlave;

	private jpvmEnvironment		jpvm;
	private jpvmTaskId			masterTaskId;

	public FrameSlave() {
		this.initComponents();
		this.setVisible(true);
	}

	public void setName(String name) {
		this.setTitle(name);
	}

	private void initComponents() {
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.jScrollPaneSlaave  = new JScrollPane();
		this.jTextAreaSlave 	 = new JTextArea();
		this.jProgressBarSlave = new JProgressBar();


		this.jTextAreaSlave.setColumns(20);
		this.jTextAreaSlave.setRows(5);
		this.jTextAreaSlave.setEditable(false);
		this.jScrollPaneSlaave.setViewportView(this.jTextAreaSlave);

		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPaneSlaave, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE).addComponent(this.jProgressBarSlave, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
								519, Short.MAX_VALUE)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPaneSlaave, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jProgressBarSlave, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(12, 12, 12)));

		this.pack();
	}

	private void receiveAndSendMessage() throws jpvmException {
		jpvmMessage message = this.jpvm.pvm_recv();

		String msg = message.buffer.upkstr();

		if (message.messageTag == -1) {
			this.signalTerminate();
			return;
		}

		JPVMData jpvmDataIn = new JPVMData(msg);
		this.jTextAreaSlave.append("\nGP " + message.sourceTid.toString() + ", mandou a tarefa: " + jpvmDataIn.getId() + ", MIM vai trabalhar...\n");

		this.jProgressBarSlave.setValue(0);
		this.jProgressBarSlave.setMaximum(jpvmDataIn.getListMessage().size());

		int tagMestre 		 = -1;
		JPVMData jpvmDataOut = null;

		switch (message.messageTag) {
			case 1:
				// processo 1
				jpvmDataOut = this.processTaskProgrammer1(jpvmDataIn);
				tagMestre = 1;

				break;

			case 2:
				// processo 2
				jpvmDataOut = this.processTaskProgrammer2(jpvmDataIn);
				tagMestre = 2;

				break;

			case 3:
				// processo 3
				jpvmDataOut = this.processTaskProgrammer3(jpvmDataIn);
				tagMestre = 3;

				break;
		}

		// se terminou de processar os efeitos, manda para o mestre guardar esse valor
		if (jpvmDataOut.isTerminate())
			tagMestre = 0;

		jpvmBuffer buf = new jpvmBuffer();
		buf.pack(jpvmDataOut.toString());

		this.jpvm.pvm_send(buf, this.masterTaskId, tagMestre);
		this.jpvm.pvm_exit();
	}

	private JPVMData processTaskProgrammer1(JPVMData jpvmData) {
		this.jTextAreaSlave.append("Implementando tarefa 1 ...\n");

		JPVMData jpvmDataResult = new JPVMData(jpvmData.getId());
		jpvmDataResult.setEffects(jpvmData.getEffects());

		for (int i = 0; i < jpvmData.getListMessage().size(); i++) {
			String[] messageIn = jpvmData.getListMessage().get(i).split("");
			String messageOut = "";

			for (int j = 0; j < messageIn.length; j++) {
				if (messageIn[j].isEmpty())
					continue;

				int value = Integer.parseInt(messageIn[j]);
				value--;

				if (value <= 0)
					value = 1;

				messageOut += value;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				this.jTextAreaSlave.append(e.getStackTrace().toString());
			}

			this.jTextAreaSlave.append(messageOut + "\n");
			this.jProgressBarSlave.setValue(this.jProgressBarSlave.getValue() + 1);

			jpvmDataResult.addData(messageOut);
		}

		return jpvmDataResult;
	}

	private JPVMData processTaskProgrammer2(JPVMData jpvmData) {
		this.jTextAreaSlave.append("Implementado tarefa 2 ...\n");

		JPVMData jpvmDataResult = new JPVMData(jpvmData.getId());
		jpvmDataResult.setEffects(jpvmData.getEffects());

		for (int i = 0; i < jpvmData.getListMessage().size(); i++) {
			String[] messageIn = jpvmData.getListMessage().get(i).split("");
			String messageOut = "";

			for (int j = 0; j < messageIn.length; j++) {
				if (messageIn[j].isEmpty())
					continue;

				int value = Integer.parseInt(messageIn[j]);
				value--;

				if (value >= 10)
					value = 8;

				messageOut += value;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				this.jTextAreaSlave.append(e.getStackTrace().toString());
			}

			this.jTextAreaSlave.append(messageOut + "\n");
			this.jProgressBarSlave.setValue(this.jProgressBarSlave.getValue() + 1);

			jpvmDataResult.addData(messageOut);
		}

		return jpvmDataResult;
	}

	private JPVMData processTaskProgrammer3(JPVMData jpvmData) {
		try {
			this.jTextAreaSlave.append("Implementado tarefa 3 ...\n");

			JPVMData jpvmDataResult = new JPVMData(jpvmData.getId());
			jpvmDataResult.setEffects(jpvmData.getEffects());

			for (int i = 0; i < jpvmData.getListMessage().size(); i++) {
				String[] messageIn = jpvmData.getListMessage().get(i).split("");
				String messageOut = "";

				for (int j = 0; j < messageIn.length; j++) {
					if (messageIn[j].isEmpty())
						continue;

					int value = Integer.parseInt(messageIn[j]);
					value--;

					if (value <= 0)
						value = 1;
					else if (value % 2 == 0)
						value = 0;

					messageOut += value;
				}

				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					this.jTextAreaSlave.append(e.getStackTrace().toString());
				}

				this.jTextAreaSlave.append(messageOut + "\n");
				this.jProgressBarSlave.setValue(this.jProgressBarSlave.getValue() + 1);

				jpvmDataResult.addData(messageOut);
			}

			return jpvmDataResult;
		} catch (Exception e) {
			this.jTextAreaSlave.append(e.getMessage());
			return null;
		}
	}

	public void setMasterId(jpvmTaskId masterTaskId) {
		this.masterTaskId = masterTaskId;
	}

	public void setJVPM(jpvmEnvironment jpvm) {
		this.jpvm = jpvm;
	}

	public void start() {
		try {
			while (true) {
				if (this.jpvm.pvm_probe())
					this.receiveAndSendMessage();
			}
		} catch (jpvmException e) {
			this.jTextAreaSlave.append("\n\n" + e.getStackTrace());
		}
	}

	public void receiverMessage() {
		try {
			this.receiveAndSendMessage();
		} catch (jpvmException e) {
			this.jTextAreaSlave.append("\n\n" + e.getStackTrace());
		}
	}

	private void signalTerminate() {
		this.jTextAreaSlave.append("\nTo morrendo ...");
		System.exit(0);
	}
}
