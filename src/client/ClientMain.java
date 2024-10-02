package bingo.client;

public class ClientMain {
    public static final String DEFAULT_PROPERTIES = "/properties/bingo.properties";

    public static void main(String[] args) {
        boolean done = false;

        String propertiesFile = args.length > 0 ? args[0] : DEFAULT_PROPERTIES;
        Client client = new Client(propertiesFile);

        System.out.println(client);

        while (!done) {
            done = client.listen();
            if (client.isFinished()) {
                client.notifyBingo();
                done = true;
            }
        }

        client.close();
    }
}
