package edu.org.mixer.model.io;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import edu.org.mixer.model.Frequency;

public final class FrequencyStream {

    private int channelCount;

    public final List<FrequencyBuffer> frequencyBuffers;

    private final Lock lock;
    private final Condition canRead;

    public FrequencyStream(final int channelCount) {
        this.channelCount = channelCount;
        this.frequencyBuffers = new LinkedList<FrequencyBuffer>();
        this.lock = new ReentrantLock();
        this.canRead = this.lock.newCondition();
    }
    
    
    /**
     * @param channelCount the channelCount to set
     */
    public void setChannelCount(final int channelCount) {
        this.channelCount = channelCount;
    }

    public void writeFrequency(final Frequency frequency) {
        this.lock.lock();
        
        try {
            FrequencyBuffer currentBuffer = this.getCurrentBuffer();
            
            if (currentBuffer == null || currentBuffer.isTerminated()) {
                currentBuffer = new FrequencyBuffer(this.channelCount);
                this.frequencyBuffers.add(currentBuffer);
                
                if (this.frequencyBuffers.size() >= 2) {
                    this.canRead.signal();
                }
            }
            
            currentBuffer.write(frequency);
        
        } finally {
            this.lock.unlock();
        }
    }

    public FrequencyBuffer read() throws InterruptedException {
        this.lock.lock();

        try {
            while (this.frequencyBuffers.size() < 2) {
                this.canRead.await();
            }
            
            return this.frequencyBuffers.remove(0);
        } finally {
            this.lock.unlock();
        }
    }

    private FrequencyBuffer getCurrentBuffer() {
        if (this.frequencyBuffers.isEmpty()) {
            return null;
        }
        
        return this.frequencyBuffers.get(this.frequencyBuffers.size() - 1);
    }

}
