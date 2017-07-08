package comandos;

import mensajeria.PaqueteMercado;

public class IntercambioItems extends ComandoCliente {
	@Override
	public void ejecutar() {
		PaqueteMercado paqueteMercado = (PaqueteMercado) paquete;
	}
}
