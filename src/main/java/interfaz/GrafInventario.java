package interfaz;

import java.awt.Graphics;

import dominio.Item;
import mensajeria.PaquetePersonaje;
import recursos.Recursos;

public class GrafInventario {
	public static void dibujarInventario(Graphics g, int x, int y, PaquetePersonaje personaje) {
		g.drawImage(Recursos.inventario, x, y, 242, 258, null);

		int posX = 35 + x, posY = 60 + y, cantItemsPorFila = 0;
		for (Item item : personaje.getInventario()) {
			g.drawImage(Recursos.items.get(item.getNombre()), posX, posY, null);
			cantItemsPorFila++;
			if (cantItemsPorFila == 3) {
				posX = 35 + x;
				posY += 63;
			} else {
				posX += 59;
			}
		}
		
	}
}
