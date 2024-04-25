package com.nashtech.CamelKafkaMongoDBDemo.controller;

import com.nashtech.CamelKafkaMongoDBDemo.service.KafkaConsumerService;
import com.nashtech.CamelKafkaMongoDBDemo.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaControllerTest {

    @Mock
    private KafkaProducerService producerService;

    @Mock
    private KafkaConsumerService consumerService;

    @InjectMocks
    private KafkaController kafkaController;

    @Test
    void sendJsonMessageToKafka_Success() {
        String jsonMessage = "{\"id\":\"1\",\"first_name\":\"John\",\"last_name\":\"Doe\",\"designation\":\"Tester\"}";
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.OK)
                .body("{\"status\": 200 OK,\n \"code\": 200, \n \"message\": \"JSON Message sent to Kafka topic successfully!\", \n \"sentMessage\": \"" + jsonMessage + "\"}");
        ResponseEntity<String> response = kafkaController.sendJsonMessageToKafka(jsonMessage);
        assertEquals(expectedResponse, response);
        verify(producerService, times(1)).sendMessage(anyList(), eq(jsonMessage));
    }

    @Test
    void sendJsonMessageToKafka_Failure() {
        String jsonMessage = "{\"id\":\"1\",\"first_name\":\"John\",\"last_name\":\"Doe\",\"designation\":\"Tester\"}";
        doThrow(new RuntimeException("Failed to send JSON message")).when(producerService).sendMessage(anyList(), anyString());
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"status\": \"error\", \n \"code\": 500, \n \"message\": \"Failed to send JSON message to Kafka topic.\", \n \"error\": \"Failed to send JSON message\"}");
        ResponseEntity<String> response = kafkaController.sendJsonMessageToKafka(jsonMessage);
        assertEquals(expectedResponse, response);
        verify(producerService, times(1)).sendMessage(anyList(), eq(jsonMessage));
    }

    @Test
    void consumeMessages_Success() {
        List<String> expectedMessages = Collections.singletonList("Test message");
        when(consumerService.getConsumedMessages()).thenReturn(expectedMessages);
        List<String> messages = kafkaController.consumeMessages();
        assertEquals(expectedMessages, messages);
        verify(consumerService, times(1)).getConsumedMessages();
    }
}
