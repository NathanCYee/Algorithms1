package Puzzle;

import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[][] tiles; // board tiles

    private int[][] getArrangedTiles() {
        int[][] arrangedTiles = new int[tiles.length][tiles.length];
        int ticker = 1;
        for (int i = 0; i < tiles.length; i++)
            for (int j = 0; j < tiles.length; j++) {
                if (i == tiles.length - 1 && j == tiles.length - 1)
                    arrangedTiles[i][j] = 0;
                else
                    arrangedTiles[i][j] = ticker;
                ticker++;
            }
        return arrangedTiles;
    }

    // return a new board with places swapped
    private Board swap(int row1, int col1, int row2, int col2) {
        int[][] tempData = new int[dimension()][dimension()];
        for (int row = 0; row < dimension(); row++)
            for (int column = 0; column < dimension(); column++)
                tempData[row][column] = tiles[row][column];
        int temp = tempData[row1][col1];
        tempData[row1][col1] = tempData[row2][col2];
        tempData[row2][col2] = temp;
        return new Board(tempData);
    }

    // in the arranged tiles, get the row of the value
    private int getRow(int val) {
        int[][] arrangedTiles = getArrangedTiles();
        for (int row = 0; row < dimension(); row++)
            for (int column = 0; column < dimension(); column++)
                if (val != 0 && val == arrangedTiles[row][column])
                    return row;
        return 0;
    }

    // in the arranged tiles, get the row of the value
    private int getColumn(int val) {
        int[][] arrangedTiles = getArrangedTiles();
        for (int row = 0; row < dimension(); row++)
            for (int column = 0; column < dimension(); column++)
                if (val != 0 && val == arrangedTiles[row][column])
                    return column;
        return 0;
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        // some weird code to make sure it copies
        this.tiles = new int[tiles.length][tiles.length];
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles.length; column++)
                this.tiles[row][column] = tiles[row][column];
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(Integer.toString(dimension())); // print the dimensions
        output.append("\n");
        for (int row = 0; row < dimension(); row++) { // print each row
            for (int column = 0; column < dimension(); column++) // print each tile in the row
                output.append(" " + tiles[row][column]);
            output.append("\n");
        }
        return output.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int[][] arrangedTiles = getArrangedTiles();
        int miss = 0;
        for (int row = 0; row < dimension(); row++)
            for (int column = 0; column < dimension(); column++)
                if (tiles[row][column] != arrangedTiles[row][column] && tiles[row][column] != 0) // if not in place, increment
                    miss++;
        return miss;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int totalDistance = 0;
        for (int row = 0; row < dimension(); row++)
            for (int column = 0; column < dimension(); column++)
                if (tiles[row][column] != 0)  // add the row/column distances to the total
                    totalDistance += Math.abs(row - getRow(tiles[row][column])) + Math.abs(column - getColumn(tiles[row][column]));
        return totalDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int[][] arrangedTiles = getArrangedTiles();
        for (int row = 0; row < dimension(); row++)
            for (int column = 0; column < dimension(); column++)
                if (tiles[row][column] != arrangedTiles[row][column]) // if anything is out of place, break and return false
                    return false;
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        try {
            Board b = (Board) y;
            if (this.dimension() != b.dimension()) return false;
            int[][] compare = b.tiles;
            for (int row = 0; row < dimension(); row++)
                for (int column = 0; column < dimension(); column++)
                    if (tiles[row][column] != compare[row][column])
                        return false;
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int zeroRow = 0;
        int zeroCol = 0;
        for (int row = 0; row < dimension(); row++)
            for (int column = 0; column < dimension(); column++)
                if (tiles[row][column] == 0) {
                    zeroRow = row;
                    zeroCol = column;
                    break;
                }
        if (zeroCol - 1 >= 0)
            neighbors.push(swap(zeroRow, zeroCol, zeroRow, zeroCol - 1));
        if (zeroCol + 1 < dimension())
            neighbors.push(swap(zeroRow, zeroCol, zeroRow, zeroCol + 1));
        if (zeroRow - 1 >= 0)
            neighbors.push(swap(zeroRow, zeroCol, zeroRow - 1, zeroCol));
        if (zeroRow + 1 < dimension())
            neighbors.push(swap(zeroRow, zeroCol, zeroRow + 1, zeroCol));
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return swap(0, 0, 0, 1);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        Board b = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        System.out.println(b);
        System.out.println(b.dimension());
        System.out.println(b.hamming());
        System.out.println(b.manhattan());
        Board b2 = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        Board b3 = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        System.out.println(b2.isGoal());
        System.out.println(b.equals(b2));
        System.out.println(b.equals(b3));
        Board b4 = new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}});
        Iterable<Board> n = b4.neighbors();
        for (Board bo : n)
            System.out.println(bo);
    }
}
