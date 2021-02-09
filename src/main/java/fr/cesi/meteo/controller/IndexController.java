package fr.cesi.meteo.controller;

import fr.cesi.meteo.http.Method;
import fr.cesi.meteo.http.Request;
import fr.cesi.meteo.http.Response;
import fr.cesi.meteo.http.annotation.Action;
import fr.cesi.meteo.domain.model.Data;
import fr.cesi.mysql.persist.PersistQuery;

import java.time.Instant;

public class IndexController  {

    @Action(path = "/", method = Method.GET)
    public Response getIndexAction(Request request) {
        Data data = Data.builder()
                .createdAt(Instant.now().getNano() / 1000)
                .humidity(10)
                .temperature(15).build();

        new PersistQuery<Data>(Data.class).create(data);

        return Response.builder().statusCode(200).body(
                "{\"heure\": [\"10h\", \"10h30\", \"11h\", \"11h30\", \"12h\"], " +
                        "\"temp\": [10, 10, 15, 25 ,20]," +
                        "\"humi\": [75, 85, 89, 92 ,80]}"
        ).build();
    }

    @Action(path = "/create", method = Method.POST)
    public Response postIndexAction(Request request) {
        return Response.builder().statusCode(200).body("create").build();
    }

    @Action(path = "/update", method = Method.UPDATE)
    public Response updateIndexAction(Request request) {
        return Response.builder().statusCode(200).body("update").build();
    }

}
