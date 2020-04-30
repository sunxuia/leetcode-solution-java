package q600;

import java.util.LinkedList;
import java.util.Queue;
import org.junit.Assert;
import org.junit.runner.RunWith;
import q450.Q427_ConstructQuadTree;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/logical-or-of-two-binary-grids-represented-as-quad-trees/
 *
 * A Binary Matrix is a matrix in which all the elements are either 0 or 1.
 *
 * Given quadTree1 and quadTree2. quadTree1 represents a n * n binary matrix and quadTree2 represents another n * n
 * binary matrix.
 *
 * Return a Quad-Tree representing the n * n binary matrix which is the result of logical bitwise OR of the two
 * binary matrixes represented by quadTree1 and quadTree2.
 *
 * Notice that you can assign the value of a node to True or False when isLeaf is False, and both are accepted in the
 * answer.
 *
 * A Quad-Tree is a tree data structure in which each internal node has exactly four children. Besides, each node has
 * two attributes:
 *
 * val: True if the node represents a grid of 1's or False if the node represents a grid of 0's.
 * isLeaf: True if the node is leaf node on the tree or False if the node has the four children.
 *
 * class Node {
 * public boolean val;
 * public boolean isLeaf;
 * public Node topLeft;
 * public Node topRight;
 * public Node bottomLeft;
 * public Node bottomRight;
 * }
 *
 * We can construct a Quad-Tree from a two-dimensional area using the following steps:
 *
 * 1. If the current grid has the same value (i.e all 1's or all 0's) set isLeaf True and set val to the value of
 * the grid and set the four children to Null and stop.
 * 2. If the current grid has different values, set isLeaf to False and set val to any value and divide the current
 * grid into four sub-grids as shown in the photo.
 * 3. Recurse for each of the children with the proper sub-grid.
 *
 * (图Q558_PIC1.png)
 *
 * If you want to know more about the Quad-Tree, you can refer to the wiki.
 *
 * Quad-Tree format:
 *
 * The input/output represents the serialized format of a Quad-Tree using level order traversal, where null signifies
 * a path terminator where no node exists below.
 *
 * It is very similar to the serialization of the binary tree. The only difference is that the node is represented as
 * a list [isLeaf, val].
 *
 * If the value of isLeaf or val is True we represent it as 1 in the list [isLeaf, val] and if the value of isLeaf or
 * val is False we represent it as 0.
 *
 *
 *
 * Example 1:
 *
 * (图Q558_PIC2.png)
 * (图Q558_PIC3.png)
 *
 * Input: quadTree1 = [[0,1],[1,1],[1,1],[1,0],[1,0]]
 * , quadTree2 = [[0,1],[1,1],[0,1],[1,1],[1,0],null,null,null,null,[1,0],[1,0],[1,1],[1,1]]
 * Output: [[0,0],[1,1],[1,1],[1,1],[1,0]]
 * Explanation: quadTree1 and quadTree2 are shown above. You can see the binary matrix which is represented by each
 * Quad-Tree.
 * If we apply logical bitwise OR on the two binary matrices we get the binary matrix below which is represented by
 * the result Quad-Tree.
 * Notice that the binary matrices shown are only for illustration, you don't have to construct the binary matrix to
 * get the result tree.
 *
 * (图Q558_PIC4.png)
 *
 * Example 2:
 *
 * Input: quadTree1 = [[1,0]]
 * , quadTree2 = [[1,0]]
 * Output: [[1,0]]
 * Explanation: Each tree represents a binary matrix of size 1*1. Each matrix contains only zero.
 * The resulting matrix is of size 1*1 with also zero.
 *
 * Example 3:
 *
 * Input: quadTree1 = [[0,0],[1,0],[1,0],[1,1],[1,1]]
 * , quadTree2 = [[0,0],[1,1],[1,1],[1,0],[1,1]]
 * Output: [[1,1]]
 *
 * Example 4:
 *
 * Input: quadTree1 = [[0,0],[1,1],[1,0],[1,1],[1,1]]
 * , quadTree2 = [[0,0],[1,1],[0,1],[1,1],[1,1],null,null,null,null,[1,1],[1,0],[1,0],[1,1]]
 * Output: [[0,0],[1,1],[0,1],[1,1],[1,1],null,null,null,null,[1,1],[1,0],[1,0],[1,1]]
 *
 * Example 5:
 *
 * Input: quadTree1 = [[0,1],[1,0],[0,1],[1,1],[1,0],null,null,null,null,[1,0],[1,0],[1,1],[1,1]]
 * , quadTree2 = [[0,1],[0,1],[1,0],[1,1],[1,0],[1,0],[1,0],[1,1],[1,1]]
 * Output: [[0,0],[0,1],[0,1],[1,1],[1,0],[1,0],[1,0],[1,1],[1,1],[1,0],[1,0],[1,1],[1,1]]
 *
 *
 *
 * Constraints:
 *
 * quadTree1 and quadTree2 are both valid Quad-Trees each representing a n * n grid.
 * n == 2^x where 0 <= x <= 9.
 *
 * 关联题目 {@link Q427_ConstructQuadTree}
 */
@RunWith(LeetCodeRunner.class)
public class Q558_LogicalOROfTwoBinaryGridsRepresentedAsQuadTrees {

    // Definition for a QuadTree node.
    private static class Node {

        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;

        public Node() {}

        public Node(boolean _val, boolean _isLeaf, Node _topLeft, Node _topRight, Node _bottomLeft, Node _bottomRight) {
            val = _val;
            isLeaf = _isLeaf;
            topLeft = _topLeft;
            topRight = _topRight;
            bottomLeft = _bottomLeft;
            bottomRight = _bottomRight;
        }
    }

