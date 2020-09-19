package mayton.compression.graphs;

import mayton.lib.graph.GraphSerializer;
import mayton.lib.graph.Graph;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class BinaryGraphSerializer<V,E> implements GraphSerializer<V,E> {

    public Map<String, Integer> createVertexIdToNumber(@NotNull Graph<V,E> graph) {
        Map<String, Integer> map = new HashMap<>();
        int cnt = 0;
        /*for(Vertex v : graph.safeGetVertexMap()
                .values()
                .stream()
                .sorted((v1,v2) -> v1.getId().compareTo(v2.getId()))
                .collect(Collectors.toList())) {

            map.put(v.getId(), cnt++);
        }*/
        return map;
    }


    @Override
    public void serialize(mayton.lib.graph.@NotNull Graph<V, E> graph, @NotNull OutputStream outputStream, @NotNull Properties properties) throws IOException {
        throw new RuntimeException("Not implemented yet!");
    }
}
