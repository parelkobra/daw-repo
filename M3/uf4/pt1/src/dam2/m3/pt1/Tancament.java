package dam2.m3.pt1;

import java.util.Date;

public class Tancament extends Accio {

	private static final String MSG_TANCAMENT_TIQUET = "Tancament. Tiquet resolt";

	public Tancament(Usuari usuari, Tiquet tiquet, Date data) {
		super(usuari, tiquet, checkData(data));
	}

	private static Date checkData(Date data) {
		return (data != null) ? data : new Date();
	}

	@Override
	public String resum() {
		return MSG_TANCAMENT_TIQUET;
	}

}
