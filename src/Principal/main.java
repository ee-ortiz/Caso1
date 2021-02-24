package Principal;

import java.util.ArrayList;

public class main {


	public static void main(String[] args){

		Carga carga = new Carga();
		ArrayList<Producto> buzonProductores = new ArrayList<>();
		ArrayList<Producto> buzonConsumidores = new ArrayList<>();
		ArrayList<Producto> buzonIntermedio = new ArrayList<>();

		new Intermedio(true, buzonProductores, buzonConsumidores,buzonIntermedio).start();
		new Intermedio(false, buzonProductores, buzonConsumidores,buzonIntermedio).start();

		for(int i = 0; i<carga.getNumProductoresYConsumidores()/2; i++){
			new Productor(true, buzonProductores).start();
			new Productor(false, buzonProductores).start();

			new Consumidor(true, buzonConsumidores).start();
			new Consumidor(false, buzonConsumidores).start();

		}

	}

}