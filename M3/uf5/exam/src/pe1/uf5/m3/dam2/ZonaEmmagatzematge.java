package pe1.uf5.m3.dam2;

import java.util.TreeSet;

public class ZonaEmmagatzematge {
    private TreeSet<Ubicacio> ubicacions;

    public ZonaEmmagatzematge() {
    }

    public ZonaEmmagatzematge(int files, int columnes) throws Exception {
	ubicacions = new TreeSet<>();
	for (int fila = 0; fila < files; fila++) {
	    for (int columna = 0; columna < columnes; columna++) {
		ubicacions.add(new Ubicacio(fila, columna));
	    }
	}
    }

    public TreeSet<Ubicacio> getUbicacions() {
	return ubicacions;
    }

    public void setUbicacions(TreeSet<Ubicacio> ubicacions) {
	this.ubicacions = ubicacions;
    }

    public void ubicar(Container container) throws Exception {
	for (Ubicacio ubicacio : ubicacions) {
	    if (ubicacio.disponible(container) && !ubicacio.cercar(container.getIdentificador())) {
		ubicacio.afegir(container);
		return;
	    }
	}
	throw new Exception("No es pot ubica el container amb id: " + container.getIdentificador()
		+ " per qué no hi ha cap espai disponible");
    }

    public Container retirar(String identificador) throws Exception {
	Ubicacio ubicacio = findUbicacio(identificador);
	if (ubicacio == null) throw new Exception("No s'ha trobat cap contenidor amb id: " + identificador);

	while (!identificador.equals(ubicacio.superior().getIdentificador())) {
	    ubicar(ubicacio.superior());
	    ubicacio.treure();
	}
	if (identificador.equals(ubicacio.superior().getIdentificador())) return ubicacio.treure();
	return null;
    }

    private Ubicacio findUbicacio(String containerId) {
	Ubicacio ubicacio = null;
	for (Ubicacio u : ubicacions) {
	    if (u.cercar(containerId)) {
		ubicacio = u;
		break;
	    }
	}
	return ubicacio;
    }

    public int totalContenidors() {
	int total = 0;
	for (Ubicacio ubicacio : ubicacions) {
	    total += ubicacio.totalContenidors();
	}
	return total;
    }

}
