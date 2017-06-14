package comandos;

import mensajeria.PaquetePersonaje;

public class ActualizarPersonaje extends ComandoCliente {

	@Override
	public void ejecutar() {
		PaquetePersonaje paquetePersonaje = (PaquetePersonaje) paquete;

		juego.getPersonajesConectados().put(paquetePersonaje.getId(), paquetePersonaje);
		
		if(juego.getPersonaje().getId() == paquetePersonaje.getId()) {
			juego.actualizarPersonaje();
			juego.getEstadoJuego().actualizarPersonaje();
		}

	}

}
