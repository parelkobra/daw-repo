package m3.uf5.preguntes.examen.pt2;

import javax.jdo.annotations.Index;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Index(members = { "nota" })
public class Lliurament {
    public static final double NOTA_MAX = 10;
    private double nota;

    @Basic(optional = false)
    @OneToOne
    private Estudiant estudiant;

    @ManyToOne
    private Examen examen;

    public Lliurament(Estudiant estudiant, Examen examen) throws Excepcio {
	if (estudiant == null) throw new Excepcio("Lliurament", "Cal indicar l'estudiant per crear el lliurament");
	this.estudiant = estudiant;
	if (examen == null) throw new Excepcio("Lliurament", "Cal indicar l'examen per crear el lliurament");
	this.examen = examen;
	this.nota = 0;
    }

    public double getNota() {
	return nota;
    }

    public void setNota(double nota) throws Excepcio {
	if (nota < 0 || nota > NOTA_MAX)
	    throw new Excepcio("Lliurament", "La nota no està normalitzada sobre " + NOTA_MAX);
	this.nota = nota;
    }

    public Estudiant getEstudiant() {
	return estudiant;
    }

    public void setEstudiant(Estudiant estudiant) throws Excepcio {
	if (estudiant == null) throw new Excepcio("Lliurament", "Cal indicar l'estudiant per crear el lliurament");
	this.estudiant = estudiant;
    }

    public Examen getExamen() {
	return examen;
    }

    public void setExamen(Examen examen) throws Excepcio {
	if (examen == null) throw new Excepcio("Lliurament", "Cal indicar l'examen per crear el lliurament");
	this.examen = examen;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) return false;
	Lliurament other = (Lliurament) obj;
	return this.estudiant.compareTo(other.getEstudiant()) == 0;
    }

}
