package pt.c01interfaces.s01knowledge.s02app.actors;

import java.util.ArrayList;
import java.util.Iterator;

import pt.c01interfaces.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.impl.Pergunta;
import pt.c01interfaces.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IEnquirer;
import pt.c01interfaces.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IResponder;

public class Enquirer implements IEnquirer
{
    IObjetoConhecimento obj;
	
	public Enquirer()
	{
	}
	
	
	/**
	 * Se conecta ao entrevistador fazendo perguntas referentes aos animais.
	 * @param responder eh o entrevistador para perguntar
	 */
	
	@Override
	public void connect(IResponder responder)
	{	
		boolean encontrado = false;
        int animal = 0;
        int questionCounter = 0;
        ArrayList <Pergunta> perguntas = new ArrayList<Pergunta>();
		IBaseConhecimento bc = new BaseConhecimento();
		String listaAnimais[] = bc.listaNomes();
		
		
        while (animal < listaAnimais.length && !encontrado) //Percorre todos animais do bd 
        {
        	obj = bc.recuperaObjeto(listaAnimais[animal]); //carrega info do animal
	
			IDeclaracao decl = obj.primeira();
			Pergunta p;
			boolean naoExiste;
			
	        boolean animalEsperado = true;
			while (decl != null && animalEsperado) {  //Percorre todas as infos do animal atual
				String pergunta = decl.getPropriedade();
				String resposta;
				String respostaEsperada = decl.getValor();
				
				p = null;
				naoExiste = true;
				
				Iterator<Pergunta>it = perguntas.iterator();
				
				while(it.hasNext()) {  //Percorre todas perguntas já feitas
					p = it.next();
		            if (p.getPergunta().equalsIgnoreCase(pergunta)) {
		            	naoExiste = false;
		            	p.incQuantidadeVezes();
		            	break;
		            }
		        }
				
				if (naoExiste){  //Pergunta ao responder, se nao for repetir a pergunta
					resposta = responder.ask(pergunta);
					p = new Pergunta(pergunta, resposta);
					perguntas.add(questionCounter++, p);
					
				}else{ 
					resposta = p.getResposta();
				}

				//Se a resposta do enquirer for a esperada, pula para proxima do mesmo animal
				// se nao, pula para o proximo animal.
				if (resposta.equalsIgnoreCase(respostaEsperada))  
					decl = obj.proxima();
				else
				{
					animalEsperado = false;
					animal++;
					continue;
				}
			}
			
			encontrado = animalEsperado;
        }
        
		boolean acertei = responder.finalAnswer(listaAnimais[animal]);
		
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");

	}

}
