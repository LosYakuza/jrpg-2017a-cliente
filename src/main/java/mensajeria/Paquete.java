package mensajeria;

import java.io.Serializable;


import com.google.gson.Gson;

import com.google.gson.JsonSyntaxException;


public class Paquete implements Serializable, Cloneable {

	public static String msjExito = "1";
	public static String msjFracaso = "0";

	private String mensaje;
	private String ip;
	private int comando;

	private String classname;
	
	private static final Gson gson = new Gson();
	
	public Paquete() {

	}

	public Paquete(String mensaje, String nick, String ip, int comando) {
		this.mensaje = mensaje;
		this.ip = ip;
		this.comando = comando;
	}

	public Paquete(String mensaje, int comando) {
		this.mensaje = mensaje;
		this.comando = comando;
	}

	public Paquete(int comando) {
		this.comando = comando;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setComando(int comando) {
		this.comando = comando;
	}
	
	public int getComando() {
		return comando;
	}

	public String getMensaje() {
		return mensaje;
	}

	public String getIp() {
		return ip;
	}

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	public String getJson() {
		this.classname = this.getClass().getName();
		return gson.toJson(this);
	}

	public static Paquete loadJson(String json) {
		Paquete p = gson.fromJson(json, Paquete.class);
		try {
			return (Paquete) gson.fromJson(json, Class.forName(p.classname));
		} catch (JsonSyntaxException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} 

	}

	public Comando getComandoObj(String packageO) {
		try {
			Comando c;
			c = (Comando) Class.forName(packageO + "." + Comando.CLASSNAMES[comando]).newInstance();
			c.setPaquete(this);
			return c;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}

	}

}
