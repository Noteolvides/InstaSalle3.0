package Trie;

import HashTable.Data;
import com.google.gson.Gson;
import processing.core.PApplet;
import exe.Menu;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Test extends PApplet {
	private final int dimensionx = 1200;
	private final int dimensiony = 700;
	int level = 1;
	int offsetY = 40;
	int circuloSize = 30;
	TrieTree tree = new TrieTree();
	int state = 0;
	String result = "";
	int[] howManyInLevelTotal;
	int[] howManyInLevel;
	int maxLevel;
	ArrayList<String> words = new ArrayList<String>();

	private void checkMaxLevel(Node<Character> node,int level){
		level++;
		for (Data c : node.children.dataSet()) {
			checkMaxLevel(node.children.get(c.getKey().toString().charAt(0)),level);
		}
		if (level >= maxLevel){
			maxLevel = level;
			howManyInLevelTotal = new int[level];
			howManyInLevel = new int[level];
		}
	}

	private void checkHowMany(Node<Character> node,int level){
		howManyInLevelTotal[level] += node.children.size();
		level++;
		for (Data c : node.children.dataSet()) {
			checkHowMany(node.children.get(c.getKey().toString().charAt(0)),level);
		}
	}


	public void visualize(String... args) {
		PApplet.main("Trie.Test", args);
	}

	public void settings() {
		size(dimensionx, dimensiony);
	}

	public void setup() {
		background(51);
		frameRate(30);
		tree = Menu.trieTree;
		checkMaxLevel(tree.root,0);
		checkHowMany(tree.root,0);
	}





	public void draw() {
		clear();
		background(51);
		if (tree.root != null) {
			howManyInLevel = new int[maxLevel];
			drawNodes(tree.root, width/2,width);
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
				if (!words.contains(result)){
					tree.insert(result);
					words.add(result);
				}else{
					tree.delete(result);
					words.remove(result);
				}
				if(tree.root != null){
					result = "";
					checkMaxLevel(tree.root,0);
					checkHowMany(tree.root,0);
					state = 0;
				}
		}

	}

	private void drawNodes(Node<Character> node, int nuevo,int anterior) {
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
			for (Data c : node.children.dataSet()) {
				level++;
				howManyInLevel[level-2] = howManyInLevel[level-2] + 1;
				int nuevoP = (width / (howManyInLevelTotal[level-2] + 1) ) * howManyInLevel[level-2];
				drawNodes(node.children.get(c.getKey().toString().charAt(0)),nuevoP , nuevo);
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
