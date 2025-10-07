package Graph.VisitedImplementation;

import java.util.ArrayList;
import java.util.List;

public class Graph<T> {
    List<Vertex<T>> vertexList;
    List<Edge> edgeList;

    Graph() {
        vertexList = new ArrayList<>();
        edgeList = new ArrayList<>();
    }

    void addVertex(T data) {
        Vertex<T> newVer = new Vertex<>(data);
        vertexList.add(newVer);
    }

}
