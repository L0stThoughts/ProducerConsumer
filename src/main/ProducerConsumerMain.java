import buffer.ConfigurableBuffer;
import config.ConfigManager;
import consumer.Consumer;
import producer.Producer;

public class ProducerConsumerMain {
    public static void main(String[] args) {
        System.out.println("Starting Producer-Consumer simulation...");

        // Load configuration
        ConfigManager configManager = new ConfigManager("config.properties");

        // Fetch configuration values
        int bufferSize = configManager.getInt("buffer.size", 10);
        int producerCount = configManager.getInt("producer.count", 3);
        int consumerCount = configManager.getInt("consumer.count", 3);
        int producerSleepTime = configManager.getInt("producer.sleep.time", 100);
        int consumerSleepTime = configManager.getInt("consumer.sleep.time", 150);

        System.out.println("Configuration Loaded:");
        System.out.println("Buffer Size: " + bufferSize);
        System.out.println("Producer Count: " + producerCount);
        System.out.println("Consumer Count: " + consumerCount);
        System.out.println("Producer Sleep Time: " + producerSleepTime + "ms");
        System.out.println("Consumer Sleep Time: " + consumerSleepTime + "ms");

        // Create the shared buffer
        ConfigurableBuffer buffer = new ConfigurableBuffer(bufferSize);

        // Start producer threads
        for (int i = 1; i <= producerCount; i++) {
            String producerName = "Producer-" + i;
            Producer producer = new Producer(buffer, producerName, producerSleepTime);
            Thread producerThread = new Thread(producer);
            producerThread.start();
            System.out.println(producerName + " started.");
        }

        // Start consumer threads
        for (int i = 1; i <= consumerCount; i++) {
            String consumerName = "Consumer-" + i;
            Consumer consumer = new Consumer(buffer, consumerName, consumerSleepTime);
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();
            System.out.println(consumerName + " started.");
        }

        System.out.println("Simulation running. Press Ctrl+C to exit.");
    }
}
