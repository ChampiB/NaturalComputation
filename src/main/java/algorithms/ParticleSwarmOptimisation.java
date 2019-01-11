package algorithms;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the PSO algorithm.
 */
public class ParticleSwarmOptimisation {

    private int NB_DIM = 2;
    private double W = 0.7;

    /**
     * Individual of the PSO.
     */
    private
    class Individual {

        Double[] best;
        Double[] pos;
        Double[] speed;

        /**
         * Individual Constructor.
         */
        public
        Individual(final int k) {
            best = new Double[NB_DIM];
            pos = new Double[NB_DIM];
            speed = new Double[NB_DIM];
            Random r = new Random();
            for (int i = 0; i < NB_DIM; i++) {
                pos[i] = 2 * k * (r.nextDouble() - 0.5);
                best[i] = pos[i];
                speed[i] = 2 * k * (r.nextDouble() - 0.5);
            }
        }
    }

    /**
     * Compute the individual fitness.
     * @param x the individual.
     * @return the fitness.
     */
    public
    Double fitness(final Double[] x) {
        return -1 * Math.abs(2 * Math.pow(x[0], 2) + 5 * x[1] + 10);
    }

    /**
     * Update the speed of the particle.
     * @param ind the particle neighbours.
     * @param x the particle.
     */
    private
    void updateSpeed(List<Individual> ind, Individual x) {
        List<Double> f = ind.stream().map(i -> fitness(i.pos)).collect(Collectors.toList());
        int index = f.indexOf(Collections.max(f));
        Random r = new Random();
        for (int i = 0; i < NB_DIM; i++) {
            x.speed[i] =
                    W * x.speed[i] +
                    r.nextDouble() * (x.best[i] - x.pos[i]) +
                    r.nextDouble() * (ind.get(index).pos[i] - x.pos[i]);
        }
    }

    /**
     * Update the position of the particle.
     * @param x the particle
     */
    private
    void updatePosition(Individual x) {
        for (int i = 0; i < NB_DIM; i++) {
            x.pos[i] = x.pos[i] + x.speed[i];
        }
    }

    /**
     * Run the PSO algorithm.
     * @param popSize the population size.
     * @param nbIter the number of iterations to run.
     * @param range the range of initial possible value of each axis (x0, x1, ..., xn belong to [-k; k]).
     * @return the best solution found.
     */
    public
    Double[] run(final int popSize, final int nbIter, final int range) {
        // Initialize the model.
        List<Individual> ind = new ArrayList<>();
        for (int j = 0; j < popSize; j++) {
            ind.add(new Individual(range));
        }
        // Train the model / Search the solution.
        for (int i = 0; i < nbIter; i++) {
            ind.forEach((x) -> updateSpeed(ind, x));
            ind.forEach(this::updatePosition);
        }
        // Return the best.
        List<Double> f = ind.stream().map(i -> fitness(i.pos)).collect(Collectors.toList());
        int index = f.indexOf(Collections.max(f));
        return ind.get(index).pos;
    }
}
