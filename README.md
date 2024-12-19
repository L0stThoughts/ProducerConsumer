# Producer-Consumer Problem with Shared Buffer

This project demonstrates the classic Producer-Consumer problem using Java. It includes a shared buffer that is accessed by multiple producers and consumers, each running in their own thread. The producers add items to the buffer, while the consumers remove items for processing. The buffer size is configurable, and the implementation uses synchronization mechanisms to handle concurrent access safely.

### Buffer Class Files

- **SharedBuffer.java**: Implements a basic shared buffer where items can be added and removed. It includes synchronization to ensure thread safety.
- **ConfigurableBuffer.java**: Extends `SharedBuffer` to allow dynamic configuration of buffer properties such as capacity.

### Config Class Files
- **ConfigManager.java**: Loads configuration settings from the `config.properties` file and provides them to other components.

### Consumer Class Files
- **Consumer.java**: Defines a consumer thread that removes items from the buffer for processing.
- **ConsumerUtils.java**: Provides utility methods for consumers, such as handling item consumption with delay, logging, or any other consumer-related operations.

### Producer Class Files
- **Producer.java**: Defines a producer thread that adds items to the buffer.
- **ProducerUtils.java**: Provides utility methods for producers, such as item production logic, logging, or any other producer-related operations.

### Logging Class Files
- **Logger.java**: A simple logger utility for logging the operations performed by producers and consumers.

### Main Class Files

- **ProducerConsumerMain.java**: The entry point for running the producer-consumer application. It initializes the buffer, producers, and consumers.

### Configuration Files

- **config.properties**: A properties file for configuring the buffer size and other parameters (e.g., number of producers, number of consumers).

### Test Files

- **ProducerTest.java**: Unit test to verify that a producer can add an item to the buffer.
- **ConsumerTest.java**: Unit test to verify that a consumer can remove an item from the buffer.
- **ProducerConsumerIntegrationTest.java**: Integration test to verify the interaction between multiple producers and consumers.
- **ConfigurableBufferTest.java**: Test to validate the configurable buffer's functionality and behavior under various configurations.

## Features

- **Configurable Buffer**: The buffer's size and other properties are configurable via the `config.properties` file.
- **Thread Safety**: Producers and consumers safely interact with the buffer using synchronization mechanisms.
- **Logging**: The `Logger.java` class helps track and debug the operations of producers and consumers.
- **Unit and Integration Tests**: Includes comprehensive tests to verify individual components and the integration of producers and consumers.

## How to Run

### Prerequisites

- JDK 8 or higher.
- Maven (for dependency management, if needed).

### Steps

1. Clone the repository:
   git clone https://github.com/yourusername/producer-consumer.git
   cd producer-consumer
