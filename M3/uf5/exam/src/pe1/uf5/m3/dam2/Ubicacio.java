package pe1.uf5.m3.dam2;

import java.util.LinkedList;

public class Ubicacio implements Comparable<Ubicacio> {
    private static final String EXCEPTION_COORDENADES = "Les coordenades de l'ubicació no poden ser negatives";
    public static final int CAPACITAT = 3;
    public static final int LIMIT_TONATGE = 30;
    private int fila;
    private int columna;
    private LinkedList<Container> containers;

    public Ubicacio() {
    }

    public Ubicacio(int fila, int columna) throws Exception {
	if (fila < 0 || columna < 0) throw new Exception(EXCEPTION_COORDENADES);
	this.fila = fila;
	this.columna = columna;
	containers = new LinkedList<>();
    }

    public int getFila() {
	return fila;
    }

    public void setFila(int fila) {
	this.fila = fila;
    }

    public int getColumna() {
	return columna;
    }

    public void setColumna(int columna) {
	this.columna = columna;
    }

    public LinkedList<Container> getContainers() {
	return containers;
    }

    public void setContainers(LinkedList<Container> containers) {
	this.containers = containers;
    }

    public boolean disponible(Container container) {
	return containers.size() >= CAPACITAT || containersWeight() + container.getCarrega() > LIMIT_TONATGE ? false
		: true;
    }

    public void afegir(Container container) {
	containers.addFirst(container);
    }

    public Container treure() {
	return containers.removeFirst();
    }

    public boolean cercar(String identificador) {
	for (Container container : containers) {
	    if (container.getIdentificador().equalsIgnoreCase(identificador)) return true;
	}
	return false;
    }

    public Container superior() {
	return containers.peekFirst();
    }

    public int totalContenidors() {
	return containers.size();
    }

    private int containersWeight() {
	int total = 0;
	for (Container container : containers) {
	    total += container.getCarrega();
	}
	return total;
    }

    @Override
    public int compareTo(Ubicacio o) {
	if (this.fila > o.fila) return 1;
	if (this.fila < o.fila) return -1;
	if (this.fila == o.fila && this.columna == o.columna)
	    return 0;
	else {
	    if (this.columna > o.columna)
		return 1;
	    else
		return -1;
	}
    }

}
