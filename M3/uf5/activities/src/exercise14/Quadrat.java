package exercise14;

public class Quadrat extends Figura {

	private int costat;

	public Quadrat(int costat) {
		this.costat = costat;
	}

	@Override
	public int numCostats() {
		return 4;
	}

	public int area() {
		return costat * costat;
	}

}
