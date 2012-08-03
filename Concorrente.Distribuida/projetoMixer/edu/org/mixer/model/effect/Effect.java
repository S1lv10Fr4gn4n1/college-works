package edu.org.mixer.model.effect;

import edu.org.mixer.model.Frequency;

public interface Effect {

    EffectType getEffectType();

    Frequency processEffect(final Frequency frequency);
}
