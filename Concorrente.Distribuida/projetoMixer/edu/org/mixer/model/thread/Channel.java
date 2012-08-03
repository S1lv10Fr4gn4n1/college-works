package edu.org.mixer.model.thread;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import edu.org.mixer.control.BarChartListener;
import edu.org.mixer.model.Frequency;
import edu.org.mixer.model.Mixer;
import edu.org.mixer.model.effect.Effect;
import edu.org.mixer.model.effect.EffectType;

public final class Channel extends Thread {

    private final Mixer mixer;

    private Frequency inputAudio;

    private final Set<EffectType> effectTypes;

    private final List<BarChartListener> listeners = new ArrayList<BarChartListener>();

    private boolean isTerminate;
    private boolean isMute;

    private final Frequency valueMute = new Frequency(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });

    private final Lock lock = new ReentrantLock();
    private final Condition canWrite = this.lock.newCondition();

    public Channel(final Mixer mixer) {
        this.mixer = mixer;
        this.inputAudio = null;
        this.isTerminate = false;

        this.effectTypes = new LinkedHashSet<EffectType>();
        this.addEffect(EffectType.EQUALIZER);
    }

    @Override
    public void run() {
        while (!this.isTerminate) {
            this.lock.lock();

            try {
                if (this.inputAudio == null) {
                    this.canWrite.await();
                }

                if (this.isMute) {
                    this.inputAudio = this.valueMute;
                } else {
                    this.inputAudio = this.processedAudioEffects(this.inputAudio); // processa efeito
                }

                // Atualiza gr�fico do canal.
                this.refreshInstrumentCharts(this.inputAudio);

                // Manda para o buffer.
                this.mixer.setInputAudio(this.inputAudio);

                this.inputAudio = null;
            } catch (final InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.lock.unlock();
            }
        }
    }

    /**
     * 
     * @param inputAudio
     */
    public void setInputAudio(final Frequency inputAudio) {
        this.lock.lock();

        try {
            this.inputAudio = inputAudio;
            this.canWrite.signal();
            /* Notifica a thread do channel e dispara
             * para poder processar o som e depois por no buffer do mixer. */
        } finally {
            this.lock.unlock();
        }
    }

    public void setTerminate(final boolean isTerminate) {
        this.isTerminate = isTerminate;
    }

    public void addEffect(final EffectType effectType) {
        if (effectType != null) {
            this.effectTypes.add(effectType);
        }
    }

    public void removeEffect(final EffectType effectType) {
        if (effectType != null) {
            this.effectTypes.remove(effectType);
        }
    }

    public void setMute(final boolean isMute) {
        this.isMute = isMute;
    }

    public void connectMixer() {
        this.mixer.addChannel();
    }

    public void disconnectMixer() {
        this.mixer.removeChannel();
    }

    public void addListener(final BarChartListener listener) {
        this.listeners.add(listener);
    }

    private Frequency processedAudioEffects(final Frequency frequency) {
        Frequency processedAudio = frequency;
        for (final Effect effect : this.mixer.getEffects()) {
            for (final EffectType type : this.effectTypes) {
                if (effect.getEffectType() == type) {
                    processedAudio = effect.processEffect(processedAudio);
                }
            }
        }
        return processedAudio;
    }

    private void refreshInstrumentCharts(final Frequency frequency) {
        for (final BarChartListener listener : this.listeners) {
            listener.updateChart(frequency);
        }
    }
}
