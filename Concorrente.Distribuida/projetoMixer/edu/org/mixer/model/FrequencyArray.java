package edu.org.mixer.model;

public final class FrequencyArray {

	private final int channelsCount;
	private final int size;

	private Frequency[][] frequencies;

	public FrequencyArray(final int channelsCount, final int size) {
		this.channelsCount = channelsCount;
		this.size = size;
		this.initArray();
	}

	private void initArray() {
		this.frequencies = new Frequency[this.channelsCount][this.size];
	}

	void loadDefaultFrequencies() {
		for (int position = 0; position < this.size; position++) {
			for (int channel = 0; channel < this.channelsCount; channel++) {
				this.setFrequency(channel, position, FrequencyArray.generateAudio());
			}
		}
	}

	public static Frequency generateAudio() {
		final Frequency stream = new Frequency();
		for (int index = 0; index < Frequency.MAX_SIZE; index++) {
			final byte value = (byte) (Math.random() * 9);
			stream.setBarSpectrum(index, value);
		}
		return stream;
	}

	public void setFrequency(final int channel, final int position, final Frequency frequency) {
		this.frequencies[channel][position] = frequency;
	}

	public Frequency getFrequency(final int channel, final int position) {
		return this.frequencies[channel][position];
	}

	public Frequency[][] getFrequencies() {
		return this.frequencies;
	}

    public int getSize() {
        return this.size;
    }    
    
    public int getChannelsCount() {
        return this.channelsCount;
    }
}
