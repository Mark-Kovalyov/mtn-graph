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
import java.util.Properties;
import java.util.stream.Collectors;

public class VerticesSerializerCSV<V,E> extends BinaryGraphSerializer<V,E> {

    static Logger logger = LoggerFactory.getLogger(VerticesSerializerCSV.class);

    @Override
    public void serialize(@NotNull Graph<V,E> graph, @NotNull OutputStream outputStream, @NotNull Properties properties) throws IOException {
        OutputStreamWriter osv = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        PrintWriter pw = new PrintWriter(osv);
        for(Vertex<V,E> value : graph.safeGetVertexMap().values()) {
            pw.print(value.getId());
            pw.print(";");
            pw.print(value.getIncomingEdges().stream()
                    .map(e -> e.getV1().getId())
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")));
            pw.print(";");
            pw.print(value.getOutgoingEdges().stream()
                    .map(e -> e.getV2().getId())
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")));
            pw.println();
        }
        pw.close();
    }

}
