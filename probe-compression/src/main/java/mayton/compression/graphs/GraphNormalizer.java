package mayton.compression.graphs;

import mayton.lib.graph.GraphConverter;
import mayton.lib.graph.Graph;
import mayton.lib.graph.Vertex;
import mayton.lib.graph.Edge;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class GraphNormalizer<V,E> implements GraphConverter<V,E> {

    public Map<String, Integer> createVertexIdToNumber(@NotNull Graph<V,E> graph) {
        Map<String, Integer> map = new HashMap<>();
        int cnt = 0;
        // TODO
        /*for(Vertex<V,E> v : graph.safeGetVertexMap()
                .values()
                .stream()
                .sorted((v1,v2) -> v1.getId().compareTo(v2.getId()))
                .collect(Collectors.toList())) {

            map.put(v.getId(), cnt++);
        }*/
        return map;
    }



    @Override
    public mayton.lib.graph.@NotNull Graph<V, E> convert(mayton.lib.graph.@NotNull Graph<V, E> graph, @NotNull Properties properties) {
        Graph graphNew = new Graph(graph.safeGetVertexMap().size(), graph.safeGetEdgeWeigthMap().size());
        Map<String, Integer> map = createVertexIdToNumber(graph);
        for(Edge<V,E> edge : graph.safeGetEdgeWeigthMap().values()) {
            // TODO
            /*graphNew.linkEdge(
                    String.valueOf(map.get(edge.getV1().getId())),
                    String.valueOf(map.get(edge.getV2().getId())));*/
        }
        return graphNew;
    }

}
