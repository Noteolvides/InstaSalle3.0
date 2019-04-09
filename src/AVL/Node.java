package AVL;

public class Node<T extends Comparable> {
    Node<T> leftChild;
    Node<T> rightChild;
    int factor;
    T data;


    public Node(T data) {
        this.data = data;
        factor = 0;
    }


}
