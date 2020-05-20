package exercise03;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

	private static final String INTRODUCE_DOTS_LENGTH = "How many dots do you want to introduce?";
	private static final String INTRODUCE_X = "x: ";
	private static final String INTRODUCE_Y = "y: ";

	public static void main(String[] args) {
		Set<Dot> dotsSet = new HashSet<Dot>();
		Scanner sc = new Scanner(System.in);
		int length, x, y;

		System.out.println(INTRODUCE_DOTS_LENGTH);
		length = sc.nextInt();
		for (int i = 0; i < length; i++) {
			System.out.print(INTRODUCE_X);
			x = sc.nextInt();
			System.out.print(INTRODUCE_Y);
			y = sc.nextInt();
			System.out.println(System.lineSeparator());

			dotsSet.add(new Dot(x, y));
		}
		sc.close();

		System.out.println("Dots introduced:");
		for (Dot dot : dotsSet) {
			System.out.println(dot);
		}

	}
}
