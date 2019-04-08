package AVL

fun main(args: Array<String>) {
    var tree :  AVLTree<Int> = AVLTree()
}

class AVLTree<T : Comparable<T>>() {
    var root : Node<T> = Node()
    var data: T? = null

    constructor(data: T) : this() {
        this.data = data
    }

    fun addValue(data : T) {
        if (root.data == null){
            root = Node(data)
            root.data!!
        }else{
            root.addNode(Node(data))
        }
    }


}