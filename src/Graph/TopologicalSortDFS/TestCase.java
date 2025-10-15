package Graph.TopologicalSortDFS;

public class TestCase {
    public static void main(String[] args) {
        Graph g1 = new Graph(); // 0 -> 1 -> 2 -> 3 -> 4
        g1.connect(0, 1);
        g1.connect(1, 2);
        g1.connect(2, 3);
        g1.connect(3, 4);
        System.out.println(g1.topologicalSort());


        Graph g2 = new Graph();
        g2.connect(0,1);
        g2.connect(0,2);
        g2.connect(2,3);
        g2.connect(1,4);
        g2.connect(3,5);
        g2.connect(4,6);
        g2.connect(6,7);
        g2.connect(4,8);
        g2.connect(4,11);
        g2.connect(8,15);
        g2.connect(7,17);
        g2.connect(7,15);
        g2.connect(8,16);
        System.out.println(g2.topologicalSort());
    }
}
