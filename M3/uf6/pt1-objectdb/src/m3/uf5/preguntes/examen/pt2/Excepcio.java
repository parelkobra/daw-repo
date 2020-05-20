package m3.uf5.preguntes.examen.pt2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

public class Excepcio extends Exception {
    private static final long serialVersionUID = 1L;
    private static Map<Date, String> registre;
    private String classe;

    public Excepcio(String classe, String message) {
	super(message);
	this.classe = classe;
	if (registre == null) {
	    registre = new TreeMap<>();
	}
	registre.put(new Date(), this.getMessage());
    }

    public String getClasse() {
	return classe;
    }

    public void setClasse(String classe) {
	this.classe = classe;
    }

    @Override
    public String getMessage() {
	return "[" + this.classe + "] " + super.getMessage();
    }

    public static String registreErrors() {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S", new Locale("CA", "ES"));
	String informe = "";

	int index = 0;
	for (Map.Entry<Date, String> entry : registre.entrySet()) {
	    informe += "`:(" + StringUtils.leftPad("" + index, 5) + " "
		    + StringUtils.rightPad(sdf.format(entry.getKey()), 23, "0") + " " + entry.getValue()
		    + System.lineSeparator();
	    index++;
	}

	return informe;
    }

}
