package producer;

import java.util.concurrent.BlockingQueue;

public class ProducerUtils {

    /**
     * This method adds items to a buffer with error handling for full buffers.
     * @param queue The queue representing the buffer.
     * @param item The item to add to the buffer.
     * @param timeout The timeout period for adding the item.
     * @return true if item was successfully added, false otherwise.
     */
    public static boolean addItemToBuffer(BlockingQueue<Integer> queue, Integer item, long timeout) {
        try {
            return queue.offer(item, timeout, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * Starts a producer thread to add items to the buffer.
     * @param queue The buffer where items will be added.
     * @param items The items to be added to the buffer.
     */
    public static void startProducer(BlockingQueue<Integer> queue, Integer[] items) {
        Thread producerThread = new Thread(() -> {
            for (Integer item : items) {
                if (addItemToBuffer(queue, item, 1000)) {
                    System.out.println("Item " + item + " added to buffer.");
                } else {
                    System.out.println("Failed to add item " + item + " to buffer.");
                }
            }
        });
        producerThread.start();
    }

    /**
     * Generates random items for the producer to add to the buffer.
     * @param size The number of items to generate.
     * @return An array of randomly generated items.
     */
    public static Integer[] generateRandomItems(int size) {
        Integer[] items = new Integer[size];
        for (int i = 0; i < size; i++) {
            items[i] = (int) (Math.random() * 100);
        }
        return items;
    }

    /**
     * Prints the current size of the buffer.
     * @param queue The buffer (queue) to check.
     */
    public static void printBufferSize(BlockingQueue<Integer> queue) {
        System.out.println("Current buffer size: " + queue.size());
    }
}
