package graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Timeout(10)
class Task1 {

    @Test
    public void testAddVertex() {
        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        Vertex v = new Vertex(1, "One");
        g.addVertex(v);
        g.addVertex(v);
        assertEquals(new HashSet<Edge>(), g.allEdges());
    }

    @Test
    public void testRemoveVertex1() {
        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        Vertex v1 = new Vertex(1, "One");
        Vertex v2 = new Vertex(2, "Two");
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v1);
        Edge<Vertex> e = new Edge<>(v1, v2);
        g.addEdge(e);
        Set<Edge<Vertex>> edgeSet = new HashSet<>();
        edgeSet.add(e);
        assertEquals(edgeSet, g.allEdges());
        assertEquals(edgeSet, g.allEdges(v1));
        g.remove(v1);
        assertEquals(new HashSet<Edge>(), g.allEdges());
    }


    @Test
    public void testRemoveVertex2() {
        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        Vertex v1 = new Vertex(1, "One");
        Vertex v2 = new Vertex(2, "Two");
        Vertex v3 = new Vertex(3, "Three");
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        Edge<Vertex> e1 = new Edge<>(v1, v2);
        Edge<Vertex> e2 = new Edge<>(v3, v2);
        g.addEdge(e1);
        g.addEdge(e2);
        Set<Edge<Vertex>> edgeSet = new HashSet<>();
        edgeSet.add(e1);
        edgeSet.add(e2);
        assertEquals(edgeSet, g.allEdges());
        g.remove(v1);
        edgeSet.remove(e1);
        assertEquals(edgeSet, g.allEdges());
    }
    @Test
    public void testRemoveVertex3() {
        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        Vertex v1 = new Vertex(1, "One");
        Vertex v2 = new Vertex(2, "Two");
        Vertex v3 = new Vertex(3, "Three");
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        Edge<Vertex> e1 = new Edge<>(v1, v2);
        Edge<Vertex> e2 = new Edge<>(v3, v2);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 10);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        Set<Edge<Vertex>> edgeSet = new HashSet<>();
        edgeSet.add(e1);
        edgeSet.add(e2);
        edgeSet.add(e3);
        assertEquals(edgeSet, g.allEdges());
        g.remove(v1);
        edgeSet.remove(e1);
        edgeSet.remove(e3);
        assertEquals(edgeSet, g.allEdges());
    }

    @Test
    public void testEdgeLengthSum() {
        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        Vertex v1 = new Vertex(1, "One");
        Vertex v2 = new Vertex(2, "Two");
        Vertex v3 = new Vertex(3, "Three");
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        Edge<Vertex> e1 = new Edge<>(v1, v2, 10);
        Edge<Vertex> e2 = new Edge<>(v3, v2, 12);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 14);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        assertEquals(36, g.edgeLengthSum());
        g.remove(e1);
        assertEquals(26, g.edgeLengthSum());
    }

    @Test
    public void testEdge() {
        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        Vertex v1 = new Vertex(1, "One");
        Vertex v2 = new Vertex(2, "Two");
        Vertex v3 = new Vertex(3, "Three");
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        Edge<Vertex> e1 = new Edge<>(v1, v2, 10);
        Edge<Vertex> e2 = new Edge<>(v3, v2, 12);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 14);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        assertTrue(g.edge(e1));
        assertTrue(g.edge(v1, v3));
        g.remove(e2);
        assertFalse(g.edge(e2));
    }

    @Test
    public void testEdgeLength1() {
        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        Vertex v1 = new Vertex(1, "One");
        Vertex v2 = new Vertex(2, "Two");
        Vertex v3 = new Vertex(3, "Three");
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        Edge<Vertex> e1 = new Edge<>(v1, v2, 10);
        Edge<Vertex> e2 = new Edge<>(v3, v2, 12);
        g.addEdge(e1);
        g.addEdge(e2);
        assertEquals(10, g.edgeLength(v1, v2));
        assertEquals(12, g.edgeLength(v2, v3));
    }

    @Test
    public void testEdgeLength2() {
        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        Vertex v1 = new Vertex(1, "One");
        Vertex v2 = new Vertex(2, "Two");
        Vertex v3 = new Vertex(3, "Three");
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        Edge<Vertex> e1 = new Edge<>(v1, v2);
        Edge<Vertex> e2 = new Edge<>(v3, v2);
        g.addEdge(e1);
        g.addEdge(e2);
        assertEquals(1, g.edgeLength(v1, v2));
        assertEquals(1, g.edgeLength(v2, v3));
    }

    @Test
    public void testAddEdge1() {
        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        Vertex v1 = new Vertex(1, "One");
        Vertex v2 = new Vertex(2, "Two");
        g.addVertex(v1);
        g.addVertex(v2);
        Edge<Vertex> e1 = new Edge<>(v1, v2);
        try {
            assertTrue(g.addEdge(e1));
        } catch (Exception ex) {
            fail("No exception expected");
        }
    }
}

