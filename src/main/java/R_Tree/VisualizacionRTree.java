package R_Tree;

import processing.core.PApplet;

import java.util.Random;
import java.util.Scanner;

public class VisualizacionRTree extends PApplet {

    final int DIM_X = RTree.WIDTH_SCRREN;
    final int DIM_Y = RTree.HEIGHT_SCREEN;

    static RTree tree = new RTree();
    Random rd = new Random();
    byte letter = 65;
    Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        Random rd = new Random();
        /*
        for (int i = 0; i < 15; i++) {
          tree.insert(new Point(rd.nextInt(RTree.WIDTH_SCRREN),rd.nextInt(RTree.HEIGHT_SCREEN)));
        }*/
        PApplet.main("R_Tree.VisualizacionRTree", args);
    }


    public void settings() {
        size(DIM_X, DIM_Y);
    }

    public void setup() {
        background(51);
        preOrder(tree.root);


    }

    public void draw(){

        int x;
        int y;
        System.out.println("introduce X");
        x = sc.nextInt();
        System.out.println("introduce Y");
        y = sc.nextInt();
        tree.insert(new Point(x,y));
        preOrder(tree.root);

    }

    private void preOrder(NodeRTree node) {
        int x;
        int y;

        int rColor;
        int gColor;
        int bColor;


        if (node.isLeaf) {
            rColor = rd.nextInt(255);
            gColor = rd.nextInt(255);
            bColor = rd.nextInt(255);
            for (int i = 0; i < node.indexArray; i++) {
                if (node.points[i] != null) {
                    x = node.points[i].x;
                    y = node.points[i].y;
                    strokeWeight(4);
                    stroke(rColor, gColor, bColor);
                    point(x, y);
                }
            }
        } else {
            rColor = rd.nextInt(255);
            gColor = rd.nextInt(255);
            bColor = rd.nextInt(255);
            for (int i = 0; i < node.indexArray; i++) {
                if (node.regions[i] != null) {
                    preOrder(node.regions[i]);
                    noFill();
                    Point maxPoint = node.regions[i].maxPoint;
                    Point minPoint = node.regions[i].minPoint;
                    strokeWeight(1);
                    stroke(rColor, gColor, bColor);
                    rect(minPoint.x, minPoint.y, maxPoint.x - minPoint.x, maxPoint.y - minPoint.y);
                    /*
                    if (letter == 91){
                        letter = 65;
                    }
                    text((char)letter,(maxPoint.x-minPoint.x),30);
                    letter++;*/
                }
            }
        }
    }

}
