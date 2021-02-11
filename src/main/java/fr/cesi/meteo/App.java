package fr.cesi.meteo;

import fr.cesi.divers.mysql.connector.SQLConnectionAdapter;
import fr.cesi.meteo.configuration.Bootstrap;
import fr.cesi.meteo.infrastructure.exception.ParameterNotFoundException;
import fr.cesi.meteo.infrastructure.http.HTTPServer;
import fr.cesi.meteo.infrastructure.http.HTTPServerInfo;

import java.util.Arrays;
import java.util.Optional;

public class App {

    public static void main(String[] args) {
        try { Class.forName("com.mysql.jdbc.Driver"); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }

        int port = -1;

        try { port = Integer.parseInt(readArgument(args, "port")); }
        catch (NumberFormatException exception) {
            System.err.println("Port is not a number.");
            System.exit(0);
        }

        Optional<SQLConnectionAdapter> sqlConnectionAdapter = SQLConnectionAdapter.from(
                "jdbc:mysql://127.0.0.1/meteo?autoReconnect=true",
                "cesi",
                "cesi"
        );

        if (sqlConnectionAdapter.isPresent()) {
            new Bootstrap().register(sqlConnectionAdapter.get());

            HTTPServerInfo httpServerInfo = new HTTPServerInfo(readArgument(args, "host"), port);
            HTTPServer httpServer = new HTTPServer(httpServerInfo);

            httpServer.start();
        }
    }

    private static String readArgument(String[] args, String name) {
        int index = Arrays.asList(args).indexOf("--"+name) + 1; // Récupérationde l'index + 1 du paramètre voulu

        if (index >= args.length)
            throw new ParameterNotFoundException("%s parameter not found.", name);

        return args[index];
    }

}
