package edu.org.compiler;

/*
 * Desenvolvedores/Aluno:
 *  
 * Marcelo Ferreira da Silva 
 * Silvio Fragnani da Silva
 * 
 * Ano:2010/1
 */

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;

@SuppressWarnings("serial")
public class FrameCompiler extends JFrame {
	private Lexico			lexico;
	private Sintatico		sintatico;
	private Semantico		semantico;

	private Container		conteiner;
	private BorderLayout	layoutBorder;

	private JPanel			panelBackgroundConsoleCode;
	private JPanel			panelBackgroundTools;
	private JPanel			panelBackgroundStatusBar;
	private JPanel			panelStatusRight;
	private JPanel			panelStatusLeft;

	private JPanel			panelEditor;
	private JPanel			panelConsole;
	private JPanel			panelTools;

	private JLabel			labelStatusRight;
	private JLabel			labelStatusLeft;

	private JTextArea		areaCode;
	private JTextArea		areaCodelines;

	private JTextArea		areaConsole;

	private JButton			buttonNew;
	private JButton			buttonOpen;
	private JButton			buttonSave;
	private JButton			buttonCopy;
	private JButton			buttonPaste;
	private JButton			buttonCut;
	private JButton			buttonCompiler;
	private JButton			buttonGenerateCode;
	private JButton			buttonTeam;

	private String			nameFileCurrent	= "";
	private boolean			canGenerateCode	= false;

	public FrameCompiler() {
		this.setTitle("Compilador");
		this.setBounds(80, 150, 800, 600);
		this.setMinimumSize(new Dimension(800, 600));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTheme();
		this.setIconImage(this.getImage("/edu/org/images/glider.png").getImage());
		this.layoutBorder = new BorderLayout();

		this.conteiner = this.getContentPane();

		this.captureAllEvents();

		this.panelBackgroundConsoleCode = new JPanel(layoutBorder);
		this.conteiner.add(BorderLayout.CENTER, this.panelBackgroundConsoleCode);

		this.panelBackgroundTools = new JPanel();
		this.conteiner.add(BorderLayout.WEST, this.panelBackgroundTools);

		this.panelBackgroundStatusBar = new JPanel(new GridLayout(1, 2));
		this.conteiner.add(BorderLayout.PAGE_END, this.panelBackgroundStatusBar);

		this.panelStatusLeft = new JPanel();
		this.panelStatusLeft.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.labelStatusLeft = new JLabel(" ");
		this.panelStatusLeft.add(this.labelStatusLeft);
		this.panelBackgroundStatusBar.add(this.panelStatusLeft);

		this.panelStatusRight = new JPanel();
		this.panelStatusRight.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.labelStatusRight = new JLabel(" ");
		this.panelStatusRight.add(this.labelStatusRight);
		this.panelBackgroundStatusBar.add(this.panelStatusRight);

		this.panelConsole = new JPanel(new GridLayout(0, 1));
		this.panelBackgroundConsoleCode.add(BorderLayout.SOUTH, this.panelConsole);

		this.areaConsole = new JTextArea(9, 50);
		this.areaConsole.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.areaConsole.setFont(new Font("MONOSPACED", 0, 10));
		this.areaConsole.setEditable(false);

		final JScrollPane scrollConsole = new JScrollPane(this.areaConsole);
		scrollConsole.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollConsole.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.panelConsole.add(scrollConsole);

		this.panelEditor = new JPanel(new GridLayout(0, 1));
		this.panelBackgroundConsoleCode.add(BorderLayout.CENTER, this.panelEditor);

		this.panelEditor.add(this.getCodeEditor());

		// montando o layout da barra de ferramentas
		final GridLayout toolsLayout = new GridLayout(9, 1, 5, 10);

		EventClickButtons eventClick = new EventClickButtons();

		this.panelTools = new JPanel();
		this.panelTools.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.panelTools.setLayout(toolsLayout);

		this.panelBackgroundTools.add(this.panelTools);

		this.buttonNew = new JButton("Novo [ctrl+n]", this.getImage("/edu/org/images/new.png"));
		this.buttonNew.addActionListener(eventClick);
		this.panelTools.add(this.buttonNew);

		this.buttonOpen = new JButton("Abrir [ctrl+a]", this.getImage("/edu/org/images/open.png"));
		this.buttonOpen.addActionListener(eventClick);
		this.panelTools.add(this.buttonOpen);

		this.buttonSave = new JButton("Salvar [ctrl+s]", this.getImage("/edu/org/images/save.png"));
		this.buttonSave.addActionListener(eventClick);
		this.panelTools.add(this.buttonSave);

		this.buttonCopy = new JButton("Copiar [ctrl+c]", this.getImage("/edu/org/images/copy.png"));
		this.buttonCopy.addActionListener(eventClick);
		this.panelTools.add(this.buttonCopy);

		this.buttonPaste = new JButton("Colar [ctrl+v]", this.getImage("/edu/org/images/paste.png"));
		this.buttonPaste.addActionListener(eventClick);
		this.panelTools.add(this.buttonPaste);

		this.buttonCut = new JButton("Recortar [ctrl+x]", this.getImage("/edu/org/images/cut.png"));
		this.buttonCut.addActionListener(eventClick);
		this.panelTools.add(this.buttonCut);

		this.buttonCompiler = new JButton("Compilar [F8]", this.getImage("/edu/org/images/compiler.png"));
		this.buttonCompiler.addActionListener(eventClick);
		this.panelTools.add(this.buttonCompiler);

		this.buttonGenerateCode = new JButton("Gerar Código [F9]", this.getImage("/edu/org/images/generate.png"));
		this.buttonGenerateCode.addActionListener(eventClick);
		this.panelTools.add(this.buttonGenerateCode);

		this.buttonTeam = new JButton("Equipe [F1]", this.getImage("/edu/org/images/team.png"));
		this.buttonTeam.addActionListener(eventClick);
		this.panelTools.add(this.buttonTeam);
	}

