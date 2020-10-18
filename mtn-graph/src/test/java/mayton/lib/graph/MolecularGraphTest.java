package mayton.lib.graph;

import org.junit.jupiter.api.Test;

public class MolecularGraphTest {

    class Point{
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    @Test
    public void test() {
        Graph<Point, Void> molecularGraph = new Graph<>();
        molecularGraph.setJoinVertexFunction((p1,p2) -> {
            return new Point((p1.x + p2.x) / 2.0, (p1.y + p2.y) / 2.0);
        });
    }

}
