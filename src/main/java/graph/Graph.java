package graph;

import java.util.*;


/**
 * Represents a mutable graph with vertices of type V.
 *
 * @param <V> represents a vertex type
 * @param <E> represents an edge type
 */
public class Graph<V extends Vertex, E extends Edge<V>> implements ImGraph<V, E>, IGraph<V, E> {

    /** Set of all vertices in graph */
    private HashSet<V> vertexSet = new HashSet<>();

    /** Set of all edges in graph */
    private HashSet<E> edgeSet = new HashSet<>();

    // Representation Invariant
    //      E instanceof Edge<V> && V instanceof Vertex.
    //
    // Abstraction Function:
    //      represents a simple, undirected graph g where
    //      g.vertexSet represents all the nodes in g and
    //      g.edgeSet represents all the node-to-node connections that exist in g



    ////////// methods from IGraph Interface //////////
    /**
     * Add vertex to the graph if no vertex with the same id already exists in graph.
     *
     * @param v vertex to add
     * @return true if the vertex was added successfully and false otherwise
     */
    public boolean addVertex(V v) {
        if (v == null) {
            return false;
        }
        for (V ver: vertexSet) {
            if (v.checkID(ver)) {
                return false;
            }
        }
        vertexSet.add(v);
        return vertexSet.contains(v);
    }

    /**
     * Checks if a vertex is part of the graph
     *
     * @param v vertex to check in the graph
     * @return true of v is part of the graph and false otherwise
     */
    public boolean vertex(V v) {
        return vertexSet.contains(v);
    }

    /**
     * Add an edge of the graph and check if added successfully.
     *
     * @param e the edge to add to the graph
     * @return true if the edge was successfully added and false otherwise
     */
    public boolean addEdge(E e) {
        if (e == null) {
            return false;
        }
        if (edgeSet.contains(e)) {
            return false;
        }
        if (vertexSet.contains(e.v2()) && vertexSet.contains(e.v1())) {
            edgeSet.add(e);
            return true;
        }
        return false;
    }

    /**
     * Check if an edge is part of the graph
     *
     * @param e the edge to check in the graph
     * @return true if e is an edge in the graoh and false otherwise
     */
    public boolean edge(E e) {
        return edgeSet.contains(e);
    }

