package exercise11;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
	System.out.print("Introduce a number: ");
	System.out.print(readInt());
    }

    public static int readInt() {
	Scanner sc = new Scanner(System.in);
	int num;
	try {
	    num = sc.nextInt();
	} catch (InputMismatchException e) {
	    sc.close();
	    return 0;
	}
	sc.close();
	return num;
    }

}
