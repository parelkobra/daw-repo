package exercise33;

public class Car extends Vehicle {
    private static final String TYPE_NAME = "Cotxe";
    private boolean clasic;

    public Car(String plateId, int km, boolean clasic) {
	super(plateId, km);
	this.clasic = clasic;
    }

    public Car(String plateId, int km) {
	super(plateId, km);
	this.clasic = false;
    }

    public boolean isClasic() {
	return clasic;
    }

    private static String classicOutput(Boolean classic) {
	return classic ? "Classic" : "Not classic";
    }

    @Override
    public String toString() {
	return TYPE_NAME + DASH + getPlateId() + DASH + getKm() + " km" + DASH + classicOutput(this.clasic);
    }
}
