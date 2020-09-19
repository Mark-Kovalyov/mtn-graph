package mayton.compression.graphs;

import mayton.lib.graph.Graph;
import mayton.lib.graph.Edge;
import mayton.lib.graph.Vertex;
import mayton.lib.graph.GraphSerializer;
import mayton.lib.graph.GraphProcessor;
import mayton.lib.graph.GraphAlgorithm;
import mayton.compression.encoders.varint.VLQOutputStream;
import mayton.lib.graph.Graph;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

public class BinaryGraphVLQSerializer<V,E> extends BinaryGraphSerializer<V,E> {

    @Override
    public void serialize(@NotNull Graph<V, E> graph, @NotNull OutputStream outputStream) throws IOException {
        //OutputStreamWriter osv = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        //PrintWriter pw = new PrintWriter(osv);
        Map<String, Integer> map = createVertexIdToNumber(graph);
        VLQOutputStream vlqOutputStream = new VLQOutputStream(outputStream);
        for(Map.Entry<Edge<V,E>, Edge<V,E>> edgeEntry : graph.safeGetEdgeWeigthMap().entrySet()) {
            Edge edge = edgeEntry.getKey();
            vlqOutputStream.writeLong(map.get(edge.getV1().getId()));
            vlqOutputStream.writeLong(map.get(edge.getV2().getId()));
            vlqOutputStream.writeLong((int) edge.getValue());
            /*pw.printf("%d;%d;%d\n",
            map.get(edge.getV1().getId()),
                    map.get(edge.getV2().getId()),
                    edge.getWeight());*/
        }
        vlqOutputStream.close();
        //osv.close();
    }



}
