package mayton.compression;

import java.util.Arrays;
import java.util.Random;

public class Stats {

    public static double probIntersect(double... args) {
        return Arrays.stream(args).reduce((a, b) -> a * b).getAsDouble();
    }

    public static double probUnion(double... args) {
        return 1.0;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static double bayes(double a, double b) {
       return 1.0;
    }

    public static void main(String[] args) {

        Random random = new Random();

        int general = 0;
        int terror = 0;

        for (int round = 1; round <= 5000; round++) {
            boolean[] res = new boolean[1000];
            for (int i = 0; i < 100; i++) {
                res[random.nextInt(1000)] = true;
            }
            //boolean terroristWin = false;
            for (int i = 0; i < 100; i++) {
                int k = random.nextInt(1000);
                if (res[k]) {
                    res[k] = false;
                    terror++;
                    //terroristWin = true;
                } else {
                    general++;
                }
            }
            /*if (terroristWin) {
                terror++;
            } else {
                general++;
            }*/
            System.out.printf("Round : %d, Score 'General - Terror' is %d : %d (%f)\n", round, general, terror, (double) general/terror);
        }
    }

}
