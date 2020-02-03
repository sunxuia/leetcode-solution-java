package q150;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/reorder-list/
 *
 * Given a singly linked list L: L0→L1→…→Ln-1→Ln,
 * reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
 *
 * You may not modify the values in the list's nodes, only nodes itself may be changed.
 *
 * Example 1:
 *
 * Given 1->2->3->4, reorder it to 1->4->2->3.
 *
 * Example 2:
 *
 * Given 1->2->3->4->5, reorder it to 1->5->2->4->3.
 */
@RunWith(LeetCodeRunner.class)
public class Q143_ReorderList {

    // 这种方式比较慢
    @Answer
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        Deque<ListNode> stack = new ArrayDeque<>();
        for (ListNode node = head; node != null; node = node.next) {
            stack.push(node);
        }
        for (ListNode cur = head, next = head.next; ; cur = next, next = next.next) {
            ListNode insert = stack.pop();
            if (insert == cur || insert == next) {
                insert.next = null;
                break;
            }
            cur.next = insert;
            insert.next = next;
        }
    }

    // 翻转后半部分的方式
    @Answer
    public void reorderList2(ListNode head) {
        int len = getLength(head);
        if (len < 3) {
            return;
        }
        ListNode tail = head;
        for (int i = len / 2; i > 0; i--) {
            tail = tail.next;
        }
        ListNode second = reverse(tail.next);
        tail.next = null;
        while (second != null) {
            ListNode next = head.next;
            head.next = second;
            second = second.next;
            head.next.next = next;
            head = next;
        }
    }

    private int getLength(ListNode node) {
        int len = 0;
        while (node != null) {
            len++;
            node = node.next;
        }
        return len;
    }

    private ListNode reverse(ListNode node) {
        ListNode prev = null;
        while (node != null) {
            ListNode next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }
        return prev;
    }

    // 针对上面找出后半部分的内容的改进
    @Answer
    public void reorderList3(ListNode head) {
        if (head == null) {
            return;
        }

        // 这一步可以保证tail 在中间.
        ListNode tail = head, second = head;
        while (second != null && second.next != null) {
            tail = tail.next;
            second = second.next.next;
        }

        second = reverse(tail.next);
        tail.next = null;
        while (second != null) {
            ListNode next = head.next;
            head.next = second;
            second = second.next;
            head.next.next = next;
            head = next;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(ListNode.createListNode(1, 2, 3, 4))
            .expectArgument(0, ListNode.createListNode(1, 4, 2, 3))
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(ListNode.createListNode(1, 2, 3, 4, 5))
            .expectArgument(0, ListNode.createListNode(1, 5, 2, 4, 3))
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument(null)
            .expectArgument(0, null)
            .build();

    @TestData
    public DataExpectation border2 = DataExpectation.builder()
            .addArgument(ListNode.createListNode(1))
            .expectArgument(0, ListNode.createListNode(1))
            .build();

    @TestData
    public DataExpectation border3 = DataExpectation.builder()
            .addArgument(ListNode.createListNode(1, 2))
            .expectArgument(0, ListNode.createListNode(1, 2))
            .build();

}
