package com.nashtech.CamelKafkaMongoDBDemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Model class representing a message.
 */
@Data
public class Message {
    @JsonProperty("id")
    private String id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("designation")
    private String designation;

    @JsonProperty("full_name")
    private String fullName;
}
