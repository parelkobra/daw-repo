package dam2.m3.pt1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class Tiquet implements Printable {

	public static final int MAX_WIDTH = 98;
	public static final int COL_WIDTH_1 = 16;
	public static final int COL_WIDTH_2 = 14;
	public static final int COL_WIDTH_3 = 37;
	public static final int COL_WIDTH_4 = 12;
	public static final int COL_WIDTH_5 = 13;
	public static final String DATE_FORMAT_1 = "dd/MM/yyyy";
	public static final String DATE_FORMAT_2 = "EEEE, d MMMM 'de' yyyy";
	private static final String ERROR_FULL_ARRAY = "ERROR: Vector de seguiment plé";
	private static final String ERROR_ASSIGNACIO = "No s'ha pogut registrar la assignació.";
	private static final String ERROR_INTERVENCIO = "No s'ha pogut registrar la intervenció.";
	private static final String ERROR_TANCAMENT = "No s'ha pogut registrar el tancament.";
	public static final String ESTAT_TANCAT = "Tancat";
	public static final String ESTAT_PROCES = "En procés";
	public static final String ESTAT_ASSIGNAT = "Assignat";
	public static final String ESTAT_PENDENT = "Pendent";
	public static final String EXCEPTION_NULL_EMPLEAT = "L'empleat no pot ser nul";
	public static final String[] CATEGORIES = { "Maquinari", "Programari", "Impressió", "Connectivitat", "Suport",
			"Altres" };
	private static final int MAX_ACCIONS = 10;
	private Empleat empleat;
	private String categoria;
	private String descripcio;
	private Accio[] seguiment;
	private int position;

	public Tiquet(Empleat empleat, String categoria, String descripcio) throws IllegalArgumentException {
		if (empleat == null) {
			throw new IllegalArgumentException(EXCEPTION_NULL_EMPLEAT);
		}
		this.empleat = empleat;
		setCategoria(categoria);
		setDescripcio(descripcio);
		this.position = 0;
		this.seguiment = new Accio[MAX_ACCIONS];
		this.seguiment[position] = new Obertura(this.empleat, this, new Date());
		this.position++;
	}

	public Tiquet(Empleat empleat, String categoria, String descripcio, Date data) throws IllegalArgumentException {
		if (empleat == null) {
			throw new IllegalArgumentException(EXCEPTION_NULL_EMPLEAT);
		}
		this.empleat = empleat;
		setCategoria(categoria);
		setDescripcio(descripcio);
		this.position = 0;
		this.seguiment = new Accio[MAX_ACCIONS];
		this.seguiment[position] = new Obertura(this.empleat, this, data);
		this.position++;
	}

	public Empleat getEmpleat() {
		return empleat;
	}

	public String getCategoria() {
		return categoria;
	}

	/**
	 * If the category does not correspond to any of the predefined categories it
	 * will be assigned to 'Altres'
	 */
	public void setCategoria(String categoria) {
		int pos = 0;
		while (pos < CATEGORIES.length && categoria != CATEGORIES[pos]) {
			pos++;
		}
		if (categoria == CATEGORIES[pos]) {
			this.categoria = categoria;
		} else {
			this.categoria = CATEGORIES[5];
		}
	}

	public String getDescripcio() {
		return descripcio;
	}

	/**
	 * If the description of a ticket is null or it is an empty text, it will
	 * directly add a 'Tancament' record after the 'Obertura'
	 */
	public void setDescripcio(String descripcio) {
		if (descripcio == null || descripcio == "") {
			this.seguiment[position] = new Tancament(this.empleat, this, new Date());
			this.position++;
		} else {
			this.descripcio = descripcio;
		}
	}

	public Accio[] getSeguiment() {
		return seguiment;
	}

	public void setSeguiment(Accio[] seguiment) {
		this.seguiment = seguiment;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Checks the current ticket status based on the acctions on record until the
	 * current moment returning any of this options: "Tancat", "En prrocés",
	 * "Assignat" o "Pendent"
	 */
	public String getEstat() {
		if (tancat() == true) {
			return ESTAT_TANCAT;
		}
		if (tancat() == false && intervencions() == true) {
			return ESTAT_PROCES;
		}
		if (tancat() == false && intervencions() == false && assignat() == true) {
			return ESTAT_ASSIGNAT;
		}
		if (tancat() == false && assignat() == false) {
			return ESTAT_PENDENT;
		}
		return ESTAT_PENDENT;
	}

	public int getPrioritat() {
		int pos = 0;
		int prioritat = 0;
		Date dateInicial = new Date();
		Date dateFinal;

		if (!assignat()) {
			return prioritat;
		}
		while (!(seguiment[pos] instanceof Tancament) && seguiment[pos + 1] != null && pos < MAX_ACCIONS - 1) {
			if (seguiment[pos] instanceof Assignacio) {
				prioritat = seguiment[pos].getPrioritat();
				dateInicial = seguiment[pos].data;
			}
			pos++;
		}
		if (seguiment[pos + 1] == null || pos == MAX_ACCIONS - 1) {
			if (seguiment[pos] instanceof Assignacio) {
				return seguiment[pos].getPrioritat();
			}
			dateFinal = seguiment[pos].data;
			long diff = dateFinal.getTime() - dateInicial.getTime();
			long daysPassed = diff / (1000 * 60 * 60 * 24);
			for (int i = 0; i < daysPassed; i++) {
				if (i != 0 && i % 2 == 0) {
					prioritat++;
				}
			}
		}
		return prioritat;
	}

	/**
	 * Does the assignment of the ticket by the supervisor to a technician
	 * indicating a priority between 1 and 9. Consists of creating a new instance of
	 * the acction "Assignacio" to add it to the ticket tracing
	 */
	public String assignacio(Supervisor supervisor, Date data, Tecnic tecnic, int prioritat) {
		int position = getPosition();
		if (supervisor != null && tecnic != null && !assignat() && !tancat() && data.after(seguiment[0].data)) {
			Accio assignacio = new Assignacio(supervisor, this, data, tecnic, prioritat);
			seguiment[position] = assignacio;
			this.position++;
			return assignacio.resum();
		} else if (position + 1 == MAX_ACCIONS) {
			return ERROR_FULL_ARRAY;
		} else {
			return ERROR_ASSIGNACIO;
		}
	}

	/**
	 * Add's the intervention to a technician during one or more hours and a brief
	 * description of the tasks done. Consists of creating a new instance of the
	 * action "Intervencio" to add it to the ticket tracing
	 */
	public String intervencio(Tecnic tecnic, Date data, int hores, String descripcio) {
		int position = getPosition();
		if (tecnic != null && !tancat() && assignat()
				&& (data.after(seguiment[1].data) || data.equals(seguiment[1].data))) {
			Accio intervencio = new Intervencio(tecnic, this, data, hores, descripcio);
			seguiment[position] = intervencio;
			this.position++;
			return intervencio.resum();
		} else if (position + 1 == MAX_ACCIONS) {
			return ERROR_FULL_ARRAY;
		} else {
			return ERROR_INTERVENCIO;
		}
	}

	/**
	 * Ends the ticket and sets it as resolved, adding a new instance of the action
	 * "Tancament" to the ticket tracing
	 */
	public String tancament(Usuari usuari, Date data) {
		int position = getPosition();
		if (usuari != null && !tancat()
				&& (data.after(seguiment[position - 1].data) || data.equals(seguiment[position - 1].data))
				&& (usuari == seguiment[0].usuari || usuari instanceof Supervisor || intervencions(usuari))) {
			Accio tancament = new Tancament(usuari, this, data);
			seguiment[position] = tancament;
			this.position++;
			return tancament.resum();
		} else if (position + 1 == MAX_ACCIONS) {
			return ERROR_FULL_ARRAY;
		} else {
			return ERROR_TANCAMENT;
		}
	}

	/**
	 * Indicates whether the ticket is assigned to a technician. Looks up on the 
	 * ticket tracing for any instance of "Assignacio"
	 */
	public boolean assignat() {
		for (Accio accio : seguiment) {
			if (accio instanceof Assignacio) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicates whether any intervention has been made on the ticket by any 
	 * technician. Looks up on the ticket tracing for any instance of "Intervencio"
	 */
	public boolean intervencions() {
		for (Accio accio : seguiment) {
			if (accio instanceof Intervencio) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicates whether any intervention has been made on the ticket by the user
	 * indicated as parameter. This user must be a technician 
	 */
	public boolean intervencions(Usuari usuari) {
		for (Accio accio : seguiment) {
			if (accio instanceof Intervencio && accio.usuari == usuari) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicates whether the ticket is closed or not. Looks up on the ticket 
	 * tracing for any instance of "Tancament"
	 */
	public boolean tancat() {
		for (Accio accio : seguiment) {
			if (accio instanceof Tancament) {
				return true;
			}
		}
		return false;
	}

	private String formatExtension(int extensio) {
		String temp = Integer.toString(extensio);
		char[] array = new char[temp.length() + 1];
		int aux = 0;
		for (int i = 0; i < array.length; i++) {
			if (i != 2) {
				array[i] = temp.charAt(aux);
				aux++;
			} else {
				array[aux] = ' ';
			}
		}
		return new String(array);
	}

	@Override
	public String informe() {
		String output = "";
		String colDec1 = "·" + StringUtils.leftPad("·", COL_WIDTH_1 + 1);
		String colDec2 = StringUtils.leftPad("·", COL_WIDTH_2 + 1);
		String colDec3 = StringUtils.leftPad("·", COL_WIDTH_3 + 1);
		String colDec4 = StringUtils.leftPad("·", COL_WIDTH_4 + 1);
		String colDec5 = StringUtils.leftPad("·", COL_WIDTH_5 + 1);
		String dec1 = StringUtils.repeat("·", MAX_WIDTH) + System.lineSeparator();
		String dec2 = colDec1 + colDec2 + colDec3 + colDec4 + colDec5 + System.lineSeparator();
		String dec3 = StringUtils.repeat("·", COL_WIDTH_2 + COL_WIDTH_3 + COL_WIDTH_4 + COL_WIDTH_5 + 4)
				+ System.lineSeparator();
		String dec4 = StringUtils.leftPad("·", COL_WIDTH_2 + COL_WIDTH_3 + COL_WIDTH_4 + COL_WIDTH_5 + 4)
				+ System.lineSeparator();

		// DADES D'OBERTURA
		String dataObertura = new SimpleDateFormat(DATE_FORMAT_1).format(seguiment[0].data);
		String empleatObertura1 = this.empleat.nom + " (ext. " + formatExtension(this.empleat.extensio) + ")";
		String empleatObertura2 = this.empleat.getUbicacio() + ", " + this.empleat.getLloc();

		// CATEGORIA TIQUET I DADES D'OBERTURA
		output += dec1 + dec2;
		output += "·" + StringUtils.center(categoria.toUpperCase(), COL_WIDTH_1) + "·";
		output += StringUtils.center(dataObertura, COL_WIDTH_2) + "·";
		output += StringUtils.rightPad(" " + empleatObertura1, COL_WIDTH_3) + "·";
		output += StringUtils.center(Integer.toString(getPrioritat()), COL_WIDTH_4) + "·";
		output += StringUtils.leftPad(getEstat() + " ", COL_WIDTH_5) + "·";
		output += System.lineSeparator();
		output += colDec1 + colDec2;
		output += StringUtils.rightPad(" " + empleatObertura2, COL_WIDTH_3) + "·" + colDec4 + colDec5
				+ System.lineSeparator();
		output += dec2 + colDec1 + dec3;

		// DESCRIPCIO TIQUET
		output += colDec1 + dec4 + colDec1;
		output += StringUtils.rightPad(this.descripcio, COL_WIDTH_2 + COL_WIDTH_3 + COL_WIDTH_4 + COL_WIDTH_5 + 3) + "·"
				+ System.lineSeparator();
		output += colDec1 + dec4;

		// HEADER: ACTUACIONS
		output += colDec1 + dec3;
		output += "·" + StringUtils.leftPad("·", COL_WIDTH_1 + 1)
				+ StringUtils.rightPad(" ACTUACIONS", COL_WIDTH_2 + COL_WIDTH_3 + COL_WIDTH_4 + COL_WIDTH_5 + 3) + "·"
				+ System.lineSeparator();

		// ACTUACIONS
		for (Accio accio : seguiment) {
			if (accio != null && !(accio instanceof Obertura)) {
				String date = new SimpleDateFormat(DATE_FORMAT_1).format(accio.data);
				String name = " " + accio.usuari.nom;
				String resum = " " + accio.resum();

				output += colDec1 + dec3;
				output += colDec1 + StringUtils.center(date, COL_WIDTH_2) + "·"
						+ StringUtils.rightPad(name, COL_WIDTH_3 + COL_WIDTH_4 + COL_WIDTH_5 + 2) + "·";
				output += System.lineSeparator();
				output += colDec1 + colDec2 + StringUtils.repeat("·", COL_WIDTH_3 + COL_WIDTH_4 + COL_WIDTH_5 + 3);
				output += System.lineSeparator();
				output += colDec1 + colDec2 + StringUtils.rightPad(resum, COL_WIDTH_3 + COL_WIDTH_4 + COL_WIDTH_5 + 2)
						+ "·";
				output += System.lineSeparator();
			}
		}

		return output;
	}

	public static String header() {
		String output = "";
		String date = new SimpleDateFormat(DATE_FORMAT_2, new Locale("CA")).format(new Date());

		String dec1 = StringUtils.repeat("·", MAX_WIDTH) + System.lineSeparator();
		String dec2 = "·" + StringUtils.leftPad("·", MAX_WIDTH - 1) + System.lineSeparator();
		String elem1 = "·" + StringUtils.center("INFORME INCIDÈNCIES. " + date, MAX_WIDTH - 2) + "·"
				+ System.lineSeparator();
		String elem2 = "·" + StringUtils.center("CATEGORIA", COL_WIDTH_1) + " "
				+ StringUtils.center("DATA", COL_WIDTH_2) + "  " + StringUtils.rightPad("EMPLEAT", COL_WIDTH_3 - 1)
				+ " " + StringUtils.center("PRIORITAT", COL_WIDTH_4) + " "
				+ StringUtils.leftPad("ESTAT", COL_WIDTH_5 - 1) + " ·" + System.lineSeparator();

		output += dec1 + dec2 + elem1 + dec2 + dec1 + dec2 + elem2;

		return output;
	}

	public static String footer(int pendents, int assignats, int intervinguts, int tancats) {
		String output = "";
		int total = pendents + assignats + intervinguts + tancats;
		final int footerColWidth = COL_WIDTH_1 + COL_WIDTH_2 + COL_WIDTH_3 + COL_WIDTH_4 + 3;
		String dec1 = StringUtils.repeat("·", MAX_WIDTH) + System.lineSeparator();
		String dec2 = "·" + StringUtils.leftPad("·", footerColWidth + 1) + StringUtils.leftPad("·", COL_WIDTH_5 + 1)
				+ System.lineSeparator();
		String dec3 = "·" + StringUtils.leftPad("·", footerColWidth + COL_WIDTH_5 + 2) + System.lineSeparator();
		String elem1 = "·" + StringUtils.leftPad("Pendents ", footerColWidth) + "·"
				+ StringUtils.center(Integer.toString(pendents), COL_WIDTH_5) + "·" + System.lineSeparator();
		String elem2 = "·" + StringUtils.leftPad("Assignats ", footerColWidth) + "·"
				+ StringUtils.center(Integer.toString(assignats), COL_WIDTH_5) + "·" + System.lineSeparator();
		String elem3 = "·" + StringUtils.leftPad("En procés ", footerColWidth) + "·"
				+ StringUtils.center(Integer.toString(intervinguts), COL_WIDTH_5) + "·" + System.lineSeparator();
		String elem4 = "·" + StringUtils.leftPad("Tancats ", footerColWidth) + "·"
				+ StringUtils.center(Integer.toString(tancats), COL_WIDTH_5) + "·" + System.lineSeparator();
		String elem5 = "·" + StringUtils.leftPad("TOTAL ", footerColWidth) + "·"
				+ StringUtils.center(Integer.toString(total), COL_WIDTH_5) + "·" + System.lineSeparator();

		output += dec1 + dec3 + dec1 + elem1 + dec1 + elem2 + dec1 + elem3 + dec1 + elem4 + dec1 + dec2 + elem5 + dec2
				+ dec1;

		return output;
	}

}
