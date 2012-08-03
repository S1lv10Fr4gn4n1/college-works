package edu.org.mixer.model.effect;

import edu.org.mixer.model.Frequency;

public final class Overdriver implements Effect {

    public EffectType getEffectType() {
        return EffectType.OVERDRIVER;
    }

    public synchronized Frequency processEffect(final Frequency frequency) {
    	final Frequency processedFrequency = frequency;

    	byte spectrumTotal = 0;
    	
    	// pegar os spectrum laterias 0-2 e 7-9 e ajustar
    	for (int i = 0; i < 3; i++) {
        	spectrumTotal += frequency.getBarSpectrum(i);
		}
    	
    	for (int i = 7; i < 9; i++) {
        	spectrumTotal += frequency.getBarSpectrum(i);
		}
    	
    	spectrumTotal = (byte) (spectrumTotal / 6);
    	
    	processedFrequency.setBarSpectrum(0, (byte) (spectrumTotal +1));
    	
    	if (spectrumTotal +2 > 6)    	
    		processedFrequency.setBarSpectrum(1, (byte) (spectrumTotal +2));
    	else
    		processedFrequency.setBarSpectrum(1, (byte) (spectrumTotal +3));
    	
    	processedFrequency.setBarSpectrum(2, (byte) (spectrumTotal +1));
    	processedFrequency.setBarSpectrum(3, spectrumTotal);
    	processedFrequency.setBarSpectrum(7, spectrumTotal);
    	processedFrequency.setBarSpectrum(8, (byte) (spectrumTotal +1));
    	processedFrequency.setBarSpectrum(9, (byte) (spectrumTotal +2));
        
    	return processedFrequency;

    }

}
