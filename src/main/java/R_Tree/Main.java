package R_Tree;

public class Main {
    public static void main(String[] args) {
        RTree rTree = new RTree();
        rTree.insert(new Point(10,10));
        rTree.insert(new Point(20,20));
        rTree.insert(new Point(100,100));
        rTree.insert(new Point(120,120));
        rTree.insert(new Point(115,115));
        rTree.insert(new Point(15,15));
        System.out.println("hola");
    }
}
