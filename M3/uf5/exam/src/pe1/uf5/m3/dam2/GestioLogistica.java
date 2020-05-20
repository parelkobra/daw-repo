package pe1.uf5.m3.dam2;

import java.awt.Desktop;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.Iterator;

public class GestioLogistica {
    public static final String FILE_ZONE_SCHEME = "data" + File.separatorChar + "esquemaZonaEmmagatzematge.pdf";
    public static final String FILE_ZONE_XML = "data" + File.separatorChar + "zonaEmmagatzematge.xml";
    public static final int COLUMNES = 4;
    public static final int FILES = 2;

    private ZonaEmmagatzematge emmagatzematge;

    public void simulacio() throws Exception {
	String html = "";

	// CARREGAR LA ZONA D'EMMAGATZEMATGE DES DEL FITXER XML A LA CARPETA DATA
	// (COMENTA LES DADES DE PROVA)

	// this.emmagatzematge = carregarZona(FILE_ZONE_XML);

	this.emmagatzematge = new ZonaEmmagatzematge(FILES, COLUMNES);

	this.emmagatzematge.ubicar(new Container("TOLU1234563", "Shangai", 10, LocalDate.of(2020, 1, 23)));
	this.emmagatzematge.ubicar(new Container("HDMZ0000024", "Hong Kong", 23, LocalDate.of(2020, 1, 23)));
	this.emmagatzematge.ubicar(new Refrigerat("HDMU0000153", "Valencia", 18, LocalDate.of(2020, 1, 24), 10));
	this.emmagatzematge.ubicar(new Container("TOLJ0000121", "Hamburg", 2, LocalDate.of(2020, 1, 24)));

	this.emmagatzematge.retirar("TOLJ0000121");

	this.emmagatzematge.ubicar(new Refrigerat("NYKU0000252", "Tokio", 11, LocalDate.of(2020, 1, 26), 15));
	this.emmagatzematge.ubicar(new Container("NYKU0000264", "Anvers", 8, LocalDate.of(2020, 2, 1)));
	this.emmagatzematge.ubicar(new Container("HDMJ0001113", "Hamburg", 10, LocalDate.of(2020, 2, 3)));

	this.emmagatzematge.retirar("HDMJ0001113");

	this.emmagatzematge.ubicar(new Container("TOLZ0045002", "Hamburg", 28, LocalDate.of(2020, 2, 3)));
	this.emmagatzematge.ubicar(new Container("NYKZ0000020", "Hong Kong", 4, LocalDate.of(2020, 2, 4)));
	this.emmagatzematge.ubicar(new Refrigerat("HDMZ0000110", "Rotterdam", 15, LocalDate.of(2020, 2, 4), 15));

	this.emmagatzematge.ubicar(new Container("TOLZ0012032", "Anvers", 3, LocalDate.of(2020, 2, 4)));
	this.emmagatzematge.ubicar(new Container("HDMZ0101010", "Valencia", 6, LocalDate.of(2020, 2, 4)));
	this.emmagatzematge.ubicar(new Refrigerat("NYKZ0000021", "Tokio", 12, LocalDate.of(2020, 2, 5), 15));

	this.emmagatzematge.retirar("NYKU0000252");

	this.emmagatzematge.ubicar(new Container("HDMU0123450", "Shangai", 15, LocalDate.of(2020, 2, 5)));
	this.emmagatzematge.ubicar(new Container("NYKU0000271", "Rotterdam", 13, LocalDate.of(2020, 2, 5)));

	// ACCIONS DE PROVA PER GENERAR SITUACIONS D'ERROR

	// this.emmagatzematge.ubicar(new Container("NYKU1234", "", 1, LocalDate.of(2030, 1, 26)));
	// this.emmagatzematge.ubicar(new Container("NYKU0012340", "Anvers", 40, LocalDate.of(2020, 2, 3)));
	// this.emmagatzematge.retirar("TOLU0012340");

	html += this.toHtml();
	htmlToPDF("Containers en zona d'emmagatzematge", html, FILE_ZONE_SCHEME, true);

	// DESAR LA ZONA D'EMMAGATZEMATGE EN UN FITXER XML (DIRECTORI 'data')
	desarZona(FILE_ZONE_XML);
    }

