package m3.uf6.pe1;

import java.util.LinkedList;

import javax.jdo.annotations.Unique;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@Unique(members = { "codi" })
@NamedQueries({ @NamedQuery(name = "Banc.findAll", query = "SELECT b FROM Banc b ORDER BY codi ASC"),
	@NamedQuery(name = "Banc.findByCodi", query = "SELECT b FROM Banc b WHERE b.codi = :codi") })
public class Banc {
    @Basic(optional = false)
    private int codi;

    @Basic(optional = false)
    private String nom;

    @OneToMany
    private LinkedList<Sucursal> sucursals;

    @OneToMany
    private LinkedList<Empleat> empleats;

    public Banc(int codi, String nom) {
	this.codi = codi;
	this.nom = nom;
	this.sucursals = new LinkedList<>();
	this.empleats = new LinkedList<>();
    }

    public int getCodi() {
	return codi;
    }

    public void setCodi(int codi) {
	this.codi = codi;
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public LinkedList<Sucursal> getSucursals() {
	return sucursals;
    }

    public void addSucursal(Sucursal oficina) {
	this.sucursals.add(oficina);
    }

    public void removeSucursal(Sucursal oficina) {
	this.sucursals.remove(oficina);
    }

    public LinkedList<Empleat> getEmpleats() {
	return empleats;
    }

    public void addEmpleat(Empleat empleat) {
	this.empleats.add(empleat);
    }

    public void removeEmpleat(Empleat empleat) {
	this.empleats.remove(empleat);
    }
}
