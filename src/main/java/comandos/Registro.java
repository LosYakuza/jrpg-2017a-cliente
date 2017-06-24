package comandos;

import java.io.IOException;

import javax.swing.JOptionPane;

import frames.MenuCreacionPj;
import mensajeria.Comando;
import mensajeria.Paquete;
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;

public class Registro extends ComandoCliente {

	@Override
	public void ejecutar() {
		synchronized (this) {
			PaquetePersonaje paquetePersonaje = cliente.getPaquetePersonaje();
			PaqueteUsuario paqueteUsuario = cliente.getPaqueteUsuario();
			if (paquete.getMensaje().equals(Paquete.msjExito)) {
				// Abro el menu para la creación del personaje
				MenuCreacionPj menuCreacionPJ = new MenuCreacionPj(this, paquetePersonaje);
				menuCreacionPJ.setVisible(true);

				// Espero a que el usuario cree el personaje
				try {
					wait();
				} catch (InterruptedException e1) {

				}

				// Le envio los datos al servidor
				paquetePersonaje.setComando(Comando.CREACIONPJ);
				try {
					cliente.getSalida().writeObject(paquetePersonaje.getJson());
				} catch (IOException e) {

				}
				JOptionPane.showMessageDialog(null, "Registro exitoso.");

				// Recibo el paquete personaje con los datos (la id
				// incluida)
				try {
					paquetePersonaje = (PaquetePersonaje) Paquete
							.loadJson(((String) cliente.getEntrada().readObject()));
				} catch (ClassNotFoundException | IOException e) {

				}
				// Indico que el usuario ya inicio sesion
				paqueteUsuario.setInicioSesion(true);
			} else {
				if (paquete.getMensaje().equals(Paquete.msjFracaso))
					JOptionPane.showMessageDialog(null, "No se pudo registrar.");

				// El usuario no pudo iniciar sesión
				paqueteUsuario.setInicioSesion(false);
			}
		}
	}

}
