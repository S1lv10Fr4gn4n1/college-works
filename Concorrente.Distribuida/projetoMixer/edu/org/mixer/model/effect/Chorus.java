package edu.org.mixer.model.effect;

import edu.org.mixer.model.Frequency;

public final class Chorus implements Effect {

    public EffectType getEffectType() {
        return EffectType.CHURUS;
    }

    public synchronized Frequency processEffect(final Frequency frequency) {	
    	final Frequency processedFrequency = frequency;       
    	byte spectrumTotal = 0;
    	
    	// pegar os spectrum laterias 0-2 e 7-9 e agustar
    	for (int i = 0; i < 2; i++) {
        	spectrumTotal += frequency.getBarSpectrum(i);
		}
    	
    	for (int i = 7; i < 9; i++) {
        	spectrumTotal += frequency.getBarSpectrum(i);
		}
    	
    	spectrumTotal = (byte) (spectrumTotal / 6);
    	
    	processedFrequency.setBarSpectrum(0, (byte) (spectrumTotal +2));
    	processedFrequency.setBarSpectrum(1, (byte) (spectrumTotal +1));
    	processedFrequency.setBarSpectrum(2, spectrumTotal);
    	processedFrequency.setBarSpectrum(6, (byte) (spectrumTotal + 1));
    	processedFrequency.setBarSpectrum(7, (byte) (spectrumTotal -1));
    	processedFrequency.setBarSpectrum(8, spectrumTotal);
    	processedFrequency.setBarSpectrum(9, (byte) (spectrumTotal +1));
        
    	return processedFrequency;
    }
}
