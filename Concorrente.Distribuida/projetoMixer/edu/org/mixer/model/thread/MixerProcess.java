/*
 * Created on 22/04/2010
 * 
 * Copyright 2010 Senior Sistemas Ltda. All rights reserved.
 */
package edu.org.mixer.model.thread;

import edu.org.mixer.model.Mixer;
import edu.org.mixer.model.io.FrequencyBuffer;
import edu.org.mixer.model.io.FrequencyStream;

/**
 * @author marcelo.silva
 * 
 */
public final class MixerProcess extends Thread {

    private final Mixer mixer;

    private final FrequencyStream inputStream;

    public MixerProcess(final Mixer mixer, final FrequencyStream inputStream) { 
        this.mixer = mixer;
        this.inputStream = inputStream;
    }

    /* (non-Javadoc)
      * @see java.lang.Thread#run()
      */
    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {

                final FrequencyBuffer frequencyBuffer = this.inputStream.read();
                this.mixer.processMixer(frequencyBuffer.getFrequencies());

            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
