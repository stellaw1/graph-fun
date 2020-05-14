package graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Testing ImGraph functions in Graph.java
 */
@Timeout(10)
class Task2 {

    private static List<String> alphabet = List.of(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    );

    /* Graph 0 */
    private static List<Vertex> lv0 = List.of(
            new Vertex(1, "A"),
            new Vertex(2, "B"),
            new Vertex(3, "C"),
            new Vertex(4, "D")
    );
    private static List<Edge<Vertex>> le0 = List.of(
            new Edge<>(lv0.get(0), lv0.get(1), 5),
            new Edge<>(lv0.get(1), lv0.get(2), 7),
            new Edge<>(lv0.get(0), lv0.get(3), 9)
    );

    /* Graph 1 (No connection) */
    private static List<Edge<Vertex>> le1 = List.of(
            new Edge<>(lv0.get(0), lv0.get(1), 5),
            new Edge<>(lv0.get(2), lv0.get(3), 7)
    );

    /* Full Alphabet vertices */
    private static Map<String, Vertex> lv1 = IntStream.range(0, 26)
            .mapToObj(a -> new AbstractMap.SimpleEntry<>(
                    alphabet.get(a), new Vertex(a, alphabet.get(a))
            ))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    /*                 Graph 2
     *
     *                    D
     *  +V----------------+--+
     *  |                 |  E
     *  |                 |
     * U+-----+S         B+-----+
     *  |     |    Q      |     C        +G
     *  +T   R+----+      |              |
     *        |    |      |              |
     *        |    +------+---------+F   |
     *    Y   |    P      A         |    |
     *    +---+X-------------------W+----+H
     *    |   |                     |    |
     *    +   +-------+N----K+------+J   |
     *    Z   O       |      |           |
     *                |      |           +I
     *                |      |
     *                +------+L
     *                M
     */
    private static List<Edge<Vertex>> le2 = List.of(
            new Edge<>(lv1.get("A"), lv1.get("P"), 6),
            new Edge<>(lv1.get("A"), lv1.get("B"), 3),
            new Edge<>(lv1.get("A"), lv1.get("F"), 9),
            new Edge<>(lv1.get("B"), lv1.get("D"), 4),
            new Edge<>(lv1.get("B"), lv1.get("C"), 5),
            new Edge<>(lv1.get("D"), lv1.get("E"), 7),
            new Edge<>(lv1.get("D"), lv1.get("V"), 15),
            new Edge<>(lv1.get("F"), lv1.get("W"), 2),
            new Edge<>(lv1.get("G"), lv1.get("H"), 43),
            new Edge<>(lv1.get("H"), lv1.get("I"), 8),
            new Edge<>(lv1.get("J"), lv1.get("W"), 1),
            new Edge<>(lv1.get("J"), lv1.get("K"), 10),
            new Edge<>(lv1.get("K"), lv1.get("L"), 11),
            new Edge<>(lv1.get("L"), lv1.get("M"), 13),
            new Edge<>(lv1.get("M"), lv1.get("N"), 14),
            new Edge<>(lv1.get("N"), lv1.get("O"), 16),
            new Edge<>(lv1.get("N"), lv1.get("K"), 19),
            new Edge<>(lv1.get("O"), lv1.get("X"), 18),
            new Edge<>(lv1.get("P"), lv1.get("Q"), 22),
            new Edge<>(lv1.get("Q"), lv1.get("R"), 23),
            new Edge<>(lv1.get("R"), lv1.get("S"), 24),
            new Edge<>(lv1.get("R"), lv1.get("X"), 25),
            new Edge<>(lv1.get("S"), lv1.get("U"), 26),
            new Edge<>(lv1.get("T"), lv1.get("U"), 27),
            new Edge<>(lv1.get("U"), lv1.get("V"), 28),
            new Edge<>(lv1.get("W"), lv1.get("H"), 29),
            new Edge<>(lv1.get("X"), lv1.get("Y"), 30),
            new Edge<>(lv1.get("X"), lv1.get("W"), 42),
            new Edge<>(lv1.get("Y"), lv1.get("Z"), 40)
    );

    private static Graph<Vertex, Edge<Vertex>> g0 = formGraph(lv0, le0);
    private static Graph<Vertex, Edge<Vertex>> g1 = formGraph(lv0, le1);
    private static Graph<Vertex, Edge<Vertex>> g2 = new Graph<>();
    private static Graph<Vertex, Edge<Vertex>> g3 = formGraph(new ArrayList<>(lv1.values()), le2);

    private static Graph<Vertex, Edge<Vertex>> formGraph(List<Vertex> lv, List<Edge<Vertex>> le) {
        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        lv.forEach(g::addVertex);
        le.forEach(g::addEdge);
        return g;
    }

    /**
     * Argument provider
     *
     * @return Graph to test, Vertex, Vertex, expected length
     */
    private static Stream<Arguments> pathLengthProvider() {
        return Stream.of(
                Arguments.of(new Graph<>(), List.of(), 0),
                Arguments.of(g0, List.of(lv0.get(0), lv0.get(1), lv0.get(2)), 12),
                Arguments.of(g3, List.of(
                        lv1.get("I"), lv1.get("H"), lv1.get("W"), lv1.get("J"), lv1.get("K")
                ), 48)
        );
    }

