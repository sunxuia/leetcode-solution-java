package q1650;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1643. Kth Smallest Instructions
 * https://leetcode.com/problems/kth-smallest-instructions/
 *
 * Bob is standing at cell (0, 0), and he wants to reach destination: (row, column). He can only travel right and down.
 * You are going to help Bob by providing instructions for him to reach destination.
 *
 * The instructions are represented as a string, where each character is either:
 *
 * 'H', meaning move horizontally (go right), or
 * 'V', meaning move vertically (go down).
 *
 * Multiple instructions will lead Bob to destination. For example, if destination is (2, 3), both "HHHVV" and "HVHVH"
 * are valid instructions.
 *
 * However, Bob is very picky. Bob has a lucky number k, and he wants the kth lexicographically smallest instructions
 * that will lead him to destination. k is 1-indexed.
 *
 * Given an integer array destination and an integer k, return the kth lexicographically smallest instructions that will
 * take Bob to destination.
 *
 * Example 1:
 * <img src="./Q1643_PIC1.png">
 * Input: destination = [2,3], k = 1
 * Output: "HHHVV"
 * Explanation: All the instructions that reach (2, 3) in lexicographic order are as follows:
 * ["HHHVV", "HHVHV", "HHVVH", "HVHHV", "HVHVH", "HVVHH", "VHHHV", "VHHVH", "VHVHH", "VVHHH"].
 *
 * Example 2:
 * <img src="./Q1643_PIC2.png">
 * Input: destination = [2,3], k = 2
 * Output: "HHVHV"
 *
 * Example 3:
 * <img src="./Q1643_PIC3.png">
 * Input: destination = [2,3], k = 3
 * Output: "HHVVH"
 *
 * Constraints:
 *
 * destination.length == 2
 * 1 <= row, column <= 15
 * 1 <= k <= nCr(row + column, row), where nCr(a, b) denotes a choose b.
 */
@RunWith(LeetCodeRunner.class)
public class Q1643_KthSmallestInstructions {

    /**
     * 组合数求解, 参考 LeetCode 的解答.
     */
    @Answer
    public String kthSmallestPath(int[] destination, int k) {
        final int m = destination[0], n = destination[1];

        // dp[i][j] 表示高宽为 i, j 的矩形的组合数量
        int[][] dp = new int[m + 1][n + 1];
        Arrays.fill(dp[m], 1);
        for (int i = 0; i < m; i++) {
            dp[i][n] = 1;
        }
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                dp[i][j] = dp[i][j + 1] + dp[i + 1][j];
            }
        }

        StringBuilder sb = new StringBuilder(m + n);
        int v = 0, h = 0;
        while (v < m && h < n) {
            if (dp[v][h + 1] >= k) {
                // 水平走仍然 >= k
                sb.append('H');
                h++;
            } else {
                // 水平走 <k, 则需要向下走
                sb.append('V');
                k -= dp[v][h + 1];
                v++;
            }
        }
        // V 用完了
        if (v == m) {
            while (h != n) {
                sb.append('H');
                h++;
            }
        }
        // H 用完了
        if (h == n) {
            while (v != m) {
                sb.append('V');
                v++;
            }
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{2, 3}, 1).expect("HHHVV");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{2, 3}, 2).expect("HHVHV");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{2, 3}, 3).expect("HHVVH");

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(new int[]{15, 15}, 155117520)
            .expect("VVVVVVVVVVVVVVVHHHHHHHHHHHHHHH");

}
