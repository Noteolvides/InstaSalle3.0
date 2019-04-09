package AVL;

public class AVLTree<T extends Comparable<T>> {
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
        for (int i = 0; i < 10; i++) {
            tree.insert((int)(Math.random()*100));
        }


        tree.postOrder();
    }

    Node<T> root;

    //Implementacion de las rotaciones

    private Node<T> rotateLL(Node<T> root) {
        Node<T> leftBranchOfRoot = root.leftChild;
        root.leftChild = leftBranchOfRoot.rightChild;
        leftBranchOfRoot.rightChild = root;
        leftBranchOfRoot.factor = 0;
        root.factor = 0;
        return leftBranchOfRoot;
    }

    private Node<T> rotateRR(Node<T>  root) {
        Node<T> rightBranchOfRoot = root.rightChild;
        root.rightChild = rightBranchOfRoot.leftChild;
        rightBranchOfRoot.leftChild = root;
        rightBranchOfRoot.factor = 0;
        root.factor = 0;
        return rightBranchOfRoot;
    }


    private Node<T> rotateLR(Node<T>  root) {
        root.leftChild = rotateRR(root.leftChild);
        return rotateLL(root);
    }

    private Node<T> rotateRL(Node<T>  root) {
        root.rightChild = rotateLL(root.rightChild);
        return rotateRR(root);
    }


    public void insert(T data) {
        Logical act = new Logical();
        root = insert(data, root,act);
    }
    
    //Implementacion de la insercion


    private Node<T> insert(T data, Node<T> node, Logical act) {
        if (node == null){
            node = new Node<>(data);
            act.bool = true;
        }else{
            int cmp = data.compareTo(node.data);
            if (cmp == 0) {
                System.out.println("Error nodo añadido");
                act.bool = false;
            }
            if (cmp < 0) {
                node.leftChild = insert(data, node.leftChild,act);
                if (act.bool) {
                    if (node.factor == -1) {
                        node.factor = 0;
                        act.bool = false;
                    }
                    else if (node.factor == 0) {
                        node.factor = 1;
                        act.bool = true;
                    }
                    else if (node.factor == 1) {
                        if (node.leftChild.factor == 1) {
                            node = rotateLL(node);
                        } else {
                            node = rotateLR(node);
                        }
                        act.bool = false;
                    }
                }
            }
            if (cmp > 0) {
                node.rightChild = insert(data, node.rightChild,act);
                if (act.bool) {
                    if (node.factor == 1) {
                        node.factor = 0;
                        act.bool = false;
                    }
                    else if (node.factor == 0) {
                        node.factor = -1;
                        act.bool = true;
                    }
                    else if (node.factor == -1) {
                        if (node.rightChild.factor == 1) {
                            node = rotateRL(node);
                        } else {
                            node = rotateRR(node);
                        }
                        act.bool = false;
                    }
                }
            }
        }
        return node;
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

    private class Logical {
        Boolean bool = false;
    }
}
