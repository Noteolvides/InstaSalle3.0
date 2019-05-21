package AVL;

public class Node<T> {
    Node<T> leftChild;
    Node<T> rightChild;
    int factor;
    T data;


    public Node(T data) {
        this.data = data;
        factor = 0;
    }

    public int compareTo(T data) {
        if ((long)this.data < (long)data) {
            return -1;
        } else if ((long)this.data > (long)data){
            return 1;
        } else {
            return 0;
        }
    }


}
