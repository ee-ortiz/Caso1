package Principal;

import java.util.ArrayList;

public class Productor extends Thread{

	private int maxProductos;
	private boolean tipoA; // si el atributo es falso ser� de tipo B
	private static ArrayList<Producto> buzonProductores;
	private static int tamanoBuzon; // espacios que sobran en el buzon



	public Productor(int maxProductos, boolean tipo, ArrayList<Producto> buzon, int tamanoBuzon){

		this.maxProductos = maxProductos;
		this.tipoA = tipo;
		Productor.buzonProductores = buzon;
		Productor.tamanoBuzon = tamanoBuzon;
		

	}

	public void run(){
		if(tipoA){
			// lo que har�a un trhead de tipo A
			
			int cont=maxProductos;
			System.out.println("Tama�o antes de: "+buzonProductores.size());
			for (int i = 0; i < maxProductos; i++) {
				Producto p= new Producto(tipoA);
				if(buzonProductores.size()<=tamanoBuzon)
				{
				buzonProductores.add(0,p);
				maxProductos--;
				}
				else{
					Thread.yield();}
			}

			System.out.println("Productor de productos tipo A. Tama�o buzon: "+ buzonProductores.size()  );
		}
		else{
			// lo que har�a un thread de tipo B
			int cont=maxProductos;

//			for (int i = 0; i < maxProductos; i++) {
//				Producto p= new Producto(!tipoA);
//				buzonProductores.add(p);
//				maxProductos--;
//			}
//			if(maxProductos==0)
//			{
//				Thread.yield();
//			}
			for (int i = 0; i < maxProductos; i++) {
				Producto p= new Producto(!tipoA);
				if(buzonProductores.size()<=tamanoBuzon)
				{
				buzonProductores.add(0,p);
				maxProductos--;
				}
				else{
					Thread.yield();}
			}
			System.out.println("Productor de productos tipo B. Tama�o buzon: "+ buzonProductores.size()  );

		}
	}

}
