package pt.c02classes.s01knowledge.s02app.app;

import java.util.Scanner;

import pt.c02classes.s01knowledge.s01base.impl.Statistics;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;
import pt.c02classes.s01knowledge.s01base.inter.IStatistics;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerAnimals;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerMaze;
import pt.c02classes.s01knowledge.s02app.actors.ResponderAnimals;
import pt.c02classes.s01knowledge.s02app.actors.ResponderMaze;

public class OrchestratorInit {

	public static void main(String[] args) {
		
		System.out.println("Qual o desafio escolhido:");
		Scanner teclado = new Scanner(System.in);
		String desafio = teclado.nextLine();
		IEnquirer enq;
		IResponder resp;
		IStatistics stat;
		
		if(desafio.equalsIgnoreCase("Animals")){
			
			System.out.println("Escolha um animal:");
			String aescolhido = teclado.nextLine();			
	        
			System.out.println("Enquirer com " + aescolhido + "...");
			stat = new Statistics();
			resp = new ResponderAnimals(stat, aescolhido);
			enq = new EnquirerAnimals();
			enq.connect(resp);
			boolean acertei = enq.discover();
			
			if(acertei)
				System.out.println("oba!Acertei");
			else
				System.out.println("fuem fuem fuem");

			
			System.out.println("----------------------------------------------------------------------------------------\n");
	        
		}
	        
		else if(desafio.equalsIgnoreCase("Maze")){
		    
			System.out.println("Escolha o labirinto");
			String lescolhido = teclado.nextLine();
			
			System.out.println("Enquirer com " + lescolhido + "...");
			stat = new Statistics();
			resp = new ResponderMaze(stat, lescolhido);
			enq = new EnquirerMaze();
			enq.connect(resp);
			enq.discover();
			System.out.println("----------------------------------------------------------------------------------------\n");			
						
		}
		teclado.close();

	}

}
