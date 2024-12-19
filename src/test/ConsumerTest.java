package test;

import buffer.SharedBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A test class to validate the behavior of the consumer in the SharedBuffer class.
 * It ensures that the consumer correctly consumes an item from the buffer.
 */
public class ConsumerTest {

    private SharedBuffer buffer;  // The shared buffer used for producer-consumer operations
    private ExecutorService executor;  // Executor service used to manage the consumer thread

    /**
     * Setup method that initializes the shared buffer and executor service before the test.
     * This method sets the buffer size to 5 and creates an executor with a single thread for the consumer.
     */
    public void setUp() {
        buffer = new SharedBuffer(5); // Example buffer size
        executor = Executors.newFixedThreadPool(1); // Single thread for consumer
    }

    /**
     * Teardown method that shuts down the executor service after the test.
     * Ensures proper cleanup of resources after test execution.
     */
    public void tearDown() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    /**
     * Test to verify that a consumer correctly consumes an item from the buffer.
     * This test adds an item (10) to the buffer and then consumes it, ensuring the correct item is consumed.
     *
     * @throws InterruptedException if any thread is interrupted during the test execution.
     */
    public void testConsumerConsumesItem() throws InterruptedException {
        executor.execute(() -> {
            try {
                buffer.addItem(10); // Add item to buffer
                int item = buffer.removeItem(); // Consumer removes item
                System.out.println("Consumer consumed item: " + item);

                // Verify if the correct item is consumed
                if (item != 10) {
                    System.err.println("Test Failed: Consumer did not consume the correct item.");
                } else {
                    System.out.println("Test Passed: Consumer consumed the correct item.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Wait for executor to complete execution
        executor.shutdown();
        boolean terminated = executor.awaitTermination(10, TimeUnit.SECONDS);

        // Basic assertion
        if (!terminated) {
            System.err.println("Test Failed: Timeout reached before consumer completed.");
        }
    }
}
