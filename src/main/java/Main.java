import algorithms.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Program entry point.
 */
public
class Main {

    /**
     * Examples of the algorithms available.
     * @param args are unused.
     */
    public static
    void main(String[] args) {
        // 001 - Example of the positive selection algorithm.
        /*
        Selection.Config<Integer> config = new Selection.Config<>(
                10,
                0F,
                Arrays.asList(1, 2, 3),
                () -> new Random().nextInt() % 10,
                (x1, x2) -> -1 * (float)Math.pow(x1 - x2, 2)
        );
        List<Integer> ps = Selection.positive(config);
        ps.forEach(System.out::println);
        */

        // 002 - Example of the negative selection algorithm.
        /*
        Selection.Config<Integer> config = new Selection.Config<>(
                10,
                -1F,
                Arrays.asList(1, 2, 3),
                () -> new Random().nextInt() % 10,
                (x1, x2) -> -1 * (float)Math.pow(x1 - x2, 2)
        );
        List<Integer> ps = Selection.negative(config);
        ps.forEach(System.out::println);
        */

        // 003 - Example of the tower sampling with rates.
        /*
        float[] rates = new float[]{1F, 1F, 5F};
        for (int i = 0; i < 10; i++) {
            System.out.println(TowerSampling.rate(rates));
        }
        */

        // 004 - Example of the tower sampling with probabilities.
        /*
        float[] probabilities = new float[]{0.1F, 0.8F, 0.1F};
        for (int i = 0; i < 10; i++) {
            System.out.println(TowerSampling.probability(probabilities));
        }
        */

        // 005 - Example of the Gillespie Algorithm.
        /*
        //
        // This class is a Gillespie system with only two reactions:
        // - A(1) + B(1) => C(1)
        // - C(1) => A(1) + B(1)
        //
        class BasicGillespieSystem extends Gillespie.GillespieSystem {

            private float[] rates = new float[]{1F, 5F};
            private int na = 100;
            private int nb = 100;
            private int nc = 0;

            public
            void print(String time) {
                System.out.println("T = " + time);
                System.out.println("na = " + na + ", nb = " + nb + ", nc = " + nc);
            }

            public
            void apply(int action) {
                if (action == 0) {
                    na--;
                    nb--;
                    nc++;
                } else if (action == 1) {
                    na++;
                    nb++;
                    nc--;
                } else {
                    System.out.println("Action is not valid.");
                }
            }

            public
            float[] getReactionsPropencity() {
                float[] p = new float[2];
                p[0] = rates[0] * na * nb;
                p[1] = rates[1] * nc;
                return p;
            }
        }

        Gillespie.GillespieSystem s = new BasicGillespieSystem();
        Gillespie.run(s, 100, 10);
        */

        // 006 - Example of the Cellular Automata Algorithm.
        /*
        CellularAutomata.run(10, 10);
        */

        // 007 - Example of the Adelman DNA Computing.
        /*
        AdelmanComputing a = new AdelmanComputing();
        a.addNode(0);
        a.addNode(1);
        a.addNode(2);
        a.addNode(3);
        a.addPath(0, 1);
        a.addPath(1, 2);
        a.addPath(0, 2);
        a.addPath(2, 3);
        a.run(0, 3);
        */

        // 008 - Example of the Genetic Algorithm.
        /*
        */
        // Letter operation
        Supplier<Character> randChar = () -> {
            String alphabet = "abcdefghijklmnopqrstuvwxyz !";
            Random r = new Random();
            return alphabet.charAt(r.nextInt(alphabet.length()));
        };

        // Individual generator
        Supplier<String> g = () -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 13; i++)
                sb.append(randChar.get());
            return sb.toString();
        };

        // Fitness function
        Function<String, Double> f = (String x) -> {
            Double fitness = 0.0;
            for (int i = 0; i < x.length(); i++) {
                fitness -= Math.abs(x.charAt(i) - "Hello world !".charAt(i));
            }
            return fitness;
        };

        // Mutator operation
        Function<String, String> m1 = (String x) -> {
            Random r = new Random();
            char[] ca = x.toCharArray();
            ca[r.nextInt(x.length())] = randChar.get();
            return String.valueOf(ca);
        };

        GeneticAlgorithm<String> ga = new GeneticAlgorithm<>(g, f);
        ga.addVariationOperator(m1);
        List<String> s = ga.run(1000, 1000, 10, 0.1F, true);
        s.forEach(System.out::println);
    }
}
