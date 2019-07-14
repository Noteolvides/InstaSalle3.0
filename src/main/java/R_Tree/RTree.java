package R_Tree;

import AVL.Node;

public class RTree {
    static final int MIN = 1;
    static final int MAX = 3;
    public static final int HEIGHT_SCREEN = 600;
    public static final int WIDTH_SCREEN = 1000;
    NodeRTree root;

    public Boolean deletePoint(Point p){
        return root.deletePoint(p);
    }

    public Point[] fintPointsNear(Point p,int radius,int maxPoints){
        return root.findPointsNear(p,radius,maxPoints);
    }

    public RTree() {
        root = new NodeRTree(true);
    }

    public void insert(Point p) {
        root.insertInside(p);
    }

    /**
     * Funcion que inserta un punto
     * TODO Pasarlo a otro tipo de estructura.
     *
     * @return NodeToActualizate
     */

}


