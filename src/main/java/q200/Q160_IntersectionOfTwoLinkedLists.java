package q200;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/intersection-of-two-linked-lists/
 *
 * Write a program to find the node at which the intersection of two singly linked lists begins.
 *
 * For example, the following two linked lists:
 *
 * (图见Q160_PIC1.png)
 *
 * begin to intersect at node c1.
 *
 *
 *
 * Example 1:
 *
 * (图见Q160_PIC2.png)
 *
 * Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
 * Output: Reference of the node with value = 8
 * Input Explanation: The intersected node's value is 8 (note that this must not be 0 if the two lists intersect).
 * From the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as [5,0,1,8,4,5]. There are 2 nodes
 * before the intersected node in A; There are 3 nodes before the intersected node in B.
 *
 *
 *
 * Example 2:
 *
 * (图见Q160_PIC3.png)
 *
 * Input: intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
 * Output: Reference of the node with value = 2
 * Input Explanation: The intersected node's value is 2 (note that this must not be 0 if the two lists intersect).
 * From the head of A, it reads as [0,9,1,2,4]. From the head of B, it reads as [3,2,4]. There are 3 nodes before the
 * intersected node in A; There are 1 node before the intersected node in B.
 *
 *
 *
 * Example 3:
 *
 * (图见Q160_PIC4.png)
 *
 * Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
 * Output: null
 * Input Explanation: From the head of A, it reads as [2,6,4]. From the head of B, it reads as [1,5]. Since the two
 * lists do not intersect, intersectVal must be 0, while skipA and skipB can be arbitrary values.
 * Explanation: The two lists do not intersect, so return null.
 *
 *
 *
 * Notes:
 *
 * If the two linked lists have no intersection at all, return null.
 * The linked lists must retain their original structure after the function returns.
 * You may assume there are no cycles anywhere in the entire linked structure.
 * Your code should preferably run in O(n) time and use only O(1) memory.
 */
@RunWith(LeetCodeRunner.class)
public class Q160_IntersectionOfTwoLinkedLists {

    /**
     * 题目要求 O(n) 的时间复杂度和 O(1) 的空间复杂度.
     * 解题思路是: 假设汇合点为C, 则 A->C 和 B->C 的路径长度不等, 但是 A->C->B和 B->C->A 的路径是相等的,
     * 从A和B 触发的指针走过这2 个路径之后再次相会的点就是C.
     */
    @Answer
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode na = headA, nb = headB;
        while (na != nb) {
            na = na == null ? headB : na.next;
            nb = nb == null ? headA : nb.next;
        }
        return na;
    }

    @TestData
    public DataExpectation example1 = createTestData(new int[]{4, 1}, new int[]{5, 0, 1}, new int[]{8, 4, 5});

    @TestData
    public DataExpectation example2 = createTestData(new int[]{0, 9, 1}, new int[]{3}, new int[]{2, 4});

    @TestData
    public DataExpectation example3 = createTestData(new int[]{2, 6, 4}, new int[]{1, 5}, new int[]{});

    @TestData
    public DataExpectation border = createTestData(new int[]{}, new int[]{1, 5}, new int[]{});

    @TestData
    public DataExpectation normal1 = createTestData(new int[]{2, 6, 4}, new int[]{1, 5, 1}, new int[]{9});

    private DataExpectation createTestData(int[] a, int[] b, int[] c) {
        ListNode l1 = ListNode.createListNode(a);
        ListNode l2 = ListNode.createListNode(b);
        ListNode l3 = ListNode.createListNode(c);
        connectList(l1, l3);
        connectList(l2, l3);
        return DataExpectation.createWith(l1, l2).expect(l3);
    }

    private void connectList(ListNode prev, ListNode next) {
        if (prev == null) {
            return;
        }
        while (prev.next != null) {
            prev = prev.next;
        }
        prev.next = next;
    }

}
