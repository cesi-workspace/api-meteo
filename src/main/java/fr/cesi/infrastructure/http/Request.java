package fr.cesi.infrastructure.http;

import lombok.Getter;

import java.util.Map;

public class Request {

    @Getter
    private final Map<String, String> parameters;

    public Request(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}