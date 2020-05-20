package dam2.m3.pt1;

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

    @Override
    public String resum() {
	return "";
    }
}
