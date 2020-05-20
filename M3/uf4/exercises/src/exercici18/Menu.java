package exercici18;

import java.util.Scanner;

public class Menu {

	private static final String NEW_STRING = "Introduce a string: ";
	private static final String NEW_INTEGERS = "Introduce two integers:\n";
	private static final String OPTION_1 = "1) Show the text length\n";
	private static final String OPTION_2 = "2) Compare with a different text\n";
	private static final String OPTION_3 = "3) Get substring\n";
	private static final String OPTION_4 = "4) Exit";
	private static final String EXIT_MESSAGE = "Bye!";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean exit = false;
		int option;

		System.out.print(NEW_STRING);
		String string = inputString(sc);

		while (!exit) {
			System.out.println(OPTION_1 + OPTION_2 + OPTION_3 + OPTION_4);
			option = sc.nextInt();
			switch (option) {
			case 1:
				getStringLength(string);
				break;
			case 2:
				System.out.println(NEW_STRING);
				sc.nextLine();
				compareStrings(string, inputString(sc));
				break;
			case 3:
				System.out.print(NEW_INTEGERS);
				getSubstring(string, inputIndex(sc), inputIndex(sc));
				break;
			case 4:
				sc.close();
				exit = true;
			}
		}
		System.out.println(EXIT_MESSAGE);
	}

	private static String inputString(Scanner sc) {
		String string = sc.nextLine();
		return string;
	}

	private static int inputIndex(Scanner sc) {
		int index = sc.nextInt();
		return index;
	}

	private static void getStringLength(String string) {
		System.out.println(string.length());
	}

	private static void compareStrings(String string, String string2) {
		System.out.print("The first string and the second one are ");
		if (!string.equalsIgnoreCase(string2)) {
			System.out.print("not ");
		}
		System.out.print("the same\n");
	}

	private static void getSubstring(String string, int beginIndex, int endIndex) {
		System.out.println(string.substring(beginIndex, endIndex));
	}
}