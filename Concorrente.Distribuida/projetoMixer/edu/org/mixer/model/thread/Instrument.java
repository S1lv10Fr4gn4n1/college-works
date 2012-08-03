package edu.org.mixer.model.thread;

import edu.org.mixer.model.Frequency;

public final class Instrument extends Thread {

    private Channel channel;

    private boolean isStop;
    
    private boolean isTerminate;
    
    static int countS = 0;
    static String s = "";
    
    public Instrument(final Channel channel) {
        this.channel = channel;
        this.isTerminate = false;
        this.isStop = false;
    }

    @Override
    public void run() {
        while (!this.isTerminate) {

            synchronized(this) {
                while (this.isStop) {
                    try {
                        this.wait();
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            this.channel.setInputAudio(Instrument.generateAudioStream());

            try {
                Thread.sleep(1);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static Frequency generateAudioStream() {
        final Frequency stream = new Frequency();
        
        countS++;
        
        for (int index = 0; index < Frequency.MAX_SIZE; index++) {
            final byte value = (byte) (Math.random() * 9);
            stream.setBarSpectrum(index, value);
            s += value;
        }
        
        s += ";";
        
        if (countS == 3) {
            System.out.println(s);
            s = "";
            
            countS = 0;
        }
        return stream;
    }

    public void setStop() {
        this.isStop = true;
    }

    public synchronized void setResume() {
        this.isStop = false;
        this.notify();
    }

    public Channel getChannel() {
        return this.channel;
    }

    public void setChannel(final Channel channel) {
        this.channel = channel;
    }

    public void setTerminate(final boolean isTerminate) {
        this.isTerminate = isTerminate;
    }
}
