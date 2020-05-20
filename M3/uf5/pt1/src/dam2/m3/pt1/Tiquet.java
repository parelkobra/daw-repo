package dam2.m3.pt1;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

/**
 * Els empleats generen els tiquets indicant mínim una descripció i han d'estar
 * assignats a una de les categories de la llista CATEGORIES.
 *
 * Durant la creació del tiquet: - Es pot indicar una data o bé assignar-la
 * automàticament al moment del registre - En qualsevol cas cal afegir un primer
 * registre d'obertura al seguiment. - Si la categoria no es correspon a cap
 * element de la llista assignar a "Altres" - Si la descripció és nul·la o és un
 * text buit afegir directament un registre de tancament després de l'obertura -
 * Podem suposar que l'empleat no serà nul
 *
 * No es pot actualitzar cap atribut a un valor incorrecte
 *
 * @author alex
 *
 */
public class Tiquet implements Printable, Comparable<Tiquet> {
    /* Informe */
    public static final int COL_1 = 15; // Cat.
    public static final int COL_2 = 12; // Data
    public static final int COL_3 = 35; // Usuari
    public static final int COL_4 = 10; // Prio.
    public static final int COL_5 = 12; // Estat
    public static final int GAP = 3;
    public static final int W_TOTAL = COL_1 + COL_2 + COL_3 + COL_4 + COL_5 + 4 * GAP;
    public static final String CHR_BORDER = "·";

    /* Estats */
    public static final String TANCAT = "Tancat";
    public static final String EN_PROCES = "En procés";
    public static final String ASSIGNAT = "Assignat";
    public static final String PENDENT = "Pendent";

    /* Categories */
    public static final Map<String, String> CATEGORIES;
    static {
	HashMap<String, String> categories = new HashMap<>();
	categories.put("Maquinari",
		"Problemes amb components físics (ratolí, teclat, pantalla) o amb l'encesa dels equipaments.");
	categories.put("Programari",
		"Funcionament del sistema, programes, permisos d'usuari, documents i còpies de seguretat.");
	categories.put("Impressió", "Accés a les impressores. Impressió i escaneig de documents.");
	categories.put("Connectivitat", "Correu electrònic, accés a Internet i unitats de dades en xarxa.");
	categories.put("Suport", "Demanada d'ajuda per part del personal tècnic.");
	categories.put("Altres", "Situacions no contemplades a les altres categories.");
	CATEGORIES = Collections.unmodifiableMap(categories);
    }

    private String categoria;
    private String descripcio;
    private LinkedList<Accio> seguiment;

    public Tiquet(Empleat empleat, String categoria, String descripcio, Date data) throws Exception {
	if (descripcio == null || "".equals(descripcio))
	    throw new Exception("La descripció d'un tiquet no pot ser nul·la ni estar buida");
	if (empleat == null) throw new Exception("L'empleat que crea un tiquet no pot ser nul");
	this.categoria = this.checkCategoria(categoria);
	this.descripcio = descripcio.trim();
	this.obertura(empleat, data);
    }

    public Tiquet(Empleat empleat, String categoria, String descripcio) throws Exception {
	if (descripcio == null || "".equals(descripcio))
	    throw new Exception("La descripció d'un tiquet no pot ser nul·la ni estar buida");
	if (empleat == null) throw new Exception("L'empleat que crea un tiquet no pot ser nul");
	this.categoria = this.checkCategoria(categoria);
	this.descripcio = descripcio.trim();
	this.obertura(empleat, new Date());
    }

    private String checkCategoria(String categoria) {
	if (categoria == null) return CATEGORIES.get("Altres").intern();

	for (String cat : CATEGORIES.keySet()) {
	    if (categoria.trim().equals(cat)) {
		return cat;
	    }
	}
	return CATEGORIES.get("Altres").intern();
    }

    public Empleat getEmpleat() {
	return (Empleat) empleatObertura();
    }

    public String getCategoria() {
	return categoria;
    }

