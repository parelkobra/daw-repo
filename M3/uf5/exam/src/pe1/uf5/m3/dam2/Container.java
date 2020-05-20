package pe1.uf5.m3.dam2;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Container {
    private static final String EXCEPTION_ID = "L'identificador del contenidor no pot ser nul o tenir una longitud diferent a 11 caràcters";
    private static final String EXCEPTION_ORIGEN = "L'origen del contenidor no pot ser d'origen nul o buit";
    private static final String EXCEPTION_CARREGA = "La carrega del contenidor no pot ser inferior a 2";
    private static final String EXCEPTION_ARRIBADA = "La data d'arribada d'un contenidor no pot ser ni nul·la, ni posterior a la data actual";
    public static final Map<Integer, String> PRIORITAT;
    static {
	HashMap<Integer, String> map = new HashMap<>();
	map.put(1, "CRITICA");
	map.put(2, "URGENT");
	map.put(3, "NORMAL");
	PRIORITAT = Collections.unmodifiableMap(map);
    }

    protected String identificador;
    protected String origen;
    protected int carrega;
    protected LocalDate arribada;

    public Container() {
    }

    public Container(String identificador, String origen, int carrega, LocalDate arribada) throws Exception {
	if (identificador.equals(null) || identificador.length() != 11) throw new Exception(EXCEPTION_ID);
	if (origen.equals(null) || "".equals(origen)) throw new Exception(EXCEPTION_ORIGEN);
	if (carrega < 2) throw new Exception(EXCEPTION_CARREGA);
	if (arribada.equals(null) || arribada.isAfter(LocalDate.now())) throw new Exception(EXCEPTION_ARRIBADA);

	this.identificador = identificador;
	this.origen = origen;
	this.carrega = carrega;
	this.arribada = arribada;
    }

    public String getIdentificador() {
	return identificador;
    }

    public void setIdentificador(String identificador) {
	this.identificador = identificador;
    }

    public String getOrigen() {
	return origen;
    }

    public void setOrigen(String origen) {
	this.origen = origen;
    }

    public int getCarrega() {
	return carrega;
    }

    public void setCarrega(int carrega) {
	this.carrega = carrega;
    }

    public LocalDate getArribada() {
	return arribada;
    }

    public void setArribada(LocalDate arribada) {
	this.arribada = arribada;
    }

    public int diesDesdeArribada() {
	return Period.between(arribada, LocalDate.now()).getDays();
    }

    public int prioritatSortida() {
	return 3;
    }

    public static Map<Integer, String> getPrioritat() {
	return PRIORITAT;
    }

}
