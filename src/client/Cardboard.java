package bingo.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Date;

public class Cardboard {
    public static final int CARD_SIZE = 15;
    public static final int LINE_SIZE = 5;

    private ArrayList<Integer> card;
    private int numbersLeft;

    public Cardboard(int maxNumber) {
        /* Generar números aleatorios de entre los posibles */
        ArrayList<Integer> possibleNumbers = new ArrayList<>(maxNumber);
        for (int i = 0; i < maxNumber; i++) {
            possibleNumbers.add(i, i + 1);
        }

        Random randomGenerator = new Random(new Date().getTime());
        Collections.shuffle(possibleNumbers, randomGenerator);

        this.card = new ArrayList<>(possibleNumbers.subList(0, CARD_SIZE));
        Collections.sort(this.card);
        this.numbersLeft = CARD_SIZE;
    }

    public void markOff(int number) {
        int index = this.card.indexOf(number);

        if (index >= 0) {
            this.card.set(index, -number);
            this.numbersLeft--;
        }
    }

    public boolean isFinished() {
        return (this.numbersLeft == 0);
    }

    public String toString() {
        String startCrossed = "\u001b[9;31m";
        String endCrossed = "\u001b[0m";

        StringBuilder outString = new StringBuilder(25 * CARD_SIZE);

        outString.repeat('-', LINE_SIZE * 3);
        outString.append("-\n");

        final int numLines = CARD_SIZE / LINE_SIZE;
        int number;
        /* Rellenar el cartón con los número ordenados por columna */
        for (int i = 0; i < numLines; i++) {
            for (int j = 0; j < LINE_SIZE; j++) {
                outString.append('|');
                number = this.card.get(i + j * numLines);
                if (number > 0) {
                    outString.append(String.format("%02d", number));
                } else {
                    outString.append(String.format(startCrossed + "%02d" + endCrossed, -number));
                }
            }
            outString.append("|\n");
            outString.repeat('-', LINE_SIZE * 3);
            outString.append("-\n");
        }

        return outString.toString();
    }
}
