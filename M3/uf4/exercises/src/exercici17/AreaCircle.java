package exercici17;

import java.util.Scanner;

public class AreaCircle {

	private static final double PI = Math.PI;

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.print("Introduce the radius of the circle: ");
		double radius = sc.nextDouble();
		sc.close();
		double area = PI * Math.pow(radius, 2);

		System.out.println("The area of the circle with a radius of " + radius + " is " + area);
	}

}
