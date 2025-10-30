package Graph.DFS;

import java.util.*;

public class Graph {
    List<List<Integer>> graph;
    int size;

    Graph(int size) {
        this.size = size;
        graph = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                row.add(Integer.MAX_VALUE);
            }
            graph.add(row);
        }
    }

    void connect(Integer from, Integer to, Integer weight) {
        if (from >= size || to >= size) return;  // out of bound
        graph.get(from).set(to, weight);
    }

    public void printMatrix() {
        for (List<Integer> row : graph) {
            for (Integer i : row) {
                if (i == Integer.MAX_VALUE) {
                    System.out.print("X" + " ");
                } else {
                    System.out.print(i + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private List<Deque<Integer>> getAllPathRecursive(int start, int end) {
        List<Deque<Integer>> res = new ArrayList<>();
        Deque<Integer> path = new LinkedList<>();  // use as stack
        path.push(start);
        boolean[] isVisited = new boolean[size];
        Arrays.fill(isVisited, false);
        isVisited[start] = true;

        dfs(graph, isVisited, res, path, start, end);
        return res;
    }

    private void dfs(List<List<Integer>> g, boolean[] isVisited, List<Deque<Integer>> res, Deque<Integer> path, int cur, int end) {
        if (cur == end) {
            res.add(new LinkedList<>(path)); // snapshot the path
            return;
        }

        for (int v = 0; v < size; v++) { // loop the index, not the value
            if (!isVisited[v] && g.get(cur).get(v) != Integer.MAX_VALUE) { // is not visited, has edge
                isVisited[v] = true;  // mark visited
                path.addLast(v);      // add to path

                dfs(g, isVisited, res, path, v, end);   // recurse

                path.removeLast();           // backtrack, remove cur from the stack
                isVisited[v] = false;        // reset neighbor as not visited for future use
            }
        }
    }

    public List<Deque<Integer>> getAllPathIterating(int start, int end) {
        List<Deque<Integer>> res = new ArrayList<>();

        boolean[] isVisited = new boolean[size];
        Arrays.fill(isVisited, false);
        int[] nextNeighbor = new int[size];
        Arrays.fill(nextNeighbor, 0);

        Deque<Integer> path = new ArrayDeque<>();
        path.addLast(start);
        isVisited[start] = true;

        while (!path.isEmpty()) {
            int cur = path.peekLast(); // get cur vertex

            if (cur == end) {  // check cur, if it is the target
                res.add(new ArrayDeque<>(path));  // snapshot path
                path.removeLast();                // backtrack remove cur
                isVisited[cur] = false;           // reset cur for future use
                nextNeighbor[cur] = 0;
                continue;
            }

            int next = -1;  // init the next vertex we are gonna to go
            for (int v = nextNeighbor[cur]; v < size; v++) {
                if (!isVisited[v] && graph.get(cur).get(v) != Integer.MAX_VALUE) { // with edge and not visited
                    next = v;      // use it as next
                    nextNeighbor[cur] = v + 1;  // store this vertex that we use it as starting point for checking the next
                    break;
                }
            }

            if (next == -1) {  // mean no more neighbors
                path.removeLast();  // backtrack
                isVisited[cur] = false; // reset
                nextNeighbor[cur] = 0;
            } else { // find a next neighbor
                path.addLast(next);      // go to that neighbor
                isVisited[next] = true;   // mark neighbor as visited
                nextNeighbor[next] = 0;   // new neighbor search from 0
            }
        }
        return res;
    }

    public void printPathsRecursive(int start, int end) {
        printAllPaths(getAllPathRecursive(start, end));
    }

    public void printPathsIterating(int start, int end) {
        printAllPaths(getAllPathIterating(start, end));
    }

    private void printAllPaths(List<Deque<Integer>> paths) {
        for (Deque<Integer> stack : paths) {
            while (!stack.isEmpty()) {
                System.out.print(stack.poll() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
