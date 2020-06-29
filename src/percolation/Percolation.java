package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] isOpen; // used to track open sites
    private static int top = 0; // where percolation originates
    private int bottom; // where the percolation is supposed to end up
    private int size; // the size of 1 side of the model
    private int opened = 0; // tracks how many of the sites have had open called on it
    private WeightedQuickUnionUF UF; // used for unions

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.isOpen = new boolean[n][n];
        this.bottom = n * n + 1;
        this.size = n;
        this.UF = new WeightedQuickUnionUF(n * n + 2); // qf for data + 2 holding for top and bottom
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // indicate that site is now open
        this.isOpen[row - 1][col - 1] = true;
        this.opened++;
        // bind the site to holding variables for top and bottom if they are connected
        if (row == 1) {
            this.UF.union(remap(row, col), this.top);
        }
        if (row == size) {
            this.UF.union(remap(row, col), this.bottom);
        }
        // Try to see if any sites near the site are open then connect them
        if (col > 1 && isOpen(row, col - 1)) // if it is not a left edge case, check the left site to see if it is open
            this.UF.union(remap(row, col), remap(row, col - 1));
        if (col < this.size && isOpen(row, col + 1)) // if it is not a right edge case, check the right site to see if it is open
            this.UF.union(remap(row, col), remap(row, col + 1));
        if (row > 1 && isOpen(row - 1, col)) // if it is not a top row case, check the top site to see if it is open
            this.UF.union(remap(row, col), remap(row - 1, col));
        if (row < this.size && isOpen(row + 1, col)) // if it is not a bottom row case, check the bottom site to see if it is open
            this.UF.union(remap(row, col), remap(row + 1, col));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // readjust back 1 since data starts at 0, 0
        return this.isOpen[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return this.UF.connected(this.top, remap(row, col)); // see if the top and the site are sharing the same root therefore connected
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.opened;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.UF.connected(this.top, this.bottom); // see if the top and bottom are connected to the same root, therefore there is flow from top to site
    }

    // remaps the 2d array coordinates to the 1d union-find object
    private int remap(int row, int col) {
        return this.size * (row - 1) + col; // move row - 1 rows forward and then advance col columns
    }
}
