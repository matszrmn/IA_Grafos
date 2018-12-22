import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Grafo {
	public static int indexDepot;
	public static Vertice[] vertices;
	
	public static void inicializarGrafo(String arquivo) {
		try {
			InputStream is = new FileInputStream(arquivo);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			br.readLine(); //"NAME"
			br.readLine(); //"COMMENT"
			br.readLine(); //"TYPE"
			
			String dimension = br.readLine(); //String que procura a quantidade de vértices
			dimension = dimension.replace("DIMENSION : ", "");
			int dimensao = Integer.parseInt(dimension); //Transformando String "dimension" em inteiro
			
			vertices = new Vertice[dimensao]; //Modificando atributo static
			
			br.readLine(); //"EDGE_WEIGHT_TYPE"
			String capacidade = br.readLine();
			capacidade = capacidade.replace("CAPACITY : ","");
			
			Caminhos.capacity = Integer.parseInt(capacidade); //Modificando atributo static
			
			br.readLine(); //"NODE_COORD_SECTION"
			
			int i;
			int numeroVertice;
			int X;
			int Y;
			String leitor;
			for(i=0; i<vertices.length; i++) {
				leitor = br.readLine();
				
				Scanner sc = new Scanner(leitor);
				numeroVertice = sc.nextInt();
				X = sc.nextInt();
				Y = sc.nextInt();
				sc.close();
				
				Vertice novo = new Vertice(numeroVertice, X, Y);
				vertices[i] = novo;
			}
			br.readLine(); //"DEMAND_SECTION"
			
			int demanda;
			for(i=0;i<vertices.length;i++) {
				leitor = br.readLine();
				
				Scanner sc = new Scanner(leitor);
				sc.nextInt(); //Numero do vertice que ja foi definido anteriormente
				demanda = sc.nextInt();
				vertices[i].setDemanda(demanda);
				
				sc.close();
			}
			br.readLine(); //"DEPOT_SECTION"
			
			leitor = br.readLine();
			Scanner sc = new Scanner(leitor);
			int depot = sc.nextInt();
			
			for(i=0; i<vertices.length; i++) {
				if(vertices[i].getNumero()==depot) {
					indexDepot = i;
					break;
				}
			}
			
			sc.close();
			br.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Arquivo nao encontrado");
		}
		catch(NoSuchElementException e) {
			System.out.println("Arquivo em formato inapropriado");
		}
		catch(NumberFormatException e) {
			System.out.println("Arquivo em formato inapropriado");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static int distanciaEuclidiana(int i, int j) {
		//"i" e "j" sao posicoes do vetor e nao o numero dos vertices descritos no arquivo de entrada
		double diferencaX = vertices[i].getX() - vertices[j].getX(); 
		double diferencaY = vertices[i].getY() - vertices[j].getY();
		
		//Abaixo calculamos a distancia euclidiana utilizando apenas 1 variavel
		double distancia = (diferencaX*diferencaX)+(diferencaY*diferencaY);
		distancia = Math.sqrt(distancia);
		distancia = Math.round(distancia);
		
		int resp = (int)distancia;
		return resp;
	}
}
