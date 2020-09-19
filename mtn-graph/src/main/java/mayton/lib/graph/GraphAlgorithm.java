package mayton.lib.graph;

import org.jetbrains.annotations.NotNull;

import java.util.Properties;

public interface GraphAlgorithm<V,E> {

    @NotNull
    Graph<V,E> process(@NotNull Graph<V,E> graph, @NotNull Properties parameters);

    @NotNull
    default Graph<V,E> process(@NotNull Graph<V,E> graph) {
        return process(graph, new Properties());
    }

}
