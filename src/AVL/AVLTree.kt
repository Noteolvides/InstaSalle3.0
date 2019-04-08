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

    fun rotateLL(root: Node<T>): Node<T> {
        //Primero cojemos mi hijo izquierdo
        var leftBranchOfRoot : Node<T> = root.leftChild!!
        //Cambiamos el padre del nodo raiz al nodo hijo
        leftBranchOfRoot.parent = root.parent
        //Para mantener la estructura del arbol el nodo izquiero de la raiz tiene que tener al nodo derecho del hijo izquiero
        root.leftChild = leftBranchOfRoot.rightChild
        //Finalmente el hijo derecho de subarbol izquiero pasa a ser el hijo derecho por la rotacion
        leftBranchOfRoot.rightChild = root
        //Para mantener la coerencia ponemos el padre de la raiz a su nuevo padre
        root.parent = leftBranchOfRoot

        //Finalmente tenemos que comprobar los cambios que han podido ocurrir

        if (leftBranchOfRoot.factor == -1){
            root.factor = 0
            leftBranchOfRoot.factor = 0
        }else{
            root.factor = -1
            leftBranchOfRoot.factor = 1
        }



    }


}