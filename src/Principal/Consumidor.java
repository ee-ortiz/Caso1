package Principal;

import java.util.ArrayList;

public class Consumidor extends Thread{

	private static Carga carga = new Carga();
	private int maxProductos = carga.getNumProductos(); // numero de productos que consume el consumidor
	private boolean tipoA; // si el atributo es falso será de tipo B
	private static ArrayList<Producto> buzonConsumidores; // buzon con espacios compartidos por los comsumidores
	private static int tamanoBuzon = carga.getEspaciosSobrantesBuzonConsumidores(); // total espacios del buzon
	private static int productosConsumidos = 0; // conteo de los productos consumidos todos los consumidores comparten esta variable


	public Consumidor(boolean tipo, ArrayList<Producto> buzon){

		this.tipoA = tipo;
		buzonConsumidores = buzon;
	}

	public void run(){

		boolean imprimir = true; // se utiliza este imprimir para saltar muchas posibles impresiones en pantalla que repetiran la misma informacion debido al yield

		while (maxProductos>0) {

			while(buzonConsumidores.size() == 0) // no hay productos que consumir
			{
				if(imprimir){
					synchronized(buzonConsumidores){
						if(buzonConsumidores.size() == 0){
							System.out.println("Buzón consumidor vacio, esperando a que haya un producto...");
							imprimir = false; // no es neceario imprimir mas en pantalla el estado de buzón consumidor
						}
					}
				}						
				yield(); // espera semi-activa a que el buzón tenga elementos que consumir
			}			

			synchronized(buzonConsumidores){
				if(buzonConsumidores.size() > 0){ // si luego de que termine el yield el buzon tiene elementos continua la operacion de consumir

					int posicion = -1;
					for(int i = 0; i<buzonConsumidores.size(); i++){
						Producto actual = buzonConsumidores.get(i);
						if(actual.tipoA == tipoA){ // si se llega a encontrar un producto que consumir se guarda la posicion
							posicion = i;
							break;
						}
					}
					if(posicion != -1){ // si efectivamente se encuentra
						buzonConsumidores.remove(posicion); // se consume el producto
						maxProductos--;
						productosConsumidos++;
						System.out.println("Pruducto consumido. Productos en el buzon consumidor: " + buzonConsumidores.size()
						+ ". " + "Total productos consumidos: " + productosConsumidos);

						if(buzonConsumidores.size() == tamanoBuzon-1){ // esto significa que el buzon estaba vacio y el nuevo add lo dejo con un elemento
							buzonConsumidores.notify(); // se notifica a el thread de intermedio que estaba esperando que hubiera espacio para meter productos
						}
					}

				}
				else{
					imprimir = false; // no es necesario imprimir en pantalla el estado del buzón consumidor
				}
			}
		}

	}

}
