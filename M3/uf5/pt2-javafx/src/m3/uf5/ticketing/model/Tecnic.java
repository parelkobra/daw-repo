package m3.uf5.ticketing.model;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Si l'empresa és nul·la cal canviar-la per ""
 *
 * @author alex
 *
 */
public class Tecnic extends Usuari {
    private static final long serialVersionUID = 6479442052247879612L;

    public static final String[] EMPRESES = { "T-SYSTEMS", "INET" };
    private String empresa;

    public Tecnic(String usuari, String nom, int extensio, String empresa) throws Exception {
	super(usuari, nom, extensio);
	if (empresa == null) throw new Exception("L'empresa del tècnic no pot ser nul·la");
	this.empresa = empresa;
    }

    @Override
    public String getEmpresa() {
	return empresa;
    }

    public void setEmpresa(String empresa) throws Exception {
	if (empresa == null) throw new Exception("L'empresa del tècnic no pot ser nul·la");
	this.empresa = empresa;
    }

    @Override
    public boolean esTecnic() {
	return true;
    }

    @Override
    public void updateUsuari(String nom, int extensio, String ubicacio, String lloc, String empresa) throws Exception {
	super.updateUsuari(nom, extensio, ubicacio, lloc, empresa);

	if (empresa == null) throw new Exception("L'empresa del tècnic no pot ser nul·la");
	this.empresa = empresa;
    }

    @Override
    public JsonObject toJson() {
	return Json.createObjectBuilder(super.toJson()).add("empresa", this.empresa)
		.add("supervisor", this.esSupervisor()).build();
    }
}
