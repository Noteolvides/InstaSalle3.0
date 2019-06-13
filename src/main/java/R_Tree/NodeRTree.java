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
        if (node.minPoint.isClosetToLeftCornerThan(minPoint)) {
            minPoint = node.minPoint;
        }
        if (node.maxPoint.isClosetToRightCornerThan(maxPoint)) {
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

    public int calculateExpansionArea(NodeRTree regionToAdd){
        Point newMinPoint = minPoint;
        Point newMaxPoint = maxPoint;
        if (regionToAdd.minPoint.isClosetToLeftCornerThan(minPoint)){
            newMinPoint = regionToAdd.minPoint;
        }
        if(regionToAdd.maxPoint.isClosetToRightCornerThan(maxPoint)){
            newMaxPoint = regionToAdd.maxPoint;
        }

        return  area(newMinPoint,newMaxPoint);
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



    public void insertInside(Point p,int indice) {
        NodeRTree node = indice == -1 ? this : this.regions[indice];
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
            node.insertInside(p,indiceConMenorArea);

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
                    node.parent = newParent.parent;
                    node.isLeaf = newParent.isLeaf;
                    node.regions = newParent.regions;
                    node.points = newParent.points;
                    node.indexArray = newParent.indexArray;
                    node.maxPoint = newParent.maxPoint;
                    node.minPoint = newParent.minPoint;
                    node.isFull = newParent.isFull;
                } else {
                    for (int i = 0; i < node.parent.indexArray; i++) {
                        if (node.parent.regions[i] == node) {
                            node.parent.regions[i] = a;
                            break;
                        }
                    }
                    this.insertRegion(b);
                }
            }
        }
    }

    /**
     * Funcion que inserta Region
     * TODO Pasarlo a otro tipo de estructura.
     *
     * @return regionToInsert
     */

    public void insertRegion(NodeRTree insertNode) {
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
            //Todo Esto puede ser una fuente de bugs pues el minimo puede ser a la vez el maximo, por ahora lo dejamos
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
                        a.insert(this.regions[i]);
                        this.regions[i] = null;
                    } else {
                        b.insert(this.regions[i]);
                        this.regions[i] = null;
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
                for (int i = 0; i < this.parent.indexArray; i++) {
                    if (this.parent.regions[i] == this) {
                        this.parent.regions[i] = a;
                        break;
                    }
                }
                this.parent.insertRegion(b);
            }


        }
    }
}
