## Camel Kafka MongoDB Demo

### Overview
This project showcases the seamless integration of Apache Camel to receive messages from Apache Kafka, process them, save them in MongoDB, and subsequently forward them to another Kafka topic after processing. By using Apache Camel's flexibility, we create a strong foundation for building integration solutions that can grow as needed.
### Requirements
- Docker Desktop or Docker Daemon
- Postman
- IntelliJ IDEA
- Java 17 or later

### Setup
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/NashTech-Labs/CamelKafkaMongoDBDemo.git
   ```

2. **Ensure Docker Environment:**
    - Ensure Docker Desktop or Docker Daemon is installed and operational.
    - Launch MongoDB and Kafka containers using:
      ```bash
      docker-compose up -d
      ```

3. **Run the Application:**
    - Execute `mvn spring-boot:run` to start the application.
    - Alternatively, run the main class from IntelliJ IDEA.

### Configuration
The application is configured via the `application.properties` file:

```properties
camel.component.mongodb.host=localhost
camel.component.mongodb.port=27017
camel.component.mongodb.database=Employee
camel.component.mongodb.collection=Emp_Coll
```

### Components
1. **KafkaController:** This REST controller facilitates sending JSON messages to Kafka and consuming messages.
2. **KafkaConsumerRoute:** It defines an Apache Camel route to consume messages from Kafka, process them, and store them in MongoDB.
3. **KafkaProducerService:** Service responsible for sending messages to Kafka topics specified in the configuration.
4. **KafkaConsumerService:** Service responsible for consuming messages from Kafka topics specified in the configuration.

### Message Flow: Kafka Topic to MongoDB
- **Sending JSON Message to Kafka:** Use a POST request to `/sendJsonToKafka` endpoint with a JSON message body. This message gets forwarded to Kafka for further handling.

   ```bash
   curl --location 'localhost:8080/sendJsonToKafka' \
   --header 'Content-Type: application/json' \
   --data '{
       "id": "1",
       "first_name": "John",
       "last_name": "Doe",
       "designation": "Tester"
   }'
   ```

- **Consuming Processed Messages:** Issue a GET request to `/consume` endpoint to retrieve processed messages from Kafka, which are then stored in MongoDB.

   ```bash
   curl http://localhost:8080/consume
   ```


Initially, messages are sent to Kafka topics `topic1`, `topic2`, and `topic3` by the Kafka Producer Service. Subsequently, Apache Camel facilitates the consumption of messages from `topic1`. Processing involves merging data based on both first and last names, adding a new column. Then, the processed data is persisted into MongoDB. Subsequently forward the processed message to another Kafka topic i.e. `topic 4`.