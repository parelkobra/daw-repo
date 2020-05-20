package dam2.m3.pt1;

import java.util.Date;

/**
 * Tancament del tiquet per part d'un usuari a una determinada data
 *
 * @author alex
 *
 */
public class Tancament extends Accio {
    public Tancament(Usuari usuari, Tiquet tiquet, Date data) throws Exception {
	super(usuari, tiquet, data);
    }

    @Override
    public boolean esTancament() {
	return true;
    }

    @Override
    public String resum() {
	return "Tancament. Tiquet resolt";
    }
}
