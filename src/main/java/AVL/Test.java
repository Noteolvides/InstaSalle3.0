package AVL;

import processing.core.PApplet;

public class Test extends PApplet {

	private final int dimensionx = 1200;
	private final int dimensiony = 700;
	int level = 1;
	int offsetY = 100;
	AVLTree<Integer> tree = new AVLTree<Integer>();
	int state = 0;
	String result="";


	public static void main(String... args){
		PApplet.main("AVL.Test",args);
	}

	public void settings(){
		size(dimensionx, dimensiony);
	}

	public void setup(){
		background(51);
		frameRate(30);
		for (int i = 0; i < 10; i++) {
			int randomInt = (int) (Math.random() * 100);
			tree.insert(randomInt);
		}
		System.out.println("Done");
		preOrder(tree.root, (dimensionx / 2), dimensiony);


	}

	public void draw(){
		clear();
		background(51);
		preOrder(tree.root, (dimensionx / 2), dimensiony);

		switch (state) {
			case 0:
				fill(255);
				text ("Inserta el nodo a eliminar \n"+result, 133, 333);
				break;

			case 1:
				fill(255, 2, 2);
				text ("Gracias Eliminar el nodo \n"+result, 133, 633);
				try {

					tree.delete(Integer.parseInt(result));
					result = "";

					state = 0;
				} catch (Exception e) {
					System.out.println("Error al eliminar el nodo que querias");
					state = 0;
				}
				break;
		}

	}

	void preOrder(Node<Integer> nodeAct,int nuevo, int anterior){
		if (level != 1){
			line(nuevo, level * (offsetY + 2), anterior, (level - 1) * (offsetY + 2));
		}
		fill(255);
		ellipse(nuevo, level * offsetY, 30,30);
		textAlign(CENTER);
		textSize(12);
		fill(0);
		text(nodeAct.data.toString(), nuevo, (level * offsetY) + 5);

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
