package q200;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/binary-tree-right-side-view/
 *
 * Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see
 * ordered from top to bottom.
 *
 * Example:
 *
 * Input: [1,2,3,null,5,null,4]
 * Output: [1, 3, 4]
 * Explanation:
 *
 * >     1            <---
 * >   /   \
 * >  2     3         <---
 * >   \     \
 * >    5     4       <---
 */
@RunWith(LeetCodeRunner.class)
public class Q199_BinaryTreeRightSideView {

    @Answer
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> res = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            res.add(queue.getLast().val);
            for (int i = queue.size(); i > 0; i--) {
                TreeNode node = queue.remove();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, 2, null, 5, null, null, 3, null, 4))
            .expect(Arrays.asList(1, 3, 4));

}
