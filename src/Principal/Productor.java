package Principal;

import java.util.ArrayList;


public class Productor extends Thread{

	private static Carga carga = new Carga();
	private int maxProductos = carga.getNumProductos(); // numero de productos que produce el productor
	private boolean tipoA; // si el atributo es falso será de tipo B
	private static ArrayList<Producto> buzonProductores; // todos los productores comparten los buzones
	private static int tamanoBuzon = carga.getEspaciosSobrantesBuzonProductores(); // numero de buzones totales



	public Productor(boolean tipo, ArrayList<Producto> buzon){
		this.tipoA = tipo;
		buzonProductores = buzon;
	}

	public void run(){

		if(tipoA){
			// lo que haría un trhead de tipo A
			boolean imprimir = true;
			while (maxProductos>0) {
				Producto p= new Producto(tipoA);
				synchronized(buzonProductores){
					if(buzonProductores.size() == tamanoBuzon) // si todos los buzones están llenos
					{
						if(imprimir){
							System.out.println("Buzón productor lleno: " +buzonProductores.size() +" de " + tamanoBuzon);
						}						
						yield();
						//						try {
						//							buzonProductores.wait();
						//						} catch (InterruptedException e) {
						//							// TODO Auto-generated catch block
						//							e.printStackTrace();
						//						}
					}			
				}
				synchronized(buzonProductores){
					if(buzonProductores.size() < tamanoBuzon){
						buzonProductores.add(p);
						System.out.println("Productos en el buzón productor: " + buzonProductores.size());
						maxProductos--;
						imprimir = true;

						if(buzonProductores.size()==1){ // esto significa que el buzon estaba vacio y el nuevo add lo dejo con un elemento
							buzonProductores.notifyAll();
						}
					}
					else{
						imprimir = false;
					}
				}
			}

		}
		else{

			boolean imprimir = true;
			while(maxProductos>0) {
				Producto p= new Producto(!tipoA);

				synchronized(buzonProductores){
					if(buzonProductores.size() == tamanoBuzon) // si todos los buzones están llenos
					{
						if(imprimir){
							System.out.println("Buzón productor lleno: " +buzonProductores.size() +" de " + tamanoBuzon);
						}
						yield();
						//						try {
						//							buzonProductores.wait();
						//						} catch (InterruptedException e) {
						//							// TODO Auto-generated catch block
						//							e.printStackTrace();
						//						}
					}			
				}
				synchronized(buzonProductores){
					if(buzonProductores.size() < tamanoBuzon){
						buzonProductores.add(p);
						System.out.println("Productos en el buzón productor: " + buzonProductores.size());
						maxProductos--;
						imprimir = true;

						if(buzonProductores.size()==1){ // esto significa que el buzon estaba vacio y el nuevo add lo dejo con un elemento
							buzonProductores.notifyAll();
						}
					}
					else{
						imprimir = false;
					}
				}
			}
		}
	}

	public void agregarProductoABuzon(Producto p){

		synchronized(buzonProductores){
			while(buzonProductores.size() == tamanoBuzon) // si todos los buzones están llenos
			{
				yield();
			}
			buzonProductores.add(p);
			maxProductos--;

			if(buzonProductores.size()==1){ // esto significa que el buzon estaba vacio y el nuevo add lo dejo con un elemento
				buzonProductores.notify();
			}
		}
	}

}
