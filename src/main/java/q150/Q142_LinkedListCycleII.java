package q150;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/linked-list-cycle-ii/
 *
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
 *
 * To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in
 * the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.
 *
 * Note: Do not modify the linked list.
 *
 *
 *
 * Example 1:
 *
 * Input: head = [3,2,0,-4], pos = 1
 * Output: tail connects to node index 1
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 *
 * (见图 Q141_PIC1.png)
 *
 * Example 2:
 *
 * Input: head = [1,2], pos = 0
 * Output: tail connects to node index 0
 * Explanation: There is a cycle in the linked list, where tail connects to the first node.
 *
 * (见图 Q141_PIC2.png)
 *
 * Example 3:
 *
 * Input: head = [1], pos = -1
 * Output: no cycle
 * Explanation: There is no cycle in the linked list.
 *
 * (见图 Q141_PIC3.png)
 *
 * Follow-up:
 * Can you solve it without using extra space?
 */
@RunWith(LeetCodeRunner.class)
public class Q142_LinkedListCycleII {

    /**
     * LeetCode 上的解法:
     * {@see
     * <a href="https://leetcode-cn.com/problems/linked-list-cycle-ii/solution/linked-list-cycle-ii-kuai-man-zhi-zhen-shuang-zhi-/"
     * />}
     */
    @Answer
    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head, fast = head;
        do {
            slow = slow.next;
            fast = fast.next == null ? null : fast.next.next;
        } while (fast != null && slow != fast);
        if (fast == null) {
            return null;
        }
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    // 结合上题一个一个试过去的方式, 这是第2 个想出来的解法, 空间复杂度做到了 O(1), 但是还是慢.
    @Answer
    public ListNode detectCycle_cycleDetect(ListNode head) {
        if (head == null || !hasCycle(head)) {
            return null;
        }
        while (head.next != null) {
            ListNode next = head.next;
            head.next = null;
            if (hasCycle(next)) {
                head.next = next;
                head = next;
            } else {
                head.next = next;
                return head;
            }
        }
        return null;
    }

    private boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head.next;
        while (fast != null && slow != fast) {
            slow = slow.next;
            fast = fast.next == null ? null : fast.next.next;
        }
        return fast != null;
    }

    // 使用额外空间的解法, 这是第1 个想出来的方法
    @Answer
    public ListNode detectCycle_extraSpace(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.add(head)) {
                head = head.next;
            } else {
                return head;
            }
        }
        return null;
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
        return DataExpectation.create(n1).expect(n2);
    }

    @TestData
    public DataExpectation example2() {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        n1.next = n2;
        n2.next = n1;
        return DataExpectation.create(n1).expect(n1);
    }

    @TestData
    public DataExpectation example3() {
        ListNode n1 = new ListNode(1);
        return DataExpectation.create(n1).expect(null);
    }

    @TestData
    public DataExpectation border() {
        return DataExpectation.create(null).expect(null);
    }

    @TestData
    public DataExpectation normal1() {
        return DataExpectation.create(ListNode.createListNode(1, 2)).expect(null);
    }

}
