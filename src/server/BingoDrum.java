package bingo.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;
import java.util.Random;


/**
 * Clase para generar los números aleatorios por parte del servidor.
 */
public class BingoDrum {
    public static final int DRUM_SIZE = 90;

    private ArrayList<Integer> drum;
    private Random randomGenerator;

    /**
     * Genera un nuevo bombo con los números del 1 a DRUM_SIZE,
     * que coloca desordenados en una lista.
     */
    public BingoDrum() {
        this.drum = new ArrayList<>(DRUM_SIZE);

        for (int i = 0; i < DRUM_SIZE; i++) {
            this.drum.add(i, i + 1);
        }

        randomGenerator = new Random(new Date().getTime());
        Collections.shuffle(this.drum, randomGenerator);
    }


    /**
     * Extrae un número aleatorio del bombo.
     * 
     * @return El número extraído, o -1 en caso de que el bombo esté vacío.
     */
    public int extract() {
        int size = this.drum.size();
        
        if (size <= 0) return -1;

        int position = this.randomGenerator.nextInt(size);
        int result = this.drum.remove(position);

        return result;
    }
}
