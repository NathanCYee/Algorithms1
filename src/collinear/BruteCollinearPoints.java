package collinear;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException();
        for (Point p : points)
            if (p == null) throw new IllegalArgumentException();
        Point[] auxArray = Arrays.copyOf(points, points.length);
        Arrays.sort(auxArray);
        isInvalid(auxArray);
        ArrayList<LineSegment> segmentsInPlace = new ArrayList<>();
        for (int p = 0; p < auxArray.length - 3; p++)
            for (int q = p + 1; q < auxArray.length - 2; q++)
                for (int r = q + 1; r < auxArray.length - 1; r++)
                    for (int s = r + 1; s < auxArray.length; s++)
                        if (auxArray[p].slopeTo(auxArray[q]) == auxArray[r].slopeTo(auxArray[s]) &&
                                auxArray[p].slopeTo(auxArray[q]) == auxArray[q].slopeTo(auxArray[r])) {
                            LineSegment newSegment = new LineSegment(auxArray[p], auxArray[s]);
                            if (!isThere(segmentsInPlace, newSegment))
                                segmentsInPlace.add(newSegment);
                        }
        this.segments = segmentsInPlace.toArray(new LineSegment[segmentsInPlace.size()]);
    }

    public int numberOfSegments() { // the number of line segments
        return this.segments.length;
    }

    public LineSegment[] segments() { // the line segments
        return this.segments;
    }

    private static boolean isThere(ArrayList<LineSegment> data, LineSegment toAdd) {
        String stringData = toAdd.toString();
        for (LineSegment line : data)
            if (line.toString().compareTo(stringData) == 0)
                return true;
        return false;
    }

    private static void isInvalid(Point[] points) {
        for (int i = 1; i < points.length; i++)
            if (points[i].compareTo(points[i - 1]) == 0) throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        Point[] points = new Point[8];
        points[0] = new Point(10000, 0);
        points[1] = new Point(0, 10000);
        points[2] = new Point(3000, 7000);
        points[3] = new Point(7000, 3000);
        points[4] = new Point(20000, 21000);
        points[5] = new Point(3000, 4000);
        points[6] = new Point(14000, 15000);
        points[7] = new Point(6000, 7000);
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
