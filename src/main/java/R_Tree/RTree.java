package R_Tree;

import AVL.Node;

public class RTree {
    static final int MIN = 1;
    static final int MAX = 3;
    static final int HEIGHT_SCREEN = 1000;
    static final int WIDTH_SCRREN = 1000;
    private NodeRTree root;


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


