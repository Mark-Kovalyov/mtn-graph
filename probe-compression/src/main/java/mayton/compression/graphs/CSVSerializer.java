package mayton.compression.graphs;

import mayton.lib.graph.Graph;
import mayton.lib.graph.Edge;
import mayton.lib.graph.GraphSerializer;
import mayton.lib.graph.Vertex;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class CSVSerializer<V,E> implements GraphSerializer<V,E> {

    static Logger logger = LoggerFactory.getLogger(CSVSerializer.class);

    @Override
    public void serialize(@NotNull Graph<V,E> graph, @NotNull OutputStream outputStream, @NotNull Properties properties) throws IOException {
        logger.info("START Serialize with CSV");
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(outputStream), CSVFormat.ORACLE);
        int cnt = 0;
        csvPrinter.printComment("# Dictionary");
        Map<String, String> id2hexnum = new HashMap<>();

        // TODO
        /*for(Vertex<String, Integer> v : graph.safeGetVertexMap()
                .values()
                .stream()
                .sorted((v1,v2) -> v1.getId().compareTo(v2.getId()))
                .collect(Collectors.toList())) {
            String hex = format("%X", cnt);
            id2hexnum.put(v.getId(), hex);
            csvPrinter.printRecord(hex, v.getId());
            cnt++;
        }*/

        csvPrinter.printComment("# Graph");
        for(Map.Entry<Integer,Vertex<V,E>> e : graph.safeGetVertexMap().entrySet()) {
            StringJoiner sj = new StringJoiner("$");
            for(Edge edgeEntry : e.getValue().getOutgoingEdges()) {
                sj.add(id2hexnum.get(edgeEntry.getV2().getId()) + ":" + edgeEntry.getValue());
            }
            csvPrinter.printRecord(id2hexnum.get(e.getKey()), sj.toString());
        }
        csvPrinter.close();
        logger.info("FINISH Serialize with CSV");
    }
}
