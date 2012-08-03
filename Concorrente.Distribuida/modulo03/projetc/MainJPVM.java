package projetc;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import jpvm.jpvmEnvironment;
import jpvm.jpvmException;
import jpvm.jpvmTaskId;

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

				FrameMestre frameMestre = new FrameMestre();
				frameMestre.setJVPM(jpvm);
				frameMestre.setNumWorjers(numWorkers);
				frameMestre.setDataIn(dataIn);
				frameMestre.start();
			} else {
				// Programador
				FrameEscravo frameEscravo = new FrameEscravo();
				frameEscravo.setName(jpvm.pvm_mytid().getHost());
				frameEscravo.setJVPM(jpvm);
				frameEscravo.setMasterId(masterTaskId);
				frameEscravo.start();
			}

		} catch (jpvmException e) {
			e.getStackTrace();
		}
	}

	private static void getParams(String[] args) {
		if (args.length < 4) {
			numWorkers = 2;
			sizeBuffer = 100;
			
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
					sizeBuffer = 2;
				}
			}
		}
	}

	private static void selectFile() {
		JFileChooser fileIn = new JFileChooser(".");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("File sound", "sound");
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
