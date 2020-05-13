package q700;

import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/map-sum-pairs/
 *
 * Implement a MapSum class with insert, and sum methods.
 *
 * For the method insert, you'll be given a pair of (string, integer). The string represents the key and the integer
 * represents the value. If the key already existed, then the original key-value pair will be overridden to the new one.
 *
 * For the method sum, you'll be given a string representing the prefix, and you need to return the sum of all the
 * pairs' value whose key starts with the prefix.
 *
 * Example 1:
 *
 * Input: insert("apple", 3), Output: Null
 * Input: sum("ap"), Output: 3
 * Input: insert("app", 2), Output: Null
 * Input: sum("ap"), Output: 5
 */
public class Q677_MapSumPairs {

    private static class MapSum {

        /**
         * Initialize your data structure here.
         */
        public MapSum() {

        }

        private Node root = new Node();

        public void insert(String key, int val) {
            Node node = root;
            for (int i = 0; i < key.length(); i++) {
                int idx = key.charAt(i) - 'a';
                Node next = node.get(idx);
                if (next == null) {
                    next = new Node();
                    next.parent = node;
                    node.put(idx, next);
                }
                node = next;
            }

            if (node.isEnd) {
                val = val - node.value;
            }
            node.isEnd = true;
            while (node != null) {
                node.value += val;
                node = node.parent;
            }
        }

        public int sum(String prefix) {
            Node node = root;
            for (int i = 0; node != null && i < prefix.length(); i++) {
                int idx = prefix.charAt(i) - 'a';
                node = node.get(idx);
            }
            return node == null ? 0 : node.value;
        }

        private static class Node extends HashMap<Integer, Node> {

            boolean isEnd;

            int value;

            Node parent;
        }
    }

    @Test
    public void testMethod() {
        MapSum sum;
        int res;

        sum = new MapSum();
        sum.insert("apple", 3);
        res = sum.sum("ap");
        Assert.assertEquals(3, res);
        sum.insert("app", 2);
        Assert.assertEquals(5, sum.sum("ap"));
    }

}
