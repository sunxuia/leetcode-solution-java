package q1550;

import java.util.LinkedList;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1505. Minimum Possible Integer After at Most K Adjacent Swaps On Digits
 * https://leetcode.com/problems/minimum-possible-integer-after-at-most-k-adjacent-swaps-on-digits/
 *
 * Given a string num representing the digits of a very large integer and an integer k.
 *
 * You are allowed to swap any two adjacent digits of the integer at most k times.
 *
 * Return the minimum integer you can obtain also as a string.
 *
 * Example 1:
 * <img src="./Q1505_PIC.png">
 * Input: num = "4321", k = 4
 * Output: "1342"
 * Explanation: The steps to obtain the minimum integer from 4321 with 4 adjacent swaps are shown.
 *
 * Example 2:
 *
 * Input: num = "100", k = 1
 * Output: "010"
 * Explanation: It's ok for the output to have leading zeros, but the input is guaranteed not to have any leading zeros.
 *
 * Example 3:
 *
 * Input: num = "36789", k = 1000
 * Output: "36789"
 * Explanation: We can keep the number without any swaps.
 *
 * Example 4:
 *
 * Input: num = "22", k = 22
 * Output: "22"
 *
 * Example 5:
 *
 * Input: num = "9438957234785635408", k = 23
 * Output: "0345989723478563548"
 *
 * Constraints:
 *
 * 1 <= num.length <= 30000
 * num contains digits only and doesn't have leading zeros.
 * 1 <= k <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1505_MinimumPossibleIntegerAfterAtMostKAdjacentSwapsOnDigits {

    /**
     * 链表的方式, 时间复杂度 O(N^2), 这种解法会超时.
     */
//    @Answer
    public String minInteger_overTime(String num, int k) {
        Node dummy = new Node(), tail = new Node(), prev = dummy;
        for (int i = 0; i < num.length(); i++) {
            Node node = new Node();
            node.val = num.charAt(i) - '0';
            node.idx = i;
            prev.next = node;
            node.prev = prev;
            prev = node;
        }
        prev.next = tail;
        tail.prev = prev;

        Node head = dummy;
        while (k > 0 && head.next != tail) {
            // 找出前k 个里面最小的节点
            Node node = head.next, min = node;
            int times = 0;
            for (int i = 0; i <= k && node != tail; i++) {
                if (min.val > node.val) {
                    min = node;
                    times = i;
                }
                node = node.next;
            }
            // 把这个节点移到前面去
            min.remove();
            head.insertAfter(min);
            head = min;
            k -= times;
        }

        StringBuilder sb = new StringBuilder(num.length());
        for (Node node = dummy.next; node != tail; node = node.next) {
            sb.append(node.val);
        }
        return sb.toString();
    }

    private static class Node {

        int val, idx;

        Node next, prev;

        @Override
        public String toString() {
            return String.format("%d(%d)", val, idx);
        }

        void remove() {
            prev.next = next;
            next.prev = prev;
        }

        void insertAfter(Node node) {
            next.prev = node;
            node.next = next;
            node.prev = this;
            next = node;
        }
    }

    /**
     * 树状数组(Binary Indexed Tree, Fenwick Tree)的解法, 还有另一种线段树的解法.
     * 思路同上, 在k 次操作能覆盖到的范围内, 把最小的数移动到前面.
     *
     * 树状数组可以保证set 和query 的时间复杂度都是 O(logN).
     * @formatter:off
     * 参考文档
     * https://zhuanlan.zhihu.com/p/25185969
     * https://blog.csdn.net/Yaokai_AssultMaster/article/details/79492190
     * https://leetcode-cn.com/problems/minimum-possible-integer-after-at-most-k-adjacent-swaps-on-digits/solution/zui-duo-k-ci-jiao-huan-xiang-lin-shu-wei-hou-de-da/
     * https://leetcode-cn.com/problems/minimum-possible-integer-after-at-most-k-adjacent-swaps-on-digits/solution/xiao-bai-xiang-python-shu-zhuang-shu-zu-fang-fa-xi/
     * @formatter:on
     *
     * 另一个树状数组的题目参见 {@link Q1536_MinimumSwapsToArrangeABinaryGrid}
     */
    @Answer
    public String minInteger(String num, int k) {
        final int n = num.length();
        // 保存数字对应的位置
        Queue<Integer>[] pos = new Queue[10];
        for (int i = 0; i < 10; i++) {
            pos[i] = new LinkedList<>();
        }
        for (int i = 0; i < n; i++) {
            // (树状数组的0 位不能用)
            pos[num.charAt(i) - '0'].offer(i + 1);
        }
        // bit 用来统计前i 项元素中已经被用到元素的总和.
        BinaryIndexedTree bit = new BinaryIndexedTree(n);
        StringBuilder res = new StringBuilder(n);
        // 从前往后遍历每一个位置
        for (int i = 1; i <= n; i++) {
            // 找出下一个要移动的数字
            for (int j = 0; j < 10; j++) {
                if (!pos[j].isEmpty()) {
                    int idx = pos[j].peek();
                    // 这个数字在当前位置后面多少(要移动的距离)
                    int behind = bit.query(idx + 1, n);
                    // 距离
                    int dist = idx + behind - i;
                    if (dist <= k) {
                        pos[j].poll();
                        bit.update(idx);
                        res.append(j);
                        k -= dist;
                        break;
                    }
                }
            }
        }
        return res.toString();
    }

    private static class BinaryIndexedTree {

        private int n;
        private int[] tree;

        public BinaryIndexedTree(int n) {
            this.n = n;
            this.tree = new int[n + 1];
        }

        // 更新第i 个元素, +1
        // 这题中就表示, 对于num[i,] 的元素, 前面被移动过的元素数量+1
        public void update(int i) {
            while (i <= n) {
                tree[i]++;
                i += lowbit(i);
            }
        }

        private int lowbit(int i) {
            return i & -i;
        }

        // 获得前i 项的和 ( [0, i) 的和)
        // 这题中表示num[i] 之前被移动的元素的数量.
        public int query(int i) {
            int res = 0;
            while (i > 0) {
                res += tree[i];
                i -= lowbit(i);
            }
            return res;
        }

        // 查询区间 [x, y] 的元素个数
        public int query(int x, int y) {
            return query(y) - query(x - 1);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("4321", 4).expect("1342");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("100", 1).expect("010");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("36789", 1000).expect("36789");

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("22", 22).expect("22");

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith("9438957234785635408", 23)
            .expect("0345989723478563548");

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("9000900", 3).expect("0009900");

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("294984148179", 11).expect("124498948179");

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith("9989150892151", 5).expect("1989950892151");

}
