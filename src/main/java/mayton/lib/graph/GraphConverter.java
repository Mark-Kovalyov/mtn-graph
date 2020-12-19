package mayton.lib.graph;

import org.jetbrains.annotations.NotNull;

import java.util.Properties;

public interface GraphConverter<V,E> {

    @NotNull Graph<V,E> convert(@NotNull Graph<V,E> graph, @NotNull Properties properties);

    @NotNull default Graph<V,E> convert(@NotNull Graph<V,E> graph) {
        return convert(graph, new Properties());
    }

}
