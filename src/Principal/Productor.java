package Principal;

import java.util.ArrayList;


public class Productor extends Thread{

	private static Carga carga = new Carga();
	private int maxProductos = carga.getNumProductos(); // numero de productos que produce el productor
	private boolean tipoA; // si el atributo es falso ser� de tipo B
	private static ArrayList<Producto> buzonProductores; // buzon con espacios compartido por todos los productres
	private static int tamanoBuzon = carga.getEspaciosSobrantesBuzonProductores(); // total de espacios del buzon



	public Productor(boolean tipo, ArrayList<Producto> buzon){
		this.tipoA = tipo;
		buzonProductores = buzon;
	}

	public void run(){

		boolean imprimir = true; // se utiliza este imprimir para saltar muchas posibles impresiones en pantalla que repetiran la misma informacion debido al yield
		while (maxProductos>0) {
			Producto p= new Producto(tipoA); // se crea el producto
			while(buzonProductores.size() == tamanoBuzon) // si todos los buzones est�n llenos
			{
				if(imprimir){
					synchronized(buzonProductores){
						if(buzonProductores.size() == tamanoBuzon){
							System.out.println("Buz�n productor lleno: " +buzonProductores.size() +" de " + tamanoBuzon);
							imprimir = false; // no es necesario imprimir m�s en pantalla el stado de un buz�n productor
						}
					}
				}						
				yield(); // espera semi-activa a que el buzon no este lleno para poder meter productos en el
			}			
			synchronized(buzonProductores){
				if(buzonProductores.size() < tamanoBuzon){ // si el buz�n no est� lleno
					buzonProductores.add(p); // se a�ade un producto a el
					System.out.println("Productos en el buz�n productor: " + buzonProductores.size());
					maxProductos--;
					imprimir = true;

					if(buzonProductores.size()==1){ // esto significa que el buzon estaba vacio y el nuevo add lo dejo con un elemento
						buzonProductores.notifyAll(); // se notifica a el thread intermedio que esperaba que hibera un producto en el buzon para poder transportarlo
					}
				}
				else{
					imprimir = false; // no es necesario imprimir en pantalla el estado del buz�n productor
				}
			}
		}

	}

}
