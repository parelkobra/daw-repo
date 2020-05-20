package exercici10;

import java.util.Scanner;

public class IsAnyRepeated {

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
		System.out.print("There is ");
		if (!repeats(array)) {
			System.out.print("no ");
		}
		System.out.print("repeated elements in the array");

	}

	// It will check whether each element is repeated or not.
	private static boolean repeats(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i] == array[j]) {
					return true;
				}
			}
		}
		return false;
	}
}
