package mayton.compression.graphs;

import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class GraphAdjacencyListTrieSerializerCSV extends BinaryGraphSerializer {

    static Comparator<Triple<Integer, Integer, Integer>> compareByLeft = Comparator.comparing(Triple::getLeft);

    static Comparator<Triple<Integer, Integer, Integer>> compareByLeftAndMiddle = compareByLeft.thenComparing(Triple::getMiddle);

    static final String OPEN_CONTEXT = "(";
    static final String CLOSE_CONTEXT = ")";
    static final String SPLITTER = ";";
    static final String EOL = "";

    @Override
    public void serialize(@NotNull Graph graph, @NotNull OutputStream outputStream, @NotNull Properties properties) throws IOException {
        OutputStreamWriter osv = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        PrintWriter pw = new PrintWriter(osv);
        Map<String, Integer> map = createVertexIdToNumber(graph);
        Triple<Integer,Integer,Integer> triplePrev = null;
        for(Triple<Integer,Integer,Integer> triple : graph.safeGetEdgeWeigthMap()
                .values()
                .stream().map(
                        edge -> Triple.of(
                            map.get(edge.getV1().getId()),
                            map.get(edge.getV2().getId()),
                            edge.getWeight())
                ).sorted(compareByLeftAndMiddle)
                .collect(Collectors.toList())
        ) {
            if (triplePrev != null) {
                if (triplePrev.getLeft().equals(triple.getLeft())) {
                    pw.printf("%d" + SPLITTER + "%d" + SPLITTER + EOL,
                            triple.getMiddle(),
                            triple.getRight());
                } else {
                    pw.printf(CLOSE_CONTEXT + EOL);
                    pw.printf("%d" + SPLITTER + OPEN_CONTEXT + "%d" + SPLITTER +"%d" + SPLITTER + EOL,
                            triple.getLeft(),
                            triple.getMiddle(),
                            triple.getRight());
                }
            } else {
                pw.printf("%d" + SPLITTER + OPEN_CONTEXT + "%d" + SPLITTER + "%d" + SPLITTER + EOL,
                        triple.getLeft(),
                        triple.getMiddle(),
                        triple.getRight());
            }
            triplePrev = triple;
        }
        pw.printf(CLOSE_CONTEXT);

        osv.close();
    }
}
