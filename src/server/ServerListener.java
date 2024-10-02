package bingo.server;

import java.util.Properties;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.DatagramPacket;
import java.io.FileInputStream;
import java.io.IOException;

public class ServerListener extends Thread {
    private InetAddress multicastAddress;
    private int port;
    private NetworkInterface networkInterface;
    private InetSocketAddress group;
    private MulticastSocket socket;

    public ServerListener(String name, InetAddress multicastAddress, int port, NetworkInterface networkInterface) {
        super(name);

        this.multicastAddress = multicastAddress;
        this.port = port;
        this.networkInterface = networkInterface;

        try {
            this.group = new InetSocketAddress(multicastAddress, port);
            this.socket = new MulticastSocket(port);
            this.socket.joinGroup(group, networkInterface);
        } catch (IOException exception) {
            System.err.println("Error: " + exception);
            System.exit(1);
        }
    }

    public void run() {
        boolean done = false;

        while (!done) {
            byte[] buffer = new byte[32];
            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
            try {
                this.socket.receive(messageIn);
            } catch (IOException exception) {
                System.err.println("Error: " + exception);
            }

            String message = new String(messageIn.getData()).trim();    /* Eliminar los \0s finales */

            if (message.equals("bingo")) {
                System.out.println("¡El jugador " + messageIn.getSocketAddress() + " ha sido el primero en cantar bingo!\nEl juego se ha terminado.");
                done = true;
            }
        }
    }

}

