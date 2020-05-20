package m3.uf6.pe1;

import javax.jdo.annotations.Unique;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Unique(members = { "nom", "comarca", "provincia", "cp" })
@NamedQueries({ @NamedQuery(name = "Municipi.findAll", query = "SELECT m FROM Municipi m ORDER BY m.nom ASC") })
public class Municipi {
    @Basic(optional = false)
    private String nom;

    @Basic(optional = false)
    private String comarca;

    @Basic(optional = false)
    private String provincia;

    @Basic(optional = false)
    private int cp;

    public Municipi(String nom, String comarca, String provincia, int cp) {
	this.nom = nom;
	this.comarca = comarca;
	this.provincia = provincia;
	this.cp = cp;
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public String getComarca() {
	return comarca;
    }

    public void setComarca(String comarca) {
	this.comarca = comarca;
    }

    public String getProvincia() {
	return provincia;
    }

    public void setProvincia(String provincia) {
	this.provincia = provincia;
    }

    public int getCp() {
	return cp;
    }

    public void setCp(int cp) {
	this.cp = cp;
    }

    @Override
    public boolean equals(Object o) {
	Municipi m = (Municipi) o;
	return this.nom.equals(m.getNom()) && this.comarca.equals(m.getComarca())
		&& this.provincia.equals(m.getProvincia()) && this.cp == m.getCp();
    }

    @Override
    public String toString() {
	return "Municipi [nom=" + nom + ", comarca=" + comarca + ", provincia=" + provincia + ", cp=" + cp + "]";
    }

}
