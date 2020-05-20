package m3.uf5.ticketing.model;

/**
 * Tècnic supervisor
 *
 * @author alex
 *
 */
public class Supervisor extends Tecnic {
    private static final long serialVersionUID = 2083866720890184288L;

    public Supervisor(String usuari, String nom, int extensio, String empresa) throws Exception {
	super(usuari, nom, extensio, empresa);
    }

    @Override
    public boolean esSupervisor() {
	return true;
    }
}
