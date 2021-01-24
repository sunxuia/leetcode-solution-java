package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1569. Number of Ways to Reorder Array to Get Same BST
 * https://leetcode.com/problems/number-of-ways-to-reorder-array-to-get-same-bst/
 *
 * Given an array nums that represents a permutation of integers from 1 to n. We are going to construct a binary search
 * tree (BST) by inserting the elements of nums in order into an initially empty BST. Find the number of different ways
 * to reorder nums so that the constructed BST is identical to that formed from the original array nums.
 *
 * For example, given nums = [2,1,3], we will have 2 as the root, 1 as a left child, and 3 as a right child. The array
 * [2,3,1] also yields the same BST but [3,2,1] yields a different BST.
 *
 * Return the number of ways to reorder nums such that the BST formed is identical to the original BST formed from
 * nums.
 *
 * Since the answer may be very large, return it modulo 10^9 + 7.
 *
 * Example 1:
 * <img src="./!1569_PIC1.png">
 * Input: nums = [2,1,3]
 * Output: 1
 * Explanation: We can reorder nums to be [2,3,1] which will yield the same BST. There are no other ways to reorder nums
 * which will yield the same BST.
 *
 * Example 2:
 * <img src="./!1569_PIC2.png">
 * Input: nums = [3,4,5,1,2]
 * Output: 5
 * Explanation: The following 5 arrays will yield the same BST:
 * [3,1,2,4,5]
 * [3,1,4,2,5]
 * [3,1,4,5,2]
 * [3,4,1,2,5]
 * [3,4,1,5,2]
 *
 * Example 3:
 * <img src="./!1569_PIC3.png">
 * Input: nums = [1,2,3]
 * Output: 0
 * Explanation: There are no other orderings of nums that will yield the same BST.
 *
 * Example 4:
 * <img src="./!1569_PIC4.png">
 * Input: nums = [3,1,2,5,4,6]
 * Output: 19
 *
 * Example 5:
 *
 * Input: nums = [9,4,2,1,3,6,5,7,8,14,11,10,12,13,16,15,17,18]
 * Output: 216212978
 * Explanation: The number of ways to reorder nums to get the same BST is 3216212999. Taking this number modulo 10^9 + 7
 * gives 216212978.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 1000
 * 1 <= nums[i] <= nums.length
 * All integers in nums are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1569_NumberOfWaysToReorderArrayToGetSameBst {

    /**
     * @formatter:off
     * 参考文档
     * https://leetcode-cn.com/problems/number-of-ways-to-reorder-array-to-get-same-bst/solution/jiang-zi-shu-zu-zhong-xin-pai-xu-de-dao-tong-yi-2/
     * @formatter:on
     */
    @Answer
    public int numOfWays(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return 0;
        }

        long[][] dp = new long[n][n];
        dp[0][0] = 1;
        for (int i = 1; i < n; i++) {
            dp[i][0] = 1;
            for (int j = 1; j < n; j++) {
                dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j]) % MOD;
            }
        }

        TreeNode root = new TreeNode(nums[0]);
        for (int i = 1; i < n; i++) {
            int val = nums[i];
            insert(root, val);
        }

        dfs(root, dp);
        return (root.res - 1 + MOD) % MOD;
    }

    private static final int MOD = 10_0000_0007;

    public void insert(TreeNode root, int value) {
        TreeNode cur = root;
        while (true) {
            ++cur.size;
            if (value < cur.value) {
                if (cur.left == null) {
                    cur.left = new TreeNode(value);
                    return;
                }
                cur = cur.left;
            } else {
                if (cur.right == null) {
                    cur.right = new TreeNode(value);
                    return;
                }
                cur = cur.right;
            }
        }
    }

    public void dfs(TreeNode node, long[][] dp) {
        if (node == null) {
            return;
        }
        dfs(node.left, dp);
        dfs(node.right, dp);
        int lsize = node.left != null ? node.left.size : 0;
        int rsize = node.right != null ? node.right.size : 0;
        int lans = node.left != null ? node.left.res : 1;
        int rans = node.right != null ? node.right.res : 1;
        node.res = (int) (dp[lsize + rsize][lsize] % MOD * lans % MOD * rans % MOD);
    }

    private static class TreeNode {

        TreeNode left;
        TreeNode right;
        final int value;
        int size;
        int res;

        TreeNode(int value) {
            this.value = value;
            this.size = 1;
            this.res = 0;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 1, 3}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 4, 5, 1, 2}).expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3}).expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{3, 1, 2, 5, 4, 6}).expect(19);

    @TestData
    public DataExpectation example5 = DataExpectation
            .create(new int[]{9, 4, 2, 1, 3, 6, 5, 7, 8, 14, 11, 10, 12, 13, 16, 15, 17, 18})
            .expect(216212978);

}
