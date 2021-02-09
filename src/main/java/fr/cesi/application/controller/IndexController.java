package fr.cesi.application.controller;

import fr.cesi.infrastructure.http.Method;
import fr.cesi.infrastructure.http.Request;
import fr.cesi.infrastructure.http.Response;
import fr.cesi.infrastructure.http.annotation.Action;

public class IndexController  {

    @Action(path = "/", method = Method.GET)
    public Response getIndexAction(Request request) {


//        return Response.builder().statusCode(200).body(
//                "{\"heure\": [\"10h\", \"10h30\", \"11h\", \"11h30\", \"12h\"], " +
//                        "\"temp\": [10, 10, 15, 25 ,20]," +
//                        "\"humi\": [75, 85, 89, 92 ,80]}"
//        ).build();
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
