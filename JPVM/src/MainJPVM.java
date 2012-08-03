import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainJPVM {
	private static List<JPVMData>	dataIn	= new ArrayList<JPVMData>();
	private static int				numWorkers;
	private static int				sizeBuffer;

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		getParams(args);

		try {
			jpvmEnvironment jpvm = new jpvmEnvironment();

			jpvmTaskId masterTaskId = jpvm.pvm_parent();

			if (masterTaskId == jpvm.PvmNoParent) {
				// Gerente de Projetos
				selectFile();

				FrameMaster frameMaster = new FrameMaster();
				frameMaster.setJVPM(jpvm);
				frameMaster.setNumWorjers(numWorkers);
				frameMaster.setDataIn(dataIn);
				frameMaster.start();
			} else {
				// Programador
				FrameSlave frameSlave = new FrameSlave();
				frameSlave.setName(jpvm.pvm_mytid().getHost());
				frameSlave.setJVPM(jpvm);
				frameSlave.setMasterId(masterTaskId);
				frameSlave.start();
			}

		} catch (jpvmException e) {
			e.getStackTrace();
		}
	}

	private static void getParams(String[] args) {
		if (args.length < 4) {
			numWorkers = 2;
			sizeBuffer = 3000;
			
			return;
		}
			
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("--works")) {
				try {
					numWorkers = Integer.parseInt(args[i + 1]);
				} catch (Exception e) {
					numWorkers = 2;
				}
			} else if (args[i].equalsIgnoreCase("--size")) {
				try {
					sizeBuffer = Integer.parseInt(args[i + 1]);
				} catch (Exception e) {
					sizeBuffer = 3000;
				}
			}
		}
	}

	private static void selectFile() {
		JFileChooser fileIn = new JFileChooser(".");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Tasks programmer", "tasks");
		fileIn.setFileFilter(filter);

		if (fileIn.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				loadFileIn(fileIn.getSelectedFile().getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void loadFileIn(String fileIn) {
		try {
			BufferedReader in;
			String strLineFile = "";

			int index = 1;

			in = new BufferedReader(new FileReader(fileIn));

			JPVMData jpvmData = new JPVMData(index);
			jpvmData.createEffect();

			while ((strLineFile = in.readLine()) != null) {
				if (strLineFile.isEmpty())
					continue;

				index++;

				jpvmData.addData(strLineFile);

				if (index == sizeBuffer) {
					index = 0;

					dataIn.add(jpvmData);

					jpvmData = new JPVMData(dataIn.size() + 1);
					jpvmData.createEffect();
				}
			}

			// adiciona o resto
			if (index != 0)
				dataIn.add(jpvmData);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
