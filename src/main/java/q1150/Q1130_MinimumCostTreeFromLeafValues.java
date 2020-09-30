package q1150;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1130. Minimum Cost Tree From Leaf Values
 * https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
 *
 * Given an array arr of positive integers, consider all binary trees such that:
 *
 * 1. Each node has either 0 or 2 children;
 * 2. The values of arr correspond to the values of each leaf in an in-order traversal of the tree.  (Recall that a node
 * is a leaf if and only if it has 0 children.)
 * 3. The value of each non-leaf node is equal to the product of the largest leaf value in its left and right subtree
 * respectively.
 *
 * Among all possible binary trees considered, return the smallest possible sum of the values of each non-leaf node.
 * It
 * is guaranteed this sum fits into a 32-bit integer.
 *
 * Example 1:
 *
 * Input: arr = [6,2,4]
 * Output: 32
 * Explanation:
 * There are two possible trees.  The first has non-leaf node sum 36, and the second has non-leaf node sum 32.
 *
 * >     24            24
 * >    /  \          /  \
 * >   12   4        6    8
 * >  /  \               / \
 * > 6    2             2   4
 *
 * Constraints:
 *
 * 2 <= arr.length <= 40
 * 1 <= arr[i] <= 15
 * It is guaranteed that the answer fits into a 32-bit signed integer (ie. it is less than 2^31).
 */
@RunWith(LeetCodeRunner.class)
public class Q1130_MinimumCostTreeFromLeafValues {

    /**
     * 根据hint 得到提示, 使用dp[i][j] 表示 arr[i, j] 的结果.
     */
    @Answer
    public int mctFromLeafValues(int[] arr) {
        final int n = arr.length;
        // max[i][j] 表示 arr[i, j] 区间内的最大值
        int[][] max = new int[n][n];
        for (int i = 0; i < n; i++) {
            max[i][i] = arr[i];
            for (int j = i + 1; j < n; j++) {
                max[i][j] = Math.max(max[i][j - 1], arr[j]);
            }
        }

        // dp[start][end] 表示 arr[start, end] 区间内的期望结果.
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            dp[i][i] = 0;
        }
        for (int r = 1; r < n; r++) {
            for (int start = 0, end = r; end < n; start++, end++) {
                for (int i = start; i < end; i++) {
                    dp[start][end] = Math.min(dp[start][end],
                            max[start][i] * max[i + 1][end] + dp[start][i] + dp[i + 1][end]);
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * LeetCode 上比较快的解法, 使用递减栈来做.
     * 参考文档 https://www.acwing.com/solution/LeetCode/content/3996/
     * 根据题意我们知道, 每次两个相邻元素组成一棵子树后, 会将较小的元素删去, 留下较大的元素.
     * 所以我们的目标就是每次删除局部最小的那个元素.
     * 比如[6, 2, 4] 中2 就是局部最小因为他小于左右两边的元素. 我们将局部最小的元素和两边较小的元素相乘加入答案, 同时将这个局部最小的元素抹去.
     */
    @Answer
    public int mctFromLeafValues2(int[] arr) {
        int res = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(Integer.MAX_VALUE);
        for (int num : arr) {
            while (stack.peek() <= num) {
                // 两边的最大值相乘 (如果当前值是第二小的值就使用当前值)
                res += stack.pop() * Math.min(num, stack.peek());
            }
            stack.push(num);
        }
        while (stack.size() > 2) {
            res += stack.pop() * stack.peek();
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{6, 2, 4}).expect(32);

}
