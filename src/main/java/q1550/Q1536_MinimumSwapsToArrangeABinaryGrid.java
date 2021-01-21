package q1550;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1536. Minimum Swaps to Arrange a Binary Grid
 * https://leetcode.com/problems/minimum-swaps-to-arrange-a-binary-grid/
 *
 * Given an n x n binary grid, in one step you can choose two adjacent rows of the grid and swap them.
 *
 * A grid is said to be valid if all the cells above the main diagonal are zeros.
 *
 * Return the minimum number of steps needed to make the grid valid, or -1 if the grid cannot be valid.
 *
 * The main diagonal of a grid is the diagonal that starts at cell (1, 1) and ends at cell (n, n).
 *
 * Example 1:
 * <img src="./Q1536_PIC1.png">
 * Input: grid = [[0,0,1],[1,1,0],[1,0,0]]
 * Output: 3
 *
 * Example 2:
 * <img src="./Q1536_PIC2.png">
 * Input: grid = [[0,1,1,0],[0,1,1,0],[0,1,1,0],[0,1,1,0]]
 * Output: -1
 * Explanation: All rows are similar, swaps have no effect on the grid.
 *
 * Example 3:
 * <img src="./Q1536_PIC3.png">
 * Input: grid = [[1,0,0],[1,1,0],[1,1,1]]
 * Output: 0
 *
 * Constraints:
 *
 * n == grid.length
 * n == grid[i].length
 * 1 <= n <= 200
 * grid[i][j] is 0 or 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1536_MinimumSwapsToArrangeABinaryGrid {

    /**
     * 树状数组的解法, 时间复杂度 O(NlogN).
     * 上一个类似题目参见 {@link Q1505_MinimumPossibleIntegerAfterAtMostKAdjacentSwapsOnDigits}
     *
     * (LeetCode 上比较快的解法是直接交换的, 时间复杂度 O(N^2) )
     */
    @Answer
    public int minSwaps(int[][] grid) {
        final int n = grid.length;
        List<Integer>[] zeros = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            zeros[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            int j = n - 1;
            while (j >= 0 && grid[i][j] == 0) {
                j--;
            }
            zeros[n - 1 - j].add(i);
        }

        int res = 0;
        // 保存满足条件的下标
        PriorityQueue<Integer> pq = new PriorityQueue<>(zeros[n]);
        // 树状数组的的数组
        int[] bits = new int[n + 1];
        for (int i = 1; i < n; i++) {
            pq.addAll(zeros[n - i]);
            if (pq.isEmpty()) {
                return -1;
            }
            int row = pq.poll();
            // 向前移动的行数 = 原本行数 - 已经被移动掉的行数
            // (row + 1 是因为树状数组的0 位置需要保留)
            int removed = query(bits, row + 1);
            res += row - removed;
            // 标记该行已经被移动掉了
            update(bits, row + 1, 1);
        }
        return res;
    }

    // 更新数量 (bits[i] + offset)
    private void update(int[] bits, int i, int offset) {
        for (; i < bits.length; i += i & -i) {
            bits[i] += offset;
        }
    }

    // 查询 [1, i] 的总数
    private int query(int[] bits, int i) {
        int res = 0;
        for (; i > 0; i -= i & -i) {
            res += bits[i];
        }
        return res;
    }


    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {0, 0, 1},
            {1, 1, 0},
            {1, 0, 0}
    }).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {0, 1, 1, 0},
            {0, 1, 1, 0},
            {0, 1, 1, 0},
            {0, 1, 1, 0}
    }).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 0, 0},
            {1, 1, 0},
            {1, 1, 1}
    }).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {1, 0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 0},
            {1, 1, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0}
    }).expect(2);

}
