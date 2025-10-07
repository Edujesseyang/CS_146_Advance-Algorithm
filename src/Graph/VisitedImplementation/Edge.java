package Graph.VisitedImplementation;

import java.util.Objects;

public class Edge implements Comparable<Edge> {
    int weight;
    Vertex<?> from;
    Vertex<?> to;

    Edge(Vertex<?> from, Vertex<?> to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge that) {
        return this.weight - that.weight;
    }

    @Override
    public boolean equals(Object that) { // check if the edge is same
        if (this == that) return true;
        if (this.getClass() != that.getClass()) return false;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(from) + Objects.hashCode(to);
    }
}
