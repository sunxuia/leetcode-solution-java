package q450;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import org.junit.runner.RunWith;
import q050.Q042_TrappingRainWater;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/trapping-rain-water-ii/
 *
 * Given an m x n matrix of positive integers representing the height of each unit cell in a 2D elevation map,
 * compute the volume of water it is able to trap after raining.
 *
 *
 *
 * Note:
 *
 * Both m and n are less than 110. The height of each unit cell is greater than 0 and is less than 20,000.
 *
 *
 *
 * Example:
 *
 * Given the following 3x6 height map:
 * [
 * [1,4,3,1,3,2],
 * [3,2,1,3,2,4],
 * [2,3,3,2,3,1]
 * ]
 *
 * Return 4.
 *
 * (图 Q470_PIC1.png )
 *
 * The above image represents the elevation map [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]] before the rain.
 *
 * (图 Q407_PIC2.bmp)
 *
 * After the rain, water is trapped between the blocks. The total volume of water trapped is 4.
 *
 * 上一题 {@link Q042_TrappingRainWater}
 */
@RunWith(LeetCodeRunner.class)
public class Q407_TrappingRainWaterII {

    // 参考网上的思路, 通过模拟海平面上升的方式来计算每一层会漏出的数量, 然后减去这个数量, 就是水池的数量.
    // 这种方式勉强过了超时测试.
    @Answer
    public int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length < 3 || heightMap[0].length < 3) {
            return 0;
        }
        final int m = heightMap.length, n = heightMap[0].length;
        Integer[] heights = getHeight(heightMap);

        //  总的体积
        int total = m * n * heights[heights.length - 1];
        // 减去柱子的体积
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                total -= heightMap[i][j];
            }
        }

        // 四周的点
        Set<Integer> borders = new HashSet<>(m * 2 + n * 2 - 4);
        for (int i = 0; i < n; i++) {
            borders.add(i);
            borders.add(m * n - n + i);
        }
        for (int i = 1; i < m - 1; i++) {
            borders.add(i * n);
            borders.add(i * n + n - 1);
        }

        // 减去有缺口的体积
        // 访问过的点
        boolean[][] visited = new boolean[m][n];
        // 最近一次新加入的点
        List<Integer> list = new ArrayList<>();
        for (int h = 1; h < heights.length; h++) {
            final int height = heights[h];
            // 遍历四周, 找到小于海平面的地方.
            borders.removeIf(id -> add(height, heightMap, id / n, id % n, visited, list));
            // 遍历list, 向内寻找
            for (int i = 0; i < list.size(); i++) {
                int currY = list.get(i) / n;
                int currX = list.get(i) % n;
                add(height, heightMap, currY + 1, currX, visited, list);
                add(height, heightMap, currY, currX + 1, visited, list);
                add(height, heightMap, currY - 1, currX, visited, list);
                add(height, heightMap, currY, currX - 1, visited, list);
            }
            // 扣掉增长的海平面.
            total -= list.size() * (height - heights[h - 1]);
        }
        return total;
    }

    private Integer[] getHeight(int[][] heightMap) {
        final int m = heightMap.length, n = heightMap[0].length;
        Set<Integer> heightSet = new HashSet<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                heightSet.add(heightMap[i][j]);
            }
        }
        Integer[] res = heightSet.toArray(new Integer[heightSet.size()]);
        Arrays.sort(res);
        return res;
    }

    private boolean add(int height, int[][] heightMap, int y, int x,
            boolean[][] visited, List<Integer> list) {
        final int m = heightMap.length, n = heightMap[0].length, id = y * n + x;
        if (y >= 0 && x >= 0 && y < m && x < n
                && heightMap[y][x] < height
                && !visited[y][x]) {
            visited[y][x] = true;
            list.add(id);
            return true;
        }
        return false;
    }


    /**
     * LeetCode 上比较快的实现, 通过优先队列让海平面上升, 避免了上面多次重复遍历的情况.
     */
    @Answer
    public int trapRainWater2(int[][] heightMap) {
        if (heightMap == null || heightMap.length < 3 || heightMap[0].length < 3) {
            return 0;
        }

        int m = heightMap.length, n = heightMap[0].length;
        boolean[][] visited = new boolean[m][n];

        // 使用优先队列来保存点位, 让水平面按顺序上升.
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.h));

        // 添加四周的点, 从最低点进入. 四周的点因为无法蓄水, 所以海平面会直接淹没.
        for (int i = 0; i < m; i++) {
            visited[i][0] = visited[i][n - 1] = true;
            pq.add(new Node(i, 0, heightMap[i][0]));
            pq.add(new Node(i, n - 1, heightMap[i][n - 1]));
        }
        for (int j = 1; j < n - 1; j++) {
            visited[0][j] = visited[m - 1][j] = true;
            pq.add(new Node(0, j, heightMap[0][j]));
            pq.add(new Node(m - 1, j, heightMap[m - 1][j]));
        }

        int[] dx = new int[]{-1, 1, 0, 0};
        int[] dy = new int[]{0, 0, -1, 1};
        int res = 0;
        while (!pq.isEmpty()) {
            // 从最低点开始, 海平面升到 curr.h 这个位置.
            Node curr = pq.poll();
            for (int i = 0; i < 4; i++) {
                int x = curr.x + dx[i];
                int y = curr.y + dy[i];
                if (x < 0 || x >= m || y < 0 || y >= n || visited[x][y]) {
                    continue;
                }
                visited[x][y] = true;

                // 水位向四周扩散, 如果周边的高度低于水位, 则就会蓄水.
                // (四周会漏出去的情况在上面对四周的处理中已经排除了).
                res += Math.max(0, curr.h - heightMap[x][y]);

                // 将周边的这个点加入优先队列, 如果已经灌水了那么就算上水平面的高度.
                pq.add(new Node(x, y, Math.max(heightMap[x][y], curr.h)));
            }
        }

        return res;
    }

    private static class Node {

        int x;
        int y;
        int h;

        public Node(int x, int y, int h) {
            this.x = x;
            this.y = y;
            this.h = h;
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[][]{
            {1, 4, 3, 1, 3, 2},
            {3, 2, 1, 3, 2, 4},
            {2, 3, 3, 2, 3, 1}
    }).expect(4);

    @TestData
    public DataExpectation border = DataExpectation.create(new int[0][0]).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {12, 13, 1, 12},
            {13, 4, 13, 12},
            {13, 8, 10, 12},
            {12, 13, 12, 12},
            {13, 13, 13, 13}
    }).expect(14);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[][]{
            {18, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    }).expect(0);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read2DArray("Q407_LongTestData"))
            .expect(41564259);

}
