package mayton.compression;

import mayton.compression.graphs.*;
import mayton.compression.tokens.TokenSentenceProcessor;
import mayton.compression.trie.DictionaryCompactTrie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.profiler.Profiler;

import java.io.*;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        String charset = "windows-1251";

        logger.info("START!");

        Properties properties = new Properties();
        properties.put("limit",     Integer.valueOf(args[0]));
        properties.put("selection", args[1]);

        Graph graph = new Graph(50_000, 260_000);

        Profiler profiler = new Profiler("Graph profiler");

        GraphProcessor graphProcessor = new TokenSentenceProcessor();

        profiler.start("Process tokens from text");

        try (Reader reader = new FileReader("text/war-and-society-1-2-3-4.utf-8.txt", UTF_8)) {
            graph = graphProcessor.upgrade(reader, graph);
        }

        profiler.start("Transform into dictionary trie");

        try (Reader reader = new FileReader("text/war-and-society-1-2-3-4.utf-8.txt", UTF_8)) {
            new DictionaryCompactTrie().transform(reader, new OutputStreamWriter(new FileOutputStream("trie/war-and-society-1-2-3-4.dict.trie")));
        }

        /*try (Reader reader = new FileReader("text/war-and-society-1-2-3-4.utf-8.txt", UTF_8)) {
            new DictionaryExpanedTrie().transform(reader, new OutputStreamWriter(new FileOutputStream("trie/war-and-society-1-2-3-4.expandedtrie")));
        }*/

        //Files.createDirectory(Paths.get("graphs"));

        new CSVSerializer().serialize(graph,  new FileOutputStream("graphs/war-and-society-1-2-3-4-graph-normalized.csv"));

        profiler.start("Serialize into Yaml");

        new YamlSerializer().serialize(graph, new FileOutputStream("graphs/war-and-society-1-2-3-4-graph.yaml"));

        profiler.start("Serialize into JSON");

        new JsonSerializer().serialize(graph, new FileOutputStream("graphs/war-and-society-1-2-3-4-graph.json"));


        //new BinaryGraphMatrixSerializer().serialize(graph, new FileOutputStream("graphs/war-and-society-1-2-3-4-graph.bin"));
        //new BinaryGraphVLQSerializer().serialize(graph, new FileOutputStream("graphs/war-and-society-1-2-3-4-graph-vlq.bin"));

        //new GraphAdjacencyListTrieSerializerCSV().serialize(graph, new FileOutputStream("graphs/war-and-society-1-2-3-4-adjacency-list.csv"));
        //new GraphAdjacencyListTrieSerializerBin().serialize(graph, new FileOutputStream("graphs/war-and-society-1-2-3-4-adjacency-list.bin"));

        profiler.start("Serialize edges for 'sql.ru'");
        new SimpleEdgeSerializerCSV().serialize(graph, new FileOutputStream("graphs/war-and-society-1-2-3-4-simple-edges.csv"));

        profiler.start("Serialize vertices");
        Graph graphNormalized = new GraphNormalizer().convert(graph);
        new VerticesSerializerCSV().serialize(graphNormalized, new FileOutputStream("graphs/war-and-society-1-2-3-4-vertices-norm.csv"));

        profiler.start("Serialize edges for 'Graphviz'");
        new GraphVizSerializer().serialize(graph, new FileOutputStream("graphviz/war-and-society-1-2-3-4.dot"), properties);

        profiler.stop().print();

        logger.info(graph.toString());

        logger.info("FINISH!");
    }

}
