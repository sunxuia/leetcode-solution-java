package q1050;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1001. Grid Illumination
 * https://leetcode.com/problems/grid-illumination/
 *
 * Given a grid of size N x N, each cell of this grid has a lamp which is intially turned off.
 *
 * Given an array of lamp positions lamps, where lamps[i] = [xi, yi] indicates that the lamp at grid[xi][yi] will be
 * turned on. When a lamp is turned on, it illiminates its cell and any cell in the same row, column or diagonal with
 * this this cell.
 *
 * Then you will be given a query array queries, where queries[i] = [xi, yi]. For the ith query, you should answer
 * whether grid[xi][yi] is illuminated or not. After answering the ith query, you should turn off the lamp at
 * grid[xi][yi] and all of its 8 adjacent lamps if they exist (i,e, lamps at adjacent cell which share side or
 * diagonal).
 *
 * Return an array of integers ans where each value ans[i] should be equal to the answer of the ith query queries[i].
 *
 * Example 1:
 * (图 Q1001_PIC1.png)
 * Input: N = 5, lamps = [[0,0],[4,4]], queries = [[1,1],[1,0]]
 * Output: [1,0]
 * Explanation: We have initial grid with all lamps turned off. In the above picture we see the grid after turning the
 * lamp at grid[0][0] on then turning the lamp at grid[4][4] on.
 * The first query asks if the lamp at grid[1][1] is illuminated or not (the blue square) and as it is illuminated, we
 * return 1. Then we turn off any lamp in the red square.
 * (图 Q1001_PIC2.png)
 * The second query asks if the lamp at grid[1][0] is illuminated or not (the blue square) and as it is not illustrated,
 * we return 0. Then we turn off any lamp in the red rectangle.
 * (图 Q1001_PIC3.png)
 *
 * Example 2:
 * Input: N = 5, lamps = [[0,0],[4,4]], queries = [[1,1],[1,1]]
 * Output: [1,1]
 *
 * Example 3:
 * Input: N = 5, lamps = [[0,0],[0,4]], queries = [[0,4],[0,1],[1,4]]
 * Output: [1,1,0]
 *
 * Constraints:
 *
 * 1 <= N <= 10^9
 * 0 <= lamps.length <= 20000
 * lamps[i].length == 2
 * 0 <= lamps[i][j] < N
 * 0 <= queries.length <= 20000
 * queries[i].length == 2
 * 0 <= queries[i][j] < N
 */
@RunWith(LeetCodeRunner.class)
public class Q1001_GridIllumination {

    @Answer
    public int[] gridIllumination(int N, int[][] lamps, int[][] queries) {
        final int last = N - 1;
        // 纵轴相同
        Map<Integer, Set<Integer>> yAligns = new HashMap<>();
        // 横轴相同
        Map<Integer, Set<Integer>> xAligns = new HashMap<>();
        // 左上 -> 右下对角线, 以右上角为编号0 开始, 对角线内以左上为编号0 开始
        Map<Integer, Set<Integer>> diagonals = new HashMap<>();
        // 右上 -> 左下对角线, 以左上角为编号0 开始, 对角线内以右上角为编号0 开始
        Map<Integer, Set<Integer>> reverseDiagonals = new HashMap<>();
        for (int[] lamp : lamps) {
            int x = lamp[0], y = lamp[1];
            addValue(yAligns, y, x);
            addValue(xAligns, x, y);
            addValue(diagonals, last + y - x, x > y ? y : x);
            addValue(reverseDiagonals, x + y, y < last - x ? y : last - x);
        }
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int x = queries[i][0], y = queries[i][1];
            if (yAligns.containsKey(y)
                    || xAligns.containsKey(x)
                    || diagonals.containsKey(last + y - x)
                    || reverseDiagonals.containsKey(x + y)) {
                res[i] = 1;
            }
            for (int[] direction : DIRECTIONS) {
                int nx = x + direction[0];
                int ny = y + direction[1];
                if (!yAligns.containsKey(ny) || !yAligns.get(ny).contains(nx)) {
                    continue;
                }
                removeValue(yAligns, ny, nx);
                removeValue(xAligns, nx, ny);
                removeValue(diagonals, last + ny - nx, nx > ny ? ny : nx);
                removeValue(reverseDiagonals, nx + ny,  ny < last - nx ? ny : last - nx);
            }
        }
        return res;
    }

    private void addValue(Map<Integer, Set<Integer>> map, Integer i, Integer j) {
        Set<Integer> set = map.get(i);
        if (set == null) {
            set = new HashSet<>();
            map.put(i, set);
        }
        set.add(j);
    }

    private void removeValue(Map<Integer, Set<Integer>> map, Integer i, Integer j) {
        Set<Integer> set = map.get(i);
        if (set.size() == 1) {
            map.remove(i);
        } else {
            set.remove(j);
        }
    }

    private static final int[][] DIRECTIONS = new int[][]{
            {-1, -1}, {0, -1}, {1, -1},
            {-1, 0}, {0, 0}, {1, 0},
            {-1, 1}, {0, 1}, {1, 1}};

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(5, new int[][]{{0, 0}, {4, 4}}, new int[][]{{1, 1}, {1, 0}})
            .expect(new int[]{1, 0});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(5, new int[][]{{0, 0}, {4, 4}}, new int[][]{{1, 1}, {1, 1}})
            .expect(new int[]{1, 1});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(5, new int[][]{{0, 0}, {0, 4}}, new int[][]{{0, 4}, {0, 1}, {1, 4}})
            .expect(new int[]{1, 1, 0});

}
