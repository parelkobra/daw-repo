package exercise14;

import customExceptions.ExceptionPropagaAuto;

public class Cercle extends Figura {

    @Override
    public int numCostats() {
	throw new ExceptionPropagaAuto();
    }

}
