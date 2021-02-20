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

		System.out.println("Tamaño buzon Productor:" + buzon.size());
		if(izquierda){
			// lo que hace el intermediario de la izquierda
//			if(conteo ==0 ||buzon.isEmpty())
//			{
//				try{
//					
//					wait();
//					
//				}catch(InterruptedException e){
//					e.printStackTrace();
//				}
//			}
						
			
			while(!buzon.isEmpty()){
				conteo--;
				
				Producto p=  buzon.remove(0);
				buzonIntermedio.add(p);
			}
			System.out.println("Tamaño bIntermedio:" +buzonIntermedio.size());
			
		}

		else{
			// lo que hace el intermediario de la derecha
		}

	}


}
