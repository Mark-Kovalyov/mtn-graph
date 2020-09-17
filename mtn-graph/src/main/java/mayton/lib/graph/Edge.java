package mayton.lib.graph;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Objects;

@SuppressWarnings({"java:S1948", "java:S125"})
public class Edge<V extends Object, E> implements Serializable {

    static final long serialVersionUID = 2L;

    // Key fields
    private Vertex<V, E> v1;
    private Vertex<V, E> v2;

    E edgeValue;

    public Edge(@NotNull Vertex<V, E> v1, @NotNull Vertex<V, E> v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Edge(@NotNull Vertex<V, E> v1, @NotNull Vertex<V, E> v2, E value) {
        this.v1 = v1;
        this.v2 = v2;
        this.edgeValue = value;
    }

    public Edge() {
        // For serialization
    }


    public Vertex<V, E> getV1() {
        return v1;
    }

    public void setV1(@NotNull Vertex<V, E> v1) {
        this.v1 = v1;
    }

    public Vertex<V, E> getV2() {
        return v2;
    }

    public void setV2(@NotNull Vertex<V, E> v2) {
        this.v2 = v2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Edge<V, E> edge = (Edge) o;

        if (Objects.equals(v1, edge.v1) && Objects.equals(v2, edge.v2)) {
            return true;
        }

        return Objects.equals(v1.getId(), edge.v1.getId())
                && Objects.equals(v2.getId(), edge.v2.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(v1.getId(), v2.getId());
    }

    @Override
    public String toString() {
        return String.format("Edge: (%s -> %s) ", v1.getId(), v2.getId());
    }

    @Nullable
    public E getValue() {
        return edgeValue;
    }

    public void setValue(@Nullable E edgeValue) {
        this.edgeValue = edgeValue;
    }
}
