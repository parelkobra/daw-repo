package dam2.m3.pt1;

import java.util.Date;

public abstract class Accio {

	protected Date data;
	protected Usuari usuari;
	protected Tiquet tiquet;

	public Accio(Usuari usuari, Tiquet tiquet, Date data) {
		this.usuari = usuari;
		this.tiquet = tiquet;
		this.data = data != null ? data : new Date();
	}

	public int getPrioritat() {
		return 0;
	}

	public abstract String resum();

}
