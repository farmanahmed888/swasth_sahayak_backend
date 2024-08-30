package com.example.demo.Entity.JWT_entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.jsonwebtoken.io.IOException;

public enum RoleType {
    ADMIN,
    FIELDWORKER,
    DOCTOR,

    NORMAL_USER,
    SUPERVISOR;

    public static class Deserializer extends JsonDeserializer<RoleType> {
        @Override
        public RoleType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, java.io.IOException {
            return RoleType.valueOf(p.getText().toUpperCase()); // Deserialize enum from JSON string
        }
    }

    public static class Serializer extends JsonSerializer<RoleType> {
        @Override
        public void serialize(RoleType value, JsonGenerator gen, SerializerProvider serializers) throws IOException, java.io.IOException {
            gen.writeString(value.name()); // Serialize enum as a string
        }
    }
}
