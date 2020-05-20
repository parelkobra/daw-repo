package m3.uf5.preguntes.examen.pt2;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeSet;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.StringUtils;

@Entity
public class Examen implements Avaluable {
    public static final int MAX_PREGUNTES = 10;
    public static final int AMPLE_ASSISTENCIA = 28;
    public static final int COL_LLIURAMENT = 28;
    public static final int GAP_LLIURAMENT = 10;
    public static final int COL_CORRECCIO = 35;
    public static final int AMPLE_ENUNCIAT = 80;
    public static final int AMPLE_PUNTUACIO = 15;
    // http://www.fileformat.info/info/unicode/char/search.htm?q=square
    public static final char NO_LLIURAT_SQUARE = '\u25EF';// '\u20DE'; '\u274F';
    public static final char LLIURAT_CHECK = '\u2A02'; // '\u274E'; ;'\u2327'

    @Basic(optional = false)
    @OneToOne
    private UnitatFormativa unitat;

    @Basic(optional = false)
    @OneToMany(cascade = CascadeType.ALL)
    private LinkedList<Pregunta> preguntes;

    @Basic(optional = false)
    @OneToMany
    private TreeSet<Estudiant> alumnes;

    @Basic(optional = false)
    @OneToMany(cascade = CascadeType.ALL)
    private Stack<Lliurament> lliuraments;

    @Basic(optional = false)
    @OneToMany(cascade = CascadeType.ALL)
    private Stack<Lliurament> correccions;

    @Basic(optional = false)
    @OneToMany(cascade = CascadeType.ALL)
    private Queue<Lliurament> revisions;

    public Examen(UnitatFormativa unitat) throws Excepcio {
	if (unitat == null) throw new Excepcio("Examen", "Cal indicar la Unitat Formativa");
	this.unitat = unitat;
	this.preguntes = new LinkedList<>();
	this.alumnes = new TreeSet<>();
	this.lliuraments = new Stack<>();
	this.correccions = new Stack<>();
	this.revisions = new LinkedList<>();
    }

    public UnitatFormativa getUnitat() {
	return unitat;
    }

    public void setUnitat(UnitatFormativa unitat) throws Excepcio {
	if (unitat == null) throw new Excepcio("Examen", "Cal indicar la Unitat Formativa");
	this.unitat = unitat;
    }

    public void inscriureEstudiant(Estudiant estudiant) throws Excepcio {
	if (estudiant == null) throw new Excepcio("Examen", "Cal indicar l'estudiant per poder inscriure'l");
	this.alumnes.add(estudiant);
    }

    public void inscriureEstudiants(Estudiant[] estudiants) throws Excepcio {
	if (estudiants == null) throw new Excepcio("Examen", "Cal indicar els estudiants per poder-los inscriure");
	for (Estudiant estudiant : estudiants) {
	    if (estudiant == null) throw new Excepcio("Examen", "Falta indicar algun dels estudiants");
	}
	this.alumnes.addAll(Arrays.asList(estudiants));
    }

    public void anularMatriculaEstudiant(Estudiant estudiant) throws Excepcio {
	if (estudiant == null) throw new Excepcio("Examen", "Cal indicar l'estudiant per poder anul·lar la matrícula");
	this.alumnes.remove(estudiant);
    }

    public String generarLlistatAssistencia() throws Excepcio {
	// Marcar el checkbox en cas que l'estudiant estigui a lliuraments o a correccions
	String assistencia = "  " + StringUtils.center("Llistat assistència", Examen.AMPLE_ASSISTENCIA) + "  "
		+ System.lineSeparator();
	assistencia += "  " + StringUtils.repeat(".", Examen.AMPLE_ASSISTENCIA) + System.lineSeparator() + "  "
		+ System.lineSeparator();

	Iterator<Estudiant> it = this.alumnes.iterator();
	while (it.hasNext()) {
	    Estudiant current = it.next();
	    assistencia += "  " + StringUtils.rightPad(
		    (this.lliuraments.contains(new Lliurament(current, this))
			    || this.correccions.contains(new Lliurament(current, this)) ? LLIURAT_CHECK
				    : NO_LLIURAT_SQUARE)
			    + "  " + StringUtils.abbreviate(current.getCognomsNom(), Examen.AMPLE_ASSISTENCIA - 3),
		    Examen.AMPLE_ASSISTENCIA) + System.lineSeparator();
	}

	assistencia += System.lineSeparator() + "  " + StringUtils.repeat("_", Examen.AMPLE_ASSISTENCIA)
		+ System.lineSeparator();
	return assistencia;
    }

