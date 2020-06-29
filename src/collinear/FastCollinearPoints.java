package collinear;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        if (points == null) throw new IllegalArgumentException();
        Point[] auxArray = Arrays.copyOf(points, points.length);
        isInvalid(auxArray);
        ArrayList<LineSegment> segmentsInPlace = new ArrayList<>();
        for (Point dataPoint : points) {
            Point head = dataPoint; // highest point in the collinear line
            Point tail = dataPoint; // lowest point in the collinear line

            Arrays.sort(auxArray, dataPoint.slopeOrder());
            int length = 1;
            for (int i = 1; i < auxArray.length; i++) {
                double slope = dataPoint.slopeTo(auxArray[i]);
                if (i == auxArray.length - 1) {
                    double trailing = dataPoint.slopeTo(auxArray[i - 1]);
                    if (auxArray[i].compareTo(head) == 1 && slope == trailing) {
                        head = auxArray[i];
                        length++;
                    }
                    if (auxArray[i].compareTo(tail) == -1 && slope == trailing) {
                        tail = auxArray[i];
                        length++;
                    }
                } else {
                    double trailing = dataPoint.slopeTo(auxArray[i - 1]);
                    double leading = dataPoint.slopeTo(auxArray[i + 1]);
                    if (auxArray[i].compareTo(head) == 1 && (slope == trailing || slope == leading)) {
                        head = auxArray[i];
                        length++;
                    }
                    if (auxArray[i].compareTo(tail) == -1 && (slope == trailing || slope == leading)) {
                        tail = auxArray[i];
                        length++;
                    }
                    if (slope == trailing && slope != leading)
                        break;
                }
            }
            if (length >= 3) {
                LineSegment newSegment = new LineSegment(tail, head);
                if (!isThere(segmentsInPlace, newSegment))
                    segmentsInPlace.add(newSegment);
            }
        }
        this.segments = segmentsInPlace.toArray(new LineSegment[segmentsInPlace.size()]);
    }

    private static boolean isThere(ArrayList<LineSegment> data, LineSegment toAdd) {
        String stringData = toAdd.toString();
        for (LineSegment line : data)
            if (line.toString().compareTo(stringData) == 0)
                return true;
        return false;
    }

    public int numberOfSegments() { // the number of line segments
        return this.segments.length;
    }

    public LineSegment[] segments() { // the line segments
        return this.segments;
    }

    private static void isInvalid(Point[] points) {
        for (Point p : points)
            if (p == null) throw new IllegalArgumentException();
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++)
            if (points[i].compareTo(points[i - 1]) == 0) throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        Point[] points = {new Point(10000, 0), new Point(0, 10000), new Point(3000, 7000), new Point(7000, 3000), new Point(20000, 21000), new Point(3000, 4000), new Point(14000, 15000), new Point(6000, 7000)};
        Point[] points2 = {new Point(19000, 10000), new Point(18000, 10000), new Point(32000, 10000), new Point(21000, 10000), new Point(1234, 5678), new Point(14000, 10000)};
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
