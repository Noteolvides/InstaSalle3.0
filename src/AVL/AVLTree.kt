package AVL

fun main(args: Array<String>) {
    var tree: AVLTree<Int> = AVLTree()
    tree.insert(12)
    tree.root.inOrder()
    tree.insert(2)
    tree.root.inOrder()
    tree.insert(1)
    tree.root.inOrder()

}

class AVLTree<T : Comparable<T>>() {
    var root: Node<T> = Node()

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


    fun insert(data: T) {
        insert(data, root)
    }

    fun insert(data: T, node: Node<T>): Boolean {
        if (node.data == null) {
            node.factor = 0
            node.data = data
            return true
        } else {
            val cmp = data.compareTo(node.data!!)
            if (cmp == 0) {
                println("Error nodo añadido")
                return false
            }
            if (cmp < 0) {
                if (insert(data, node.leftChild!!)) {
                    if (node.factor == -1) {
                        node.factor = 0
                        return false
                    }
                    if (node.factor == 0) {
                        node.factor = 1
                        return true
                    }
                    if (node.factor == 1) {
                        if (node.leftChild!!.factor == 1) {
                            rotateLL(node)
                        } else {
                            rotateLR(node)
                        }
                        return false
                    }
                }
            }
            if (cmp > 0) {
                if (insert(data, node.leftChild!!)) {
                    if (node.factor == 1) {
                        node.factor = 0
                        return false
                    }
                    if (node.factor == 0) {
                        node.factor = -1
                        return true
                    }
                    if (node.factor == -1) {
                        if (node.rightChild!!.factor == 1) {
                            rotateRL(node)
                        } else {
                            rotateRR(node)
                        }
                        return false
                    }
                }
            }
        }
        return false
    }
}


