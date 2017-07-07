package cliente;

import java.io.ObjectInputStream;
import java.util.HashMap;

import javax.swing.JOptionPane;


import comandos.ComandoCliente;

import juego.Juego;

import mensajeria.Paquete;

import mensajeria.PaqueteMovimiento;
import mensajeria.PaquetePersonaje;

public class EscuchaMensajes extends Thread {

	private Juego juego;
	private Cliente cliente;
	private ObjectInputStream entrada;
	
	public EscuchaMensajes(Juego juego) {
		this.juego = juego;
		cliente = juego.getCliente();
		entrada = cliente.getEntrada();
	}
	
	public void run() {

		try {

			Paquete paquete;

			juego.setPersonajesConectados(new HashMap<Integer, PaquetePersonaje>());
			juego.setUbicacionPersonajes(new HashMap<Integer, PaqueteMovimiento>());

			while (cliente.estaEjecutando()) {
				
				String objetoLeido = (String)entrada.readObject();

				paquete = Paquete.loadJson(objetoLeido);
				
				ComandoCliente cc = (ComandoCliente)paquete.getComandoObj(ComandoCliente.PACKAGEO);
				cc.setJuego(juego);
				cc.ejecutar();
					
			}
		} catch (Exception e) {
			if(cliente.estaEjecutando()){
				JOptionPane.showMessageDialog(null, "Fallo la conexión con el servidor.");
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}