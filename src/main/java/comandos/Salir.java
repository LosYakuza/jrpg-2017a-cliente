package comandos;

import java.io.IOException;

import mensajeria.Comando;
import mensajeria.Paquete;

public class Salir extends ComandoCliente {

	@Override
	public void ejecutar() {
		cliente.getPaqueteUsuario().setInicioSesion(false);
		try {
			cliente.getSalida().writeObject(new Paquete(Comando.DESCONECTAR).getJson());
			cliente.getSocket().close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

}
