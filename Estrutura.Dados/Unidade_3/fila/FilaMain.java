package fila;

import javax.swing.JOptionPane;

public class FilaMain {
	public static void main(String[] args) {
		FilaVetor fila1 = new FilaVetor(5);
		FilaVetor fila2 = new FilaVetor(3);
		
		try {
			fila1.insere(1);
			fila1.insere(2);
			fila1.insere(3);
			fila1.insere(4);
			fila1.insere(5);
			
			fila2.insere(9);
			fila2.insere(67);
			fila2.insere(91);
			
			//fila2.retira();
			
			System.out.println("fila1: "+fila1);
			System.out.println("fila2: "+fila2);
			
			FilaVetor f3 = fila1.merge(fila2);
			
			System.out.println("fila3: "+f3);
			
			//fila.retira();
			//System.out.println(fila);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		
	}
	
}
