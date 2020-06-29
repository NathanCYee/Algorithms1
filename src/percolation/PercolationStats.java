package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials; // tracks how many times percolation was run
    private double[] opened; // data for each trial

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        this.opened = new double[trials];

        for (int trial = 0; trial < trials; trial++) {
            Percolation p = new Percolation(n); // use this to run the trial
            // run until it percolates then collect the number of sites that needed to be open
            int open = 0;
            while (!p.percolates()) {
                int randRow = StdRandom.uniform(1, n + 1); // generate a random row within range
                int randCol = StdRandom.uniform(1, n + 1); // generate a random column within range

                if (!p.isOpen(randRow, randCol)) { // if there is an unopened site, open it and increase the tick
                    p.open(randRow, randCol);
                    open++;
                }
            }
            this.opened[trial] = (double) open / (Math.pow(n, 2)); // return the fraction of opened sites as the value for the trial
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.opened);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.opened);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (1.96 * this.stddev()) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (1.96 * this.stddev()) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationSim = new PercolationStats(n, trials);

        String confidenceInterval = "[" + percolationSim.confidenceLo() + ", " + percolationSim.confidenceHi() + "]";
        System.out.println("mean                    = " + percolationSim.mean());
        System.out.println("stddev                  = " + percolationSim.stddev());
        System.out.println("95% confidence interval = " + confidenceInterval);
    }
}
