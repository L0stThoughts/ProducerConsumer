package consumer;

import java.util.concurrent.BlockingQueue;

public class ConsumerUtils {

    /**
     * This method removes items from the buffer with error handling for empty buffers.
     * @param queue The queue representing the buffer.
     * @param timeout The timeout period for waiting for an item.
     * @return The item that was removed, or null if no item was available within the timeout.
     */
    public static Integer removeItemFromBuffer(BlockingQueue<Integer> queue, long timeout) {
        try {
            return queue.poll(timeout, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /**
     * Starts a consumer thread to remove items from the buffer.
     * @param queue The buffer from which items will be removed.
     */
    public static void startConsumer(BlockingQueue<Integer> queue) {
        Thread consumerThread = new Thread(() -> {
            while (true) {
                Integer item = removeItemFromBuffer(queue, 1000);
                if (item != null) {
                    System.out.println("Item " + item + " removed from buffer.");
                } else {
                    System.out.println("No item available to consume.");
                }
            }
        });
        consumerThread.start();
    }

    /**
     * Generates a consumer that removes items based on the provided conditions.
     * @param queue The buffer from which items will be consumed.
     * @param itemsToConsume The number of items the consumer should consume.
     */
    public static void consumeItems(BlockingQueue<Integer> queue, int itemsToConsume) {
        for (int i = 0; i < itemsToConsume; i++) {
            Integer item = removeItemFromBuffer(queue, 1000);
            if (item != null) {
                System.out.println("Consumed item: " + item);
            } else {
                System.out.println("Failed to consume item, buffer may be empty.");
            }
        }
    }

    /**
     * Prints the current size of the buffer.
     * @param queue The buffer (queue) to check.
     */
    public static void printBufferSize(BlockingQueue<Integer> queue) {
        System.out.println("Current buffer size: " + queue.size());
    }
}
