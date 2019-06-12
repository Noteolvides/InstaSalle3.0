package R_Tree;


class NodeRTree {
    Point points[];
    NodeRTree regions[];
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
        resetLimits();
        points = new Point[RTree.MAX + 1];
        regions = new NodeRTree[RTree.MAX + 1];
    }

    public void insert(NodeRTree node) {
        if (node.minPoint.isClosetToRightCornerThan(minPoint)) {
            minPoint = node.minPoint;
        }
        if (node.maxPoint.isClosetToLeftCornerThan(maxPoint)) {
            maxPoint = node.maxPoint;
        }
        regions[indexArray++] = node;
        isFull = indexArray >= RTree.MAX;
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
        return area(newMinPoint, newMaxPoint);
    }

    private int area(Point min, Point max) {
        int distX = (max.x - min.x) == 0 ? 1 : max.x - min.x;
        int distY = (max.y - min.y) == 0 ? 1 : max.y - min.y;
        return distX * distY;
    }

    public void resetLimits() {
        minPoint = new Point(RTree.WIDTH_SCRREN + 1, RTree.HEIGHT_SCREEN + 1);
        maxPoint = new Point(0, 0);
    }
}
