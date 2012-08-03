package edu.org.mixer.model.effect;

import edu.org.mixer.model.Frequency;

public final class Reverb implements Effect {

    public EffectType getEffectType() {
        return EffectType.REVERB;
    }

    public synchronized Frequency processEffect(final Frequency frequency) { 
        final Frequency processedFrequency = frequency;       
        byte spectrumTotal = 0;
        
        for (int i = 3; i < 7; i++) {
            spectrumTotal += frequency.getBarSpectrum(i);
        }
        
        spectrumTotal = (byte) (spectrumTotal / 4);
        
        processedFrequency.setBarSpectrum(0, frequency.getBarSpectrum(0));
        processedFrequency.setBarSpectrum(1, frequency.getBarSpectrum(1));
        processedFrequency.setBarSpectrum(2, frequency.getBarSpectrum(2));
        processedFrequency.setBarSpectrum(3, (byte) (spectrumTotal));
        processedFrequency.setBarSpectrum(4, (byte) (spectrumTotal +1));
        processedFrequency.setBarSpectrum(5, (byte) (spectrumTotal +1));
        processedFrequency.setBarSpectrum(6, (byte) (spectrumTotal));
        processedFrequency.setBarSpectrum(7, frequency.getBarSpectrum(7));
        processedFrequency.setBarSpectrum(8, frequency.getBarSpectrum(8));
        processedFrequency.setBarSpectrum(9, frequency.getBarSpectrum(9));
        
        return processedFrequency;
    }
}
