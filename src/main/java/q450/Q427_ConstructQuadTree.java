package q450;

import org.junit.Assert;
import org.junit.runner.RunWith;
import q600.Q558_LogicalOROfTwoBinaryGridsRepresentedAsQuadTrees;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/construct-quad-tree/
 *
 * Given a n * n matrix grid of 0's and 1's only. We want to represent the grid with a Quad-Tree.
 *
 * Return the root of the Quad-Tree representing the grid.
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
 * If the current grid has the same value (i.e all 1's or all 0's) set isLeaf True and set val to the value of
 * the grid and set the four children to Null and stop.
 * If the current grid has different values, set isLeaf to False and set val to any value and divide the current
 * grid into four sub-grids as shown in the photo.
 * Recurse for each of the children with the proper sub-grid.
 * (图Q427_PIC1.png)
 * If you want to know more about the Quad-Tree, you can refer to the wiki.
 *
 * Quad-Tree format:
 *
 * The output represents the serialized format of a Quad-Tree using level order traversal, where null signifies a
 * path terminator where no node exists below.
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
 * (图Q427_PIC2.png)
 * Input: grid = [[0,1],[1,0]]
 * Output: [[0,1],[1,0],[1,1],[1,1],[1,0]]
 * Explanation: The explanation of this example is shown below:
 * Notice that 0 represnts False and 1 represents True in the photo representing the Quad-Tree.
 * (图Q427_PIC3.png)
 *
 * Example 2:
 * (图Q427_PIC4.png)
 * Input: grid = [[1,1,1,1,0,0,0,0],[1,1,1,1,0,0,0,0],[1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1],[1,1,1,1,0,0,0,0],[1,1,1,1,
 * 0,0,0,0],[1,1,1,1,0,0,0,0],[1,1,1,1,0,0,0,0]]
 * Output: [[0,1],[1,1],[0,1],[1,1],[1,0],null,null,null,null,[1,0],[1,0],[1,1],[1,1]]
 * Explanation: All values in the grid are not the same. We divide the grid into four sub-grids.
 * The topLeft, bottomLeft and bottomRight each has the same value.
 * The topRight have different values so we divide it into 4 sub-grids where each has the same value.
 * Explanation is shown in the photo below:
 * (图Q427_PIC5.png)
 *
 * Example 3:
 *
 * Input: grid = [[1,1],[1,1]]
 * Output: [[1,1]]
 *
 * Example 4:
 *
 * Input: grid = [[0]]
 * Output: [[1,0]]
 *
 * Example 5:
 *
 * Input: grid = [[1,1,0,0],[1,1,0,0],[0,0,1,1],[0,0,1,1]]
 * Output: [[0,1],[1,1],[1,0],[1,0],[1,1]]
 *
 *
 *
 * Constraints:
 *
 * n == grid.length == grid[i].length
 * n == 2^x where 0 <= x <= 6
 *
 * 关联题目 {@link Q558_LogicalOROfTwoBinaryGridsRepresentedAsQuadTrees}
 */
@RunWith(LeetCodeRunner.class)
public class Q427_ConstructQuadTree {

    // Definition for a QuadTree node.
    private static class Node {

        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;


        public Node() {
            this.val = false;
            this.isLeaf = false;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
        }
    }

    // 难度主要来自于长长的题目 -_-||
    @Answer
    public Node construct(int[][] grid) {
        leaf0 = newLeaf(false);
        leaf1 = newLeaf(true);
        return dfs(grid, 0, 0, grid.length);
    }

    private Node leaf0, leaf1;

    private Node newLeaf(boolean val) {
        Node node = new Node();
        node.isLeaf = true;
        node.val = val;
        return node;
    }

    private Node dfs(int[][] grid, int x, int y, int n) {
        if (n == 1) {
            return grid[y][x] == 0 ? leaf0 : leaf1;
        }
        int half = n / 2;
        Node topLeft = dfs(grid, x, y, half);
        Node topRight = dfs(grid, x + half, y, half);
        Node bottomLeft = dfs(grid, x, y + half, half);
        Node bottomRight = dfs(grid, x + half, y + half, half);

        if (topLeft == topRight && topLeft == bottomLeft && topLeft == bottomRight) {
            return topLeft;
        }
        Node node = new Node();
        node.topLeft = topLeft;
        node.topRight = topRight;
        node.bottomLeft = bottomLeft;
        node.bottomRight = bottomRight;
        return node;
    }

    @TestData
    public DataExpectation example1() {
        Node root = new Node();
        root.topLeft = newLeaf(false);
        root.topRight = newLeaf(true);
        root.bottomLeft = newLeaf(true);
        root.bottomRight = newLeaf(false);

        return build(new int[][]{
                {0, 1},
                {1, 0}
        }, root);
    }

    private DataExpectation build(int[][] grid, Node node) {
        return DataExpectation.builder()
                .addArgument(grid)
                .expect(node)
                .assertMethod(this::assertMethod)
                .build();
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

    @TestData
    public DataExpectation example2() {
        Node root = new Node();
        root.topLeft = newLeaf(true);
        root.topRight = new Node();
        root.topRight.topLeft = newLeaf(false);
        root.topRight.topRight = newLeaf(false);
        root.topRight.bottomLeft = newLeaf(true);
        root.topRight.bottomRight = newLeaf(true);
        root.bottomLeft = newLeaf(true);
        root.bottomRight = newLeaf(false);

        return build(new int[][]{
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0}
        }, root);
    }

    @TestData
    public DataExpectation example3() {
        return build(new int[][]{
                {1, 1},
                {1, 1}
        }, newLeaf(true));
    }

    @TestData
    public DataExpectation example4() {
        return build(new int[][]{
                {0}
        }, newLeaf(false));
    }

    @TestData
    public DataExpectation example5() {
        Node root = new Node();
        root.topLeft = newLeaf(true);
        root.topRight = newLeaf(false);
        root.bottomLeft = newLeaf(false);
        root.bottomRight = newLeaf(true);
        return build(new int[][]{
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {0, 0, 1, 1},
                {0, 0, 1, 1}
        }, root);
    }

}
