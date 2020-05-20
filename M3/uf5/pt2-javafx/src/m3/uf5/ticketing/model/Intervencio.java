package m3.uf5.ticketing.model;

import java.util.Date;

/**
 * Nova intervenció a una determinada data per resoldre el tiquet, indicant les hores dedicades i
 * una breu descripció de l'actuació
 *
 * Durant la creació de la intervenció: - Les hores han de ser >= 1, en cas contrari indicar 1 - Si
 * la descripció és nul·la substituir per un text buit
 *
 *
 * No es pot actualitzar cap atribut a un valor incorrecte
 *
 * @author alex
 *
 */
public class Intervencio extends Accio {
    private int hores;
    private String descripcio;

    public Intervencio(Tecnic tecnic, Tiquet tiquet, Date data, int hores, String descripcio) throws Exception {
	super(tecnic, tiquet, data);
	if (hores <= 0) throw new Exception("El mínim d’hores dedicades a una intervenció és 1");
	if (descripcio == null) throw new Exception("La descripció de la intervenció no pot ser nul·la");
	this.hores = hores;
	this.descripcio = descripcio.trim();
    }

    public Intervencio() {
    }

    public int getHores() {
	return hores;
    }

    public void setHores(int hores) throws Exception {
	if (hores <= 0) throw new Exception("El mínim d’hores dedicades a una intervenció és 1");
	this.hores = hores;
    }

    public String getDescripcio() {
	return descripcio;
    }

    public void setDescripcio(String descripcio) throws Exception {
	if (descripcio == null) throw new Exception("La descripció d'una intervenció no pot ser nul");
	this.descripcio = descripcio.trim();
    }

    @Override
    public boolean esIntervencio() {
	return true;
    }

    @Override
    public String resum() {
	return "(" + this.hores + "h.) " + this.descripcio;
    }
}
