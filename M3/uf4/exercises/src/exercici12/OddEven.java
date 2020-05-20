package exercici12;

import java.util.Scanner;

public class OddEven {

	private static final int ARRAY_LENGTH = 10;

	public static void main(String[] args) {

		// Input
		int[] array = new int[ARRAY_LENGTH];
		Scanner sc = new Scanner(System.in);

		System.out.print("Introduce " + ARRAY_LENGTH + " numbers\n");
		for (int i = 0; i < ARRAY_LENGTH; i++) {
			System.out.print("> ");
			array[i] = sc.nextInt();
		}
		sc.close();

		// Output
		System.out.println("Odds: " + odds(array));
		System.out.println("Evens: " + evens(array));
	}

	// It will return the amount of odd numbers
	private static int odds(int[] array) {
		int res = 0;
		for (int i = 0; i < ARRAY_LENGTH; i++) {
			if (array[i] % 2 != 0) {
				res++;
			}
		}
		return res;
	}

	// It will return the amount of even numbers
	private static int evens(int[] array) {
		int res = 0;
		for (int i = 0; i < ARRAY_LENGTH; i++) {
			if (array[i] % 2 == 0) {
				res++;
			}
		}
		return res;
	}
}
