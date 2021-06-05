package mayton.lib.graph;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EdgeTest {

    @Test
    void test_link_edge() {
        Graph<String,String> graph = new Graph<>();
        Edge<String, String> edge1 = graph.linkEdge(1,2);
        assertNotNull(edge1);
        edge1.setEdgeValue("Label1");
        assertEquals("Label1", edge1.getEdgeValue());
        assertNotNull(edge1.getV1());
        assertNotNull(edge1.getV2());
    }

}
