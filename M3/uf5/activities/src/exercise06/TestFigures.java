package exercise06;

import java.util.Iterator;
import java.util.LinkedList;

public class TestFigures {
    public static void main(String[] args) {
	LinkedList<Figura> figures = new LinkedList<Figura>();
	figures.add(new Triangle(5, 8));
	figures.add(new Recta());
	figures.add(new Quadrat(6));

	// En ordre d'entrada
	Iterator<Figura> iter = figures.iterator();
	printFigures(iter);

	// En ordre invers
	Iterator<Figura> descendingIter = figures.descendingIterator();
	printFigures(descendingIter);
    }

    private static void printFigures(Iterator<Figura> iter) {
	while (iter.hasNext()) {
	    Figura figura = iter.next();
	    printMessage(figura);
	}
	System.out.println(System.lineSeparator());
    }

    private static void printMessage(Figura figura) {
	System.out.println("Numero de costats (" + figura.getClass().getSimpleName() + "): " + figura.numCostats());
    }
}
