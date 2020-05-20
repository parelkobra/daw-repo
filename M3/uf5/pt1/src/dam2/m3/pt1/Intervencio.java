package dam2.m3.pt1;

import java.util.Date;

/**
 * Nova intervenció a una determinada data per resoldre el tiquet, indicant les
 * hores dedicades i una breu descripció de l'actuació
 *
 * Durant la creació de la intervenció: - Les hores han de ser >= 1, en cas
 * contrari indicar 1 - Si la descripció és nul·la substituir per un text buit
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
	if (hores < 1) throw new Exception("El mínim d'hores dedicades a una intervenció és 1");
	if (descripcio == null) throw new Exception("La descripció d'una intervenció no pot ser nula");
	this.hores = hores;
	this.descripcio = descripcio;
    }

    public int getHores() {
	return hores;
    }

    public void setHores(int hores) {
	if (hores <= 0) return;
	this.hores = hores;
    }

    public String getDescripcio() {
	return descripcio;
    }

    public void setDescripcio(String descripcio) {
	if (descripcio == null) return;
	this.descripcio = descripcio;
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
