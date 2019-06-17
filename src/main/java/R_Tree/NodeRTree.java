package R_Tree;


class NodeRTree {
    Point points[];
    NodeRTree regions[];
    Integer indexArray;
    NodeRTree parent;
    Boolean isLeaf;
    Boolean isFull;

    Point minPoint;
    Point maxPoint;


    /**
     * Inicializa el Nodo segun si es una hoja o no
     *
     * @param isLeaf Si es hoja o no
     */
    public NodeRTree(boolean isLeaf) {
        this.isLeaf = isLeaf;
        this.isFull = false;
        this.indexArray = 0;
        resetLimits();
        points = new Point[RTree.MAX + 1];
        regions = new NodeRTree[RTree.MAX + 1];
    }

    public void insert(NodeRTree node) {
        if (node.minPoint.y < minPoint.y) {
            minPoint.y = node.minPoint.y;
        }
        if (node.minPoint.x < minPoint.x) {
            minPoint.x = node.minPoint.x;
        }
        if (node.maxPoint.y > maxPoint.y) {
            maxPoint.y = node.maxPoint.y;
        }
        if (node.minPoint.x > maxPoint.x) {
            maxPoint.x = node.maxPoint.x;
        }
        regions[indexArray++] = node;
        isFull = indexArray >= RTree.MAX;
    }


    public void insert(Point p) {
        if (p.y < minPoint.y) {
            minPoint.y = p.y;
        }
        if (p.x < minPoint.x) {
            minPoint.x = p.x;
        }
        if (p.y > maxPoint.y) {
            maxPoint.y = p.y;
        }
        if (p.x > maxPoint.x) {
            maxPoint.x = p.x;
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

    public int calculateExpansionArea(NodeRTree regionToAdd) {
        Point newMinPoint = minPoint;
        Point newMaxPoint = maxPoint;
        if (regionToAdd.minPoint.isClosetToLeftCornerThan(minPoint)) {
            newMinPoint = regionToAdd.minPoint;
        }
        if (regionToAdd.maxPoint.isClosetToRightCornerThan(maxPoint)) {
            newMaxPoint = regionToAdd.maxPoint;
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


    public NodeRTree insertInside(Point p) {
        NodeRTree node = this;
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
            NodeRTree b = node.regions[indiceConMenorArea].insertInside(p);
            if (b != null) {
                return node.insertRegion(b);
            }

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

                Point min = new Point(RTree.WIDTH_SCRREN + 1, RTree.HEIGHT_SCREEN + 1);
                int minIndex = -1;
                Point max = new Point(0, 0);
                int maxIndex = -1;
                //Todo Esto puede ser una fuente de bugs pues el minimo puede ser a la vez el maximo, por ahora lo dejamos --> Efectivamente querido watson hahah
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
                            if (a.indexArray-1 != (RTree.MAX - RTree.MIN)) {
                                a.insert(pointSearch);
                                node.points[i] = null;
                            } else {
                                b.insert(pointSearch);
                                node.points[i] = null;
                            }
                        } else {
                            if (b.indexArray-1 != (RTree.MAX - RTree.MIN)) {
                                b.insert(pointSearch);
                                node.points[i] = null;
                            } else {
                                a.insert(pointSearch);
                                node.points[i] = null;
                            }
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
                    node.parent = newParent.parent;
                    node.isLeaf = newParent.isLeaf;
                    node.regions = newParent.regions;
                    node.points = newParent.points;
                    node.indexArray = newParent.indexArray;
                    node.maxPoint = newParent.maxPoint;
                    node.minPoint = newParent.minPoint;
                    node.isFull = newParent.isFull;
                } else {
                    node.parent = a.parent;
                    node.isLeaf = a.isLeaf;
                    node.regions = a.regions;
                    node.points = a.points;
                    node.indexArray = a.indexArray;
                    node.maxPoint = a.maxPoint;
                    node.minPoint = a.minPoint;
                    node.isFull = a.isFull;
                    return b;
                }
            }
        }
        return null;
    }

    /**
     * Funcion que inserta Region
     * TODO Pasarlo a otro tipo de estructura.
     *
     * @return regionToInsert
     */

    public NodeRTree insertRegion(NodeRTree insertNode) {
        if (!this.isFull) {
            //Es nodo intermedio  y no esta llena pues genial la añadimos
            this.insert(insertNode);
        } else {
            //Que pena esta lleno asi que split
            //Hacemos una insercion espercial pues sabemos que puede tener uno de mas
            this.insert(insertNode);
            NodeRTree a = new NodeRTree(false);
            a.parent = this.parent;
            NodeRTree b = new NodeRTree(false);
            b.parent = this.parent;
            int minIndex = -1;
            int maxIndex = -1;
            NodeRTree min = new NodeRTree(false);
            NodeRTree max = new NodeRTree(false);
            //Todo Esto puede ser una fuente de bugs pues el minimo puede ser a la vez el maximo, por ahora lo dejamos -- si, eres muy listo
            for (int i = 0; i < this.indexArray; i++) {
                if (this.regions[i].minPoint.isClosetToLeftCornerThan(min.minPoint)) {
                    min = this.regions[i];
                    minIndex = i;
                }
                if (this.regions[i].maxPoint.isClosetToRightCornerThan(max.maxPoint)) {
                    max = this.regions[i];
                    maxIndex = i;
                }
            }


            this.regions[minIndex] = null; //Para luego no tener que meterlos
            this.regions[maxIndex] = null;

            a.insert(min);
            b.insert(max);

            //El splip finaliza redistribuimos los puntos


            for (int i = 0; i < this.indexArray; i++) {
                if (this.regions[i] != null) {
                    double areaA = a.calculateExpansionArea(this.regions[i]);
                    double areaB = b.calculateExpansionArea(this.regions[i]);
                    if (areaA < areaB) {
                        if (a.indexArray-1 != (RTree.MAX - RTree.MIN)) {
                            a.insert(this.regions[i]);
                            this.regions[i] = null;
                        } else {
                            b.insert(this.regions[i]);
                            this.regions[i] = null;
                        }
                    } else {
                        if (b.indexArray-1 != (RTree.MAX - RTree.MIN)) {
                            b.insert(this.regions[i]);
                            this.regions[i] = null;
                        } else {
                            a.insert(this.regions[i]);
                            this.regions[i] = null;
                        }
                    }
                }
            }

            //Ahora tenemos dos casos, en el caso de sea la primera ejecucion, el nodo padre sera null en ese caso creamos uno nuevo
            //En el otro caso, somos un nodo interno y tenemos que sustituir y añadir la otra region

            if (this.parent == null) {
                NodeRTree newParent = new NodeRTree(false);
                a.parent = newParent;
                newParent.insert(a);//Suponemos que de tamaño minimo es 2
                b.parent = newParent;
                newParent.insert(b);
                this.parent = newParent.parent;
                this.isLeaf = newParent.isLeaf;
                this.regions = newParent.regions;
                this.points = newParent.points;
                this.indexArray = newParent.indexArray;
                this.maxPoint = newParent.maxPoint;
                this.minPoint = newParent.minPoint;
                this.isFull = newParent.isFull;
            } else {
                this.parent = a.parent;
                this.isLeaf = a.isLeaf;
                this.regions = a.regions;
                this.points = a.points;
                this.indexArray = a.indexArray;
                this.maxPoint = a.maxPoint;
                this.minPoint = a.minPoint;
                this.isFull = a.isFull;
                return b;
            }


        }
        return null;
    }
}
