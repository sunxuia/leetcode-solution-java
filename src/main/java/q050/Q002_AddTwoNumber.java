package q050;

import static util.provided.ListNode.createListNode;

import org.junit.runner.RunWith;
import q450.Q445_AddTwoNumbersII;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse
 * order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Example:
 *
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 *
 * 下一题 {@link Q445_AddTwoNumbersII}
 */
@RunWith(LeetCodeRunner.class)
public class Q002_AddTwoNumber {

    /**
     * 时间复杂度 O(n)
     */
    @Answer
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0), prev = head;
        prev.next = l1;
        int carry = 0;
        while (l1 != null && l2 != null) {
            int total = l1.val + l2.val + carry;
            carry = total / 10;
            l1.val = total % 10;
            prev = l1;
            l1 = l1.next;
            l2 = l2.next;
        }

        while (l1 != null && carry == 1) {
            int total = l1.val + 1;
            carry = total / 10;
            l1.val = total % 10;
            prev = l1;
            l1 = l1.next;
        }
        if (l2 != null) {
            prev.next = l2;
            while (l2 != null && carry == 1) {
                int total = l2.val + 1;
                carry = total / 10;
                l2.val = total % 10;
                prev = l2;
                l2 = l2.next;
            }
        }

        if (carry == 1) {
            head.val = 1;
            ListNode res = head.next;
            head.next = null;
            prev.next = head;
            return res;
        } else {
            return head.next;
        }
    }

    @TestData
    public DataExpectation normalCase = DataExpectation.builder()
            .addArgument(createListNode(2, 4, 3))
            .addArgument(createListNode(5, 6, 4))
            .expect(createListNode(7, 0, 8))
            .assertMethod(ListNode::assertEquals)
            .build();

    @TestData
    public DataExpectation borderCase1 = DataExpectation.builder()
            .addArgument(createListNode(1))
            .addArgument(createListNode(9, 9))
            .expect(createListNode(0, 0, 1))
            .assertMethod(ListNode::assertEquals)
            .build();

    @TestData
    public DataExpectation borderCase2 = DataExpectation.builder()
            .addArgument(createListNode())
            .addArgument(createListNode(1, 2))
            .expect(createListNode(1, 2))
            .assertMethod(ListNode::assertEquals)
            .build();

}
