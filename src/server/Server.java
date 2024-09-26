package server;

import java.util.Properties;
import java.net.InetAddress;
import java.io.FileInputStream;

public class Server {
    public static void main(String[] args) {
        readConfiguration("properties/bingo.properties");
    }

    private static void readConfiguration(String propertiesFile) {
        try {
            Properties configuration = new Properties();
            FileInputStream configurationFile = new FileInputStream(propertiesFile);

            configuration.load(configurationFile);

            configurationFile.close();

            InetAddress multicastAddress = InetAddress.getByName(configuration.getProperty("multicastAddress"));
            String port = configuration.getProperty("port");

            System.out.println("IP: " + multicastAddress);
            System.out.println("port: " + port);
        } catch (Exception e) {
            System.out.println("Exception catched: " + e);
        }
    }
}

