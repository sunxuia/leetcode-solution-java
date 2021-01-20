package q1550;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1530. Number of Good Leaf Nodes Pairs
 * https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/
 *
 * Given the root of a binary tree and an integer distance. A pair of two different leaf nodes of a binary tree is said
 * to be good if the length of the shortest path between them is less than or equal to distance.
 *
 * Return the number of good leaf node pairs in the tree.
 *
 * Example 1:
 * <img src="./Q1530_PIC1.png">
 * Input: root = [1,2,3,null,4], distance = 3
 * Output: 1
 * Explanation: The leaf nodes of the tree are 3 and 4 and the length of the shortest path between them is 3. This is
 * the only good pair.
 *
 * Example 2:
 * <img src="./Q1530_PIC2.png">
 * Input: root = [1,2,3,4,5,6,7], distance = 3
 * Output: 2
 * Explanation: The good pairs are [4,5] and [6,7] with shortest path = 2. The pair [4,6] is not good because the length
 * of ther shortest path between them is 4.
 *
 * Example 3:
 *
 * Input: root = [7,1,4,6,null,5,3,null,null,null,null,null,2], distance = 3
 * Output: 1
 * Explanation: The only good pair is [2,5].
 *
 * Example 4:
 *
 * Input: root = [100], distance = 1
 * Output: 0
 *
 * Example 5:
 *
 * Input: root = [1,1,1], distance = 2
 * Output: 1
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [1, 2^10].
 * Each node's value is between [1, 100].
 * 1 <= distance <= 10
 */
@RunWith(LeetCodeRunner.class)
public class Q1530_NumberOfGoodLeafNodesPairs {

    @Answer
    public int countPairs(TreeNode root, int distance) {
        return dfs(root, distance)[distance];
    }

    /**
     * res[1:distance-1] 表示 node 中距离这个节点 x 长度的叶子节点的数量.
     * res[distance] 表示 node 中已有的符合题意的叶子节点对.
     */
    private int[] dfs(TreeNode node, int dist) {
        int[] res = new int[dist + 1];
        if (node == null) {
            return res;
        }
        if (node.left == null && node.right == null) {
            res[0]++;
            return res;
        }
        int[] left = dfs(node.left, dist);
        int[] right = dfs(node.right, dist);
        for (int i = 1; i < dist; i++) {
            res[i] = left[i - 1] + right[i - 1];
        }
        // (求和, 为下面的计算做准备)
        for (int i = 1; i < dist; i++) {
            left[i] += left[i - 1];
        }
        res[dist] = left[dist] + right[dist];
        for (int i = 0; i < dist - 1; i++) {
            res[dist] += right[i] * left[dist - i - 2];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2, 3, null, 4), 3)
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2, 3, 4, 5, 6, 7), 3)
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(TreeNode.createByLevel(7, 1, 4, 6, null, 5, 3, null, null, null, null, null, 2), 3)
            .expect(1);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(TreeNode.createByLevel(100), 1)
            .expect(0);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 1, 1), 2)
            .expect(1);

}
