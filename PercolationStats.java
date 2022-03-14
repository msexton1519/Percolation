import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/*
   This class is designed to create an object that performs a Monte Carlo
   simulation on an n x n grid and calculates this object's mean and
   standard deviation in order to give an interval where the percolation
   threshold lies within 95% accuracy.

   Note: Since it is unknown whether an exact value exists for a grid to
   percolate, it must be estimated.
 */
public class PercolationStats {
    private double[] samples;
    private int sampleNum;

    // The constructor Performs a Monte Carlo simulation on an n x n grid to estimate
    // the percolation threshold value.
    public PercolationStats(int n, int trials) {
        // Trials and the number n must be greater than zero, (n being length of grid).
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        samples = new double[trials];
        Percolation perc;
        int row;
        int col;
        for (int i = 1; i <= trials; i++) {
            perc = new Percolation(n);
            while (!perc.percolates()) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                perc.open(row, col);
            }
            samples[sampleNum] = (double) perc.numberOfOpenSites() / (n * n);
            sampleNum++;
        }
    }

    // Calculates the sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(samples);
    }

    // Calculates the sample standard deviation of percolation threshold
    public double stddev() {
        if (samples.length == 1) {
            return Double.NaN;
        }

        return StdStats.stddev(samples);
    }

    // low endpoint of 95% confidence interval for percolation threshold.
    public double confidenceLo() {
        return mean() - (1.95 * stddev() / (Math.sqrt(samples.length)));
    }

    // high endpoint of 95% confidence interval for percolation threshold.
    public double confidenceHi() {
        return mean() + (1.95 * stddev() / (Math.sqrt(samples.length)));
    }

    /*
        Tests to verify accuracy of the methods and class constructor.
        Main method takes two command line arguments, first is n, second is number
        of trials.
    */
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
                                                   Integer.parseInt(args[1]));
        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.mean());
        System.out.println(
                "95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