    /**
     * Check if v1-v2 or v2-v1 is an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return true if the v1-v2 or v2-v1 edge is part of the graph and false otherwise
     */
    public boolean edge(V v1, V v2) {
        for (E e : edgeSet) {
            if (e.v1().equals(v1) && e.v2().equals(v2) || e.v1().equals(v2) && e.v2().equals(v1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine the length on an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return the length of the v1-v2 or v2-v1 edge, if this edge is part of the graph
     *              and 0 otherwise
     */
    public int edgeLength(V v1, V v2) {
        if (edge(v1, v2) || edge(v2, v1)) {
            for (E e : edgeSet) {
                if (e.v1().equals(v1) && e.v2().equals(v2)
                        || e.v1().equals(v2) && e.v2().equals(v1)) {
                    return e.length();
                }
            }
        }
        return 0;
    }

    /**
     * Obtain the sum of the lengths of all edges in the graph
     *
     * @return the sum of the lengths of all edges in the graph
     */
    public int edgeLengthSum() {
        int sum = 0;
        for (E e: edgeSet) {
            sum += e.length();
        }
        return sum;
    }

    /**
     * Remove an edge from the graph and check if removed successfully.
     *
     * @param e the edge to remove
     * @return true if e was successfully removed and false otherwise
     */
    public boolean remove(E e) {
        if (edge(e)) {
            edgeSet.remove(e);
            return true;
        }
        return false;
    }

    /**
     * Remove a vertex from the graph and check if removed successfully.
     * Also remove all edges associated with that vertex if removed successfully.
     *
     * @param v the vertex to remove
     * @return true if v was successfully removed and false otherwise
     */
    public boolean remove(V v) {
        if (vertex(v)) {
            vertexSet.remove(v);
            for (E e: this.allEdges()) {
                if (e.incident(v)) {
                    edgeSet.remove(e);
                }
            }
            return true;
        }
        return false;
    }


    /**
     * Obtain a set of all vertices in the graph.
     * Access to this set does not permit graph mutations.
     *
     * @return a set of all vertices in the graph
     */
    public Set<V> allVertices() {
        return new HashSet<>(vertexSet);
    }

    /**
     * Obtain a set of all edges incident on v.
     * Access to this set does not permit graph mutations.
     *
     * @param v the vertex of interest
     * @return all edges incident on v
     */
    public Set<E> allEdges(V v) {
        Set<E> thisESet = new HashSet<>();
        for (E e : edgeSet) {
            if (e.incident(v)) {
                thisESet.add(e);
            }
        }
        return thisESet;
    }

    /**
     * Obtain a set of all edges in the graph.
     * Access to this set does not permit graph mutations.
     *
     * @return all edges in the graph
     */
    public Set<E> allEdges() {
        return new HashSet<>(edgeSet);
    }

    /**
     * Obtain all the neighbours of vertex v.
     * Access to this map may permit graph mutations.
     *
     * @param v is the vertex whose neighbourhood we want.
     * @return a map containing each vertex w that neighbors v and the edge between v and w.
     */
    public Map<V, E> getNeighbours(V v) {
        Map<V, E> neighbourMap = new HashMap<>();
        for (E e : edgeSet) {
            if (e.incident(v)) {
                neighbourMap.put(e.distinctVertex(v), e);
            }
        }
        return neighbourMap;
    }



    ////////// methods from ImGraph Interface //////////
    /**
     * Compute the shortest path from source to sink, using Dijkstra's algorithm
     *
     * @param source the start vertex
     * @param sink   the end vertex
     * @return the vertices, in order, on the shortest path from source to sink
     *              (both end points are part of the list) and returns empty list
     *              if no shortest path exists
     */
    public List<V> shortestPath(V source, V sink) {

        //check invalid inputs
        if (source == sink) {
            List<V> path = new ArrayList<>();
            path.add(sink);
            return path;
        }

        //initialize variables
        Set<V> visitedNodes = new HashSet<>();
        Map<V, Integer> nodeDist = new HashMap<>();
        Map<V, V> prevNode = new HashMap<>();

        //Initialize all nodeWeights to be infinity (represented by Integer.MAX_VALUE)
        // except source vertex is set to a weight of 0
        for (V v: vertexSet) {
            if (v.equals(source)) {
                nodeDist.put(source, 0);
            } else {
                nodeDist.put(v, Integer.MAX_VALUE);
            }
        }

        //initialize priority queue with initial capacity of number of vertices in graph
        // and ordering according to node distances in nodeDist
        Comparator<V> comp = new Comparator<V>() {
            @Override
            public int compare(V v1, V v2) {
                if (nodeDist.get(v1) < nodeDist.get(v2)) {
                    return -1;
                } else if (nodeDist.get(v1) > nodeDist.get(v2)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };

        PriorityQueue<V> pq = new PriorityQueue<>(this.vertexSet.size(), comp);
        pq.add(source);


        //loop while priority queue is not empty
        while (!pq.isEmpty()) {
            //remove top node from pq with the smallest distance
            V current = pq.poll();

            //skip this node if already visited
            if (!visitedNodes.contains(current)) {

                //add current node to visited set
                visitedNodes.add(current);

                //update distances of all of current node's neighbours
                Map<V, E> neighbours = getNeighbours(current);
                for (V neighbour : neighbours.keySet()) {
                    //update node distance only if new distance is shorter than existing dist
                    int newDist = nodeDist.get(current) + neighbours.get(neighbour).length();
                    if (newDist < nodeDist.get(neighbour)) {
                        //if shorter distance found, replace dist in nodeDist map and update prevNode
                        nodeDist.replace(neighbour, newDist);

                        if (!prevNode.containsKey(neighbour)) {
                            prevNode.put(neighbour, current);
                        } else {
                            prevNode.replace(neighbour, current);
                        }

                        if(!visitedNodes.contains(neighbour))
                            pq.add(neighbour);
                    }
                }
            }
        }

        //output shortest path using prevNodes
        List<V> path = new ArrayList<>();
        path.add(sink);
        V curr = sink;
        while (curr != source) {
            if (!prevNode.containsKey(curr)) {
                return new ArrayList<V>();
            }
            curr = prevNode.get(curr);
            path.add(curr);
        }
        Collections.reverse(path);

        return path;
    }

    /**
     * Compute the minimum spanning tree of the graph.
     *
     * @return a list of edges that forms a minimum spanning tree of the graph
     */
    public List<E> minimumSpanningTree() {
        List<E> mstEdges = new ArrayList<>();
        List<Set<V>> unvisitedNodes = new ArrayList<>();
        E shortestEdge = null;

        for (V v: vertexSet) {
            Set<V> thisNodeSet = new HashSet<>();
            thisNodeSet.add(v);
            unvisitedNodes.add(thisNodeSet);
        }

        List<E> allEdges = new ArrayList<>(edgeSet);

        //sort allEdges by length from shortest to longest
        allEdges.sort(new Comparator<>() {
            @Override
            public int compare(E e1, E e2) {
                return e1.length() - e2.length();
            }
        });

        //add shortest edge to unvisitedNodes set only if the edge does not form a loop
        // ie its two nodes are in different sets
        // then merge the two sets that contain its two nodes
        for (E e : allEdges) {
            Set<V> v1Set = new HashSet<>();
            Set<V> v2Set = new HashSet<>();
            for (int i = 0; i < unvisitedNodes.size(); i++) {
                if (unvisitedNodes.get(i).contains(e.v1())) {
                    v1Set = unvisitedNodes.get(i);
                }
            }

            for (int j = 0; j < unvisitedNodes.size(); j++) {
                if (unvisitedNodes.get(j).contains(e.v2())) {
                    v2Set = unvisitedNodes.get(j);
                }
            }

            if (!v1Set.equals(v2Set)) {
                for (int i = 0; i < unvisitedNodes.size(); i++) {
                    if (unvisitedNodes.get(i).equals(v1Set)) {
                        for (int j = 0; j < unvisitedNodes.size(); j++) {
                            if (unvisitedNodes.get(j).equals(v2Set)) {
                                unvisitedNodes.get(i).addAll(unvisitedNodes.get(j));
                                unvisitedNodes.remove(unvisitedNodes.get(j));
                                mstEdges.add(e);
                            }
                        }
                    }
                }
            }
        }
        return mstEdges;
    }


    /**
     * Compute the length of a given path
     *
     * @param path is a List of Vertices that indicate the vertices of a given path (order matters)
     * @return integer that represents the length of path
     */
    public int pathLength(List<V> path) {
        int pathLength = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            pathLength += edgeLength(path.get(i), path.get(i + 1));
        }

        return pathLength;
    }


    /**
     * Compute the diameter of the graph, namely the length of the longest shortest
     * path in the graph.
     *
     * @return the diameter of the graph. (diameter of the largest component
     *              if graph has multiple components)
     */
    public int diameter() {
        Set<Integer> shortestPaths = new HashSet<>();
        int diam = 0;

        //add all vertices to a list
        List<V> vertices = new ArrayList<>(vertexSet);

        //calculate shortest path length for each vertices pair
        for (V source: vertices) {
            for (int i = 0; i < vertices.size(); i++) {
                V sink = vertices.get(i);
                int length = 0;
                if (!sink.equals(source)) {
                    List<V> shortestPath = shortestPath(source, sink);
                    for (int j = 0; j < shortestPath.size() - 1; j++) {
                        length += edgeLength(shortestPath.get(j), shortestPath.get(j + 1));
                    }
                    shortestPaths.add(length);
                }

            }
        }

        //find longest value in shortestPaths
        for (int length: shortestPaths) {
            if (length > diam) {
                diam = length;
            }
        }

        return diam;
    }

    /**
     * Finds the edge that connects two vertices if such an edge exists.
     * This method may permit graph mutations to returned edge in edgeSet.
     *
     * @param v1 one end of the edge
     * @param v2 the other end of the edge
     * @return the edge connecting v1 and v2, and returns null
     *              if edge does not exist in graph
     */
    public E getEdge(V v1, V v2) {
        if (edge(v1, v2)) {
            for (E edge: edgeSet) {
                if (edge.v1().equals(v1) && edge.v2().equals(v2)
                        || edge.v1().equals(v2) && edge.v2().equals(v1)) {
                    return edge;
                }
            }
        }
        return null;
    }
}
