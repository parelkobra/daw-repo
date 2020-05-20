package exercici09;

import java.util.Scanner;

public class ElementsSum {

	private static final int ARRAY_LENGTH = 2;

	public static void main(String[] args) {

		// We create an array to store 2 integers and a Scanner to get them
		// from the user
		int[] array = new int[ARRAY_LENGTH];
		Scanner sc = new Scanner(System.in);

		System.out.print("Introduce " + ARRAY_LENGTH + " numbers\n");
		for (int i = 0; i < ARRAY_LENGTH; i++) {
			System.out.print("> ");
			array[i] = sc.nextInt();
			System.out.print("\n");
		}
		sc.close();

		System.out.println("The sum of both numbers is " + sum(array));
	}

	// This method will sum the integer in the position 0 and 1
	private static int sum(int[] array) {
		return array[0] + array[1];
	}
}
