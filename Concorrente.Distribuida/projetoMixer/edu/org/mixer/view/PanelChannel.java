package edu.org.mixer.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;

import edu.org.mixer.control.BarChartListener;
import edu.org.mixer.model.Frequency;
import edu.org.mixer.model.effect.EffectType;
import edu.org.mixer.model.thread.Channel;
import edu.org.mixer.model.thread.Instrument;

public final class PanelChannel extends JPanel implements BarChartListener {

    /**
     * @since 08/04/2010
     */
    private static final long serialVersionUID = -6066040004451637479L;

    private enum instrumentos {
        VAZIO, GUITARRA, BAIXO, TECLADO, BATERIA, PERCURSSAO, VOZ, VIOLAO, VIOLINO, BAIXOACUSTICO, FLAUTA, PIANO, GAITAFOLE, GAITAHARMONICA
    }

    private JToggleButton buttonChorus;
    private JToggleButton buttonDelay;
    private JToggleButton buttonOverdriver;
    private JToggleButton buttonMute;
    private JToggleButton buttonReverb;
    private JComboBox comboInstrument;
    private JProgressBar[] listaProgressBar;
    private JLabel labelChannel;
    private Channel channel;
    private Instrument instrument;

    private final int channelsCount;
    private instrumentos instAtual = instrumentos.VAZIO;

    public PanelChannel(final int channelsCount) {
        this.channelsCount = channelsCount;

        this.initComponents();
    }

