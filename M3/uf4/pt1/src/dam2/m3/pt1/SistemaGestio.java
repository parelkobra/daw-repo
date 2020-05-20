package dam2.m3.pt1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SistemaGestio implements Printable {
	public static final int MAX_USERS = 10;
	public static final int MAX_TICKETS = 10;

	private Usuari[] usuaris;
	private Tiquet[] incidencies;

	/**
	 * Inicialitza les estructures de dades i carrega dades de prova pel sistema
	 * d'incidències (mètode init())
	 *
	 * @throws ParseException - Si alguna de les dates de les dades de prova no té
	 *                        el format correcte
	 */
	public SistemaGestio() throws ParseException {
		usuaris = new Usuari[MAX_USERS];
		incidencies = new Tiquet[MAX_TICKETS];
		this.init();
	}

	/**
	 * Si l'usuari no és null i el vector d'usuaris no està ple Afegeix el l'usuari
	 * al vector d'usuaris
	 *
	 * @param tiquet
	 */
	public void nouUsuari(Usuari usuari) {
		if (usuari != null && usuaris[MAX_USERS - 1] == null) {
			int position = 0;
			while (position < MAX_USERS - 1) {
				if (usuaris[position] == null) {
					usuaris[position] = usuari;
					return;
				}
				position++;
			}
		}
	}

	/**
	 * Si el tiquet no és null i el vector d'incidències no està ple Afegeix el
	 * tiquet al vector d'incidències
	 *
	 * @param tiquet
	 */
	public void nouTiquet(Tiquet tiquet) {
		if (tiquet != null && usuaris[MAX_TICKETS - 1] == null) {
			int position = 0;
			while (position < MAX_TICKETS - 1) {
				if (incidencies[position] == null) {
					incidencies[position] = tiquet;
					return;
				}
				position++;
			}
		}
	}

	/**
	 * Retorna un text que conté l’informe de tots els tiquets registrats. L’informe
	 * inclou una capçalera, la informació de cada tiquet i un peu en aquest mateix
	 * ordre.
	 *
	 * @see Tiquet#header()
	 * @see Tiquet#footer(int, int, int, int)
	 * @see Tiquet#informe()
	 *
	 * @return informa d'incidències
	 */
	@Override
	public String informe() {
		String output = "";
		output += Tiquet.header();
		for (Tiquet tiquet : incidencies) {
			if (tiquet != null) {
				output += tiquet.informe();
			}
		}
		output += Tiquet.footer(getTotalByEstat(Tiquet.ESTAT_PENDENT), getTotalByEstat(Tiquet.ESTAT_ASSIGNAT),
				getTotalByEstat(Tiquet.ESTAT_PROCES), getTotalByEstat(Tiquet.ESTAT_TANCAT));
		return output;
	}

	/**
	 * Obté el nombre total de tiquets en un cert estat
	 *
	 * @param estat per comptar
	 * @return total de tiquets en aquest estat
	 */
	public int getTotalByEstat(String estat) {
		int total = 0;
		for (Tiquet tiquet : incidencies) {
			if (tiquet == null) {
				return total;
			}
			if (tiquet.getEstat() == estat) {
				total++;
			}
		}
		return total;
	}

	private void init() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("CA", "ES"));

		Empleat e1 = new Empleat("alex", "Alex Macia Perez", 3515, "Sala SATURN", "L15");
		Empleat e2 = new Empleat("maria", "Maria Lopez i Castells", 3513, "Sala SATURN", "L13");
		Empleat e3 = new Empleat("lola", "Lola Valls i Vilalta", 2501, "Sala VENUS", "L01");
		Empleat e4 = new Empleat("toni", "Antoni Bosc i Cases", 2508, "Sala VENUS", "L08");

		Tecnic tec1 = new Tecnic("raul", "Raul Casanova i Ferrer", 1504, "T-SYSTEMS");
		Tecnic tec2 = new Tecnic("genis", "Genís Esteve i Prats", 1515, "INET");

		Supervisor s1 = new Supervisor("laia", "Laia Vives i Marsans", 1501, "INET");

		this.nouUsuari(e1);
		this.nouUsuari(e2);
		this.nouUsuari(e3);
		this.nouUsuari(e4);

		this.nouUsuari(tec1);
		this.nouUsuari(tec2);

		this.nouUsuari(s1);

		Tiquet t1 = new Tiquet(e1, "Maquinari",
				"El ratolí no funciona bé, a vegades desapareix o no es mou. "
						+ "A més sovint he de posar un paper a sota. Ho podeu revisar. Gràcies",
				sdf.parse("01/10/2019"));

		t1.assignacio(s1, sdf.parse("02/10/2019"), tec1, 4);
		t1.intervencio(tec1, sdf.parse("04/10/2019"), 2, "És un ratolí inalàmbric, les piles estaven gastades. "
				+ "S'han substituït però segueix sense funcionar bé del tot. Cal canviar el ratolí.");
		t1.intervencio(tec2, sdf.parse("05/10/2019"), 1, "Ratolí inalàmbric canviat per model USB.");
		t1.tancament(tec1, sdf.parse("05/10/2019"));

		Tiquet t2 = new Tiquet(e3, "Connectivitat",
				"Internet funciona molt lent, fent servir el navegador Firefox "
						+ "les pàgines triguen molt a carregar-se i s'obren finestres emergents de propaganda. "
						+ "He provat d'instal·lar Chrome però em demana la contrasenya d'administrador. "
						+ "Necessito una solució urgentment!!!",
				sdf.parse("24/09/2019"));

		t2.assignacio(s1, sdf.parse("26/09/2019"), tec2, 9);
		t2.intervencio(tec2, sdf.parse("26/09/2019"), 4, "Google Chrome instal·lat. Firefox actualitzat i restablert "
				+ "a la configuració de fàbrica, l'ordinador està ple de virus. S'ha actualitzat l'antivirus, un escaneig complet "
				+ "en profunditat i programació d'una tasca per automatitzar un escaneig dos cops per setmana (dilluns i dijous al migdia)");

		Tiquet t3 = new Tiquet(e2, "Suport", "Estic intentant fer el desglossament de l'IVA pels apunts importats al "
				+ "programa de comptabilitat però no me'n surto. Necessito un cop de ma perquè la propera setmana "
				+ "ve l'auditor. Gràcies", sdf.parse("24/09/2019"));

		Tiquet t4 = new Tiquet(e4, "Impressió",
				"No puc imprimir a doble cara a la impressora de la Sala VENUS. "
						+ "Tot i que marco l'opció abans d'enviar a imprimir documents,no surten a doble cara ",
				sdf.parse("27/09/2019"));
		t4.assignacio(s1, sdf.parse("29/09/2019"), tec1, 3);

		this.nouTiquet(t1);
		this.nouTiquet(t2);
		this.nouTiquet(t3);
		this.nouTiquet(t4);
	}

	public static void main(String[] args) throws ParseException {
		SistemaGestio sistema = new SistemaGestio();
		System.out.println(sistema.informe());
	}
}
