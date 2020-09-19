package mayton.compression.character;

import mayton.lib.graph.GraphProcessor;
import mayton.lib.graph.Graph;
import mayton.compression.languagespec.ru.RuUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;

public class CharacterProcessor<V,E> implements GraphProcessor<V,E> {

    void linkChars(Graph<V,E> graph, int v1, int v2) {
        //TODO:
        //graph.linkEdge("" + Character.toLowerCase((char) v1), "" + Character.toLowerCase((char) v2));
    }

    @Override
    public @NotNull Graph<V, E> upgrade(@NotNull Reader reader, @NotNull Graph<V, E> graph) throws IOException {
        int c = 0;
        int cp = -1;
        while ((c = reader.read()) >= 0) {
            if (cp != -1) {
                if (RuUtils.isCyrillic(c)) {
                    linkChars(graph, cp, c);
                } else {
                    c = (int)('$');
                    if (cp != '$') {
                        linkChars(graph, cp, c);
                    }
                }
                cp = c;
            }
        }
        return graph;
    }


}