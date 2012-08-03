package edu.org.mixer.view;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;

import edu.org.mixer.control.BarChartListener;
import edu.org.mixer.model.ConcreteMixer;
import edu.org.mixer.model.Frequency;
import edu.org.mixer.model.Mixer;
import edu.org.mixer.model.thread.AudioOutput;
import edu.org.mixer.model.thread.Channel;

public final class FrameMixer extends JFrame implements BarChartListener {

    /**
     * @since 08/04/2010
     */
    private static final long serialVersionUID = -1089156587065776019L;

    private JScrollPane scrollPanel;

    private JPanel mainPanel;
    private JPanel panelEqualizer;
    private JPanel panelGrafico;

    private final List<PanelChannel> channelPanels = new ArrayList<PanelChannel>();
    private final JProgressBar[] progressBars = new JProgressBar[10];
    private final JSlider[] sliders = new JSlider[10];

    private JToggleButton btnResume;
    private JToggleButton btnStart;
    private JToggleButton btnStop;

    private final Mixer mixer = new ConcreteMixer();
    //private final Mixer mixer = new AntigoMixer();
    
    private final AudioOutput audioOutput = new AudioOutput(this.mixer);

    public FrameMixer() {
    	this.setTheme();
        this.initComponents();
        this.createChannels();
        this.initControl();
    }
    
