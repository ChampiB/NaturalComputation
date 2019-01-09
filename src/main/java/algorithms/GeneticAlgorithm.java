package algorithms;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Implementation of the genetic algorithm.
 * @param <T> The individuals' type.
 */
public class GeneticAlgorithm<T> {

    private Supplier<T> generator;
    private Function<T, Double> fitness;
    private List<Function<T, T>> varOp1 = new ArrayList<>();
    private List<BiFunction<T, T, T>> varOp2 = new ArrayList<>();

    /**
     * The constructor of the genetic algorithm.
     * @param generator the function used to generate the individuals.
     * @param fitness the fitness function.
     */
    public
    GeneticAlgorithm(Supplier<T> generator, Function<T, Double> fitness) {
        this.generator = generator;
        this.fitness = fitness;
    }

    /**
     * Add a variation operator taking one individual as parameters and return a new one (e.g. mutation).
     * @param varOp the operator.
     */
    public
    void addVariationOperator(Function<T, T> varOp) {
        this.varOp1.add(varOp);
    }

    /**
     * Add a variation operator taking two individual as parameters and return a new one (e.g. crossover).
     * @param varOp the operator.
     */
    public
    void addVariationOperator(BiFunction<T, T, T> varOp) {
        this.varOp2.add(varOp);
    }

    /**
     * Generate a random number between minV and maxV.
     * @param minV the minimum value of value generated.
     * @param maxV the maximum value of value generated.
     * @return the value generated.
     */
    private
    int rand(final int minV, final int maxV) {
        return minV + new Random().nextInt(maxV - minV + 1);
    }

    /**
     * Select randomly an element of the list.
     * @param l the list.
     * @param <U> the type of elements of the list.
     * @return the selected value.
     */
    private <U>
    U randFrom(List<U> l) {
        return l.get(rand(0, l.size() - 1));
    }

    /**
     * Generate a population.
     * @param popSize the population's size.
     * @return the population.
     */
    private
    List<T> generation(final int popSize) {
        List<T> pop = new LinkedList<>();

        int i = 0;
        while (i < popSize) {
            pop.add(generator.get());
            ++i;
        }
        return pop;
    }

    /**
     * Select a population part.
     * @param p the initial population.
     * @param n the final population size.
     * @return the final population.
     */
    private
    List<T> selection(List<T> p, final int n) {
        List<T> np = new LinkedList<>();
        List<Double> f = p.stream().map((x) -> fitness.apply(x)).collect(Collectors.toList());

        int i = 0;
        while (i < n) {
            try {
                final int index = f.indexOf(Collections.max(f));
                np.add(p.get(index));
                p.remove(index);
                f.remove(index);
            } catch (NoSuchElementException e) {
                System.err.println(e.getMessage());
                break;
            }
            ++i;
        }
        return np;
    }

    /**
     * Apply variations to the population.
     * @param p the initial population.
     * @param popSize the final population size.
     * @return the new population.
     */
    private
    List<T> variation(List<T> p, final int popSize, final boolean elitism) {
        List<T> np = new LinkedList<>();

        // Keep the fitness if elitism is required.
        if (elitism)
            np.add(p.get(0));
        // Apply variation.
        int i = 0;
        while (i < popSize) {
            if (p.size() == 0 || (varOp1.size() == 0 && varOp2.size() == 0))
                break;
            T individual;
            if (varOp1.size() == 0) {
                individual = randFrom(varOp2).apply(randFrom(p), randFrom(p));
            } else if (varOp2.size() == 0 || rand(0, 1) == 0) {
                individual = randFrom(varOp1).apply(randFrom(p));
            } else {
                individual = randFrom(varOp2).apply(randFrom(p), randFrom(p));
            }
            np.add(individual);
            ++i;
        }
        return np;
    }

    /**
     * Run the Genetic Algorithm.
     * @param maxI the number of generation to run.
     * @param ps the population size.
     * @param n the number of solutions to return.
     * @param ss the strength of selection (zero => no death & 1 => no survivor).
     * @return the best solutions found.
     */
    public
    List<T> run(final int maxI, final int ps, final int n, final float ss, final boolean elitism) {
        if (ss < 0 || ss > 1 || ps < 0) {
            return null;
        }
        List<T> p = generation(ps);
        int i = 0;
        while (i < maxI) {
            p = selection(p, (int)(ps * ss));
            p = variation(p, ps, elitism);
            ++i;
        }
        return selection(p, n);
    }
}
