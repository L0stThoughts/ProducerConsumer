package test;

import buffer.SharedBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Tests for verifying the behavior of the producer in a shared buffer.
 */
public class ProducerTest {

    private SharedBuffer buffer;
    private ExecutorService executor;

    /**
     * Initializes the shared buffer and executor before each test.
     */
    public void setUp() {
        buffer = new SharedBuffer(5);
        executor = Executors.newFixedThreadPool(1);
    }

    /**
     * Shuts down the executor after the test.
     */
    public void tearDown() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    /**
     * Verifies that the producer can add an item to the buffer and that the item can be removed correctly.
     * 
     * @throws InterruptedException if the thread is interrupted during the test.
     */
    public void testProducerAddsItem() throws InterruptedException {
        executor.execute(() -> {
            try {
                buffer.addItem(10);
                int item = buffer.removeItem();
                if (item == 10) {
                    System.out.println("Test Passed: Producer added and consumed the correct item.");
                } else {
                    System.err.println("Test Failed: Item mismatch.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        executor.shutdown();
        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
            System.err.println("Test Failed: Timeout before completion.");
        }
    }

    /**
     * Verifies that the producer waits when the buffer is full.
     * 
     * @throws InterruptedException if the thread is interrupted during the test.
     */
    public void testProducerWaitsWhenBufferIsFull() throws InterruptedException {
        executor.execute(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.addItem(i);
                    System.out.println("Added: " + i);
                }
                buffer.addItem(6); // Should block until space is available
                System.out.println("Added: 6");

                // Attempting to add more items after waiting
                buffer.addItem(7);
                System.out.println("Added: 7 after waiting for space.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        executor.shutdown();
        if (!executor.awaitTermination(15, TimeUnit.SECONDS)) {
            System.err.println("Test Failed: Timeout before completion.");
        }
    }
}
