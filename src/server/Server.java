package bingo.server;

import java.util.Properties;
import java.util.Collections;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.DatagramPacket;
import java.io.InputStream;
import java.io.IOException;

public class Server {
    private InetAddress multicastAddress;
    private int port;
    private NetworkInterface networkInterface;
    private InetSocketAddress group;
    private int maxNumber;
    private BingoDrum drum;
    private MulticastSocket senderSocket;
    private ServerListener listener;

    public Server(String propertiesFile) {
        this.readConfiguration(propertiesFile);
        this.drum = new BingoDrum(maxNumber);

        try {
            this.group = new InetSocketAddress(multicastAddress, port);
            this.senderSocket = new MulticastSocket(port);
            this.senderSocket.joinGroup(group, networkInterface);
        } catch (IOException exception) {
            System.err.println("Error: " + exception);
            System.exit(1);
        }

        this.listener = new ServerListener("ServerListener", multicastAddress, port, networkInterface);
        this.listener.start();
    }

    public InetAddress getMulticastAddress() {
        return this.multicastAddress;
    }

    public int getPort() {
        return this.port;
    }

    public InetSocketAddress getGroup() {
        return this.group;
    }

    public int getMaxNumber() {
        return this.maxNumber;
    }

    public int nextNumber() {
        int number = this.drum.extract();
        byte[] buffer = String.format("%02d", number).getBytes();

        DatagramPacket messageOut = new DatagramPacket(buffer, buffer.length, this.group);
        try {
            this.senderSocket.send(messageOut);
        } catch (IOException exception) {
            System.err.println("Error: " + exception);
        }
        System.out.println("Número extraído: " + String.format("%02d", number));

        return number;
    }

    public boolean gameFinished() {
        return !this.listener.isAlive();
    }

    public void close() {
        try {
            this.senderSocket.leaveGroup(this.group, this.networkInterface);
            this.senderSocket.close();
        } catch (IOException exception) {
            System.err.println("Error: " + exception);
        }
    }

    private void readConfiguration(String propertiesFile) {
        try {
            Properties configuration = new Properties();
            InputStream configurationFile = getClass().getResourceAsStream(propertiesFile);

            configuration.load(configurationFile);

            configurationFile.close();

            this.multicastAddress = InetAddress.getByName(configuration.getProperty("multicastAddress"));
            this.port = Integer.decode(configuration.getProperty("port"));
            this.networkInterface = NetworkInterface.getByName(configuration.getProperty("networkInterface"));
            this.maxNumber = Integer.decode(configuration.getProperty("maxNumber"));
        } catch (IOException exception) {
            System.err.println("Error: " + exception);
            System.exit(1);
        }
    }

    public String toString() {
        InetAddress address = Collections.list(this.networkInterface.getInetAddresses()).getLast(); /* La dirección IPv6 aparece primero */
        String outString = "Servidor de bingo emitiendo desde " + address + ":" + this.senderSocket.getLocalPort() + "\n";
        outString += "Dirección multicast: " + this.multicastAddress.toString() + "; Puerto: " + this.port + "\n";
        outString += "\n¡Comienza la partida!\n\n";

        return outString;
    }
}

