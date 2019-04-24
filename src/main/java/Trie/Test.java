package Trie;

import processing.core.PApplet;

import java.awt.*;

public class Test extends PApplet {

	private final int dimensionx = 1200;
	private final int dimensiony = 700;
	int level = 1;
	int offsetY = 40;
	int circuloSize = 30;
	TrieTree tree = new TrieTree();
	int state = 0;
	String result = "";


	public static void main(String... args) {
		PApplet.main("Trie.Test", args);
	}

	public void settings() {
		size(dimensionx, dimensiony);
	}

	public void setup() {
		background(51);
		frameRate(30);

	}

	private void draw2(Node<Character> node, Point previus, Point ant, Point next) {
		int cuantos = 0;
		for (Character c : node.children.keySet()) {
			cuantos += node.children.get(c).children.size();
		}
		int i = 1;
		for (Character c : node.children.keySet()) {
			int dif;
			Point newPrevius = null,newAnt,newNext;
			dif = ant.x - previus.x;
			newAnt = new Point(dif/(cuantos+1) * i, (int) (ant.getY()+offsetY));
			if (i == 1){
				newPrevius = new Point((int)previus.getX()-30,(int)previus.getY()+offsetY);
			}
			if (i > 1){
				newPrevius = new Point(dif/(cuantos+1) * (i-1), (int) (ant.getY()+offsetY));
			}
			if (i == cuantos){
				newNext = new Point((int)next.getX()-30,(int)next.getY()+offsetY);
			}else{
				newNext = new Point(dif/(cuantos+1) * (i+1), (int) (ant.getY()+offsetY));
			}
			i++;
			line((int)ant.getX(),(int) ant.getY(),(int) newAnt.getX(),(int) newAnt.getY());
			fill(255, node.isWord ? 0 : 255, node.isWord ? 0 : 255);
			ellipse((int)newAnt.getX(),(int)newAnt.getY(), circuloSize, circuloSize);
			textAlign(CENTER);
			textSize(12);
			fill(0);
			text(node.children.get(c).data, (int)newAnt.getX(), (int)newAnt.getY());
			draw2(node.children.get(c),newPrevius,newAnt,newNext);
		}

	}

	public void draw() {
		clear();
		background(51);
		if (tree.root != null) {
			draw2(tree.root, new Point(0,0),new Point(width-100,offsetY),new Point(dimensionx,0));
		} else {
			fill(255);
			text("No hay mas nodos", width / 2, width / 2);
		}

		switch (state) {
			case 0:
				fill(255);
				text("Inserta o elimina nodo \n" + result, 133, 50);
				break;

			case 1:
				fill(255, 2, 2);
				text("Gracias Eliminar el nodo \n" + result, 133, 100);
				tree.insert(result);
				result = "";
				state = 0;
		}

	}

	private void drawNodes(Node<Character> node, int nuevo, int anterior) {
		if (level != 1) {
			line(nuevo, level * (offsetY + 2), anterior, (level - 1) * (offsetY + 2));
		}
		fill(255, node.isWord ? 0 : 255, node.isWord ? 0 : 255);
		ellipse(nuevo, level * offsetY, 30, 30);
		textAlign(CENTER);
		textSize(12);
		fill(0);
		String value;
		if (node.data == null) {
			value = "root";
		} else {
			value = node.data.toString();
		}
		text(value, nuevo, (level * offsetY) + 5);

		if (node.children.size() > 0) {
			int i = 1;

			for (Character c : node.children.keySet()) {
				level++;
				drawNodes(node.children.get(c), (width / (node.children.size() + 1)) * i, nuevo);
				i++;
				level--;
			}
		}
	}

	public void keyPressed() {
		if (key == ENTER || key == RETURN) {
			state++;
		} else if (key == BACKSPACE) {
			if (result.length() > 0) {
				StringBuilder aux = new StringBuilder(result);
				result = aux.deleteCharAt(aux.length() - 1).toString();
			}
		} else
			result = result + key;
	}

}
