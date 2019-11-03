package q150;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/copy-list-with-random-pointer/
 *
 * A linked list is given such that each node contains an additional random pointer which could point to any node in
 * the list or null.
 *
 * Return a deep copy of the list.
 *
 *
 *
 * Example 1:
 *
 * ( 图见 Q138_PIC.png)
 *
 * Input:
 * {"$id":"1","next":{"$id":"2","next":null,"random":{"$ref":"2"},"val":2},"random":{"$ref":"2"},"val":1}
 *
 * Explanation:
 * Node 1's value is 1, both of its next and random pointer points to Node 2.
 * Node 2's value is 2, its next pointer points to null and its random pointer points to itself.
 *
 *
 *
 * Note:
 *
 * You must return the copy of the given head as a reference to the cloned list.
 */
@RunWith(LeetCodeRunner.class)
public class Q138_CopyListWithRandomPointer {

    class Node {

        public int val;
        public Node next;
        public Node random;

        public Node() {}

        public Node(int _val, Node _next, Node _random) {
            val = _val;
            next = _next;
            random = _random;
        }
    }

    @Answer
    public Node copyRandomList(Node head) {
        Map<Node, Node> nodes = new HashMap<>();
        return dfs(head, nodes);
    }

    private Node dfs(Node node, Map<Node, Node> exists) {
        if (node == null) {
            return null;
        }
        if (exists.containsKey(node)) {
            return exists.get(node);
        }
        Node newNode = new Node();
        newNode.val = node.val;
        exists.put(node, newNode);

        newNode.next = dfs(node.next, exists);
        newNode.random = dfs(node.random, exists);
        return newNode;
    }

    @TestData
    public DataExpectation example() {
        Node n1 = new Node();
        n1.val = 1;
        Node n2 = new Node();
        n2.val = 2;
        n1.next = n2;
        n1.random = n2;
        n2.random = n2;

        return DataExpectation.builder()
                .addArgument(n1)
                .<Node>assertMethod(act -> {
                    Assert.assertEquals(1, act.val);
                    Assert.assertEquals(act.next, act.random);
                    Node a2 = act.next;
                    Assert.assertEquals(2, a2.val);
                    Assert.assertEquals(a2, a2.random);
                    Assert.assertNull(a2.next);
                })
                .build();
    }

}