	private ImageIcon getImage(final String location) {
		InputStream resourceAsStream = this.getClass().getResourceAsStream(location);

		byte[] array = null;
		try {
			array = new byte[resourceAsStream.available()];
			resourceAsStream.read(array);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(array);
	}

	private void setTheme() {
		for (LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(laf.getName())) {
				try {
					UIManager.setLookAndFeel(laf.getClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class EventClickButtons implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == buttonNew)
				actionButtonNew();
			if (e.getSource() == buttonOpen)
				actionButtonOpen();

			if (e.getSource() == buttonSave)
				actionButtonSave();

			if (e.getSource() == buttonCopy)
				actionButtonCopy();

			if (e.getSource() == buttonPaste)
				actionButtonPaste();

			if (e.getSource() == buttonCut)
				actionButtonCut();

			if (e.getSource() == buttonCompiler)
				actionButtonCompile();

			if (e.getSource() == buttonGenerateCode)
				actionButtonGenereteCode();

			if (e.getSource() == buttonTeam)
				actionButtonTeam();
		}
	}

	private void captureAllEvents() {
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

			public void eventDispatched(AWTEvent event) {
				KeyEvent e = (KeyEvent) event;

				if (e.getID() != KeyEvent.KEY_RELEASED)
					return;

				if (e.getModifiers() == KeyEvent.CTRL_MASK) {
					if (e.getKeyCode() == KeyEvent.VK_N) {
						actionButtonNew();
						return;
					}
				}

				if (e.getModifiers() == KeyEvent.CTRL_MASK) {
					if (e.getKeyCode() == KeyEvent.VK_A) {
						actionButtonOpen();
						return;
					}
				}

				if (e.getModifiers() == KeyEvent.CTRL_MASK) {
					if (e.getKeyCode() == KeyEvent.VK_S) {
						actionButtonSave();
						return;
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_F8) {
					actionButtonCompile();
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_F9) {
					actionButtonGenereteCode();
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_F1) {
					actionButtonTeam();
					return;
				}

				if (e.getSource() == areaCode) {
					if ((e.getKeyCode() == 17) || (e.getKeyCode() == 18))
						return;

					labelStatusLeft.setText("Modificado");
				}

			}
		}, AWTEvent.KEY_EVENT_MASK);
	}

	private void actionButtonNew() {
		this.setNameFileCurrent("");
		this.areaCode.setText("");
		this.areaConsole.setText("");
		this.labelStatusLeft.setText("Arquivo não modificado");
	}

	private void actionButtonOpen() {
		JFileChooser selectFile = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Compile Filer", "ms"); // mudar a extencao
		selectFile.setFileFilter(filter);

		if (selectFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				this.actionButtonNew();

				String pathFile = selectFile.getSelectedFile().getCanonicalPath();

				File e = new File(pathFile);
				if (!e.exists()) {
					JOptionPane.showMessageDialog(null, "Arquivo " + pathFile + " não exite");
					return;
				}

				this.setNameFileCurrent(pathFile);
				this.labelStatusLeft.setText("Arquivo não modificado");

				BufferedReader in = new BufferedReader(new FileReader(pathFile));
				String str;

				while ((str = in.readLine()) != null)
					this.areaCode.append(str + "\n");

			} catch (IOException erro) {
				erro.printStackTrace();
			}
		}
	}

