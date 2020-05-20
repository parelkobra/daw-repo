package dam2.m3.pt1;

public class Empleat extends Usuari {

	private String ubicacio;
	private String lloc;

	public Empleat(String usuari, String nom, int extensio, String ubicacio, String lloc) {
		super(usuari, nom, extensio);
		this.ubicacio = (ubicacio == null) ? "" : ubicacio;
		this.lloc = (lloc == null) ? "" : lloc;
	}

	public String getUbicacio() {
		return ubicacio;
	}

	public String getLloc() {
		return lloc;
	}

}
