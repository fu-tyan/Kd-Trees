public class PointSET {

    private SET<Point2D> pointSet;
    private int pointsNumber;
    private SET<Point2D> rectPoints;

    public PointSET() {
        pointSet = new SET<Point2D>();
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
        if (!this.contains(p))
        {
            pointSet.add(p);
            pointsNumber++;
        }
    } // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("NULL!!!");
        }

        return pointSet.contains(p);
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

    public static void main(String[] args) {}// unit testing of the methods (optional)
}