	private void actionButtonSave() {
		if (this.areaCode.getText().trim().isEmpty())
			return;

		if (!this.nameFileCurrent.isEmpty()) {
			this.saveCodeEditor(this.nameFileCurrent);
			return;
		}

		// se arquivo novo
		JFileChooser saveFile = new JFileChooser();

		if (saveFile.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				String canonicalFile = saveFile.getSelectedFile().getCanonicalPath();

				// verificando se exite extencao, senao colocar nossa extencao
				// default .ms
				String fileName = saveFile.getSelectedFile().getName();
				if (fileName.lastIndexOf(".") < 0)
					canonicalFile += ".ms";

				this.saveCodeEditor(canonicalFile);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo\n", e.getMessage(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void actionButtonCopy() {
		this.areaCode.copy();
	}

	private void actionButtonPaste() {
		this.areaCode.paste();
	}

	private void actionButtonCut() {
		this.areaCode.cut();
	}

	private void actionButtonCompile() {
		this.areaConsole.setText("");

		String resultCompiler = this.compiler(this.areaCode.getText());

		this.areaConsole.append(resultCompiler);
	}

	private String compiler(String inputCode) {
		this.canGenerateCode = false;
		
		this.lexico    = new Lexico();
		this.sintatico = new Sintatico();
		this.semantico = new Semantico();

		this.lexico.setInput(inputCode);

		try {
			// faz uma pre verificacao para ver as palavras reservadas invalidas
			this.checkKeyWords();

			// coloca o analisador lexico para o comeco novamente
			this.lexico.setPosition(0);
			this.sintatico.parse(this.lexico, this.semantico);
			
			this.canGenerateCode = true;
			
			return "Programa compilado com sucesso";
		} catch (LexicalError e) {
			return this.treatLexicalError(e);
		} catch (SemanticError e) {
			return this.treatSemanticError(e);
		} catch (SyntaticError e) {
			return this.treatSyntaticError(e);
		}
	}

	private String treatSyntaticError(SyntaticError e) {
		try {
			this.lexico.setPosition(e.getPosition());
			Token tok = this.lexico.nextToken();

			if (tok == null)
				return "Erro sintático na linha: " + this.getLineByPosicion(e.getPosition()) + "\n" + "Encontrado fim de programa" + ", " + e.getMessage();

			return "Erro sintático na linha: " + this.getLineByPosicion(e.getPosition()) + "\n" + "Encontrado " + this.getClassTokenByID(tok.getId()) + ", " + e.getMessage();
		} catch (LexicalError e1) {
			e1.printStackTrace();
			return "problemas";
		}
	}

	private String treatSemanticError(SemanticError e) {
		return "Erro semantico na linha: " + this.getLineByPosicion(e.getPosition()) + ", " + e.getMessage();
	}

	private String treatLexicalError(LexicalError e) {
		if (ScannerConstants.SCANNER_ERROR[0] == e.getMessage())
			return "Erro lexico na linha " + this.getLineByPosicion(e.getPosition()) + ": " + getCaracterErrorByPosicion(e.getPosition()) + ", " + e.getMessage();
		else
			return "Erro lexico na linha " + this.getLineByPosicion(e.getPosition()) + ": " + e.getMessage();
	}

	private void checkKeyWords() throws LexicalError {
		Token token = null;

		token = this.lexico.nextToken();

		while (token != null) {
			this.checkKeyword(token.getLexeme(), token.getId(), token.getPosition());

			token = null;
			token = lexico.nextToken();
		}
	}

	private void checkKeyword(String lexeme, int id, int position) throws LexicalError {
		if (id < 2 || id > 24)
			return;

		for (String s : ScannerConstants.SPECIAL_CASES_KEYS) {
			if (s.equalsIgnoreCase(lexeme)) {
				return;
			}
		}

		throw new LexicalError(lexeme + ", palavra reservada inválida", position);
	}

	private String getCaracterErrorByPosicion(int posicion) {
		try {
			return " " + this.areaCode.getText(posicion, 1) + " ";
		} catch (BadLocationException e) {
			return "";
		}
	}

	private String getLineByPosicion(int position) {
		try {
			int line = 1 + this.areaCode.getLineOfOffset(position);

			return String.valueOf(line);
		} catch (BadLocationException e) {
			//e.printStackTrace();
			return "*";
		}
	}

	private String getClassTokenByID(int id) {
		if (id >= 2 && id <= 24)
			return "palavra reservada";

		if (id >= 25 && id <= 48)
			return "caracter";

		if (id == 49)
			return "inteiro";

		if (id == 50)
			return "real";

		if (id == 51)
			return "literal";

		if (id == 52)
			return "identificador";

		return "não identificado";
	}

	private void actionButtonGenereteCode() {
		if (this.semantico != null && this.canGenerateCode) {
			this.areaConsole.setText("Codigo objeto gerado com sucesso.");
			
			this.saveCodeObject(this.semantico.getCodeObject());
			
			this.canGenerateCode = false;
		} else {
			JOptionPane.showMessageDialog(null, "Nao existe codigo para ser gerado!");
		}
	}

	private void saveCodeObject(String codeObject) {
		try {
			JFileChooser saveFile = new JFileChooser();

			if (saveFile.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				String canonicalFile = saveFile.getSelectedFile().getCanonicalPath() + ".obj";
				
				BufferedWriter out = new BufferedWriter(new FileWriter(canonicalFile));
				
				out.write(codeObject);
	
				out.flush();
				out.close();			
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao salvar o codigo objeto.\n", e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actionButtonTeam() {
		JOptionPane.showMessageDialog(this, "Desenvolvedores:\n\n\tMarcelo Ferreira da Silva\n\n\tSilvio Fragnani da Silva");
	}

	private void setNameFileCurrent(String nameFileCurrent) {
		this.nameFileCurrent = nameFileCurrent.trim();

		this.labelStatusRight.setText(this.nameFileCurrent);
	}

	private void saveCodeEditor(String pathFile) {

		try {
			this.setNameFileCurrent(pathFile);
			this.labelStatusLeft.setText("Arquivo não alterado");

			BufferedWriter out = new BufferedWriter(new FileWriter(pathFile));
			out.write(this.areaCode.getText());

			out.flush();
			out.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao gravar o arquivo\n", e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}

	}

	private JScrollPane getCodeEditor() {
		JScrollPane scroll = new JScrollPane();

		this.areaCode = new JTextArea();
		this.areaCode.setTabSize(4);

		this.areaCodelines = new JTextArea("1");

		this.areaCodelines.setBackground(Color.LIGHT_GRAY);
		this.areaCodelines.setEditable(false);

		this.areaCode.getDocument().addDocumentListener(new DocumentListener() {

			public String getText() {
				int caretPosition = areaCode.getDocument().getLength();

				Element root = areaCode.getDocument().getDefaultRootElement();
				String text = "1" + System.getProperty("line.separator");

				for (int i = 2; i < root.getElementIndex(caretPosition) + 2; i++)
					text += i + System.getProperty("line.separator");

				return text;
			}

			public void changedUpdate(DocumentEvent de) {
				areaCodelines.setText(getText());
			}

			public void insertUpdate(DocumentEvent de) {
				areaCodelines.setText(getText());
			}

			public void removeUpdate(DocumentEvent de) {
				areaCodelines.setText(getText());
			}
		});

		scroll.getViewport().add(areaCode);
		scroll.setRowHeaderView(areaCodelines);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		return scroll;
	}

	public static void main(String[] args) {
		new FrameCompiler().setVisible(true);
	}
}
