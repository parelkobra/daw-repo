package exercise15;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Sum {
    public static void main(String[] args) {
	boolean exit = false;
	int a = 0;
	int b = 0;
	while (!exit) {
	    try {
		System.out.println("Introduce 2 numbers to sum:");
		a = readInt();
		b = readInt();
		exit = true;
	    } catch (InputMismatchException e) {
		System.out.println("Error: not an integer");
	    }
	}
	System.out.println("Result: " + sum(a, b));
    }

    private static int readInt() {
	@SuppressWarnings("resource")
	Scanner sc = new Scanner(System.in);
	System.out.print(": ");
	int num = sc.nextInt();
	return num;
    }

    private static int sum(int a, int b) {
	return a + b;
    }
}
