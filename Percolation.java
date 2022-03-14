import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF percolates;
    private WeightedQuickUnionUF fullSites;
    private byte[] sites;
    private int sqrt;
    private int openSitesNum;

    /*
        Constructor creates an object that represents an n x n grid however two QuickUnion
        objects are created. One, to check whether this object has a connection from top to
        bottom and the other whether the grid percolates. This is designed to prevent backwash,
        (sites connected to the virtual bottom but not connected to the top layer).
        Site 0 is a virtual top and sites n*n + 1 and n*n + 2 are virtual bottom points
        for fullSites and percolates respectively.
     */
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();
        fullSites = new WeightedQuickUnionUF(n * n + 1);
        percolates = new WeightedQuickUnionUF(n * n + 2);
        sites = new byte[n * n];
        sqrt = n;
    }

    // Opens a site for the current n x n grid.
    public void open(int row, int col) {
        validation(row, col);
        if (!isOpen(row, col)) {
            int temp = arr2Dto1D(row - 1, col);
            sites[arr2Dto1D(row - 1, col - 1)] = 1;
            // Connect to virtual top location if in top row of grid
            if (temp <= sqrt) {
                percolates.union(temp, 0);
                fullSites.union(temp, 0);
            }
            // Connect to virtual bottom location if in last row of grid
            if (temp > sqrt * (sqrt - 1)) {
                percolates.union(temp, sqrt * sqrt + 1);
            }
            // Connects this site to up, down, left, right of this site, if possible.
            openHelper(row - 1, col, temp);
            openHelper(row + 1, col, temp);
            openHelper(row, col - 1, temp);
            openHelper(row, col + 1, temp);
            openSitesNum++;
            /*
            if (inBounds(row - 1, col) && isOpen(row - 1, col)) {
                percolates.union(temp, twoDtoOne(row - 1, col));
                fullSites.union(temp, twoDtoOne(row - 1, col));
            }
            if (inBounds(row + 1, col) && isOpen(row + 1, col)) {
                percolates.union(temp, twoDtoOne(row + 1, col));
                fullSites.union(temp, twoDtoOne(row + 1, col));
            }
            if (inBounds(row, col - 1) && isOpen(row, col - 1)) {
                percolates.union(temp, twoDtoOne(row, col - 1));
                fullSites.union(temp, twoDtoOne(row, col - 1));
            }
            if (inBounds(row, col + 1) && isOpen(row, col + 1)) {
                percolates.union(temp, twoDtoOne(row, col + 1));
                fullSites.union(temp, twoDtoOne(row, col + 1));
            }
            */
        }
    }

    /*
        Helper to open() where (row, col) pair is checked for openness and
        boundedness. If both checks pass, connect temp to arr2Dto1D.
    */
    private void openHelper(int row, int col, int temp) {
        int arr2Dto1D = arr2Dto1D(row - 1, col);
        if (inBounds(row, col) && isOpen(row, col)) {
            percolates.union(temp, arr2Dto1D);
            fullSites.union(temp, arr2Dto1D);
        }
    }

    /*
        Verify that the location selected is in bounds. Since grid sites
        will have to check if sites above, below, left and right are open and
        connect them. However, edge grid sites will go out of bounds either
        on left or right, top or bottom, in which case, they need to be disregarded.
     */
    private boolean inBounds(int row, int col) {
        return row > 0 && col > 0 && row <= sqrt && col <= sqrt;
    }

    // Checks to see if a particular site is open: 1 or closed: 0.
    public boolean isOpen(int row, int col) {
        validation(row, col);
        return sites[arr2Dto1D(row - 1, col - 1)] == 1;
    }

    /*
        Checks to see if water from the top has reached the given (row, column) pair.
        The pair must be 1: open, 2: connected to the top layer by using find()
        for the (row, column) pair and the virtual top point. If they have the same
        value, they must be connected and if (row, column) is open, (row, column) is full.
     */
    public boolean isFull(int row, int col) {
        validation(row, col);
        return isOpen(row, col) && fullSites.find(arr2Dto1D(row - 1, col)) == fullSites.find(0);
    }

    public int numberOfOpenSites() {
        return openSitesNum;
    }

    /*
        Determines whether site percolates. ie) Does substance from top layer
        reach bottom layer?
        Note: sqrt * sqrt + 1 is the virtual bottom point.
    */
    public boolean percolates() {
        return percolates.find(sqrt * sqrt + 1) == percolates.find(0);
    }

    // Converts what would be a (row, col) pair to an index of a single dimensional array.
    private int arr2Dto1D(int row, int col) {
        return row * sqrt + col;
    }

    /*
        Validates whether row and column given is within 1 <= row, col <= n,
        otherwise throw an IllegalArgumentException.
     */
    private void validation(int row, int col) {
        if (row < 1 || row > sqrt || col < 1 || col > sqrt) throw new IllegalArgumentException();
    }
}
