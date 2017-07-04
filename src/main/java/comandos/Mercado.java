package comandos;

import estados.Estado;
import estados.EstadoMercado;
import mensajeria.PaqueteMercado;

public class Mercado extends ComandoCliente {

	@Override
	public void ejecutar() {
		PaqueteMercado paqueteMercado = (PaqueteMercado)paquete;
		juego.getPersonaje().setEstado(Estado.estadoMercado);
		Estado.setEstado(null);
		juego.setEstadoMercado(new EstadoMercado(juego, paqueteMercado));
		Estado.setEstado(juego.getEstadoMercado());
		
	}
	
}
