package m3.uf5.ticketing.model;

import java.io.Serializable;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Els noms d'usuari han de ser únics, es manté un registre estàtic dels noms d'usuari utilitzats
 * que només s'ha d'actualitzar cada vegada que es crea un nou usuari
 *
 * Si l'usuari és nul cal canviar-lo per "johndoe"
 *
 * Si el nom de l'usuari és nul canviar-lo per "JOHN DOE"
 *
 * Si l'extensió és negativa o superior a 9999 canviar a 0
 *
 * En cas de trobar un nom d'usuari existent cal afegir-li un sufix numèric aleatori de 3 dígits
 *
 * @author alex
 *
 */
public abstract class Usuari implements Serializable {
    private static final long serialVersionUID = 7356486918543784799L;

    public static final String[] TIPUS_USERS = { "Empleat", "Tècnic", "Supervisor" };
    private static final int MAX_EXTENSIO = 9999;
    private String usuari;
    private String nom;
    private int extensio;

    public Usuari(String usuari, String nom, int extensio) throws Exception {
	if (usuari == null) throw new Exception("El nom d'usuari no pot ser nul");
	if (nom == null || "".equals(nom.trim()))
	    throw new Exception("El nom de l'usuari no pot ser nul ni estar buit");
	if (extensio < 0 || extensio > MAX_EXTENSIO) throw new Exception("L'extensió de l'usuari és incorrecta");

	this.usuari = usuari;
	this.nom = nom;
	this.extensio = extensio;
    }

    public String getUsuari() {
	return usuari;
    }

    public void setUsuari(String usuari) throws Exception {
	throw new Exception("No es pot modificar el nom d'usuari");
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) throws Exception {
	if (nom == null || "".equals(nom.trim()))
	    throw new Exception("El nom de l'usuari no pot ser nul ni estar buit");
	this.nom = nom;
    }

    public int getExtensio() {
	return extensio;
    }

    public void setExtensio(int extensio) throws Exception {
	if (extensio < 0 || extensio > MAX_EXTENSIO) throw new Exception("L'extensió de l'usuari és incorrecta");
	this.extensio = extensio;
    }

    public boolean esEmpleat() {
	return false;
    }

    public boolean esSupervisor() {
	return false;
    }

    public boolean esTecnic() {
	return false;
    }

    public String getEmpresa() {
	return "";
    }

    public String getUbicacio() {
	return "";
    }

    public String getLloc() {
	return "";
    }

    public void updateUsuari(String nom, int extensio, String ubicacio, String lloc, String empresa) throws Exception {
	if (nom == null) throw new Exception("El nom de l'usuari no pot ser nul");
	if (extensio < 0 || extensio > MAX_EXTENSIO) throw new Exception("L'extensió de l'usuari és incorrecta");

	this.nom = nom;
	this.extensio = extensio;
    }

    @Override
    public int hashCode() {
	return usuari.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) return false;
	Usuari other = (Usuari) obj;
	return this.usuari.equals(other.getUsuari());
    }

    public JsonObject toJson() {
	return Json.createObjectBuilder().add("usuari", this.usuari).add("nom", this.nom).add("extensio", this.extensio)
		.build();
    }

    @Override
    public String toString() {
	return this.usuari + ", " + nom + " (" + extensio + ")";
    }
}
