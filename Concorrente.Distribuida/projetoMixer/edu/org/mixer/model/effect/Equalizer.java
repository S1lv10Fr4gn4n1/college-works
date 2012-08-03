package edu.org.mixer.model.effect;

import edu.org.mixer.model.Frequency;

public final class Equalizer implements Effect {

    public EffectType getEffectType() {
        return EffectType.EQUALIZER;
    }

    public synchronized Frequency processEffect(final Frequency frequency) {	
        final Frequency processedFrequency = frequency;
        
        for (int index = 0; index < Frequency.MAX_SIZE; index++) {
             final byte spectrum = (byte) (frequency.getBarSpectrum(index));
             
             if (spectrum %2 == 0)             
                 processedFrequency.setBarSpectrum(index, spectrum);
             else
                 processedFrequency.setBarSpectrum(index, (byte) (spectrum -1));
        }
        
        return processedFrequency;
    }
}
