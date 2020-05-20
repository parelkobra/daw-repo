package customExceptions;

public class ExceptionPropagaAuto extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ExceptionPropagaAuto() {
	super("Es propaga automaticament" + System.lineSeparator());
    }
}
