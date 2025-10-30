package Graph.Prim.Matrix;

import java.util.Arrays;
import java.util.List;

public class Tests {
    public static void main(String[] args) {
        Graph g1 = new Graph(5);
        g1.connect(0, 1, 2);
        g1.connect(1, 2, 3);
        g1.connect(2, 3, 4);
        g1.connect(1, 4, 1);
        g1.connect(1, 3, 1);
        g1.connect(3, 4, 2);
        g1.connect(1, 2, 1);

        List<int[]> mst = g1.getMST();
        for (int[] e : mst) {
            System.out.println(Arrays.toString(e));
        }

    }


}
