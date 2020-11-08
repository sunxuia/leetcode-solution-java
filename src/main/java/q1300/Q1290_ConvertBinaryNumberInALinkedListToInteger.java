package q1300;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1290. Convert Binary Number in a Linked List to Integer
 * https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/
 *
 * Given head which is a reference node to a singly-linked list. The value of each node in the linked list is either 0
 * or 1. The linked list holds the binary representation of a number.
 *
 * Return the decimal value of the number in the linked list.
 *
 * Example 1:
 * <img src="./Q1290_PIC.png">
 * Input: head = [1,0,1]
 * Output: 5
 * Explanation: (101) in base 2 = (5) in base 10
 *
 * Example 2:
 *
 * Input: head = [0]
 * Output: 0
 *
 * Example 3:
 *
 * Input: head = [1]
 * Output: 1
 *
 * Example 4:
 *
 * Input: head = [1,0,0,1,0,0,1,1,1,0,0,0,0,0,0]
 * Output: 18880
 *
 * Example 5:
 *
 * Input: head = [0,0]
 * Output: 0
 *
 * Constraints:
 *
 * The Linked List is not empty.
 * Number of nodes will not exceed 30.
 * Each node's value is either 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1290_ConvertBinaryNumberInALinkedListToInteger {

    @Answer
    public int getDecimalValue(ListNode head) {
        int res = 0;
        while (head != null) {
            res = res * 2 + head.val;
            head = head.next;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(ListNode.createListNode(1, 0, 1)).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(ListNode.createListNode(0)).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(ListNode.createListNode(1)).expect(1);

    @TestData
    public DataExpectation example4 = DataExpectation
            .create(ListNode.createListNode(1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0))
            .expect(18880);

    @TestData
    public DataExpectation example5 = DataExpectation.create(ListNode.createListNode(0, 0)).expect(0);

}
