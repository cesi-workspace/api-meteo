package fr.cesi.meteo.infrastructure.http;

import com.sun.net.httpserver.HttpServer;
import fr.cesi.meteo.configuration.route.RouterList;
import lombok.Data;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;

@Data
public class HTTPServer {

    private final HTTPServerInfo httpServerInfo;

    @SneakyThrows
    public void start() {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(
                httpServerInfo.getHost(),
                httpServerInfo.getPort()
        ), 0);

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.registerAllRoutes(new RouterList());

        httpServer.createContext("/", dispatcher);
        httpServer.start();
    }

}
