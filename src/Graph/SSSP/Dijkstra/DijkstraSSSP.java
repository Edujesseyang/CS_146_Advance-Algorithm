package Graph.SSSP.Dijkstra;

import java.util.*;

public class DijkstraSSSP {
    private final List<List<int[]>> adjacentList;  // element is [to, weight]
    private final int size;
    private final int[] predRecord;
    private final int[] distRecord;
    private int lastTimeStart;

    public DijkstraSSSP(int size) {
        adjacentList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            adjacentList.add(new ArrayList<>());
        }
        predRecord = new int[size];
        distRecord = new int[size];
        this.size = size;
    }

    public void connect(int from, int to, int weight) {
        if (weight < 0) throw new IllegalArgumentException("Dijkstra cannot have negative edge weight");
        adjacentList.get(from).add(new int[]{to, weight});
        lastTimeStart = -1;
    }

    /**
     * find SSSP
     *
     * @param start : starting point
     */
    public void DijkstraSSSP(int start) {
        boolean[] isVisited = new boolean[size]; //

        for (int i = 0; i < size; i++) {
            int MAX_WEIGHT_ALLOW = 1000;
            distRecord[i] = MAX_WEIGHT_ALLOW;
            predRecord[i] = -1;
            isVisited[i] = false;
        }
        distRecord[start] = 0;

        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparing(x -> x[0])); // store [key, v], key is dist
        pq.offer(new int[]{0, start});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int curV = cur[1], key = cur[0];

            if (isVisited[curV] || key != distRecord[curV]) continue;
            isVisited[curV] = true;

            for (int[] next : adjacentList.get(curV)) {
                int nextV = next[0], weight = next[1];
                if (isVisited[nextV]) continue;
                if (distRecord[nextV] > weight + distRecord[curV]) {
                    distRecord[nextV] = weight + distRecord[curV];
                    predRecord[nextV] = curV;
                    pq.offer(new int[]{distRecord[nextV], nextV});
                }
            }
        }
        lastTimeStart = start;
    }

    public int shortestDistance(int start, int end) {
        if (lastTimeStart != start) DijkstraSSSP(start);
        return distRecord[end];
    }

    public String shortestPath(int start, int end) {
        if (lastTimeStart != start) DijkstraSSSP(start);
        int i = end;
        StringBuilder sb = new StringBuilder();
        sb.append(end).append(" <-- ");
        while (predRecord[i] != -1) {
            sb.append(predRecord[i]).append(" <-- ");
            i = predRecord[i];
        }
        sb.delete(sb.length() - 5, sb.length() - 1);
        return sb.toString();
    }
}
