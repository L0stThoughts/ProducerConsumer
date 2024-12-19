package producer;

import buffer.ConfigurableBuffer;

/**
 * The Producer class produces integer items and adds them to the buffer.
 * It sleeps for a random amount of time after adding each item to simulate work.
 */
public class Producer implements Runnable {
    private final ConfigurableBuffer buffer;
    private final String name;
    private final int sleepTime;

    /**
     * Constructor for the Producer class.
     *
     * @param buffer the buffer shared by all producers and consumers.
     * @param name the name of the producer (used for logging).
     * @param sleepTime the time (in milliseconds) the producer will sleep after producing an item.
     */
    public Producer(ConfigurableBuffer buffer, String name, int sleepTime) {
        this.buffer = buffer;
        this.name = name;
        this.sleepTime = sleepTime;
    }

    /**
     * The run method for the producer thread.
     * It produces items and adds them to the buffer until the program is stopped.
     */
    @Override
    public void run() {
        try {
            while (true) {
                // Simulate producing an item (just a random number)
                int item = (int) (Math.random() * 100);
                System.out.println(name + " is producing item: " + item);

                // Add the item to the buffer
                buffer.addItem(item);

                // Sleep for the configured time to simulate work
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException e) {
            System.out.println(name + " was interrupted while producing.");
        }
    }
}
