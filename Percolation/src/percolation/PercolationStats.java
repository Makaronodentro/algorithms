package percolation;

/**
 * Created by Makaronodentro on 27/11/2015.
 */

import java.util.Random;
import java.lang.Math;
import java.util.Scanner;

public class PercolationStats {
    // Stores each experiment's resulting percolation threshold
    private double[] thresholds;
    // Number of experiments
    private int T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T){
        // Check input
        if (N < 1 || T < 1){
            throw new IllegalArgumentException("Grid size and number of experiments must be greater than 1");
        }

        // Initialize thresholds array.
        this.T = T;
        thresholds = new double[T];

        // Initialise T percolations
        for(int t = 0; t < T; t++){
            Percolation percolation = new Percolation(N);
            int openSites = 0;

            // Random number ge
            Random random = new Random();

            // Keep opening sites until system percolates
            while(!percolation.percolates()){
                int i = random.nextInt(N) + 1; // Range 1..N
                int j = random.nextInt(N) + 1;

                if(!percolation.isOpen(i, j)){
                    percolation.open(i, j);
                    openSites += 1;
                }
            }

            // Record threshold result
            thresholds[t] = (double)openSites/(double)(N*N);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        double sum = 0;
        for(double threshold : thresholds) sum += threshold;

        return sum/(double)T;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        double var = 0; // Variance variable
        double mean = mean(); // Mean

        // calculate variance
        for(double t : thresholds) var += (mean - t) * (mean - t);
        var /= (double)T;

        return Math.sqrt(var);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - (1.96*stddev()/Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + (1.96*stddev()/Math.sqrt(T));
    }

    // test client (described below)
    public static void main(String[] args){
        // Input stream
        Scanner reader = new Scanner(System.in);

        System.out.println("Enter grid size: ");
        int N = reader.nextInt();
        System.out.println("Enter number of tests: ");
        int T = reader.nextInt();

        PercolationStats stats = new PercolationStats(N, T);

        System.out.println("mean = " + stats.mean());
        System.out.println("standard deviation = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + " | " + stats.confidenceHi());
    }
}
