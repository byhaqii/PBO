import java.util.LinkedList;
import java.util.Queue;

public class Quese {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();

        // Add elements to the queue
        queue.add("First");
        queue.add("Second");
        queue.add("Third");

        // Display elements
        System.out.println("Queue: " + queue);

        // Remove an element
        String removed = queue.poll();
        System.out.println("Removed: " + removed);

        // Display elements again
        System.out.println("Queue after removal: " + queue);
    }
}
