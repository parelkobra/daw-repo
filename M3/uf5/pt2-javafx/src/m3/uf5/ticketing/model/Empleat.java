package m3.uf5.ticketing.model;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Si la ubicació o el lloc són nuls cal canviar-ho per ""
 *
 * @author alex
 *
 */
public class Empleat extends Usuari {
    private static final long serialVersionUID = 4641358378918364382L;

    public static final String[] UBICACIONS_EMPLEATS = { "Sala SATURN", "Sala JÚPITER", "Sala VENUS" };
    private String ubicacio;
    private String lloc;

    public Empleat(String usuari, String nom, int extensio, String ubicacio, String lloc) throws Exception {
	super(usuari, nom, extensio);
	if (ubicacio == null) throw new Exception("La ubicació de l'empleat no pot ser nul");
	if (lloc == null) throw new Exception("El lloc de feina de l'empleat no pot ser nul");
	this.ubicacio = ubicacio;
	this.lloc = lloc;
    }

    @Override
    public String getUbicacio() {
	return ubicacio;
    }

    public void setUbicacio(String ubicacio) throws Exception {
	if (ubicacio == null) throw new Exception("La ubicació de l'empleat no pot ser nul");
	this.ubicacio = ubicacio;
    }

    @Override
    public String getLloc() {
	return lloc;
    }

    public void setLloc(String lloc) throws Exception {
	if (lloc == null) throw new Exception("El lloc de feina de l'empleat no pot ser nul");
	this.lloc = lloc;
    }

    @Override
    public void updateUsuari(String nom, int extensio, String ubicacio, String lloc, String empresa) throws Exception {
	super.updateUsuari(nom, extensio, ubicacio, lloc, empresa);

	if (ubicacio == null) throw new Exception("La ubicació de l'empleat no pot ser nul");
	if (lloc == null) throw new Exception("El lloc de feina de l'empleat no pot ser nul");

	this.ubicacio = ubicacio;
	this.lloc = lloc;
    }

    @Override
    public boolean esEmpleat() {
	return true;
    }

    @Override
    public JsonObject toJson() {
	return Json.createObjectBuilder(super.toJson()).add("ubicacio", this.ubicacio).add("lloc", this.lloc).build();
    }
}
