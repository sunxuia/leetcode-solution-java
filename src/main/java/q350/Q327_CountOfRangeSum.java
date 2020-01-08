package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/count-of-range-sum/
 *
 * Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
 * Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.
 *
 * Note:
 * A naive algorithm of O(n2) is trivial. You MUST do better than that.
 *
 * Example:
 *
 * Input: nums = [-2,5,-1], lower = -2, upper = 2,
 * Output: 3
 * Explanation: The three ranges are : [0,0], [2,2], [0,2] and their respective sums are: -2, -1, 2.
 *
 * 题解: 从nums 中找出范围  下标 a, b, 让数组 [a, b] 对应的元素之和在 [lower, upper] 之间. 找出符合条件的a,b 组合数.
 * 要求时间复杂度小于 O(n^2)
 */
@RunWith(LeetCodeRunner.class)
public class Q327_CountOfRangeSum {

    /**
     * 时间复杂度的要求就是在暗示是 O(nlogn).
     * 这个解法参考了网上的解法 https://www.cnblogs.com/EdwardLiu/p/5138198.html 比较好理解的一个解法
     */
    @Answer
    public int countRangeSum(int[] nums, int lower, int upper) {
        final int len = nums.length;
        if (len == 0) {
            return 0;
        }
        long[] sums = new long[len + 1];
        for (int i = 0; i < len; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }
        TreeNode root = new TreeNode(sums[0]);
        int count = 0;
        for (int i = 1; i <= len; i++) {
            // lower < sum[i] - x < upper,  其中x 的可取值数量.
            count += rangeSize(root, sums[i] - upper, sums[i] - lower);
            insert(root, sums[i]);
        }
        return count;
    }

    // 二叉搜索树
    private class TreeNode {
        // 值
        long val = 0;
        // 等于val 的值的个数
        int count = 1;
        // 左子节点数
        int leftSize = 0;
        // 右子节点数
        int rightSize = 0;
        // 左子节点
        TreeNode left = null;
        // 右子节点
        TreeNode right = null;

        public TreeNode(long v) {
            this.val = v;
        }
    }

    // 插入新的值(val), node 是要插入的节点, 可能会放在node 或node 的左右节点上.
    private TreeNode insert(TreeNode node, long val) {
        if (node == null) {
            return new TreeNode(val);
        } else if (node.val == val) {
            node.count++;
        } else if (val < node.val) {
            node.leftSize++;
            node.left = insert(node.left, val);
        } else {
            node.rightSize++;
            node.right = insert(node.right, val);
        }
        return node;
    }

    // 计算小于val 的值的个数
    private int countSmaller(TreeNode node, long val) {
        if (node == null) {
            return 0;
        } else if (node.val == val) {
            return node.leftSize;
        } else if (node.val > val) {
            return countSmaller(node.left, val);
        } else {
            return node.leftSize + node.count + countSmaller(node.right, val);
        }
    }

    // 计算大于val 的值的个数
    private int countLarger(TreeNode node, long val) {
        if (node == null) {
            return 0;
        } else if (node.val == val) {
            return node.rightSize;
        } else if (node.val < val) {
            return countLarger(node.right, val);
        } else {
            return countLarger(node.left, val) + node.count + node.rightSize;
        }
    }

    // 计算范围在 [lower, upper] 的值.
    private int rangeSize(TreeNode root, long lower, long upper) {
        // 总数
        int total = root.count + root.leftSize + root.rightSize;
        // < lower 的值排除
        int smaller = countSmaller(root, lower);
        // > uppeer 的值排除
        int larger = countLarger(root, upper);
        return total - smaller - larger;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{-2, 5, -1}, -2, 2).expect(3);

}
