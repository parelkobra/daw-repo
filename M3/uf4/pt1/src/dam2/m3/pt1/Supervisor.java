package dam2.m3.pt1;

public class Supervisor extends Tecnic {

	public Supervisor(String usuari, String nom, int extensio, String empresa) {
		super(usuari, nom, extensio, empresa);
	}

	@Override
	public boolean esSupervisor() {
		return true;
	}

}
