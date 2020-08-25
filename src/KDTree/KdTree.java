package KDTree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {

    private class Node {
        private final Point2D point;
        private final boolean isVert;
        private Node left;
        private Node right;

        private Node(Point2D point, boolean isVert) {
            this.point = point;
            this.isVert = isVert;
            this.left = null;
            this.right = null;
        }

        public Point2D getPoint() { return this.point; }

        public boolean isVert() { return this.isVert; }

        public Node getLeft() { return this.left; }

        public Node getRight() { return this.right; }

        public void setLeft(Node left) { this.left = left; }

        public void setRight(Node right) { this.right = right; }
    }

    private Node head;
    private int count;

    private boolean insert(Point2D p, Node n) {
        if (p == null) throw new IllegalArgumentException();
        if (p.compareTo(n.getPoint()) == 0) return false;

        if (n.isVert()) {
            if (p.x() < n.getPoint().x()) {
                if (n.getLeft() == null) {
                    n.setLeft(new Node(p, !n.isVert()));
                    return true;
                }
                return insert(p, n.getLeft());
            } else {
                if (n.getRight() == null) {
                    n.setRight(new Node(p, !n.isVert()));
                    return true;
                }
                return insert(p, n.getRight());
            }
        } else {
            if (p.y() < n.getPoint().y()) {
                if (n.getLeft() == null) {
                    n.setLeft(new Node(p, !n.isVert()));
                    return true;
                }
                return insert(p, n.getLeft());
            } else {
                if (n.getRight() == null) {
                    n.setRight(new Node(p, !n.isVert()));
                    return true;
                }
                return insert(p, n.getRight());
            }
        }
    }

    private static boolean contains(Point2D p, Node n) {
        if (p == null) throw new IllegalArgumentException();
        if (n == null) return false;
        if (n.getPoint().compareTo(p) == 0) return true;

        if (n.isVert()) {
            if (p.x() < n.getPoint().x()) return contains(p, n.getLeft());
            else return contains(p, n.getRight());
        } else {
            if (p.y() < n.getPoint().y()) return contains(p, n.getLeft());
            else return contains(p, n.getRight());
        }
    }

    private static void draw(Node n) {
        if (n == null) return;
        n.getPoint().draw();
        draw(n.getLeft());
        draw(n.getRight());
    }

    private static boolean intersectLeft(RectHV rect, Node n) {
        if (n == null) return false;
        if (n.isVert()) return rect.xmin() < n.getPoint().x();
        else return rect.ymin() < n.getPoint().y();
    }

    private static boolean intersectRight(RectHV rect, Node n) {
        if (n == null) return false;
        if (n.isVert()) return rect.xmax() > n.getPoint().x();
        else return rect.ymax() > n.getPoint().y();
    }

    private static void range(RectHV rect, Node n, ArrayList<Point2D> points) {
        if (n == null) return;
        if (rect.contains(n.getPoint())) points.add(n.getPoint());
        if (intersectLeft(rect, n)) range(rect, n.getLeft(), points);
        if (intersectRight(rect, n)) range(rect, n.getRight(), points);
    }

    private static Point2D nearest(Point2D p, Point2D nearest, Node n) {
        if (p == null) throw new IllegalArgumentException();
        if (n == null) return nearest;
        if (n.isVert()) { // left/right
            if (p.x() < n.getPoint().x()) { // left sub tree
                Point2D checkLeft = nearest(p, n.getPoint().distanceSquaredTo(p) < nearest.distanceSquaredTo(p) ? n.getPoint() : nearest, n.getLeft());
                if (checkLeft.distanceSquaredTo(p) > Math.abs(n.getPoint().x() - p.x())) {
                    // if left is unfufilled, check right
                    Point2D checkRight = nearest(p, checkLeft, n.right);
                    return checkRight.distanceSquaredTo(p) > checkLeft.distanceSquaredTo(p) ? checkLeft : checkRight;
                } else return checkLeft;

            } else { // right sub tree
                Point2D checkRight = nearest(p, n.getPoint().distanceSquaredTo(p) < nearest.distanceSquaredTo(p) ? n.getPoint() : nearest, n.getRight());
                if (checkRight.distanceSquaredTo(p) > Math.abs(n.getPoint().x() - p.x())) {
                    // if right is unfufilled, check left
                    Point2D checkLeft = nearest(p, checkRight, n.left);
                    return checkRight.distanceSquaredTo(p) > checkLeft.distanceSquaredTo(p) ? checkLeft : checkRight;
                } else return checkRight;
            }
        } else { // top/bottom
            if (p.y() < n.getPoint().y()) { // bottom sub tree
                Point2D checkDown = nearest(p, n.getPoint().distanceSquaredTo(p) < nearest.distanceSquaredTo(p) ? n.getPoint() : nearest, n.getLeft());
                if (checkDown.distanceSquaredTo(p) > Math.abs(n.getPoint().x() - p.x())) {
                    // if down is unfufilled, check up
                    Point2D checkUp = nearest(p, checkDown, n.right);
                    return checkUp.distanceSquaredTo(p) > checkDown.distanceSquaredTo(p) ? checkDown : checkDown;
                } else return checkDown;
            } else { // top sub tree
                Point2D checkUp = nearest(p, n.getPoint().distanceSquaredTo(p) < nearest.distanceSquaredTo(p) ? n.getPoint() : nearest, n.getRight());
                if (checkUp.distanceSquaredTo(p) > Math.abs(n.getPoint().x() - p.x())) {
                    // if up is unfufilled, check down
                    Point2D checkDown = nearest(p, checkUp, n.left);
                    return checkUp.distanceSquaredTo(p) > checkDown.distanceSquaredTo(p) ? checkDown : checkUp;
                } else return checkUp;
            }
        }
    }

    public KdTree() {
        this.head = null;
        this.count = 0;
    } // construct an empty set of points

    public boolean isEmpty() { return head == null; } // is the set empty?

    public int size() { return count; } // number of points in the set

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (head == null) {
            head = new Node(p, true);
            count++;
        } else {
            if (insert(p, head)) count++;
        }
    }

    public boolean contains(Point2D p) { return contains(p, head); } // does the set contain point p?

    public void draw() { draw(head); } // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> inRange = new ArrayList<>();
        range(rect, head, inRange);
        return inRange;
    }

    public Point2D nearest(Point2D p) { return isEmpty() ? null : nearest(p, head.getPoint(), head); } // a nearest neighbor in the set to point p; null if the set is empty
}