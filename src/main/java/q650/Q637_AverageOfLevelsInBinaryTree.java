package q650;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/average-of-levels-in-binary-tree/
 *
 * Given a non-empty binary tree, return the average value of the nodes on each level in the form of an array.
 *
 * Example 1:
 *
 * Input:
 * >     3
 * >    / \
 * >   9  20
 * >     /  \
 * >    15   7
 * Output: [3, 14.5, 11]
 * Explanation:
 * The average value of nodes on level 0 is 3,  on level 1 is 14.5, and on level 2 is 11. Hence return [3, 14.5, 11].
 *
 * Note:
 *
 * The range of node's value is in the range of 32-bit signed integer.
 */
@RunWith(LeetCodeRunner.class)
public class Q637_AverageOfLevelsInBinaryTree {

    @Answer
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            double sum = 0, count = 0;
            for (int i = queue.size(); i > 0; i--) {
                TreeNode node = queue.remove();
                if (node == null) {
                    continue;
                }
                count++;
                sum += node.val;
                queue.add(node.left);
                queue.add(node.right);
            }
            if (count > 0) {
                res.add(sum / count);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(TreeNode.createByLevel(3, 9, 20, null, null, 15, 7))
            .expect(Arrays.asList(3.0, 14.5, 11.0));

}
