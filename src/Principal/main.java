package Principal;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class main {


	public static void main(String[] args){

		main principal = new main();
		Integer[] valores = principal.inicializar();
		int numProductoresYConsumidores = valores[0];
		int numProductos = valores[1];
		int espaciosSobrantesBuzonProductores = valores[2];
		int espaciosSobrantesBuzonConsumidores = valores[3];
		ArrayList<Producto> buzonProductores = new ArrayList<>();
		ArrayList<Producto> buzonConsumidores = new ArrayList<>();
		ArrayList<Producto> buzonIntermedio = new ArrayList<>();

		Intermedio i1 = new Intermedio(true, numProductoresYConsumidores*numProductos, buzonProductores, buzonIntermedio);
		Intermedio i2 = new Intermedio(false, numProductoresYConsumidores*numProductos, buzonConsumidores, buzonIntermedio);

		i1.start(); i2.start();

		for(int i = 0; i<numProductoresYConsumidores/2; i++){
			new Productor(numProductos, true, buzonProductores, espaciosSobrantesBuzonProductores).start();
			new Productor(numProductos, false, buzonProductores ,espaciosSobrantesBuzonProductores).start();

			new Consumidor(numProductos, true, buzonConsumidores, espaciosSobrantesBuzonConsumidores).start();
			new Consumidor(numProductos, true, buzonConsumidores, espaciosSobrantesBuzonConsumidores).start();

		}


	}

	public Integer[] inicializar(){

		Scanner in;
		Integer[] rta = new Integer[4];
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
			rta[0] = numPyC; rta[1] = numP; rta[2] = tBP; rta[3] = tBC;

		} catch (Exception e){e.printStackTrace();}

		return rta;


	}

}