package dam2.m3.pt1;

import java.util.Objects;

/**
 * Els noms d'usuari han de ser únics, es manté un registre estàtic dels noms
 * d'usuari utilitzats que només s'ha d'actualitzar cada vegada que es crea un
 * nou usuari
 *
 * Si l'usuari és nul cal canviar-lo per "johndoe"
 *
 * Si el nom de l'usuari és nul canviar-lo per "JOHN DOE"
 *
 * Si l'extensió és negativa o superior a 9999 canviar a 0
 *
 * En cas de trobar un nom d'usuari existent cal afegir-li un sufix numèric
 * aleatori de 3 dígits
 *
 * @author alex
 *
 */
public abstract class Usuari {
    private static final int MAX_EXTENSIO = 9999;
    private String usuari;
    private String nom;
    private int extensio;

    public Usuari(String usuari, String nom, int extensio) throws Exception {
	super();
	if (usuari == null) throw new Exception("L'atribut usuari d'usuari no pot ser nul");
	if (nom == null || "".equals(nom)) throw new Exception("L'atribut nom d'usuari no pot ser nul");
	if (extensio < 0 || extensio > MAX_EXTENSIO)
	    throw new Exception("L'extensió d'un usuari ha de tenir un valor entre 0 i 9999");
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
	if (nom == null || "".equals(nom)) throw new Exception("L'atribut nom d'usuari no pot ser nul");
	this.nom = nom;
    }

    public int getExtensio() {
	return extensio;
    }

    public void setExtensio(int extensio) throws Exception {
	if (extensio < 0 || extensio > MAX_EXTENSIO)
	    throw new Exception("L'extensió d'un usuari ha de tenir un valor entre 0 i 9999");
	this.extensio = extensio;
    }

    public boolean esSupervisor() {
	return false;
    }

    public boolean esTecnic() {
	return false;
    }

    @Override
    public int hashCode() {
	return Objects.hash(extensio, nom, usuari);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) return true;
	Usuari other = (Usuari) obj;
	return extensio == other.extensio && Objects.equals(nom, other.nom) && Objects.equals(usuari, other.usuari);
    }
}
