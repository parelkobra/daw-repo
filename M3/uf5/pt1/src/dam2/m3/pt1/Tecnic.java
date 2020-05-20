package dam2.m3.pt1;

/**
 * Si l'empresa és nul·la cal canviar-la per ""
 *
 * @author alex
 *
 */
public class Tecnic extends Usuari {
    private String empresa;

    public Tecnic(String usuari, String nom, int extensio, String empresa) throws Exception {
	super(usuari, nom, extensio);
	if (empresa == null) throw new Exception("L'empresa d'un tècnic no pot ser nula");
	this.empresa = empresa;
    }

    public String getEmpresa() {
	return empresa;
    }

    public void setEmpresa(String empresa) {
	if (empresa == null) return;
	this.empresa = empresa;
    }

    @Override
    public boolean esTecnic() {
	return true;
    }
}
