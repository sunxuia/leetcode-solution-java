package q550;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/random-flip-matrix/
 *
 * You are given the number of rows n_rows and number of columns n_cols of a 2D binary matrix where all values are
 * initially 0. Write a function flip which chooses a 0 value uniformly at random, changes it to 1, and then returns
 * the position [row.id, col.id] of that value. Also, write a function reset which sets all values back to 0. Try to
 * minimize the number of calls to system's Math.random() and optimize the time and space complexity.
 *
 * Note:
 *
 * 1 <= n_rows, n_cols <= 10000
 * 0 <= row.id < n_rows and 0 <= col.id < n_cols
 * flip will not be called when the matrix has no 0 values left.
 * the total number of calls to flip and reset will not exceed 1000.
 *
 * Example 1:
 *
 * Input:
 * ["Solution","flip","flip","flip","flip"]
 * [[2,3],[],[],[],[]]
 * Output: [null,[0,1],[1,2],[1,0],[1,1]]
 *
 * Example 2:
 *
 * Input:
 * ["Solution","flip","flip","reset","flip"]
 * [[1,2],[],[],[],[]]
 * Output: [null,[0,0],[0,1],null,[0,0]]
 *
 * Explanation of Input Syntax:
 *
 * The input is two lists: the subroutines called and their arguments. Solution's constructor has two arguments,
 * n_rows and n_cols. flip and reset have no arguments. Arguments are always wrapped with a list, even if there
 * aren't any.
 */
public class Q519_RandomFlipMatrix {

    // LeetCode 上比较好的解法如下
    private static class Solution {

        private Random random = new Random();

        Map<Integer, Integer> map = new HashMap<>();

        private final int row, col;

        private int total;

        public Solution(int n_rows, int n_cols) {
            row = n_rows;
            col = n_cols;
            total = row * col;
        }

        public int[] flip() {
            // 取小于total 的随机数 val
            int val = random.nextInt(total--);
            // 取得随机数 val 对应的值
            int r = map.getOrDefault(val, val);
            // 将val 的值更新为total 的值,
            // 因为这个total 对应的值将不会被随机取到, 而val 可能会被重复取得,
            // 这样当下次取到val 的时候就会返回这次的total 值.
            map.put(val, map.getOrDefault(total, total));

            return new int[]{r / col, r % col};
        }

        public void reset() {
            total = row * col;
            map.clear();
        }
    }

    @Test
    public void testMethod() {
        testCase(2, 3, 1);
        testCase(1, 2, 1);

        testCase(1_0000, 1_0000, 1_0000);
    }

    private void testCase(int n_rows, int n_cols, int flipFactor) {
        final int length = n_rows * n_cols;
        Solution solution = new Solution(n_rows, n_cols);
        Set<Integer> set = new HashSet<>(n_rows * n_cols / flipFactor);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < n_rows * n_cols / flipFactor; j++) {
                int[] flip = solution.flip();
                String position = "(" + flip[0] + "," + flip[1] + ")";
                Assert.assertTrue(position + "行不在范围内", 0 <= flip[0] && flip[0] < n_rows);
                Assert.assertTrue(position + "列不在范围内", 0 <= flip[1] && flip[1] < n_cols);
                Assert.assertTrue(position + "重复点位", set.add(flip[0] * n_cols + flip[1]));
            }
            solution.reset();
            set.clear();
        }
    }
}
