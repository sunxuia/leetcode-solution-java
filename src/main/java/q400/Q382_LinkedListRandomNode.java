package q400;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import util.provided.ListNode;

/**
 * https://leetcode.com/problems/linked-list-random-node/
 *
 * Given a singly linked list, return a random node's value from the linked list. Each node must have the same
 * probability of being chosen.
 *
 * Follow up:
 * What if the linked list is extremely large and its length is unknown to you? Could you solve this efficiently
 * without using extra space?
 *
 * Example:
 *
 * // Init a singly linked list [1,2,3].
 * ListNode head = new ListNode(1);
 * head.next = new ListNode(2);
 * head.next.next = new ListNode(3);
 * Solution solution = new Solution(head);
 *
 * // getRandom() should return either 1, 2, or 3 randomly. Each element should have equal probability of returning.
 * solution.getRandom();
 */
public class Q382_LinkedListRandomNode {

    /**
     * 参考 https://www.cnblogs.com/grandyang/p/5759926.html
     *
     * 这题用到了水塘抽样的算法:
     * https://zh.wikipedia.org/wiki/%E6%B0%B4%E5%A1%98%E6%8A%BD%E6%A8%A3
     * 水塘算法可以从包含n个项目的集合S 中选取k 个样本, 其中n 未知或很大.
     * 水塘算法首先从S 中选择前k 项放到水塘中,
     * 对于S 中剩下的, 对于每一个S[j] ( j >= k ) 项:
     * 随机产生一个 [0, j) 的整数r, 如果r < k, 则把水塘中的第r 项替换为S[j]
     * 这样一直循环到S 被全部遍历为止.
     *
     * 对应到这题, 就是从一个包含未知个项目的链表集合S 中选取1 个样本.
     */
    private static class Solution {

        private ListNode head;

        /**
         * @param head The linked list's head.
         * Note that the head is guaranteed to be not null, so it contains at least one node.
         */
        public Solution(ListNode head) {
            this.head = head;
        }

        /**
         * Returns a random node's value.
         */
        public int getRandom() {
            // 水塘. 首先填满水塘(head 的值)
            int res = head.val;

            // 遍历S 中剩下的元素
            Random random = new Random();
            int i = 2;
            ListNode cur = head.next;
            // cur != null, 即表示水塘是整个链表范围.
            while (cur != null) {
                // 随机产生一个 [0, i) 的整数
                int j = random.nextInt(i);
                // 如果 j < k, 即 j == 0
                if (j == 0) {
                    res = cur.val;
                }
                i++;
                cur = cur.next;
            }
            return res;
        }
    }

    @Test
    public void testMethod() {
        // Init a singly linked list [1,2,3].
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);

        Solution solution = new Solution(head);

        // getRandom() should return either 1, 2, or 3 randomly.
        // Each element should have equal probability of returning.
        int res = solution.getRandom();
        Assert.assertTrue(res == 1 || res == 2 || res == 3);
    }

}
