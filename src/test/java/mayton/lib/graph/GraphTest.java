package mayton.lib.graph;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Main Graph test")
class GraphTest {

    @Test
    void testHashMapContractByStringKeys() {
        Graph<Void,Void> graph = new Graph();

        for (int i = 0; i < 100; i++) {
            graph.linkEdge(i,  i + 1);
        }

        assertEquals(101, graph.safeGetVertexMap().size(),"Assume after 100 linking edges chain must be 101 vertex");

        assertEquals(100, graph.safeGetEdgeWeigthMap().size(),"Assume after 100 linking edges chain must contains 100 edges");

        for (int i = 0; i < 100; i++) {
            assertTrue(graph.containsDirectedEdgeByIds(i, i + 1),"Graph must contans every edge");
        }

        for (int i = 0; i < 100; i++) {
            assertTrue(graph.containsVertexWithId(i),"Graph must contans every edge");
            assertTrue(graph.containsVertexWithId(i + 1), "Graph must contans every edge");
        }
    }

    @Test
    void testHashMapContractByObjectKeys() {

        Graph<Void,Void> graph = new Graph<>(50_000, 260_000);

        for (int i = 0; i < 100; i++) {
            graph.linkEdge(new Vertex(i), new Vertex(i + 1));
        }

        assertEquals(101, graph.safeGetVertexMap().size());
        assertEquals(100, graph.safeGetEdgeWeigthMap().size());

        for (int i = 0; i < 100; i++) {
            assertTrue(graph.containsDirectedEdge(
                    new Vertex(i),
                    new Vertex(i + 1)));
        }

        for (int i = 0; i < 100; i++) {
            assertTrue(graph.containsVertexWithId(i));
            assertTrue(graph.containsVertexWithId(i + 1));
        }
    }

    @Test
    void testGraphContainsEdges() {
        Graph<Void,Integer> graph = new Graph();
        graph.setRefreshEdgeFunction((a) -> a + 1);
        graph.setEdgeNeutralElement(1);

        graph.addVertex(1);
        graph.addVertex(2);
        graph.linkEdge(1,2);

        assertFalse(graph.containsDirectedEdgeByIds(2,1));
        assertTrue(graph.containsDirectedEdgeByIds(1,2));
        assertTrue(graph.containsDirectedEdge(new Vertex(1), new Vertex(2)));

        Edge<Void,Integer> edge = graph.extractEdgeByIds(1,2);
        assertNotNull(edge);

        assertEquals(1, (int) edge.getEdgeValue());
        graph.linkEdge(1, 2);
        assertEquals(2, (int) edge.getEdgeValue());
        graph.linkEdge(1, 2);
        assertEquals(3, (int) edge.getEdgeValue());
        graph.linkEdge(1, 2);
        assertEquals(4, (int) edge.getEdgeValue());
    }

    @Test
    void testGraphOutgoingEdges() {
        Graph<Void, Integer> graph = new Graph();
        graph.setRefreshEdgeFunction((a) -> a + 1);
        graph.setEdgeNeutralElement(1);

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        //     (1)
        //    /  \
        //  (2)  (3)
        //
        assertEquals(0, graph.extractOutgoingEdges(1).size());
        assertEquals(0, graph.extractOutgoingEdges(2).size());
        assertEquals(0, graph.extractOutgoingEdges(3).size());
        graph.linkEdge(1, 2);
        assertEquals(1, graph.extractOutgoingEdges(1).size());
        assertEquals(0, graph.extractOutgoingEdges(2).size());
        assertEquals(0, graph.extractOutgoingEdges(3).size());
        graph.linkEdge(1, 3);
        assertEquals(2, graph.extractOutgoingEdges(1).size());
        assertEquals(0, graph.extractOutgoingEdges(2).size());
        assertEquals(0, graph.extractOutgoingEdges(3).size());
        graph.linkEdge(1, 3); // Double link should not produce any outgoing edges
        assertEquals(2, graph.extractOutgoingEdges(1).size());
        assertEquals(0, graph.extractOutgoingEdges(2).size());
        assertEquals(0, graph.extractOutgoingEdges(3).size());
    }

    @Test
    void testVertexJoin() {
        Graph<String, Integer> graph = new Graph();

        graph.setRefreshEdgeFunction((a) -> a + 1);
        graph.setJoinVertexFunction((a,b) -> a + " " + b);

        graph.setEdgeNeutralElement(1);

        graph.linkEdge(new Vertex<>(1, "безухов"), new Vertex<>(2, "бухал"));
        graph.linkEdge(new Vertex<>(2), new Vertex<>(3, "c"));
        graph.linkEdge(new Vertex<>(3), new Vertex<>(4, "болконским"));
        graph.linkEdge(new Vertex<>(3), new Vertex<>(4));

        Edge<String, Integer> theSameEdge = graph.extractEdgeByIds(3,4);
        assertNotNull(theSameEdge);
        assertEquals(3, theSameEdge.getV1().getId());
        assertEquals(4, theSameEdge.getV2().getId());
        assertEquals(2, (int) theSameEdge.getEdgeValue());
    }

}
