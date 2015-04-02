import java.util.TreeSet;

public class KdTree {

    private Node root;
    private int pointsNumber;

    private TreeSet<Node> treeSet;

    private class Node {
        private Point2D nodePoint;
        private Node leftNode;
        private Node rightNode;
        private boolean isOdd;
        private RectHV rect;

        private Node(Point2D nodePoint, Node leftNode, Node rightNode, boolean isOdd, RectHV rect) {
            this.nodePoint = nodePoint;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
            this.isOdd = isOdd;
            this.rect = rect;
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
        if (this.isEmpty()) {
            root = new Node(p, null, null, true, new RectHV(0.00, 0.00, 1.00, 1.00));
            pointsNumber++;
        } else {
            Node currentNode = root;
            while (currentNode != null) {
                if (currentNode.isOdd) {
                    if (currentNode.nodePoint.x() <= p.x()) {
                        if (currentNode.rightNode == null) {
                            currentNode.rightNode = new Node(p, null, null, !currentNode.isOdd, new RectHV(currentNode.nodePoint.x(), currentNode.rect.ymin(), currentNode.rect.xmax(), currentNode.rect.ymax()));
                            currentNode = null;
                            pointsNumber++;
                        } else {
                            currentNode = currentNode.rightNode;
                        }
                    } else {
                        if (currentNode.leftNode == null) {
                            currentNode.leftNode = new Node(p, null, null, !currentNode.isOdd, new RectHV(currentNode.rect.xmin(), currentNode.rect.ymin(), currentNode.nodePoint.x(), currentNode.rect.ymax()));
                            currentNode = null;
                            pointsNumber++;
                        } else {
                            currentNode = currentNode.leftNode;
                        }
                    }
                } else {
                    if (currentNode.nodePoint.y() <= p.y()) {
                        if (currentNode.rightNode == null) {
                            currentNode.rightNode = new Node(p, null, null, !currentNode.isOdd, new RectHV(currentNode.rect.xmin(), currentNode.nodePoint.y(), currentNode.rect.xmax(), currentNode.rect.ymax()));
                            currentNode = null;
                            pointsNumber++;
                        } else {
                            currentNode = currentNode.rightNode;
                        }
                    } else {
                        if (currentNode.leftNode == null) {
                            currentNode.leftNode = new Node(p, null, null, !currentNode.isOdd, new RectHV(currentNode.rect.xmin(), currentNode.rect.ymin(), currentNode.rect.xmax(), currentNode.nodePoint.y()));
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
                if (currentNode.nodePoint.x() <= p.x()) {
                    currentNode = currentNode.rightNode;
                } else {
                    currentNode = currentNode.leftNode;
                }
            } else {
                if (currentNode.nodePoint.y() <= p.y()) {
                    currentNode = currentNode.rightNode;
                } else {
                    currentNode = currentNode.leftNode;
                }
            }
        }
        return false;
    } // does the set contain point p?

    public void draw() {
        if (this.isEmpty()) {
            return;
        }
        StdDraw.setXscale(-1, 2);
        StdDraw.setYscale(-1, 2);

        Node currentNode;
        Queue<Node> pointsQueue = new Queue<Node>();
        pointsQueue.enqueue(this.root);
        while (!pointsQueue.isEmpty()) {
            currentNode = pointsQueue.dequeue();
            if (currentNode.leftNode != null) {
                pointsQueue.enqueue(currentNode.leftNode);
            }
            if (currentNode.rightNode != null) {
                pointsQueue.enqueue(currentNode.rightNode);
            }
            if (currentNode.isOdd) {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(.01);
                currentNode.nodePoint.draw();
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(currentNode.nodePoint.x(), currentNode.rect.ymin(), currentNode.nodePoint.x(), currentNode.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(.01);
                currentNode.nodePoint.draw();
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(currentNode.rect.xmin(), currentNode.nodePoint.y(), currentNode.rect.xmax(), currentNode.nodePoint.y());
            }
        }
    } // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> rectPoints = new SET<Point2D>();
        if (this.isEmpty()) {
            return rectPoints;
        }

        Node currentNode;
        Queue<Node> pointsQueue = new Queue<Node>();
        pointsQueue.enqueue(this.root);
        while (!pointsQueue.isEmpty()) {
            currentNode = pointsQueue.dequeue();
            if (rect.contains(currentNode.nodePoint)) {
                rectPoints.add(currentNode.nodePoint);
            }
            if (currentNode.isOdd) {
                if (currentNode.leftNode != null && currentNode.nodePoint.x() >= rect.xmin()) {
                    pointsQueue.enqueue(currentNode.leftNode);
                }
                if (currentNode.rightNode != null && currentNode.nodePoint.x() <= rect.xmax()) {
                    pointsQueue.enqueue(currentNode.rightNode);
                }
            } else {
                if (currentNode.leftNode != null && currentNode.nodePoint.y() >= rect.ymin()) {
                    pointsQueue.enqueue(currentNode.leftNode);
                }
                if (currentNode.rightNode != null && currentNode.nodePoint.y() <= rect.ymax()) {
                    pointsQueue.enqueue(currentNode.rightNode);
                }
            }
        }
        return rectPoints;
    } // all points that are inside the rectangle

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("NULL!!!");
        }
        if (this.isEmpty()) {
            return null;
        }
        Node currentNode;
        Queue<Node> pointsQueue = new Queue<Node>();
        pointsQueue.enqueue(this.root);
        Point2D neighbor = this.root.nodePoint;
        double neighborDistance = p.distanceSquaredTo(neighbor);
        double newNeighborDistance;
        while (!pointsQueue.isEmpty()) {
            currentNode = pointsQueue.dequeue();
            newNeighborDistance = p.distanceSquaredTo(currentNode.nodePoint);
            if (newNeighborDistance < neighborDistance) {
                neighborDistance = newNeighborDistance;
                neighbor = currentNode.nodePoint;
            }
            if (currentNode.isOdd) {
                if (currentNode.leftNode != null && currentNode.leftNode.rect.distanceSquaredTo(p) < neighborDistance) {
                    pointsQueue.enqueue(currentNode.leftNode);
                }
                if (currentNode.rightNode != null && currentNode.rightNode.rect.distanceSquaredTo(p) < neighborDistance) {
                    pointsQueue.enqueue(currentNode.rightNode);
                }
            } else {
                if (currentNode.leftNode != null && currentNode.leftNode.rect.distanceSquaredTo(p) < neighborDistance) {
                    pointsQueue.enqueue(currentNode.leftNode);
                }
                if (currentNode.rightNode != null && currentNode.rightNode.rect.distanceSquaredTo(p) < neighborDistance) {
                    pointsQueue.enqueue(currentNode.rightNode);
                }
            }
        }
        return neighbor;
    } // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        KdTree kd = new KdTree();
        kd.insert(new Point2D(1.00, 1.00));
        kd.insert(new Point2D(0.00, 1.00));
        kd.insert(new Point2D(1.00, 0.00));
        kd.insert(new Point2D(0.00, 0.00));
        kd.insert(new Point2D(0.5, 0.4));
        kd.insert(new Point2D(0.3, 0.2));
        kd.insert(new Point2D(0.10, 0.70));
        kd.insert(new Point2D(0.90, 0.20));

        StdOut.println(kd.nearest(new Point2D(0.90, 0.20)));

        kd.draw();

    }// unit testing of the methods (optional)
}
