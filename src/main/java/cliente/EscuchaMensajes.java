package cliente;

import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import dominio.Personaje;
import estados.Estado;
import estados.EstadoBatalla;
import juego.Juego;
import mensajeria.Comando;
import mensajeria.Paquete;
import mensajeria.PaqueteAtacar;
import mensajeria.PaqueteBatalla;
import mensajeria.PaqueteDeMovimientos;
import mensajeria.PaqueteDePersonajes;
import mensajeria.PaqueteFinalizarBatalla;
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
			PaquetePersonaje paquetePersonaje;
			PaqueteMovimiento personaje;
			PaqueteBatalla paqueteBatalla;
			PaqueteAtacar paqueteAtacar;
			PaqueteFinalizarBatalla paqueteFinalizarBatalla;
			juego.setPersonajesConectados(new HashMap<Integer, PaquetePersonaje>());
			juego.setUbicacionPersonajes(new HashMap<Integer, PaqueteMovimiento>());

			while (true) {
				
				String objetoLeido = (String)entrada.readObject();

				paquete = Paquete.loadJson(objetoLeido);
				
				switch (paquete.getComando()) {
	
				case Comando.CONEXION:
					juego.setPersonajesConectados((Map<Integer, PaquetePersonaje>) ((PaqueteDePersonajes)paquete).getPersonajes());
					break;

				case Comando.MOVIMIENTO:
					juego.setUbicacionPersonajes((Map<Integer, PaqueteMovimiento>) ((PaqueteDeMovimientos)paquete).getPersonajes());
					break;
					
				case Comando.BATALLA:
					paqueteBatalla = (PaqueteBatalla)paquete;
					juego.getPersonaje().setEstado(Estado.estadoBatalla);
					Estado.setEstado(null);
					juego.setEstadoBatalla(new EstadoBatalla(juego, paqueteBatalla));
					Estado.setEstado(juego.getEstadoBatalla());
					break;
					
				case Comando.ATACAR:
					paqueteAtacar = (PaqueteAtacar) paquete;
					HashMap<String, Object> datos = juego.getEstadoBatalla().getPersonaje().getTodo();
					datos.putAll(paqueteAtacar.getTodoPersonaje());
					juego.getEstadoBatalla().getPersonaje().actualizar(datos);
					
					datos = juego.getEstadoBatalla().getEnemigo().getTodo();
					datos.putAll(paqueteAtacar.getTodoEnemigo());
					juego.getEstadoBatalla().getEnemigo().actualizar(datos);

					juego.getEstadoBatalla().setMiTurno(true);
					break;
					
				case Comando.FINALIZARBATALLA:
					paqueteFinalizarBatalla = (PaqueteFinalizarBatalla) paquete;
					juego.getPersonaje().setEstado(Estado.estadoJuego);
					Estado.setEstado(juego.getEstadoJuego());
					break;
					
				case Comando.ACTUALIZARPERSONAJE:
					paquetePersonaje = (PaquetePersonaje) paquete;

					juego.getPersonajesConectados().put(paquetePersonaje.getId(), paquetePersonaje);
					
					if(juego.getPersonaje().getId() == paquetePersonaje.getId()) {
						juego.actualizarPersonaje();
						juego.getEstadoJuego().actualizarPersonaje();
					}
				}	
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Fallo la conexión con el servidor.");
			e.printStackTrace();
		}
	}
}