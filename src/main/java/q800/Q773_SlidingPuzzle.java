package q800;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/sliding-puzzle/
 *
 * On a 2x3 board, there are 5 tiles represented by the integers 1 through 5, and an empty square represented by 0.
 *
 * A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.
 *
 * The state of the board is solved if and only if the board is [[1,2,3],[4,5,0]].
 *
 * Given a puzzle board, return the least number of moves required so that the state of the board is solved. If it is
 * impossible for the state of the board to be solved, return -1.
 *
 * Examples:
 *
 * Input: board = [[1,2,3],[4,0,5]]
 * Output: 1
 * Explanation: Swap the 0 and the 5 in one move.
 *
 * Input: board = [[1,2,3],[5,4,0]]
 * Output: -1
 * Explanation: No number of moves will make the board solved.
 *
 * Input: board = [[4,1,2],[5,0,3]]
 * Output: 5
 * Explanation: 5 is the smallest number of moves that solves the board.
 * An example path:
 * After move 0: [[4,1,2],[5,0,3]]
 * After move 1: [[4,1,2],[0,5,3]]
 * After move 2: [[0,1,2],[4,5,3]]
 * After move 3: [[1,0,2],[4,5,3]]
 * After move 4: [[1,2,0],[4,5,3]]
 * After move 5: [[1,2,3],[4,5,0]]
 *
 * Input: board = [[3,2,4],[1,5,0]]
 * Output: 14
 *
 * Note:
 *
 * board will be a 2 x 3 array as described above.
 * board[i][j] will be a permutation of [0, 1, 2, 3, 4, 5].
 */
@RunWith(LeetCodeRunner.class)
public class Q773_SlidingPuzzle {

    /**
     * 用数字的每3 位表示board 中的一个元素值, 然后通过bfs 遍历所有可能的交换结果.
     * 相当于8进制的数字, 调试的时候可以通过 String.format("%o", value) 来查看结果.
     */
    @Answer
    public int slidingPuzzle(int[][] board) {
        int origin = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                origin = (origin << 3) + board[i][j];
            }
        }

        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(origin);
        Set<Integer> visited = new HashSet<>();
        int res = 0;
        while (!queue.isEmpty()) {
            for (int len = queue.size(); len > 0; len--) {
                int value = queue.poll();
                if (!visited.add(value)) {
                    continue;
                }
                if (value == 0123_450) {
                    return res;
                }

                int zero = zeroIndex(value);
                if (zero != 0 && zero != 9) {
                    queue.add(swap(value, zero, zero - 3));
                }
                if (zero != 15 && zero != 6) {
                    queue.add(swap(value, zero, zero + 3));
                }
                if (zero < 9) {
                    queue.add(swap(value, zero, zero + 9));
                } else {
                    queue.add(swap(value, zero, zero - 9));
                }
            }
            res++;
        }
        return -1;
    }

    private int zeroIndex(int value) {
        for (int i = 0; i <= 15; i += 3) {
            if ((value & 7 << i) == 0) {
                return i;
            }
        }
        return -1;
    }

    private int swap(int value, int pos1, int pos2) {
        int v1 = (value >> pos1) & 7;
        int v2 = (value >> pos2) & 7;
        return value + ((v1 - v2) << pos2) + ((v2 - v1) << pos1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 2, 3},
            {4, 0, 5}
    }).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 2, 3},
            {5, 4, 0}
    }).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {4, 1, 2},
            {5, 0, 3}
    }).expect(5);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{
            {3, 2, 4},
            {1, 5, 0}
    }).expect(14);

}
