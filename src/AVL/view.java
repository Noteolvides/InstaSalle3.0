package AVL;

import AVL.Node;

import javax.swing.*;

public class view extends JFrame {
    static int level = 2;
    static int x = 600;
    static int y = 50;

    public view(Node tree) {
        int width = 600;
        int height = 600;
        setSize(width, height);
        setLayout(null);
        printTree(tree);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void printTree(Node tree) {
        JButton aux = new JButton(tree.data.toString());
        aux.setLocation(x,y);
        aux.setSize(50,50);
        add(aux);
        if (tree.leftChild != null) {
            int xaux = x;
            int yaux = y;
            x = x - x/level++;
            y = y+50;
            printTree(tree.leftChild);
            x = xaux;
            y = yaux;
        }
        if (tree.rightChild != null) {
            int xaux = x;
            int yaux = y;
            x = x + x/level++;
            y = y+50;
            printTree(tree.rightChild);
            x = xaux;
            y = yaux;
        }
    }

}
