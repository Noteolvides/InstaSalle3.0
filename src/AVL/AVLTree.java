package AVL;

public class AVLTree<T extends Comparable<T>> {
    Node<T> root;

    //Implementacion de las rotaciones

    private void rotateLL( Node<T> root) {
        //Primero cojemos mi hijo izquierdo
        Node<T> leftBranchOfRoot = root.leftChild;
        //Para mantener la estructura del arbol el nodo izquiero de la raiz tiene que tener al nodo derecho del hijo izquiero
        root.leftChild = leftBranchOfRoot.rightChild;
        //Finalmente el hijo derecho de subarbol izquiero pasa a ser el hijo derecho por la rotacion
        root = leftBranchOfRoot.rightChild;
        //Finalmente arreglamos los indices

        root.factor = 0;
        leftBranchOfRoot.factor = 0;

    }

    private void rotateRR(Node<T>  root) {
        //Primero cojemos mi hijo derecho
        Node<T>  rightBranchOfRoot = root.rightChild;
        //Para mantener la estructura del arbol el nodo derecho de la raiz tiene que tener al nodo izquier del hijo derecho
         root.rightChild = rightBranchOfRoot.leftChild;
        //Finalmente el hijo izquiero de subarbol derecho pasa a ser el hijo izquiero por la rotacion
        root = rightBranchOfRoot.rightChild;
        //Finalmente arreglamos los indices
        root.factor = 0;
        rightBranchOfRoot.factor = 0;
    }


    private void rotateLR(Node<T>  root) {
        rotateRR(root.leftChild);
        rotateLL(root);
    }

    private void rotateRL(Node<T>  root) {
        rotateLL(root.rightChild);
        rotateRR(root);
    }


    public void insert(T data) {
        Boolean act = Boolean.FALSE;
        insert(data, root,act);
    }
    
    //Implementacion de la insercion


    private void insert(T data,Node<T> node,Boolean act) {
        if (node == null){
            node = new Node<>(data);
            act = true;
        }else{
            int cmp = data.compareTo(node.data);
            if (cmp == 0) {
                System.out.println("Error nodo añadido");
                act = false;
            }
            boolean flag;
            if (cmp < 0) {
                insert(data, node.leftChild,act);
                if (act) {
                    if (node.factor == -1) {
                        node.factor = 0;
                        act = false;
                    }
                    if (node.factor == 0) {
                        node.factor = 1;
                        act = true;
                    }
                    if (node.factor == 1) {
                        if (node.leftChild.factor == 1) {
                            rotateLL(node);
                        } else {
                            rotateLR(node);
                        }
                        act = false;
                    }
                }
            }
            if (cmp > 0) {
                insert(data, node.rightChild,act);
                if (act) {
                    if (node.factor == 1) {
                        node.factor = 0;
                        act = false;
                    }
                    if (node.factor == 0) {
                        node.factor = -1;
                        act = true;
                    }
                    if (node.factor == -1) {
                        if (node.rightChild.factor == 1) {
                            rotateRL(node);
                        } else {
                            rotateRR(node);
                        }
                        act = false;
                    }
                }
            }
        }
    }

    //TODO : Implementacion de la eliminacion



    // Implementacion de Visualizaciones


    public void preOrder(){
        preOrderPriv(root);
    }

    private void preOrderPriv(Node<T> node){
        System.out.println(node.data);
        if (node.leftChild != null){
            preOrderPriv(node.leftChild);
        }
        if (node.rightChild != null){
            preOrderPriv(node.rightChild);
        }
    }

    public void inOrder(){
        inOrderPriv(root);
    }

    private void inOrderPriv(Node<T> node){
        System.out.println(node.data);
        if (node.leftChild != null){
            inOrderPriv(node.leftChild);
        }
        if (node.rightChild != null){
            inOrderPriv(node.rightChild);
        }
    }

    public void postOrder(){
        postOrderPriv(root);
    }

    private void postOrderPriv(Node<T> node){
        System.out.println(node.data);
        if (node.leftChild != null){
            postOrderPriv(node.leftChild);
        }
        if (node.rightChild != null){
            postOrderPriv(node.rightChild);
        }
    }
}
