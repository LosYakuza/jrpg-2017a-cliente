package estados;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.JOptionPane;

import dominio.Item;
import dominio.Personaje;
import interfaz.GrafInventario;
import interfaz.MenuInfoPersonaje;
import juego.Juego;
import juego.Pantalla;
import mensajeria.Comando;
import mensajeria.PaqueteFinalizarBatalla;
import mensajeria.PaqueteFinalizarMercado;
import mensajeria.PaqueteMercado;
import mensajeria.PaqueteOfertaMercado;
import mensajeria.PaquetePersonaje;
import mundo.Mundo;
import recursos.Recursos;

public class EstadoMercado extends Estado {
	private PaqueteFinalizarMercado paqFinalizarMercado;
	private PaquetePersonaje personaje;

	private PaqueteOfertaMercado paqOfertaMercado;
	// Para oferta
	private int idItemOfrecido = -1;
	private String nameItemRequerido = "";

	private boolean quiereIntercambiar;
	private int[] posMouse;

	int posInicialItemsX = 200;

	public EstadoMercado(Juego juego, PaqueteMercado paqueteMercado) {
		super(juego);
		Pantalla.visibleChat();
		personaje = juego.getPersonaje();
		quiereIntercambiar = paqueteMercado.getQuiereIntercambiar();

		// limpio la accion del mouse
		juego.getHandlerMouse().setNuevoClick(false);

		paqFinalizarMercado = new PaqueteFinalizarMercado();
		paqFinalizarMercado.setId(personaje.getId());
		
		paqOfertaMercado = new PaqueteOfertaMercado();
	}

	@Override
	public void actualizar() {
		if (juego.getHandlerMouse().getNuevoClick()) {
			posMouse = juego.getHandlerMouse().getPosMouse();
			if (clickEnCancelar(posMouse[0], posMouse[1])) {
				finalizarMercado();
			} else if (clickEnAceptar(posMouse[0], posMouse[1])) {
				aceptoAccionMercado();
			}
			
			if (quiereIntercambiar) {
				actualizarIntercambio();
			} else {
				actualizarOferta();
			}
		}
	}

	private void actualizarIntercambio() {
		// implementar
	}

	private void actualizarOferta() {
		if (clickEnItemRequerido(posMouse[0], posMouse[1])) {
			nameItemRequerido = getNameClickedItem();
		} else if (clickEnItemInventario(posMouse[0], posMouse[1])) {
			idItemOfrecido = getIdItemOfrecido();
		}
	}

	@Override
	public void graficar(Graphics g) {
		graficarElementosComunes(g);

		if(quiereIntercambiar) {
			graficarIntercambio(g);
		} else {
			graficarOferta(g);
		}
	}

	private void graficarIntercambio(Graphics g) {
		// implementar
	}
	
	private void graficarOferta(Graphics g) {
		// Grafico inventario y leyenda
		GrafInventario.dibujarInventario(g, 5, 100, personaje);
		
		// Grafico posibles items requeridos
		g.setColor(Color.LIGHT_GRAY);
		g.drawString("Seleccione el item que desea obtener", 350, 80);
		int posItemX = posInicialItemsX;
		for (String name : Item.nameItems) {
			g.drawImage(Recursos.items.get(name), posItemX, 5, null);
			posItemX += 55;
		} 
		
		if (nameItemRequerido != "") {
			g.setColor(Color.WHITE);
			g.drawString("Item requerido", 480, 190);
			g.drawImage(Recursos.items.get(nameItemRequerido), 500, 200, null);
		}
		
		if (idItemOfrecido != -1) {
			g.setColor(Color.WHITE);
			g.drawString("Item ofrecido", 480, 290);
			g.drawImage(Recursos.items.get(getNameOfItemInInventario(idItemOfrecido)), 500, 300, null);
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

	private boolean clickEnItemRequerido(int mouseX, int mouseY) {
		return (mouseX >= posInicialItemsX && mouseX <= 10 * 55 + posInicialItemsX) 
				&& (mouseY >= 5 && mouseY <= 5+63);
	}
	
	private boolean clickEnItemInventario(int mouseX, int mouseY) {
		return (mouseX >= 40 && mouseX <= 217) && (mouseY >= 160 && mouseY <= 349);
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

	private void aceptoAccionMercado() {
		// Si no tiene los datos que ingresa el usuario, no hago nada
		if (idItemOfrecido != -1 && nameItemRequerido != "") {
			paqOfertaMercado.addOferta(-1, idItemOfrecido, nameItemRequerido, personaje.getId());
			try {
				paqOfertaMercado.setComando(Comando.ENVIAR_OFERTA_MERCADO);
				juego.getCliente().getSalida().writeObject(paqOfertaMercado.getJson());
				Estado.setEstado(juego.getEstadoJuego());
				juego.getEstadoJuego().setHaySolicitud(true, juego.getPersonaje(), MenuInfoPersonaje.menuOfertaEnviada);
				juego.getHandlerMouse().setNuevoClick(false);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Fallo la conexion con el servidor.");
				e.printStackTrace();
			}
			finalizarMercado();
		}
	}

	private String getNameClickedItem() {
		return Item.nameItems[(posMouse[0]-posInicialItemsX)/55];
	}

	private String getNameOfItemInInventario(int id) {
		String name = "Anillo3"; // Le pongo default para que no falle por si las dudas...
		for (Item i : personaje.getInventario()) {
			if (i.getIdItem() == id)
				name = i.getNombre();
		}
		return name;
	}

	private int getIdItemOfrecido() {
		int fil = (posMouse[1]-100) / 63;
		int col = (posMouse[0]-5) / 59;
		
		int index = 3 * (fil-1) + (col-1);
		if (index < personaje.getInventario().size() && index > -1) {
			return personaje.getInventario().get(index).getIdItem();
		} else {
			if (idItemOfrecido != -1) // Si ya lo tengo asignado no lo desasigno
				return idItemOfrecido;
			else
				return -1;
		}
	}
}
