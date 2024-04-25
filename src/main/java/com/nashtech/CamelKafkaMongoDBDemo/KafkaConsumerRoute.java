package com.nashtech.CamelKafkaMongoDBDemo;

import com.nashtech.CamelKafkaMongoDBDemo.model.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Camel route for consuming messages from Kafka "topic 1" and storing them in MongoDB and again sending it to kafka
 * "topic 4" after processing.
 */
@Component
public class KafkaConsumerRoute extends RouteBuilder {

    @Value("${camel.component.mongodb.host}")
    private String mongoHost;

    @Value("${camel.component.mongodb.port}")
    private int mongoPort;

    @Value("${camel.component.mongodb.database}")
    private String mongoDatabase;

    @Value("${camel.component.mongodb.collection}")
    private String mongoCollection;

    @Override
    public void configure() throws Exception {
        from("kafka:topic1?brokers=localhost:9092")
                .unmarshal().json(JsonLibrary.Jackson, Message.class)
                .process(exchange -> {
                    Message message = exchange.getIn().getBody(Message.class);
                    String fullName = message.getFirstName() + " " + message.getLastName();
                    message.setFullName(fullName);
                    exchange.getIn().setBody(message);
                })
                .marshal().json(JsonLibrary.Jackson)
                .to("mongodb:" + mongoHost + ":" + mongoPort + "?database=" + mongoDatabase + "&collection=" + mongoCollection + "&operation=insert")
                .log("Writing to MongoDB: ${body}")
                .to("kafka:topic4?brokers=localhost:9092")
                .log("Writing to Kafka topic 4: ${body}");
    }
}
