package customExceptions;

public class ExceptionPropagaNoAuto extends Exception {
    private static final long serialVersionUID = 1L;

    public ExceptionPropagaNoAuto() {
	super("No es propaga automaticament" + System.lineSeparator());
    }
}
