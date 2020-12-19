package mayton.lib.graph;

import org.jetbrains.annotations.NotNull;

public interface GraphMutator<V,E> {

    boolean mutate(@NotNull Graph<V,E> graph);

}
