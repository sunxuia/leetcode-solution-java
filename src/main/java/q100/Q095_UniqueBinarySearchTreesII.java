package q100;

import static util.provided.TreeNode.createByPreOrderTraversal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/unique-binary-search-trees-ii/
 *
 * Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.
 *
 * Example:
 *
 * Input: 3
 * Output:
 * > [
 * >   [1,null,3,2],
 * >   [3,2,null,1],
 * >   [3,1,null,null,2],
 * >   [2,1,3],
 * >   [1,null,2,null,3]
 * > ]
 * Explanation:
 * The above output corresponds to the 5 unique BST's shown below:
 *
 * >   1         3     3      2      1
 * >    \       /     /      / \      \
 * >     3     2     1      1   3      2
 * >    /     /       \                 \
 * >   2     1         2                 3
 *
 * 题解:
 *  这题是要生成所有可能的二叉搜索树, 左子节点的值比节点的值小, 右子节点的值比节点的值小.
 *  相关问题 : {@link Q096_UniqueBinarySearchTrees}
 */
@RunWith(LeetCodeRunner.class)
public class Q095_UniqueBinarySearchTreesII {

    @Answer
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return Collections.emptyList();
        }
        return dfs(1, n);
    }

    private List<TreeNode> dfs(int start, int end) {
        if (start > end) {
            return Collections.singletonList(null);
        }
        List<TreeNode> res = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftNodes = dfs(start, i - 1);
            List<TreeNode> rightNodes = dfs(i + 1, end);
            for (TreeNode leftNode : leftNodes) {
                for (TreeNode rightNode : rightNodes) {
                    TreeNode node = new TreeNode(i);
                    node.left = leftNode;
                    node.right = rightNode;
                    res.add(node);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(3)
            .expect(new TreeNode[]{
                    createByPreOrderTraversal(1, null, 3, 2),
                    createByPreOrderTraversal(3, 2, 1),
                    createByPreOrderTraversal(3, 1, null, 2),
                    createByPreOrderTraversal(2, 1, null, null, 3),
                    createByPreOrderTraversal(1, null, 2, null, 3)
            }).unorderResult()
            .build();

    @TestData
    public DataExpectation border = DataExpectation.builder()
            .addArgument(0)
            .expect(Collections.emptyList())
            .unorderResult()
            .build();

}
