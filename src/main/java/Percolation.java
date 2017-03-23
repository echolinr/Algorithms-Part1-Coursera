import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by macbook on 1/22/17.
 */
public class Percolation {
    private WeightedQuickUnionUF uf; // declare 1-dimensional array with identifier the same as its index.
    private int n;      // n is the number of objects on one side of the n*n grid
    private boolean[] status;      // status "true" means this site is open ; "false" means it is blocked.

    /**
     * create n-by-n grid, with all sites blocked
     */

    public Percolation(int n) {

        if (n <= 0)
            throw new IllegalArgumentException("n or numberOfTrials cannot be less than n");

        this.n = n;
        int siteCount = n * n;
        uf = new WeightedQuickUnionUF(siteCount + 2);
        status = new boolean[n * n + 2];

        for (int i = 1; i < siteCount + 2; i++) {
            status[i] = false;
        }
        status[0] = true;
        status[siteCount + 1] = true;

    }

    /**
     * row and col begins with 0, index is the corresponding UF array index.
     */

    private int index(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IndexOutOfBoundsException("the row or/and col number is out of boundary");
        }
        return ((row -1) * n + col);
    }


//    private void displayStatus() {
//        for (int i = 0; i < n * n + 2; i++) {
//            StdOut.print(i);
//            StdOut.print(":");
//            if (status[i]) {
//                StdOut.print("1");
//            } else {
//                StdOut.print("0");
//            }
//            StdOut.print(" ");
//        }
//        StdOut.print('\n');
//    }
// open site (row, col) if it is not open already

    public void open(int row, int col) {
        if (status[index(row, col)]) {
            return;
        }


        status[index(row, col)] = true;
        // StdOut.println("open:" +row+","+col);
        // displayStatus();


        // connect to the site above
        if (row != 1 && isOpen(row - 1, col)) {
            uf.union(index(row - 1, col), index(row, col));
            // StdOut.println("union:" +(row-1)+","+col+"--"+row+","+col);
        }

        // connect to the site left
        if (col != 1 && isOpen(row, col - 1)) {
            uf.union(index(row, col - 1), index(row, col));
            // StdOut.println("union:" +row+","+(col-1)+"--"+row+","+col);
        }

        // connect to the site right
        if (col < n  && isOpen(row, col + 1)) {
            uf.union(index(row, col + 1), index(row, col));
            // StdOut.println("union:" +row+","+(col+1)+"--"+row+","+col);
        }

        // connect to the site below
        if (row < n  && isOpen(row + 1, col)) {
            // displayStatus();
            uf.union(index(row + 1, col), index(row, col));
            // StdOut.println("union:" +(row+1)+","+col+"--"+row+","+col);
            // displayStatus();
        }

        // check first row
        if (row == 1) {
            uf.union(0, index(row, col));
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return status[index(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return uf.connected(index(row, col), 0);
    }

    // number of open sites
    public int numberOfOpenSites() {
        int num = 0;
        for (int i = 1; i <= n * n; i++) {
            if (status[i]) {
                num++;
            }
        }
        return num;
    }

    // does the system percolate?
    public boolean percolates() {

        for (int col = 1; col <= n; col++) {
            if (isFull(n, col)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 1);

        StdOut.println("# of opened sites: " + p.numberOfOpenSites());
        StdOut.println("Percolated: " + p.percolates());

    }  // test client (optional)


}
