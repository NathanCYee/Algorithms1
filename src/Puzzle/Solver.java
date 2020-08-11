package Puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private boolean isSolvable = false;
    private Node lastNode = null;

    // Node of the solution, used to wrap board with solution path, moves, and priority
    private class Node implements Comparable<Node> {
        Board board;
        int moves = 0;
        int priority;
        Node previous = null;

        // constructor for head Node
        private Node(Board board) {
            this.board = board;
            this.priority = this.board.manhattan() + this.moves;
        }

        private Node(Board board, Node previous) {
            this.board = board;
            this.moves = previous.moves + 1;
            this.priority = this.board.manhattan() + this.moves;
            this.previous = previous;
        }

        @Override
        public int compareTo(Node other) {
            return this.priority - other.priority;
        }
    }

    // check to make sure the board is not repeated in the path
    private boolean isInPlace(Board toAdd, Node parent) {
        if (parent.board.equals(toAdd)) return false;
        if (parent.previous == null) return true; // hit the end
        return isInPlace(toAdd, parent.previous); // move up the tree
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        if (initial.isGoal()) {
            isSolvable = true;
            lastNode = new Node(initial);
        } else {

            MinPQ<Node> twinSolutions = new MinPQ<>();
            twinSolutions.insert(new Node(initial.twin()));
            boolean twinSolvable = false;

            MinPQ<Node> solutions = new MinPQ<>();
            solutions.insert(new Node(initial));
            while (!isSolvable && !twinSolvable && !twinSolutions.isEmpty() && !solutions.isEmpty()) {
                Node twinHead = twinSolutions.delMin(); // get the smallest in the twin PQ
                Iterable<Board> twinNeighbors = twinHead.board.neighbors();
                for (Board neighbor : twinNeighbors)
                    if (isInPlace(neighbor, twinHead)) {
                        Node n = new Node(neighbor, twinHead);
                        twinSolutions.insert(n);
                        // found the goal, break and set the path
                        if (neighbor.isGoal()) {
                            twinSolvable = true;
                            break;
                        }
                    }

                Node head = solutions.delMin(); // get the smallest in the PQ

                Iterable<Board> neighbors = head.board.neighbors();
                for (Board neighbor : neighbors)
                    // critical optimization
                    if (isInPlace(neighbor, head)) {
                        Node n = new Node(neighbor, head);
                        solutions.insert(n);
                        // found the goal, break and set the path
                        if (neighbor.isGoal()) {
                            isSolvable = true;
                            this.lastNode = n;
                            break;
                        }
                    }
            }
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable ? lastNode.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable) return null;
        Stack<Board> path = new Stack<>();
        for (Node tracer = lastNode; tracer != null; tracer = tracer.previous) path.push(tracer.board);
        return path;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
