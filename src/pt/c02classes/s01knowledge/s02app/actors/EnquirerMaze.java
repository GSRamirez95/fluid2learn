package pt.c02classes.s01knowledge.s02app.actors;

import java.util.ArrayList;

import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerMaze implements IEnquirer {

	IResponder responder;
	int posX; 
	int posY;

	String[] dirs = {"oeste", "norte", "leste", "sul"} ;
	int[][] nDirs = {{ -1,       0,		  1, 	  0},
					 {  0, 		 1,		  0,	 -1}};
	
	ArrayList<String> mapa = new ArrayList<String>();
	
	
	
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	
	// Resolve o labirinto - funcao recursiva - roda uma vez para cada ponto do labirinto
	private boolean solveMaze(String voltaPasso){
		boolean resolveu = false;
		String resp = null;
		
		
		/* Inverte direcao de ida para direcao de volta
		 * parametro voltaPasso indica a direcao do ultimo movimento
		 */
		switch (voltaPasso) {
			case "norte": voltaPasso = "sul"; break;
			case "sul":   voltaPasso = "norte"; break;
			case "leste": voltaPasso = "oeste"; break;
			case "oeste": voltaPasso = "leste"; break;
			default: break;
		}
		
		// Testa se ja esta na Saida
		if(responder.ask("aqui").equals("saida"))
			return true;
		
		// Se ainda nao chegou na saida, procura caminhos disponiveis
		for(int i = 0; i < dirs.length; i++){
			if(!voltaPasso.equals(dirs[i])){
				resp = responder.ask(dirs[i]);
				
				// Verifica se nao ha parede, nem mundo e nem ja passou por ali.
				if( !resp.equals("parede") && 
					!resp.equals("mundo") && 
					!mapa.contains(  (posX + nDirs[0][i]) + "," + (posY + nDirs[1][i])   )
				   ){
					
					//Solicita movimento para responder
					responder.move(dirs[i]);
					
					//Atualiza posicao atual e guarda que esta passando por ali
					posX += nDirs[0][i];
					posY += nDirs[1][i];
					mapa.add(posX + "," + posY);
					
					// Chama recursao para proximo passo
					resolveu = solveMaze(dirs[i]);
				
				}
				// A partir do momento que resolver em uma das direcoes, para de procurar outras
				if(resolveu)
					break;
			}
		}
		
		// Se ja tiver resolvido, apenas termina retornando true, se nao, volta para tentar outro caminho
		if(resolveu)
			return true;
		else{
			
			// Solicita ao responder para voltar ao anterior e remove registro de que passou por ali
			responder.move(voltaPasso);
			mapa.remove(posX + "," + posY);

			// Encontra posicao do vetor de direcoes que corresponde a direcao de volta
			int i;
			for(i = 0; i < dirs.length; i++)
				if(dirs[i].equals(voltaPasso))
					break;
			
			// Volta as coordenadas para localizacao anterior
			posX += nDirs[0][i];
			posY += nDirs[1][i];
			
			return false;
		}
	}
	
	
	
	public boolean discover() {
		
		posX = 0; posY = 0;
		solveMaze(" ");
		if (responder.finalAnswer("cheguei"))
			System.out.println("Voce encontrou a saida!");
		else
			System.out.println("Fuem fuem fuem!");
		
		//scanner.close();
		
		return true;
	}
	
}
