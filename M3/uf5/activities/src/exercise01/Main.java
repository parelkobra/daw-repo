package exercise01;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

public class Main {

	public static void main(String[] args) {

		String[] dades = { "hola", "adeu", "un dos tres", "tot tot tothom" };
		String[] mesdades = { "1", "12", "123" };

		// Crea un Vector de String a partir del array 'dades'
		Vector<String> vector = new Vector<>(Arrays.asList(dades));

		// Afegeix el array 'mesdades' totes de cop (Veure classe Arrays)
		vector.addAll(Arrays.asList(mesdades));

		// Afegeix el text "olele olala" a la posici� 2
		vector.add(1, "olele olala");

		// Mostra: La mida del vector
		System.out.println("Mida del vector: " + vector.size());

		// Mostra: Si cont� l'element "adeu"
		System.out.println("Cont� l'element \"adeu\"?: " + vector.contains("adeu"));

		// Mostra: Si cont� l'element "not found"
		System.out.println("Cont� l'element \"not found\"?: " + vector.contains("not found"));

		// Mostra: L'element de la posici� 5
		System.out.println("Element de la posici� 5: " + vector.get(5));

		System.out.println(System.lineSeparator() + vector + System.lineSeparator());

		// Esborra l'element de la posici� 0
		vector.remove(0);

		// Esborra "hola"
		vector.remove("hola");

		// Converteix els elements de la posici� 3 fins al final en una llista
		// enlla�ada (LinkedList)
		LinkedList<String> linkedList = new LinkedList<String>(vector.subList(3, vector.size()));

		// Mostra el contingut de la llista enll�ada
		System.out.println(linkedList);
	}

}
