package q450;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/all-oone-data-structure/
 *
 * Implement a data structure supporting the following operations:
 *
 * 1. Inc(Key) - Inserts a new key with value 1. Or increments an existing key by 1. Key is guaranteed to be a
 * non-empty string.
 * 2. Dec(Key) - If Key's value is 1, remove it from the data structure. Otherwise decrements an existing key by 1.
 * If the key does not exist, this function does nothing. Key is guaranteed to be a non-empty string.
 * 3. GetMaxKey() - Returns one of the keys with maximal value. If no element exists, return an empty string "".
 * 4. GetMinKey() - Returns one of the keys with minimal value. If no element exists, return an empty string "".
 *
 * Challenge: Perform all these in O(1) time complexity.
 */
public class Q432_AllOOneDataStructure {

    private static class AllOne {

        private static class Node {

            private Node next, prev;

            private Set<String> keys = new HashSet<>();

        }

        // key 次数 -> Node 节点
        private Map<Integer, Node> counts = new HashMap<>();

        // key -> key 次数
        private Map<String, Integer> keys = new HashMap<>();

        // 头尾节点
        private Node head = new Node(), tail = new Node();

        /**
         * Initialize your data structure here.
         */
        public AllOne() {
            head.next = tail;
            tail.prev = head;
        }

        /**
         * Inserts a new key <Key> with value 1. Or increments an existing key by 1.
         */
        public void inc(String key) {
            int count = keys.getOrDefault(key, 0);
            Node oldNode = counts.get(count);
            Node newNode = counts.get(count + 1);
            if (newNode == null) {
                Node prev = oldNode == null ? head : oldNode;
                newNode = new Node();
                prev.next.prev = newNode;
                newNode.next = prev.next;
                prev.next = newNode;
                newNode.prev = prev;
                counts.put(count + 1, newNode);
            }
            newNode.keys.add(key);
            keys.put(key, count + 1);

            if (oldNode != null) {
                oldNode.keys.remove(key);
                if (oldNode.keys.isEmpty()) {
                    oldNode.prev.next = oldNode.next;
                    oldNode.next.prev = oldNode.prev;
                    counts.remove(count);
                }
            }
        }

        /**
         * Decrements an existing key by 1. If Key's value is 1, remove it from the data structure.
         */
        public void dec(String key) {
            int count = keys.getOrDefault(key, 0);
            if (count == 0) {
                return;
            }
            Node oldNode = counts.get(count);
            if (count > 1) {
                Node newNode = counts.get(count - 1);
                if (newNode == null) {
                    newNode = new Node();
                    oldNode.prev.next = newNode;
                    newNode.prev = oldNode.prev;
                    oldNode.prev = newNode;
                    newNode.next = oldNode;
                    counts.put(count - 1, newNode);
                }
                newNode.keys.add(key);
                keys.put(key, count - 1);
            } else {
                keys.remove(key);
            }

            oldNode.keys.remove(key);
            if (oldNode.keys.isEmpty()) {
                oldNode.prev.next = oldNode.next;
                oldNode.next.prev = oldNode.prev;
                counts.remove(count);
            }
        }

        /**
         * Returns one of the keys with maximal value.
         */
        public String getMaxKey() {
            return tail.prev == head ? "" : tail.prev.keys.iterator().next();
        }

        /**
         * Returns one of the keys with Minimal value.
         */
        public String getMinKey() {
            return head.next == tail ? "" : head.next.keys.iterator().next();
        }
    }

    @Test
    public void testMethod() {
        AllOne obj = new AllOne();
        obj.inc("key");
        obj.dec("key");
        Assert.assertEquals("", obj.getMaxKey());
        Assert.assertEquals("", obj.getMaxKey());
    }

}
