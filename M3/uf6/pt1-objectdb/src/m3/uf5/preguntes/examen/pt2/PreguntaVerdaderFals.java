package m3.uf5.preguntes.examen.pt2;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;

@Entity
public class PreguntaVerdaderFals extends Pregunta {

    public PreguntaVerdaderFals(String text, double puntuacio) throws Excepcio {
	super(text, puntuacio);
    }

    @Override
    public String getEnunciatPregunta(int num) {
	return super.crearEnunciatPregunta(num, System.lineSeparator()
		+ StringUtils.leftPad('\u20DE' + "  Cert      " + '\u20DE' + "  Fals", Examen.AMPLE_ENUNCIAT + 2)
		+ StringUtils.repeat(System.lineSeparator(), 2));
    }

}
