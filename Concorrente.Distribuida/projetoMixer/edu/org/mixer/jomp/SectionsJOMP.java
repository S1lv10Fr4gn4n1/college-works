package edu.org.mixer.jomp;

import jomp.runtime.OMP;
import edu.org.mixer.model.Frequency;
import edu.org.mixer.model.FrequencyArray;
import edu.org.mixer.model.effect.Delay;
import edu.org.mixer.model.effect.Effect;
import edu.org.mixer.model.effect.Reverb;

public final class SectionsJOMP {

    //silvio, tirado o main, e feito um start passando o FrequencyArray
	public void start(FrequencyArray frequencyArray) {
		OMP.setNumThreads(3);

		int myid = 0;
		int totalGeral  = 0;
		int totChannel1 = 0;
		int totChannel2 = 0;
		int totChannel3 = 0;
		
		int size = frequencyArray.getSize();
		
		//omp parallel sections private(myid) reduction(+:totalGeral)
		{

			//omp section
			{
				myid = OMP.getThreadNum();
				System.out.println("-> Thread entrou: " +myid);
				Effect[] effects = new Effect[] { new Delay(), new Reverb() };
				for (int position = 0; position < size; position++) {
    					Frequency frequency = frequencyArray.getFrequency(0, position);
    					frequencyArray.setFrequency(0, position, processedAudioEffects(effects, frequency));
    					
    					System.out.println("-> Thread " + myid + ", processou: " + frequency + ", position: " + position + ", size: " + size);
    					totalGeral++;
    					totChannel1++;
    				}
				
				System.out.println("-> Thread saiu: " +myid);
			}

			//omp section
            {
                myid = OMP.getThreadNum();
                System.out.println("-> Thread entrou: " +myid);
                Effect[] effects = new Effect[] { new Reverb() };
                for (int position = 0; position < size; position++) {
                        Frequency frequency = frequencyArray.getFrequency(1, position);
                        frequencyArray.setFrequency(1, position, processedAudioEffects(effects, frequency));
                        
                        System.out.println("-> Thread " + myid + ", processou: " + frequency + ", position: " + position + ", size: " + size);
                        totalGeral++;
                        totChannel2++;
                    }
                
                System.out.println("-> Thread saiu: " +myid);
            }
			
            //omp section
            {
                myid = OMP.getThreadNum();
                System.out.println("-> Thread entrou: " +myid);
                Effect[] effects = new Effect[] { new Delay() };
                for (int position = 0; position < size; position++) {
                        Frequency frequency = frequencyArray.getFrequency(2, position);
                        frequencyArray.setFrequency(2, position, processedAudioEffects(effects, frequency));
                        
                        System.out.println("-> Thread " + myid + ", processou: " + frequency + ", position: " + position + ", size: " + size);
                        totalGeral++;
                        totChannel3++;
                    }
                
                System.out.println("-> Thread saiu: " +myid);
            }
			
		}
		
		System.out.println("End");
		System.out.println("Total Geral: " + totalGeral + "\nTotal1: " + totChannel1+ "\nTotal2: " + totChannel2+ "\nTotal3: " + totChannel3);
	}

	private Frequency processedAudioEffects(final Effect[] effects, final Frequency frequency) {
        Frequency processedAudio = frequency;
        for (int i = 0; i < effects.length; i++) {
            Effect effect = effects[i];
            processedAudio = effect.processEffect(processedAudio);
        }
        return processedAudio;
    }

}
