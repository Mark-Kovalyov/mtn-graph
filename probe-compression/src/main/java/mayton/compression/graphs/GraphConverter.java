package mayton.compression.graphs;

import org.jetbrains.annotations.NotNull;

import java.util.Properties;

public interface GraphConverter {

    @NotNull Graph convert(@NotNull Graph graph,@NotNull Properties properties);

    @NotNull default Graph convert(@NotNull Graph graph) {
        return convert(graph, new Properties());
    }

}
