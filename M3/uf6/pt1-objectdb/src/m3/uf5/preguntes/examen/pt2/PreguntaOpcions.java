package m3.uf5.preguntes.examen.pt2;

import java.util.Arrays;
import java.util.HashSet;

import javax.persistence.Basic;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;

@Entity
public class PreguntaOpcions extends Pregunta {
    @Basic(optional = false)
    private HashSet<String> opcions;

    public PreguntaOpcions(String text, double puntuacio, String[] opcions) throws Excepcio {
	super(text, puntuacio);
	this.opcions = new HashSet<>(Arrays.asList(opcions));
    }

    public HashSet<String> getOpcions() {
	return opcions;
    }

    public void setOpcions(HashSet<String> opcions) {
	this.opcions = opcions;
    }

    @Override
    public String getEnunciatPregunta(int num) {
	String resposta = System.lineSeparator();
	for (String opcio : opcions) {
	    resposta += StringUtils.rightPad("  " + '\u20DE' + "  " + opcio, Examen.AMPLE_ENUNCIAT + 2, ".")
		    + System.lineSeparator();
	}
	// resposta += System.lineSeparator();
	return super.crearEnunciatPregunta(num, resposta);
    }

}
