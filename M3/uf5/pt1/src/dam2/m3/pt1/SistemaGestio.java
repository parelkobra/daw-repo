package dam2.m3.pt1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SistemaGestio {
    private Set<Usuari> usuaris;
    private Queue<Tiquet> incidencies;
    private List<Tiquet> tasques;
    private SortedSet<Tiquet> historic;

    /**
     * Inicialitza les estructures de dades i carrega dades de prova pel sistema
     * d'incidències (mètode init())
     *
     * @throws ParseException - Si alguna de les dates de les dades de prova no
     *                        té el format correcte
     */
    public SistemaGestio() throws ParseException {
	this.usuaris = new HashSet<>();
	this.incidencies = new LinkedList<>();
	this.tasques = new LinkedList<>();
	this.historic = new TreeSet<>();
	this.init();
    }

    /**
     * Si l'usuari no és null i el vector d'usuaris no està ple Afegeix el
     * l'usuari al vector d'usuaris
     *
     * @param tiquet
     * @throws Exception
     */
    public void nouUsuari(Usuari usuari) throws Exception {
	if (usuari == null) throw new Exception("L'usuari no pot ser nul");
	if (usuaris.contains(usuari))
	    throw new Exception("No es pot afegir l'usuari " + usuari.getNom() + " perqué ja existeix");
	this.usuaris.add(usuari);
    }

    /**
     * Si el tiquet no és null i el vector d'incidències no està ple Afegeix el
     * tiquet al vector d'incidències
     *
     * @param tiquet
     * @throws Exception
     */
    public void nouTiquet(Tiquet tiquet) throws Exception {
	if (tiquet == null) throw new Exception("No es pot afegir un nou tiquet nul");
	this.incidencies.add(tiquet);
    }

    /**
     * Retorna un text que conté l’informe de tots els tiquets registrats.
     * L’informe inclou una capçalera, la informació de cada tiquet i un peu en
     * aquest mateix ordre.
     *
     * @see Tiquet#header()
     * @see Tiquet#footer(int, int, int, int)
     * @see Tiquet#informe()
     *
     * @return informa d'incidències
     */
    public String informeIncidencies() {
	String informe = "";
	informe += Tiquet.header("INFORME INCIDÈNCIES");
	for (Tiquet tiquet : incidencies) {
	    if (tiquet != null) {
		informe += tiquet.informe();
	    }
	}
	informe += Tiquet.footer(getTotalByEstat(Tiquet.PENDENT), getTotalByEstat(Tiquet.ASSIGNAT),
		getTotalByEstat(Tiquet.EN_PROCES), getTotalByEstat(Tiquet.TANCAT));
	return informe;
    }

    public String informeTasques() {
	String informe = "";
	informe += Tiquet.header("LLISTAT TASQUES");
	for (Tiquet tiquet : tasques) {
	    if (tiquet != null) {
		informe += tiquet.informe();
	    }
	}
	informe += Tiquet.footer(getTotalByEstat(Tiquet.PENDENT), getTotalByEstat(Tiquet.ASSIGNAT),
		getTotalByEstat(Tiquet.EN_PROCES), getTotalByEstat(Tiquet.TANCAT));
	return informe;

    }

    public String informeHistoric() {
	String informe = "";
	informe += Tiquet.header("HISTÒRIC ACTUACIONS");
	for (Tiquet tiquet : historic) {
	    if (tiquet != null) {
		informe += tiquet.informe();
	    }
	}
	informe += Tiquet.footer(getTotalByEstat(Tiquet.PENDENT), getTotalByEstat(Tiquet.ASSIGNAT),
		getTotalByEstat(Tiquet.EN_PROCES), getTotalByEstat(Tiquet.TANCAT));
	return informe;

    }

    /**
     * Obté el total de tiquets en un cert estat
     *
     * @param estat
     * @return
     */
    public int getTotalByEstat(String estat) {
	if (estat == null) return 0;
	int total = 0;

	for (Tiquet tiquet : incidencies) {
	    if (tiquet != null && estat.equals(tiquet.getEstat())) total++;
	}
	return total;
    }

    public void esborrarUsuari(String usuari) throws Exception {
	boolean userRemoved = false;
	for (Usuari user : usuaris) {
	    if (usuari.equals(user.getUsuari())) {
		userRemoved = usuaris.remove(user);
		break;
	    }
	}
	if (!userRemoved) throw new Exception("No s'ha trobat l'usuari per esborrar");
    }

    public void novaTasca(Supervisor supervisor, Date data, Tecnic tecnic, int prioritat) throws Exception {
	if (incidencies.isEmpty()) throw new Exception("La cua d'incidencies està buida");
	Tiquet tiquet = incidencies.poll();
	if (tiquet != null) {
	    tiquet.assignacio(supervisor, data, tecnic, prioritat);
	    tasques.add(tiquet);
	}
    }

    public void novaIntervencio(Empleat empleat, Date obertura, Tecnic tecnic, Date intervencio, int hores,
	    String descripcio) throws Exception {
	Tiquet tiquet = cercarTasca(empleat, obertura);
	if (tiquet == null) throw new Exception("Tiquet per nova intervenció no trobat");
	tiquet.intervencio(tecnic, intervencio, hores, descripcio);
    }

    public void tancarTiquet(Empleat empleat, Date obertura, Usuari usuari, Date tancament) throws Exception {
	Tiquet tiquet = cercarTasca(empleat, obertura);
	if (tiquet == null) throw new Exception("Tiquet per tancar tiquet no trobat");

	tiquet.tancament(usuari, tancament);
	if (tiquet.tancat()) {
	    tasques.remove(tiquet);
	    historic.add(tiquet);
	}

    }

    private Tiquet cercarTasca(Empleat empleat, Date obertura) {
	for (Tiquet tiquet : tasques) {
	    if (empleat.equals(tiquet.empleatObertura()) && obertura.equals(tiquet.dataObertura())) {
		return tiquet;
	    }
	}
	return null;
    }

    public void init() {
	try {
	    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	    Logger rootLogger = Logger.getLogger("");
	    Handler[] handlers = rootLogger.getHandlers();
	    if (handlers[0] instanceof ConsoleHandler) {
		rootLogger.removeHandler(handlers[0]);
	    }

	    FileHandler fileTxt = new FileHandler("Logging.log");
	    SimpleFormatter formatterTxt = new SimpleFormatter();
	    fileTxt.setFormatter(formatterTxt);
	    logger.addHandler(fileTxt);

	    logger.setLevel(Level.INFO);

	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("CA", "ES"));
	    Empleat e1 = new Empleat("alex", "Alex Macia Perez", 3515, "Sala SATURN", "L15");
	    Empleat e2 = new Empleat("maria", "Maria Lopez i Castells", 3513, "Sala SATURN", "L13");
	    Empleat e3 = new Empleat("lola", "Lola Valls i Vilalta", 2501, "Sala VENUS", "L01");
	    Empleat e4 = new Empleat("toni", "Antoni Bosc i Cases", 2508, "Sala VENUS", "L08");
	    Empleat e5 = new Empleat("robert", "Robert Planes i Pujol", 3510, "Sala SATURN", "L10");

	    Tecnic tec1 = new Tecnic("raul", "Raul Casanova i Ferrer", 1504, "T-SYSTEMS");
	    Tecnic tec2 = new Tecnic("genis", "Genís Esteve i Prats", 1515, "INET");

	    Supervisor s1 = new Supervisor("laia", "Laia Vives i Marsans", 1501, "INET");

	    // Situació d'error 1 (Usuari nul)
	    try {
		this.nouUsuari(null);
	    } catch (Exception e) {
		Logger.getGlobal().warning(e.getMessage());
	    }

	    this.nouUsuari(e1);
	    this.nouUsuari(e2);
	    this.nouUsuari(e3);
	    this.nouUsuari(e4);
	    this.nouUsuari(e5);

	    this.esborrarUsuari("robert");

	    this.nouUsuari(tec1);
	    this.nouUsuari(tec2);

	    this.nouUsuari(s1);

	    Tiquet t1 = new Tiquet(e1, "Maquinari",
		    "El ratolí no funciona bé, a vegades desapareix o no es mou. "
			    + "A més sovint he de posar un paper a sota. Ho podeu revisar. Gràcies",
		    sdf.parse("01/10/2019 08:15:31"));

	    Tiquet t2 = new Tiquet(e3, "Connectivitat",
		    "Internet funciona molt lent, fent servir el navegador Firefox "
			    + "les pàgines triguen molt a carregar-se i s'obren finestres emergents de propaganda. "
			    + "He provat d'instal·lar Chrome però em demana la contrasenya d'administrador. "
			    + "Necessito una solució urgentment!!!",
		    sdf.parse("24/09/2019 07:54:13"));

	    Tiquet t3 = new Tiquet(e2, "Suport",
		    "Estic intentant fer el desglossament de l'IVA pels apunts importats al "
			    + "programa de comptabilitat però no me'n surto. Necessito un cop de ma perquè la propera setmana "
			    + "ve l'auditor. Gràcies",
		    sdf.parse("24/09/2019 17:32:04"));

	    Tiquet t4 = new Tiquet(e4, "Impressió",
		    "No puc imprimir a doble cara a la impressora de la Sala VENUS. "
			    + "Tot i que marco l'opció abans d'enviar a imprimir documents,no surten a doble cara ",
		    sdf.parse("27/09/2019 11:27:50"));

	    this.nouTiquet(t1);
	    this.nouTiquet(t2);
	    this.nouTiquet(t3);
	    this.nouTiquet(t4);

	    // Situació d'error 2 (Tiquet nul)
	    try {
		this.nouTiquet(null);
	    } catch (Exception e) {
		Logger.getGlobal().warning(e.getMessage());
	    }

	    // Situació d'error 3 (Data anteriror a l'obertura)
	    //
	    // this.novaTasca(s1, sdf.parse("02/10/2019 09:21:15"), tec1, 4);
	    try {
		this.novaTasca(s1, sdf.parse("01/08/2019 09:21:15"), tec1, 4);
	    } catch (Exception e) {
		Logger.getGlobal().warning(e.getMessage());
	    }

	    try {
		this.novaIntervencio(e1, sdf.parse("01/10/2019 08:15:31"), tec1, sdf.parse("04/10/2019 11:25:11"), 2,
			"És un ratolí inalàmbric, les piles estaven gastades. "
				+ "S'han substituït però segueix sense funcionar bé del tot. Cal canviar el ratolí.");
	    } catch (Exception e) {
		Logger.getGlobal().warning(e.getMessage());
	    }

	    try {
		this.novaIntervencio(e1, sdf.parse("01/10/2019 08:15:31"), tec2, sdf.parse("05/10/2019 13:44:37"), 1,
			"Ratolí inalàmbric canviat per model USB.");
	    } catch (Exception e) {
		Logger.getGlobal().warning(e.getMessage());
	    }

	    // Situació d'error 4 (Tancar tiquet amb un usuari sense
	    // autorització)
	    try {
		this.tancarTiquet(e1, sdf.parse("01/10/2019 08:15:31"), e5, sdf.parse("05/10/2019 17:02:00"));
	    } catch (Exception e) {
		Logger.getGlobal().warning(e.getMessage());
	    }

	    // this.tancarTiquet(e1, sdf.parse("01/10/2019 08:15:31"), tec1,
	    // sdf.parse("05/10/2019 17:02:00"));
	    this.novaTasca(s1, sdf.parse("26/09/2019 11:39:14"), tec2, 9);

	    this.novaIntervencio(e3, sdf.parse("24/09/2019 07:54:13"), tec2, sdf.parse("26/09/2019 13:15:54"), 4,
		    "Google Chrome instal·lat. Firefox actualitzat i restablert "
			    + "a la configuració de fàbrica, l'ordinador està ple de virus. S'ha actualitzat l'antivirus, un escaneig complet "
			    + "en profunditat i programació d'una tasca per automatitzar un escaneig dos cops per setmana (dilluns i dijous al migdia)");

	    this.novaTasca(s1, sdf.parse("29/09/2019 12:32:04"), tec1, 3);
	} catch (

	Exception e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) throws ParseException {
	SistemaGestio sistema = new SistemaGestio();
	// System.out.println(sistema.informeIncidencies());
	// System.out.println(sistema.informeTasques());
	// System.out.println(sistema.informeHistoric());
    }
}
