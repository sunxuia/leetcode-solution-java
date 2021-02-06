package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1707. Maximum XOR With an Element From Array
 * https://leetcode.com/problems/maximum-xor-with-an-element-from-array/
 *
 * You are given an array nums consisting of non-negative integers. You are also given a queries array, where queries[i]
 * = [xi, mi].
 *
 * The answer to the ith query is the maximum bitwise XOR value of xi and any element of nums that does not exceed mi.
 * In other words, the answer is max(nums[j] XOR xi) for all j such that nums[j] <= mi. If all elements in nums are
 * larger than mi, then the answer is -1.
 *
 * Return an integer array answer where answer.length == queries.length and answer[i] is the answer to the ith query.
 *
 * Example 1:
 *
 * Input: nums = [0,1,2,3,4], queries = [[3,1],[1,3],[5,6]]
 * Output: [3,3,7]
 * Explanation:
 * 1) 0 and 1 are the only two integers not greater than 1. 0 XOR 3 = 3 and 1 XOR 3 = 2. The larger of the two is 3.
 * 2) 1 XOR 2 = 3.
 * 3) 5 XOR 2 = 7.
 *
 * Example 2:
 *
 * Input: nums = [5,2,4,6,6,3], queries = [[12,4],[8,1],[6,3]]
 * Output: [15,-1,5]
 *
 * Constraints:
 *
 * 1 <= nums.length, queries.length <= 10^5
 * queries[i].length == 2
 * 0 <= nums[j], xi, mi <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1707_MaximumXorWithAnElementFromArray {

    /**
     * 根据提示可以使用字典树来做.
     */
    @Answer
    public int[] maximizeXor(int[] nums, int[][] queries) {
        final int n = queries.length;
        Node root = new Node();
        root.digit = 32;
        for (int num : nums) {
            build(root, num, 31);
        }

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            int x = queries[i][0], m = queries[i][1];
            if (root.min > m) {
                res[i] = -1;
                continue;
            }

            int expect = Integer.MAX_VALUE ^ x;
            // 从高位到低位寻找尽可能符合条件的结果.
            Node node = root;
            while (node.digit > 0) {
                int val = expect >>> (node.digit - 1) & 1;
                Node next = node.lower[val];
                if (next == null || next.min > m) {
                    next = node.lower[1 - val];
                }
                node = next;
            }
            res[i] = node.mask ^ x;
        }
        return res;
    }

    private static class Node {

        int digit, mask;
        int max, min = Integer.MAX_VALUE;

        Node[] lower = new Node[2];

    }

    private void build(Node parent, int num, int digit) {
        int val = num >>> digit & 1;
        Node node = parent.lower[val];
        if (node == null) {
            node = new Node();
            node.digit = digit;
            node.mask = parent.mask | (val << digit);
            parent.lower[val] = node;
        }
        if (digit == 0) {
            node.max = node.min = node.mask;
        } else {
            build(node, num, digit - 1);
        }
        parent.max = Math.max(parent.max, node.max);
        parent.min = Math.min(parent.min, node.min);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{0, 1, 2, 3, 4}, new int[][]{{3, 1}, {1, 3}, {5, 6}})
            .expect(new int[]{3, 3, 7});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{5, 2, 4, 6, 6, 3}, new int[][]{{12, 4}, {8, 1}, {6, 3}})
            .expect(new int[]{15, -1, 5});

}