    private void initComponents() {
        this.setEnabled(false);
        this.setMaximumSize(new java.awt.Dimension(306, 162));

        this.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        this.labelChannel = new JLabel("Channel " + this.channelsCount);

        final EventsChannel events = new EventsChannel();

        this.comboInstrument = new JComboBox(instrumentos.values());
        this.comboInstrument.addActionListener(events);

        this.buttonReverb = new JToggleButton();
        this.buttonReverb.setText("Rev");
        this.buttonReverb.addActionListener(events);

        this.buttonChorus = new JToggleButton();
        this.buttonChorus.setText("Cho");
        this.buttonChorus.addActionListener(events);

        this.buttonDelay = new JToggleButton();
        this.buttonDelay.setText("Del");
        this.buttonDelay.addActionListener(events);

        this.buttonOverdriver = new JToggleButton();
        this.buttonOverdriver.setText("Ove");
        this.buttonOverdriver.addActionListener(events);

        this.buttonMute = new JToggleButton();
        this.buttonMute.setText("Mute");
        this.buttonMute.addActionListener(events);

        this.listaProgressBar = new JProgressBar[10];

        for (int i = 0; i < this.listaProgressBar.length; i++) {
            final JProgressBar p = new JProgressBar();
            p.setOrientation(1);
            p.setMaximum(9);
            p.setMinimum(0);
            this.listaProgressBar[i] = p;
        }

        final GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.labelChannel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE).addComponent(this.buttonMute, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)).addComponent(this.comboInstrument, 0, 282, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.buttonReverb, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.buttonOverdriver, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addComponent(this.buttonDelay, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.buttonChorus, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.listaProgressBar[0], GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.listaProgressBar[1], GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.listaProgressBar[2], GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.listaProgressBar[3], GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.listaProgressBar[4], GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.listaProgressBar[5], GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.listaProgressBar[6], GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.listaProgressBar[7], GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.listaProgressBar[8], GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.listaProgressBar[9], GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.buttonMute).addComponent(this.labelChannel)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.comboInstrument, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.buttonChorus).addComponent(this.buttonDelay)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.buttonReverb).addComponent(this.buttonOverdriver))).addComponent(this.listaProgressBar[1], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE).addComponent(this.listaProgressBar[0], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE).addComponent(this.listaProgressBar[2], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE).addComponent(this.listaProgressBar[3], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE).addComponent(this.listaProgressBar[4], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE).addComponent(this.listaProgressBar[5], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE).addComponent(this.listaProgressBar[6], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE).addComponent(this.listaProgressBar[7], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE).addComponent(this.listaProgressBar[8], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE).addComponent(this.listaProgressBar[9], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    }

    public int getChannelsCount() {
        return this.channelsCount;
    }

    public void setChannel(final Channel channel) {
        this.channel = channel;
    }

    public Instrument getInstrument() {
        return this.instrument;
    }

    public void updateChart(final Frequency frequency) {
        for (int index = 0; index < Frequency.MAX_SIZE; index++) {
            this.listaProgressBar[index].setValue(frequency.getBarSpectrum(index));
        }
    }

    public void start() {
        if ((this.channel != null) && (this.instrument != null)) {
            this.channel.addListener(this);

            this.channel.start();
            this.instrument.start();
        }
    }

    public void setStop() {
        if (this.instrument != null) {
            this.instrument.setStop();
        }
    }

    public void setResume() {
        if (this.instrument != null) {
            this.instrument.setResume();
        }
    }

    private class EventsChannel implements ActionListener {

        public void actionPerformed(final ActionEvent e) {
            if (e.getSource() == PanelChannel.this.comboInstrument) {
                PanelChannel.this.instrument = null;

                PanelChannel.this.buttonChorus.setSelected(false);
                PanelChannel.this.buttonDelay.setSelected(false);
                PanelChannel.this.buttonOverdriver.setSelected(false);
                PanelChannel.this.buttonMute.setSelected(false);
                PanelChannel.this.buttonReverb.setSelected(false);

                if (PanelChannel.this.comboInstrument.getSelectedItem() != instrumentos.VAZIO) {
                    PanelChannel.this.instrument = new Instrument(PanelChannel.this.channel);
                }

                final String s = PanelChannel.this.comboInstrument.getSelectedItem().toString();
                final instrumentos instTemp = instrumentos.valueOf(s);

                if (PanelChannel.this.instAtual != instTemp) {
                    if ((PanelChannel.this.instAtual == instrumentos.VAZIO) && (instTemp != instrumentos.VAZIO)) {
                        PanelChannel.this.channel.connectMixer();
                    } else if ((PanelChannel.this.instAtual != instrumentos.VAZIO) && (instTemp == instrumentos.VAZIO)) {
                        PanelChannel.this.channel.disconnectMixer();
                    }
                }
                PanelChannel.this.instAtual = instTemp;
                return;
            }

            if (e.getSource() == PanelChannel.this.buttonChorus) {
                if (PanelChannel.this.instrument != null) {
                    if (PanelChannel.this.buttonChorus.isSelected()) {
                        PanelChannel.this.channel.addEffect(EffectType.CHURUS);
                    } else {
                        PanelChannel.this.channel.removeEffect(EffectType.CHURUS);
                    }
                }
                return;
            }

            if (e.getSource() == PanelChannel.this.buttonDelay) {
                if (PanelChannel.this.instrument != null) {
                    if (PanelChannel.this.buttonDelay.isSelected()) {
                        PanelChannel.this.channel.addEffect(EffectType.DELAY);
                    } else {
                        PanelChannel.this.channel.removeEffect(EffectType.DELAY);
                    }
                }
                return;
            }

            if (e.getSource() == PanelChannel.this.buttonOverdriver) {
                if (PanelChannel.this.instrument != null) {
                    if (PanelChannel.this.buttonOverdriver.isSelected()) {
                        PanelChannel.this.channel.addEffect(EffectType.OVERDRIVER);
                    } else {
                        PanelChannel.this.channel.removeEffect(EffectType.OVERDRIVER);
                    }
                }
                return;
            }

            if (e.getSource() == PanelChannel.this.buttonMute) {
                if (PanelChannel.this.instrument != null) {
                    PanelChannel.this.channel.setMute(PanelChannel.this.buttonMute.isSelected());
                }
                return;
            }

            if (e.getSource() == PanelChannel.this.buttonReverb) {
                if (PanelChannel.this.instrument != null) {
                    if (PanelChannel.this.buttonReverb.isSelected()) {
                        PanelChannel.this.channel.addEffect(EffectType.REVERB);
                    } else {
                        PanelChannel.this.channel.removeEffect(EffectType.REVERB);
                    }
                }
                return;
            }

            if (e.getSource() == PanelChannel.this.buttonDelay) {
                if (PanelChannel.this.instrument != null) {
                    if (PanelChannel.this.buttonDelay.isSelected()) {
                        PanelChannel.this.channel.addEffect(EffectType.DELAY);
                    } else {
                        PanelChannel.this.channel.removeEffect(EffectType.DELAY);
                    }
                }
                return;
            }
        }
    }

    
    public void disabledJCombo() {
        this.comboInstrument.setEnabled(false);        
    }
}