    public void apilarLliurament(Estudiant estudiant) throws Excepcio {
	if (estudiant == null) throw new Excepcio("Examen", "Cal indicar l'estudiant per poder fer el lliurament");
	Lliurament nou = new Lliurament(estudiant, this);
	if (this.lliuraments.contains(nou) || this.correccions.contains(nou))
	    throw new Excepcio("Examen", "L'estudiant " + estudiant.getCognomsNom() + " ja ha fet un lliurament");
	this.lliuraments.push(nou);
    }

    public void corregirLliurament(double nota) throws Excepcio {
	// Treure de lliuraments i afegir a correccions
	this.validarNota(nota);

	if (this.lliuraments.isEmpty()) throw new Excepcio("Examen", "No hi ha cap lliurament per corregir");
	Lliurament correccio = this.lliuraments.pop();
	correccio.setNota(this.notaNormalitzada(nota));
	this.correccions.push(correccio);
    }

    public String mostrarCorreccions() {
	// Mostra les dues piles: lliuraments i correccions respectant l'ordre de cadascuna

	String pila = "  " + StringUtils.repeat(" ", GAP_LLIURAMENT);
	pila += StringUtils.center("LLIURAMENTS", COL_LLIURAMENT);
	pila += StringUtils.repeat(" ", GAP_LLIURAMENT);
	pila += StringUtils.center("CORRECCIONS", COL_CORRECCIO) + System.lineSeparator();

	pila += "  " + StringUtils.repeat(" ", GAP_LLIURAMENT);
	pila += StringUtils.repeat(" ", COL_LLIURAMENT);
	pila += StringUtils.repeat(" ", GAP_LLIURAMENT);
	pila += StringUtils.repeat(" ", COL_CORRECCIO) + System.lineSeparator() + System.lineSeparator();

	int top = Math.max(this.lliuraments.size(), this.correccions.size()) - 1;

	while (top >= 0) {
	    pila += "  ";
	    // Primera columna. LLiuraments
	    pila += Examen.mostrarLliuramentPila(this.lliuraments, top, false);

	    // Segona columna. Correccions
	    pila += Examen.mostrarLliuramentPila(this.correccions, top, true);

	    pila += System.lineSeparator() + System.lineSeparator();
	    top--;
	}

	pila += "  " + StringUtils.repeat(" ", GAP_LLIURAMENT);
	pila += StringUtils.center(" TAULA PROFESSOR ", COL_LLIURAMENT + GAP_LLIURAMENT + COL_CORRECCIO, "_");
	pila += System.lineSeparator() + System.lineSeparator();

	return pila;
    }

    private static String mostrarLliuramentPila(Stack<Lliurament> pila, int index, boolean nota) {
	String strLliurament = "";
	String strCurrent = "";
	if (index <= pila.size() - 1) {
	    Lliurament current = pila.get(index);

	    if (index == pila.size() - 1) {
		strLliurament += StringUtils.center("TOP ->", GAP_LLIURAMENT);
	    } else {
		strLliurament += StringUtils.repeat(" ", GAP_LLIURAMENT);
	    }
	    strLliurament += "[ ";
	    if (nota) {
		strCurrent += (new DecimalFormat("#0.00")).format(current.getNota()) + " - ";
	    }
	    strCurrent += current.getEstudiant().getCognomsNom();
	    strLliurament += StringUtils.center(StringUtils.abbreviate(strCurrent, COL_LLIURAMENT - 4),
		    COL_LLIURAMENT - 4, " ") + " ]";
	} else {
	    strLliurament += StringUtils.repeat(" ", GAP_LLIURAMENT + COL_LLIURAMENT);
	}
	return strLliurament;
    }

    // Només si el lliurament de l'estudiant està corregit
    public void solicitarRevisio(Estudiant estudiant) throws Excepcio {
	if (!this.correccions.contains(new Lliurament(estudiant, this))) throw new Excepcio("Examen",
		"El lliurament de l'alumne " + estudiant.getCognomsNom() + " no està corregit");

	Lliurament lliuramentEstudiant = Examen.cercarLliuramentEstudiant(this.correccions, estudiant);
	if (lliuramentEstudiant != null) {
	    this.revisions.offer(lliuramentEstudiant);
	}
    }

    private static Lliurament cercarLliuramentEstudiant(Stack<Lliurament> pila, Estudiant find) {
	for (Lliurament lliurament : pila)
	    if (lliurament.getEstudiant().compareTo(find) == 0) return lliurament;

	return null;
    }

