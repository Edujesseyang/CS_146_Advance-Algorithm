package Graph.Prim.AdjacentMatrix;

import Graph.Prim.Prim;

import java.util.*;

public class PrimGraphMatrix implements Prim {
    private final int[][] matrix;
    private final int size;
    private final int MAX_WEIGHT = 1000;

    public PrimGraphMatrix(int size) {
        matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = -1;
            }
        }
        this.size = size;
    }

    public void connect(int from, int to, int weight) {
        matrix[from][to] = weight;
        matrix[to][from] = weight;
    }

    /**
     * returns minimal spanning tree if all vertices are connected. if not, return minimal spanning jungle.
     */
    public List<int[]> getMST() {
        int[] price = new int[size]; // price record of v to component
        boolean[] isInMST = new boolean[size];  // flag is v is in MST
        int[] pred = new int[size];   // pred record
        List<int[]> edgesOfMST = new ArrayList<>(); // result list

        for (int i = 0; i < size; i++) { // init
            price[i] = MAX_WEIGHT;  // max out all weight
            isInMST[i] = false;     // all v are undo
            pred[i] = -1;           // all v's pred are unknown
        }
        price[0] = 0; // start at arbitrary vertex

        Queue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparing(element -> element[1])); //[v, price], compare price
        priorityQueue.offer(new int[]{0, 0}); // dummy, v0 to MST, weight = 0;

        while (!priorityQueue.isEmpty()) {
            int[] cur = priorityQueue.poll(); // get min element of priority queue
            int curVertex = cur[0], distToComponent = cur[1];  // parse out cur info
            if (isInMST[curVertex] || distToComponent != price[curVertex]) continue;  // check if it needs to do

            if (pred[curVertex] != -1) {
                edgesOfMST.add(new int[]{curVertex, pred[curVertex]}); // add to MST
            }
            isInMST[curVertex] = true; // set flag it is already in MST

            for (int nextVertex = 0; nextVertex < size; nextVertex++) {
                int weightToNext = matrix[curVertex][nextVertex];
                if (weightToNext == -1) continue; // no edge
                // if (isInMST[nextVertex]) continue; // cycling finding vertex that had done already
                if (price[nextVertex] > weightToNext) {
                    price[nextVertex] = weightToNext; // relax
                    pred[nextVertex] = curVertex;
                    priorityQueue.offer(new int[]{nextVertex, weightToNext}); // enqueue all edges
                }
            }
        }

        return edgesOfMST;
    }


}
