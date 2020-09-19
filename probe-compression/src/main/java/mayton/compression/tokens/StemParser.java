package mayton.compression.tokens;

import mayton.lib.graph.Graph;
import mayton.lib.graph.Edge;
import mayton.lib.graph.Vertex;
import mayton.lib.graph.GraphSerializer;
import mayton.lib.graph.GraphProcessor;
import mayton.lib.graph.GraphAlgorithm;

import org.apache.lucene.analysis.ru.RussianLightStemFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;

import org.jetbrains.annotations.NotNull;

import org.tartarus.snowball.ext.RussianStemmer;

public class StemParser<V,E> extends TokenProcessor<V,E> {

    private static RussianLightStemFilter russianLightStemFilter = null;
    private static RussianStemmer russianStemmer = new RussianStemmer();
    private static SnowballFilter snowballFilter = null;

    static {
        /*TokenStream tokenStream = new RussianLightStemFilter(new To);
        snowballFilter = new SnowballFilter(tokenStream,"");*/
    }

    @Override
    public void processGraphNode(@NotNull String token, @NotNull Graph<V,E> graph) {
        if (prevToken != null) {
            logger.debug(":: link {} -> {}", prevToken, token);
            // TODO
            //graph.linkEdge(prevToken, token);
        }
        prevToken = token;
    }
}
