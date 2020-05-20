package exercise13;

import customExceptions.ExceptionPropagaNoAuto;

public class TestException {
    public static void main(String[] args) throws ExceptionPropagaNoAuto {
	method1();
    }

    private static void method1() throws ExceptionPropagaNoAuto {
	method2();
    }

    private static void method2() throws ExceptionPropagaNoAuto {
	method3();
    }

    private static void method3() throws ExceptionPropagaNoAuto {
	method4();
    }

    private static void method4() throws ExceptionPropagaNoAuto {
	throw new ExceptionPropagaNoAuto();
    }
}
