package m3.uf5.ticketing.model;

import java.util.Date;

/**
 * Assignació del tiquet per part del supervisor a un tècnic indicant la prioritat corresponent en
 * una determinada data
 *
 * Durant la creació de l'assignació: - La prioritat ha de tenir un valor entre 1 i 9, si el valor
 * indicat està fora d'aquest rang indicar 1 - Podem suposar que el tècnic no serà nul
 *
 *
 * No es pot actualitzar cap atribut a un valor incorrecte
 *
 * @author alex
 *
 */
public class Assignacio extends Accio {
    public static final int MIN_PRIORITAT = 1;
    public static final int MAX_PRIORITAT = 9;
    private Tecnic tecnic;
    private int prioritat;

    public Assignacio(Supervisor supervisor, Tiquet tiquet, Date data, Tecnic tecnic, int prioritat) throws Exception {
	super(supervisor, tiquet, data);
	if (tecnic == null) throw new Exception("El tècnic de l'assignació no pot ser nul");
	if (prioritat < MIN_PRIORITAT || prioritat > MAX_PRIORITAT)
	    throw new Exception("La prioritat de l'assignació no és correcta");
	this.tecnic = tecnic;
	this.prioritat = prioritat;
    }

    public Assignacio() {
    }

    public Tecnic getTecnic() {
	return tecnic;
    }

    public void setTecnic(Tecnic tecnic) throws Exception {
	if (tecnic == null) throw new Exception("El tècnic de l'assignació no pot ser nul");
	this.tecnic = tecnic;
    }

    @Override
    public int getPrioritat() {
	return prioritat;
    }

    public void setPrioritat(int prioritat) throws Exception {
	if (prioritat < MIN_PRIORITAT || prioritat > MAX_PRIORITAT)
	    throw new Exception("La prioritat de l'assignació no és correcta");
	this.prioritat = prioritat;
    }

    @Override
    public boolean esAssignacio() {
	return true;
    }

    @Override
    public String resum() {
	return "Tiquet assignat a " + tecnic.getNom() + " (" + tecnic.getEmpresa() + ")";
    }
}