    public void setCategoria(String categoria) {
	this.categoria = this.checkCategoria(categoria);
    }

    public String getDescripcio() {
	return descripcio;
    }

    public void setDescripcio(String descripcio) throws Exception {
	throw new Exception("No es pot modificar la descripció");
    }

    /**
     * Si té un registre de tancament => "Tancat"
     *
     * En cas contrari si té intervencions => "En procés"
     *
     * Si està assignat a un tècnic però encara no té intervencions =>
     * "Assignat"
     *
     * Si encara no està assignat a cap tècnic => "Pendent"
     *
     *
     * @return
     */
    public String getEstat() {
	if (this.tancat()) return TANCAT;
	if (this.intervencions()) return EN_PROCES;
	if (this.assignat()) return ASSIGNAT;

	return PENDENT;
    }

    /**
     * Obtenir la prioritat a partir de l'assignació del tiquet
     *
     * Adicionalment cada dos dies des de l'obertura fins a la data de tancament
     * o fins el moment actual si encara no s'ha tancat s'incrementa la
     * prioritat inicial al nivell següent fins assolir el nivell màxim
     *
     * Si el tiquet no està encara assignat la prioritat és 0.
     *
     * @return
     */
    public int getPrioritat() {
	if (!this.assignat()) return 0;

	// Si està assignat serà la segona acció
	int prioritat = seguiment.get(1).getPrioritat();

	Date limit = this.dataTancament();
	if (limit == null) limit = new Date();

	long dies = Math.abs(Duration.between(seguiment.get(1).getData().toInstant(), limit.toInstant()).toDays());

	return Math.min(Assignacio.MAX_PRIORITAT, prioritat + (int) dies / 2);
    }

    public boolean obert() {
	return seguiment.peek() instanceof Obertura ? true : false;
    }

    public Date dataObertura() {
	if (seguiment != null) {
	    return seguiment.getFirst().getData();
	}
	return null;
    }

    public Usuari empleatObertura() {
	if (seguiment != null) {
	    return seguiment.getFirst().getUsuari();
	}
	return null;
    }

    /*
     * Totes les operacions de seguiment indicades a continuació s'han de
     * registrar ordenades temporalment al seguiment, i cal que respectin el
     * fluxe següent:
     *
     * - Obertura - Tancament => mentre no està assignat un tiquet només el
     * poden tancar l'usuari que l'ha obert o un supervisor
     *
     * - Assignació - Intervencio - Intervencio ....
     *
     * - Tancament => El tècnic assignat a un tiquet també el pot tancar sempre
     * que ell mateix hagi fet alguna de les intervencions
     */

    private String obertura(Empleat empleat, Date data) throws Exception {
	if (empleat == null) {
	    throw new Exception("L'usuari que crea aquest tiquet no pot ser nul");
	}
	if (this.seguiment != null) {
	    throw new Exception("Aquest tiquet ja està obert");
	}
	this.seguiment = new LinkedList<>();
	this.seguiment.add(new Obertura(empleat, this, data));

	return "";
    }

    /**
     * Assignació del tiquet per part del supervisor a un dels tècnics indicant
     * una prioritat entre 1 i 9
     *
     * No es poden assignar tiquets que ja estan assignats o bé tancats
     *
     * La data ha de ser posterior a la data d'obertura del tiquet
     *
     * @param supervisor
     * @param prioritat
     * @param tecnic
     * @throws Exception
     */

    public String assignacio(Supervisor supervisor, Date data, Tecnic tecnic, int prioritat) throws Exception {
	if (supervisor == null) {
	    throw new Exception("El supervisor responsable de l'assignació no pot ser nul");
	}
	if (tecnic == null) {
	    throw new Exception("El tècnic assignat a un tiquet no pot ser nul");
	}
	if (this.assignat() || this.tancat()) {
	    throw new Exception("No es poden assignar tiquets que ja estan assignats");
	}
	Calendar calCreacio = Calendar.getInstance(new Locale("ca-ES"));
	calCreacio.setTime(seguiment.getLast().getData());

	if (seguiment.getLast().getData().after(data == null ? new Date() : data)) {
	    throw new Exception("L'assignació no pot ser anterior a l'obertura del tiquet");
	}

	this.seguiment.add(new Assignacio(supervisor, this, data, tecnic, prioritat));

	return "";
    }

