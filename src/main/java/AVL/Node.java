package AVL;

import Data.Post;

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
        if (data instanceof Post) {
            return Long.compare(((Post) this.data).publishedWhen, ((Post) data).publishedWhen);
        }
        if (data instanceof Long) {
            return Long.compare(((Post) this.data).publishedWhen, (Long)data);
        }
        return 0;
    }


}
