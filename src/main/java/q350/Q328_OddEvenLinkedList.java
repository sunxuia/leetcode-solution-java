package q350;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/odd-even-linked-list/
 *
 * Given a singly linked list, group all odd nodes together followed by the even nodes. Please note here we are
 * talking about the node number and not the value in the nodes.
 *
 * You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.
 *
 * Example 1:
 *
 * Input: 1->2->3->4->5->NULL
 * Output: 1->3->5->2->4->NULL
 *
 * Example 2:
 *
 * Input: 2->1->3->5->6->4->7->NULL
 * Output: 2->3->6->7->1->5->4->NULL
 *
 * Note:
 *
 * The relative order inside both the even and odd groups should remain as it was in the input.
 * The first node is considered odd, the second node even and so on ...
 */
@RunWith(LeetCodeRunner.class)
public class Q328_OddEvenLinkedList {

    @Answer
    public ListNode oddEvenList(ListNode head) {
        ListNode oddDummy = new ListNode(0), odd = oddDummy;
        ListNode evenDummy = new ListNode(0), even = evenDummy;
        while (head != null) {
            odd.next = head;
            odd = head;
            head = head.next;

            if (head != null) {
                even.next = head;
                even = head;
                head = head.next;
            }
        }
        odd.next = evenDummy.next;
        even.next = null;
        return oddDummy.next;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(ListNode.createListNode(1, 2, 3, 4, 5))
            .expect(ListNode.createListNode(1, 3, 5, 2, 4));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(ListNode.createListNode(2, 1, 3, 5, 6, 4, 7))
            .expect(ListNode.createListNode(2, 3, 6, 7, 1, 5, 4));

}
