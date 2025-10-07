package Graph.VisitedImplementation;

import java.util.ArrayList;
import java.util.List;

class Vertex<T> {
    private static final int id = 0;  // FIXME: I want a universal id for each vertex in the graph
    T data;
    List<Edge> incomingEdges = new ArrayList<>();
    List<Edge> outGoingEdges = new ArrayList<>();
    List<Vertex<T>> adjacentVertices = new ArrayList<>();

    Vertex(T data) {
        this.data = data;
    }

    void linkTo(Vertex<T> destination, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        Edge newEdge = new Edge(this, destination, weight);
        outGoingEdges.add(newEdge);
        adjacentVertices.add(destination);
    }

    void linkFrom(Vertex<T> cameFrom, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        Edge newEdge = new Edge(cameFrom, this, weight);
        incomingEdges.add(newEdge);
        adjacentVertices.add(cameFrom);
    }
}
