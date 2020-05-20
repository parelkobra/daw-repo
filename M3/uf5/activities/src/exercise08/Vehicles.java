package exercise08;

public abstract class Vehicles implements Comparable<Vehicles> {

    protected String matricula;
    protected int km;

    public Vehicles(String matricula, int km) {
	this.matricula = matricula;
	this.km = km;
    }

    public String getMatricula() {
	return matricula;
    }

    public int getKm() {
	return km;
    }

    public abstract double amortitzacio();

    @Override
    public abstract String toString();

    public int compareTo(Vehicles o) {
	if (o.getMatricula() == this.getMatricula())
	    return 0;
	if (o.getKm() > this.getKm())
	    return 1;
	return -1;
    }

}