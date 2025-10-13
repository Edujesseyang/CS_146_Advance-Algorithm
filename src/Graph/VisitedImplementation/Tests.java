package Graph.VisitedImplementation;

import org.junit.*;

public class Tests {
    @Test
    public void testMakingGraph() {
        Graph g1 = new Graph(5);
        g1.connect(1, 2, 1);
        g1.connect(2, 3, 1);
        g1.connect(3, 4, 1);
        System.out.println(g1.shortestPath(1, 4));
    }
}
