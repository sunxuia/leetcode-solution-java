package q100;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list/
 *
 * Given a sorted linked list, delete all duplicates such that each element appear only once.
 *
 * Example 1:
 *
 * Input: 1->1->2
 * Output: 1->2
 *
 * Example 2:
 *
 * Input: 1->1->2->3->3
 * Output: 1->2->3
 */
@RunWith(LeetCodeRunner.class)
public class Q083_RemoveDuplicatesFromSortedList {

    // dfs 解法
    @Answer
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        while (head.next != null && head.val == head.next.val) {
            head = head.next;
        }
        head.next = deleteDuplicates(head.next);
        return head;
    }

    // 常规解法
    @Answer
    public ListNode normal(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode prev = head, next = head.next;
        while (next != null) {
            if (next.val == prev.val) {
                next = next.next;
            } else {
                prev = prev.next = next;
            }
        }
        prev.next = null;
        return head;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(ListNode.createListNode(1, 1, 2))
            .expect(ListNode.createListNode(1, 2));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(ListNode.createListNode(1, 1, 2, 3, 3))
            .expect(ListNode.createListNode(1, 2, 3));

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(ListNode.createListNode(1, 1))
            .expect(ListNode.createListNode(1));

}
