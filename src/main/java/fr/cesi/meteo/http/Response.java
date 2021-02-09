package fr.cesi.meteo.http;

import com.sun.net.httpserver.HttpExchange;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;


@Builder
@Getter
public class Response {

    private final int statusCode;
    private String body;

    @SneakyThrows
    public void sendResponse(HttpExchange exchange) {
        body = "{\"status\": " + statusCode + ", \"content\": "+body+"}";
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(statusCode, body.length());

        Writer out = new OutputStreamWriter(exchange.getResponseBody(), StandardCharsets.UTF_8);
        out.write(body);
        out.flush();
        out.close();
    }

}
