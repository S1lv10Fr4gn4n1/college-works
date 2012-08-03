package edu.org.mixer.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.org.mixer.jomp.ForJOMP;
import edu.org.mixer.jomp.SectionsJOMP;
import edu.org.mixer.model.FrequencyArray;

public class FrameMain extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private static List<String> soundIn = new ArrayList<String>();
    //private List<int[]> soundOut = new ArrayList<int[]>();

    private ButtonGroup buttonGroup1;
    private JButton jButtonEntrada;
    private JButton jButtonSaida;
    private JButton jButtonStart;
    private JCheckBox jCheckBoxCompactar;
    private JCheckBox jCheckBoxFiltrar;
    private JCheckBox jCheckBoxMasterizar;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JProgressBar jProgressBar1;
    private JRadioButton jRadioButtonFlac;
    private JRadioButton jRadioButtonMP3;
    private JRadioButton jRadioButtonWMV;
    private JTextField jTextEntrada;
    private JTextField jTextSaida;

    public FrameMain() {
        initComponents();
    }

    private void initComponents() {

        buttonGroup1 = new ButtonGroup();
        jLabel1 = new JLabel();
        jButtonEntrada = new JButton();
        jCheckBoxCompactar = new JCheckBox();
        jProgressBar1 = new JProgressBar();
        jTextEntrada = new JTextField();
        jLabel2 = new JLabel();
        jTextSaida = new JTextField();
        jButtonSaida = new JButton();
        jRadioButtonMP3 = new JRadioButton();
        jRadioButtonWMV = new JRadioButton();
        jRadioButtonFlac = new JRadioButton();
        jCheckBoxMasterizar = new JCheckBox();
        jCheckBoxFiltrar = new JCheckBox();
        jButtonStart = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Conversor");
        setResizable(false);

        jLabel1.setText("Entrada");

        jButtonEntrada.setText("Selecionar");
        jButtonEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEntradaActionPerformed(evt);
            }
        });

        jCheckBoxCompactar.setText("Compactar");
        jCheckBoxCompactar.setEnabled(false);

        jLabel2.setText("Saida");

        jButtonSaida.setText("Selecionar");
        jButtonSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaidaActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButtonMP3);
        jRadioButtonMP3.setSelected(true);
        jRadioButtonMP3.setText("mp3");

        buttonGroup1.add(jRadioButtonWMV);
        jRadioButtonWMV.setText("wmv");

        buttonGroup1.add(jRadioButtonFlac);
        jRadioButtonFlac.setText("flac");

        jCheckBoxMasterizar.setText("Masterizar");
        jCheckBoxMasterizar.setEnabled(false);

        jCheckBoxFiltrar.setText("Filtrar");
        jCheckBoxFiltrar.setEnabled(false);

        jButtonStart.setText("Start");
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonEntrada)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextSaida, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonStart, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonSaida, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(450, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextEntrada, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(119, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonMP3)
                            .addComponent(jRadioButtonFlac)
                            .addComponent(jRadioButtonWMV))
                        .addGap(87, 87, 87)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckBoxFiltrar)
                                .addContainerGap(302, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckBoxCompactar)
                                .addContainerGap(264, Short.MAX_VALUE))
                            .addComponent(jCheckBoxMasterizar)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jProgressBar1, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextEntrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEntrada))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextSaida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSaida))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonMP3)
                    .addComponent(jCheckBoxMasterizar))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonFlac)
                    .addComponent(jCheckBoxCompactar)
                    .addComponent(jButtonStart))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonWMV)
                    .addComponent(jCheckBoxFiltrar))
                .addGap(28, 28, 28)
                .addComponent(jProgressBar1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButtonEntradaActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileIn = new JFileChooser("/media/_Dados/Workspace_Projetos/workspace_Eclipse/Mixer_JOMP/src/edu/org/mixer/jomp");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("File sound", "sound"); // mudar a extencao
        fileIn.setFileFilter(filter);
        
        if (fileIn.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                jTextEntrada.setText(fileIn.getSelectedFile().getCanonicalPath());
                
                //File fileSound = new File(jTextEntrada.getText());
                
                //if (fileSound.exists())
                    loadFileInstruments(fileIn.getSelectedFile().getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void jButtonSaidaActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileOut = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("File sound", "sound");
        fileOut.setFileFilter(filter);
        
        if (fileOut.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                jTextSaida.setText(fileOut.getSelectedFile().getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {
        jProgressBar1.setMaximum(this.soundIn.size());
        
        //loadFileInstruments("src/edu/org/mixer/jomp/music1.sound");
        
        ForJOMP forJOMP = new ForJOMP();
        SectionsJOMP sectionsJOMP = new SectionsJOMP();
        
        System.out.println("Convertendo para Array");        
        String[] dados = new String[soundIn.size()];
        
        for (int i = 0; i < soundIn.size(); i++) {
            dados[i] = soundIn.get(i);
        }
        
        System.out.println("Iniciando conversao de arquivo .sound para FrequencyArray //omp for ");
        FrequencyArray freqArray = forJOMP.start(dados);
        System.out.println("Terminou a conversao ...");
        
        System.out.println("Iniciando processamento dos efeitos dos canais //omp sections ");
        sectionsJOMP.start(freqArray);
        System.out.println("Terminou o processamento ...");
        System.exit(0);
    }
    
    private static void loadFileInstruments(String fileSound) {
        System.out.println("Lendo arquivo....");
        
        try {
            BufferedReader in;            
            String strFrequency = "";
            
            in = new BufferedReader(new FileReader(fileSound));

            while ((strFrequency = in.readLine()) != null) {
                if (strFrequency.isEmpty())
                    continue;
                                
                soundIn.add(strFrequency);
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Leitura terminada!");
    }


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameMain().setVisible(true);
            }
        });
    }

    public void setProgress(int value) {
        jProgressBar1.setValue(value);
        System.out.println(jProgressBar1.getMaximum() + ", " + value);
    }
}
