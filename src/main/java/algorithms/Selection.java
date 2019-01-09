package algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Implementation of the positive & negative selection algorithms.
 */
public
class Selection {

    /**
     * This class define the configuration of the Positive Selection Algorithm.
     * @param <T>
     */
    public static
    class Config<T> {
        private int n;
        private Float s;
        private List<T> p;
        private Supplier<T> gf;
        private BiFunction<T, T, Float> sf;

        /**
         * Construct the configuration.
         * @param n is the number of detectors to generate.
         * @param s is the similarity required for a detector to be considered as close.
         * @param p is the list of pattern from which new detectors will be derived.
         * @param gf is the function used to generate new detector/shape.
         * @param sf is the function used to compute the similarity between two patterns.
         */
        public
        Config(
            final int n,
            final Float s,
            final List<T> p,
            final Supplier<T> gf,
            final BiFunction<T, T, Float> sf
        ) {
            this.n = n;
            this.s = s;
            this.p = p;
            this.gf = gf;
            this.sf = sf;
        }
    }

    /**
     * Run the Positive Selection Algorithm.
     * @param config is the configuration of the algorithm.
     * @param <T> is the patterns' type.
     * @return the list of detectors with high similarity with at least one of the configuration's pattern.
     */
    public static <T>
    List<T> positive(Config<T> config) {
        List<T> ds = new LinkedList<>();
        int n = 0;
        while (n < config.n) {
            T d = config.gf.get();
            Double maxSimilarity = config.p.stream()
                    .map((x) -> config.sf.apply(x, d))
                    .mapToDouble(Float::valueOf)
                    .max()
                    .orElse(Double.MIN_VALUE);
            if (maxSimilarity >= config.s) {
                ds.add(d);
                n++;
            }
        }
        return ds;
    }

    /**
     * Run the Negative Selection Algorithm.
     * @param config the configuration of the algorithm.
     * @param <T> the patterns' type.
     * @return the list of detectors with a low similarity with all the configuration's patterns.
     */
    public static <T>
    List<T> negative(Config<T> config) {
        List<T> ds = new LinkedList<>();
        int n = 0;
        while (n < config.n) {
            T d = config.gf.get();
            Double maxSimilarity = config.p.stream()
                    .map((x) -> config.sf.apply(x, d))
                    .mapToDouble(Float::valueOf)
                    .max()
                    .orElse(Double.MIN_VALUE);
            if (maxSimilarity <= config.s) {
                ds.add(d);
                n++;
            }
        }
        return ds;
    }
}
