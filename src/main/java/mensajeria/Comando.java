package mensajeria;

public abstract class Comando {
	
	public static final String[] CLASSNAMES =  {"Conexion","CreacionPJ","Desconectar","InicioSesion",
			"MostrarMapas","Movimiento","Registro","Salir","Batalla","Atacar",
			"FinalizarBatalla","ActualizarPersonaje"};
	public static final int ACTUALIZARPERSONAJE = 11;
	public static final int ATACAR = 9;
	public static final int BATALLA = 8;
	public static final int CONEXION = 0;
	public static final int CREACIONPJ = 1;
	public static final int DESCONECTAR = 2;
	public static final int FINALIZARBATALLA = 10; 
	public static final int INICIOSESION = 3;
	public static final int MOSTRARMAPAS = 4;
	public static final int MOVIMIENTO = 5;
	public static final int REGISTRO = 6;
	public static final int SALIR = 7;
	
	protected Paquete paquete;
	
	public void setPaquete(Paquete p){
		this.paquete = p;
	}
	
	public abstract void ejecutar();
	
}
