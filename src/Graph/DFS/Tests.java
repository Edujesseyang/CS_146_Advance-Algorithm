package Graph.DFS;

public class Tests {

    public static void main(String[] args) {
        Graph g1 = new Graph(6);
        g1.connect(1, 4, 2);
        g1.connect(2, 1, 3);
        g1.connect(3, 2, 6);
        g1.printMatrix(); // path 1: 3-2-1-4

        g1.connect(3, 0, 9);
        g1.connect(0, 5, 4);
        g1.connect(5, 4, 3);
        g1.printMatrix(); // path 2 : 3-0-5-4

        g1.printPathsRecursive(3, 4);
        g1.printPathsIterating(3, 4);


    }
}
