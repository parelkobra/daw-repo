package exercici15;

public class Exchange {

	public static void main(String[] args) {

		int a = 15;
		int b = 3;

		System.out.println("Initial values:\nA: " + a + "\tB: " + b);
		exchange(a, b);
		System.out.println("Current values:\nA: " + a + "\tB: " + b);
	}

	private static void exchange(int a, int b) {
		int aux = a;
		a = b;
		b = aux;
	}

	// We can't exchange the values because the method get's a copy of the
	// original variable, so any change we do won't affect the original value.

}
