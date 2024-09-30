package bingo.client;

import bingo.server.BingoDrum;

public class Client {
    public static void main(String[] args) {
        Cardboard cardboard = new Cardboard();
        BingoDrum drum = new BingoDrum();

        for (int i = 0; i < BingoDrum.DRUM_SIZE; i++) {
            int newNumber = drum.extract();
            cardboard.markOff(newNumber);
            System.out.println("Number extracted: " + newNumber);
            System.out.println(cardboard);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {}
        }

    }
}
