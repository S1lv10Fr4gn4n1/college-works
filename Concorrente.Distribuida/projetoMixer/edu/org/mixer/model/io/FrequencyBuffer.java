package edu.org.mixer.model.io;

import java.util.ArrayList;
import java.util.List;

import edu.org.mixer.model.Frequency;

public final class FrequencyBuffer {

    private int bufferSize;

    private final List<Frequency> frequencies;

    //private static Semaphore bufferNotEmpty = new Semaphore(0);

    public FrequencyBuffer(final int bufferSize) {
        this.bufferSize = bufferSize;

        this.frequencies = new ArrayList<Frequency>(this.bufferSize);
    }

    public void setBufferSize(final int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    /**
     * @return the frequencies
     */
    public List<Frequency> getFrequencies() {
        return this.frequencies;
    }

    public void write(final Frequency frequency) {
        this.frequencies.add(frequency);
    }

    public boolean isTerminated() {
        return this.bufferSize == this.frequencies.size();
    }

}
