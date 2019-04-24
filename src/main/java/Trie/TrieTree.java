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
}
