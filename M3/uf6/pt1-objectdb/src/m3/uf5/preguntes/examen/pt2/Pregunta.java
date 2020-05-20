package m3.uf5.preguntes.examen.pt2;

import java.text.DecimalFormat;

import javax.jdo.annotations.Index;
import javax.persistence.Basic;
import javax.persistence.Entity;

import org.apache.commons.lang3.text.WordUtils;

@Entity
@Index(members = { "puntuacio" })
public abstract class Pregunta {
    @Basic(optional = false)
    protected String text;
    protected double puntuacio;

    public Pregunta(String text, double puntuacio) throws Excepcio {
	if (text == null || "".equals(text.trim()))
	    throw new Excepcio("Pregunta", "Cal indicar un text per la pregunta");
	this.text = text;
	if (puntuacio <= 0) throw new Excepcio("Pregunta", "La puntuació de la pregunta ha de ser major que 0");
	this.puntuacio = puntuacio;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) throws Excepcio {
	if (text == null || "".equals(text.trim()))
	    throw new Excepcio("Pregunta", "Cal indicar un text per la pregunta");
	this.text = text;
    }

    public double getPuntuacio() {
	return puntuacio;
    }

    public void setPuntuacio(double puntuacio) throws Excepcio {
	if (puntuacio <= 0) throw new Excepcio("Pregunta", "La puntuació de la pregunta ha de ser major que 0");
	this.puntuacio = puntuacio;
    }

    public abstract String getEnunciatPregunta(int num);

    protected String crearEnunciatPregunta(int num, String resposta) {
	return WordUtils
		.wrap("Pregunta " + num + ". (" + (new DecimalFormat("#0.0")).format(this.puntuacio) + " pts) "
			+ this.text, Examen.AMPLE_ENUNCIAT, System.lineSeparator(), true)
		+ System.lineSeparator() + resposta;
    }

}
