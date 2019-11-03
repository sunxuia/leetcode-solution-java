package q150;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/clone-graph/
 *
 * Given a reference of a node in a connected undirected graph, return a deep copy (clone) of the graph. Each node in
 * the graph contains a val (int) and a list (List[Node]) of its neighbors.
 *
 * (图见 Q133_PIC.png)
 *
 * Example:
 *
 * Input:
 * {"$id":"1","neighbors":[{"$id":"2","neighbors":[{"$ref":"1"},{"$id":"3","neighbors":[{"$ref":"2"},{"$id":"4",
 * "neighbors":[{"$ref":"3"},{"$ref":"1"}],"val":4}],"val":3}],"val":2},{"$ref":"4"}],"val":1}
 *
 * Explanation:
 * Node 1's value is 1, and it has two neighbors: Node 2 and 4.
 * Node 2's value is 2, and it has two neighbors: Node 1 and 3.
 * Node 3's value is 3, and it has two neighbors: Node 2 and 4.
 * Node 4's value is 4, and it has two neighbors: Node 1 and 3.
 *
 *
 *
 * Note:
 *
 * The number of nodes will be between 1 and 100.
 * The undirected graph is a simple graph, which means no repeated edges and no self-loops in the graph.
 * Since the graph is undirected, if node p has node q as neighbor, then node q must have node p as neighbor too.
 * You must return the copy of the given node as a reference to the cloned graph.
 */
@RunWith(LeetCodeRunner.class)
public class Q133_CloneGraph {

    static class Node {

        public int val;
        public List<Node> neighbors;

        public Node() {}

        public Node(int _val, List<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    @Answer
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        Map<Node, Node> nodes = new HashMap<>();
        return dfs(node, nodes);
    }

    private Node dfs(Node node, Map<Node, Node> nodes) {
        if (nodes.containsKey(node)) {
            return nodes.get(node);
        }

        Node newNode = new Node(node.val, new ArrayList<>(node.neighbors.size()));
        nodes.put(node, newNode);

        for (Node neighbor : node.neighbors) {
            Node newNeighbor = dfs(neighbor, nodes);
            if (!newNode.neighbors.contains(newNeighbor)) {
                newNode.neighbors.add(newNeighbor);
                newNeighbor.neighbors.add(newNode);
            }
        }
        return newNode;
    }

    @TestData
    public DataExpectation example() {
        Node n1 = new Node(1, new ArrayList<>());
        Node n2 = new Node(2, new ArrayList<>());
        Node n3 = new Node(3, new ArrayList<>());
        Node n4 = new Node(4, new ArrayList<>());
        n1.neighbors.add(n2);
        n1.neighbors.add(n4);
        n2.neighbors.add(n1);
        n2.neighbors.add(n3);
        n3.neighbors.add(n2);
        n3.neighbors.add(n4);
        n4.neighbors.add(n3);
        n4.neighbors.add(n1);

        return DataExpectation.builder()
                .addArgument(n1)
                .expect(null)
                .<Node>assertMethod(actual -> {
                    Assert.assertEquals(1, actual.val);
                    Assert.assertEquals(2, actual.neighbors.size());

                    Node a2 = getNeighbor(actual, 2);
                    Assert.assertNotNull(a2);
                    Assert.assertEquals(2, a2.neighbors.size());
                    Assert.assertEquals(getNeighbor(a2, 1), actual);

                    Node a4 = getNeighbor(actual, 4);
                    Assert.assertNotNull(a4);
                    Assert.assertEquals(2, a4.neighbors.size());
                    Assert.assertEquals(getNeighbor(a4, 1), actual);

                    Node a3 = getNeighbor(a2, 3);
                    Assert.assertNotNull(a3);
                    Assert.assertEquals(2, a3.neighbors.size());
                    Assert.assertEquals(getNeighbor(a3, 2), a2);
                    Assert.assertEquals(getNeighbor(a3, 4), a4);
                }).build();
    }

    private Node getNeighbor(Node node, int neighborVal) {
        for (Node neighbor : node.neighbors) {
            if (neighbor.val == neighborVal) {
                return neighbor;
            }
        }
        return null;
    }

    @TestData
    public DataExpectation border() {
        return DataExpectation.create(null).expect(null);
    }

    @TestData
    public DataExpectation normal1() {
        Node node = new Node(1, new ArrayList<>());
        return DataExpectation.builder()
                .addArgument(node)
                .<Node>assertMethod(actual -> {
                    Assert.assertEquals(node.val, actual.val);
                    Assert.assertEquals(0, actual.neighbors.size());
                }).build();
    }

}
