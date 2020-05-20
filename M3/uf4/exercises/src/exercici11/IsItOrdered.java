package exercici11;

import java.util.Scanner;

public class IsItOrdered {

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
		System.out.print("The array is ");
		if (!ordered(array)) {
			System.out.print("not ");
		}
		System.out.print("ordered");
	}

	// TODO Is only ordered one way
	private static boolean ordered(int[] array) {
		int i = 0;
		while (i < ARRAY_LENGTH - 1 && array[i] <= array[i + 1]) {
			i++;
		}
		if (i == ARRAY_LENGTH - 1) {
			return true;
		}
		return false;
	}
}
