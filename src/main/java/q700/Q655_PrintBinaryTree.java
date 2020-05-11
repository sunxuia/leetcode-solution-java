package q700;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/print-binary-tree/
 *
 * Print a binary tree in an m*n 2D string array following these rules:
 *
 * The row number m should be equal to the height of the given binary tree.
 * The column number n should always be an odd number.
 * The root node's value (in string format) should be put in the exactly middle of the first row it can be put.
 * The column and the row where the root node belongs will separate the rest space into two parts (left-bottom
 * part and right-bottom part). You should print the left subtree in the left-bottom part and print the right
 * subtree in the right-bottom part. The left-bottom part and the right-bottom part should have the same size.
 * Even if one subtree is none while the other is not, you don't need to print anything for the none subtree but
 * still need to leave the space as large as that for the other subtree. However, if two subtrees are none, then
 * you don't need to leave space for both of them.
 * Each unused space should contain an empty string "".
 * Print the subtrees following the same rules.
 *
 * Example 1:
 *
 * Input:
 * >      1
 * >     /
 * >    2
 * Output:
 * [["", "1", ""],
 * ["2", "", ""]]
 *
 * Example 2:
 *
 * Input:
 * >      1
 * >     / \
 * >    2   3
 * >     \
 * >      4
 * Output:
 * [["", "", "", "1", "", "", ""],
 * ["", "2", "", "", "", "3", ""],
 * ["", "", "4", "", "", "", ""]]
 *
 * Example 3:
 *
 * Input:
 * >       1
 * >      / \
 * >     2   5
 * >    /
 * >   3
 * >  /
 * > 4
 * Output:
 *
 * [["",  "",  "", "",  "", "", "", "1", "",  "",  "",  "",  "", "", ""]
 * ["",  "",  "", "2", "", "", "", "",  "",  "",  "",  "5", "", "", ""]
 * ["",  "3", "", "",  "", "", "", "",  "",  "",  "",  "",  "", "", ""]
 * ["4", "",  "", "",  "", "", "", "",  "",  "",  "",  "",  "", "", ""]]
 *
 * Note: The height of binary tree is in the range of [1, 10].
 */
@RunWith(LeetCodeRunner.class)
public class Q655_PrintBinaryTree {

    /**
     * 这里假设root 的depth 为0, 第i 层的节点需要在前面和后面附加的空字符串的数量为:
     * 2^(maxDepth - currDepth) - 1
     */
    @Answer
    public List<List<String>> printTree(TreeNode root) {
        List<List<String>> res = new ArrayList<>();
        // 找出最大深度, res.size() - 1 就是最大深度
        extendDepth(root, 0, res);
        // 中序遍历, 组装结果
        inOrder(root, 0, res);
        return res;
    }

    private void extendDepth(TreeNode node, int depth, List<List<String>> res) {
        if (node == null) {
            return;
        }
        if (res.size() <= depth) {
            res.add(new ArrayList<>());
        }
        extendDepth(node.left, depth + 1, res);
        extendDepth(node.right, depth + 1, res);
    }

    private void inOrder(TreeNode node, int depth, List<List<String>> res) {
        final int maxDepth = res.size() - 1;
        if (depth > maxDepth) {
            return;
        }
        int pad = (1 << (maxDepth - depth)) - 1;
        // 前面的pad
        List<String> level = res.get(depth);
        for (int i = 0; i < pad; i++) {
            level.add("");
        }
        // 中间的值
        level.add(node == null ? "" : String.valueOf(node.val));
        // 后面的pad
        for (int i = 0; i < pad; i++) {
            level.add("");
        }

        inOrder(node == null ? null : node.left, depth + 1, res);
        // 该节点下面应该都是""
        for (int i = depth + 1; i <= maxDepth; i++) {
            res.get(i).add("");
        }
        inOrder(node == null ? null : node.right, depth + 1, res);
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.create(TreeNode.createByLevel(1, 2))
            .expect(new String[][]{
                    {"", "1", ""},
                    {"2", "", ""}
            });

    @TestData
    public DataExpectation exmaple2 = DataExpectation.create(TreeNode.createByLevel(1, 2, 3, null, 4))
            .expect(new String[][]{
                    {"", "", "", "1", "", "", ""},
                    {"", "2", "", "", "", "3", ""},
                    {"", "", "4", "", "", "", ""}
            });

    @TestData
    public DataExpectation exmaple3 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, 2, 3, 4, null, null, null, null, 5))
            .expect(new String[][]{
                    {"", "", "", "", "", "", "", "1", "", "", "", "", "", "", ""},
                    {"", "", "", "2", "", "", "", "", "", "", "", "5", "", "", ""},
                    {"", "3", "", "", "", "", "", "", "", "", "", "", "", "", ""},
                    {"4", "", "", "", "", "", "", "", "", "", "", "", "", "", ""}
            });

}
