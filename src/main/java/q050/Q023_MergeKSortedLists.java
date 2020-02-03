package q050;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/merge-k-sorted-lists/
 *
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
 *
 * Example:
 *
 * Input:
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * Output: 1->1->2->3->4->4->5->6
 */
@RunWith(LeetCodeRunner.class)
public class Q023_MergeKSortedLists {

    /**
     * 这道题目还要求分析复杂度, 空间复杂度为 O(1), 没有和ListNode 元素数量相关的对象创建.
     * 时间复杂度为 O(kN), k 表示数组长度, N 表示链表中元素的数量, 因为第1, 2 个数组中的元素需要比较
     * lists.length - 1 次, 第 3 个数组中的元素的比较次数比第2 次少1 次, 最后一个数组中的元素只要比
     * 较1 次, 所以对于最坏情况, 最多比较次数的第1 个链表来说, 时间复杂度就是 元素的长度 * 数组的长度.
     */
    @Answer
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        ListNode node = lists[0];
        ListNode dummy = new ListNode(0);
        // LeetCode 中最快的合并方式是两两合并.
        for (int i = 1; i < lists.length; i++) {
            node = merge2LinkedList(dummy, node, lists[i]);
        }
        return node;
    }

    private ListNode merge2LinkedList(ListNode dummy, ListNode list1, ListNode list2) {
        ListNode node = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                node.next = list1;
                node = list1;
                list1 = list1.next;
            } else {
                node.next = list2;
                node = list2;
                list2 = list2.next;
            }
        }
        node.next = list1 == null ? list2 : list1;
        return dummy.next;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new ListNode[]{
                            ListNode.createListNode(1, 4, 5),
                            ListNode.createListNode(1, 3, 4),
                            ListNode.createListNode(2, 6)
                    }
            ).expect(ListNode.createListNode(1, 1, 2, 3, 4, 4, 5, 6))
            .build();
}
