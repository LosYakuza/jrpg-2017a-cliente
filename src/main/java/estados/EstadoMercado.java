package estados;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.JOptionPane;

import interfaz.GrafInventario;
import juego.Juego;
import juego.Pantalla;
import mensajeria.PaqueteFinalizarBatalla;
import mensajeria.PaqueteFinalizarMercado;
import mensajeria.PaqueteMercado;
import mensajeria.PaquetePersonaje;
import mundo.Mundo;
import recursos.Recursos;

public class EstadoMercado extends Estado {
	private PaqueteFinalizarMercado paqFinalizarMercado;
	private PaquetePersonaje personaje;
	private boolean quiereIntercambiar;
	private int[] posMouse;

	public EstadoMercado(Juego juego, PaqueteMercado paqueteMercado) {
		super(juego);
		Pantalla.visibleChat();
		personaje = juego.getPersonaje();
		quiereIntercambiar = paqueteMercado.getQuiereIntercambiar();
		
		// limpio la accion del mouse
		juego.getHandlerMouse().setNuevoClick(false);
		
		paqFinalizarMercado = new PaqueteFinalizarMercado();
		paqFinalizarMercado.setId(personaje.getId());
	}

	@Override
	public void actualizar() {
		if (juego.getHandlerMouse().getNuevoClick()) {
			posMouse = juego.getHandlerMouse().getPosMouse();
			if (clickEnCancelar(posMouse[0], posMouse[1])) {
				finalizarMercado();
			}
		}
	}

	@Override
	public void graficar(Graphics g) {
		graficarElementosComunes(g);

		if(quiereIntercambiar) {
			
		} else {
			GrafInventario.dibujarInventario(g, 5, 100, personaje);
			g.setColor(Color.WHITE);
			g.drawString("Seleccione el item que desea intercambiar", 5, 90);
		}
		
	}

	private void graficarElementosComunes(Graphics g) {
		g.drawImage(Recursos.background, 0, 0, juego.getAncho(), juego.getAlto(), null);
		g.setColor(Color.WHITE);
 
		// Botón aceptar
		g.drawImage(Recursos.botonMenu, 300, 550, 200, 25, null);
		Pantalla.centerString(g, new Rectangle(300, 550, 200, 25), "Aceptar");

		// Botón cancelar
		g.drawImage(Recursos.botonMenu, 550, 550, 200, 25, null);
		Pantalla.centerString(g, new Rectangle(550, 550, 200, 25), "Cancelar");		
	}

	private boolean clickEnCancelar(int mouseX, int mouseY) {
		return (mouseX >= 550 && mouseX <= 750) && (mouseY >= 550 && mouseY <= 575);
	}

	private boolean clickEnAceptar(int mouseX, int mouseY) {
		return (mouseX >= 300 && mouseX <= 500) && (mouseY >= 550 && mouseY <= 575);
	}

	private void finalizarMercado() {
		try {
			juego.getCliente().getSalida().writeObject(paqFinalizarMercado.getJson());
			Estado.setEstado(juego.getEstadoJuego());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fallo la conexion con el servidor.");
			e.printStackTrace();
		}
	}
}
