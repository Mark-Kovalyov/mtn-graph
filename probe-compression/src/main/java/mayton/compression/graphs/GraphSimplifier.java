package mayton.compression.graphs;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class GraphSimplifier implements GraphConverter {

    @Override
    public @NotNull Graph convert(@NotNull Graph graph, @NotNull Properties properties) {
        Graph newGraph = new Graph();

        Iterator<Map.Entry<Edge, Edge>> iterator = graph.getEdgeWeigthMap().entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<Edge, Edge> item = iterator.next();
            Edge edge = item.getKey();
            Set<Vertex> vin = edge.getV1().getIncomingEdges().stream().map(e -> e.getV2()).collect(Collectors.toSet());
            Set<Vertex> vout = edge.getV1().getIncomingEdges().stream().map(e -> e.getV2()).collect(Collectors.toSet());
            boolean condition = false;
            if (condition) {
                // Create new Vertex with new ID = concat(id1,id2)
                // Map outgoing edges
                // Map incoming edges
                // Remove V1, V2
                iterator.remove();
            }
        }
        return newGraph;
    }

}
