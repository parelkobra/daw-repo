package exercici16;

public class AreaCircle {

	private static final double PI = Math.PI;

	public static void main(String[] args) {

		double radius = Double.parseDouble(args[0]);
		double area = PI * Math.pow(radius, 2);

		System.out.println("The area of a cercle with a radius of " + radius + " is " + area);

	}

}
