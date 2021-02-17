package Principal;

import java.util.ArrayList;

public class Intermedio extends Thread{

	private boolean izquierda; // representa si es el intermediario de la izquierda o de la derecha en el esquema productores/consumidores
	private int conteo; // el numero de productos restantes por transportar 
	public static ArrayList<Producto> buzon; // puede ser el buzon de productores o consumidores
	public static ArrayList<Producto> buzonIntermedio;

	public Intermedio(boolean izquierda, int conteo, ArrayList<Producto> buzon, ArrayList<Producto> buzonIntermedio){

		this.izquierda = izquierda;
		this.conteo = conteo;
		Intermedio.buzon = buzon;
		Intermedio.buzonIntermedio = buzonIntermedio;
	}

	public void run(){

		if(izquierda){
			// lo que hace el intermediario de la izquierda
		}

		else{
			// lo que hace el intermediario de la derecha
		}

	}

}
