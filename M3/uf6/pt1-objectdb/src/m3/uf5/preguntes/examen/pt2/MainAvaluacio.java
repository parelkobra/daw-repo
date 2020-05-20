package m3.uf5.preguntes.examen.pt2;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainAvaluacio {
    public static final String ODB = "db/avaluacio.odb";

    public static final String DAM = "Desenvolupament d'Aplicacions Multiplataforma";
    public static final String DAW = "Desenvolupament d'Aplicacions Web";
    public static final String ASIX = "Administració de Sistemes Informàtics en la Xarxa";

    public static void main(String[] args) throws Excepcio, IOException {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory(ODB);
	EntityManager em = emf.createEntityManager();

	em.getTransaction().begin();

	Estudiant joan = new Estudiant("Joan", "Pérez i Castells", 19);
	em.persist(joan);

	Estudiant maria = new Estudiant("Maria", "González i Fornells", 21);
	em.persist(maria);

	Estudiant john = new Estudiant("John", "Doe", 31);
	em.persist(john);

	Estudiant marta = new Estudiant("Marta", "Martínez i Miracles", 24);
	em.persist(marta);

	Estudiant pere = new Estudiant("Pere", "Gálvez i Gaudí", 22);
	em.persist(pere);

	Estudiant anna = new Estudiant("Anna", "Ruiz i Roure", 22);
	em.persist(anna);

	Estudiant raul = new Estudiant("Raul", "Ruiz i Miralls", 26);
	em.persist(raul);

	Estudiant toni = new Estudiant("Toni", "Téllez i Saperas", 28);
	em.persist(toni);

	Estudiant gemma = new Estudiant("Gemma", "Suárez i Rius", 19);
	em.persist(gemma);

	Estudiant[] estudiants = { joan, maria, john, marta, pere, anna, raul, toni, gemma };

	Estudiant marc = new Estudiant("Marc", "Gómez i Crusellas", 23);
	em.persist(marc);

	UnitatFormativa m3damUf4 = new UnitatFormativa("DAM", "M03. Programació", 4,
		"Programació orientada a objectes (POO). Fonaments", 35);
	em.persist(m3damUf4);

	UnitatFormativa m7asixUf2 = new UnitatFormativa("ASIX", "M07. Planificació i Administració de Xarxes", 2,
		"Administració de dispositius de xarxa", 55);
	em.persist(m7asixUf2);

	Examen pe1Uf1M7 = new Examen(m7asixUf2);

	if (!pe1Uf1M7.esAvaluable()) {
	    System.out.println("1. \"pe1Uf1M7\" no és avaluable" + System.lineSeparator());
	}

	Examen pe1Uf4M3 = new Examen(m3damUf4);

	/* Matrícula alumnat */
	pe1Uf4M3.inscriureEstudiants(estudiants);
	pe1Uf4M3.inscriureEstudiant(joan); // repetit
	pe1Uf4M3.inscriureEstudiant(marc);
	pe1Uf4M3.anularMatriculaEstudiant(john);

	System.out.println("2. Llistat assistència 9 alumnes: Marta, Pere, Marc, Maria, Joan, Raul, Anna, Gemma, Toni"
		+ System.lineSeparator());
	System.out.println(pe1Uf4M3.generarLlistatAssistencia());	// sense checks

	/* Redacció de l'examen */
	System.out.print("3. ");
	try {
	    if (!pe1Uf4M3.esborrarPregunta(2)) {
		System.out.print("É");
	    }
	} catch (Excepcio e) {
	    System.out.println("ERROR => " + e.getMessage());
	    System.in.read();

	    System.out.print("É");
	}
	if (pe1Uf4M3.afegirPreguntaOberta(
		"Explica què és una classe abstracte, quines característiques té i les diferències respecte una classe normal.",
		1.5)) {
	    System.out.print("s");
	}
	if (pe1Uf4M3.afegirPreguntaOberta("Lorem ipsum dolor sit amet, consectetuer adipiscing elit...", 2)) {
	    System.out.print(" ");
	}
	if (pe1Uf4M3.afegirPreguntaOpcions(
		"Marca la resposta correcte. Exemples de tipus de dades primitives poden ser:", 0.8,
		new String[] { "int, double, String.", "char, double, float.", "Char, Float, Boolean." })) {
	    System.out.print("c");
	}
	if (pe1Uf4M3.afegirPreguntaOpcions(
		"Marca la resposta correcte. La millor manera per comparar el text \"Hola\" amb la variable de tipus String hola és:",
		0.8, new String[] { "\"Hola\" == hola", "hola.equals(\"Hola\")", "\"Hola\".equals(hola)",
			"Cap de les anteriors" })) {
	    System.out.print("o");
	}
	if (pe1Uf4M3.afegirPreguntaOpcions(
		"Marca la resposta correcte. L'ordre correcte dels elements dins una classe és:", 0.8,
		new String[] { "atributs > constructor > getters/setters > altres mètodes.",
			"atributs > getters/setters > constructor > altres mètodes.",
			"atributs > altres mètodes > constructor > getters/setters.",
			"altres mètodes > getters/setters > constructor > atributs." })) {
	    System.out.print("r");
	}
	if (pe1Uf4M3.afegirPreguntaOberta(
		"Descriu l'esquema jeràrquic de classes que implementa el llenguatge Java i les seves característiques principals. Posa exemples concrets.",
		2)) {
	    System.out.print("r");
	}
	if (pe1Uf4M3.afegirPreguntaVerdaderFals(
		"Indica si la següent afirmació és certa o falsa. Una classe abstracta A implementa la interfície I, per tant la classe A està obligada a implementar els mètodes de I.",
		0.8)) {
	    System.out.print("e");
	}
	if (pe1Uf4M3.afegirPreguntaVerdaderFals(
		"Indica si la següent afirmació és certa o falsa. Una classe A hereta d'una classe B, aleshores es pot afirmar que B hereta atributs i mètodes de A.",
		0.8)) {
	    System.out.print("c");
	}
	try {
	    if (!pe1Uf4M3.esborrarPregunta(-1)) {
		System.out.print("t");
	    }
	} catch (Excepcio e) {
	    System.out.println("ERROR => " + e.getMessage());
	    System.in.read();

	    System.out.print("t");
	}
	if (pe1Uf4M3.esborrarPregunta(2)) {
	    System.out.print("e");
	}
	if (pe1Uf4M3.afegirPreguntaOberta("Proposa el diagrama UML complet d'una classe que representi una persona",
		2.5)) {
	    System.out.println("!" + System.lineSeparator());
	}

	if (pe1Uf4M3.esAvaluable()) {
	    System.out.println("4. Enunciat \"pe1Uf4M3\"" + System.lineSeparator());
	    System.out.println(pe1Uf4M3.getTitol());
	    System.out.println(pe1Uf4M3.getEnunciat() + System.lineSeparator());

	    System.out.println("5. Enunciat descendent \"pe1Uf4M3\"" + System.lineSeparator());
	    System.out.println(pe1Uf4M3.getTitol());
	    System.out.println(pe1Uf4M3.getEnunciatDescendent() + System.lineSeparator());
	}

	/* Lliuraments dels alumnes */
	// System.out.println(pe1Uf4M3.mostrarCorreccions()); // Pila buida
	pe1Uf4M3.apilarLliurament(marta);
	pe1Uf4M3.apilarLliurament(marc);
	pe1Uf4M3.apilarLliurament(gemma);
	pe1Uf4M3.apilarLliurament(toni);
	pe1Uf4M3.apilarLliurament(raul);
	pe1Uf4M3.apilarLliurament(anna);

	System.out.println(
		"6. Llistat assistència 6 lliuraments: Marc, Maria, Raul, Anna, Gemma, Toni" + System.lineSeparator());
	System.out.println(pe1Uf4M3.generarLlistatAssistencia());	// amb checks i sense

	pe1Uf4M3.apilarLliurament(joan);
	try {
	    pe1Uf4M3.apilarLliurament(joan);		// Existent
	} catch (Excepcio e) {
	    System.out.println("ERROR => " + e.getMessage());
	    System.in.read();
	}
	pe1Uf4M3.apilarLliurament(pere);

	System.out.println("7. Cap correcció i 8 lliuraments" + System.lineSeparator());
	System.out.println(pe1Uf4M3.mostrarCorreccions());

	try {
	    pe1Uf4M3.corregirLliurament(-4.5);		// No corregeix
	} catch (Excepcio e) {
	    System.out.println("ERROR => " + e.getMessage());
	    System.in.read();
	}
	try {
	    pe1Uf4M3.corregirLliurament(24.5);		// No corregeix
	} catch (Excepcio e) {
	    System.out.println("ERROR => " + e.getMessage());
	    System.in.read();
	}

	pe1Uf4M3.corregirLliurament(9);			// Pere
	pe1Uf4M3.corregirLliurament(7.75);		// Joan
	pe1Uf4M3.corregirLliurament(8.1);		// Anna
	pe1Uf4M3.corregirLliurament(4.3);		// Raul
	pe1Uf4M3.apilarLliurament(maria);

	System.out.println("8. Total correccions 4: Raul, Anna, Joan i Pere" + System.lineSeparator());
	System.out.println(pe1Uf4M3.mostrarCorreccions());

	pe1Uf4M3.corregirLliurament(7.05);		// Maria
	pe1Uf4M3.corregirLliurament(8.25);		// Toni
	pe1Uf4M3.corregirLliurament(9.65);		// Gemma
	pe1Uf4M3.corregirLliurament(5.54);		// Marc
	pe1Uf4M3.corregirLliurament(2);			// Marta
	try {
	    pe1Uf4M3.corregirLliurament(0);			// pila buida
	} catch (Excepcio e) {
	    System.out.println("ERROR => " + e.getMessage());
	    System.in.read();
	}

	System.out.println(
		"9. Correccions finalitzades. Total 9: Marta, Marc, Gemma, Toni, Maria, Raul, Anna, Joan, Pere "
			+ System.lineSeparator());
	System.out.println(pe1Uf4M3.mostrarCorreccions());	// ??

	System.out.println("10. Llistat assistència correccions finalitzades. Tots marcats" + System.lineSeparator());
	System.out.println(pe1Uf4M3.generarLlistatAssistencia());	// amb checks

	try {
	    pe1Uf4M3.atendreRevisio(-1);	// valor KO
	} catch (Excepcio e) {
	    System.out.println("ERROR => " + e.getMessage());
	    System.in.read();
	}
	pe1Uf4M3.atendreRevisio(5);		// cua buida
	pe1Uf4M3.solicitarRevisio(marta);
	pe1Uf4M3.solicitarRevisio(raul);
	pe1Uf4M3.solicitarRevisio(maria);
	pe1Uf4M3.atendreRevisio(3);		// Atendre Marta
	pe1Uf4M3.atendreRevisio(7);		// Atendre Raul
	pe1Uf4M3.solicitarRevisio(marta);
	pe1Uf4M3.atendreRevisio(1);		// Atendre Maria
	pe1Uf4M3.atendreRevisio(3.5);		// Atendre Marta
	pe1Uf4M3.atendreRevisio(0);		// cua buida

	System.out.println("11. Correccions finalitzades. Canvi notes: Marta -> 3.5, Raul -> 7, Maria -> 1"
		+ System.lineSeparator());
	System.out.println(pe1Uf4M3.mostrarCorreccions());

	System.out.println("12. Registre ERRORS (7 en total)" + System.lineSeparator());
	System.out.println(Excepcio.registreErrors());

	em.persist(pe1Uf1M7);
	em.persist(pe1Uf4M3);

	em.getTransaction().commit();

	// Close EntityManager and EntityManagerFactory
	em.close();
	emf.close();
    }

}
