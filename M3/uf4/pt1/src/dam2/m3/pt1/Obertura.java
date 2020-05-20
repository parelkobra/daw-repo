package dam2.m3.pt1;

import java.util.Date;

public class Obertura extends Accio {

	public Obertura(Empleat empleat, Tiquet tiquet, Date data) {
		super(empleat, tiquet, data);
	}

	@Override
	public String resum() {
		return "";
	}

}
