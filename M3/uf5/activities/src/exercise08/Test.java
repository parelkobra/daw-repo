package exercise08;

import org.apache.commons.lang3.RandomStringUtils;

public class Test {

	public static void main(String[] args) {

		Car c1 = new Car(RandomStringUtils.randomAlphanumeric(6).toUpperCase(),
				Integer.parseInt(RandomStringUtils.randomNumeric(3, 6)), true);
		Car c2 = new Car(RandomStringUtils.randomAlphanumeric(6).toUpperCase(),
				Integer.parseInt(RandomStringUtils.randomNumeric(3, 6)), false);

		Motorcycle m1 = new Motorcycle(RandomStringUtils.randomAlphabetic(6).toUpperCase(),
				Integer.parseInt(RandomStringUtils.randomNumeric(3, 6)), 125);
		Motorcycle m2 = new Motorcycle(RandomStringUtils.randomAlphabetic(6).toUpperCase(),
				Integer.parseInt(RandomStringUtils.randomNumeric(3, 6)), 250);
		Motorcycle m3 = new Motorcycle(RandomStringUtils.randomAlphabetic(6).toUpperCase(),
				Integer.parseInt(RandomStringUtils.randomNumeric(3, 6)), 500);

		Concessionaire concs1 = new Concessionaire();

		// Adding and removing vehicles from the concessionaire
		concs1.addVehicle(c1, "Calle Falsa 123");
		concs1.addVehicle(c1, "Calle Falsa 456");
		concs1.addVehicle(c2, "Calle Verde 321");
		concs1.addVehicle(m1, "Calle Naranja 432");
		concs1.addVehicle(m2, "Calle Azul 172");
		concs1.addVehicle(m3, "Calle Rapida 222");

		concs1.removeVehicle(m3);

		System.out.println(concs1.getAllVehicles().toString() + System.lineSeparator());

		// Gets the amount of cars in the concessionaire
		System.out.println(
				"Concessionaire's amount of vehicles : " + concs1.getAllVehicles().size() + System.lineSeparator());

		// Cars amortization
		System.out.println(c1.toString());
		System.out.println("Amortització: " + c1.amortitzacio() + System.lineSeparator());
		System.out.println(c2.toString());
		System.out.println("Amortització: " + c2.amortitzacio() + System.lineSeparator());

		// Motorcycles amortization
		System.out.println(m1.toString());
		System.out.println("Amortització: " + m1.amortitzacio() + System.lineSeparator());
		System.out.println(m2.toString());
		System.out.println("Amortització: " + m2.amortitzacio() + System.lineSeparator());

		// Average amortization
		System.out.println("Amortització mitjana: " + concs1.amortizationAvg());

	}

}
