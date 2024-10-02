package bingo.client;

import java.util.Properties;
import java.util.Collections;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.DatagramPacket;
import java.io.InputStream;
import java.io.IOException;

public class Client {
    private InetAddress multicastAddress;
    private int port;
    private NetworkInterface networkInterface;
    private InetSocketAddress group;
    private MulticastSocket socket;
    private Cardboard cardboard;
    private int maxNumber;
    
    public Client(String propertiesFile) {
        this.readConfiguration(propertiesFile);
        this.cardboard = new Cardboard(maxNumber);

        try {
            this.group = new InetSocketAddress(multicastAddress, port);
            this.socket = new MulticastSocket(port);
            this.socket.joinGroup(group, networkInterface);
        } catch (IOException exception) {
            System.err.println("Error: " + exception);
            System.exit(1);
        }
    }

    public boolean listen() {
        byte[] buffer = new byte[32];

        DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);

        try {
            this.socket.receive(messageIn);
        } catch (IOException exception) {
            System.err.println("Error: " + exception);
        }

        String message = new String(messageIn.getData()).trim();

        if (message.equals("bingo")) {
            System.out.println("El jugador " + messageIn.getSocketAddress() + " ha sido el primero en cantar bingo.");
            System.out.println("Te deseamos más suerte la próxima vez.");
            return true;
        }
        try {
            int number = Integer.decode(message);
            this.cardboard.markOff(number);
            System.out.println("Número recibido: " + String.format("%02d", number));
            System.out.println(this.cardboard);
        } catch (NumberFormatException exception) {} /* Si el mensaje no era "bingo" o un número, se ignora */

        return false;
    }

    public boolean isFinished() {
        return this.cardboard.isFinished();
    }

    public void notifyBingo() {
        byte[] buffer = "bingo".getBytes();

        DatagramPacket messageOut = new DatagramPacket(buffer, buffer.length, this.group);
        try {
            this.socket.send(messageOut);
        } catch (IOException exception) {
            System.err.println("Error: " + exception);
        }
        System.out.println("!BINGO!");
    }

    public void close() {
        try {
            this.socket.leaveGroup(this.group, this.networkInterface);
            this.socket.close();
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
        InetAddress address = Collections.list(this.networkInterface.getInetAddresses()).getLast();    /* El primer elemento es la dirección IPv6 */
        String outString = "Cliente de bingo jugando en " + address + ":" + this.socket.getLocalPort() + "\n";
        outString += "Dirección multicast: " + this.multicastAddress.toString() + "; Puerto: " + this.port + "\n";
        outString += "Cartón de juego:\n" + this.cardboard.toString();
        outString += "Esperando números del servidor...\n";

        return outString;
    }

}
