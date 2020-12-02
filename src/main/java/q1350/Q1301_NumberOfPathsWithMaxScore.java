package q1350;

import java.util.List;
import org.junit.runner.RunWith;
import util.common.json.JsonTypeWrapper;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1301. Number of Paths with Max Score
 * https://leetcode.com/problems/number-of-paths-with-max-score/
 *
 * You are given a square board of characters. You can move on the board starting at the bottom right square marked with
 * the character 'S'.
 *
 * You need to reach the top left square marked with the character 'E'. The rest of the squares are labeled either with
 * a numeric character 1, 2, ..., 9 or with an obstacle 'X'. In one move you can go up, left or up-left (diagonally)
 * only if there is no obstacle there.
 *
 * Return a list of two integers: the first integer is the maximum sum of numeric characters you can collect, and the
 * second is the number of such paths that you can take to get that maximum sum, taken modulo 10^9 + 7.
 *
 * In case there is no path, return [0, 0].
 *
 * Example 1:
 * Input: board = ["E23","2X2","12S"]
 * Output: [7,1]
 * Example 2:
 * Input: board = ["E12","1X1","21S"]
 * Output: [4,2]
 * Example 3:
 * Input: board = ["E11","XXX","11S"]
 * Output: [0,0]
 *
 * Constraints:
 *
 * 2 <= board.length == board[i].length <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1301_NumberOfPathsWithMaxScore {

    @Answer
    public int[] pathsWithMaxScore(List<String> board) {
        final int mod = 10_0000_0007;
        final int[][] froms = new int[][]{{1, 1}, {1, 0}, {0, 1}};
        final int m = board.size(), n = board.get(0).length();
        // sums[i][j] 表示到 board[i][j] 时候的总分数
        int[][] sums = new int[m + 1][n + 1];
        int[][] paths = new int[m + 1][n + 1];
        paths[m][n] = 1;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                char c = board.get(i).charAt(j);
                if (c == 'X') {
                    continue;
                }
                int max = 0;
                for (int[] from : froms) {
                    max = Math.max(max, sums[i + from[0]][j + from[1]]);
                }
                for (int[] from : froms) {
                    if (max == sums[i + from[0]][j + from[1]]) {
                        paths[i][j] = (paths[i][j] + paths[i + from[0]][j + from[1]]) % mod;
                    }
                }
                if (paths[i][j] > 0) {
                    sums[i][j] = max + (c == 'E' || c == 'S' ? 0 : c - '0');
                }
            }
        }
        return new int[]{sums[0][0], paths[0][0]};
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(List.of(
            "E23",
            "2X2",
            "12S"
    )).expect(new int[]{7, 1});

    @TestData
    public DataExpectation example2 = DataExpectation.create(List.of(
            "E12",
            "1X1",
            "21S"
    )).expect(new int[]{4, 2});

    @TestData
    public DataExpectation example3 = DataExpectation.create(List.of(
            "E11",
            "XXX",
            "11S"
    )).expect(new int[]{0, 0});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(TestDataFileHelper.read(JsonTypeWrapper.STRING_LIST))
            .expect(new int[]{1773, 690285631});

}
