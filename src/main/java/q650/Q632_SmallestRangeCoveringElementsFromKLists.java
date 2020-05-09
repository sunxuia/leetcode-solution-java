package q650;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/
 *
 * You have k lists of sorted integers in ascending order. Find the smallest range that includes at least one number
 * from each of the k lists.
 *
 * We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c if b-a == d-c.
 *
 *
 *
 * Example 1:
 *
 * Input: [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
 * Output: [20,24]
 * Explanation:
 * List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
 * List 2: [0, 9, 12, 20], 20 is in range [20,24].
 * List 3: [5, 18, 22, 30], 22 is in range [20,24].
 *
 *
 *
 * Note:
 *
 * 1. The given list may contain duplicates, so ascending order means >= here.
 * 2. 1 <= k <= 3500
 * 3. -10^5 <= value of elements <= 10^5.
 */
@RunWith(LeetCodeRunner.class)
public class Q632_SmallestRangeCoveringElementsFromKLists {

    /**
     * 使用优先队列, 队列中保存了nums 中的一个元素, 每次取出最小值, 这样就获得了队列中的最大值和最小值,
     * 然后把最小值对应的下一个元素入队, 这样队列中又有所有的元素了.
     * 时间复杂度 O(NlogN)
     */
    @Answer
    public int[] smallestRange(List<List<Integer>> nums) {
        // 在队列中的list 的元素的下标
        int[] index = new int[nums.size()];
        // 确保队列中始终有nums 中所有list 的一个元素.
        PriorityQueue<Integer> pq = new PriorityQueue<>(nums.size(),
                Comparator.comparingInt(a -> nums.get(a).get(index[a])));
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.size(); i++) {
            pq.add(i);
            max = Math.max(max, nums.get(i).get(0));
        }
        int start = -10_0000, end = max;
        while (!pq.isEmpty()) {
            int i = pq.remove();
            int min = nums.get(i).get(index[i]);
            if (end - start > max - min) {
                start = min;
                end = max;
            }
            index[i]++;
            // 这个数组已经结束了, 队列中将缺少一个元素.
            if (index[i] == nums.get(i).size()) {
                break;
            }
            max = Math.max(max, nums.get(i).get(index[i]));
            pq.add(i);
        }
        return new int[]{start, end};
    }

    /**
     * LeetCode 上最快的方式.
     * 通过归并排序将nums 合并为一个长链表, 然后遍历链表, 统计nums 中对应 list
     * 的数量, 当所有list 都全了的时候就可以得到起始和结束的值了.
     * 时间复杂度 O(N)
     */
    @Answer
    public int[] smallestRange2(List<List<Integer>> nums) {
        // 将nums 通过归并排序转换为一个链表
        List<Node> lists = toListNodeLists(nums);
        Node node = mergeKLists(lists);

        int k = lists.size();
        int range = Integer.MAX_VALUE;
        int[] res = new int[2];
        int[] map = new int[k + 1];
        int count = 0;
        Node left = node;
        Node right = node;

        while (right != null) {
            int rightK = right.k;
            map[rightK]++;
            if (map[rightK] == 1) {
                count++;
            }
            while (map[left.k] > 1) {
                map[left.k]--;
                left = left.next;
            }
            if (count == k) {
                if (right.val - left.val < range) {
                    range = right.val - left.val;
                    res[0] = left.val;
                    res[1] = right.val;
                }
            }
            right = right.next;
        }
        return res;
    }

    // 将nums 转换为 Node 链条的List
    private List<Node> toListNodeLists(List<List<Integer>> nums) {
        List<Node> res = new ArrayList<>();
        for (int i = 0; i < nums.size(); i++) {
            res.add(toListNode(nums.get(i), i + 1));
        }
        return res;
    }

    private Node toListNode(List<Integer> nums, int k) {
        Node preHead = new Node(0, 0);
        Node curr = preHead;
        for (int num : nums) {
            curr.next = new Node(num, k);
            curr = curr.next;
        }
        return preHead.next;
    }

    // 将Node 的List 合并为一个Node 链条
    private Node mergeKLists(List<Node> lists) {
        Queue<Node> queue = new LinkedList<>();
        for (Node list : lists) {
            queue.offer(list);
        }
        while (queue.size() > 1) {
            queue.offer(mergeTwoLists(queue.poll(), queue.poll()));
        }
        return queue.poll();
    }

    private Node mergeTwoLists(Node l1, Node l2) {
        Node preHead = new Node(0, 0);
        Node curr = preHead;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        if (l1 == null) {
            curr.next = l2;
        } else {
            curr.next = l1;
        }
        return preHead.next;
    }

    public class Node {

        // 元素值
        int val;
        // 所在nums 中List 的下标 + 1
        int k;
        // 下一个元素
        Node next;

        Node(int val, int k) {
            this.val = val;
            this.k = k;
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.create(Arrays.asList(
            Arrays.asList(4, 10, 15, 24, 26),
            Arrays.asList(0, 9, 12, 20),
            Arrays.asList(5, 18, 22, 30)
    )).expect(new int[]{20, 24});

    @TestData
    public DataExpectation normal1 = DataExpectation.create(Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(1, 2, 3),
            Arrays.asList(1, 2, 3)
    )).expect(new int[]{1, 1});

}
