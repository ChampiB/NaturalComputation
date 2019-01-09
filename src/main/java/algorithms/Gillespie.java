package algorithms;

import java.util.Random;

/**
 * Implementation of the Gillespie algorithm.
 */
public
class Gillespie {

    /**
     * Abstract class representing a Gillespie System.
     */
    public static abstract
    class GillespieSystem {

        /**
         * Display the system' state.
         * @param time is the current time of the system.
         */
        public abstract
        void print(String time);

        /**
         * Run an action in the system.
         * @param action is the action to be executed.
         */
        public abstract
        void apply(int action);

        /**
         * Return the reactions' propencity.
         * @return an array of propencities.
         */
        public abstract
        float[] getReactionsPropencity();
    }

    /**
     * Run the Gillespie Algorithm.
     * @param s the Gillespie System.
     * @param nbIteration the number of iteration to run.
     * @param displayRate the rate at which the system should be displayed.
     */
    public static
    void run(GillespieSystem s, int nbIteration, int displayRate) {
        float time = 0;
        int i = 0;
        while (i < nbIteration) {
            if (displayRate != -1 && i % displayRate == 0)
                s.print(String.valueOf(time));
            float[] p = s.getReactionsPropencity();
            if (p == null)
                return;
            s.apply(TowerSampling.rate(p));
            float sum = 0F;
            for (float rate: p)
                sum += rate;
            time += 1F / sum + (float) Math.log10(new Random().nextDouble() + 1D);
            i++;
        }
    }
}
