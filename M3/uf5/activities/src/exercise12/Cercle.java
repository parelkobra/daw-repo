package exercise12;

public class Cercle {

    public static void main(String[] args) {

	int numParametres = args.length;
	double radi = 0;
	switch (numParametres) {
	case 0:
	    System.out.println("Error en els parameters d’entrada");
	    System.out.println("Cal introduir el radi I la opció a realitzar");
	    System.exit(0);
	case 1:
	    System.out.println("Error en els parameters d’entrada");
	    System.out.println("Cal introduir el radi I la opció a realitzar");
	    System.exit(0);
	default:
	    String sRadi = args[0];
	    try {
		radi = Double.parseDouble(sRadi);
	    } catch (NumberFormatException e) {
		System.out.println("El primer argument no es pot transformar a double");
		System.exit(0);
	    }
	    if (radi <= 0) {
		System.out.println("El radi ha de ser positiu");
		System.exit(0);
	    }
	    int queFaig = 0;
	    String sQueFaig = args[1];
	    try {
		queFaig = Integer.parseInt(sQueFaig);
	    } catch (NumberFormatException e) {
		System.out.println("El segon argument no es pot transformar a integer");
		System.exit(0);
	    }
	    if (queFaig > 0 && queFaig < 3) {
		switch (queFaig) {
		case 1:
		    double perimetre = 2.0 * Math.PI * radi;
		    System.out.println("El perimetre és: " + perimetre);
		    break;
		case 2:
		    double area = Math.PI * Math.pow(radi, 2.0);
		    System.out.println("L'àrea és: " + area);
		    break;
		}
	    } else {
		System.out.println("La opció es incorrecte (El segon argument ha de ser entre 0 y 3)");
		System.exit(0);
	    }
	}
    }
}