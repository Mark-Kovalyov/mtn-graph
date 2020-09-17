package mayton.lib.graph;

import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableMap;

/**
 * Directed, Weight graph
 */
@SuppressWarnings({"java:S1948", "java:S125", "java:S1488", "java:S1135", "java:S1133"})
public class Graph<V, E> implements Serializable {

    static Logger logger = LoggerFactory.getLogger(Graph.class);

    private Map<Edge<V, E>, Edge<V, E>> edgeMap;
    private Map<Integer, Vertex<V, E>> vertexMap;

    private Function<E,E> refreshEdgeFunction;

    private BiFunction<V,V,V> joinVertexFunction;

    private E edgeNeutralElement;

    public Graph() {
        vertexMap = new HashMap<>();
        edgeMap = new HashMap<>();
    }

    public Graph(@IntRange(from = 0) int estimatedVertices, @IntRange(from = 0) int estimatedEdges) {
        vertexMap = new HashMap<>(estimatedVertices);
        edgeMap = new HashMap<>(estimatedEdges);
    }

    @Nullable
    public Vertex<V, E> findVertexById(int id) {
        return vertexMap.get(id);
    }

    @NotNull
    public Vertex<V, E> addVertex(int id) {
        if (vertexMap.containsKey(id)) {
            return vertexMap.get(id);
        } else {
            Vertex<V, E> t = new Vertex<>(id);
            vertexMap.put(id, t);
            return t;
        }
    }

    public boolean deleteVertex(int id) {
        if (vertexMap.containsKey(id)) {
            vertexMap.remove(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean containsVertexWithId(int id) {
        return vertexMap.containsKey(id);
    }

    @NotNull
    public List<Edge<V, E>> extractOutgoingEdges(int parentVertexId) {
        Vertex<V, E> parentVertex = vertexMap.get(parentVertexId);
        if (parentVertex == null) {
            return Collections.emptyList();
        } else {
            return parentVertex.getOutgoingEdges();
        }
    }

    @NotNull
    public List<Edge<V, E>> extractIncomingEdges(int destinationVertexId) {
        Vertex<V, E> parentVertex = vertexMap.get(destinationVertexId);
        if (parentVertex == null) {
            return Collections.emptyList();
        } else {
            return parentVertex.getIncomingEdges();
        }
    }

    public boolean containsDirectedEdgeByIds(int id1, int id2) {
        return containsDirectedEdge(new Vertex<>(id1), new Vertex<>(id2));
    }

    public boolean containsDirectedEdge(@NotNull Vertex<V, E> v1, @NotNull Vertex<V, E> v2) {
        return edgeMap.containsKey(new Edge<>(v1, v2));
    }

    @Nullable
    public Edge<V, E> extractEdgeByIds(int id1, int id2) {
        Edge<V, E> keyEdge = new Edge<>(new Vertex<>(id1), new Vertex<>(id2));
        return edgeMap.get(keyEdge);
    }

    @NotNull
    public Edge<V, E> linkEdge(int id1, int id2) {
        return linkEdge(new Vertex<>(id1), new Vertex<>(id2));
    }

    public Edge<V, E> linkEdge(Vertex<V, E> v1, Vertex<V, E> v2) {
        // TODO: Simplify with putIfAbsent
        Vertex<V, E> v1real = v1;
        Vertex<V, E> v2real = v2;
        if (!vertexMap.containsKey(v1.getId())) {
            vertexMap.put(v1.getId(), v1);
        } else {
            v1real = vertexMap.get(v1.getId());
            if (v1.getValue() != null && !v1real.getValue().equals(v1.getValue())) {
                throw new RuntimeException("Unable to link existing vertex id = " + v1.getId() + " with new Value = " + v1.getValue());
            }
        }
        if (!vertexMap.containsKey(v2.getId())) {
            vertexMap.put(v2.getId(), v2);
        } else {
            v2real = vertexMap.get(v2.getId());
            if (v2.getValue() != null && !v2real.getValue().equals(v2.getValue())) {
                throw new RuntimeException("Unable to link existing vertex id = " + v2.getId() + " with new Value = " + v2.getValue());
            }
        }

        // New egdes must be initialized with "neutralElement"
        Edge<V, E>  newEdge = new Edge<>(v1real, v2real, edgeNeutralElement);
        // TODO: Simplify with computeIfPresent()
        if (edgeMap.containsKey(newEdge)) {
            // Existing edges must upgade value according to () -> refreshEdgeFunction
            Edge<V, E> realEdge = edgeMap.get(newEdge);
            realEdge.setValue(refreshEdgeFunction.apply(realEdge.getValue()));
            return realEdge;
        } else {
            // New egdes must be added into vertex lists
            v1real.addOutgoingEdge(v2real);
            v2real.addIncomingEdge(v1real);
            edgeMap.put(newEdge, newEdge);
            return newEdge;
        }
    }

    @NotNull
    public Map<Edge<V, E> , Edge<V, E> > safeGetEdgeWeigthMap() {
        return unmodifiableMap(edgeMap);
    }

    @NotNull
    public Map<Integer, Vertex<V, E>> safeGetVertexMap() {
        return unmodifiableMap(vertexMap);
    }

    @Override
    public String toString() {
        return format("G(|V|=%d,|E|=%d)", vertexMap.size(), edgeMap.size());
    }

    @NotNull
    public Map<Edge<V, E>, Edge<V, E>> getEdgeMap() {
        return edgeMap;
    }

    @NotNull
    public Map<Integer, Vertex<V, E>> getVertexMap() {
        return vertexMap;
    }

    public void setRefreshEdgeFunction(Function<E, E> refreshEdgeFunction) {
        this.refreshEdgeFunction = refreshEdgeFunction;
    }

    public void setJoinVertexFunction(BiFunction<V, V, V> joinVertexFunction) {
        this.joinVertexFunction = joinVertexFunction;
    }

    public void setEdgeNeutralElement(E edgeNeutralElement) {
        this.edgeNeutralElement = edgeNeutralElement;
    }
}
