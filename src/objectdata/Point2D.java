package objectdata;

public class Point2D {
    private final double x;
    private final double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point2D translate(double dx, double dy) {
        return new Point2D(x + dx, y + dy);
    }

    public Point2D rotate(double angle) {
        return new Point2D(Math.cos(angle) * x - Math.sin(angle) * y, Math.sin(angle) * x + Math.cos(angle) * y);
    }

    public Point2D scale(double scale) {
        return new Point2D(x * scale, y * scale);
    }
}
