package R_Tree;

import java.awt.*;

class NodeRTree {
    Point points[];
    int indexArray;
    NodeRTree parent;
    boolean isLeaf;
    boolean isFull;

    Point minPoint;
    Point maxPoint;


    /**
     * Inicializa el Nodo segun si es una hoja o no
     *
     * @param isLeaf Si es hoja o no
     */
    public NodeRTree(boolean isLeaf) {
        this.isLeaf = isLeaf;
        minPoint = new Point(RTree.WIDTH_SCRREN + 1, RTree.HEIGHT_SCREEN + 1);
        maxPoint = new Point(0, 0);
        points = new Point[RTree.MAX+1];
    }

    public void insert(Point p) {
        if (p.isClosetToLeftCornerThan(minPoint)) {
            minPoint = p;
        }
        if (p.isClosetToRightCornerThan(maxPoint)) {
            maxPoint = p;
        }
        points[indexArray++] = p;
        isFull = indexArray >= RTree.MAX;
    }


    public int calculateExpansionArea(Point pointToAdd) {
        Point newMinPoint = minPoint;
        Point newMaxPoint = maxPoint;

        if (pointToAdd.isClosetToLeftCornerThan(minPoint)) {
            newMinPoint = pointToAdd;
        }
        if (pointToAdd.isClosetToRightCornerThan(maxPoint)) {
            newMaxPoint = pointToAdd;
        }

        return area(newMinPoint,newMaxPoint);
    }

    private int area(Point min, Point max) {
        int distX = (max.x - min.x) == 0 ? 1: max.x - min.x;
        int distY = (max.y - min.y) == 0 ? 1: max.y - min.y;
        return distX*distY;
    }
}
