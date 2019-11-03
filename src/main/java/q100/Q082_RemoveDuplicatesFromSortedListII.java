package q100;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
 *
 * Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the
 * original list.
 *
 * Example 1:
 *
 * Input: 1->2->3->3->4->4->5
 * Output: 1->2->5
 *
 * Example 2:
 *
 * Input: 1->1->1->2->3
 * Output: 2->3
 */
@RunWith(LeetCodeRunner.class)
public class Q082_RemoveDuplicatesFromSortedListII {

    // 常规解法
    @Answer
    public ListNode normal(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        while (prev.next != null) {
            ListNode next = prev.next.next;
            while (next != null && prev.next.val == next.val) {
                next = next.next;
            }
            if (prev.next.next == next) {
                prev = prev.next;
            } else {
                prev.next = next;
            }
        }
        return dummy.next;
    }

    // dfs
    @Answer
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        if (head.val == head.next.val) {
            while (head.next != null && head.val == head.next.val) {
                head = head.next;
            }
            return deleteDuplicates(head.next);
        } else {
            head.next = deleteDuplicates(head.next);
            return head;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(ListNode.createListNode(1, 2, 3, 3, 4, 4, 5))
            .expect(ListNode.createListNode(1, 2, 5));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(ListNode.createListNode(1, 1, 1, 2, 3))
            .expect(ListNode.createListNode(2, 3));

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(ListNode.createListNode(1, 1, 1))
            .expect(ListNode.createListNode());


    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(ListNode.createListNode(1, 1, 1, 2, 2))
            .expect(ListNode.createListNode());

}
