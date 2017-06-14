package comandos;

import estados.Estado;

public class FinalizarBatalla extends ComandoCliente {

	@Override
	public void ejecutar() {
		juego.getPersonaje().setEstado(Estado.estadoJuego);
		Estado.setEstado(juego.getEstadoJuego());

	}

}
