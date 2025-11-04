package Graph.Prim.AdjacentList;

import Graph.Prim.Prim;

import java.util.*;

public class PrimGraphList implements Prim {
    private final List<List<int[]>> adjacentList;  // store [to, weight]
    private final int size;
    private final int MAX_WEIGHT_ALLOWED = 10000;

    public PrimGraphList(int size) {
        adjacentList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            adjacentList.add(new ArrayList<>());
        }
        this.size = size;
    }

    public void connect(int from, int to, int weight) {
        adjacentList.get(from).add(new int[]{to, weight});
    }

    public List<int[]> getMST() {
        int[] priceMap = new int[size];
        int[] pred = new int[size];
        boolean[] inMST = new boolean[size];
        List<int[]> mst = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            priceMap[i] = MAX_WEIGHT_ALLOWED;
            pred[i] = -1;
            inMST[i] = false;
        }
        priceMap[0] = 0;

        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparing(element -> element[0]));  // [priceKey, v]
        pq.offer(new int[]{0, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int curV = cur[1], priceKey = cur[0];

            if (inMST[curV] || priceKey != priceMap[curV]) continue;

            if (pred[curV] != -1) mst.add(new int[]{pred[curV], curV});
            inMST[curV] = true;

            for (int[] i : adjacentList.get(curV)) {
                int nextV = i[0], weight = i[1];
                if (inMST[nextV]) continue;
                if (priceMap[nextV] > weight) {
                    priceMap[nextV] = weight;
                    pred[nextV] = curV;
                    pq.offer(new int[]{weight, nextV});
                }
            }
        }
        return mst;
    }
}
