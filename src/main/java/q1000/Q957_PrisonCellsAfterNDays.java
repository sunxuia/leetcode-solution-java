package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 957. Prison Cells After N Days
 * https://leetcode.com/problems/prison-cells-after-n-days/
 *
 * There are 8 prison cells in a row, and each cell is either occupied or vacant.
 *
 * Each day, whether the cell is occupied or vacant changes according to the following rules:
 *
 * If a cell has two adjacent neighbors that are both occupied or both vacant, then the cell becomes occupied.
 * Otherwise, it becomes vacant.
 *
 * (Note that because the prison is a row, the first and the last cells in the row can't have two adjacent neighbors.)
 *
 * We describe the current state of the prison in the following way: cells[i] == 1 if the i-th cell is occupied, else
 * cells[i] == 0.
 *
 * Given the initial state of the prison, return the state of the prison after N days (and N such changes described
 * above.)
 *
 * Example 1:
 *
 * Input: cells = [0,1,0,1,1,0,0,1], N = 7
 * Output: [0,0,1,1,0,0,0,0]
 * Explanation:
 * The following table summarizes the state of the prison on each day:
 * Day 0: [0, 1, 0, 1, 1, 0, 0, 1]
 * Day 1: [0, 1, 1, 0, 0, 0, 0, 0]
 * Day 2: [0, 0, 0, 0, 1, 1, 1, 0]
 * Day 3: [0, 1, 1, 0, 0, 1, 0, 0]
 * Day 4: [0, 0, 0, 0, 0, 1, 0, 0]
 * Day 5: [0, 1, 1, 1, 0, 1, 0, 0]
 * Day 6: [0, 0, 1, 0, 1, 1, 0, 0]
 * Day 7: [0, 0, 1, 1, 0, 0, 0, 0]
 *
 * Example 2:
 *
 * Input: cells = [1,0,0,1,0,0,1,0], N = 1000000000
 * Output: [0,0,1,1,1,1,1,0]
 *
 * Note:
 *
 * cells.length == 8
 * cells[i] is in {0, 1}
 * 1 <= N <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q957_PrisonCellsAfterNDays {

    /**
     * 纯粹就是找数字变化规律的题
     */
    @Answer
    public int[] prisonAfterNDays(int[] cells, int N) {
        int mask = firstRoundMask(cells);
        for (N = --N % CYCLE[mask]; N > 0; N = --N % CYCLE[mask]) {
            mask = JUMP[mask];
        }

        cells[0] = cells[7] = 0;
        for (int i = 0; i < 6; i++) {
            cells[i + 1] = mask >> i & 1;
        }
        return cells;
    }

    private int firstRoundMask(int[] cells) {
        int res = 0;
        for (int i = 1; i <= 6; i++) {
            if (cells[i - 1] + cells[i + 1] != 1) {
                res |= 1 << i;
            }
        }
        return res / 2;
    }

    private static final int[] JUMP = new int[64];
    private static final int[] CYCLE = new int[64];

    static {
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 6; j++) {
                JUMP[i] |= ((i >> j - 1 & 1) + (i >> j + 1 & 1) + 1 & 1) << j;
            }
        }

        for (int i = 0; i < 64; i++) {
            int val = i;
            do {
                val = JUMP[val];
                CYCLE[i]++;
            } while (val != i);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{0, 1, 0, 1, 1, 0, 0, 1}, 7)
            .expect(new int[]{0, 0, 1, 1, 0, 0, 0, 0});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 0, 0, 1, 0, 0, 1, 0}, 10_0000_0000)
            .expect(new int[]{0, 0, 1, 1, 1, 1, 1, 0});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{0, 0, 1, 1, 1, 0, 0, 0}, 6_8252_9619)
            .expect(new int[]{0, 1, 0, 0, 1, 0, 1, 0});

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{0, 0, 1, 1, 1, 1, 0, 0}, 8)
            .expect(new int[]{0, 0, 0, 1, 1, 0, 0, 0});

}