    public void atendreRevisio(double nota) throws Excepcio {
	this.validarNota(nota);

	Lliurament correccio = this.revisions.poll();
	if (correccio != null) {
	    correccio.setNota(this.notaNormalitzada(nota));
	}
    }

    public void validarNota(double nota) throws Excepcio {
	if (nota < 0 || nota > this.getPuntuacio()) throw new Excepcio("Examen", "El valor de la nota és incorrecte: "
		+ (new DecimalFormat("#0.00")).format(this.notaNormalitzada(nota)));
    }

    // Sobre 10
    public double notaNormalitzada(double nota) {
	if (this.getPuntuacio() == 0) return 0;

	return nota / this.getPuntuacio() * 10;
    }

    // retorna false si no hi ha espai al vector, text eś null o puntuació < 0
    public boolean afegirPreguntaOberta(String text, double puntuacio) throws Excepcio {
	this.validarPregunta(text, puntuacio);

	return this.preguntes.add(new PreguntaOberta(text, puntuacio));
    }

    // retorna false si no hi ha espai al vector, text eś null, puntuació < 0 o opcions és null
    public boolean afegirPreguntaOpcions(String text, double puntuacio, String[] opcions) throws Excepcio {
	this.validarPregunta(text, puntuacio);

	if (opcions == null) throw new Excepcio("Examen", "Les opcions de la pregunta són incorrectes");

	return this.preguntes.add(new PreguntaOpcions(text, puntuacio, opcions));
    }

    // retorna false si no hi ha espai al vector, text eś null o puntuació < 0
    public boolean afegirPreguntaVerdaderFals(String text, double puntuacio) throws Excepcio {
	this.validarPregunta(text, puntuacio);

	return this.preguntes.add(new PreguntaVerdaderFals(text, puntuacio));
    }

    // Esborra la pregunta (canvia la instància per null) num començant per 1 fins a 10 retorna false si
    // l'índex és incorrecte
    public boolean esborrarPregunta(int num) throws Excepcio {
	if (num < 1 || num > this.preguntes.size())
	    throw new Excepcio("Examen", "No es pot esborrar la pregunta " + num + ", no existeix ");

	this.preguntes.remove(num - 1);

	return true;
    }

    private void validarPregunta(String text, double puntuacio) throws Excepcio {
	if (this.preguntes.size() >= MAX_PREGUNTES)
	    throw new Excepcio("Examen", "L'examen ha arribat al límit de preguntes: " + MAX_PREGUNTES);

	if (text == null) throw new Excepcio("Examen", "Cal indicar el text de la pregunta");

	if (puntuacio <= 0) throw new Excepcio("Examen", "La puntuació de la pregunta hauria de ser major que 0");
    }

    // titol
    @Override
    public String getTitol() {
	return StringUtils.center(" Examen " + unitat.getModul() + " ", AMPLE_ENUNCIAT, "#") + System.lineSeparator()
		+ System.lineSeparator()
		+ StringUtils.rightPad(
			StringUtils.abbreviate("UF" + unitat.getNum() + ": " + unitat.getTitol(), AMPLE_ENUNCIAT),
			AMPLE_ENUNCIAT)
		+ System.lineSeparator();
    }

    // suma punts
    @Override
    public double getPuntuacio() {
	double punt = 0;
	for (Pregunta pregunta : preguntes) {
	    if (pregunta != null) {
		punt += pregunta.getPuntuacio();
	    }
	}
	return punt;
    }

    // enunciat
    @Override
    public String getEnunciat() {
	return this.getEnunciatIterator(this.preguntes.iterator());
    }

    public String getEnunciatDescendent() {
	return this.getEnunciatIterator(this.preguntes.descendingIterator());
    }

    private String getEnunciatIterator(Iterator<Pregunta> iterator) {
	String enunciat = StringUtils.rightPad("ENUNCIAT", AMPLE_ENUNCIAT - AMPLE_PUNTUACIO) + StringUtils
		.leftPad((new DecimalFormat("##0.0")).format(this.getPuntuacio()) + " PUNTS", AMPLE_PUNTUACIO);
	enunciat += System.lineSeparator() + StringUtils.repeat("-", AMPLE_ENUNCIAT) + System.lineSeparator()
		+ System.lineSeparator();

	int num = 1;
	while (iterator.hasNext()) {
	    enunciat += iterator.next().getEnunciatPregunta(num) + StringUtils.repeat(System.lineSeparator(), 3);
	    num++;
	}
	return enunciat;
    }

    @Override
    public boolean esAvaluable() {
	return this.getPuntuacio() > 0;
    }
}
