package mayton.lib.graph;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;

/**
 * Generic vertex with VT (vertex value type) and ET (edge value type)
 *
 * @param <E>
 */
@SuppressWarnings({"java:S1948", "java:S125", "java:S1172", "java:S1135"})
public class Vertex<V, E> implements Serializable {

    static final long serialVersionUID = 1L;

    // Key fields
    private int id;

    // Non-key fields
    @Nullable
    private V value;
    // TODO: Leonid proposes to get rid of Edges instead of primitives.
    private transient List<Edge<V, E>> outgoingEdges = new ArrayList<>();
    private transient List<Edge<V, E>> incomingEdges = new ArrayList<>();

    public Vertex(int id) {
        this.id = id;
    }

    public Vertex(int id, V value) {
        this.id = id;
        this.value = value;
    }

    public Vertex() {
        // For serialization
    }

    // Incoming edges

    void addIncomingEdge(@NotNull Edge<V, E> edge) {
        checkArgument(edge.getV2() != this, "Unable to link edge " + edge.toString() + " because of illegal V2 value");
        incomingEdges.add(edge);
    }

    void addIncomingEdgeWithValue(@NotNull Vertex<V, E> sourceVertex, @Nullable E edgeValue) {
        Edge<V, E> incoming = new Edge<>(sourceVertex, this);
        // TODO:
        //incoming.setValue(value);
        incomingEdges.add(incoming);
    }

    void addIncomingEdge(@NotNull Vertex<V, E> sourceVertex) {
        addIncomingEdgeWithValue(sourceVertex, null);
    }

    // Outgoing edges

    void addOutgoingEdge(@NotNull Edge<V, E> edge) {
        checkArgument(edge.getV1() != this, "Unable to link edge " + edge.toString() + " because of illegal V1 value");
        outgoingEdges.add(edge);
    }

    void addOutgoingEdge(@NotNull Vertex<V, E> destinationVertex) {
        addOutgoingEdgeWithValue(destinationVertex, null);
    }

    void addOutgoingEdgeWithValue(@NotNull Vertex<V, E> destinationVertex, @Nullable E edgeValue) {
        Edge<V, E> outgoing = new Edge<>(this, destinationVertex, edgeValue);
        // TODO: WTF?
        outgoing.setValue(edgeValue);
        outgoingEdges.add(outgoing);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<E, V>vertex = (Vertex) o;
        return Objects.equals(id, vertex.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    @NotNull
    public List<Edge<V, E>> getOutgoingEdges() {
        return unmodifiableList(outgoingEdges);
    }

    @NotNull
    public List<Edge<V, E>> getIncomingEdges() {
        return unmodifiableList(incomingEdges);
    }

    @Override
    public String toString() {
        return format("Vertex id = %s, with incoming vertices : %s, and outgoing vertices : %s",
                id,
                incomingEdges.stream()
                        .map(edge -> edge.getV1().id)
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")),
                outgoingEdges.stream()
                        .map(edge -> edge.getV2().id)
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")));
    }

    @Nullable
    public V getValue() {
        return value;
    }

    public void setValue(@Nullable V value) {
        this.value = value;
    }
}
