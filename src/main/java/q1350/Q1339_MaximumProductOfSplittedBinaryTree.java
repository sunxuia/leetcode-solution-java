package q1350;

import org.junit.runner.RunWith;
import util.generator.JsonParser;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1339. Maximum Product of Splitted Binary Tree
 * https://leetcode.com/problems/maximum-product-of-splitted-binary-tree/
 *
 * Given a binary tree root. Split the binary tree into two subtrees by removing 1 edge such that the product of the
 * sums of the subtrees are maximized.
 *
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * Example 1:
 * <img src="./Q1339_PIC1.png">
 * Input: root = [1,2,3,4,5,6]
 * Output: 110
 * Explanation: Remove the red edge and get 2 binary trees with sum 11 and 10. Their product is 110 (11*10)
 *
 * Example 2:
 * <img src="./Q1339_PIC2.png">
 * Input: root = [1,null,2,3,4,null,null,5,6]
 * Output: 90
 * Explanation:  Remove the red edge and get 2 binary trees with sum 15 and 6.Their product is 90 (15*6)
 *
 * Example 3:
 *
 * Input: root = [2,3,9,10,7,8,6,5,4,11,1]
 * Output: 1025
 *
 * Example 4:
 *
 * Input: root = [1,1]
 * Output: 1
 *
 * Constraints:
 *
 * Each tree has at most 50000 nodes and at least 2 nodes.
 * Each node's value is between [1, 10000].
 */
@RunWith(LeetCodeRunner.class)
public class Q1339_MaximumProductOfSplittedBinaryTree {

    @Answer
    public int maxProduct(TreeNode root) {
        SumNode rootSum = buildSumNode(root);
        sum = rootSum.sum;
        dfs(rootSum);
        return (int) (res % MOD);
    }

    private long sum, res;

    private final long MOD = 10_0000_0007L;

    private static class SumNode {

        long sum;

        SumNode left, right;
    }

    private SumNode buildSumNode(TreeNode node) {
        if (node == null) {
            return null;
        }
        SumNode sn = new SumNode();
        sn.left = buildSumNode(node.left);
        sn.right = buildSumNode(node.right);
        sn.sum = node.val + (sn.left == null ? 0 : sn.left.sum)
                + (sn.right == null ? 0 : sn.right.sum);
        return sn;
    }

    private void dfs(SumNode sn) {
        if (sn == null) {
            return;
        }
        res = Math.max(res, (sum - sn.sum) * sn.sum);
        dfs(sn.left);
        dfs(sn.right);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(1, 2, 3, 4, 5, 6))
            .expect(110);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(1, null, 2, 3, 4, null, null, 5, 6))
            .expect(90);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(TreeNode.createByLevel(2, 3, 9, 10, 7, 8, 6, 5, 4, 11, 1))
            .expect(1025);

    @TestData
    public DataExpectation example4 = DataExpectation
            .create(TreeNode.createByLevel(1, 1))
            .expect(1);

    /**
     * 这个用于测试 int 运算的overflow 问题.
     */
    @TestData
    public DataExpectation normal1() {
        String str = TestDataFileHelper.readString("Q1339_TestData").get();
        Integer[] args = JsonParser.parseJsonToList(str).stream()
                .map(i -> i == null ? null : ((Long) i).intValue())
                .toArray(Integer[]::new);
        return DataExpectation.create(TreeNode.createByLevel(args)).expect(763478770);
    }

}
