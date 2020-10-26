package q1250;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

/**
 * [Hard] 1206. Design Skiplist
 * https://leetcode.com/problems/design-skiplist/
 *
 * Design a Skiplist without using any built-in libraries.
 *
 * A Skiplist is a data structure that takes O(log(n)) time to add, erase and search. Comparing with treap and red-black
 * tree which has the same function and performance, the code length of Skiplist can be comparatively short and the idea
 * behind Skiplists are just simple linked lists.
 *
 * For example: we have a Skiplist containing [30,40,50,60,70,90] and we want to add 80 and 45 into it. The Skiplist
 * works this way:
 * <img src="Q1206_PIC.gif">
 * Artyom Kalinin [CC BY-SA 3.0], via Wikimedia Commons
 *
 * You can see there are many layers in the Skiplist. Each layer is a sorted linked list. With the help of the top
 * layers, add , erase and search can be faster than O(n). It can be proven that the average time complexity for each
 * operation is O(log(n)) and space complexity is O(n).
 *
 * To be specific, your design should include these functions:
 *
 * bool search(int target) : Return whether the target exists in the Skiplist or not.
 * void add(int num): Insert a value into the SkipList.
 * bool erase(int num): Remove a value in the Skiplist. If num does not exist in the Skiplist, do nothing and return
 * false. If there exists multiple num values, removing any one of them is fine.
 *
 * See more about Skiplist : https://en.wikipedia.org/wiki/Skip_list
 *
 * Note that duplicates may exist in the Skiplist, your code needs to handle this situation.
 *
 * Example:
 *
 * Skiplist skiplist = new Skiplist();
 *
 * skiplist.add(1);
 * skiplist.add(2);
 * skiplist.add(3);
 * skiplist.search(0);   // return false.
 * skiplist.add(4);
 * skiplist.search(1);   // return true.
 * skiplist.erase(0);    // return false, 0 is not in skiplist.
 * skiplist.erase(1);    // return true.
 * skiplist.search(1);   // return false, 1 has already been erased.
 *
 * Constraints:
 *
 * 0 <= num, target <= 20000
 * At most 50000 calls will be made to search, add, and erase.
 */
public class Q1206_DesignSkiplist {

    private static class Skiplist {

        /**
         * 跳表的深度, 这个在LeetCode 中调成10 以上就没多少速度提升了
         */
        static final int DEPTH = 4;

        static class Node {

            public Node(int value) {
                this.value = value;
            }

            final int value;

            Node next, lower;

            @Override
            public String toString() {
                return String.valueOf(value);
            }
        }

        Node[] levels = new Node[DEPTH];

        Random random = new Random(100);

        public Skiplist() {
            // 哨兵
            levels[0] = new Node(-1);
            for (int i = 1; i < DEPTH; i++) {
                levels[i] = new Node(-1);
                levels[i - 1].lower = levels[i];
            }
        }

        public boolean search(int target) {
            Node node = levels[0];
            while (node != null && node.value < target) {
                if (node.next != null && node.next.value <= target) {
                    node = node.next;
                } else {
                    node = node.lower;
                }
            }
            return node != null && node.value == target;
        }

        public void add(int num) {
            Node[] nodes = findBeforeNodes(num);
            Node lower = null;
            int i = DEPTH - 1;
            do {
                Node newNode = new Node(num);
                newNode.lower = lower;
                newNode.next = nodes[i].next;
                nodes[i].next = newNode;
                lower = newNode;
                i--;
            } while (i >= 0 && random.nextDouble() < 0.5);
        }

        private Node[] findBeforeNodes(int num) {
            Node[] res = new Node[DEPTH];
            Node node = levels[0];
            for (int i = 0; i < DEPTH; i++) {
                while (node.next != null && node.next.value < num) {
                    node = node.next;
                }
                res[i] = node;
                node = node.lower;
            }
            return res;
        }

        public boolean erase(int num) {
            Node[] nodes = findBeforeNodes(num);
            Node node = nodes[DEPTH - 1].next;
            if (node == null || node.value != num) {
                return false;
            }
            int i = DEPTH - 1;
            Node lower;
            do {
                nodes[i].next = node.next;
                lower = node;
                node = i == 0 ? null : nodes[--i].next;
            } while (node != null && node.lower == lower);
            return true;
        }
    }

    /**
     * Your Skiplist object will be instantiated and called as such:
     * Skiplist obj = new Skiplist();
     * boolean param_1 = obj.search(target);
     * obj.add(num);
     * boolean param_3 = obj.erase(num);
     */
    @Test
    public void testMethod() {
        Skiplist skiplist = new Skiplist();
        skiplist.add(1);
        skiplist.add(2);
        skiplist.add(3);
        Assert.assertFalse(skiplist.search(0));
        skiplist.add(4);
        Assert.assertTrue(skiplist.search(1));
        Assert.assertFalse(skiplist.erase(0));
        Assert.assertTrue(skiplist.erase(1));
        Assert.assertFalse(skiplist.search(1));
        Assert.assertTrue(skiplist.erase(2));
        Assert.assertFalse(skiplist.search(2));
    }

}
