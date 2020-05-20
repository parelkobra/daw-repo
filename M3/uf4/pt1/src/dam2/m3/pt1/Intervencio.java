package dam2.m3.pt1;

import java.util.Date;

public class Intervencio extends Accio {

	private static final String MSG_INTERVENCIO_1 = "(";
	private static final String MSG_INTERVENCIO_2 = "h.) ";

	private int hores;
	private String descripcio;

	public Intervencio(Usuari usuari, Tiquet tiquet, Date data, int hores, String descripcio) {
		super(usuari, tiquet, data);
		this.hores = hores;
		this.descripcio = descripcio;
	}

	public int getHores() {
		return hores;
	}

	public String getDescripcio() {
		return descripcio;
	}

	@Override
	public String resum() {
		return MSG_INTERVENCIO_1 + getHores() + MSG_INTERVENCIO_2 + getDescripcio();
	}

}
