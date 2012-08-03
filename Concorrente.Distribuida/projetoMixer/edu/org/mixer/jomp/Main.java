package edu.org.mixer.jomp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.org.mixer.model.FrequencyArray;

public class Main {
	
	private static List<String> soundIn = new ArrayList<String>();
	
	public static void main(String[] args) {
        
		loadFileInstruments("src/edu/org/mixer/jomp/music1.sound");
		
		ForJOMP forJOMP = new ForJOMP();
		SectionsJOMP sectionsJOMP = new SectionsJOMP();
        
        System.out.println("Convertendo para Array");        
        String[] dados = new String[soundIn.size()];
        
        for (int i = 0; i < soundIn.size(); i++) {
            dados[i] = soundIn.get(i);
        }
        
        System.out.println("Iniciando conversao de arquivo .sound para FrequencyArray //omp for ");
        FrequencyArray freqArray = forJOMP.start(dados);
        System.out.println("Terminou a conversao ...");
        
        System.out.println("Iniciando processamento dos efeitos dos canais //omp sections ");
        sectionsJOMP.start(freqArray);
        System.out.println("Terminou o processamento ...");
	}
	
    private static void loadFileInstruments(String fileSound) {
        System.out.println("Lendo arquivo....");
        
        try {
            BufferedReader in;            
            String strFrequency = "";
            
            in = new BufferedReader(new FileReader(fileSound));

            while ((strFrequency = in.readLine()) != null) {
                if (strFrequency.isEmpty())
                    continue;
                                
                soundIn.add(strFrequency);
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Leitura terminada!");
    }

}
