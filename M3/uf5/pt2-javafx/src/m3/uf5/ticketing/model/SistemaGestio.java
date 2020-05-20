package m3.uf5.ticketing.model;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.stage.Stage;
import m3.uf5.ticketing.ControllerMenu;

public class SistemaGestio {
    /*
     * public static final String PATH_HISTORIC = "data/historic.xml"; public static final String
     * TITOL_INFORME_OPERACIONS = "INFORME M15: REGISTRE OPERACIONS CAIXER"; public static final String
     * TITOL_JUSTIFICANT_OPERACIO = "DOCUMENT D02: JUSTIFICANT OPERACIÓ"; public static final String
     * TITOL_INFORME_CLIENTS = "INFORME C02: CLIENTS ENTITAT"; public static final String
     * TITOL_INFORME_MOVIMENTS_CLIENT = "INFORME C10: DARRERS MOVIMENTS CLIENT";
     *
     * public static final String BASE_DIR = "data/"; public static final String FILE_INFORME_OPERACIONS
     * = "InformeOperacions.pdf"; public static final String FILE_JUSTIFICANT_OPERACIO =
     * "JustificantOperacio.pdf"; public static final String FILE_INFORME_CLIENTS = "LlistaClients.pdf";
     * public static final String FILE_INFORME_MOVIMENTS_CLIENT = "LlistatMovimentsClient.pdf";
     */

    private HashSet<Usuari> usuaris;
    private LinkedList<Tiquet> incidencies;
    private LinkedList<Tiquet> tasques;
    private TreeSet<Tiquet> historic;

    private Usuari usuari;

    private static SistemaGestio instance = null;
    private Stage stage;
    private Application app;
    private ControllerMenu menuController;

    protected SistemaGestio() {
	this.usuaris = new HashSet<>();
	this.incidencies = new LinkedList<>();
	this.tasques = new LinkedList<>();
	this.historic = new TreeSet<>();
	this.init();
    }

    public static SistemaGestio getInstance() {
	if (instance == null) {
	    instance = new SistemaGestio();
	}
	return instance;
    }

    public Stage getStage() {
	return stage;
    }

    public void setStage(Stage stage) {
	this.stage = stage;
    }

    public Application getApp() {
	return app;
    }

    public void setApp(Application app) {
	this.app = app;
    }

    public Usuari getUsuari() {
	return usuari;
    }

    public void setUsuari(Usuari usuari) {
	this.usuari = usuari;
    }

    public ControllerMenu getMenuController() {
	return menuController;
    }

    public void setMenuController(ControllerMenu menuController) {
	this.menuController = menuController;
    }

    /**
     * Afegeix el l'usuari al conjunt d'usuaris
     *
     * @param usuari
     * @throws Exception si l'usuari és nul o ja existeix
     */
    public void nouUsuari(Usuari usuari) throws Exception {
	if (usuari == null) throw new Exception("L'usuari no pot ser nul");
	if (this.usuaris.contains(usuari)) throw new Exception("Usuari \"" + usuari.getUsuari() + "\" existent");
	this.usuaris.add(usuari);
    }

    /**
     * Si troba l'usuari amb l'atribut "usuari" indicat per paràmetre el treu del conjunt d'usuaris
     *
     * @param usuari
     * @throws Exception si no troba l'usuari al conjunt d'usuaris
     */
    public void esborrarUsuari(String usuari) throws Exception {
	Usuari esborrar = new Usuari(usuari, "NA", 0) {
	    private static final long serialVersionUID = -8031856591931400527L;
	}; // Implementació genèrica d'usuari abstracte
	if (usuari == null || !this.usuaris.contains(esborrar))
	    throw new Exception("Usuari \"" + usuari + "\" no trobat");

	this.usuaris.remove(esborrar);
    }

