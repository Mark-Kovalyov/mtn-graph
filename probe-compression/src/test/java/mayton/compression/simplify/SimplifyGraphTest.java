package mayton.compression.simplify;

import mayton.compression.graphs.Graph;
import org.jetbrains.annotations.NotNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimplifyGraphTest {

    @Test
    public void test_01() {
        SimplifyGraph simplifyGraph = new SimplifyGraph();
        Graph result = simplifyGraph.process(new Graph());
        assertTrue(result.safeGetVertexMap().containsKey("1"));
    }

}
