package exercise08;

public class Car extends Vehicles {

	boolean classic;

	public Car(String matricula, int km, boolean classic) {
		super(matricula, km);
		this.classic = classic;
	}

	public boolean isClassic() {
		return classic;
	}

	@Override
	public double amortitzacio() {
		if (classic || km > 100000) {
			return 100;
		}
		return km / 1000;
	}

	@Override
	public String toString() {
		String output = "";
		output += this.getClass().getSimpleName() + (isClassic() ? " (Classic)" : "") + System.lineSeparator();
		output += "Matricula: " + this.matricula + System.lineSeparator();
		output += "KM: " + this.km + System.lineSeparator();
		return output;
	}

}
