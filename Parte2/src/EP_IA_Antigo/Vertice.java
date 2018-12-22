public class Vertice {
	private int numero;
	private int X;
	private int Y;
	private int demanda;
	
	public Vertice(int numero, int X, int Y) {
		this.numero = numero;
		this.X = X;
		this.Y = Y;
	}
	
	//Seção de getters e setters
	public int getNumero() { //Nao necessita de setter por causa do construtor
		return numero;
	}
	public int getX() { //Nao necessita de setter por causa do construtor
		return X;
	}
	public int getY() { //Nao necessita de setter por causa do construtor
		return Y;
	}

	public int getDemanda() {
		return demanda;
	}
	public void setDemanda(int demanda) {
		this.demanda = demanda;
	}
}
