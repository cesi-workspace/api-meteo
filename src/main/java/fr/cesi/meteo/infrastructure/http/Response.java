package fr.cesi.meteo.infrastructure.http;

import com.sun.net.httpserver.HttpExchange;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;


@Builder
@Getter
@Setter
public class Response {

    private int statusCode;
    private JSONObject body;

    @SneakyThrows
    public void sendResponse(HttpExchange exchange) {
        JSONObject response = new JSONObject();
        response.put("status", statusCode);
        response.put("content", body);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(statusCode, response.toString().length());


        Writer out = new OutputStreamWriter(exchange.getResponseBody(), StandardCharsets.UTF_8);
        out.write(response.toString());
        out.flush();
        out.close();

    }

}
