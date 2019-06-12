package R_Tree;

public class RTree {
    static final int MIN = 1;
    static final int MAX = 3;
    static final int HEIGHT_SCREEN = 1080;
    static final int WIDTH_SCRREN = 1080;
    private NodeRTree root;


    public RTree() {
        root = new NodeRTree(true);
    }

    public void insert(Point p) {
        insertInside(p, root);
    }

    /**
     * Funcion que inserta un punto
     * TODO Pasarlo a otro tipo de estructura.
     */
    public void insertInside(Point p, NodeRTree node) {
        if (!node.isLeaf) {

        } else {
            if (!node.isFull) {
                //Es hoja y no esta llena pues genial la añadimos
                node.insert(p);
            } else {
                //Que pena esta lleno asi que split
                //Hacemos una insercion espercial pues sabemos que puede tener uno de mas
                node.insert(p);

                NodeRTree a = new NodeRTree(false); //Region A en la que estara el punto minimo y los mas cercanos a este
                NodeRTree b = new NodeRTree(false); //Region B en la que estara el punto maximo y los mas cercanos a este

                Point min = new Point(WIDTH_SCRREN+1, HEIGHT_SCREEN+1);
                int minIndex = -1;
                Point max = new Point(0, 0);
                int maxIndex = -1;
                //Todo Esto puede ser una fuente de bugs pues el minimo puede ser a la vez el maximo, por ahora lo dejamos
                for (int i = 0; i < MAX; i++) {
                    Point pointSearch = node.points[i];
                    if (p.isClosetToLeftCornerThan(min)) {
                        min = pointSearch;
                        minIndex = i;
                    }
                    if (pointSearch.isClosetToRightCornerThan(max)) {
                        max = pointSearch;
                        maxIndex = i;
                    }
                }
                node.points[minIndex] = null; //Para luego no tener que meterlos
                node.points[maxIndex] = null;

                a.insert(min);
                b.insert(max);


                //El splip finaliza redistribuimos los puntos
                //Limpiamos el nodo en el que estamos

                node.indexArray = 0;
                node.isLeaf = false;
                node.isFull = false;

                for (Point pointSearch : node.points) {
                    if (pointSearch != null){
                        double areaA = a.calculateExpansionArea(pointSearch);
                        double areaB = b.calculateExpansionArea(pointSearch);
                        if (areaA < areaB){
                            a.insert(pointSearch);
                        }else{
                            b.insert(pointSearch);
                        }
                    }
                }







            }
        }

    }

}
