package Graph.Prim;

import Graph.Prim.AdjacentList.PrimGraphList;
import Graph.Prim.AdjacentMatrix.PrimGraphMatrix;

import java.util.Arrays;
import java.util.List;

public class Tests {
    public static void main(String[] args) {
        Prim g1 = new PrimGraphMatrix(5);
        g1.connect(0, 1, 2);
        g1.connect(1, 2, 3);
        g1.connect(2, 3, 4);
        g1.connect(1, 4, 1);
        g1.connect(1, 3, 1);
        g1.connect(3, 4, 2);
        g1.connect(0, 2, 1);

        List<int[]> mst = g1.getMST();
        System.out.println("Use adjacent matrix");
        for (int[] e : mst) {
            System.out.println(Arrays.toString(e));
        }

        Prim g2 = new PrimGraphList(5);
        g2.connect(0, 1, 2);
        g2.connect(1, 2, 3);
        g2.connect(2, 3, 4);
        g2.connect(1, 4, 1);
        g2.connect(1, 3, 1);
        g2.connect(3, 4, 2);
        g2.connect(0, 2, 1);

        List<int[]> mst2 = g2.getMST();

        System.out.println("Use adjacent list");
        for (int[] e : mst) {
            System.out.println(Arrays.toString(e));
        }

    }


}
