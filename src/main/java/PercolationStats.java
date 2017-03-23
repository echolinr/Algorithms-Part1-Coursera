import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // private int n;
    private int numberOfTrials;
    private double[] fraction;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int numberOfTrials) {
        // this.n = n;
        if (n <= 0 || numberOfTrials <= 0)
            throw new IllegalArgumentException("n or numberOfTrials cannot be less than n");
//        this.n = n;                                  // mistake I made #2
        this.numberOfTrials = numberOfTrials;   // mistake I made #2
        fraction = new double[numberOfTrials]; // mistake #3: move constructor out of loop

        for (int i = 0; i < numberOfTrials; i++) {
            Percolation p = new Percolation(n);
            int siteCount = 0;
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    siteCount++;

                }
            }
            fraction[i] = (double) siteCount / (double) (n * n);
           // StdOut.println("fraction[" + (i) + "] = " + siteCount);
        }
    }


    public double mean() {
        return StdStats.mean(fraction);
    }                    // sample mean of percolation threshold

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fraction);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(numberOfTrials));
    }              // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(numberOfTrials));
    }              // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = 10;
        int numberOfTrials = 10;
        PercolationStats ps = new PercolationStats(n, numberOfTrials);
        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();

        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    } // test client (described below)
}