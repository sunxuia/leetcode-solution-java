package q150;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/linked-list-cycle/
 *
 * Given a linked list, determine if it has a cycle in it.
 *
 * To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in
 * the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.
 *
 *
 *
 * Example 1:
 *
 * Input: head = [3,2,0,-4], pos = 1
 * Output: true
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 *
 * (见图 Q141_PIC1.png)
 *
 * Example 2:
 *
 * Input: head = [1,2], pos = 0
 * Output: true
 * Explanation: There is a cycle in the linked list, where tail connects to the first node.
 *
 * (见图 Q141_PIC2.png)
 *
 * Example 3:
 *
 * Input: head = [1], pos = -1
 * Output: false
 * Explanation: There is no cycle in the linked list.
 *
 * (见图 Q141_PIC3.png)
 *
 *
 *
 * Follow up:
 *
 * Can you solve it using O(1) (i.e. constant) memory?
 */
@RunWith(LeetCodeRunner.class)
public class Q141_LinkedListCycle {

    // 题目要求 O(1) 的空间复杂度.
    // 这里slow 每次前进 1 步, fast 每次前进 2 步, 如果有循环, 则fast 一定会在循环中遇到slow 的.
    @Answer
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head, fast = head.next;
        while (fast != null && slow != fast) {
            slow = slow.next;
            fast = fast.next == null ? null : fast.next.next;
        }
        return fast != null;
    }

    @TestData
    public DataExpectation example1() {
        ListNode n1 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(0);
        ListNode n4 = new ListNode(-4);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n2;
        return DataExpectation.create(n1).expect(true);
    }

    @TestData
    public DataExpectation example2() {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        n1.next = n2;
        n2.next = n1;
        return DataExpectation.create(n1).expect(true);
    }

    @TestData
    public DataExpectation example3() {
        ListNode n1 = new ListNode(1);
        return DataExpectation.create(n1).expect(false);
    }

    @TestData
    public DataExpectation border() {
        return DataExpectation.create(null).expect(false);
    }

}