    private void desarZona(String fitxer) {
	XMLEncoder encoder = null;
	try {
	    encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FILE_ZONE_XML)));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    System.out.println("ERROR al nom/ruta del fitxer xml");
	}

	// To encode LocalDate fields
	encoder.setPersistenceDelegate(LocalDate.class, new PersistenceDelegate() {
	    @Override
	    protected Expression instantiate(Object obj, Encoder e) {
		LocalDate localDate = (LocalDate) obj;
		return new Expression(localDate, LocalDate.class, "parse", new Object[] { localDate.toString() });
	    }
	});

	encoder.writeObject(emmagatzematge);
	encoder.close();
    }

    public ZonaEmmagatzematge carregarZona(String fitxer) {
	XMLDecoder decoder = null;
	try {
	    decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fitxer)));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    System.out.println("ERROR el fitxer no s'ha pogut carregar");
	}
	ZonaEmmagatzematge emmagatzematge = (ZonaEmmagatzematge) decoder.readObject();
	decoder.close();

	return emmagatzematge;
    }

    public static void htmlToPDF(String titol, String html, String path, boolean landscape) {
	try {
	    new PdfManager(path).generarPDF(titol, html, landscape);

	    if (Desktop.isDesktopSupported()) {
		Desktop.getDesktop().open(new File(path));
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println("ERROR generant la representació de la zona d'emmagatzematge");
	}
    }

    private String toHtml() {
	// Començar per fila superior
	String html = "";
	if (this.emmagatzematge.getUbicacions() == null || this.emmagatzematge.getUbicacions().isEmpty())
	    return "No hi ha cap ubicació";

	/* Capçalera */
	html += "<table cellpadding='10' cellspacing='0' border='0'>" + System.lineSeparator();
	html += "  <tr class='header-row'>" + System.lineSeparator();
	html += "    <td align='center' colspan='" + COLUMNES + "'>ZONA D'EMMAGATZEMATGE</td>" + System.lineSeparator();
	html += "  </tr>" + System.lineSeparator();
	html += "  <tr class='subheader-row'>" + System.lineSeparator();
	html += "    <td align='center' colspan='" + COLUMNES + "'>";
	html += "    Ubicacions (fila, columna), límit " + Ubicacio.LIMIT_TONATGE
		+ " tones. Dins de cada ubicació es mostren els contenidors apilats.";
	html += "    </td>" + System.lineSeparator();
	html += "  </tr>" + System.lineSeparator();

	Ubicacio ubicacio = null;
	Iterator<Ubicacio> it = this.emmagatzematge.getUbicacions().iterator();
	for (int row = 0; row < FILES; row++) {
	    html += "  <tr>" + System.lineSeparator();
	    for (int col = 0; col < COLUMNES; col++) {
		html += "    <td align='center' valign='bottom' width='" + Math.round(100 / COLUMNES) + "%'>"
			+ System.lineSeparator();

		if (ubicacio == null && it.hasNext()) {
		    ubicacio = it.next();
		}

		if (ubicacio.getFila() == row && ubicacio.getColumna() == col) {
		    html += uToHtml(ubicacio);
		    ubicacio = null;
		} else {
		    html += emptyToHtml();
		}

		html += "      <span class='ubicacio-label'>(" + row + "," + col + ")</span>" + System.lineSeparator();
		html += "    </td>" + System.lineSeparator();
	    }
	    html += "  </tr>" + System.lineSeparator();
	}
	html += "</table>";

	return html;
    }

    private String uToHtml(Ubicacio u) {
	// Començar per fila superior
	String html = "";
	if (u.getContainers() == null || u.getContainers().isEmpty()) return emptyToHtml();

	for (int i = 0; i < Ubicacio.CAPACITAT - u.totalContenidors(); i++) {
	    html += "      <div class='container container-empty'>&nbsp;</div>" + System.lineSeparator();
	}

	for (Container container : u.getContainers()) {
	    html += cToHtml(container);
	}

	return html;
    }

    private String emptyToHtml() {
	// Començar per fila superior
	String html = "";
	for (int i = 0; i < Ubicacio.CAPACITAT; i++) {
	    html += "      <div class='container container-empty'>&nbsp;</div>" + System.lineSeparator();
	}

	return html;
    }

    private String cToHtml(Container c) {
	String html = "";
	String cssClass = "";
	int prio = c.prioritatSortida();
	switch (prio) {
	case 1:
	    cssClass = " red";
	    break;
	case 2:
	    cssClass = " orange";
	    break;

	default:
	    cssClass = " green";
	    break;
	}

	html += "      <div class='container'>" + System.lineSeparator();
	html += "         <span class='container-item container-header'>" + c.getIdentificador() + "</span><br/>"
		+ System.lineSeparator();
	html += "         <span class='container-item container-data'>" + c.getCarrega() + " tones</span>";
	html += "         <span class='container-item container-data'>(" + c.getOrigen() + ")</span><br/>"
		+ System.lineSeparator();
	html += "         <span class='container-item container-data" + cssClass + "'>Prio. "
		+ Container.PRIORITAT.get(c.prioritatSortida()) + "</span>";
	html += "         <span class='container-item container-data'>(" + c.diesDesdeArribada() + " dies)</span>"
		+ System.lineSeparator();
	html += "      </div>" + System.lineSeparator();

	return html;
    }

    public static void main(String[] args) throws Exception {
	new GestioLogistica().simulacio();
    }

}
