package q1250;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1222. Queens That Can Attack the King
 * https://leetcode.com/problems/queens-that-can-attack-the-king/
 *
 * On an 8x8 chessboard, there can be multiple Black Queens and one White King.
 *
 * Given an array of integer coordinates queens that represents the positions of the Black Queens, and a pair of
 * coordinates king that represent the position of the White King, return the coordinates of all the queens (in any
 * order) that can attack the King.
 *
 * Example 1:
 *
 * Input: queens = [[0,1],[1,0],[4,0],[0,4],[3,3],[2,4]], king = [0,0]
 * Output: [[0,1],[1,0],[3,3]]
 * Explanation:
 * The queen at [0,1] can attack the king cause they're in the same row.
 * The queen at [1,0] can attack the king cause they're in the same column.
 * The queen at [3,3] can attack the king cause they're in the same diagnal.
 * The queen at [0,4] can't attack the king cause it's blocked by the queen at [0,1].
 * The queen at [4,0] can't attack the king cause it's blocked by the queen at [1,0].
 * The queen at [2,4] can't attack the king cause it's not in the same row/column/diagnal as the king.
 *
 * Example 2:
 *
 * Input: queens = [[0,0],[1,1],[2,2],[3,4],[3,5],[4,4],[4,5]], king = [3,3]
 * Output: [[2,2],[3,4],[4,4]]
 *
 * Example 3:
 *
 * Input: queens = [[5,6],[7,7],[2,1],[0,7],[1,6],[5,1],[3,7],[0,3],[4,0],[1,2],[6,3],[5,0],[0,4],[2,2],[1,1],[6,4],
 * [5,4],[0,0],[2,6],[4,5],[5,2],[1,4],[7,5],[2,3],[0,5],[4,2],[1,0],[2,7],[0,1],[4,6],[6,1],[0,6],[4,3],[1,7]],
 * king = [3,4]
 * Output: [[2,3],[1,4],[1,6],[3,7],[4,3],[5,4],[4,5]]
 *
 * Constraints:
 *
 * 1 <= queens.length <= 63
 * queens[0].length == 2
 * 0 <= queens[i][j] < 8
 * king.length == 2
 * 0 <= king[0], king[1] < 8
 * At most one piece is allowed in a cell.
 */
@RunWith(LeetCodeRunner.class)
public class Q1222_QueensThatCanAttackTheKing {

    @Answer
    public List<List<Integer>> queensAttacktheKing(int[][] queens, int[] king) {
        List<Integer>[][] match = new List[3][3];
        List<List<Integer>> res = new ArrayList<>();
        for (int[] queen : queens) {
            int di = queen[0] - king[0];
            int dj = queen[1] - king[1];
            if (di != 0 && dj != 0 && di != dj && di != -dj) {
                continue;
            }
            int i = di < 0 ? 0 : di == 0 ? 1 : 2;
            int j = dj < 0 ? 0 : dj == 0 ? 1 : 2;
            if (match[i][j] == null) {
                match[i][j] = Arrays.asList(queen[0], queen[1]);
                continue;
            }
            int newOffset = Math.abs(di == 0 ? dj : di);
            int oldDi = match[i][j].get(0) - king[0];
            int oldDj = match[i][j].get(1) - king[1];
            int oldOffset = Math.abs(oldDi == 0 ? oldDj : oldDi);
            if (newOffset < oldOffset) {
                match[i][j] = Arrays.asList(queen[0], queen[1]);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (match[i][j] != null) {
                    res.add(match[i][j]);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{0, 1}, {1, 0}, {4, 0}, {0, 4}, {3, 3}, {2, 4}}, new int[]{0, 0})
            .expect(Arrays.asList(Arrays.asList(0, 1), Arrays.asList(1, 0), Arrays.asList(3, 3)))
            .unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{0, 0}, {1, 1}, {2, 2}, {3, 4}, {3, 5}, {4, 4}, {4, 5}}, new int[]{3, 3})
            .expect(Arrays.asList(Arrays.asList(2, 2), Arrays.asList(3, 4), Arrays.asList(4, 4)))
            .unOrder();

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(
            new int[][]{{5, 6}, {7, 7}, {2, 1}, {0, 7}, {1, 6}, {5, 1}, {3, 7}, {0, 3}, {4, 0}, {1, 2}, {6, 3}, {5, 0},
                    {0, 4}, {2, 2}, {1, 1}, {6, 4}, {5, 4}, {0, 0}, {2, 6}, {4, 5}, {5, 2}, {1, 4}, {7, 5}, {2, 3},
                    {0, 5}, {4, 2}, {1, 0}, {2, 7}, {0, 1}, {4, 6}, {6, 1}, {0, 6}, {4, 3}, {1, 7}}, new int[]{3, 4})
            .expect(Arrays.asList(Arrays.asList(2, 3), Arrays.asList(1, 4), Arrays.asList(1, 6), Arrays.asList(3, 7),
                    Arrays.asList(4, 3), Arrays.asList(5, 4), Arrays.asList(4, 5)))
            .unOrder()
            .orExpect(Arrays.asList(Arrays.asList(5, 6), Arrays.asList(0, 7), Arrays.asList(3, 7),
                    Arrays.asList(1, 2), Arrays.asList(0, 4), Arrays.asList(6, 4), Arrays.asList(5, 2)));

}
