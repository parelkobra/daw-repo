package exercise33;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VehicleFileReader {
    private static final String COMMENT = "#";
    private static final String CURRENT_PATH = "src" + File.separator + "exercise33" + File.separator;
    private static final String FILE_NAME = "vehicle_data";

    public static void main(String[] args) throws IOException {
	File file = new File(CURRENT_PATH + FILE_NAME);
	List<Vehicle> vehicleList = getVehicleList(file);
	printList(vehicleList);
    }

    private static List<Vehicle> getVehicleList(File file) throws IOException {
	BufferedReader reader = new BufferedReader(new FileReader(file));
	List<String> fileLines = getFileLines(reader);
	List<String[]> vehicleFields = getUntrimmedFields(fileLines);
	List<Vehicle> vehicleList = addVehicles(vehicleFields);
	reader.close();
	return vehicleList;
    }

    private static List<String> getFileLines(BufferedReader reader) throws IOException {
	List<String> fileLines = new ArrayList<>();
	String line = "";
	while ((line = reader.readLine()) != null) {
	    if (!line.startsWith(COMMENT)) {
		fileLines.add(line);
	    }
	}
	return fileLines;
    }

    private static List<String[]> getUntrimmedFields(List<String> fileLines) {
	List<String[]> untrimmedFields = new ArrayList<>();
	String[] line = new String[4];
	for (String string : fileLines) {
	    line = string.split(";");
	    untrimmedFields.add(line);
	}
	return untrimmedFields;
    }

    private static List<Vehicle> addVehicles(List<String[]> vehicleFields) {
	List<Vehicle> vehicles = new ArrayList<>();
	for (String[] fields : vehicleFields) {
	    if (isCar(fields)) {
		vehicles.add(new Car(getPlateId(fields), getKm(fields), isClasic(fields)));
	    } else if (isMoto(fields)) {
		vehicles.add(new Motorcycle(getPlateId(fields), getKm(fields), getCc(fields)));
	    }
	}
	return vehicles;
    }

    private static String getPlateId(String[] field) {
	return field[1].trim();
    }

    private static int getKm(String[] field) {
	return Integer.parseInt(field[2].trim());
    }

    private static boolean isClasic(String[] field) {
	return Boolean.parseBoolean(field[3].trim());
    }

    private static int getCc(String[] fields) {
	return Integer.parseInt(fields[3].trim());
    }

    private static boolean isCar(String[] field) {
	return "cotxe".equalsIgnoreCase(field[0]);
    }

    private static boolean isMoto(String[] field) {
	return "moto".equalsIgnoreCase(field[0]);
    }

    private static void printList(List<Vehicle> vehicleList) {
	for (Vehicle vehicle : vehicleList) {
	    System.out.println(vehicle);
	}
    }

}
