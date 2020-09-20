package mayton.lib.graph;

import mayton.lib.graph.Graph;
import mayton.lib.graph.Edge;
import mayton.lib.graph.Vertex;
import mayton.lib.graph.GraphSerializer;
import mayton.lib.graph.GraphProcessor;
import mayton.lib.graph.GraphAlgorithm;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class GraphTestAdjucentControl {

    //
    //  (1)         (4)
    //    \         /
    //    (2) -- (3)-- (5)
    //    /        \
    //  (7)        (6)
    @Test
    public void test() {
        Graph<String, Integer> graph = new Graph<>();
        graph.setEdgeNeutralElement(0);
        graph.setJoinVertexFunction((String s1,String s2) -> s1 + " " + s2);
        graph.setRefreshEdgeFunction((Integer e1) -> e1 + 1);

        graph.linkEdge(1,2);
        graph.linkEdge(2,3);
        graph.linkEdge(3,4);
        graph.linkEdge(3,4);
        graph.linkEdge(3,5);
        graph.linkEdge(3,6);
        graph.linkEdge(7,2);

        assertNotNull(graph.findVertexById(1));
        assertNotNull(graph.findVertexById(2));
        assertNotNull(graph.findVertexById(3));
        assertNotNull(graph.findVertexById(4));
        assertNotNull(graph.findVertexById(5));
        assertNotNull(graph.findVertexById(6));
        assertNotNull(graph.findVertexById(7));

        List<Edge<String,Integer>> incomingEdgesOptional = graph.extractIncomingEdges(2);

        assertEquals(2, incomingEdgesOptional.size());

        List<Edge<String,Integer>> outgoingEdgesOptional = graph.extractOutgoingEdges(3);

        assertEquals(3, outgoingEdgesOptional.size());
    }

}

