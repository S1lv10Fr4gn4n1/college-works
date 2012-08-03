package projetc;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
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

public class FrameEscravo extends JFrame {
	private static final long	serialVersionUID	= 6169812021049212844L;

	private JProgressBar		jProgressBarEscravo;
	private JScrollPane			jScrollPaneEscravo;
	private JTextArea			jTextAreaEscravo;

	private jpvmEnvironment		jpvm;
	private jpvmTaskId			masterTaskId;

	public FrameEscravo() {
		this.initComponents();
		this.setVisible(true);
	}

	public void setName(String name) {
		this.setTitle(name);
	}

	private void initComponents() {
		this.setResizable(false);
		this.jScrollPaneEscravo = new JScrollPane();
		this.jTextAreaEscravo = new JTextArea();
		this.jProgressBarEscravo = new JProgressBar();

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.jTextAreaEscravo.setColumns(20);
		this.jTextAreaEscravo.setRows(5);
		this.jTextAreaEscravo.setEditable(false);
		this.jScrollPaneEscravo.setViewportView(this.jTextAreaEscravo);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPaneEscravo, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE).addComponent(this.jProgressBarEscravo, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
								519, Short.MAX_VALUE)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPaneEscravo, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jProgressBarEscravo, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(12, 12, 12)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void receiveAndSendMessage() throws jpvmException {
		jpvmMessage message = this.jpvm.pvm_recv();

		String msg = message.buffer.upkstr();
		
		if (message.messageTag == -1) {
			this.signalTerminate();
			return;
		}

		JPVMData jpvmDataIn = new JPVMData(msg);
		this.jTextAreaEscravo.append("\nGP " + message.sourceTid.toString() + ", mandou a tarefa: " + jpvmDataIn.getId() + ", MIM vai trabalhar...\n");
		
		this.jProgressBarEscravo.setValue(0);
		this.jProgressBarEscravo.setMaximum(jpvmDataIn.getListMessage().size());
		
		int tagMestre = -1;
		JPVMData jpvmDataOut = null;

		switch (message.messageTag) {
			case 1:
				// processo 1
				jpvmDataOut = this.processEfecty1(jpvmDataIn);
				tagMestre = 1;

				break;

			case 2:
				// processo 2
				jpvmDataOut = this.processEfecty2(jpvmDataIn);
				tagMestre = 2;

				break;

			case 3:
				// processo 3
				jpvmDataOut = this.processEfecty3(jpvmDataIn);
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

	private JPVMData processEfecty1(JPVMData jpvmData) {
		this.jTextAreaEscravo.append("Processando efeito 1 ...\n");
		
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

			this.jTextAreaEscravo.append(messageOut + "\n");
			this.jProgressBarEscravo.setValue(this.jProgressBarEscravo.getValue() + 1);

			jpvmDataResult.addData(messageOut);
		}

		return jpvmDataResult;
	}

	private JPVMData processEfecty2(JPVMData jpvmData) {
		this.jTextAreaEscravo.append("Processando efeito 2 ...\n");

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

			this.jTextAreaEscravo.append(messageOut + "\n");
			this.jProgressBarEscravo.setValue(this.jProgressBarEscravo.getValue() + 1);
			
			jpvmDataResult.addData(messageOut);
		}

		return jpvmDataResult;
	}

	private JPVMData processEfecty3(JPVMData jpvmData) {	
		try {
		this.jTextAreaEscravo.append("Processando efeito 3 ...\n");

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

			this.jTextAreaEscravo.append(messageOut + "\n");
			this.jProgressBarEscravo.setValue(this.jProgressBarEscravo.getValue() + 1);
			
			jpvmDataResult.addData(messageOut);
		}

		return jpvmDataResult;
		} catch (Exception e) {
			this.jTextAreaEscravo.append(e.getMessage());
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
			this.jTextAreaEscravo.append("\n\n" + e.getStackTrace());
		}
	}

	public void receiverMessage() {
		try {
			this.receiveAndSendMessage();
		} catch (jpvmException e) {
			this.jTextAreaEscravo.append("\n\n" + e.getStackTrace());
		}
	}

	private void signalTerminate() {
		this.jTextAreaEscravo.append("\nTo morrendo ...");
		System.exit(0);
	}
}
