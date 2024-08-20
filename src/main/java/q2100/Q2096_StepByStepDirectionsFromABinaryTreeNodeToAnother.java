package q2100;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2096. Step-By-Step Directions From a Binary Tree Node to Another</h3>
 * <a href="https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/">
 * https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/
 * </a><br/>
 *
 * <p>You are given the <code>root</code> of a <strong>binary tree</strong> with <code>n</code> nodes. Each node is
 * uniquely assigned a value from <code>1</code> to <code>n</code>. You are also given an integer
 * <code>startValue</code> representing the value of the start node <code>s</code>, and a different integer
 * <code>destValue</code> representing the value of the destination node <code>t</code>.</p>
 *
 * <p>Find the <strong>shortest path</strong> starting from node <code>s</code> and ending at node <code>t</code>.
 * Generate step-by-step directions of such path as a string consisting of only the <strong>uppercase</strong> letters
 * <code>&#39;L&#39;</code>, <code>&#39;R&#39;</code>, and <code>&#39;U&#39;</code>. Each letter indicates a specific
 * direction:</p>
 *
 * <ul>
 * 	<li><code>&#39;L&#39;</code> means to go from a node to its <strong>left child</strong> node.</li>
 * 	<li><code>&#39;R&#39;</code> means to go from a node to its <strong>right child</strong> node.</li>
 * 	<li><code>&#39;U&#39;</code> means to go from a node to its <strong>parent</strong> node.</li>
 * </ul>
 *
 * <p>Return <em>the step-by-step directions of the <strong>shortest path</strong> from node </em><code>s</code><em>
 *     to node
 * </em> <code>t</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/15/eg1.png" style="width: 214px; height: 163px;" />
 * <pre>
 * <strong>Input:</strong> root = [5,1,2,3,null,6,4], startValue = 3, destValue = 6
 * <strong>Output:</strong> &quot;UURL&quot;
 * <strong>Explanation:</strong> The shortest path is: 3 &rarr; 1 &rarr; 5 &rarr; 2 &rarr; 6.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/15/eg2.png" style="width: 74px; height: 102px;" />
 * <pre>
 * <strong>Input:</strong> root = [2,1], startValue = 2, destValue = 1
 * <strong>Output:</strong> &quot;L&quot;
 * <strong>Explanation:</strong> The shortest path is: 2 &rarr; 1.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li>The number of nodes in the tree is <code>n</code>.</li>
 * 	<li><code>2 &lt;= n &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= Node.val &lt;= n</code></li>
 * 	<li>All the values in the tree are <strong>unique</strong>.</li>
 * 	<li><code>1 &lt;= startValue, destValue &lt;= n</code></li>
 * 	<li><code>startValue != destValue</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2096_StepByStepDirectionsFromABinaryTreeNodeToAnother {

    @Answer
    public String getDirections(TreeNode root, int startValue, int destValue) {
        Path path = dfs(root, startValue, destValue);
        return path.sb.toString();
    }

    private Path dfs(TreeNode node, int startValue, int destValue) {
        if (node == null) {
            return null;
        }

        Path curr = null;
        if (node.val == startValue) {
            curr = new Path();
            curr.start = true;
        } else if (node.val == destValue) {
            curr = new Path();
            curr.end = true;
        }

        Path left = dfs(node.left, startValue, destValue);
        if (left != null) {
            if (left.start && left.end) {
                return left;
            } else if (left.start) {
                left.sb.append("U");
            } else {
                left.sb.append("L");
            }
        }

        Path right = null;
        if (curr == null || left == null) {
            right = dfs(node.right, startValue, destValue);
            if (right != null) {
                if (right.start && right.end) {
                    return right;
                } else if (right.start) {
                    right.sb.append("U");
                } else {
                    right.sb.append("R");
                }
            }
        }

        if (curr == null) {
            if (left != null && right != null) {
                // 路径在这里闭合
                if (left.start) {
                    // left -> right
                    left.end = true;
                    left.sb.append(right.sb.reverse());
                    return left;
                } else {
                    // right -> left
                    right.end = true;
                    right.sb.append(left.sb.reverse());
                    return right;
                }
            } else {
                // 只是单个路径
                return left == null ? right : left;
            }
        } else {
            // 一条路, 不需要闭合
            if (left != null || right != null) {
                Path child = left == null ? right : left;
                if (curr.start) {
                    curr.end = true;
                    curr.sb = child.sb.reverse();
                } else {
                    curr.start = true;
                    curr.sb = child.sb;
                }
            }
            return curr;
        }
    }

    private static class Path {

        boolean start, end;

        StringBuilder sb = new StringBuilder();

    }


    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByLevel(5, 1, 2, 3, null, 6, 4), 3, 6)
            .expect("UURL");

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(2, 1), 2, 1)
            .expect("L");

}
