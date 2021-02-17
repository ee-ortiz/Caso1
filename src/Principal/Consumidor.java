package Principal;

import java.util.ArrayList;

public class Consumidor extends Thread{

	private int maxProductos;
	private boolean tipoA; // si el atributo es falso ser� de tipo B
	private static ArrayList<Producto> buzonConsumidores; 
	private static int tamanoBuzon; // espacios que sobran en el buzon


	public Consumidor(int maxProductos, boolean tipo, ArrayList<Producto> buzon, int tamanoBuzon){

		this.maxProductos = maxProductos;
		this.tipoA = tipo;
		Consumidor.buzonConsumidores = buzon;
		Consumidor.tamanoBuzon = tamanoBuzon;
	}

	public void run(){

		if(tipoA){
			// lo que har�a un trhead de tipo A
		}
		else{
			// lo que har�a un thread de tipo B
		}
	}

}
