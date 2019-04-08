package AVL



class Node<T : Comparable<T>> () {
    var parent: Node<T>? = null
    var rightChild: Node<T>? = null
    var leftChild: Node<T>? = null
    var factor : Byte = 0
    var data : T? = null

    constructor(data: T, parent: Node<T>) : this() {
        this.data = data
        this.parent = parent
    }

    constructor(data: T) : this() {
        this.data = data
    }

    fun addNode(node: Node<T>) : Boolean {
        node.parent = this
        var cmp = node.data!!.compareTo(this.data!!)
        if (cmp > 0){
            rightChild = node
            return true
        }else{
            leftChild = node
            return false
        }

    }



    

    fun preOrder(){
        println(this.data)
        if (this.leftChild != null){
            this.leftChild!!.preOrder()
        }
        if (this.rightChild != null){
            this.rightChild!!.preOrder()
        }
    }


    fun inOrder(){
        if (this.leftChild != null){
            this.leftChild!!.inOrder()
        }
        println(this.data)
        if (this.rightChild != null){
            this.rightChild!!.inOrder()
        }
    }

    fun postOrder(){
        if (this.leftChild != null){
            this.leftChild!!.postOrder()
        }
        if (this.rightChild != null){
            this.rightChild!!.postOrder()
        }
        println(this.data)
    }


}

