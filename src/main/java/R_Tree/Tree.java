package R_Tree;

public class Tree {
	final static int BTREEDIMENSION = 2;
	Region root;


	public Tree() {
		this.root = new Region(BTREEDIMENSION);
	}


	//Todo Insercion
	public void insertion(Object o, Region actual){
		Region aux;

		//Búsqueda dle mejor nodo región en el que colocar el punto
		//Todo implementar la búsqueda del mejor nodo
		aux = bestNodeSearch(this, actual, root);

		//comprovación de si está o no llena la mejor región
		//Todo implementar isFull
		if(!aux.isfull()){
			//si no lo está, añadimos el objeto
			//Todo implementar add
			aux.add(o);
		}else{
			//En caso que no lo esté, hacemos split
			//Todo Implementar regionSplit
			regionSplit(o, aux, this);
		}
	}

	private Region bestNodeSearch(Tree tree, Region actual, Region root) {
		return(new Region());
	}

	private void regionSplit(Object o, Region aux, Tree t){
	}

	//Todo Busqueda

	//Todo Eliminacion
}
