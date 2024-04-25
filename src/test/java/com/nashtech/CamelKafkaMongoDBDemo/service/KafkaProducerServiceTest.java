package com.nashtech.CamelKafkaMongoDBDemo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class KafkaProducerServiceTest {

    @Autowired
    private KafkaProducerService producerService;

    @MockBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    public void sendMessage_Success() {
        List<String> topics = Arrays.asList("topic1", "topic2", "topic3");
        String message = "test message";
        producerService.sendMessage(topics, message);
        for (String topic : topics) {
            verify(kafkaTemplate, times(1)).send(eq(topic), eq(message));
        }
    }
}