    public Usuari cercarUsuari(String usuari) throws Exception {
	Usuari cercar = new Usuari(usuari, "NA", 0) {
	    private static final long serialVersionUID = 618819102129880593L;
	};

	if (usuari == null) throw new Exception("Usuari incorrecte");

	for (Usuari usu : this.usuaris) {
	    if (usu.equals(cercar)) return usu;
	}

	throw new Exception("Usuari \"" + usuari + "\" no trobat");
    }

    public LinkedList<Usuari> getUsuarisByTipus(String tipus, String cerca) {
	LinkedList<Usuari> usuarisTipus = new LinkedList<>();

	if (tipus == null) {
	    tipus = "";
	}

	for (Usuari usuari : this.usuaris) {
	    if (("".equals(tipus) || (Usuari.TIPUS_USERS[0].equals(tipus) && usuari.esEmpleat())
		    || (Usuari.TIPUS_USERS[1].equals(tipus) && usuari.esTecnic())
		    || (Usuari.TIPUS_USERS[2].equals(tipus) && usuari.esSupervisor()))
		    && ("".equals(cerca) || (!"".equals(cerca) && usuari.getNom().toLowerCase(new Locale("CA", "ES"))
			    .contains(cerca.toLowerCase(new Locale("CA", "ES")))))) {

		usuarisTipus.add(usuari);
	    }
	}

	return usuarisTipus;
    }

    /**
     * Afegeix el tiquet a la cua d'incidències obertes
     *
     * @param tiquet
     * @throws Exception
     */
    public void nouTiquet(Tiquet tiquet) throws Exception {
	if (tiquet == null) throw new Exception("El tiquet no pot ser nul");

	this.incidencies.addLast(tiquet);
    }

    /**
     * Assignar el primer tiquet de la cua d'incidències al tècnic indicat per paràmetre i afegir-lo al
     * final de la llista de tasques. Aquest tiquet ha de sortir de la cua d'incidències
     *
     * @param supervisor
     * @param data
     * @param tecnic
     * @param prioritat
     * @throws Exception La cua d'incidències està buida
     */
    public void novaTasca(Supervisor supervisor, Date data, Tecnic tecnic, int prioritat) throws Exception {
	if (this.incidencies.isEmpty()) throw new Exception("No hi ha tiquets pendents d'assignar");

	Tiquet tiquet = this.incidencies.element();

	tiquet.assignacio(supervisor, data, tecnic, prioritat);

	this.incidencies.remove();
	this.tasques.addLast(tiquet);
    }

    /**
     * El tècnic fa una intervenció per al tiquet obert per empleat en data obertura de la llista de
     * tasques.
     *
     * La intervenció té una durada de les hores indicades i s'inclou la descripció de les tasques
     * realitzades
     *
     * @param empleat
     * @param obertura
     * @param tecnic
     * @param hores
     * @param descripcio
     * @throws Exception
     */
    public void novaIntervencio(Empleat empleat, Date obertura, Tecnic tecnic, Date intervencio, int hores,
	    String descripcio) throws Exception {
	Tiquet tasca = this.cercarTasca(empleat, obertura);

	tasca.intervencio(tecnic, intervencio, hores, descripcio);
    }

    /**
     * L'usuari tanca el tiquet en data tancament obert per empleat en data obertura de la llista de
     * tasques.
     *
     * El tiquet surt de la llista de tasques i entra a l'històric en estat tancat
     *
     * @param empleat
     * @param obertura
     * @param usuari
     * @param tancament
     * @throws Exception
     */
    public void tancarTiquet(Empleat empleat, Date obertura, Usuari usuari, Date tancament) throws Exception {
	Tiquet tancar = this.cercarTasca(empleat, obertura);

	tancar.tancament(usuari, tancament);

	this.tasques.remove(tancar);
	this.historic.add(tancar);
    }

