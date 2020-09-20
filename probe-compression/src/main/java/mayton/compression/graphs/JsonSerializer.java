package mayton.compression.graphs;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import mayton.lib.graph.Graph;
import mayton.lib.graph.Vertex;
import mayton.lib.graph.Edge;
import mayton.lib.graph.GraphSerializer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class JsonSerializer<V,E> implements GraphSerializer<V,E> {

    static Logger logger = LoggerFactory.getLogger(JsonSerializer.class);




    @Override
    public void serialize(@NotNull Graph<V, E> graph, @NotNull OutputStream outputStream, @NotNull Properties properties) throws IOException {
        JsonFactory jfactory = new JsonFactory();
        try(JsonGenerator jGenerator = jfactory.createGenerator(outputStream, JsonEncoding.UTF8)) {
            jGenerator.writeStartObject();
            jGenerator.writeObjectField("vertices", graph.safeGetVertexMap().size());
            jGenerator.writeObjectField("edges", graph.safeGetEdgeWeigthMap().size());
            jGenerator.writeArrayFieldStart("data");

            for (Map.Entry<Integer,Vertex<V,E>> e : graph.safeGetVertexMap().entrySet()) {
                jGenerator.writeStartObject();
                jGenerator.writeStringField("name", String.valueOf(e.getKey()));
                jGenerator.writeNumberField("outgoing-edges-count", e.getValue().getOutgoingEdges().size());
                jGenerator.writeArrayFieldStart("outgoing-edges");
                for (Edge edgeEntry : e.getValue().getOutgoingEdges()) {
                    jGenerator.writeString(edgeEntry.getV2().getId() + ":" + (Integer) edgeEntry.getEdgeValue());
                }
                jGenerator.writeEndArray();
                jGenerator.writeEndObject();
            }

            jGenerator.writeEndArray();
            jGenerator.writeEndObject();
        }
    }
}
