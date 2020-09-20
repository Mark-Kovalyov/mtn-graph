package mayton.lib.graph;

import org.junit.Test;

public class VertexTest {

    @Test
    public void test() {
        Graph<String,String> graph = new Graph<>();
        graph.addVertex(1,"bojack");
        graph.addVertex(2,"todd");

    }

}