    private void setTheme() {
        for (final LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(laf.getName())) {
                try {
                    UIManager.setLookAndFeel(laf.getClassName());
                } catch (final ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (final InstantiationException e) {
                    e.printStackTrace();
                } catch (final IllegalAccessException e) {
                    e.printStackTrace();
                } catch (final UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initControl() {
        this.audioOutput.addListener(this);
    }

    private void initComponents() {
        this.scrollPanel = new JScrollPane();
        this.mainPanel = new JPanel();
        this.panelEqualizer = new JPanel();

        this.btnStart = new JToggleButton();
        this.btnStop = new JToggleButton();
        this.btnResume = new JToggleButton();
        this.panelGrafico = new JPanel();

        final MouseListener mouseLis = new MouseAdapter() {

            @Override
            public void mouseReleased(final MouseEvent e) {
                for (int i = 0; i < FrameMixer.this.sliders.length; i++) {
                    if (e.getSource() == FrameMixer.this.sliders[i]) {
                        FrameMixer.this.mixer.setFrequency(i, FrameMixer.this.sliders[i].getValue());
                    }
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            final JSlider s = new JSlider();
            s.setOrientation(SwingConstants.VERTICAL);
            s.setPaintTicks(true);
            s.setSnapToTicks(true);
            s.setMaximum(9);
            s.setMinimum(0);
            s.addMouseListener(mouseLis);

            this.sliders[i] = s;
        }

        for (int i = 0; i < 10; i++) {
            final JProgressBar progressBar = new JProgressBar();
            progressBar.setOrientation(1);
            progressBar.setMaximum(9);
            progressBar.setMinimum(0);

            this.progressBars[i] = progressBar;
        }

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("ConcreteMixer");
        this.setResizable(false);

        this.mainPanel.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        this.mainPanel.setAutoscrolls(true);
        this.mainPanel.setLayout(new GridLayout(2, 5));

        this.scrollPanel.setViewportView(this.mainPanel);
        this.panelEqualizer.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        this.btnStart.setText("start");
        this.btnStart.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                FrameMixer.this.buttonStartActionPerformed(evt);
            }
        });

        this.btnStop.setText("stop");
        this.btnStop.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                FrameMixer.this.buttonStopActionPerformed(evt);
            }
        });

        this.btnResume.setText("resume");
        this.btnResume.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                FrameMixer.this.buttonResumeActionPerformed(evt);
            }
        });

        final GroupLayout panelEqualizerLayout = new GroupLayout(this.panelEqualizer);
        this.panelEqualizer.setLayout(panelEqualizerLayout);
        panelEqualizerLayout.setHorizontalGroup(panelEqualizerLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelEqualizerLayout.createSequentialGroup().addGap(28, 28, 28).addGroup(panelEqualizerLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(panelEqualizerLayout.createSequentialGroup().addComponent(this.sliders[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.sliders[1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.sliders[2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.sliders[3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.sliders[4], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.sliders[5], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(8, 8, 8).addComponent(this.sliders[6], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.sliders[7], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.sliders[8], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.sliders[9], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnStart).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnStop).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnResume))).addContainerGap(43, Short.MAX_VALUE)));

        panelEqualizerLayout.setVerticalGroup(panelEqualizerLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelEqualizerLayout.createSequentialGroup().addContainerGap().addGroup(panelEqualizerLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, panelEqualizerLayout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE).addGroup(panelEqualizerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.btnStart).addComponent(this.btnStop).addComponent(this.btnResume))).addComponent(this.sliders[1], GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE).addComponent(this.sliders[2], GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE).addComponent(this.sliders[3], GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE).addComponent(this.sliders[4], GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE).addComponent(this.sliders[5], GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE).addComponent(this.sliders[6], GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE).addComponent(this.sliders[7], GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE).addComponent(this.sliders[9], GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE).addComponent(this.sliders[8], GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE).addComponent(this.sliders[0], GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)).addContainerGap()));

        this.panelGrafico.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        final GroupLayout panelGraficoLayout = new GroupLayout(this.panelGrafico);
        this.panelGrafico.setLayout(panelGraficoLayout);
        panelGraficoLayout.setHorizontalGroup(panelGraficoLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelGraficoLayout.createSequentialGroup().addGap(34, 34, 34).addComponent(this.progressBars[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(28, 28, 28).addComponent(this.progressBars[1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(31, 31, 31).addComponent(this.progressBars[2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(28, 28, 28).addComponent(this.progressBars[3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(30, 30, 30).addComponent(this.progressBars[4], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(28, 28, 28).addComponent(this.progressBars[5], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(31, 31, 31).addComponent(this.progressBars[6], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(30, 30, 30).addComponent(this.progressBars[7], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(29, 29, 29).addComponent(this.progressBars[8], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(28, 28, 28).addComponent(this.progressBars[9], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap(229, Short.MAX_VALUE)));
        panelGraficoLayout.setVerticalGroup(panelGraficoLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelGraficoLayout.createSequentialGroup().addContainerGap().addGroup(panelGraficoLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.progressBars[9], GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE).addComponent(this.progressBars[8], GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE).addComponent(this.progressBars[7], GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE).addComponent(this.progressBars[6], GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE).addComponent(this.progressBars[5], GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE).addComponent(this.progressBars[4], GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE).addComponent(this.progressBars[3], GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE).addComponent(this.progressBars[2], GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE).addComponent(this.progressBars[1], GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE).addComponent(this.progressBars[0], GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)).addContainerGap()));

        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.scrollPanel, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE).addComponent(this.panelEqualizer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(this.panelGrafico, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.scrollPanel, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.panelEqualizer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.panelGrafico, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        this.pack();
    }

    private void createChannels() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                final PanelChannel panelChanel = new PanelChannel(this.channelPanels.size() + 1);
                panelChanel.setChannel(new Channel(this.mixer));
                this.channelPanels.add(panelChanel);
                this.mainPanel.add(panelChanel);
            }
        }
    }

    private void buttonStartActionPerformed(final java.awt.event.ActionEvent evt) {
        this.btnStart.setEnabled(false);
        this.btnStop.setEnabled(true);
        this.btnStop.setSelected(false);
        this.btnResume.setSelected(true);
        this.btnResume.setEnabled(false);

        this.mixer.start();
        this.audioOutput.start();

        for (final PanelChannel panel : this.channelPanels) {
            panel.start();
            panel.disabledJCombo();
        }
    }

    private void buttonStopActionPerformed(final java.awt.event.ActionEvent evt) {
        // parar as threads
        this.btnStop.setEnabled(false);
        this.btnResume.setSelected(false);
        this.btnResume.setEnabled(true);

        for (final PanelChannel panelChannel : this.channelPanels) {
            panelChannel.setStop();
        }
    }

    private void buttonResumeActionPerformed(final java.awt.event.ActionEvent evt) {
        this.btnResume.setSelected(true);
        this.btnResume.setEnabled(false);
        this.btnStop.setEnabled(true);
        this.btnStop.setSelected(false);

        for (final PanelChannel panelChannel : this.channelPanels) {
            panelChannel.setResume();
        }
    }

    public static void main(final String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FrameMixer().setVisible(true);
            }
        });
    }

    public void updateChart(final Frequency frequency) {
        for (int index = 0; index < Frequency.MAX_SIZE; index++) {
            this.progressBars[index].setValue(frequency.getBarSpectrum(index));
        }
    }
}
