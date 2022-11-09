package q2050;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 2003. Smallest Missing Genetic Value in Each Subtree
 * https://leetcode.com/problems/smallest-missing-genetic-value-in-each-subtree/
 *
 * There is a family tree rooted at 0 consisting of n nodes numbered 0 to n - 1. You are given a 0-indexed integer array
 * parents, where parents[i] is the parent for node i. Since node 0 is the root, parents[0] == -1.
 *
 * There are 10^5 genetic values, each represented by an integer in the inclusive range [1, 10^5]. You are given a
 * 0-indexed integer array nums, where nums[i] is a distinct genetic value for node i.
 *
 * Return an array ans of length n where ans[i] is the smallest genetic value that is missing from the subtree rooted at
 * node i.
 *
 * The subtree rooted at a node x contains node x and all of its descendant nodes.
 *
 * Example 1:
 * (图Q2003_PIC1.png)
 * Input: parents = [-1,0,0,2], nums = [1,2,3,4]
 * Output: [5,1,1,1]
 * Explanation: The answer for each subtree is calculated as follows:
 * - 0: The subtree contains nodes [0,1,2,3] with values [1,2,3,4]. 5 is the smallest missing value.
 * - 1: The subtree contains only node 1 with value 2. 1 is the smallest missing value.
 * - 2: The subtree contains nodes [2,3] with values [3,4]. 1 is the smallest missing value.
 * - 3: The subtree contains only node 3 with value 4. 1 is the smallest missing value.
 *
 * Example 2:
 * (图Q2003_PIC2.png)
 * Input: parents = [-1,0,1,0,3,3], nums = [5,4,6,2,1,3]
 * Output: [7,1,1,4,2,1]
 * Explanation: The answer for each subtree is calculated as follows:
 * - 0: The subtree contains nodes [0,1,2,3,4,5] with values [5,4,6,2,1,3]. 7 is the smallest missing value.
 * - 1: The subtree contains nodes [1,2] with values [4,6]. 1 is the smallest missing value.
 * - 2: The subtree contains only node 2 with value 6. 1 is the smallest missing value.
 * - 3: The subtree contains nodes [3,4,5] with values [2,1,3]. 4 is the smallest missing value.
 * - 4: The subtree contains only node 4 with value 1. 2 is the smallest missing value.
 * - 5: The subtree contains only node 5 with value 3. 1 is the smallest missing value.
 *
 * Example 3:
 *
 * Input: parents = [-1,2,3,0,2,4,1], nums = [2,3,4,5,6,7,8]
 * Output: [1,1,1,1,1,1,1]
 * Explanation: The value 1 is missing from all the subtrees.
 *
 * Constraints:
 *
 * n == parents.length == nums.length
 * 2 <= n <= 10^5
 * 0 <= parents[i] <= n - 1 for i != 0
 * parents[0] == -1
 * parents represents a valid tree.
 * 1 <= nums[i] <= 10^5
 * Each nums[i] is distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q2003_SmallestMissingGeneticValueInEachSubtree {

    @Answer
    public int[] smallestMissingValueSubtree(int[] parents, int[] nums) {
        final int n = parents.length;

        Node[] nodes = new Node[n];
        int max = 0;
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
            nodes[i].num = nums[i];
            max = Math.max(max, nums[i]);
        }
        for (int i = 1; i < n; i++) {
            nodes[parents[i]].children.add(nodes[i]);
        }

        // 最后1 位是哨兵
        roots = new int[max + 2];
        for (int i = 1; i <= max + 1; i++) {
            roots[i] = i;
        }

        nodes[0].inspect();
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = nodes[i].min;
        }
        return res;
    }

    private int[] roots;

    private int findRoot(int i) {
        if (roots[i] == i) {
            return i;
        }
        return roots[i] = findRoot(roots[i]);
    }

    private class Node {

        int num, min = 1;

        List<Node> children = new ArrayList<>();

        void inspect() {
            for (Node child : children) {
                child.inspect();
            }
            int root = findRoot(num);
            for (Node child : children) {
                roots[findRoot(child.num)] = root;
                min = Math.max(min, child.min);
            }
            while (findRoot(min) == root) {
                min++;
            }
        }
    }


    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{-1, 0, 0, 2}, new int[]{1, 2, 3, 4})
            .expect(new int[]{5, 1, 1, 1});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{-1, 0, 1, 0, 3, 3}, new int[]{5, 4, 6, 2, 1, 3})
            .expect(new int[]{7, 1, 1, 4, 2, 1});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{-1, 2, 3, 0, 2, 4, 1}, new int[]{2, 3, 4, 5, 6, 7, 8})
            .expect(new int[]{1, 1, 1, 1, 1, 1, 1});

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation overTime = DataExpectation.createWith(
                    TestDataFileHelper.read(testDataFile, 1, int[].class),
                    TestDataFileHelper.read(testDataFile, 2, int[].class))
            .expect(TestDataFileHelper.read(testDataFile, 3, int[].class));

}
