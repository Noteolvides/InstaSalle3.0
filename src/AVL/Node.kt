package AVL

class Node<T>(data : T,parent: Node<T>) {
    var parent: Node<T>? = null
    var data: T? = null
    var rightChild : Node<T>? = null
    var leftChild : Node<T>? = null

    init {
        this.data = data;
        this.parent = parent;
    }


}