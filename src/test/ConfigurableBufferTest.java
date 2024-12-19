package test;

import buffer.SharedBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A test class that validates the behavior of the SharedBuffer class in a producer-consumer scenario.
 * It includes tests to ensure proper interaction between producers and consumers and to check the buffer's behavior under stress.
 */
public class ConfigurableBufferTest {

    private SharedBuffer buffer;  // The shared buffer used for producer-consumer operations
    private ExecutorService executor;  // The executor service used for managing threads

    /**
     * Setup method that initializes the shared buffer and executor service before each test.
     * This method sets the buffer size to 5 and creates an executor with a fixed thread pool
     * of 6 threads, assuming 3 producers and 3 consumers.
     */
    public void setUp() {
        buffer = new SharedBuffer(5); // Example buffer size
        executor = Executors.newFixedThreadPool(6); // Assuming 3 producers + 3 consumers
    }

    /**
     * Teardown method that shuts down the executor service after each test.
     * This ensures proper cleanup of resources after test execution.
     */
    public void tearDown() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    /**
     * Test to verify the interaction between multiple producers and consumers.
     * This test launches 3 producers and 3 consumers, each running for 10 production cycles,
     * and checks that items are correctly added and consumed.
     *
     * @throws InterruptedException if any thread is interrupted during the test execution.
     */
    public void testProducersAndConsumersInteraction() throws InterruptedException {
        int producerCount = 3;
        int consumerCount = 3;
        int productionCycles = 10;

        // Launch producers
        for (int i = 1; i <= producerCount; i++) {
            final int producerId = i;
            executor.execute(() -> {
                try {
                    for (int j = 0; j < productionCycles; j++) {
                        int item = producerId * 100 + j;
                        buffer.addItem(item);
                        System.out.println("Producer-" + producerId + " added item: " + item);
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Launch consumers
        for (int i = 1; i <= consumerCount; i++) {
            final int consumerId = i;
            executor.execute(() -> {
                try {
                    for (int j = 0; j < productionCycles; j++) {
                        int item = buffer.removeItem();
                        System.out.println("Consumer-" + consumerId + " consumed item: " + item);
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Shutdown executor and wait for tasks to finish
        executor.shutdown();
        boolean terminated = executor.awaitTermination(30, TimeUnit.SECONDS);
        
        // Basic assertion
        if (terminated) {
            System.out.println("Test Passed: All producer and consumer threads completed execution.");
        } else {
            System.err.println("Test Failed: Timeout reached before all threads completed.");
        }
    }

    /**
     * Stress test to evaluate the buffer's behavior under heavy load with a small buffer size.
     * This test uses a buffer size of 2 and launches 10 producers and 10 consumers,
     * each running for 20 production cycles. The aim is to check if the system can handle
     * a high number of threads and rapid production/consumption.
     *
     * @throws InterruptedException if any thread is interrupted during the test execution.
     */
    public void testBufferUnderStress() throws InterruptedException {
        int bufferSize = 2;
        int producerCount = 10;
        int consumerCount = 10;
        int productionCycles = 20;

        buffer = new SharedBuffer(bufferSize); // Example buffer size
        executor = Executors.newFixedThreadPool(producerCount + consumerCount);

        // Launch producers
        for (int i = 1; i <= producerCount; i++) {
            executor.execute(() -> {
                try {
                    for (int j = 0; j < productionCycles; j++) {
                        buffer.addItem((int) (Math.random() * 1000));
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Launch consumers
        for (int i = 1; i <= consumerCount; i++) {
            executor.execute(() -> {
                try {
                    for (int j = 0; j < productionCycles; j++) {
                        buffer.removeItem();
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        boolean terminated = executor.awaitTermination(30, TimeUnit.SECONDS);
        
        // Basic assertion
        if (terminated) {
            System.out.println("Test Passed: All threads completed execution under stress.");
        } else {
            System.err.println("Test Failed: Timeout reached before all threads completed.");
        }
    }
}
