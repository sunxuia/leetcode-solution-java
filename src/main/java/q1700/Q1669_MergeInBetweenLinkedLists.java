package q1700;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1669. Merge In Between Linked Lists
 * https://leetcode.com/problems/merge-in-between-linked-lists/
 *
 * You are given two linked lists: list1 and list2 of sizes n and m respectively.
 *
 * Remove list1's nodes from the ath node to the bth node, and put list2 in their place.
 *
 * The blue edges and nodes in the following figure incidate the result:
 * <img src="./Q1669_PIC1.png">
 *
 * Build the result list and return its head.
 *
 * Example 1:
 * <img src="./Q1669_PIC2.png">
 * Input: list1 = [0,1,2,3,4,5], a = 3, b = 4, list2 = [1000000,1000001,1000002]
 * Output: [0,1,2,1000000,1000001,1000002,5]
 * Explanation: We remove the nodes 3 and 4 and put the entire list2 in their place. The blue edges and nodes in the
 * above figure indicate the result.
 *
 * Example 2:
 * <img src="./Q1669_PIC3.png">
 * Input: list1 = [0,1,2,3,4,5,6], a = 2, b = 5, list2 = [1000000,1000001,1000002,1000003,1000004]
 * Output: [0,1,1000000,1000001,1000002,1000003,1000004,6]
 * Explanation: The blue edges and nodes in the above figure indicate the result.
 *
 * Constraints:
 *
 * 3 <= list1.length <= 10^4
 * 1 <= a <= b < list1.length - 1
 * 1 <= list2.length <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1669_MergeInBetweenLinkedLists {

    @Answer
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode start = list1;
        int i = 1;
        while (i < a) {
            start = start.next;
            i++;
        }
        ListNode end = start.next;
        while (i < b) {
            end = end.next;
            i++;
        }
        start.next = list2;
        while (list2.next != null) {
            list2 = list2.next;
        }
        list2.next = end.next;
        return list1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(
            ListNode.createListNode(0, 1, 2, 3, 4, 5), 3, 4,
            ListNode.createListNode(1000000, 1000001, 1000002)
    ).expect(ListNode.createListNode(0, 1, 2, 1000000, 1000001, 1000002, 5));

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(
            ListNode.createListNode(0, 1, 2, 3, 4, 5, 6), 2, 5,
            ListNode.createListNode(1000000, 1000001, 1000002, 1000003, 1000004)
    ).expect(ListNode.createListNode(0, 1, 1000000, 1000001, 1000002, 1000003, 1000004, 6));

}
