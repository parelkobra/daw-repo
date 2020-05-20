package dam2.m3.pt1;

/**
 * Si la ubicació o el lloc són nuls cal canviar-ho per ""
 *
 * @author alex
 *
 */
public class Empleat extends Usuari {
    private String ubicacio;
    private String lloc;

    public Empleat(String usuari, String nom, int extensio, String ubicacio, String lloc) throws Exception {
	super(usuari, nom, extensio);
	if (ubicacio == null) throw new Exception("L'ubicació d'un empleat no pot ser nula");
	if (lloc == null) throw new Exception("El lloc de feina d'un empleat no pot ser nul");
	this.ubicacio = ubicacio;
	this.lloc = lloc;
    }

    public String getUbicacio() {
	return ubicacio;
    }

    public void setUbicacio(String ubicacio) {
	if (ubicacio == null) return;
	this.ubicacio = ubicacio;
    }

    public String getLloc() {
	return lloc;
    }

    public void setLloc(String lloc) {
	if (lloc == null) return;
	this.lloc = lloc;
    }
}
