package algorithms;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The Adelman Computing for finding a path passing through all nodes of graph only once.
 */
public class AdelmanComputing {

    private Set<Integer> nodes = new HashSet<>();
    private List<Pair<Integer, Integer>> paths = new LinkedList<>();

    /**
     * Add a node into the graph.
     * @param index the index of the node to add.
     */
    public
    void addNode(int index) {
        nodes.add(index);
    }

    /**
     * Add a path into the graph.
     * @param departure the departure node.
     * @param arrival the arrival node.
     */
    public
    void addPath(int departure, int arrival) {
        if (!nodes.contains(departure) || !nodes.contains(arrival))
            return;
        paths.add(new Pair<>(departure, arrival));
    }

    /**
     * Run the Adelman Algorithm.
     * @param departure the departure node.
     * @param arrival the arrival node.
     */
    public
    void run(int departure, int arrival) {
        List<Integer[]> generatedPaths = generatePaths(100);
        List<Integer[]> validPaths = generatedPaths.stream()
                .filter((Integer[] x) -> x[0] == departure && x[x.length - 1] == arrival)
                .filter((Integer[] x) -> x.length == nodes.size())
                .filter((Integer[] x) -> {
                    boolean res = true;
                    for (Integer e: x) {
                        if (!nodes.contains(e))
                            res = false;
                    }
                    return res;
                })
                .collect(Collectors.toList());
        if (validPaths.isEmpty()) {
            System.out.println("No solutions found.");
        } else {
            displayPaths(validPaths);
        }
    }

    /**
     * Generate one path of the graph.
     * @return the generated path.
     */
    private
    Integer[] generatePath() {
        // Create the random number generator.
        Random r = new Random();
        // Create the path.
        List<Integer> path = new LinkedList<>();
        // Initialize the current node.
        int index = Math.abs(r.nextInt() % nodes.size());
        Integer currNode = nodes.toArray(new Integer[0])[index];
        // Generate the full path.
        for (int i = 0; i < nodes.size() + (r.nextInt() % 3); i++) {
            // Set i-th element of the path.
            path.add(currNode);
            // Select the next element of the path.
            final Integer node = currNode;
            List<Pair<Integer, Integer>> pPaths = paths.stream()
                    .filter((x) -> x.getKey().equals(node))
                    .collect(Collectors.toList());
            if (pPaths.size() == 0)
                break;
            index = Math.abs(r.nextInt() % pPaths.size());
            currNode = pPaths.get(index).getValue();
        }
        return path.toArray(new Integer[0]);
    }

    /**
     * Generate paths in the graph.
     * @param n is the number of paths to generate.
     * @return the generated paths.
     */
    private
    List<Integer[]> generatePaths(final int n) {
        final List<Integer[]> gPaths = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            gPaths.add(generatePath());
        }
        return gPaths;
    }

    /**
     * Display the paths passed as parameter.
     * @param paths the paths to display.
     */
    private
    void displayPaths(final List<Integer[]> paths) {
        for (Integer[] path: paths) {
            for (Integer i: path) {
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
