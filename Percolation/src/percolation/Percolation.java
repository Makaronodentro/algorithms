package percolation;

/**
 * Created by Makaronodentro on 27/11/2015.
 */

import weightedUnionFind.UnionFind;

public class Percolation {

    // Size of grid
    private int gridSize;

    // isOpen monitors if a site is open/closed
    private boolean[] isOpen;

    // monitors percolation between sites. Connected and open sites percolate.
    private UnionFind percolation;

    // tracks fullness without backwash
    private UnionFind fullness;

    // Index connected to entire top. (0)
    private int topIndex;
    // Index connected to entire bot. (N*N+1)
    private int botIndex;

    // converts between two dimensional coordinate system and site array index.
    // throws exceptions on invalid bounds. valid indices are 1 : N^2
    // i is the row; j is the column
    private int siteIndex(int i, int j){
        checkBounds(i, j);
        int x = j;
        int y = i;
        return (y - 1) * gridSize + (x);
    }


    // Throw a java.lang.IndexOutOfBoundsException if either i or j is outside this range.
    private void checkBounds(int i, int j){
        if (i>gridSize || i < 1 )
        {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        if (j>gridSize || j<1)
        {
            throw new IndexOutOfBoundsException("column index j out of bounds");
        }
    }

    // create N-by-N grid, with all sites blocked
    public Percolation(int N){
        if(N < 1){
            throw new IllegalArgumentException("Illegal grid size");
        }

        gridSize = N;
        int arraySize = N*N+2; // open arraySize +2 for top and bot
        isOpen = new boolean[arraySize];

        topIndex = 0;
        botIndex = N * N + 1;

        isOpen[topIndex] = true; // Open top
        isOpen[botIndex] = true; // Open bot

        percolation = new UnionFind(arraySize);
        fullness = new UnionFind(arraySize);

        for(int j = 1; j <= N;j++){
            // Connect top row to topIndex
            int i = 1;
            int topSiteIndex = siteIndex(i, j);
            percolation.union(topIndex, topSiteIndex);
            fullness.union(topIndex, topSiteIndex);

            // Connect bot row to topIndex
            i = N;
            int botSiteIndex = siteIndex(i, j);
            percolation.union(botIndex, botSiteIndex);
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j){
        int siteIndex = siteIndex(i, j);
        if(!isOpen[siteIndex]){
            // Open site
            isOpen[siteIndex] = true;

            /// Then connect to four neighbours. Only the open ones.

            // Left
            // check that it is not on the left edge and and that it is open
            if(j > 1 && isOpen(i, j-1)){
                int indexLeft = siteIndex(i, j-1);
                percolation.union(siteIndex, indexLeft);
                fullness.union(siteIndex, indexLeft);
            }

            // Right
            // check that it is not on the right edge and and that it is open
            if(j < gridSize && isOpen(i, j+1)){
                int indexRight = siteIndex(i, j+1);
                percolation.union(siteIndex, indexRight);
                fullness.union(siteIndex, indexRight);
            }

            // Top
            // check that it is not on the top edge and and that it is open
            if(i > 1 && isOpen(i - 1, j)){
                int indexTop = siteIndex(i - 1, j);
                percolation.union(siteIndex, indexTop);
                fullness.union(siteIndex, indexTop);
            }

            // Bot
            // check that it is not on the bot edge and and that it is open
            if(i < gridSize && isOpen(i + 1, j)){
                int indexBot = siteIndex(i + 1, j);
                percolation.union(siteIndex, indexBot);
                fullness.union(siteIndex, indexBot);
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j){
        int siteIndex = siteIndex(i, j);
        return isOpen[siteIndex];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j){
        int siteIndex = siteIndex(i, j);
        return (fullness.connected(topIndex,siteIndex) && isOpen[siteIndex]);
    }

    // does the system percolate?
    public boolean percolates(){
        if(gridSize > 1){
            return percolation.connected(topIndex, botIndex);
        }
        else{
            return isOpen[siteIndex(1,1)];
        }
    }


    // Test percolates()
    public static void main(String[] args){

        Percolation percolation = new Percolation(1);
        System.out.println(percolation.percolates());
        percolation.open(1,1);

        System.out.println(percolation.percolates());
        Percolation percolation2 = new Percolation(2);

        System.out.println(percolation2.percolates());
        percolation2.open(1,1);

        System.out.println(percolation2.percolates());
        percolation2.open(2,1);

        System.out.println(percolation2.percolates());
    }
}