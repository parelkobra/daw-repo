package m3.uf5.ticketing.model;

import java.util.Date;

/**
 * Obertura d'un tiquet per un usuari en una determinada data
 *
 * @author alex
 *
 */
public class Obertura extends Accio {
    public Obertura(Empleat empleat, Tiquet tiquet, Date data) throws Exception {
	super(empleat, tiquet, data);
    }

    public Obertura() {
    }

    @Override
    public String resum() {
	return "";
    }

    @Override
    public boolean esObertura() {
	return true;
    }
}
