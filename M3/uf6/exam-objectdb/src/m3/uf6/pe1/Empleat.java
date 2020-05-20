package m3.uf6.pe1;

import javax.jdo.annotations.Unique;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Unique(members = { "dni" })
@NamedQueries({ @NamedQuery(name = "Empleat.findAll", query = "SELECT e FROM Empleat e ORDER BY e.nom ASC"),
	@NamedQuery(name = "Empleat.findByDni", query = "SELECT e FROM Empleat e WHERE e.dni = :dni") })
public class Empleat {
    @Basic(optional = false)
    private String dni;

    @Basic(optional = false)
    private String nom;

    @Basic(optional = false)
    private int antiguitat;

    @Basic(optional = false)
    private double sou;

    public Empleat(String dni, String nom, int antiguitat, double sou) {
	this.dni = dni;
	this.nom = nom;
	this.antiguitat = antiguitat;
	this.sou = sou;
    }

    public String getDni() {
	return dni;
    }

    public void setDni(String dni) {
	this.dni = dni;
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public int getAntiguitat() {
	return antiguitat;
    }

    public void setAntiguitat(int antiguitat) {
	this.antiguitat = antiguitat;
    }

    public double getSou() {
	return sou;
    }

    public void setSou(double sou) {
	this.sou = sou;
    }
}
