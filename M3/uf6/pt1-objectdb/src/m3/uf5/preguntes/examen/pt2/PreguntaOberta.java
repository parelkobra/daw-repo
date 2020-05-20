package m3.uf5.preguntes.examen.pt2;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;

@Entity
public class PreguntaOberta extends Pregunta {

    public PreguntaOberta(String text, double puntuacio) throws Excepcio {
	super(text, puntuacio);
    }

    @Override
    public String getEnunciatPregunta(int num) {
	return super.crearEnunciatPregunta(num,
		"  " + StringUtils.repeat("_", Examen.AMPLE_ENUNCIAT - 2) + System.lineSeparator()
			+ StringUtils.repeat(" |" + StringUtils.repeat(" ", Examen.AMPLE_ENUNCIAT - 3) + " |"
				+ System.lineSeparator(), 5)
			+ " |" + StringUtils.repeat("_", Examen.AMPLE_ENUNCIAT - 3) + "_|")
		+ System.lineSeparator();
    }

}
