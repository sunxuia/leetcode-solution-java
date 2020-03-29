package q500;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/lfu-cache/
 *
 * Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following
 * operations: get and put.
 *
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity,
 * it should invalidate the least frequently used item before inserting a new item. For the purpose of this problem,
 * when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be
 * evicted.
 *
 * Note that the number of times an item is used is the number of calls to the get and put functions for that item
 * since it was inserted. This number is set to zero when the item is removed.
 *
 *
 *
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 *
 *
 *
 * Example:
 *
 * LFUCache cache = new LFUCache( 2 capacity  );
 *
 * cache.put(1,1);
 * cache.put(2,2);
 * cache.get(1);       // returns 1
 * cache.put(3,3);    // evicts key 2
 * cache.get(2);       // returns -1 (not found)
 * cache.get(3);       // returns 3.
 * cache.put(4,4);    // evicts key 1.
 * cache.get(1);       // returns -1 (not found)
 * cache.get(3);       // returns 3
 * cache.get(4);       // returns 4
 */
public class Q460_LFUCache {

    // 题目不困难, 就是麻烦
    private static class LFUCache {

        static class CountNode {

            int count;

            CountNode prev, next;

            Node head = new Node(), tail = new Node();

            Map<Integer, Node> map = new HashMap<>();

            CountNode() {
                head.next = tail;
                tail.prev = head;
            }

        }

        static class Node {

            private int key, value;

            private Node prev, next;

        }

        Map<Integer, CountNode> map = new HashMap<>();

        CountNode zero = new CountNode();

        final int capacity;

        public LFUCache(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            Node node = visitNode(key);
            return node == null ? -1 : node.value;
        }

        Node visitNode(int key) {
            CountNode curr = map.get(key);
            if (curr == null) {
                return null;
            }
            CountNode next = curr.next;
            if (next == null
                    || next.count != curr.count + 1) {
                next = new CountNode();
                next.count = curr.count + 1;
                next.prev = curr;
                next.next = curr.next;
                next.prev.next = next;
                if (next.next != null) {
                    next.next.prev = next;
                }
            }
            Node node = removeNode(key);
            addNode(next, node);
            return node;
        }

        Node removeNode(int key) {
            CountNode countNode = map.remove(key);
            Node node = countNode.map.remove(key);
            node.prev.next = node.next;
            node.next.prev = node.prev;
            if (countNode.map.isEmpty() && countNode != zero) {
                countNode.prev.next = countNode.next;
                if (countNode.next != null) {
                    countNode.next.prev = countNode.prev;
                }
            }
            return node;
        }

        void addNode(CountNode countNode, Node node) {
            map.put(node.key, countNode);
            countNode.map.put(node.key, node);
            node.prev = countNode.head;
            node.next = countNode.head.next;
            node.prev.next = node;
            node.next.prev = node;
        }

        public void put(int key, int value) {
            Node node = visitNode(key);
            if (node != null) {
                node.value = value;
                return;
            }

            if (map.size() == capacity) {
                if (capacity == 0) {
                    return;
                } else if (!zero.map.isEmpty()) {
                    node = removeNode(zero.tail.prev.key);
                } else {
                    node = removeNode(zero.next.tail.prev.key);
                }
                map.remove(node.key);
            } else {
                node = new Node();
            }
            node.key = key;
            node.value = value;
            addNode(zero, node);
            map.put(key, zero);
        }
    }

    @Test
    public void testMethod() {
        LFUCache cache = new LFUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        Assert.assertEquals(1, cache.get(1));
        cache.put(3, 3);
        Assert.assertEquals(-1, cache.get(2));
        Assert.assertEquals(3, cache.get(3));
        cache.put(4, 4);
        Assert.assertEquals(-1, cache.get(1));
        Assert.assertEquals(3, cache.get(3));
        Assert.assertEquals(4, cache.get(4));
    }

    @Test
    public void testMethod2() {
        LFUCache cache = new LFUCache(3);
        cache.put(2, 2);
        cache.put(1, 1);
        Assert.assertEquals(2, cache.get(2));
        Assert.assertEquals(1, cache.get(1));
        Assert.assertEquals(2, cache.get(2));
        cache.put(3, 3);
        cache.put(4, 4);
        Assert.assertEquals(-1, cache.get(3));
        Assert.assertEquals(2, cache.get(2));
        Assert.assertEquals(1, cache.get(1));
        Assert.assertEquals(4, cache.get(4));
    }

    @Test
    public void testMethod3() {
        LFUCache cache = new LFUCache(2);
        cache.put(3, 1);
        cache.put(2, 1);
        cache.put(2, 2);
        cache.put(4, 4);
        Assert.assertEquals(2, cache.get(2));
    }

    @Test
    public void testMethod4() {
        LFUCache cache = new LFUCache(0);
        cache.put(0, 0);
        Assert.assertEquals(-1, cache.get(0));
    }

}
