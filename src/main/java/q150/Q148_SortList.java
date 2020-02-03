package q150;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/sort-list/
 *
 * Sort a linked list in O(n log n) time using constant space complexity.
 *
 * Example 1:
 *
 * Input: 4->2->1->3
 * Output: 1->2->3->4
 *
 * Example 2:
 *
 * Input: -1->5->3->4->0
 * Output: -1->0->3->4->5
 */
@RunWith(LeetCodeRunner.class)
public class Q148_SortList {

    /**
     * dfs 的归并排序方式, 堆栈做到了 O(1) 的时间复杂度
     */
    @Answer
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode second = sortList(slow.next);
        slow.next = null;
        ListNode first = sortList(head);

        ListNode dummy = new ListNode(0);
        ListNode prev = dummy;
        while (first != null && second != null) {
            if (first.val < second.val) {
                prev.next = first;
                first = first.next;
            } else {
                prev.next = second;
                second = second.next;
            }
            prev = prev.next;
        }
        prev.next = first == null ? second : first;
        return dummy.next;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(ListNode.createListNode(4, 2, 1, 3))
            .expect(ListNode.createListNode(1, 2, 3, 4));

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(ListNode.createListNode(-1, 5, 3, 4, 0))
            .expect(ListNode.createListNode(-1, 0, 3, 4, 5));

    @TestData
    public DataExpectation border1 = DataExpectation.create(null).expect(null);

    @TestData
    public DataExpectation border2 = DataExpectation.create(new ListNode(0)).expect(new ListNode(0));

}