    // 题目本身比较简单
    @Answer
    public Node intersect(Node quadTree1, Node quadTree2) {
        if (quadTree1.isLeaf && quadTree2.isLeaf) {
            quadTree1.val = quadTree1.val || quadTree2.val;
            return quadTree1;
        }
        if (quadTree1.isLeaf || quadTree2.isLeaf) {
            if (quadTree1.isLeaf) {
                Node t = quadTree1;
                quadTree1 = quadTree2;
                quadTree2 = t;
            }
            quadTree2.topLeft = quadTree2.topRight = quadTree2.bottomLeft = quadTree2.bottomRight = quadTree2;
        }
        quadTree1.topLeft = intersect(quadTree1.topLeft, quadTree2.topLeft);
        quadTree1.topRight = intersect(quadTree1.topRight, quadTree2.topRight);
        quadTree1.bottomLeft = intersect(quadTree1.bottomLeft, quadTree2.bottomLeft);
        quadTree1.bottomRight = intersect(quadTree1.bottomRight, quadTree2.bottomRight);
        if (quadTree1.topLeft.isLeaf
                && quadTree1.topRight.isLeaf
                && quadTree1.bottomLeft.isLeaf
                && quadTree1.bottomRight.isLeaf
                && quadTree1.topLeft.val == quadTree1.topRight.val
                && quadTree1.topLeft.val == quadTree1.bottomLeft.val
                && quadTree1.topLeft.val == quadTree1.bottomRight.val) {
            quadTree1.isLeaf = true;
            quadTree1.val = quadTree1.topLeft.val;
            quadTree1.topLeft = quadTree1.topRight = quadTree1.bottomLeft = quadTree1.bottomRight = null;
        }
        return quadTree1;
    }

    @TestData
    public DataExpectation example1 = testCase(
            new int[][]{{0, 1}, {1, 1}, {1, 1}, {1, 0}, {1, 0}},
            new int[][]{{0, 1}, {1, 1}, {0, 1}, {1, 1}, {1, 0}, null, null, null, null, {1, 0}, {1, 0}, {1, 1}, {1, 1}},
            new int[][]{{0, 0}, {1, 1}, {1, 1}, {1, 1}, {1, 0}}
    );

    @TestData
    public DataExpectation example2 = testCase(
            new int[][]{{1, 0}},
            new int[][]{{1, 0}},
            new int[][]{{1, 0}}
    );

    @TestData
    public DataExpectation example3 = testCase(
            new int[][]{{0, 0}, {1, 0}, {1, 0}, {1, 1}, {1, 1}},
            new int[][]{{0, 0}, {1, 1}, {1, 1}, {1, 0}, {1, 1}},
            new int[][]{{1, 1}}
    );

    @TestData
    public DataExpectation example4 = testCase(
            new int[][]{{0, 0}, {1, 1}, {1, 0}, {1, 1}, {1, 1}},
            new int[][]{{0, 0}, {1, 1}, {0, 1}, {1, 1}, {1, 1}, null, null, null, null, {1, 1}, {1, 0}, {1, 0}, {1, 1}},
            new int[][]{{0, 0}, {1, 1}, {0, 1}, {1, 1}, {1, 1}, null, null, null, null, {1, 1}, {1, 0}, {1, 0}, {1, 1}}
    );

    @TestData
    public DataExpectation example5 = testCase(
            new int[][]{{0, 1}, {1, 0}, {0, 1}, {1, 1}, {1, 0}, null, null, null, null, {1, 0}, {1, 0}, {1, 1}, {1, 1}},
            new int[][]{{0, 1}, {0, 1}, {1, 0}, {1, 1}, {1, 0}, {1, 0}, {1, 0}, {1, 1}, {1, 1}},
            new int[][]{{0, 0}, {0, 1}, {0, 1}, {1, 1}, {1, 0}, {1, 0}, {1, 0}, {1, 1}, {1, 1}, {1, 0}, {1, 0}, {1, 1},
                    {1, 1}}
    );

    private DataExpectation testCase(int[][] quadTree1, int[][] quadTree2, int[][] expect) {
        return DataExpectation.builder()
                .addArgument(newQuadTree(quadTree1))
                .addArgument(newQuadTree(quadTree2))
                .expect(newQuadTree(expect))
                .assertMethod(this::assertMethod)
                .build();
    }

    private Node newQuadTree(int[][] grid) {
        Node root = newNode(grid, 0);
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int i = 1;
        while (!queue.isEmpty() && i < grid.length) {
            for (int count = queue.size(); count > 0; count--) {
                Node node = queue.remove();
                if (node != null) {
                    queue.add(node.topLeft = newNode(grid, i++));
                    queue.add(node.topRight = newNode(grid, i++));
                    queue.add(node.bottomLeft = newNode(grid, i++));
                    queue.add(node.bottomRight = newNode(grid, i++));
                }
            }
        }
        return root;
    }

    private Node newNode(int[][] grid, int i) {
        if (i >= grid.length || grid[i] == null) {
            return null;
        }
        Node res = new Node();
        res.isLeaf = grid[i][0] == 1;
        res.val = grid[i][1] == 1;
        return res;
    }

    private void assertMethod(Node expect, Node actual) {
        if (expect == actual) {
            return;
        }
        Assert.assertTrue(expect != null && actual != null);
        Assert.assertEquals(expect.isLeaf, actual.isLeaf);
        if (expect.isLeaf) {
            Assert.assertEquals(expect.val, actual.val);
        }

        assertMethod(expect.topLeft, actual.topLeft);
        assertMethod(expect.topRight, actual.topRight);
        assertMethod(expect.bottomLeft, actual.bottomLeft);
        assertMethod(expect.bottomRight, actual.bottomRight);
    }

}
