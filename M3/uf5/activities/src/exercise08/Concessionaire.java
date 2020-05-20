package exercise08;

import java.util.TreeMap;

public class Concessionaire {

	private static final int MAX_VEHICLES = 10;

	private TreeMap<Vehicles, String> vehiclesMap;

	public Concessionaire() {
		vehiclesMap = new TreeMap<Vehicles, String>();
	}

	public void addVehicle(Vehicles vehicle, String address) {
		if (vehiclesMap.size() < MAX_VEHICLES) {
			vehiclesMap.put(vehicle, address);
		} else {
			System.out.println("Error: Concessionaire's capacity is full");
		}
	}

	public void removeVehicle(Vehicles vehicle) {
		vehiclesMap.remove(vehicle);
	}

	public TreeMap<Vehicles, String> getAllVehicles() {
		return vehiclesMap;
	}

	public double amortizationAvg() {
		double total = 0;
		for (Vehicles vehicle : vehiclesMap.keySet()) {
			total += vehicle.amortitzacio();
		}
		return total / vehiclesMap.size();
	}

}
