package Principal;

import java.util.ArrayList;

public class Intermedio extends Thread{

	private Carga carga = new Carga();
	private boolean izquierda; // representa si es el intermediario de la izquierda o de la derecha en el esquema productores/consumidores
	private int conteo = carga.getNumProductoresYConsumidores()*carga.getNumProductos(); // el numero total de productos que transportar�
	private static ArrayList<Producto> buzonProductor; // buzon de productores
	private static ArrayList<Producto> buzonConsumidor; // buzon de consumidores

	private static ArrayList<Producto> buzonIntermedio;

	public Intermedio(boolean izquierda, ArrayList<Producto> buzonP, ArrayList<Producto> buzonC, ArrayList<Producto> intermedio){

		this.izquierda = izquierda;  
		buzonProductor = buzonP;
		buzonConsumidor = buzonC;
		buzonIntermedio = intermedio;
	}

	public void run(){

		if(izquierda){
			System.out.println("Productos en el buzon productor:" + buzonProductor.size());
			while(conteo !=0){ // mientras hayan productos por transportar

				Producto deleted = null;
				synchronized(buzonProductor){ // sincronizo el buzon para hacer funcionar al wait respecto al objeto que lo determina
					while(buzonProductor.size()==0){ // mientras el buz�n productor est� vacio
						System.out.println("Buzon Productores vacio, esperando a que haya un producto...");
						try {
							buzonProductor.wait();	// espera activa a que haya un producto en el buz�n productor para transportar
						} catch (InterruptedException e) {}			
					}
					Producto eliminado = buzonProductor.remove(buzonProductor.size()-1); // elimina el ultimo elemento del buzon 				    			
					deleted = eliminado;
					System.out.println("Productos en el buz�n productor: " + buzonProductor.size());

				}

				synchronized (buzonIntermedio) {
					while(buzonIntermedio.size()==1){ // si el buzon intermedio llega a su maximo de un producto
						try {
							buzonIntermedio.wait(); // espera activa
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}		
					buzonIntermedio.add(deleted);	// se a�ade al buzon intermedio el objeto extraido del buzon de productos
					System.out.println("Productos en buz�n intermedio : " + buzonIntermedio.size());
					buzonIntermedio.notify(); // se notifica el otro thread contectado al buz�n intermemdio para que pueda sacra productos del buz�n
					conteo--;
				}
			}
		}
		else{
			System.out.println("Productos en el buzon consumidor:" + buzonProductor.size());
			while(conteo !=0){ // mientras hayan productos por transportar
				Producto deleted = null;
				synchronized(buzonIntermedio){ // sincronizo el buzon para hacer funcionar al wait respecto al objeto que lo determina
					while(buzonIntermedio.size()==0){ // mientra el buz�n intermedio est� vacio
						System.out.println("Buzon Intermedio vacio, esperando a que haya un producto...");
						try {
							buzonIntermedio.wait(); // espera activa 
						} catch (InterruptedException e) {}			
					}
					Producto eliminado = buzonIntermedio.remove(buzonIntermedio.size()-1); // elimina el unico producto el buz�n intermedio
					System.out.println("Productos en el buz�n Intermedio: " + buzonIntermedio.size());
					buzonIntermedio.notify(); // se notifica al otro thread pendiente del buz�n intermedio que puede ingresar productos 
					deleted = eliminado;

				}
				synchronized (buzonConsumidor) {
					while(buzonConsumidor.size()==carga.getEspaciosSobrantesBuzonConsumidores()){ // mientras el buz�n consumidor est� lleno
						try {
							System.out.println("Buzon Consumidor lleno, esperando a que haya espacio...");
							buzonConsumidor.wait(); // espera activa
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					buzonConsumidor.add(deleted);	// se a�ade al buzon intermedio el objeto extraido del buzon
					conteo--;
					System.out.println("Productos en buz�n consumidor: " + buzonConsumidor.size());	
				}
			}
		}

	}
}
