package comandos;

import estados.Estado;
import estados.EstadoMercado;
import mensajeria.PaqueteMercado;

public class OfertaMercado extends ComandoCliente {

	@Override
	public void ejecutar() {
		PaqueteMercado paqueteMercado = (PaqueteMercado) paquete;
	}
	
}
