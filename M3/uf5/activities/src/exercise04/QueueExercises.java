package exercise04;

import java.util.Arrays;
import java.util.LinkedList;

public class QueueExercises {

	public static void main(String[] args) {

		LinkedList<String> queue = new LinkedList<String>();

		// Add to the queue in this order "1", "2", "3", "4", "5", "6"
		queue.addAll(Arrays.asList("1", "2", "3", "4", "5", "6"));
		printQueue(queue);

		// Take off from the queue 3 elements
		for (int i = 0; i < 3; i++) {
			queue.remove();
		}
		printQueue(queue);

		// Add to the queue in this order "A", "B", "C"
		queue.addAll(Arrays.asList("A", "B", "C"));
		printQueue(queue);

		// Take of from the queue 2 elements
		for (int i = 0; i < 2; i++) {
			queue.remove();
		}
		printQueue(queue);

	}

	private static void printQueue(LinkedList<String> queue) {
		for (String elem : queue) {
			System.out.print("[" + elem + "]");
		}
		System.out.println(System.lineSeparator());
	}

}
