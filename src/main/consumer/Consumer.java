package consumer;

import buffer.ConfigurableBuffer;

/**
 * The Consumer class consumes integer items from the buffer.
 * It sleeps for a random amount of time after consuming each item to simulate work.
 */
public class Consumer implements Runnable {
    private final ConfigurableBuffer buffer;
    private final String name;
    private final int sleepTime;

    /**
     * Constructor for the Consumer class.
     *
     * @param buffer the buffer shared by all producers and consumers.
     * @param name the name of the consumer (used for logging).
     * @param sleepTime the time (in milliseconds) the consumer will sleep after consuming an item.
     */
    public Consumer(ConfigurableBuffer buffer, String name, int sleepTime) {
        this.buffer = buffer;
        this.name = name;
        this.sleepTime = sleepTime;
    }

    /**
     * The run method for the consumer thread.
     * It consumes items from the buffer until the program is stopped.
     */
    @Override
    public void run() {
        try {
            while (true) {
                // Remove an item from the buffer
                int item = buffer.removeItem();

                // Simulate consuming the item
                System.out.println(name + " consumed item: " + item);

                // Sleep for the configured time to simulate work
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException e) {
            System.out.println(name + " was interrupted while consuming.");
        }
    }
}
