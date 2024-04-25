package com.nashtech.CamelKafkaMongoDBDemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for producing messages to Kafka.
 */
@Service
public class KafkaProducerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Sends a message to Kafka.
     *
     * @param topics  The list of topics to send the message to
     * @param message The message to send
     */
    public void sendMessage(List<String> topics, Object message) {
        for (String topic : topics) {
            kafkaTemplate.send(topic, message);
            LOGGER.info("Message sent successfully to topic: {}, message: {}", topic, message);
        }
    }
}
