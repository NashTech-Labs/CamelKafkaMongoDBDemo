package com.nashtech.CamelKafkaMongoDBDemo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class KafkaConsumerServiceTest {

    @Autowired
    private KafkaConsumerService consumerService;

    @Test
    public void consume_Success() {
        ConsumerRecord<String, String> record = new ConsumerRecord<>("topic1", 0, 0, "key", "test message");
        consumerService.consume(record);
        assertEquals(1, consumerService.getConsumedMessages().size());
        assertEquals("Topic: topic1, Message: test message", consumerService.getConsumedMessages().get(0));
    }
}
