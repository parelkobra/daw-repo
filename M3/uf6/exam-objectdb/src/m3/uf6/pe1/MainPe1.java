package m3.uf6.pe1;

import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

public class MainPe1 {
    public static final String DATABASE = "$objectdb/db/sucursals.odb";
    public static final int MIDA_PAGINA = 8;
    public static final int COL_RESULTAT = 20;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) {

	initEM();

	// Mètodes Exercici 3. Operacions CRUD
	gestionarDades1();
	gestionarDades2();
	gestionarDades3();
	gestionarDades4();

	// Mètodes Exercici 4. Consultes
	consultaDades1(3, "Baix Empordà", 20);
	consultaDades2(2);
	consultaDades3(5, 0, 1);

	closeDatabase();

	System.out.println("FINAL");
    }

    public static void gestionarDades1() {
	System.out.println("GESTIONAR DADES 1" + System.lineSeparator());

	/* Consultar el banc amb codi «19» fent servir la la consulta amb nom «Banc.findByCodi» */
	System.out.println("1. Consultar el banc amb codi «19»");
	TypedQuery<Banc> q = em.createNamedQuery("Banc.findByCodi", Banc.class);
	q.setParameter("codi", 19);

	Banc banc = q.getSingleResult();
	System.out.println("[codi: " + banc.getCodi() + "] [nom: " + banc.getNom() + "]");
	System.out.println();

	/* Crear un nou municipi amb les següents dades: "La cope", "Barcelonés", "Barcelona", 08888 */
	System.out.println(
		"2. Crear un nou municipi amb les següents dades: 'La cope', 'Barcelonés', 'Barcelona', 08888");

	Municipi municipi = new Municipi("La cope", "Barcelonés", "Barcelona", Integer.parseInt("08888"));
	System.out.println(municipi.toString());
	System.out.println();

	/* Crear una nova sucursal per al banc i municipi anteriors amb 105 clients */
	System.out.println("3. Crear una nova sucursal per al banc i municipi anteriors amb 105 clients");
	Sucursal sucursal = new Sucursal(banc, municipi, 105);
	System.out.println("Sucursal [clients: " + sucursal.getClients() + "]");
	System.out.println();

	/* Persist entity objects */
	Object[] objs = { municipi, sucursal };
	persistToDB(objs);
    }

    public static void gestionarDades2() {
	System.out.println("GESTIONAR DADES 2" + System.lineSeparator());

	/* Consultar el banc amb codi «2100» */
	System.out.println("1. Consultar el banc amb codi «2100»");
	TypedQuery<Banc> q1 = em.createNamedQuery("Banc.findByCodi", Banc.class);
	q1.setParameter("codi", 2100);

	Banc banc = q1.getSingleResult();
	System.out.println("[codi: " + banc.getCodi() + "] [nom: " + banc.getNom() + "]");
	System.out.println();

	/* Consultar l’empleat amb dni «00044402-Y» */
	System.out.println("2. Consultar l’empleat amb dni «00044402-Y»");
	TypedQuery<Empleat> q2 = em.createNamedQuery("Empleat.findByDni", Empleat.class);
	q2.setParameter("dni", "00044402-Y");

	Empleat empleat = q2.getSingleResult();
	System.out.println("[dni: " + empleat.getDni() + "] [nom: " + empleat.getNom() + "] [antiguitat: "
		+ empleat.getAntiguitat() + "] [sou: " + empleat.getSou() + "]");
	System.out.println();

	/* Modificar el nom de l’empleat per «Joan Rius i Taulet» */
	System.out.println("3. Modificar el nom de l’empleat per «Joan Rius i Taulet»");
	empleat.setNom("Joan Rius i Taulet");
	System.out.println();

	/* Afegir l’empleat al banc anterior */
	System.out.println("4. Afegir l’empleat al banc anterior");
	banc.addEmpleat(empleat);
	System.out.println();

	/* Persist entity objects */
	Object[] objs = { empleat, banc };
	persistToDB(objs);
    }

    public static void gestionarDades3() {
	System.out.println("GESTIONAR DADES 3" + System.lineSeparator());

	/* Consultar l’empleat amb dni «10222304-A» */
	System.out.println("1. Consultar l’empleat amb dni «10222304-A»");
	TypedQuery<Empleat> q = em.createNamedQuery("Empleat.findByDni", Empleat.class);
	q.setParameter("dni", "10222304-A");

	Empleat empleat = q.getSingleResult();
	System.out.println("[dni: " + empleat.getDni() + "] [nom: " + empleat.getNom() + "] [antiguitat: "
		+ empleat.getAntiguitat() + "] [sou: " + empleat.getSou() + "]");
	System.out.println();

	/* Esborrar l’empleat anterior */
	System.out.println("2. Esborrar l’empleat anterior");
	System.out.println();
	Object[] objs = { empleat };
	deleteFromDB(objs);
    }

    public static void gestionarDades4() {
	System.out.println("GESTIONAR DADES 4" + System.lineSeparator());

	/*
	 * Crear un nou empleat amb nom «Antoni Crusellas Peris», amb 10 anys d’antiguitat, un sou de 1.500€
	 * i amb el dni null.
	 */
	System.out.println(
		"1. Esborrar l’empleat anteriorCrear un nou empleat amb nom «Antoni Crusellas Peris», amb 10 anys d’antiguitat, un sou de 1.500€ i amb el dni null.");
	Empleat empleat = new Empleat(null, "Antoni Crusellas Peris", 10, 1500);
	Object[] objs = { empleat };
	persistToDB(objs);
	System.out.println();
    }

    private static void persistToDB(Object[] objs) {
	em.getTransaction().begin();
	for (Object obj : objs) {
	    em.persist(obj);
	}
	try {
	    em.getTransaction().commit();
	} catch (RollbackException e) {
	    System.out.println("TRANSACTION ERROR: " + e.getMessage());
	}
    }

    private static void deleteFromDB(Object[] objs) {
	em.getTransaction().begin();
	for (Object obj : objs) {
	    em.remove(obj);
	}
	try {
	    em.getTransaction().commit();
	} catch (RollbackException e) {
	    System.out.println("TRANSACTION ERROR: " + e.getMessage());
	}
    }

    public static void consultaDades1(int page, String comarca, int clients) {
	String jpqlQuery = "SELECT s.clients, s.banc.nom, s.municipi.nom "
		+ "FROM Sucursal s WHERE municipi.comarca = :comarca AND clients > :clients "
		+ "ORDER BY clients DESC, municipi.nom ASC";

	Query q = em.createQuery(jpqlQuery);
	q.setParameter("clients", clients);
	q.setParameter("comarca", comarca);

	q.setFirstResult((page - 1) * MIDA_PAGINA);
	q.setMaxResults(MIDA_PAGINA);

	@SuppressWarnings("unchecked")
	List<Object[]> res = q.getResultList();

	Vector<String[]> dades = new Vector<>();
	for (Object[] items : res) {
	    dades.add(new String[] { items[0] + "", items[1] + "", items[2] + "" });
	}

	System.out.println(mostrarResultatConsulta("Consulta Dades 1", dades));
    }

    public static void consultaDades2(int page) {
	String jpqlQuery = "SELECT b.codi, b.nom, size(b.sucursals), e.dni, e.nom, e.sou FROM Banc b "
		+ "JOIN b.empleats e WHERE size(sucursals) > 3 ORDER BY b.nom ASC, e.sou DESC";

	Query q = em.createQuery(jpqlQuery);

	q.setFirstResult((page - 1) * MIDA_PAGINA);
	q.setMaxResults(MIDA_PAGINA);

	@SuppressWarnings("unchecked")
	List<Object[]> res = q.getResultList();

	Vector<String[]> dades = new Vector<>();
	for (Object[] items : res) {
	    dades.add(new String[] { items[0] + "", items[1] + "", items[2] + "", items[3] + "", items[4] + "",
		    items[5] + "" });
	}

	System.out.println(mostrarResultatConsulta("Consulta Dades 2", dades));
    }

    public static void consultaDades3(int page, int min, int anys) {
	String jpqlQuery = "SELECT b.codi, b.nom, count(b.empleats), avg(e.sou) FROM Banc b JOIN b.empleats e "
		+ "WHERE size(b.empleats) > :min AND e.antiguitat > :anys GROUP BY b "
		+ "ORDER BY count(b.empleats) DESC, b.codi ASC";

	Query q = em.createQuery(jpqlQuery);
	q.setParameter("min", min);
	q.setParameter("anys", anys);

	q.setFirstResult((page - 1) * MIDA_PAGINA);
	q.setMaxResults(MIDA_PAGINA);

	@SuppressWarnings("unchecked")
	List<Object[]> res = q.getResultList();

	Vector<String[]> dades = new Vector<>();
	for (Object[] items : res) {
	    dades.add(new String[] { items[0] + "", items[1] + "", items[2] + "", items[3] + "" });
	}

	System.out.println(mostrarResultatConsulta("Consulta Dades 3", dades));
    }

    public static String mostrarResultatConsulta(String titol, Vector<String[]> dades) {
	if (dades == null || titol == null)
	    return System.lineSeparator() + "No es pot mostrar les dades de la consulta. Dades d'entrada incorrectes";

	String resultat = System.lineSeparator() + "RESULTATS " + titol + System.lineSeparator();

	if (dades.size() == 0) {
	    resultat = titol + System.lineSeparator() + " SENSE RESULTATS " + titol + System.lineSeparator();
	    return resultat;
	}

	resultat += System.lineSeparator();
	for (int i = 0; i < dades.get(0).length; i++) {
	    resultat += StringUtils.rightPad("Camp " + (i + 1), COL_RESULTAT) + "   ";
	}
	resultat += System.lineSeparator();
	for (int i = 0; i < dades.get(0).length; i++) {
	    resultat += StringUtils.repeat("-", COL_RESULTAT) + "   ";
	}
	resultat += System.lineSeparator();

	for (String[] fila : dades) {
	    for (String dada : fila) {
		resultat += StringUtils.rightPad(StringUtils.abbreviate(dada, COL_RESULTAT), COL_RESULTAT);
		resultat += "   ";
	    }
	    resultat += System.lineSeparator();
	}
	return resultat + System.lineSeparator();
    }

    /**
     * Retorna una nova instancia d'EntityManager creada des de la factoria emf associada a la base de
     * dades DATABASE
     *
     * @return instància d'Entity Manager per la base de dades DATABASE
     */
    public static void initEM() {
	emf = Persistence.createEntityManagerFactory(DATABASE);
	em = emf.createEntityManager();
    }

    /**
     * Close em i emf
     *
     * @param em
     */
    public static void closeDatabase() {
	em.close();
	emf.close();
    }

}
