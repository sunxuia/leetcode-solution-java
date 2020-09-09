package q1050;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1036. Escape a Large Maze
 * https://leetcode.com/problems/escape-a-large-maze/
 *
 * In a 1 million by 1 million grid, the coordinates of each grid square are (x, y) with 0 <= x, y < 10^6.
 *
 * We start at the source square and want to reach the target square.  Each move, we can walk to a 4-directionally
 * adjacent square in the grid that isn't in the given list of blocked squares.
 *
 * Return true if and only if it is possible to reach the target square through a sequence of moves.
 *
 * Example 1:
 *
 * Input: blocked = [[0,1],[1,0]], source = [0,0], target = [0,2]
 * Output: false
 * Explanation:
 * The target square is inaccessible starting from the source square, because we can't walk outside the grid.
 *
 * Example 2:
 *
 * Input: blocked = [], source = [0,0], target = [999999,999999]
 * Output: true
 * Explanation:
 * Because there are no blocked cells, it's possible to reach the target square.
 *
 * Note:
 *
 * 0 <= blocked.length <= 200
 * blocked[i].length == 2
 * 0 <= blocked[i][j] < 10^6
 * source.length == target.length == 2
 * 0 <= source[i][j], target[i][j] < 10^6
 * source != target
 */
@RunWith(LeetCodeRunner.class)
public class Q1036_EscapeALargeMaze {

    /**
     * 因为 blocked.length <= 200, 而格子是100万*100万的,
     * 所以如果不能相遇, 则 blocked 连成的线条(配合边界)要么围住source, 要么围住target,
     * 这个围城的最大长度也就是 blocked.length, 也就是source/ target 在角落, blocked 按照斜线排列.
     * 只要从source/ target 出发, 走过的最长距离 >= blocked.length 也就说明能够走出包围.
     * 或者在尝试走出包围的时候遇到了target, 则相遇成功.
     */
    @Answer
    public boolean isEscapePossible(int[][] blocked, int[] source, int[] target) {
        final int[][] forwards = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        Map<Integer, Set<Integer>> blocks = new HashMap<>();
        for (int[] block : blocked) {
            blocks.computeIfAbsent(block[0], k -> new HashSet<>()).add(block[1]);
        }

        // 从source 出发, 如果能最多走出blocked.length 距离则说明source 没有被包围.
        Queue<int[]> queue = new ArrayDeque<>();
        // 缓存是必要的
        Set<Long> visited = new HashSet<>();
        queue.add(source);
        for (int dist = 0; dist < blocked.length && !queue.isEmpty(); dist++) {
            visited.clear();
            for (int len = queue.size(); len > 0; len--) {
                int[] pos = queue.poll();
                // 与target 相遇了就不管有没有包围圈了
                if (pos[0] == target[0] && pos[1] == target[1]) {
                    return true;
                }
                for (int[] forward : forwards) {
                    int i = pos[0] + forward[0];
                    int j = pos[1] + forward[1];
                    if (i == -1 || j == -1 || i == 100_0000 || j == 100_0000
                            || Math.abs(i - source[0]) + Math.abs(j - source[1]) != dist + 1
                            || blocks.containsKey(i) && blocks.get(i).contains(j)
                            || !visited.add(100_0000L * i + j)) {
                        continue;
                    }
                    queue.add(new int[]{i, j});
                }
            }
        }
        if (queue.isEmpty()) {
            // 没有距离超过 blocked.length 的格子, 说明被包围了
            return false;
        }

        // 从target 开始出发, 尝试走出包围圈.
        queue.clear();
        queue.add(target);
        for (int dist = 0; dist < blocked.length && !queue.isEmpty(); dist++) {
            visited.clear();
            for (int len = queue.size(); len > 0; len--) {
                int[] pos = queue.poll();
                for (int[] forward : forwards) {
                    int i = pos[0] + forward[0];
                    int j = pos[1] + forward[1];
                    if (i == -1 || j == -1 || i == 100_0000 || j == 100_0000
                            || Math.abs(i - target[0]) + Math.abs(j - target[1]) != dist + 1
                            || blocks.containsKey(i) && blocks.get(i).contains(j)
                            || !visited.add(100_0000L * i + j)) {
                        continue;
                    }
                    queue.add(new int[]{i, j});
                }
            }
        }
        return !queue.isEmpty();
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{0, 1}, {1, 0}}, new int[]{0, 0}, new int[]{0, 2})
            .expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{}, new int[]{0, 0}, new int[]{999999, 999999})
            .expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[][]{{691938, 300406}, {710196, 624190}, {858790, 609485}, {268029, 225806},
                            {200010, 188664}, {132599, 612099}, {329444, 633495}, {196657, 757958}, {628509, 883388}},
                    new int[]{655988, 180910}, new int[]{267728, 840949})
            .expect(true);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(
                    new int[][]{{887968, 76029}, {478040, 100615}, {413693, 211521}, {84037, 225089}, {516868, 914974},
                            {161225, 96123}, {320456, 162051}, {70328, 776373}, {642302, 711843}, {115275, 37429},
                            {499329, 577780}, {735068, 364357}, {345167, 829298}, {135661, 972336}, {61002, 836637},
                            {951394, 543688}, {412217, 135798}, {710786, 140643}, {753732, 863686}, {774842, 591769},
                            {993338, 581144}, {153690, 512200}, {705935, 957690}, {140374, 898192}, {103638, 250560}},
                    new int[]{147020, 428481}, new int[]{197254, 373912})
            .expect(true);

}
