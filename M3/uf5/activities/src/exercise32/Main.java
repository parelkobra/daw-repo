package exercise32;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "file";
    private static final String FILE_SUCCESS = "STRING WRITTEN SUCCESSFULLY" + System.lineSeparator();
    private static final String INTRODUCE_STRING = "Introduce a string (use \"fi\" as a delimiter)";
    private static final String CURRENT_PATH = "src/exercise32/";
    private static final String WORD_COUNT_MSG_1 = "(Te ";
    private static final String WORD_COUNT_MSG_2 = " paraules)" + System.lineSeparator();
    private static final Boolean APPEND = true;

    public static void main(String[] args) throws IOException {
	File file = new File(CURRENT_PATH + FILE_NAME);
	FileWriter fileWriter = new FileWriter(file, APPEND);
	try {
	    fileWriter.write(getFileContent());
	    fileWriter.close();
	    file.createNewFile();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    System.out.println(FILE_SUCCESS);
	}
    }

    /**
     * Returns line which will be written on the file
     */
    private static String getFileContent() {
	String string = getString();
	string += WORD_COUNT_MSG_1 + getWords(string) + WORD_COUNT_MSG_2;
	return string;
    }

    /**
     * Returns the string introduced by console delimited by the string "fi"
     */
    private static String getString() {
	System.out.println(INTRODUCE_STRING);
	Scanner sc = new Scanner(System.in);
	sc.useDelimiter("fi");
	String string = "";
	string = sc.next();
	sc.close();
	return string;
    }

    /**
     * Turns the array into an array of chars and, for each white space it will
     * count a word. It will take into account if the last character is not a
     * white space
     */
    private static int getWords(String string) {
	int words = 0;
	char[] splittedString = string.toCharArray();
	for (char ch : splittedString) {
	    if (Character.isWhitespace(ch)) {
		words++;
	    }
	}
	if (splittedString[splittedString.length - 1] != ' ') {
	    words++;
	}
	return words;
    }
}
