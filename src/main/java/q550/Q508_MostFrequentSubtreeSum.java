package q550;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/most-frequent-subtree-sum/
 *
 * Given the root of a tree, you are asked to find the most frequent subtree sum. The subtree sum of a node is
 * defined as the sum of all the node values formed by the subtree rooted at that node (including the node itself).
 * So what is the most frequent subtree sum value? If there is a tie, return all the values with the highest
 * frequency in any order.
 *
 * Examples 1
 * Input:
 *
 * >   5
 * >  /  \
 * > 2   -3
 *
 * return [2, -3, 4], since all the values happen only once, return all of them in any order.
 *
 * Examples 2
 * Input:
 *
 * >   5
 * >  /  \
 * > 2   -5
 *
 * return [2], since 2 happens twice, however -5 only occur once.
 *
 * Note: You may assume the sum of values in any subtree is in the range of 32-bit signed integer.
 */
@RunWith(LeetCodeRunner.class)
public class Q508_MostFrequentSubtreeSum {

    @Answer
    public int[] findFrequentTreeSum(TreeNode root) {
        Map<Integer, Integer> counts = new HashMap<>();
        dfs(root, counts);
        int maxCount = counts.values().stream()
                .max(Comparator.comparingInt(i -> i))
                .orElse(0);
        return counts.entrySet().stream()
                .filter(e -> e.getValue() == maxCount)
                .mapToInt(Map.Entry::getKey)
                .toArray();
    }

    private int dfs(TreeNode node, Map<Integer, Integer> counts) {
        if (node == null) {
            return 0;
        }
        int sum = node.val + dfs(node.left, counts) + dfs(node.right, counts);
        int count = 1 + counts.getOrDefault(sum, 0);
        counts.put(sum, count);
        return sum;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(TreeNode.createByLevel(5, 2, -3))
            .expect(new int[]{2, -3, 4})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(TreeNode.createByLevel(5, 2, -5))
            .expect(new int[]{2})
            .unorderResult()
            .build();

}
