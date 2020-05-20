package pe1.uf5.m3.dam2;

import java.time.LocalDate;

public class Refrigerat extends Container {
    private static final String EXCEPTION_MAXIM = "El nombre màxim de dies dels contenidors refrigerats no pot ser negatiu";
    private int maxim;

    public Refrigerat() {
    }

    public Refrigerat(String identificador, String origen, int carrega, LocalDate arribada, int maxim)
	    throws Exception {
	super(identificador, origen, carrega, arribada);
	if (maxim < 0) throw new Exception(EXCEPTION_MAXIM);
	this.maxim = maxim;
    }

    public int getMaxim() {
	return maxim;
    }

    public void setMaxim(int maxim) {
	this.maxim = maxim;
    }

    @Override
    public int prioritatSortida() {
	return super.diesDesdeArribada() > maxim ? 1 : 2;
    }

}
