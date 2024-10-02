package bingo.server;

import java.util.Iterator;
import java.net.NetworkInterface;
import java.io.IOException;

public class ServerMain {
    public static final String DEFAULT_PROPERTIES = "/properties/bingo.properties";

    public static void main(String[] args) {
        String propertiesFile = args.length > 0 ? args[0] : DEFAULT_PROPERTIES;
        Server server = new Server(propertiesFile);

        System.out.println(server);

        for (int i = 0; i < server.getMaxNumber(); i++) {
            server.nextNumber();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {}
            if (server.gameFinished()) break;
        }

        server.close();
    }
}
