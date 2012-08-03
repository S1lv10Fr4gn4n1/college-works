package edu.org.mixer.model.thread;

import java.util.ArrayList;
import java.util.List;

import edu.org.mixer.control.BarChartListener;
import edu.org.mixer.model.Frequency;
import edu.org.mixer.model.Mixer;

public final class AudioOutput extends Thread {

    private final Mixer mixer;

    private final List<BarChartListener> listeners = new ArrayList<BarChartListener>();

    public AudioOutput(final Mixer mixer) {
        this.mixer = mixer;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                final Frequency value = this.mixer.getOutputAudio();
                if (value == null) {
                    continue;
                }
                this.refreshCharts(value);
                Thread.sleep(80);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshCharts(final Frequency value) {
        for (final BarChartListener listener : this.listeners) {
            listener.updateChart(value);
        }
    }

    public void addListener(final BarChartListener listener) {
        this.listeners.add(listener);
    }
}
