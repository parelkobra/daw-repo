package exercise14;

public class Triangle extends Figura {

	private int alcada;
	private int base;

	public Triangle(int alcada, int base) {
		this.alcada = alcada;
		this.base = base;
	}

	// TODO impl
	@Override
	public int numCostats() {
		return 3;
	}

	// TODO impl
	public int area() {
		return (base * alcada) / 2;
	}

}
