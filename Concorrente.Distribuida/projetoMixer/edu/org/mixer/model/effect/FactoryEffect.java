/*
 * Created on 27/03/2010
 * 
 * Copyright 2010 BalaioDeGato. All rights reserved.
 */
package edu.org.mixer.model.effect;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author marcelo.silva
 */
public final class FactoryEffect {

    private FactoryEffect() {
        // Classe utilitaria para a construção dos efeitos [Effect].   
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Clone not supported for the singleton.");
    }

    public static Effect makeEffect(final EffectType effectType) {
        if (effectType == null) {
            throw new IllegalArgumentException("[EffectType] cannot be null.");
        }

        switch (effectType) {
            case CHURUS:
                return new Chorus();

            case DELAY:
                return new Delay();

            case EQUALIZER:
                return new Equalizer();

            case REVERB:
                return new Reverb();

            case OVERDRIVER:
                return new Overdriver();
        }

        return null;
    }

    public static Set<Effect> makeEffects(final EffectType... effectTypes) {
        if (effectTypes == null) {
            throw new IllegalArgumentException("[EffectType] cannot be null.");
        }

        final Set<Effect> effects = new LinkedHashSet<Effect>();

        for (final EffectType effectType : effectTypes) {
            effects.add(FactoryEffect.makeEffect(effectType));
        }

        return effects;
    }

}