    /**
     * Argument provider
     *
     * @return Graph to test, v0, v1, edge
     */
    private static Stream<Arguments> edgeProvider() {
        return Stream.of(
                Arguments.of(g0, lv0.get(0), lv0.get(1), le0.get(0))
        );
    }

    /**
     * Argument provider
     *
     * @return Graph to test, expected sum
     */
    private static Stream<Arguments> edgeLengthSumProvider() {
        return Stream.of(
                Arguments.of(g0, 21),
                Arguments.of(g1, 12),   /* No path */
                Arguments.of(g2, 0),     /* No path */
                Arguments.of(g3, 520)
        );
    }

    /**
     * Argument provider
     *
     * @return Graph to test, expected
     */
    private static Stream<Arguments> shortestPathProvider() {
        return Stream.of(
                Arguments.of(g0, lv0.get(2), lv0.get(3), 21),
                Arguments.of(g1, lv0.get(0), lv0.get(3), Integer.MAX_VALUE), /* No path */
                Arguments.of(g3, lv1.get("I"), lv1.get("X"), 79)
        );
    }

    /**
     * Argument provider
     *
     * @return Graph to test, expected
     */
    private static Stream<Arguments> minimumSpanningTreeProvider() {
        /* A<->F, D<->V, L<->M, X<->W */
        Set<Edge<Vertex>> g3Excluded = Set.of(le2.get(16), le2.get(21), le2.get(24), le2.get(27));
        Set<Edge<Vertex>> g3Mst = le2.stream()
                .filter(e -> !g3Excluded.contains(e))
                .collect(Collectors.toSet());

        return Stream.of(
                Arguments.of(g2, Set.of()),
                Arguments.of(g3, g3Mst)
        );
    }

    /**
     * Argument provider
     *
     * @return Graph to test, expected diameter
     */
    private static Stream<Arguments> diameterProvider() {
        return Stream.of(
                Arguments.of(g0, 21),
                Arguments.of(g1, 7),
                Arguments.of(g3, 184)
        );
    }

    @ParameterizedTest
    @MethodSource("pathLengthProvider")
    void pathLength(Graph<Vertex, Edge<Vertex>> g, List<Vertex> path, int weight) {
        try {
            assertEquals(weight, g.pathLength(path));
        } catch(Exception ex) {
            if (weight != 0) {
                fail();
            }
        }

    }

    @ParameterizedTest
    @MethodSource("edgeProvider")
    void edge(Graph<Vertex, Edge<Vertex>> g, Vertex v0, Vertex v1, Edge<Vertex> e) {
        assertEquals(e, g.getEdge(v0, v1));
    }

    @Test
    void remove() {
        List<Vertex> lv0 = List.of(
                new Vertex(1, "A"),
                new Vertex(2, "B"),
                new Vertex(3, "C"),
                new Vertex(4, "D")
        );
        Edge<Vertex> eRem = new Edge<>(lv0.get(0), lv0.get(1), 5);
        List<Edge<Vertex>> le0 = List.of(
                eRem,
                new Edge<>(lv0.get(1), lv0.get(2), 7),
                new Edge<>(lv0.get(0), lv0.get(3), 9)
        );

        var g = formGraph(lv0, le0);

        g.remove(le0.get(0));
        assertFalse("Edge not in Graph", g.allEdges().contains(eRem));
    }

    @ParameterizedTest
    @MethodSource("edgeLengthSumProvider")
    void edgeLengthSum(Graph<Vertex, Edge<Vertex>> g, int sum) {
        assertEquals(sum, g.edgeLengthSum());
    }

    @ParameterizedTest
    @MethodSource("shortestPathProvider")
    void shortestPath(Graph<Vertex, Edge<Vertex>> g, Vertex v0, Vertex v1, int shortest) {
        try {
            int answer = g.pathLength(g.shortestPath(v0, v1));
            if (g.shortestPath(v0, v1).size() == 0) {
                if (answer == 0 || answer == Integer.MAX_VALUE) {
                    shortest = answer;
                }
            }
            assertEquals(shortest, g.pathLength(g.shortestPath(v0, v1)));
        }
        catch(Exception ex) {
            if (shortest != 0 || shortest != Integer.MAX_VALUE) {
                fail();
            }
        }
    }

    @ParameterizedTest
    @MethodSource("minimumSpanningTreeProvider")
    void minimumSpanningTree(Graph<Vertex, Edge<Vertex>> g, Set<Edge<Vertex>> mstExpected) {
        try {
            Set<Edge<Vertex>> mst = new HashSet<>(g.minimumSpanningTree());
            assertEquals(mstExpected, mst);
        }
        catch (Exception ex) {
            if (mstExpected.size() == 0) {
                // okay to see an exception if the graph is disconnected
                assertTrue(true);
            } else {
                fail();
            }
        }
    }

    @ParameterizedTest
    @MethodSource("diameterProvider")
    void diameter(Graph<Vertex, Edge<Vertex>> g, int expected) {
        try {
            int answer = g.diameter();
            if (answer < 0 || answer == Integer.MAX_VALUE) {
                if (g.allEdges().size() == 0) {
                    expected = answer;
                }
            }
            assertEquals(expected, answer);
        }
        catch (Exception ex) {
            if (g.allEdges().size() != 0) {
                fail();
            }
        }
    }
}

