package q150;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/lru-cache/
 *
 * Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following
 * operations: get and put.
 *
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity,
 * it should invalidate the least recently used item before inserting a new item.
 *
 * The cache is initialized with a positive capacity.
 *
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 *
 * Example:
 *
 * LRUCache cache = new LRUCache( 2 ); // argument 2 is capacity
 *
 * cache.put(1, 1);
 * cache.put(2, 2);
 * cache.get(1);       // returns 1
 * cache.put(3, 3);    // evicts key 2
 * cache.get(2);       // returns -1 (not found)
 * cache.put(4, 4);    // evicts key 1
 * cache.get(1);       // returns -1 (not found)
 * cache.get(3);       // returns 3
 * cache.get(4);       // returns 4
 */
public class Q146_LRUCache {

    private static class LRUCache {

        private Map<Integer, Node> keyNodeMap;

        private static class Node {

            private Node prev, next;

            private int key;

            private int value;

        }

        private Node headDummy, tailDummy;

        private final int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            keyNodeMap = new HashMap<>(capacity);

            headDummy = new Node();
            tailDummy = new Node();
            headDummy.next = tailDummy;
            tailDummy.prev = headDummy;
        }

        public int get(int key) {
            Node node = getNode(key);
            if (node == null) {
                return -1;
            }
            return node.value;
        }

        private Node getNode(int key) {
            Node node = keyNodeMap.get(key);
            if (node != null) {
                removeNode(node);
                addNode(node);
            }
            return node;
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addNode(Node node) {
            node.prev = tailDummy.prev;
            node.next = tailDummy;
            tailDummy.prev.next = node;
            tailDummy.prev = node;
        }

        public void put(int key, int value) {
            if (keyNodeMap.containsKey(key)) {
                getNode(key).value = value;
            } else {
                Node node;
                if (keyNodeMap.size() == capacity) {
                    node = headDummy.next;
                    removeNode(node);
                    keyNodeMap.remove(node.key);
                } else {
                    node = new Node();
                }
                node.key = key;
                node.value = value;
                keyNodeMap.put(key, node);
                addNode(node);
            }
        }
    }

    @Test
    public void testMethod() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        Assert.assertEquals(1, cache.get(1));
        cache.put(3, 3);
        Assert.assertEquals(-1, cache.get(2), -1);
        cache.put(4, 4);
        Assert.assertEquals(-1, cache.get(1), -1);
        Assert.assertEquals(3, cache.get(3));
        Assert.assertEquals(4, cache.get(4));
    }

}
