import java.util.LinkedList;
import java.util.Random;

public class GerarVizinhos {
	public static void gerarVizinhosAleatoriamente(LinkedList<Integer> estados) {
		Random random = new Random();
		double valorAleatorio = random.nextGaussian();
		double tamanhoLista = (double) estados.size();
		
		int posRandom1 = (int) (Math.random()*tamanhoLista);
		int posRandom2 = (int) (Math.random()*tamanhoLista);
		
		while(posRandom1 == posRandom2) {
			posRandom1 = (int) (Math.random()*tamanhoLista);
			posRandom2 = (int) (Math.random()*tamanhoLista);
		}
		if(valorAleatorio < -0.431) {
			oneToOneExchange(posRandom1, posRandom2, estados);
		}
		else if(valorAleatorio > 0.431) {
			deleteAndInsert(posRandom1, posRandom2, estados);
		}
		else {
			partialReversal(posRandom1, posRandom2, estados);
		}
	}
	
	
	public static void oneToOneExchange (int pos1, int pos2, LinkedList<Integer> estados) {
		int swap1 = estados.get(pos1);
		int swap2 = estados.get(pos2);
		
		estados.set(pos1, swap2);
		estados.set(pos2, swap1);
	}
	public static void deleteAndInsert (int posDelete, int posInsert, LinkedList<Integer> estados) {
		int valorEstado = estados.get(posDelete);
		
		estados.remove(posDelete);
		estados.add(posInsert, valorEstado);
	}
	public static void partialReversal (int pos1, int pos2, LinkedList<Integer> estados) {
		int posEsquerda;
		int posDireita;
		int swap1;
		int swap2;
		
		if(pos1 < pos2) {
			posEsquerda = pos1;
			posDireita = pos2;
		}
		else { //pos2 < pos1
			posEsquerda = pos2;
			posDireita = pos1;
		}
		while(posEsquerda < posDireita) {
			swap1 = estados.get(posEsquerda);
			swap2 = estados.get(posDireita);
			
			estados.set(posEsquerda, swap2);
			estados.set(posDireita, swap1);
			
			posEsquerda++;
			posDireita--;
		}
	}
}
