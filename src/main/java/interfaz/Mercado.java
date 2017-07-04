package interfaz;

import java.awt.Graphics;

import mensajeria.PaqueteMovimiento;
import mensajeria.PaquetePersonaje;

public class Mercado {
	public static boolean esZonaMercado() {
		PaqueteMovimiento paqMov;
		//paqMov = juego.getUbicacionPersonaje();
		//usar paq mov para determinar si el pj está en la zona de mercadeo.
		return true;
	}
	
	public static void graficarMenuMercado(Graphics g, PaquetePersonaje enemigo) {
		//Ponerle leyenda de Inventario Enemigo
		//Agregar img de objetos a intercambiar
		GrafInventario.dibujarInventario(g, 300, 100, enemigo);
		//Agregar botón INTERCAMBIAR
		
	}
}
