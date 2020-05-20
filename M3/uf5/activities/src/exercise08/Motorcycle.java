package exercise08;

public class Motorcycle extends Vehicles {

	private int cilindrada;

	public Motorcycle(String matricula, int km, int cilindrada) {
		super(matricula, km);
		this.cilindrada = cilindrada;
	}

	@Override
	public double amortitzacio() {
		if (cilindrada <= 125) {
			if (km > 50000) {
				return 100;
			}
			return km / 500;
		}
		if (km > 100000) {
			return 100;
		}
		return km / 1000;
	}

	@Override
	public String toString() {
		String output = "";
		output += this.getClass().getSimpleName() + " (" + this.cilindrada + " cc)" + System.lineSeparator();
		output += "Matricula: " + this.matricula + System.lineSeparator();
		output += "KM: " + this.km + System.lineSeparator();
		return output;
	}

}