    /**
     * Intervenció d'un dels tècnics per resoldre el tiquet
     *
     * No es poden fer intervencions si el tiquet està tancat
     *
     * Per poder resoldre un tiquet cal que estigui assignat, tot i que no
     * necessàriament al tècnic que realitza la intervenció
     *
     * Les intervencions han de ser posteriors a l'assignació
     *
     * @param tecnic
     * @param data
     * @param hores
     * @param descripcio
     * @return
     * @throws Exception
     */

    public String intervencio(Tecnic tecnic, Date data, int hores, String descripcio) throws Exception {
	if (tecnic == null) {
	    throw new Exception("El tècnic que realitza una intervenció no pot ser nul");
	}
	if (this.tancat()) {
	    throw new Exception("No es poden fer intervencions si el tiquet està tancat");
	}
	if (!this.assignat()) {
	    throw new Exception("No es poden fer intervencions si el tiquet està pendent d'assignar");
	}
	if (seguiment.getFirst().getData().after(data == null ? new Date() : data)) {
	    throw new Exception("La intervenció no pot ser anterior a l'assignació del tiquet");
	}

	this.seguiment.add(new Intervencio(tecnic, this, data, hores, descripcio));

	return "";
    }

    /**
     * Tancament d'un tiquet
     *
     * Els tiquets tancats no es poden tornar a tancar, els únics usuaris que
     * poden tramitar el tancament són:
     *
     * - L'usuari que ha obert el tiquet i els supervisors poden tancar-lo en
     * qualsevol moment - El tècnic assignat al tiquet només el pot tancar si
     * ell mateix a realitzat alguna intervenció
     *
     * El tancament ha de ser igual o posterior a qualsevol altre acció del
     * tiquet
     *
     * @param usuari
     * @param data
     * @return
     * @throws Exception
     */

    public String tancament(Usuari usuari, Date data) throws Exception {
	if (usuari == null) {
	    throw new Exception("L'usuari que tanca un tiquet no pot ser nul");
	}
	if (this.tancat()) {
	    throw new Exception("No es pot tancar un tiquet si ja està tancat");
	}
	if ((empleatObertura() != null || empleatObertura().equals(usuari)) && !usuari.esSupervisor()
		&& !this.intervencions(usuari)) {
	    throw new Exception("Aquest usuari no pot tancar el tiquet");
	}
	for (Accio accio : seguiment) {
	    if (accio != null && accio.getData().after(data == null ? new Date() : data)) {
		throw new Exception("El tacancament ha de ser la darrera acció del tiquet");
	    }
	}

	this.seguiment.add(new Tancament(usuari, this, data));

	return "";
    }

    /*
     * Fi Operacions
     *
     */

    /**
     * Està assignat a algun tècnic
     *
     * @return
     */
    public boolean assignat() {
	for (Accio accio : seguiment) {
	    if (accio != null && accio.esAssignacio()) return true;
	}
	return false;
    }

    /**
     * Té alguna intervenció
     *
     * @return
     */
    public boolean intervencions() {
	for (Accio accio : seguiment) {
	    if (accio != null && accio.esIntervencio()) return true;
	}
	return false;
    }

    /**
     * L'usuari és tècnic i té intervencions d'aquest usuari
     *
     * @param usuari
     * @return
     */
    public boolean intervencions(Usuari usuari) {
	if (!usuari.esTecnic()) return false;

	for (Accio accio : seguiment) {
	    if (accio != null && accio.esIntervencio() && accio.getUsuari().equals(usuari)) return true;
	}
	return false;
    }

