package mayton.compression.graphs;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class GraphNormalizer implements GraphConverter {

    public Map<String, Integer> createVertexIdToNumber(@NotNull Graph graph) {
        Map<String, Integer> map = new HashMap<>();
        int cnt = 0;
        for(Vertex v : graph.safeGetVertexMap()
                .values()
                .stream()
                .sorted((v1,v2) -> v1.getId().compareTo(v2.getId()))
                .collect(Collectors.toList())) {

            map.put(v.getId(), cnt++);
        }
        return map;
    }

    @Override
    public @NotNull Graph convert(@NotNull Graph graph, @NotNull Properties properties) {
        Graph graphNew = new Graph(graph.safeGetVertexMap().size(), graph.safeGetEdgeWeigthMap().size());
        Map<String, Integer> map = createVertexIdToNumber(graph);
        for(Edge edge : graph.safeGetEdgeWeigthMap().values()) {
            graphNew.linkEdge(
                    String.valueOf(map.get(edge.getV1().getId())),
                    String.valueOf(map.get(edge.getV2().getId())));
        }
        return graphNew;
    }

}
