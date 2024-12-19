package buffer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ConfigurableBuffer is a thread-safe bounded buffer implementation.
 * It allows producers to add items and consumers to remove items while adhering to the buffer size limits.
 */
public class ConfigurableBuffer {
    private final Queue<Integer> buffer;
    private final int maxSize;

    /**
     * Constructs a ConfigurableBuffer with a specified maximum size.
     *
     * @param maxSize the maximum number of items the buffer can hold.
     * @throws IllegalArgumentException if maxSize is less than 1.
     */
    public ConfigurableBuffer(int maxSize) {
        if (maxSize < 1) {
            throw new IllegalArgumentException("Buffer size must be at least 1.");
        }
        this.maxSize = maxSize;
        this.buffer = new LinkedList<>();
    }

    /**
     * Adds an item to the buffer.
     * Blocks if the buffer is full until space becomes available.
     *
     * @param item the item to add to the buffer.
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    public synchronized void addItem(int item) throws InterruptedException {
        while (buffer.size() == maxSize) {
            System.out.println("Buffer is full. Producer is waiting...");
            wait(); // Wait until space is available
        }
        buffer.add(item);
        System.out.println("Item added: " + item + " | Buffer size: " + buffer.size());
        notifyAll(); // Notify consumers that an item is available
    }

    /**
     * Removes an item from the buffer.
     * Blocks if the buffer is empty until an item becomes available.
     *
     * @return the item removed from the buffer.
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    public synchronized int removeItem() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.println("Buffer is empty. Consumer is waiting...");
            wait(); // Wait until an item is available
        }
        int item = buffer.poll();
        System.out.println("Item removed: " + item + " | Buffer size: " + buffer.size());
        notifyAll(); // Notify producers that space is available
        return item;
    }

    /**
     * Gets the current size of the buffer.
     *
     * @return the number of items currently in the buffer.
     */
    public synchronized int getSize() {
        return buffer.size();
    }

    /**
     * Checks if the buffer is full.
     *
     * @return true if the buffer is full, false otherwise.
     */
    public synchronized boolean isFull() {
        return buffer.size() == maxSize;
    }

    /**
     * Checks if the buffer is empty.
     *
     * @return true if the buffer is empty, false otherwise.
     */
    public synchronized boolean isEmpty() {
        return buffer.isEmpty();
    }
}
