import java.util.LinkedList;

public class SimulatedAnnealing {
	
	private static boolean aceita(int distTotalAnterior, int distTotalAtual, double temperatura) {
		double deltaE = distTotalAnterior - distTotalAtual; //deltaE será negativo
		double expoente = deltaE/temperatura;
		double probabilidadeAceitacao = Math.pow(Math.E, expoente); //Numero "e" elevado a (deltaE/temperatura)
		
		double random = Random.gerarAleatorio();
		if(probabilidadeAceitacao > random) return true;
		else return false;
	}
	private static void transferirParaSolucao(LinkedList<Integer> sequenciaEstados) { //Agrupa estados para a rota de cada caminhao
		int demandaTotal = 0;
		Caminhos.zerarSolucaoAnterior(); //Limpa lista de solucoes
		LinkedList<Integer> novaRota = new LinkedList<Integer>();
		
		for(Integer estado : sequenciaEstados) {
			if(Grafo.vertices[estado].getDemanda() + demandaTotal <= Caminhos.capacity) {
				demandaTotal += Grafo.vertices[estado].getDemanda();
				novaRota.add(estado);
			}
			else {
				Caminhos.solucao.addLast(novaRota);
				demandaTotal = Grafo.vertices[estado].getDemanda();
				novaRota = new LinkedList<Integer>();
				novaRota.add(estado);
			}
		}
		Caminhos.solucao.addLast(novaRota);
		
		for(LinkedList<Integer> rotaAtual : Caminhos.solucao) { 
			rotaAtual.addFirst(Grafo.indexDepot); //Insere depot no inicio da rota de cada caminhao
			rotaAtual.addLast(Grafo.indexDepot); //Insere depot no fim da rota de cada caminhao
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void simulatedAnnealing() {
		
		LinkedList<LinkedList<Integer>> solucaoAnterior = new LinkedList<LinkedList<Integer>>();
		LinkedList<Integer> sequenciaEstadosAntiga = new LinkedList<Integer>();
		LinkedList<Integer> sequenciaEstados = new LinkedList<Integer>();
		
		for(int i=0; i<Grafo.vertices.length; i++) { //Repassa todos os estados para a lista atual
			if(i != Grafo.indexDepot) sequenciaEstados.add(i); //Adiciona todos os estados menos o inicial
		}
		sequenciaEstadosAntiga = (LinkedList<Integer>) sequenciaEstados;
		int tamanhoLista = sequenciaEstados.size();
		
 		double temperatura = (double) tamanhoLista;
		double taxaResfriamento = (double) 1/(tamanhoLista*tamanhoLista);
		double limiteTemperatura = taxaResfriamento;
		double ultimaModificacao = (double) (tamanhoLista*tamanhoLista);
		
		int distTotalAnterior;
		int distTotalAtual;
 		int caminhoesUsadosAnterior = 0;
		int caminhoesUsadosAtual = 0;
		
 		transferirParaSolucao(sequenciaEstados); //Atualiza solucao
 		distTotalAnterior = Caminhos.distanciaTotalRotas();
 		caminhoesUsadosAnterior = Caminhos.solucao.size();
 		
		while(temperatura >= limiteTemperatura || ultimaModificacao > 0) {
			GerarVizinhos.gerarVizinhosAleatoriamente(sequenciaEstados);
			
			Caminhos.copiarDeSolucaoParaLista(solucaoAnterior);
			transferirParaSolucao(sequenciaEstados);
			distTotalAtual = Caminhos.distanciaTotalRotas();
			
			caminhoesUsadosAtual = Caminhos.solucao.size();
			
			boolean modificou = false;
			
			if(distTotalAtual > distTotalAnterior) { //Solucao atual eh pior que a anterior
				boolean aceitacao = aceita(distTotalAnterior, distTotalAtual, temperatura);
				if(aceitacao==false) { //Retoma a condicao inicial
					sequenciaEstados = (LinkedList<Integer>) sequenciaEstadosAntiga.clone();
					Caminhos.copiarDeListaParaSolucao(solucaoAnterior);
					modificou = false;
				}
				else {
					sequenciaEstadosAntiga = (LinkedList<Integer>) sequenciaEstados.clone();
					distTotalAnterior = distTotalAtual;
					caminhoesUsadosAnterior = caminhoesUsadosAtual;
					modificou = true;
				}
			}
			else if(distTotalAtual == distTotalAnterior) {
				if(caminhoesUsadosAtual <= caminhoesUsadosAnterior) { //Se melhorou ou se nao fez diferenca, a solucao sera
					                                                  //a atual
					sequenciaEstadosAntiga = (LinkedList<Integer>) sequenciaEstados.clone();
					distTotalAnterior = distTotalAtual;
					caminhoesUsadosAnterior = caminhoesUsadosAtual;
					modificou = true;
				}
				else {
					sequenciaEstados = (LinkedList<Integer>) sequenciaEstadosAntiga.clone();
					Caminhos.copiarDeListaParaSolucao(solucaoAnterior);
					modificou = false;
				}
			}
			else { //Solucao atual eh melhor que a anterior
				sequenciaEstadosAntiga = (LinkedList<Integer>) sequenciaEstados.clone();
				distTotalAnterior = distTotalAtual;
				caminhoesUsadosAnterior = caminhoesUsadosAtual;
				modificou = true;
			}
			if(modificou == true) ultimaModificacao += 1;
			else ultimaModificacao -=1;
			temperatura = temperatura - (temperatura*taxaResfriamento);
		}
 	}
}
