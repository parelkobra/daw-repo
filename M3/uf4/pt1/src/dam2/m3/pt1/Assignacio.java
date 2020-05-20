package dam2.m3.pt1;

import java.util.Date;

public class Assignacio extends Accio {

	private static final String MSG_ASSIGNACIO_1 = "Tiquet assignat a ";
	private static final String MSG_ASSIGNACIO_2 = " (";
	private static final String MSG_ASSIGNACIO_3 = ")";
	public static final int MIN_PRIORITAT = 1;
	public static final int MAX_PRIORITAT = 9;

	private int prioritat;
	private Tecnic tecnic;

	public Assignacio(Supervisor supervisor, Tiquet tiquet, Date data, Tecnic tecnic, int prioritat) {
		super(supervisor, tiquet, data);
		this.prioritat = (prioritat > MIN_PRIORITAT && prioritat <= MAX_PRIORITAT) ? prioritat : 1;
		this.tecnic = tecnic;
	}

	public int getPrioritat() {
		return prioritat;
	}

	public Tecnic getTecnic() {
		return tecnic;
	}

	@Override
	public String resum() {
		return MSG_ASSIGNACIO_1 + tecnic.nom + MSG_ASSIGNACIO_2 + tecnic.getEmpresa() + MSG_ASSIGNACIO_3;
	}

}
