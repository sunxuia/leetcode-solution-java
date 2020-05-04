package q600;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.Nary;
import util.provided.Nary.Node;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/n-ary-tree-postorder-traversal/
 *
 * Given an n-ary tree, return the postorder traversal of its nodes' values.
 *
 * Nary-Tree input serialization is represented in their level order traversal, each group of children is separated
 * by the null value (See examples).
 *
 *
 *
 * Follow up:
 *
 * Recursive solution is trivial, could you do it iteratively?
 *
 *
 *
 * Example 1:
 *
 * (图 Q589_590_PIC1.png)
 *
 * Input: root = [1,null,3,2,4,null,5,6]
 * Output: [5,6,3,2,4,1]
 *
 * Example 2:
 *
 * (图 Q589_590_PIC2.png)
 *
 * Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
 * Output: [2,6,14,11,7,3,12,8,4,13,9,10,5,1]
 *
 *
 *
 * Constraints:
 *
 * 1. The height of the n-ary tree is less than or equal to 1000
 * 2. The total number of nodes is between [0, 10^4]
 */
@RunWith(LeetCodeRunner.class)
public class Q590_NaryTreePostorderTraversal {

    @Answer
    public List<Integer> postorder(Node root) {
        List<Integer> res = new ArrayList<>();
        dfs(root, res);
        return res;
    }

    private void dfs(Node node, List<Integer> res) {
        if (node == null) {
            return;
        }
        if (node.children != null) {
            for (Node child : node.children) {
                dfs(child, res);
            }
        }
        res.add(node.val);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(Nary.createNodeByLevel(1, null,
                    3, 2, 4, null,
                    5, 6))
            .expect(Arrays.asList(5, 6, 3, 2, 4, 1));

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(Nary.createNodeByLevel(1, null,
                    2, 3, 4, 5, null,
                    null, 6, 7, null, 8, null, 9, 10, null,
                    null, 11, null, 12, null, 13, null, null,
                    14))
            .expect(Arrays.asList(2, 6, 14, 11, 7, 3, 12, 8, 4, 13, 9, 10, 5, 1));

}
