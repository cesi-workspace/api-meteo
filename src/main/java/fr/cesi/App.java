package fr.cesi;

import fr.cesi.domain.model.Data;
import fr.cesi.infrastructure.exception.ParameterNotFoundException;
import fr.cesi.infrastructure.http.HTTPServer;
import fr.cesi.infrastructure.http.HTTPServerInfo;
import fr.cesi.mysql.connector.SQLConnectionAdapter;
import fr.cesi.mysql.connector.SQLConnectionAdapterFactory;
import fr.cesi.mysql.persist.PersistQuery;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Optional;

public class App {

    public static void main(String[] args) {
        int port = -1;

        try { port = Integer.parseInt(readArgument(args, "port")); }
        catch (NumberFormatException exception) {
            System.err.println("Port is not a number.");
            System.exit(0);
        }

        Optional<SQLConnectionAdapter> sqlConnectionAdapter = SQLConnectionAdapter.from(
                "jdbc:mysql://192.168.37.252/meteo?autoReconnect=true",
                "cesi",
                "cesi"
        );

        if (sqlConnectionAdapter.isPresent()) {
            SQLConnectionAdapterFactory.getInstance().addConnectionAdapter(
                    Data.class,
                    sqlConnectionAdapter.get()
            );

            new PersistQuery<Data>(Data.class).createTable();

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
