package q150;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/insertion-sort-list/
 *
 * Sort a linked list using insertion sort.
 *
 * (图见 Q147_PIC.gif, 动图)
 *
 * A graphical example of insertion sort. The partial sorted list (black) initially contains only the first element
 * in the list.
 * With each iteration one element (red) is removed from the input data and inserted in-place into the sorted list
 *
 *
 * Algorithm of Insertion Sort:
 *
 * 1. Insertion sort iterates, consuming one input element each repetition, and growing a sorted output list.
 * 2. At each iteration, insertion sort removes one element from the input data, finds the location it belongs
 * within the sorted list, and inserts it there.
 * 3. It repeats until no input elements remain.
 *
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
public class Q147_InsertionSortList {

    @Answer
    public ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = head;
        for (ListNode prev = dummy, cur = head; cur != null; cur = prev.next) {
            ListNode insert = dummy;
            if (prev.val <= cur.val) {
                // 这个判断可以让程序少很多下面while 的无效循环
                prev = cur;
            } else {
                while (insert.next.val < cur.val) {
                    insert = insert.next;
                }
                prev.next = cur.next;
                cur.next = insert.next;
                insert.next = cur;
            }
        }
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