    /**
     * Està tancat
     *
     * @return
     */
    public boolean tancat() {
	for (Accio accio : seguiment) {
	    if (accio != null && accio.esTancament()) return true;
	}
	return false;
    }

    private Date dataTancament() {
	for (Accio accio : seguiment) {
	    if (accio != null && accio.esTancament()) return accio.getData();
	}
	return null;
    }

    @Override
    public String informe() {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("CA", "ES"));

	String text = "";

	// Línia 1. Dades
	text += printIdentEmptyRow(CHR_BORDER);
	text += "  " + CHR_BORDER;
	text += StringUtils.center(this.getCategoria().toUpperCase(new Locale("ca-ES")), COL_1) + " " + CHR_BORDER
		+ " ";
	text += StringUtils.center(sdf.format(seguiment.getFirst().getData()), COL_2) + " " + CHR_BORDER + " ";
	text += StringUtils.rightPad(
		StringUtils.abbreviate(empleatObertura().getNom(), COL_3 - 7) + " "
			+ (empleatObertura().getExtensio() + "").replaceFirst("(\\d{2})(\\d{2})", "(ext. $1 $2)"),
		COL_3) + " " + CHR_BORDER + " ";
	text += StringUtils.center(this.getPrioritat() + "", COL_4) + " " + CHR_BORDER + " ";
	text += StringUtils.leftPad(this.getEstat() + " ", COL_5);
	text += CHR_BORDER + "  ";
	text += System.lineSeparator();
	text += "  " + CHR_BORDER;
	text += StringUtils.repeat(" ", COL_1) + " " + CHR_BORDER + " ";
	text += StringUtils.repeat(" ", COL_2) + " " + CHR_BORDER + " ";
	text += StringUtils.rightPad(StringUtils.abbreviate(
		(((Empleat) empleatObertura()).getUbicacio() + ", " + ((Empleat) empleatObertura()).getLloc()), COL_3),
		COL_3) + " " + CHR_BORDER + " ";
	text += StringUtils.repeat(" ", COL_4) + " " + CHR_BORDER + " ";
	text += StringUtils.repeat(" ", COL_5) + "" + CHR_BORDER + "  ";
	text += System.lineSeparator();

	text += printIdentEmptyRow(CHR_BORDER);

	// Línia 2 a ?. Descripció
	text += printIdentSeparatorRow(CHR_BORDER);
	text += printIdentSeparatorRow(" ");
	String descWrapped = WordUtils.wrap(this.getDescripcio(), COL_2 + COL_3 + COL_4 + COL_5 + 3 * GAP, "%%", true);
	String[] descArray = descWrapped.split("%%");
	for (int i = 0; i < descArray.length; i++) {
	    descArray[i] = "  " + CHR_BORDER + StringUtils.repeat(" ", COL_1) + " " + CHR_BORDER + " "
		    + StringUtils.rightPad(descArray[i], COL_2 + COL_3 + COL_4 + COL_5 + 3 * GAP) + CHR_BORDER + "  ";
	}
	descWrapped = StringUtils.join(descArray, System.lineSeparator());
	text += descWrapped;
	text += System.lineSeparator();

