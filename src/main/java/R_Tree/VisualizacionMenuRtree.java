package R_Tree;

import processing.core.PApplet;

import java.util.Random;
import java.util.Scanner;

public class VisualizacionMenuRtree extends PApplet {

    final int DIM_X = RTree.WIDTH_SCREEN;
    final int DIM_Y = RTree.HEIGHT_SCREEN;
    static RTree tree = new RTree();
    Random rd = new Random();
    Scanner sc = new Scanner(System.in);


    public static void visualize(String[] args) {
        /*Random rd = new Random();

        for (int i = 0; i < 1000; i++) {
            int x = rd.nextInt(RTree.WIDTH_SCREEN);
            int y = rd.nextInt(RTree.HEIGHT_SCREEN);
            try{
                tree.insert(new Point(x,y));
            }catch (Exception e){
            }
        }

        Point[] result = tree.fintPointsNear(new Point(250,250),20,20);
        System.out.println("hola");*/
        PApplet.main("R_Tree.VisualizacionMenuRtree", args);
    }


    public void settings() {
        size(DIM_X, DIM_Y);
    }

    public void setup() {
        background(51);
        preOrder(tree.root, 0);
    }


    public void draw() {
        System.out.println("Eliminar punto?");
        int x = sc.nextInt();
        System.out.println("introduce Y");
        int y = sc.nextInt();
        if(tree.deletePoint(new Point(x,y))){
            clear();
            background(51);
            preOrder(tree.root,0);
        }
    }



    private void preOrder(NodeRTree node, int level) {
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
                    if (!node.points[i].dontShow){
                        x = node.points[i].x;
                        y = node.points[i].y;
                        strokeWeight(4);
                        stroke(rColor, gColor, bColor);
                        point(x, y);
                    }
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
                    text(Character.toString((char) (65 + i)) + level, minPoint.x + (maxPoint.x - minPoint.x) / 2, minPoint.y + (maxPoint.y - minPoint.y) / 2);
                    preOrder(node.regions[i], level + 1);
                }
            }
        }
    }

}
