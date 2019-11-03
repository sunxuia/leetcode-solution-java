package q100;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/partition-list/
 *
 * Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or
 * equal to x.
 *
 * You should preserve the original relative order of the nodes in each of the two partitions.
 *
 * Example:
 *
 * Input: head = 1->4->3->2->5->2, x = 3
 * Output: 1->2->2->4->3->5
 *
 * 题解: 如上面的示例, 题目要求把 < 3 的节点都放到 >= 3 节点的的前面.
 */
@RunWith(LeetCodeRunner.class)
public class Q086_PartitionList {

    @Answer
    public ListNode partition(ListNode head, int x) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode insert = dummy;
        while (insert.next != null && insert.next.val < x) {
            insert = insert.next;
        }
        for (ListNode prev = insert, cur = insert.next; cur != null; prev = cur, cur = cur.next) {
            if (cur.val < x) {
                prev.next = cur.next;
                cur.next = insert.next;
                insert.next = cur;
                insert = cur;
                cur = prev;
            }
        }
        return dummy.next;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(ListNode.createListNode(1, 4, 3, 2, 5, 2), 3)
            .expect(ListNode.createListNode(1, 2, 2, 4, 3, 5));

    @TestData
    public DataExpectation border = DataExpectation
            .createWith(ListNode.createListNode(), 3)
            .expect(ListNode.createListNode());

}
