package edu.org.mixer.model.effect;

import edu.org.mixer.model.Frequency;

public final class Delay implements Effect {

    public EffectType getEffectType() {
        return EffectType.DELAY;
    }

    public synchronized Frequency processEffect(final Frequency frequency) {
       final Frequency processedFrequency = frequency;
        
        int j = 9;
        
        for (int i = 0; i < 5; i++) {
            byte spectrum = (byte) (processedFrequency.getBarSpectrum(i) + processedFrequency.getBarSpectrum(j) / 2);
            processedFrequency.setBarSpectrum(i, spectrum);
            processedFrequency.setBarSpectrum(j, spectrum);
            j--;
        }
        
        return processedFrequency;
    }

}
