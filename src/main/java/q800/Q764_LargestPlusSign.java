package q800;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/largest-plus-sign/
 *
 * In a 2D grid from (0, 0) to (N-1, N-1), every cell contains a 1, except those cells in the given list mines which
 * are 0. What is the largest axis-aligned plus sign of 1s contained in the grid? Return the order of the plus sign.
 * If there is none, return 0.
 *
 * An "axis-aligned plus sign of 1s of order k" has some center grid[x][y] = 1 along with 4 arms of length k-1 going
 * up, down, left, and right, and made of 1s. This is demonstrated in the diagrams below. Note that there could be 0s
 * or 1s beyond the arms of the plus sign, only the relevant area of the plus sign is checked for 1s.
 *
 * Examples of Axis-Aligned Plus Signs of Order k:
 *
 * Order 1:
 * 000
 * 010
 * 000
 *
 * Order 2:
 * 00000
 * 00100
 * 01110
 * 00100
 * 00000
 *
 * Order 3:
 * 0000000
 * 0001000
 * 0001000
 * 0111110
 * 0001000
 * 0001000
 * 0000000
 *
 * Example 1:
 *
 * Input: N = 5, mines = [[4, 2]]
 * Output: 2
 * Explanation:
 * 11111
 * 11111
 * 11111
 * 11111
 * 11011
 * In the above grid, the largest plus sign can only be order 2.  One of them is marked in bold.
 *
 * Example 2:
 *
 * Input: N = 2, mines = []
 * Output: 1
 * Explanation:
 * There is no plus sign of order 2, but there is of order 1.
 *
 * Example 3:
 *
 * Input: N = 1, mines = [[0, 0]]
 * Output: 0
 * Explanation:
 * There is no plus sign, so return 0.
 *
 * Note:
 *
 * 1. N will be an integer in the range [1, 500].
 * 2. mines will have length at most 5000.
 * 3. mines[i] will be length 2 and consist of integers in the range [0, N-1].
 * 4. (Additionally, programs submitted in C, C++, or C# will be judged with a slightly smaller time limit.)
 */
@RunWith(LeetCodeRunner.class)
public class Q764_LargestPlusSign {

    @Answer
    public int orderOfLargestPlusSign(int N, int[][] mines) {
        // 4 个元素分别表示上左下右的1 的数量 (遇到0 停止), +2 是因为4 个边作为哨兵.
        int[][][] margin = new int[N + 2][N + 2][4];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                Arrays.fill(margin[i][j], 1);
            }
        }
        // 设置0 的点
        for (int[] mine : mines) {
            Arrays.fill(margin[mine[0] + 1][mine[1] + 1], 0);
        }

        // 设置1 的长度
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (margin[i][j][0] > 0) {
                    margin[i][j][0] += margin[i - 1][j][0];
                    margin[i][j][1] += margin[i][j - 1][1];
                }
            }
        }
        for (int i = N; i > 0; i--) {
            for (int j = N; j > 0; j--) {
                if (margin[i][j][0] > 0) {
                    margin[i][j][2] += margin[i + 1][j][2];
                    margin[i][j][3] += margin[i][j + 1][3];
                }
            }
        }

        // 获取结果
        int res = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                res = Math.max(res, Math.min(
                        Math.min(margin[i][j][0], margin[i][j][1]),
                        Math.min(margin[i][j][2], margin[i][j][3])));
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(5, new int[][]{{4, 2}}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, new int[0][0]).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(1, new int[][]{{0, 0}}).expect(0);

}
