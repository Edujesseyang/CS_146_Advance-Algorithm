package Graph.MatrixGraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class Graph {
    private final int MAX_WEIGHT = 10000;
    private final int size;
    private final int[][] matrix;

    Graph(int size) {
        this.size = size;
        matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = MAX_WEIGHT + 1;
            }
        }
    }

    void connect(int from, int to) {
        matrix[from][to] = 1;
    }

    List<Integer> getOutgoingList(int from) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < size; i++) if (matrix[from][i] != MAX_WEIGHT + 1) res.add(i);
        return res;
    }

    List<Integer> getIncomingList(int to) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < size; i++) if (matrix[i][to] != MAX_WEIGHT + 1) res.add(i);
        return res;
    }

    int getInDegree(int to) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (matrix[i][to] != MAX_WEIGHT + 1) count++;
        }
        return count;
    }

    int[] makeInDegreeList() {
        int[] inDegree = new int[size];
        // make a list of all indegrees
        for (int toInd = 0; toInd < size; toInd++) inDegree[toInd] = getInDegree(toInd);
        return inDegree;
    }

    List<Integer> getTopologicalOrder() {
        Deque<Integer> que = new ArrayDeque<>();
        int[] inDegree = makeInDegreeList();
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            if (getInDegree(i) == 0){
                que.push(i);
                res.add(i);
            }
        }

        while (!que.isEmpty()) {
            int cur = que.pop();
            List<Integer> outgoingList = getOutgoingList(cur);
            for (Integer i : outgoingList) {
                inDegree[i]--;
                if (inDegree[i] == 0) {
                    que.add(i);
                    res.add(i);
                }
            }
        }
        return res;
    }
}


