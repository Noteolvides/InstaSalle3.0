package R_Tree;

public class Point {
    int x;
    int y;

    public Point(int x,int y){
        this.x = x;
        this.y = y;
    }

    public double calculateDistanceFrom(Point point) {
        double cateto1 = this.x - point.x;
        double cateto2 = this.y - point.y;
        return Math.sqrt(cateto1*cateto1 + cateto2*cateto2);
    }

    public boolean isClosetToLeftCornerThan(Point point) {
        double distancia1 = calculateDistanceFrom(new Point(0,0));
        double distancia2 = point.calculateDistanceFrom(new Point(0,0));
        return distancia1 < distancia2;
    }

    public boolean isClosetToRightCornerThan(Point point) {
        double distancia1 = calculateDistanceFrom(new Point(RTree.WIDTH_SCRREN,RTree.HEIGHT_SCREEN));
        double distancia2 = point.calculateDistanceFrom(new Point(RTree.WIDTH_SCRREN,RTree.HEIGHT_SCREEN));
        return distancia1 < distancia2;
    }
}
