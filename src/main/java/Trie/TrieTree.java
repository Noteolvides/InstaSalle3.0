package Trie;


public class TrieTree {
	public static void main(String[] args) {
		TrieTree tree = new TrieTree();
	}
	Node<Character> root = null;


	public void insert(String phrase){
		if (root == null){
			root = new Node<Character>(null);
		}
		insertInside(phrase,0,root);
	}

	private void insertInside(String sentence, int i, Node<Character> node) {
		Character c = sentence.charAt(i);
		if (!node.children.containsKey(c)){
			Node<Character> aux = new Node<Character>(c);
			if (i+1 < sentence.length()){
				node.children.put(c,aux);
				insertInside(sentence,i+1,aux);
			}else{
				aux.isWord = true;
				node.children.put(c,aux);
			}
		}else {
			if (i+1 < sentence.length()){
				insertInside(sentence,i+1,node.children.get(c));
			}else{
				node.children.get(c).isWord = true;
			}
		}
	}

	public boolean contains(String phrase){
		if (root == null){
			return false;
		}
		return containsInside(phrase,0,root);
	}

	private boolean containsInside(String phrase, int i, Node<Character> node) {
		Character c = phrase.charAt(i);
		if (!node.children.containsKey(c)){
			return false;
		}else {
			if (i+1 < phrase.length()){
				return containsInside(phrase,i+1,node.children.get(c));
			}
			return node.children.get(c).isWord;
		}
	}

	public boolean delete(String phrase){
		//Si root es null, no hay nada en el árbol y por tanto es fallido
		if(root == null){
			return(false);
		}
		//Se llama al delete. Dependiendo del resultado (int) será exitoso o no
		int result = deleteinside(phrase,0,root);
		//Si result es 1, será una operación fallida
		if(result == 1){
			System.out.println("Delete fallido");
			return (false);
		}
		//En caso contrario, result será 4 y será una operación exitosa
		System.out.println("Delete exitoso");
		return (true);
	}

	private int deleteinside(String phrase, int i, Node<Character> node) {
		int j;
		boolean found = false;
		int casenum = 0;

		//Se comprueva que el nodo sea final de palabra
		if(node.isWord){
			if(node.data == phrase.charAt(i)){
				//Si lo es, se comprueva si alguna palabra depende de esta letra
				if(node.children.size() == 0){
					//Si no tiene hijos, se quita la flag se devuelve un 2
					node.isWord = false;
					return(2);
				}else{
					//Si tiene hijos, se quita la flag y se devuelve un 4
					node.isWord = false;
					return(4);
				}
			}
		}else{
			//
			for(j =0; j < node.children.size(); j++){
				if(node.children.get(j).data == phrase.charAt(i)){
					found =true;
					casenum = deleteinside(phrase, i+1, node.children.get(j));
					break;
				}
			}
		}
		if(j < node.children.size()){
			casenum = 1;
		}
		switch (casenum){
			case 1:
				return(1);
			case 2:
				node.children.put(node.children.get(j).data, null);
				if(node.isWord){
					return(2);
				}else{
					return(4);
				}
			/*case 3:
				node.isWord = false;
				return(4);
			break;*/
			case 4:
				//Success
				return(4);
		}

		return(1);
	}
}
