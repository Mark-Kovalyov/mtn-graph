package mayton.lib.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VertexTest {

    @Test
    void test_add_delete_vertex() {
        Graph<String,String> graph = new Graph<>();
        graph.addVertex(1,"bojack");
        Vertex<String,String> v2 = graph.addVertex(2,"todd");
        assertTrue(graph.deleteVertex(1));
        assertFalse(graph.deleteVertex(3));
        assertEquals(v2, graph.addVertex(2));
    }

}
