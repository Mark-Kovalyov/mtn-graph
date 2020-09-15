package mayton.compression;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MapIteratorTest {

    @Test
    public void testRemovingFromIterator() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.put("d", 4);

        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> value = iterator.next();
            if (value.getValue() % 2 == 0) {
                iterator.remove();
            }
        }

        assertTrue(map.containsKey("a"));
        assertTrue(map.containsKey("c"));

        assertFalse(map.containsKey("b"));
        assertFalse(map.containsKey("d"));

    }

}
