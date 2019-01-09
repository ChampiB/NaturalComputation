package algorithms;

import java.util.Random;

/**
 * This class emulate one kind of Cellular Automata Algorithm.
 */
public class CellularAutomata {
    /**
     * Run the Cellular Automata Algorithm.
     *
     * @param nbIter is the number of iteration to run.
     * @param width is the width of the line to emulate.
     */
    public static
    void run(int nbIter, int width) {
        Random r = new Random();
        int[] line = new int[width];
        for (int j = 0; j < line.length; j++)
            line[j] = Math.abs(r.nextInt() % 2);
        displayLine(line);
        for (int i = 0; i < nbIter; i++) {
            int[] newLine = new int[width];
            for (int j = 0; j < line.length; j++) {
                newLine[j] = applyRules(
                    getValue(line, j - 1),
                    getValue(line, j),
                    getValue(line, j + 1)
                );
            }
            line = newLine;
            displayLine(line);
        }
    }

    /**
     * Display the line in the standard output.
     * @param line the line to display.
     */
    private static
    void displayLine(int[] line) {
        for (int i = 0; i < line.length; i++)
            System.out.print(line[i]);
        System.out.println();
    }

    /**
     * Apply the rules of the Cellular Automata.
     * @param prev the (j - 1)-th line's value.
     * @param curr the j-th line's value.
     * @param next the (j + 1)-th line's value.
     * @return the transformed value with respect to the rules.
     */
    private static
    int applyRules(int prev, int curr, int next) {
        int[][] rules = new int[][]{
                {0, 0, 1, 1},
                {0, 0, 0, 1},
                {1, 1, 0, 0},
                {0, 1, 0, 0}
        };
        for (int[] rule: rules) {
            if (rule[0] == prev && rule[1] == curr && rule[2] == next)
                return rule[3];
        }
        return 0;
    }

    /**
     * Return the value of
     * Get the value at the position pos in the array a.
     * @param a the array.
     * @param pos the position.
     * @return the value or zero if the position is not valid.
     */
    private static
    int getValue(int[] a, int pos) {
        if (pos < 0 || pos >= a.length)
            return 0;
        return a[pos];
    }
}
