package AVL;

import Data.Post;
import Trie.TrieTree;
import com.google.gson.Gson;
import exe.Menu;
import processing.core.PApplet;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Test extends PApplet {

	private final int dimensionx = 1200;
	private final int dimensiony = 700;
	int level = 1;
	int offsetY = 100;
	AVLTree<Post> tree = new AVLTree<Post>();
	int state = 0;
	String result="";

	public void setTree(AVLTree<Post> tree) {
		this.tree = tree;
	}

	public void visualize(String... args){
		PApplet.main("AVL.Test",args);
	}

	public void settings(){
		size(dimensionx, dimensiony);
	}

	public void setup() {
		background(51);
		frameRate(30);
		tree = Menu.avlTree;
	}

	public void draw(){
		clear();
		background(51);
		if (tree.root != null ){
			preOrder(tree.root, (width / 2), width);
		}else{
			fill(255);
			text ("No hay mas nodos", width/2, width/2);
		}

		switch (state) {
			case 0:
				fill(255);
				text ("Inserta o elimina nodo \n"+result, 133, 50);
				break;

			case 1:
				fill(255, 2, 2);
				text ("Gracias Eliminar el nodo \n"+result, 133, 100);
				/*try {

					tree.delete(Integer.parseInt(result));
					result = "";

					state = 0;
				} catch (Exception e) {
					tree.insert(Integer.parseInt(result));
					state = 0;
					result = "";
				}*/
				break;
		}

	}

	void preOrder(Node<Post> nodeAct,int nuevo, int anterior){
		if (level != 1){
			line(nuevo, level * (offsetY + 2), anterior, (level - 1) * (offsetY + 2));
		}
		fill(255);
		ellipse(nuevo, level * offsetY, 30,30);
		textAlign(CENTER);
		textSize(12);
		fill(255,0,0);
		text(nodeAct.factor, nuevo+10, (level * offsetY)-4);
		fill(0);
		text(Integer.toString(nodeAct.data.id), nuevo, (level * offsetY) + 5);

		double vector = Math.abs(anterior - nuevo);
		if (nodeAct.leftChild != null) {
			level++;
			preOrder(nodeAct.leftChild, (int) Math.abs((vector / 2) - nuevo), nuevo);
			level--;
		}
		if (nodeAct.rightChild != null) {
			level++;
			preOrder(nodeAct.rightChild, (int) Math.abs((vector / 2) + nuevo), nuevo);
			level--;
		}
	}

	public void keyPressed() {
		if (key==ENTER||key==RETURN) {
			state++;
		}else if (key == BACKSPACE){
			if (result.length() > 0){
				StringBuilder aux = new StringBuilder(result);
				result = aux.deleteCharAt(aux.length()-1).toString();
			}
		} else
			result = result + key;
	}

}
