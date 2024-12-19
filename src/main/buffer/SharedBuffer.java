package buffer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A thread-safe shared buffer used in producer-consumer scenarios.
 * This class provides methods to add and remove items from the buffer
 * while ensuring proper synchronization between threads.
 */
public class SharedBuffer {
    
    private final Queue<Integer> buffer;  // The buffer to hold items
    private final int capacity;  // Maximum capacity of the buffer

    /**
     * Constructs a new SharedBuffer with the specified capacity.
     *
     * @param capacity The maximum number of items the buffer can hold.
     */
    public SharedBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
    }

    /**
     * Adds an item to the buffer. If the buffer is full, the producer will wait
     * until space becomes available.
     *
     * @param item The item to add to the buffer.
     * @throws InterruptedException if the thread is interrupted while waiting or performing operations.
     */
    public synchronized void addItem(int item) throws InterruptedException {
        while (buffer.size() == capacity) {
            wait(); // Wait until there is space in the buffer
        }
        buffer.offer(item);
        notifyAll(); // Notify consumers that an item is available
    }

    /**
     * Removes and returns an item from the buffer. If the buffer is empty,
     * the consumer will wait until there is an item to consume.
     *
     * @return The item removed from the buffer.
     * @throws InterruptedException if the thread is interrupted while waiting or performing operations.
     */
    public synchronized int removeItem() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait(); // Wait until there is an item to consume
        }
        int item = buffer.poll();
        notifyAll(); // Notify producers that space is available
        return item;
    }

    /**
     * Returns the current size of the buffer.
     *
     * @return The number of items currently in the buffer.
     */
    public synchronized int size() {
        return buffer.size();
    }

    /**
     * Checks if the buffer is empty.
     *
     * @return true if the buffer is empty, otherwise false.
     */
    public synchronized boolean isEmpty() {
        return buffer.isEmpty();
    }
}
