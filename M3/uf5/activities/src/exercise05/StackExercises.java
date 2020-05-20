package exercise05;

import java.util.LinkedList;

public class StackExercises {

	public static void main(String[] args) {

		LinkedList<String> stack = new LinkedList<String>();
		String[] data1 = { "1", "2", "3", "4", "5", "6" };
		String[] data2 = { "A", "B", "C" };

		// Add to the stack in this order “1”, “2”, “3”, “4”, “5”, “6”
		for (String elem : data1) {
			stack.push(elem);
		}
		printQueue(stack);

		// Take off from the stack 3 elements
		for (int i = 0; i < 3; i++) {
			stack.pop();
		}
		printQueue(stack);

		// Add to the stack in this order "A", "B", "C"
		for (String elem : data2) {
			stack.push(elem);
		}
		printQueue(stack);

		// Take off from the stack 2 elements
		for (int i = 0; i < 2; i++) {
			stack.pop();
		}
		printQueue(stack);

	}

	private static void printQueue(LinkedList<String> stack) {
		for (String elem : stack) {
			System.out.print("[" + elem + "]");
		}
		System.out.println(System.lineSeparator());
	}

}
