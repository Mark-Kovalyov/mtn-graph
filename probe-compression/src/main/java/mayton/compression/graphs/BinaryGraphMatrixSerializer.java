package mayton.compression.graphs;

import mayton.lib.graph.Graph;
import mayton.lib.graph.Edge;
import mayton.lib.graph.Vertex;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

public class BinaryGraphMatrixSerializer<V,E> extends BinaryGraphSerializer<V,E> {

    static Logger logger = LoggerFactory.getLogger(BinaryGraphMatrixSerializer.class);

    int calcOffset(int row, int col, int width) {
        return row * width + col;
    }

    @Override
    public void serialize(@NotNull Graph<V,E> graph, @NotNull OutputStream outputStream, @NotNull Properties properties) throws IOException {
        logger.info("START");
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        int width = graph.safeGetVertexMap().size();
        Map<String, Integer> map = createVertexIdToNumber(graph);
        int size = graph.safeGetVertexMap().size();
        logger.info("vertexIdToNumber size = {}", size);
        int PARTS = 4;
        int partSize = size / PARTS;
        logger.info("partSize = {}", partSize);
        for(int k = 0 ; k < PARTS ; k++) {
            int minRow = k * partSize;
            int maxRow = (k + 1) * partSize;
            logger.info("process partition # {}, with range [{}..{})", k, minRow, maxRow);
            byte[] matrixPartition = new byte[size * 2 * partSize];
            for(Map.Entry<Edge<V,E>, Edge<V,E>> edgeEntry : graph.safeGetEdgeWeigthMap().entrySet()) {
                Edge edge = edgeEntry.getKey();
                int row = map.get(edge.getV1().getId());
                int col = map.get(edge.getV2().getId());
                if (row >= minRow && row < maxRow) {
                    int weight = (int) edgeEntry.getKey().getEdgeValue();
                    int offset = calcOffset(row - minRow, 2 * col, width);
                    matrixPartition[offset]     = (byte) (weight >>> 8);
                    matrixPartition[offset + 1] = (byte) (weight & 0xFF);
                }
            }
            dataOutputStream.write(matrixPartition);
        }
        dataOutputStream.close();
        logger.info("FINISH binary graph serialization");
    }

}
