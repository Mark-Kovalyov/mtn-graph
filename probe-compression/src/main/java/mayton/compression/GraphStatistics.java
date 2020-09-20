package mayton.compression;

import com.google.common.math.Quantiles;
import com.google.common.math.Stats;
import mayton.lib.graph.Graph;
import mayton.lib.graph.GraphProcessor;
import mayton.lib.graph.Graph;
import mayton.lib.graph.Edge;
import mayton.lib.graph.Vertex;
import mayton.lib.graph.GraphSerializer;
import mayton.lib.graph.GraphProcessor;
import mayton.lib.graph.GraphAlgorithm;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

public class GraphStatistics<V,E> {

    static Logger logger = LoggerFactory.getLogger(GraphStatistics.class);

    private transient LinkedHashMap<String, Object> statistics;

    private double fd(double v) {
        return v;
    }

    @NotNull
    public LinkedHashMap calculateStatistics(Graph<V,E> graph) {

        statistics = new LinkedHashMap<>();

        statistics.put("Vertices", valueOf(graph.getVertexMap().size()));
        statistics.put("Edges",    valueOf(graph.getEdgeMap().size()));

        List<Integer> weights = graph.getEdgeMap().entrySet()
                .stream()
                .map(x -> (Integer) x.getValue().getEdgeValue())
                .collect(Collectors.toList());

        if (!weights.isEmpty()) {

            Stats stats = Stats.of(weights);
            Lhm weightStatsMap = new Lhm();
            weightStatsMap.put("Max edge weight", fd(stats.max()));
            weightStatsMap.put("AVG edge weight", fd(stats.mean()));
            weightStatsMap.put("Median edge weight", fd(Quantiles.median().compute(weights)));

            Quantiles.percentiles().indexes(75, 80, 85, 90, 95, 97)
                    .compute(weights).entrySet()
                    .stream()
                    .forEach(item ->
                            weightStatsMap.put("" + item.getKey() + "-th percentille edge weight", item.getValue()));

            statistics.put("weightStats", weightStatsMap);

        } else {
            logger.warn("Unable to calculate weight statistics!");
        }

        List<Integer> joins = graph.getVertexMap().entrySet().stream()
                .map(entry -> entry.getValue())
                .map(vertex -> vertex.getOutgoingEdges().size() + vertex.getIncomingEdges().size())
                .collect(Collectors.toList());

        Stats joinsStats = Stats.of(joins);

        Lhm joinsStatsMap = new Lhm();
        joinsStatsMap.put("Max joins",               fd(joinsStats.max()));
        joinsStatsMap.put("AVG joins",               fd(joinsStats.mean()));
        joinsStatsMap.put("Median joins",            fd(Quantiles.median().compute(joins)));

        Quantiles.percentiles().indexes(75, 80, 85, 90, 95, 97)
                .compute(joins).entrySet()
                .stream()
                .forEach(item ->
                        joinsStatsMap.put("" + item.getKey() + "-th percentille joins", item.getValue()));

        statistics.put("joinsStats", joinsStatsMap);

        List<Integer> nameLength = graph.getVertexMap().keySet()
                .stream()
                .collect(Collectors.toList());

        Stats nameLengthStats = Stats.of(nameLength);

        Lhm nameStatsMap = new Lhm();
        nameStatsMap.put("Max length",               fd(nameLengthStats.max()));
        nameStatsMap.put("AVG length",               fd(nameLengthStats.mean()));
        nameStatsMap.put("Median length",            fd(Quantiles.median().compute(nameLength)));

        Quantiles.percentiles().indexes(75, 80, 85, 90, 95, 97)
                .compute(nameLength).entrySet()
                .stream()
                .forEach(item ->
                        nameStatsMap.put("" + item.getKey() + "-th percentille name length", item.getValue()));

        statistics.put("nameStats", nameStatsMap);

        return statistics;
    }

}