	// Seguiment. Saltar obertura, començar des de assignació
	text += printIdentSeparatorRow(" ");
	text += printIdentSeparatorRow(CHR_BORDER);
	text += "  " + CHR_BORDER + StringUtils.repeat(" ", COL_1) + " " + CHR_BORDER + " "
		+ StringUtils.rightPad("ACTUACIONS", COL_2 + COL_3 + COL_4 + COL_5 + 3 * GAP) + CHR_BORDER + "  "
		+ System.lineSeparator();
	text += printIdentSeparatorRow(CHR_BORDER);
	for (int i = 1; i < seguiment.size(); i++) {
	    Accio accio = seguiment.get(i);
	    if (accio != null) {
		text += "  " + CHR_BORDER + StringUtils.repeat(" ", COL_1) + " " + CHR_BORDER + " ";
		text += StringUtils.center(sdf.format(accio.getData()), COL_2) + " " + CHR_BORDER + " ";
		text += StringUtils.rightPad(accio.getUsuari().getNom(), COL_3 + COL_4 + COL_5 + 2 * GAP) + CHR_BORDER
			+ "  " + System.lineSeparator();

		text += "  " + CHR_BORDER + StringUtils.repeat(" ", COL_1) + " " + CHR_BORDER + " ";
		text += StringUtils.repeat(" ", COL_2) + " "
			+ StringUtils.repeat(CHR_BORDER, COL_3 + COL_4 + COL_5 + 3 * GAP - 1) + CHR_BORDER + "  "
			+ System.lineSeparator();

		String resumWrapped = WordUtils.wrap(accio.resum(), COL_3 + COL_4 + COL_5 + 2 * GAP, "%%", true);
		String[] resumArray = resumWrapped.split("%%");
		for (int j = 0; j < resumArray.length; j++) {
		    resumArray[j] = "  " + CHR_BORDER + StringUtils.repeat(" ", COL_1) + " " + CHR_BORDER + " "
			    + StringUtils.repeat(" ", COL_2) + " " + CHR_BORDER + " "
			    + StringUtils.rightPad(resumArray[j], COL_3 + COL_4 + COL_5 + 2 * GAP) + CHR_BORDER + "  ";
		}
		resumWrapped = StringUtils.join(resumArray, System.lineSeparator());
		text += resumWrapped;

		text += System.lineSeparator();
		text += i + 1 < seguiment.size() && seguiment.get(i + 1) == null ? printSeparatorRow(CHR_BORDER)
			: printIdentSeparatorRow(CHR_BORDER);
	    }
	}

