package Graph.SSSP;

import Graph.SSSP.Dijkstra.DijkstraSSSP;

public class Test {
    public static void main(String[] args) {
        DijkstraSSSP g1 = new DijkstraSSSP(6);
        g1.connect(0, 1, 3);
        g1.connect(1, 2, 3);
        g1.connect(2, 3, 4);
        g1.connect(3, 4, 5);
        g1.connect(2, 5, 1);
        g1.connect(2, 4, 2);
        g1.connect(4, 2, 1);

        System.out.println(g1.shortestDistance(0, 5));
        System.out.println(g1.shortestPath(0, 5));

        System.out.println(g1.shortestDistance(0, 4));
        System.out.println(g1.shortestPath(0, 4));

        System.out.println(g1.shortestDistance(0, 3));
        System.out.println(g1.shortestPath(0, 3));

        System.out.println(g1.shortestDistance(3, 5));
        System.out.println(g1.shortestPath(3, 5));
    }
}
