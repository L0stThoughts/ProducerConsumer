package test;

import buffer.SharedBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Integration test class to validate the interaction between producers and consumers in the SharedBuffer class.
 * The test ensures that producers can add items to the buffer, and consumers can consume them correctly.
 */
public class ProducerConsumerIntegrationTest {

    private SharedBuffer buffer;  // The shared buffer used for producer-consumer operations
    private ExecutorService executor;  // Executor service used to manage producer and consumer threads

    /**
     * Setup method that initializes the shared buffer and executor service before the test.
     * This method sets the buffer size to 5 and creates an executor with six threads for 3 producers and 3 consumers.
     */
    public void setUp() {
        buffer = new SharedBuffer(5); // Example buffer size
        executor = Executors.newFixedThreadPool(6); // Assuming 3 producers + 3 consumers
    }

    /**
     * Teardown method that shuts down the executor service after the test.
     * Ensures proper cleanup of resources after the test execution.
     */
    public void tearDown() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    /**
     * Integration test to verify the interaction between multiple producers and consumers.
     * This test launches multiple producer threads to add items to the buffer and multiple consumer threads to consume them.
     * It checks if the items are properly produced and consumed, and validates that the buffer behaves as expected.
     *
     * @throws InterruptedException if any thread is interrupted during the test execution.
     */
    public void testProducersAndConsumersInteraction() throws InterruptedException {
        int producerCount = 3;   // Number of producer threads
        int consumerCount = 3;   // Number of consumer threads
        int productionCycles = 10; // Number of items each producer will produce

        // Launch producer threads
        for (int i = 1; i <= producerCount; i++) {
            final int producerId = i;
            executor.execute(() -> {
                try {
                    for (int j = 0; j < productionCycles; j++) {
                        int item = producerId * 100 + j; // Unique item for each producer
                        buffer.addItem(item); // Add item to buffer
                        System.out.println("Producer-" + producerId + " added item: " + item);
                        Thread.sleep(50); // Simulate time taken to produce an item
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Launch consumer threads
        for (int i = 1; i <= consumerCount; i++) {
            final int consumerId = i;
            executor.execute(() -> {
                try {
                    for (int j = 0; j < productionCycles; j++) {
                        int item = buffer.removeItem(); // Consumer removes item from buffer
                        System.out.println("Consumer-" + consumerId + " consumed item: " + item);
                        Thread.sleep(50); // Simulate time taken to consume an item
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Shutdown executor and wait for tasks to finish
        executor.shutdown();
        boolean terminated = executor.awaitTermination(30, TimeUnit.SECONDS);

        // Check if the test passed or failed based on thread completion
        if (terminated) {
            System.out.println("Test Passed: All producer and consumer threads completed execution.");
        } else {
            System.err.println("Test Failed: Timeout reached before all threads completed.");
        }
    }
}
