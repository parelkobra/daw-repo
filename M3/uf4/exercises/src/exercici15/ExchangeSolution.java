package exercici15;

public class ExchangeSolution {

	public static void main(String[] args) {

		int a = 15;
		int b = 3;
		int[] array = { a, b };

		System.out.println("Initial values:\nA: " + array[0] + "\tB: " + array[1]);
		exchange(array);
		System.out.println("Current values:\nA: " + array[0] + "\tB: " + array[1]);
	}

	private static void exchange(int[] array) {
		int aux = array[0];
		array[0] = array[1];
		array[1] = aux;
	}

}
