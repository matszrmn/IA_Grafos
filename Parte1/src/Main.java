import java.util.LinkedList;

public class Main {
	private static void imprimirSolucao() {
		int distanciaAcumulada = 0;
		int i=0;
		int numeroRota;
		int demandaRota;
		
		for(LinkedList<Integer> rotaAtual : Caminhos.solucao) {
			numeroRota = i+1;
			demandaRota = 0;
			
			System.out.print("Rota#"+numeroRota+": ");
			for(Integer estado : rotaAtual) {
				System.out.print(estado+" ");
				demandaRota += Grafo.vertices[estado].getDemanda();
			}
			System.out.print("custo: " + Caminhos.distanciaAcumuladaRota(i)+" ");
			System.out.println("demanda atendida: "+demandaRota);
			distanciaAcumulada += Caminhos.distanciaAcumuladaRota(i);
			i++;
		}
		System.out.println("Custo "+distanciaAcumulada);
	}
	private static void testar_SimulatedAnnealing_N_vezes(int n) {
		try {
			SimulatedAnnealing.simulatedAnnealing();
			
			int melhorCusto = Caminhos.distanciaTotalRotas();
			LinkedList<LinkedList<Integer>> melhorSolucao = new LinkedList<LinkedList<Integer>>();
			Caminhos.copiar(melhorSolucao);
			
			for(int i=1; i<=n; i++) {
				SimulatedAnnealing.simulatedAnnealing();
				if(Caminhos.distanciaTotalRotas() < melhorCusto) {
					melhorCusto = Caminhos.distanciaTotalRotas();
					Caminhos.copiar(melhorSolucao);
				}
			}
			Caminhos.solucao = melhorSolucao;		
			imprimirSolucao();
		}
		catch(NullPointerException e) {
			System.out.println("Erro Metodo Main");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
			Grafo.inicializarGrafo("C:\\Users\\pc\\Desktop\\P-n19-k2.vrp");
			Caminhos.inicializarListaSolucao();
		
			long tempoAnterior = System.currentTimeMillis();
			testar_SimulatedAnnealing_N_vezes(10);
			long tempoAtual = System.currentTimeMillis();
			long delta = tempoAtual - tempoAnterior;

			System.out.println("Tempo "+delta);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