	return text;
    }

    public static String header(String string) {
	SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMMM 'de' yyyy'.'", new Locale("CA", "ES"));
	String text = "";
	text += System.lineSeparator();
	text += printSeparatorRow(CHR_BORDER);
	text += printSeparatorRow(" ");
	text += "  " + CHR_BORDER;
	text += StringUtils.center(string + ". " + StringUtils.capitalize(sdf.format(new Date())), W_TOTAL);
	text += CHR_BORDER + "  " + System.lineSeparator();
	text += printSeparatorRow(" ");
	text += printSeparatorRow(CHR_BORDER);
	text += printSeparatorRow(" ");
	text += "  " + CHR_BORDER;
	text += StringUtils.center("CATEGORIA", COL_1) + StringUtils.repeat(" ", GAP);
	text += StringUtils.center("DATA", COL_2) + StringUtils.repeat(" ", GAP);
	text += StringUtils.rightPad(StringUtils.abbreviate("EMPLEAT", COL_3), COL_3) + StringUtils.repeat(" ", GAP);
	text += StringUtils.center("PRIORITAT", COL_4) + StringUtils.repeat(" ", GAP);
	text += StringUtils.leftPad("ESTAT ", COL_5);
	text += CHR_BORDER + "  " + System.lineSeparator();
	text += printSeparatorRow(CHR_BORDER);

	return text;

    }

    public static String footer(int pendents, int assignats, int intervinguts, int tancats) {
	String text = "";
	text += printSeparatorRow(" ");
	text += printSeparatorRow(CHR_BORDER);

	text += "  " + CHR_BORDER + StringUtils.repeat(" ", COL_1 + COL_2 + COL_3 + 3 * GAP)
		+ StringUtils.leftPad("Pendents", COL_4);
	text += " " + CHR_BORDER + " " + StringUtils.center(pendents + "", COL_5) + CHR_BORDER + "  "
		+ System.lineSeparator();
	text += printSeparatorRowFooter(CHR_BORDER);

	text += "  " + CHR_BORDER + StringUtils.repeat(" ", COL_1 + COL_2 + COL_3 + 3 * GAP)
		+ StringUtils.leftPad("Assignats", COL_4);
	text += " " + CHR_BORDER + " " + StringUtils.center(assignats + "", COL_5) + CHR_BORDER + "  "
		+ System.lineSeparator();
	text += printSeparatorRowFooter(CHR_BORDER);

	text += "  " + CHR_BORDER + StringUtils.repeat(" ", COL_1 + COL_2 + COL_3 + 3 * GAP)
		+ StringUtils.leftPad("En procés", COL_4);
	text += " " + CHR_BORDER + " " + StringUtils.center(intervinguts + "", COL_5) + CHR_BORDER + "  "
		+ System.lineSeparator();
	text += printSeparatorRowFooter(CHR_BORDER);

	text += "  " + CHR_BORDER + StringUtils.repeat(" ", COL_1 + COL_2 + COL_3 + 3 * GAP)
		+ StringUtils.leftPad("Tancats", COL_4);
	text += " " + CHR_BORDER + " " + StringUtils.center(tancats + "", COL_5) + CHR_BORDER + "  "
		+ System.lineSeparator();
	text += printSeparatorRowFooter(CHR_BORDER);

	text += printIdentEmptyRowFooter(CHR_BORDER);

	text += "  " + CHR_BORDER + StringUtils.repeat(" ", COL_1 + COL_2 + COL_3 + 3 * GAP)
		+ StringUtils.leftPad("TOTAL", COL_4);
	text += " " + CHR_BORDER + " " + StringUtils.center((pendents + assignats + intervinguts + tancats) + "", COL_5)
		+ CHR_BORDER + "  " + System.lineSeparator();

	text += printIdentEmptyRowFooter(CHR_BORDER);
	text += printSeparatorRow(CHR_BORDER);

	return text;
    }

    private static String printSeparatorRow(String caracter) {
	return "  " + CHR_BORDER + StringUtils.repeat(caracter, W_TOTAL) + CHR_BORDER + "  " + System.lineSeparator();
    }

    private static String printIdentSeparatorRow(String caracter) {
	return "  " + CHR_BORDER + StringUtils.repeat(" ", COL_1) + " " + CHR_BORDER
		+ StringUtils.repeat(caracter, COL_2 + COL_3 + COL_4 + COL_5 + 3 * GAP + 1) + CHR_BORDER + "  "
		+ System.lineSeparator();
    }

    private static String printIdentEmptyRow(String caracter) {
	return "  " + CHR_BORDER + StringUtils.repeat(" ", COL_1) + " " + caracter + " "
		+ StringUtils.repeat(" ", COL_2) + " " + caracter + " " + StringUtils.repeat(" ", COL_3) + " "
		+ caracter + " " + StringUtils.repeat(" ", COL_4) + " " + caracter + " "
		+ StringUtils.repeat(" ", COL_5) + CHR_BORDER + "  " + System.lineSeparator();
    }

    private static String printSeparatorRowFooter(String caracter) {
	return "  " + CHR_BORDER + StringUtils.repeat(caracter, COL_1 + COL_2 + COL_3 + 3 * GAP)
		+ StringUtils.repeat(caracter, COL_4 + COL_5 + GAP) + CHR_BORDER + "  " + System.lineSeparator();
    }

    private static String printIdentEmptyRowFooter(String caracter) {
	return "  " + CHR_BORDER + StringUtils.repeat(" ", COL_1 + COL_2 + COL_3 + 3 * GAP)
		+ StringUtils.repeat(" ", COL_4) + " " + caracter + " " + StringUtils.repeat(" ", COL_5) + CHR_BORDER
		+ "  " + System.lineSeparator();
    }

    // Compara el nombre de usuario del empleado y la fecha de obertura del
    // tiquet
    @Override
    public int compareTo(Tiquet o) {
	if (getEmpleat().getUsuari().equals(o.getEmpleat().getUsuari()) && dataObertura().equals(o.dataObertura())) {
	    return 0;
	}
	return getEmpleat().getUsuari().compareTo(o.getEmpleat().getUsuari());
    }
}
