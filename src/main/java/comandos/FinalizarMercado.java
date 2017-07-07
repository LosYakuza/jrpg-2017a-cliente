package comandos;

import estados.Estado;

public class FinalizarMercado extends ComandoCliente {

	@Override
	public void ejecutar() {
		juego.getPersonaje().setEstado(Estado.estadoJuego);
		Estado.setEstado(juego.getEstadoJuego());
	}
}
