package fr.cesi.meteo.http;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HTTPServerInfo {

    private final String host;
    private final int port;

}
