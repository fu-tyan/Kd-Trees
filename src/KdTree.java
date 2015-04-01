public class KdTree {

    private Node root;
    private int pointsNumber;

    private class Node {
        private Point2D nodePoint;
        private Node leftNode;
        private Node rightNode;
        private boolean isOdd;

        private Node (Point2D nodePoint, Node leftNode, Node rightNode, boolean isOdd) {
            this.nodePoint = nodePoint;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
            this.isOdd = isOdd;
        }
    }

    public KdTree() {
        root = null;
        pointsNumber = 0;
    } // construct an empty set of points

    public boolean isEmpty() {
        return (pointsNumber == 0);
    } // is the set empty?

    public int size() {
        return pointsNumber;
    } // number of points in the set

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("NULL!!!");
        }
        if (this.contains(p)) {
            return;
        }
        if (!this.isEmpty()) {
            root = new Node(p, null, null, true);
            pointsNumber++;
        } else {
            Node currentNode = root;
            while (currentNode != null) {
                if (currentNode.isOdd) {
                    if (currentNode.nodePoint.x() < p.x()) {
                        if (currentNode.rightNode == null) {
                            currentNode.rightNode = new Node(p, null, null, !currentNode.isOdd);
                            currentNode = null;
                            pointsNumber++;
                        } else {
                            currentNode = currentNode.rightNode;
                        }
                    } else {
                        if (currentNode.leftNode == null) {
                            currentNode.leftNode = new Node(p, null, null, !currentNode.isOdd);
                            currentNode = null;
                            pointsNumber++;
                        } else {
                            currentNode = currentNode.leftNode;
                        }
                    }
                } else {
                    if (currentNode.nodePoint.y() < p.y()) {
                        if (currentNode.rightNode == null) {
                            currentNode.rightNode = new Node(p, null, null, !currentNode.isOdd);
                            currentNode = null;
                            pointsNumber++;
                        } else {
                            currentNode = currentNode.rightNode;
                        }
                    } else {
                        if (currentNode.leftNode == null) {
                            currentNode.leftNode = new Node(p, null, null, !currentNode.isOdd);
                            currentNode = null;
                            pointsNumber++;
                        } else {
                            currentNode = currentNode.leftNode;
                        }
                    }
                }
            }
        }
    } // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("NULL!!!");
        }
        if (this.isEmpty()) {
            return false;
        }
        Node currentNode = root;
        while (currentNode != null) {
            if (currentNode.nodePoint.equals(p)) {
                return true;
            }
            if (currentNode.isOdd) {
                if (currentNode.nodePoint.x() < p.x()) {
                    currentNode = currentNode.rightNode;
                } else {
                    currentNode = currentNode.leftNode;
                }
            } else {
                if (currentNode.nodePoint.y() < p.y()) {
                    currentNode = currentNode.rightNode;
                } else {
                    currentNode = currentNode.leftNode;
                }
            }
        }
        return false;
    } // does the set contain point p?

    public void draw() {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);

        for (Point2D p : pointSet) {
            p.draw();
        }
    } // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        rectPoints = new SET<Point2D>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                rectPoints.add(p);
            }
        }
        return rectPoints;
    } // all points that are inside the rectangle

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("NULL!!!");
        }
        double currentDistance = 100000;
        double newDistance = 100000;
        Point2D neighbor = null;
        for (Point2D currentPoint : pointSet) {
            newDistance = p.distanceTo(currentPoint);
            if (newDistance < currentDistance) {
                currentDistance = newDistance;
                neighbor = currentPoint;
            }
        }
        return neighbor;
    } // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }// unit testing of the methods (optional)
}
