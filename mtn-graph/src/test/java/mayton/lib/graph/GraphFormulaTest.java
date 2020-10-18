package mayton.lib.graph;

import net.jqwik.api.*;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Сумма степеней вершин равна удвоенному числу ребер
 *
 */
@PropertyDefaults(tries = 300)
public class GraphFormulaTest {

    private Random random = new Random();

    @Property
    boolean graphFormulaIsCorrect(@ForAll("RandomGraphs") Graph<Void,Void> graph) {

        int sumVertexPowers = graph.getVertexMap().entrySet().stream()
                .map(Map.Entry::getValue)
                .map(vertex -> vertex.getIncomingEdges().size() + vertex.getOutgoingEdges().size())
                .collect(Collectors.summingInt(Integer::intValue));

        int edgesCount = graph.getEdgeMap().size();

        return sumVertexPowers == 2 * edgesCount;
    }

    @Provide("RandomGraphs")
    Arbitrary<Graph<Void,Void>> randomGraph() {
        return Arbitraries.create(() -> randomGraphSupplier(20));
    }

    private Graph<Void, Void> randomGraphSupplier(int parameter) {
        Graph graph = new Graph<>();
        int n = random.nextInt(parameter);
        for (int i = 0; i < (n * n / 2); i++) {
            graph.linkEdge(random.nextInt(20), random.nextInt(20));
        }
        return graph;
    }


}
