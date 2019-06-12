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
        root = insertInside(p, root);
    }

    /**
     * Funcion que inserta un punto
     * TODO Pasarlo a otro tipo de estructura.
     *
     * @return NodeToActualizate
     */
    public NodeRTree insertInside(Point p, NodeRTree node) {
        if (!node.isLeaf) {
            int areaMinimaBusqueda = Integer.MAX_VALUE;
            int indiceConMenorArea = -1;
            int areaIteracion;
            for (int i = 0; i < node.indexArray; i++) {
                if (node.regions[i].minPoint.x <= p.x && node.regions[i].minPoint.y <= p.y && node.regions[i].maxPoint.x >= p.x && node.regions[i].maxPoint.y >= p.y) {
                    areaIteracion = 0;
                } else {
                    areaIteracion = node.regions[i].calculateExpansionArea(p);
                }
                if (areaIteracion < areaMinimaBusqueda) {
                    areaMinimaBusqueda = areaIteracion;
                    indiceConMenorArea = i;
                }
            }
            node.regions[indiceConMenorArea] = insertInside(p, node.regions[indiceConMenorArea]);

        } else {
            if (!node.isFull) {
                //Es hoja y no esta llena pues genial la añadimos
                node.insert(p);
            } else {
                //Que pena esta lleno asi que split
                //Hacemos una insercion espercial pues sabemos que puede tener uno de mas
                node.insert(p);

                NodeRTree a = new NodeRTree(true);//Region A en la que estara el punto minimo y los mas cercanos a este
                a.parent = node.parent;
                NodeRTree b = new NodeRTree(true); //Region B en la que estara el punto maximo y los mas cercanos a este
                b.parent = node.parent;

                Point min = new Point(WIDTH_SCRREN + 1, HEIGHT_SCREEN + 1);
                int minIndex = -1;
                Point max = new Point(0, 0);
                int maxIndex = -1;
                //Todo Esto puede ser una fuente de bugs pues el minimo puede ser a la vez el maximo, por ahora lo dejamos
                for (int i = 0; i < node.indexArray; i++) {
                    Point pointSearch = node.points[i];
                    if (pointSearch.isClosetToLeftCornerThan(min)) {
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


                for (int i = 0; i < node.indexArray; i++) {
                    Point pointSearch = node.points[i];
                    if (pointSearch != null) {
                        double areaA = a.calculateExpansionArea(pointSearch);
                        double areaB = b.calculateExpansionArea(pointSearch);
                        if (areaA < areaB) {
                            a.insert(pointSearch);
                            node.points[i] = null;
                        } else {
                            b.insert(pointSearch);
                            node.points[i] = null;
                        }
                    }
                }

                //Ahora tenemos dos casos, en el caso de sea la primera ejecucion, el nodo padre sera null en ese caso creamos uno nuevo
                //En el otro caso, somos un nodo interno y tenemos que sustituir y añadir la otra region

                if (node.parent == null) {
                    NodeRTree newParent = new NodeRTree(false);
                    a.parent = newParent;
                    newParent.insert(a);//Suponemos que de tamaño minimo es 2
                    b.parent = newParent;
                    newParent.insert(b);
                    node.parent = newParent;
                    return newParent;
                } else {
                    int indexToReturn = -1;
                    for (int i = 0; i < node.parent.indexArray; i++) {
                        if (node.parent.regions[i] == node) {
                            node.parent.regions[i] = a;
                            indexToReturn = i;
                            break;
                        }
                    }
                    node.parent = insertRegion(b, node.parent);
                    return node.parent.regions[indexToReturn];
                }


            }
        }
        return node;
    }

    /**
     * Funcion que inserta Region
     * TODO Pasarlo a otro tipo de estructura.
     *
     * @return regionToInsert
     */

    public NodeRTree insertRegion(NodeRTree insertNode, NodeRTree from) {
        if (!from.isFull) {
            //Es nodo intermedio  y no esta llena pues genial la añadimos
            from.insert(insertNode);
        } else {
            //Que pena esta lleno asi que split
            //Hacemos una insercion espercial pues sabemos que puede tener uno de mas
            from.insert(insertNode);
            NodeRTree a = new NodeRTree(false);
            a.parent = from.parent;
            NodeRTree b = new NodeRTree(false);
            b.parent = from.parent;
            int minIndex = -1;
            int maxIndex = -1;
            NodeRTree min = new NodeRTree(false);
            NodeRTree max = new NodeRTree(false);
            //Todo Esto puede ser una fuente de bugs pues el minimo puede ser a la vez el maximo, por ahora lo dejamos
            for (int i = 0; i < from.indexArray; i++) {
                if (from.regions[i].minPoint.isClosetToLeftCornerThan(min.minPoint)) {
                    min = from.regions[i];
                    minIndex = i;
                }
                if (from.regions[i].maxPoint.isClosetToRightCornerThan(max.maxPoint)) {
                    max = from.regions[i];
                    maxIndex = i;
                }
            }


            from.regions[minIndex] = null; //Para luego no tener que meterlos
            from.regions[maxIndex] = null;

            a.insert(min);
            b.insert(max);

            //El splip finaliza redistribuimos los puntos


            for (int i = 0; i < from.indexArray; i++) {
                if (from.regions[i] != null) {
                    double areaA = a.calculateExpansionArea(from.regions[i]);
                    double areaB = b.calculateExpansionArea(from.regions[i]);
                    if (areaA < areaB) {
                        a.insert(from.regions[i]);
                        from.regions[i] = null;
                    } else {
                        b.insert(from.regions[i]);
                        from.regions[i] = null;
                    }
                }
            }

            //Ahora tenemos dos casos, en el caso de sea la primera ejecucion, el nodo padre sera null en ese caso creamos uno nuevo
            //En el otro caso, somos un nodo interno y tenemos que sustituir y añadir la otra region

            if (from.parent == null) {
                NodeRTree newParent = new NodeRTree(false);
                a.parent = newParent;
                newParent.insert(a);//Suponemos que de tamaño minimo es 2
                b.parent = newParent;
                newParent.insert(b);
                return newParent;
            } else {
                int indexToReturn = -1;
                for (int i = 0; i < from.parent.indexArray; i++) {
                    if (from.parent.regions[i] == from) {
                        from.parent.regions[i] = a;
                        indexToReturn = i;
                        break;
                    }
                }
                from.parent = insertRegion(b, from.parent);
                return from.parent.regions[indexToReturn];
            }


        }
        return from;
    }

}


