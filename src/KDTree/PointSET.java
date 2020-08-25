package KDTree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> points;

    public PointSET() { this.points = new SET<Point2D>(); } // construct an empty set of points

    public boolean isEmpty() {return points.isEmpty(); } // is the set empty?

    public int size() { return points.size(); } // number of points in the set

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    public void draw() { for (Point2D point : this.points) point.draw(); } // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> inRect = new ArrayList<>();
        for (Point2D point : this.points) if (rect.contains(point)) inRect.add(point);
        return inRect;
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException();
        Point2D min = null;
        double minDistance = 0;
        for (Point2D point : this.points)
            if (min == null || point.distanceSquaredTo(p) < minDistance) {
                min = point;
                minDistance = point.distanceSquaredTo(p);
            }

        return min;
    }
}