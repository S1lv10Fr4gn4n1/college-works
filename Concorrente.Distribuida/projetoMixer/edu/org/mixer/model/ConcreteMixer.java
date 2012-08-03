package edu.org.mixer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;

import edu.org.mixer.model.effect.Effect;
import edu.org.mixer.model.effect.EffectType;
import edu.org.mixer.model.effect.FactoryEffect;
import edu.org.mixer.model.io.FrequencyStream;
import edu.org.mixer.model.thread.MixerProcess;

public final class ConcreteMixer implements Mixer {

    private static Semaphore bufferNotEmpty = new Semaphore(0);

    private final Set<Effect> effects;
    
    private List<Frequency> outputBuffer = new ArrayList<Frequency>();
    
    private final Frequency frequencies;

    private int channelCount = 0;

    private FrequencyStream inputStream;
    
    private MixerProcess mixerProcess;
    //private FrequencyBuffer outputBuffer;

    public ConcreteMixer() {
        this.effects = FactoryEffect.makeEffects(
        /**/EffectType.EQUALIZER,
        /**/EffectType.CHURUS,
        /**/EffectType.DELAY,
        /**/EffectType.OVERDRIVER,
        /**/EffectType.REVERB);
        
        this.frequencies = new Frequency(new byte[] { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 });
        
        this.inputStream = new FrequencyStream(this.channelCount);
    }

    /* (non-Javadoc)
     * @see edu.org.mixer.model.Mixer#getEffects()
     */
    public Set<Effect> getEffects() {
        return Collections.unmodifiableSet(this.effects);
    }

    /* (non-Javadoc)
     * @see edu.org.mixer.model.Mixer#setFrequency(int, int)
     */
    public void setFrequency(final int frequency, final int intencidade) {
        this.frequencies.setBarSpectrum(frequency, (byte) intencidade);
    }

    /* (non-Javadoc)
     * @see edu.org.mixer.model.Mixer#addChannel()
     */
    public void addChannel() {
        this.inputStream.setChannelCount(++this.channelCount);
    }

    /* (non-Javadoc)
     * @see edu.org.mixer.model.Mixer#removeChannel()
     */
    public void removeChannel() {
        if (this.channelCount == 0) {
            return;
        }
        this.inputStream.setChannelCount(--this.channelCount);
    }

    /* (non-Javadoc)
     * @see edu.org.mixer.model.Mixer#start()
     */
    public synchronized void start() {
        //this.outputBuffer = new FrequencyBuffer(10);
        this.mixerProcess = new MixerProcess(this, this.inputStream); //, this.outputBuffer
        this.mixerProcess.start();
    }

    /* (non-Javadoc)
     * @see edu.org.mixer.model.Mixer#setInputAudio(edu.org.mixer.model.Frequency)
     */
    public void setInputAudio(final Frequency audio) {
        this.inputStream.writeFrequency(audio);
    }

    /* (non-Javadoc)
     * @see edu.org.mixer.model.Mixer#getOutputAudio()
     */
    public Frequency getOutputAudio() throws InterruptedException {
        ConcreteMixer.bufferNotEmpty.acquire();
        
        return this.outputBuffer.remove(0); //this.outputBuffer.read();
    }

    /* (non-Javadoc)
     * @see edu.org.mixer.model.Mixer#processMixer(java.util.List)
     */
    public void processMixer(final List<Frequency> channelsAudio) {
        final Frequency mixedFrequency = new Frequency();

        // Soma tudo.
        for (int i = 0; i < this.channelCount; i++) {
            for (int j = 0; j < 10; j++) {
                final Frequency frequency = channelsAudio.get(i);
                final byte barSpectrum = frequency.getBarSpectrum(j);
                mixedFrequency.appendBarSpectrum(j, barSpectrum);
            }
        }

        // Divide pela quantidade de canais.
        for (int index = 0; index < Frequency.MAX_SIZE; index++) {
            final byte barSpectrum = mixedFrequency.getBarSpectrum(index);
            mixedFrequency.setBarSpectrum(index, (byte) (barSpectrum / this.channelCount));
        }

        // Regula conform frequ�ncia.
        for (int index = 0; index < Frequency.MAX_SIZE; index++) {
            final byte barSpectrum = this.frequencies.getBarSpectrum(index);
            if (mixedFrequency.isGreaterThan(index, barSpectrum)) {
                mixedFrequency.setBarSpectrum(index, barSpectrum);
            }
        }

        this.outputBuffer.add(mixedFrequency);
        
        ConcreteMixer.bufferNotEmpty.release();
    }
}
