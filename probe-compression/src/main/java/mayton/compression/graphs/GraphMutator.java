package mayton.compression.graphs;

import org.jetbrains.annotations.NotNull;

public interface GraphMutator {

    boolean mutate(@NotNull Graph graph);

}
