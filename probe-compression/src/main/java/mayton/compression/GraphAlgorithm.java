package mayton.compression;

import mayton.compression.graphs.Graph;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

public interface GraphAlgorithm {

    @NotNull
    Graph process(@NotNull Graph graph, @NotNull Properties parameters);

    @NotNull
    default Graph process(@NotNull Graph graph) {
        return process(graph, new Properties());
    }

}
