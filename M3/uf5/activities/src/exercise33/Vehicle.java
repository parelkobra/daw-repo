package exercise33;

public abstract class Vehicle {
    public static final String DASH = " - ";

    protected String plateId;
    protected int km;

    public Vehicle(String plateId, int km) {
	this.plateId = plateId;
	this.km = km;
    }

    public String getPlateId() {
	return plateId;
    }

    public int getKm() {
	return km;
    }

    @Override
    public abstract String toString();
}
