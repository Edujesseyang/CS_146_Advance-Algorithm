package Graph.KahnAlgorithm;

import java.sql.Array;
import java.util.*;

public class Graph {
    List<List<Integer>> outgoingList;
    List<List<Integer>> incomingList;
    int size;

    Graph(int size) {
        this.size = size;
        outgoingList = new ArrayList<>(size);
        incomingList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            outgoingList.add(new ArrayList<>());
            incomingList.add(new ArrayList<>());
        }
    }

    void connect(int from, int to) {
        outgoingList.get(from).add(to);
        incomingList.get(to).add(from);
    }

    /**
     * Kahn's Algorithm
     *
     * @return sortedList in topological order
     */
    List<Integer> topologicalSort() {
        Deque<Integer> queue = new ArrayDeque<>(); // que for looping
        List<Integer> indegreeMap = new ArrayList<>(); // in-degree list for all vertices' in-degree
        List<Integer> result = new ArrayList<>();  // result for returning

        for (int i = 0; i < size; i++) { // init all in-degree,  add starting points in to que
            int indegree = incomingList.get(i).size(); // get in-degree
            indegreeMap.add(indegree);   // add in-degree to map
            if (indegree == 0) {  // add all vertex who in-degree == 0 to que
                queue.offer(i);
                result.add(i); // add to result
            }
        }

        while (!queue.isEmpty()) { // loop until que is empty
            int ver = queue.poll(); // poll one vertex's incomingList
            for (Integer to : outgoingList.get(ver)) { // "to" is each vertex that current vertex is going to
                indegreeMap.set(to, indegreeMap.get(to) - 1); // dec indegree for each "to" vertex
                if (indegreeMap.get(to) == 0) { // check if "to" vertex has 0 in-degree
                    queue.offer(to);  // if so, add "to" to que
                    result.add(to); // add it to result as well
                }
            }
        }

        if (result.size() == size) { // check if the result's size is equal to the graph's size
            return result; // if so, return result
        } else {
            System.out.println(result);
            throw new RuntimeException("Cycle founded"); // if not, cycle found
        }
    }
}
