package Graph.Prim;

import java.util.List;

public interface Prim {
    void connect(int from, int to, int weight);

    List<int[]> getMST();
}
