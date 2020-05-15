package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/knight-probability-in-chessboard/
 *
 * On an NxN chessboard, a knight starts at the r-th row and c-th column and attempts to make exactly K moves. The
 * rows and columns are 0 indexed, so the top-left square is (0, 0), and the bottom-right square is (N-1, N-1).
 *
 * A chess knight has 8 possible moves it can make, as illustrated below. Each move is two squares in a cardinal
 * direction, then one square in an orthogonal direction.
 *
 * (图 Q688_PIC.png)
 *
 * Each time the knight is to move, it chooses one of eight possible moves uniformly at random (even if the piece
 * would go off the chessboard) and moves there.
 *
 * The knight continues moving until it has made exactly K moves or has moved off the chessboard. Return the
 * probability that the knight remains on the board after it has stopped moving.
 *
 *
 *
 * Example:
 *
 * Input: 3, 2, 0, 0
 * Output: 0.0625
 * Explanation: There are two moves (to (1,2), (2,1)) that will keep the knight on the board.
 * From each of those positions, there are also two moves that will keep the knight on the board.
 * The total probability the knight stays on the board is 0.0625.
 *
 *
 *
 * Note:
 *
 * N will be between 1 and 25.
 * K will be between 0 and 100.
 * The knight always initially starts on the board.
 */
@RunWith(LeetCodeRunner.class)
public class Q688_KnightProbabilityInChessboard {

    /**
     * 这题没什么太大的意思.
     * 看了Solution 才知道题目想要的做法是可能性的累加, 而不是求出最后总的比值,
     * 因为这2 种做法在K 变大了之后结果就不一样了.
     */
    @Answer
    public double knightProbability(int N, int K, int r, int c) {
        double[][][] probability = new double[K + 1][N][N];
        probability[0][r][c] = 1;
        for (int step = 0; step < K; step++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (probability[step][i][j] > 0) {
                        for (int[] d : DIRECTIONS) {
                            int ni = i + d[0], nj = j + d[1];
                            if (ni > -1 && ni < N && nj > -1 && nj < N) {
                                probability[step + 1][ni][nj] += probability[step][i][j] / 8;
                            }
                        }
                    }
                }
            }
        }
        double res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                res += probability[K][i][j];
            }
        }
        return res;
    }

    private static final int[][] DIRECTIONS = new int[][]{
            {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}};

    @TestData
    public DataExpectation example = DataExpectation.createWith(3, 2, 0, 0).expect(0.0625);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(3, 1, 0, 0).expect(0.25);

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(8)
            .addArgument(30)
            .addArgument(6)
            .addArgument(4)
            .expectDouble(0.00019, 6)
            .build();

}
