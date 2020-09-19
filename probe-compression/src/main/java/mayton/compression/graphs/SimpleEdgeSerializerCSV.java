package mayton.compression.graphs;

import mayton.lib.graph.Graph;
import mayton.lib.graph.Edge;
import mayton.lib.graph.Vertex;
import mayton.lib.graph.GraphSerializer;
import mayton.lib.graph.GraphProcessor;
import mayton.lib.graph.GraphAlgorithm;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

public class SimpleEdgeSerializerCSV<V,E> extends BinaryGraphSerializer<V,E> {

    static Logger logger = LoggerFactory.getLogger(SimpleEdgeSerializerCSV.class);

    @Override
    public void serialize(@NotNull Graph<V,E> graph, @NotNull OutputStream outputStream, @NotNull Properties properties) throws IOException {
        OutputStreamWriter osv = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        PrintWriter pw = new PrintWriter(osv);
        Map<String, Integer> map = createVertexIdToNumber(graph);
        for(Edge<V,E> edge : graph.safeGetEdgeWeigthMap().keySet()) {
            pw.print(map.get(edge.getV1().getId()));
            pw.print(",");
            pw.println(map.get(edge.getV2().getId()));
        }
        pw.close();
    }
}
