package R_Tree;

import processing.core.PApplet;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class VisualizacionRTree extends PApplet {

    final int DIM_X = RTree.WIDTH_SCRREN;
    final int DIM_Y = RTree.HEIGHT_SCREEN;
    static RTree tree = new RTree();
    Random rd = new Random();
    Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        Random rd = new Random();

        for (int i = 0; i < 5; i++) {
            int x = rd.nextInt(RTree.WIDTH_SCRREN);
            int y = rd.nextInt(RTree.HEIGHT_SCREEN);
            try{
                tree.insert(new Point(x,y));
            }catch (Exception e){
                tree.insert(new Point(x,y));
                System.out.println("12");
            }
        }

        PApplet.main("R_Tree.VisualizacionRTree", args);
        System.out.println("12");
    }


    public void settings() {
        size(DIM_X, DIM_Y);
    }

    public void setup() {
        background(51);
        preOrder(tree.root,0);
    }


    public void draw(){/*
        int x;
        int y;
        System.out.println("introduce X");
        x = sc.nextInt();
        System.out.println("introduce Y");
        y = sc.nextInt();
        tree.insert(new Point(x,y));
        LEVEL = 0;
        checkHeigh(tree.root);
        preOrder(tree.root,0);*/
    }

    private void preOrder(NodeRTree node,int level) {
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
                    noFill();
                    Point maxPoint = node.regions[i].maxPoint;
                    Point minPoint = node.regions[i].minPoint;
                    strokeWeight(1);
                    stroke(rColor, gColor, bColor);
                    rect(minPoint.x, minPoint.y, maxPoint.x - minPoint.x, maxPoint.y - minPoint.y);
                    textAlign(CENTER);
                    textSize(10);
                    text(Character.toString((char) (65+i)) +level,minPoint.x+(maxPoint.x-minPoint.x)/2,minPoint.y+(maxPoint.y-minPoint.y)/2);
                    preOrder(node.regions[i],level+1);
                }
            }
        }
    }

}
