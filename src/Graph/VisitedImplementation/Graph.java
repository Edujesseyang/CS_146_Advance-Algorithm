package Graph.VisitedImplementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Graph {
    static int idCounter = 0;
    List<List<Integer>> outGoingList = new ArrayList<>(); // store the id of edge
    List<List<Integer>> inCommingList = new ArrayList<>();
    /*  back track table example
     | vertex | outGoingEdges |
     | 4      | null          |  --> vertex without any outgoing edges
     | 3      | edge[3,4,5.4] |
     | 2      | edge[2,3,1.3] |
     | 1      | edge[1,2,2.4] |
    * */

    List<Edge> edgeList = new ArrayList<>(); // store the id's
    List<int[]> backTrackTable = new ArrayList<>();   //[pred, weight]
    /*  back track table example
     | vertex | predecessor | distance |
     | 4      | 3           | 5.4      |
     | 3      | 2           | 1.3      |
     | 2      | 1           | 2.4      |
    * */
    int size;

    Graph(int size) {
        this.size = size;
        ensureSpace(size);
    }

    void connect(int from, int to, int weight) {
        ensureSpace(Math.max(from, to));
        Edge edge = new Edge(from, to, weight);

        edge.id = idCounter++;  // giving new edge an id, means it is active now.
        edgeList.add(edge); // store edge
        if (outGoingList.get(from) == null) { // if vertex has no edge, create one
            outGoingList.set(from, new ArrayList<>(List.of(edge.id)));
        } else {
            outGoingList.get(from).add(edge.id); // if there is one, add a new one
        }
    }

    void ensureSpace(int max) {
        if (max < size) return;
        for (int i = size; i <= max; i++) outGoingList.add(new ArrayList<>());
        for (int i = size; i <= max; i++) inCommingList.add(new ArrayList<>());
        for (int i = size; i <= max; i++) backTrackTable.add(new int[2]);
    }

    void resetBackTrackTable() {
        backTrackTable.clear();
    }

    private void dfs(int from, int to) {
        backTrackTable.clear();
        ensureSpace(Math.max(from, to));
        System.out.println(backTrackTable.size());
        Stack<Integer> stk = new Stack<>();
        stk.push(from);
        while (!stk.isEmpty()) {
            int cur = stk.pop();
            List<Integer> neighbors = outGoingList.get(cur);
            for (int i : neighbors) {
                Edge e = edgeList.get(i);
                if (e.to == to) backTrackTable.set(to, new int[]{i, e.weight});
                stk.push(e.to);
            }
        }
    }

    int shortestPath(Integer from, Integer to) {
        dfs(from, to);
        int dist = 0;
        Integer cur = to;
        while (cur != null) {
            cur = backTrackTable.get(cur)[0]; // back up cur first
            dist += backTrackTable.get(cur)[1];// add the weight
            if (cur.equals(from)) break;
        }
        return dist;
    }
}
