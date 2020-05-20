package exercise33;

public class Motorcycle extends Vehicle {
    private static final String TYPE_NAME = "Moto";
    private int cc;

    public Motorcycle(String plateId, int km, int cc) {
	super(plateId, km);
	this.cc = cc;
    }

    public Motorcycle(String plateId, int km) {
	super(plateId, km);
	this.cc = 125;
    }

    public int getCc() {
	return cc;
    }

    @Override
    public String toString() {
	return TYPE_NAME + DASH + getPlateId() + DASH + getKm() + " km" + DASH + getCc() + "cc";
    }

}
