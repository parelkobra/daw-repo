package m3.uf5.ticketing;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import m3.uf5.ticketing.model.Tecnic;

public class NovaIntervencioResult {
    Tecnic tecnic;
    LocalDate dataIntervencio;
    int hores;
    String descripcio;

    public NovaIntervencioResult(Tecnic tecnic, LocalDate dataIntervencio, int hores, String descripcio) {
	this.tecnic = tecnic;
	this.dataIntervencio = dataIntervencio;
	this.hores = hores;
	this.descripcio = descripcio;
    }

    public Tecnic getTecnic() {
	return tecnic;
    }

    public void setTecnic(Tecnic tecnic) {
	this.tecnic = tecnic;
    }

    public Date getData() {
	return Date.from(dataIntervencio.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public void setData(LocalDate dataIntervencio) {
	this.dataIntervencio = dataIntervencio;
    }

    public int getHores() {
	return hores;
    }

    public void setHores(int hores) {
	this.hores = hores;
    }

    public String getDescripcio() {
	return descripcio;
    }

    public void setDescripcio(String descripcio) {
	this.descripcio = descripcio;
    }
}
