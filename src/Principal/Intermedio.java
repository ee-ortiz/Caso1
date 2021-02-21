package Principal;

import java.util.ArrayList;

public class Intermedio extends Thread{

	private Carga carga = new Carga();
	private boolean izquierda; // representa si es el intermediario de la izquierda o de la derecha en el esquema productores/consumidores
	private int conteo = carga.getNumProductoresYConsumidores()*carga.getNumProductos(); // el numero total de productos que transportará
	private static ArrayList<Producto> buzonPoV; // puede ser el buzon de productores o consumidores
	private static ArrayList<Producto> buzonIntermedio;

	public Intermedio(boolean izquierda, ArrayList<Producto> buzon, ArrayList<Producto> intermedio){

		this.izquierda = izquierda;  
		buzonPoV = buzon;
		buzonIntermedio = intermedio;
	}

	public void run(){

		System.out.println("Productos en el buzon productor:" + buzonPoV.size());
		if(izquierda){

			while(conteo !=0){
				synchronized(buzonPoV){ // sincronizo el buzon para hacer funcionar al wait respecto al objeto que lo determina
					while(buzonPoV.size()==0){
						System.out.println("Buzon Productores vacio, esperando a que haya un producto...");
						try {
							buzonPoV.wait();
						} catch (InterruptedException e) {}			
					}

				}
				Producto deleted;
				synchronized (buzonPoV) {
					Producto eliminado = buzonPoV.remove(buzonPoV.size()-1); // elimina el ultimo elemento del buzon 				    
					deleted = eliminado;
					System.out.println("Productos en el buzón productor: " + buzonPoV.size());				

				}	

				synchronized (buzonIntermedio) {
					buzonIntermedio.add(deleted);	// se añade al buzon intermedio el objeto extraido del buzon
					conteo--;
					System.out.println("Productos en intermedio: " + buzonIntermedio.size());				
				}
			}
			System.out.println("Tamaño buzón Intermedio: " +buzonIntermedio.size());
		}
		else{
			// lo que hace el intermediario de la derecha
		}

	}

	public void operacionIntermedioIzquierdo(){

		synchronized(buzonPoV){ // sincronizo el buzon para hacer funcionar al wait respecto al objeto que lo determina
			while(buzonPoV.size()==0){
				System.out.println("Buzon Productores lleno, esperando a que haya un espacio...");
				try {
					wait();
				} catch (InterruptedException e) {}			
			}

			Producto eliminado = buzonPoV.remove(buzonPoV.size()-1); // elimina el ultimo elemento del buzon 				    
			buzonIntermedio.add(eliminado);	// se añade al buzon intermedio el objeto extraido del buzon
			conteo--;
			if(buzonPoV.size()==carga.getEspaciosSobrantesBuzonProductores()-1){
				buzonPoV.notify();
			}

		}
	}


}
