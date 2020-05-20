package dam2.m3.pt1;

/**
 * Tècnic supervisor
 *
 * @author alex
 *
 */
public class Supervisor extends Tecnic {
    public Supervisor(String usuari, String nom, int extensio, String empresa) throws Exception {
	super(usuari, nom, extensio, empresa);
    }

    @Override
    public boolean esSupervisor() {
	return true;
    }
}
