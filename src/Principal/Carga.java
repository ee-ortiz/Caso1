package Principal;

import java.io.FileReader;
import java.util.Scanner;

public class Carga {

	private int numProductoresYConsumidores;
	private int numProductos;
	private int espaciosSobrantesBuzonProductores;
	private int espaciosSobrantesBuzonConsumidores;

	Carga(){

		Scanner in;
		try {
			in = new Scanner(new FileReader("./data/properties.txt"));
			int numPyC = 0, numP = 0, tBP = 0, tBC = 0;  // se inicializan las variables de las propiedades
			int conteo = 0;
			while(in.hasNext()) {
				String actual = in.next();
				conteo++;
				if(conteo == 3){
					numPyC= Integer.parseInt(actual);

				}
				else if(conteo == 6){
					numP = Integer.parseInt(actual);

				}
				else if(conteo == 9){
					tBP = Integer.parseInt(actual);

				}
				else if(conteo == 12){
					tBC = Integer.parseInt(actual);

				}
			}
			in.close();
			numProductoresYConsumidores = numPyC; numProductos = numP; 
			espaciosSobrantesBuzonProductores = tBP; espaciosSobrantesBuzonConsumidores = tBC;

		} catch (Exception e){e.printStackTrace();}

	}

	public int getNumProductoresYConsumidores() {
		return numProductoresYConsumidores;
	}

	public int getNumProductos() {
		return numProductos;
	}

	public int getEspaciosSobrantesBuzonProductores() {
		return espaciosSobrantesBuzonProductores;
	}

	public int getEspaciosSobrantesBuzonConsumidores() {
		return espaciosSobrantesBuzonConsumidores;
	}



}
