package algorithms;

/**
 * Implementation of the tower sampling algorithm (rates & probabilities).
 */
public
class TowerSampling {

    /**
     * Run the Tower Sampling on an array of rates.
     * @param rates the array of rates.
     * @return the index of the selected action/state.
     */
    public static
    int rate(final float[] rates) {
        float[] probabilities = new float[rates.length];
        float s = 0;
        for (float rate : rates)
            s += rate;
        for (int i = 0; i < rates.length; i++)
            probabilities[i] = rates[i] / s;
        return TowerSampling.probability(probabilities);
    }

    /**
     * Run the Tower Sampling on an array of probabilities.
     * @param probabilities the array of probabilities.
     * @return the index of the selected action/state.
     */
    public static
    int probability(final float[] probabilities) {
        Float r = (float) Math.random(); // Draw a random number between 0 and 1.
        Float sum = 0F;
        for (int i = 0; i < probabilities.length; i++) {
            sum += probabilities[i];
            if (sum >= r)
                return i;
        }
        return probabilities.length - 1;
    }
}
