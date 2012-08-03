package edu.org.mixer.jomp;

import edu.org.mixer.model.Frequency;
import edu.org.mixer.model.FrequencyArray;
import jomp.runtime.OMP;

public class ForJOMP {
    
    private FrequencyArray frequencyArray;
    
    public FrequencyArray start(String[] data) {
        int myid;
        int i = 0;

        System.out.println("Iniciando iniciando ImplementsJOMP");
        
        initFrequencyArray(data);
        
        OMP.setNumThreads(30);

        //omp parallel private(myid, i) shared(data)
        {
            myid = OMP.getThreadNum();

            //omp for
            for (i = 0; i < data.length; i++) {
                String stream = data[i];
                   
                System.out.println("thread: " + myid + ", dado: " + stream);
                
                convertData(data[i], i);
            }
            
            System.out.println("thread: " + myid);
        }
        return this.frequencyArray;
    }

    private void initFrequencyArray(String[] dados) {
        String[] countChannel = dados[0].split(";");
        
        this.frequencyArray = new FrequencyArray(countChannel.length, dados.length);
    }

    private void convertData(String value, int aTot) {
        String[] countChannel = value.split(";");
        
        for (int i = 0; i < countChannel.length; i++) {
            Frequency frequency = new Frequency(convertStreamToSpectrum(countChannel[i]));
            this.frequencyArray.setFrequency(i, aTot, frequency);
        }        
    }    
    
    private byte[] convertStreamToSpectrum(String spectrumIn) {
        byte[] spectrunOut = new byte[spectrumIn.length()];
        
        for (int i = 0; i < spectrumIn.length(); i++) {
            spectrunOut[i] = (byte) spectrumIn.charAt(i);
        }
        
        return spectrunOut;
    }
}
