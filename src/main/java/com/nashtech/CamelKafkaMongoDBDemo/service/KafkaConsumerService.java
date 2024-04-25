package com.nashtech.CamelKafkaMongoDBDemo.service;

import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for consuming messages from Kafka.
 */
@Getter
@Service
public class KafkaConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

    private final List<String> consumedMessages = new ArrayList<>();

    /**
     * Consumes a message from Kafka.
     *
     * @param record The consumed record
     */
    @KafkaListener(id = "myListener", groupId = "my_group_id", topicPattern = "topic.*")
    public void consume(ConsumerRecord<String, String> record) {
        String topic = record.topic();
        String message = record.value();
        LOGGER.info("Consumed message from topic {}: {}", topic, message);
        consumedMessages.add("Topic: " + topic + ", Message: " + message);
    }
}
