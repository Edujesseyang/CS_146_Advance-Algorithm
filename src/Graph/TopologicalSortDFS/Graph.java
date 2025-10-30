package Graph.TopologicalSortDFS;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class Graph {

    private class Vertex {
        int id;
        List<Integer> outGoingList;
        List<Integer> inCominglist;
        boolean isVisited = false;
        int color = 0; // 0=white(non-visited), 1=gray(in stack), 2=black(finished)

        Vertex(int id) {
            this.id = id;
            outGoingList = new ArrayList<>();
            inCominglist = new ArrayList<>();
        }
    }

    List<Vertex> vertices;

    Graph() {
        this.vertices = new ArrayList<>();
    }

    void ensureSpace(int n) {
        if (n < vertices.size()) return;
        for (int i = vertices.size(); i <= n; i++) vertices.add(new Vertex(vertices.size()));
    }

    void connect(int from, int to) {
        ensureSpace(Math.max(from, to));
        vertices.get(from).outGoingList.add(to);
        vertices.get(to).inCominglist.add(from);
    }

    void refresh() {
        for (Vertex v : vertices) {
            v.color = 0;
            v.isVisited = false;
        }
    }

    List<Integer> topologicalSort() {
        refresh();
        List<Integer> result = new ArrayList<>();
        for (Vertex ver : vertices) {
            int last = dfs(ver);
            result.add(last);
            vertices.get(last).color = 2;
        }
        return result;
    }


    int dfs(Vertex v) {
        v.color = 1;
        int end = v.id;
        for (Integer i : v.outGoingList) {
            Vertex next = vertices.get(i);
            if (next.color == 0) {
                end = dfs(vertices.get(i));
            }
            if (next.outGoingList.isEmpty()) next.color = 2;
        }
        return end;
    }
}
