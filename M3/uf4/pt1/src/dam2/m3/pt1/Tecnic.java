package dam2.m3.pt1;

public class Tecnic extends Usuari {

	protected String empresa;

	public Tecnic(String usuari, String nom, int extensio, String empresa) {
		super(usuari, nom, extensio);
		this.empresa = empresa;
	}

	public String getEmpresa() {
		return empresa;
	}

	@Override
	public boolean esTecnic() {
		return true;
	}

}
