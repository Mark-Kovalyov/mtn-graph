package mayton.compression.graphs;

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

public class VerticesSerializerCSV extends BinaryGraphSerializer {

    static Logger logger = LoggerFactory.getLogger(VerticesSerializerCSV.class);

    @Override
    public void serialize(@NotNull Graph graph, @NotNull OutputStream outputStream, @NotNull Properties properties) throws IOException {
        OutputStreamWriter osv = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        PrintWriter pw = new PrintWriter(osv);
        for(Vertex value : graph.safeGetVertexMap().values()) {
            pw.print(value.getId());
            pw.print(";");
            pw.print(value.getIncomingEdges().stream()
                    .map(e -> e.getV1().getId())
                    .collect(Collectors.joining(",")));
            pw.print(";");
            pw.print(value.getOutgoingEdges().stream()
                    .map(e -> e.getV2().getId())
                    .collect(Collectors.joining(",")));
            pw.println();
        }
        pw.close();
    }

}
