package q1150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1128. Number of Equivalent Domino Pairs
 * https://leetcode.com/problems/number-of-equivalent-domino-pairs/
 *
 * Given a list of dominoes, dominoes[i] = [a, b] is equivalent to dominoes[j] = [c, d] if and only if either (a==c and
 * b==d), or (a==d and b==c) - that is, one domino can be rotated to be equal to another domino.
 *
 * Return the number of pairs (i, j) for which 0 <= i < j < dominoes.length, and dominoes[i] is equivalent to
 * dominoes[j].
 *
 * Example 1:
 * Input: dominoes = [[1,2],[2,1],[3,4],[5,6]]
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= dominoes.length <= 40000
 * 1 <= dominoes[i][j] <= 9
 */
@RunWith(LeetCodeRunner.class)
public class Q1128_NumberOfEquivalentDominoPairs {

    @Answer
    public int numEquivDominoPairs(int[][] dominoes) {
        int[][] counts = new int[10][10];
        for (int[] domino : dominoes) {
            counts[domino[0]][domino[1]]++;
        }

        int res = 0;
        for (int i = 1; i <= 9; i++) {
            if (counts[i][i] >= 2) {
                res += counts[i][i] * (counts[i][i] - 1) / 2;
            }
            for (int j = i + 1; j <= 9; j++) {
                int sum = counts[i][j] + counts[j][i];
                if (sum >= 2) {
                    res += sum * (sum - 1) / 2;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(new int[][]{{1, 2}, {2, 1}, {3, 4}, {5, 6}})
            .expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{{1, 2}, {1, 2}, {1, 1}, {1, 2}, {2, 2}})
            .expect(3);

}
