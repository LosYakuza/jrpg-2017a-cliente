package comandos;

import javax.swing.JOptionPane;

import mensajeria.Paquete;
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;

public class InicioSesion extends ComandoCliente {

	@Override
	public void ejecutar() {
		PaqueteUsuario paqueteUsuario = cliente.getPaqueteUsuario();
		if (paquete.getMensaje().equals(Paquete.msjExito)) {
			// El usuario ya inicio sesion
			paqueteUsuario.setInicioSesion(true);

			// Recibo el paquete personaje con los datos
			cliente.setPaquetePersonaje((PaquetePersonaje) paquete);

		} else {
			if (paquete.getMensaje().equals(Paquete.msjFracaso))
				JOptionPane.showMessageDialog(null,
						"Error al iniciar sesión. Revise el usuario y la contraseña");

			// El usuario no pudo iniciar sesión
			paqueteUsuario.setInicioSesion(false);
		}

	}

}
