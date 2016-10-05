package edu.schmitml;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Marc Schmitt
 * schmitml@rose-hulman.edu
 * MA381
 */
public class Main {
    private static int NUMBER_OF_TRIALS = 1000000;

    public static void main(String[] args) {
        System.out.println("Two Player Game");
        TwoPlayerGame g1 = new TwoPlayerGame();
        determineBest(g1.runGame(NUMBER_OF_TRIALS));

        System.out.println("Three Player Game");
        ThreePlayerGame g2 = new ThreePlayerGame();
        determineBest(g2.runGame(NUMBER_OF_TRIALS));

        System.out.println("Four Player Game");
        FourPlayerGame g3 = new FourPlayerGame();
        determineBest(g3.runGame(NUMBER_OF_TRIALS));
    }

    private static void determineBest(ArrayList<Double> winPercentages){
        int bestStop = 0;
        double bestPercent = winPercentages.get(0);
        for(int i = 0; i < 21; i++){
            if(winPercentages.get(i) >= bestPercent){
                bestPercent = winPercentages.get(i);
                bestStop = i;
            }
            System.out.println( winPercentages.get(i));
        }
        System.out.println("Best stop " + bestStop +" with " + bestPercent + "\n");
    }

    /*****************UTIL*****************/
    public static int spinWheel(){
        return ThreadLocalRandom.current().nextInt(0, 20 + 1);
    }
}
