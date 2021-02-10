package fr.cesi.meteo.infrastructure.http;

import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;

import java.util.Map;

@Builder
@Getter
public class Request {

    private final Map<String, String> parameters;
    private final JSONObject body;

}