    private Tiquet cercarTasca(Empleat empleat, Date obertura) throws Exception {
	Tiquet tancar = new Tiquet(empleat, "", "NA", obertura);

	if (!this.tasques.contains(tancar)) {

	    String strDia = "";
	    String strHora = "";
	    if (obertura != null) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("CA", "ES"));
		strDia = sdf.format(obertura);
		sdf.applyPattern("hh:mm");
		strHora = sdf.format(obertura);
	    }
	    throw new Exception("No s'ha trobat el tiquet obert per l'empleat " + empleat.getNom() + " el dia " + strDia
		    + " a les " + strHora);
	}

	return this.tasques.get(this.tasques.indexOf(tancar));
    }

    /**
     * Retorna un text que conté l’informe de tots els tiquets registrats. L’informe inclou una
     * capçalera, la informació de cada tiquet i un peu en aquest mateix ordre.
     *
     * @see Tiquet#header()
     * @see Tiquet#footer(int, int, int, int)
     * @see Tiquet#informe()
     *
     * @return informa d'incidències
     */
    public String informeIncidencies(LocalDate desde, LocalDate fins, Empleat empleat, String categoria) {
	return this.informe(this.incidencies, "INFORME INCIDÈNCIES.", desde, fins, empleat, categoria);
    }

    public String informeTasques(LocalDate desde, LocalDate fins, Empleat empleat, String categoria) {
	return this.informe(this.tasques, "LLISTAT TASQUES.", desde, fins, empleat, categoria);
    }

    public String informeHistoric(LocalDate desde, LocalDate fins) {
	return this.informe(this.historic, "HISTÒRIC ACTUACIONS.", desde, fins, null, "");
    }

    public List<Tiquet> getIncidencies(LocalDate desde, LocalDate fins, Empleat empleat, String categoria) {
	return this.filtreTiquets(this.incidencies, desde, fins, empleat, categoria);
    }

    public List<Tiquet> getTasquest(LocalDate desde, LocalDate fins, Empleat empleat, String categoria) {
	return this.filtreTiquets(this.tasques, desde, fins, empleat, categoria);
    }

    public List<Tiquet> getHistoric(LocalDate desde, LocalDate fins, Empleat empleat, String categoria) {
	return this.filtreTiquets(this.historic, desde, fins, empleat, categoria);
    }

    private List<Tiquet> filtreTiquets(Collection<Tiquet> tiquets, LocalDate desde, LocalDate fins, Empleat empleat,
	    String categoria) {
	LinkedList<Tiquet> resultats = new LinkedList<>();
	if (categoria == null) {
	    categoria = "";
	}
	for (Tiquet tiquet : tiquets) {
	    if (tiquet != null) {
		LocalDate dataTiquet = tiquet.dataObertura().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if ((desde == null || (desde != null && (dataTiquet.isAfter(desde) || dataTiquet.equals(desde))))
			&& (fins == null || (fins != null && (dataTiquet.isBefore(fins) || dataTiquet.equals(fins))))
			&& (empleat == null || (empleat != null && tiquet.getEmpleat().equals(empleat)))
			&& ("".equals(categoria)
				|| (!"".equals(categoria) && tiquet.getCategoria().equals(categoria)))) {
		    resultats.add(tiquet);
		}
	    }
	}

	return resultats;
    }

    private String informe(Collection<Tiquet> tiquets, String title, LocalDate desde, LocalDate fins, Empleat empleat,
	    String categoria) {

	List<Tiquet> resultats = this.filtreTiquets(tiquets, desde, fins, empleat, categoria);

	String informe = "";
	informe += Tiquet.header(title);
	for (Tiquet tiquet : resultats) {
	    if (tiquet != null) {
		LocalDate dataTiquet = tiquet.dataObertura().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if ((desde == null || (desde != null && (dataTiquet.isAfter(desde) || dataTiquet.equals(desde))))
			&& (fins == null || (fins != null && (dataTiquet.isBefore(fins) || dataTiquet.equals(fins))))) {
		    informe += tiquet.informe();
		}
	    }
	}
	informe += Tiquet.footer(getTotalByEstat(Tiquet.PENDENT, resultats),
		getTotalByEstat(Tiquet.ASSIGNAT, resultats), getTotalByEstat(Tiquet.EN_PROCES, resultats),
		getTotalByEstat(Tiquet.TANCAT, resultats));

	return informe;
    }

    /**
     * Obté el total de tiquets en un cert estat
     *
     * @param estat
     * @return
     */
    public int getTotalByEstat(String estat, Collection<Tiquet> tiquets) {
	if (estat == null) return 0;
	int total = 0;

	for (Tiquet tiquet : tiquets) {
	    if (tiquet != null && estat.equals(tiquet.getEstat())) {
		total++;
	    }
	}
	return total;
    }

    public void backup(File selectedFile) throws Exception {
	ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(selectedFile));
	fileOut.writeObject(usuaris);
	fileOut.close();
    }

    @SuppressWarnings("unchecked")
    public void restore(File selectedFile) throws Exception {
	HashSet<Usuari> fileUsuaris = null;
	ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream(selectedFile));
	fileUsuaris = (HashSet<Usuari>) fileIn.readObject();
	fileIn.close();

	this.usuaris.clear();
	this.usuaris = fileUsuaris;
    }

    public void export(File selectedFile) throws Exception {
	XMLEncoder encoder;
	encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(selectedFile)));
	encoder.writeObject(incidencies);
	encoder.close();
    }

    public void init() {
	try {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("CA", "ES"));
	    Empleat e1 = new Empleat("alex", "Alex Macia Perez", 3515, "Sala SATURN", "L15");
	    Empleat e2 = new Empleat("maria", "Maria Lopez i Castells", 3513, "Sala SATURN", "L13");
	    Empleat e3 = new Empleat("lola", "Lola Valls i Vilalta", 2501, "Sala VENUS", "L01");
	    Empleat e4 = new Empleat("toni", "Antoni Bosc i Cases", 2508, "Sala VENUS", "L08");
	    Empleat e5 = new Empleat("robert", "Robert Planes i Pujol", 3510, "Sala SATURN", "L10");

	    Tecnic tec1 = new Tecnic("raul", "Raul Casanova i Ferrer", 1504, "T-SYSTEMS");
	    Tecnic tec2 = new Tecnic("genis", "Genís Esteve i Prats", 1515, "INET");

	    Supervisor s1 = new Supervisor("laia", "Laia Vives i Marsans", 1501, "INET");

	    this.nouUsuari(e1);
	    this.nouUsuari(e2);
	    this.nouUsuari(e3);
	    this.nouUsuari(e4);
	    this.nouUsuari(e5);

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

	    this.novaTasca(s1, sdf.parse("02/10/2019 09:21:15"), tec1, 4);
	    this.novaIntervencio(e1, sdf.parse("01/10/2019 08:15:31"), tec1, sdf.parse("04/10/2019 11:25:11"), 2,
		    "És un ratolí inalàmbric, les piles estaven gastades. "
			    + "S'han substituït però segueix sense funcionar bé del tot. Cal canviar el ratolí.");

	    this.novaIntervencio(e1, sdf.parse("01/10/2019 08:15:31"), tec2, sdf.parse("05/10/2019 13:44:37"), 1,
		    "Ratolí inalàmbric canviat per model USB.");

	    this.tancarTiquet(e1, sdf.parse("01/10/2019 08:15:31"), tec1, sdf.parse("05/10/2019 17:02:00"));
	    this.novaTasca(s1, sdf.parse("26/09/2019 11:39:14"), tec2, 9);

	    this.novaIntervencio(e3, sdf.parse("24/09/2019 07:54:13"), tec2, sdf.parse("26/09/2019 13:15:54"), 4,
		    "Google Chrome instal·lat. Firefox actualitzat i restablert "
			    + "a la configuració de fàbrica, l'ordinador està ple de virus. S'ha actualitzat l'antivirus, un escaneig complet "
			    + "en profunditat i programació d'una tasca per automatitzar un escaneig dos cops per setmana (dilluns i dijous al migdia)");

	    this.novaTasca(s1, sdf.parse("29/09/2019 12:32:04"), tec1, 3);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
