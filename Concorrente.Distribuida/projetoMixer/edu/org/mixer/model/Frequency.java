package edu.org.mixer.model;

public final class Frequency {

    public static final int MAX_SIZE = 10;

    private byte[] spectrum = new byte[Frequency.MAX_SIZE];

    public Frequency() {
        //this(Frequency.loadRandomSpectrum());
    }
    
    public Frequency(final byte[] spectrum) {
    	this.spectrum = spectrum;
    }

    public static byte[] loadRandomSpectrum() {
        final byte[] stream = new byte[Frequency.MAX_SIZE];
        for (int i = 0; i < stream.length; i++) {
            final byte valor = (byte) (Math.random() * 9);
            stream[i] = valor;
        }
        return stream;
    }
    
    public void setBarSpectrum(final int index, final byte spectrum) {
    	this.spectrum[index] = spectrum;
    }
    
    public void appendBarSpectrum(final int index, final byte barSpectrum) {
    	this.spectrum[index] += barSpectrum;
    }
    
    public boolean isGreaterThan(final int index, final byte barSpectrum) {
    	return this.spectrum[index] > barSpectrum;
    }

    public byte getBarSpectrum(final int index) {
        return this.spectrum[index];
    }

}
