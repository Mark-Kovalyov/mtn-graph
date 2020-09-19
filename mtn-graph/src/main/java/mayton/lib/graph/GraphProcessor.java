package mayton.lib.graph;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;

public interface GraphProcessor<V,E> {

    @NotNull
    default Graph<V,E> process(@NotNull Reader reader) throws IOException {
        return upgrade(reader, new Graph<V,E>());
    }

    @NotNull
    Graph<V,E> upgrade(@NotNull Reader reader, @NotNull Graph<V,E> graph) throws IOException;

}
