package fr.cesi.infrastructure.http;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class Router {

    protected final Map<String, Class<?>> controllers = new HashMap<>();
    protected String domain = "";

}
