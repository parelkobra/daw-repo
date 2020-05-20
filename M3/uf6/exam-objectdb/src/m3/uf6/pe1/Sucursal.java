package m3.uf6.pe1;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
	@NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s ORDER BY s.municipi.nom ASC") })
public class Sucursal {
    @ManyToOne
    private Banc banc;

    @OneToOne
    private Municipi municipi;

    @Basic(optional = false)
    private int clients;

    public Sucursal(Banc banc, Municipi municipi, int clients) {
	this.banc = banc;
	this.municipi = municipi;
	this.clients = clients;
	this.banc.addSucursal(this);
    }

    public Banc getBanc() {
	return banc;
    }

    public void setBanc(Banc banc) {
	this.banc = banc;
    }

    public Municipi getMunicipi() {
	return municipi;
    }

    public void setMunicipi(Municipi municipi) {
	this.municipi = municipi;
    }

    public int getClients() {
	return clients;
    }

    public void setClients(int clients) {
	this.clients = clients;
    }

}
