package mayton.compression.simplify;

import mayton.lib.graph.*;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

// mayton.compression.simplify.SimplifyGraphTest

public class SimplifyGraph<V,E> implements GraphAlgorithm<V,E> {

    static XLogger xLogger = XLoggerFactory.getXLogger(SimplifyGraph.class);

    static Logger logger = LoggerFactory.getLogger(SimplifyGraph.class);

    @Override
    public @NotNull Graph<V,E> process(@NotNull Graph<V,E> graph, @NotNull Properties parameters) {
        xLogger.entry(parameters);
        Iterator<Map.Entry<Edge<V,E>, Edge<V,E>>> iterator = graph.safeGetEdgeWeigthMap().entrySet().iterator();
        while(iterator.hasNext()) {
            iterator.remove();
        }
        return graph;
    }
}
