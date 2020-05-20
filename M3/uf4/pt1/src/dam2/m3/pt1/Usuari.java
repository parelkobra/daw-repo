package dam2.m3.pt1;

import org.apache.commons.lang3.RandomUtils;

public abstract class Usuari {

	private static final int MAX_EXTENSIO = 9999;
	private static final String usuaris[] = new String[SistemaGestio.MAX_USERS];
	private int position = 0;

	protected String usuari;
	protected String nom;
	protected int extensio;

	public Usuari(String usuari, String nom, int extensio) {
		this.usuari = (usuari == null) ? "johndoe" : checkUsuari(usuari);
		this.nom = (nom == null) ? "JOHN DOE" : nom;
		this.extensio = (extensio < 0 || extensio > MAX_EXTENSIO) ? 0 : extensio;

		// If the array is not full, the new user will be added to the array
		if (position < SistemaGestio.MAX_USERS && usuaris[position] == null) {
			usuaris[position] = usuari;
			position++;
		}
	}

	public boolean esSupervisor() {
		return false;
	}

	public boolean esTecnic() {
		return false;
	}

	/**
	 * Returns the user, but if the user already exists on the 'usuaris' array it
	 * will return it adding a numeric suffix made of 3 digits.
	 */
	public static String checkUsuari(String usuari) {
		for (String element : usuaris) {
			if (element == usuari) {
				return usuari + RandomUtils.nextInt(0, 999);
			}
		}
		return usuari;
	}

}
