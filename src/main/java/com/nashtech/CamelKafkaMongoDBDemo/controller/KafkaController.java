package com.nashtech.CamelKafkaMongoDBDemo.controller;

import com.nashtech.CamelKafkaMongoDBDemo.service.KafkaConsumerService;
import com.nashtech.CamelKafkaMongoDBDemo.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Controller responsible for handling Kafka-related operations.
 */
@RestController
public class KafkaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    private KafkaProducerService producerService;

    @Autowired
    private KafkaConsumerService consumerService;
    List<String> topics = Arrays.asList("topic1", "topic2", "topic3");

    /**
     * Sends a JSON message to Kafka.
     *
     * @param jsonMessage The JSON message to send
     * @return ResponseEntity indicating the status of the operation
     */
    @PostMapping("/sendJsonToKafka")
    public ResponseEntity<String> sendJsonMessageToKafka(@RequestBody String jsonMessage) {
        try {
            producerService.sendMessage(topics, jsonMessage);
            LOGGER.info("JSON Message sent to Kafka topic successfully: {}", jsonMessage);
            return ResponseEntity.status(HttpStatus.OK).body("{\"status\": " + HttpStatus.OK + ",\n \"code\": " + HttpStatus.OK.value() + ", \n \"message\": \"JSON Message sent to Kafka topic successfully!\", \n \"sentMessage\": \"" + jsonMessage + "\"}");
        } catch (Exception e) {
            LOGGER.error("Failed to send JSON message to Kafka topic", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"status\": \"error\", \n \"code\": " + HttpStatus.INTERNAL_SERVER_ERROR.value() + ", \n \"message\": \"Failed to send JSON message to Kafka topic.\", \n \"error\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Retrieves consumed messages from Kafka.
     *
     * @return List of consumed messages
     */
    @GetMapping("/consume")
    public List<String> consumeMessages() {
        return consumerService.getConsumedMessages();
    }
}
