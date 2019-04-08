package AVL

fun main(args: Array<String>) {
    var tree: AVLTree<Int> = AVLTree()
}

class AVLTree<T : Comparable<T>>() {
    var root: Node<T> = Node()
    var data: T? = null

    constructor(data: T) : this() {
        this.data = data
    }

    fun addValue(data: T) {
        if (root.data == null) {
            root = Node(data)
            root.data!!
        } else {
            root.addNode(Node(data))
        }
    }

    private fun rotateLL(root: Node<T>) {
        //Primero cojemos mi hijo izquierdo
        var leftBranchOfRoot: Node<T> = root.leftChild!!
        //Para mantener la estructura del arbol el nodo izquiero de la raiz tiene que tener al nodo derecho del hijo izquiero
        root.leftChild = leftBranchOfRoot.rightChild
        //Finalmente el hijo derecho de subarbol izquiero pasa a ser el hijo derecho por la rotacion
        leftBranchOfRoot.rightChild = root
        //Finalmente arreglamos los indices

        root.factor = 0
        leftBranchOfRoot.factor = 0

    }

    private fun rotateRR(root: Node<T>) {
        //Primero cojemos mi hijo derecho
        var leftBranchOfRoot: Node<T> = root.rightChild!!
        //Para mantener la estructura del arbol el nodo derecho de la raiz tiene que tener al nodo izquier del hijo derecho
        root.rightChild = leftBranchOfRoot.leftChild
        //Finalmente el hijo izquiero de subarbol derecho pasa a ser el hijo izquiero por la rotacion
        leftBranchOfRoot.leftChild = root
        //Finalmente arreglamos los indices
        root.factor = 0
        leftBranchOfRoot.factor = 0
    }


    private fun rotateLR(root: Node<T>) {
        rotateRR(root.leftChild!!)
        rotateLL(root)
    }

    private fun rotateRL(root: Node<T>) {
        rotateLL(root.rightChild!!)
        rotateRR(root)
    }

}