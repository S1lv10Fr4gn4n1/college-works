/*
 * Created on 22/04/2010
 * 
 * Copyright 2010 Senior Sistemas Ltda. All rights reserved.
 */
package edu.org.mixer.model;

import java.util.List;
import java.util.Set;

import edu.org.mixer.model.effect.Effect;

/**
 * @author marcelo.silva
 *
 */
public interface Mixer {

    Set<Effect> getEffects();

    void setFrequency(final int frequency, final int intencidade);

    void addChannel();

    void removeChannel();

    void start();

    void setInputAudio(final Frequency audio);
    
    void processMixer(final List<Frequency> channelsAudio);

    Frequency getOutputAudio() throws InterruptedException;

}
