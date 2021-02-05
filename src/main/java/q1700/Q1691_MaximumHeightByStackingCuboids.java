package q1700;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1691. Maximum Height by Stacking Cuboids
 * https://leetcode.com/problems/maximum-height-by-stacking-cuboids/
 *
 * Given n cuboids where the dimensions of the ith cuboid is cuboids[i] = [widthi, lengthi, heighti] (0-indexed). Choose
 * a subset of cuboids and place them on each other.
 *
 * You can place cuboid i on cuboid j if widthi <= widthj and lengthi <= lengthj and heighti <= heightj. You can
 * rearrange any cuboid's dimensions by rotating it to put it on another cuboid.
 *
 * Return the maximum height of the stacked cuboids.
 *
 * Example 1:
 * <img src="./Q1691_PIC.jpg">
 * Input: cuboids = [[50,45,20],[95,37,53],[45,23,12]]
 * Output: 190
 * Explanation:
 * Cuboid 1 is placed on the bottom with the 53x37 side facing down with height 95.
 * Cuboid 0 is placed next with the 45x20 side facing down with height 50.
 * Cuboid 2 is placed next with the 23x12 side facing down with height 45.
 * The total height is 95 + 50 + 45 = 190.
 *
 * Example 2:
 *
 * Input: cuboids = [[38,25,45],[76,35,3]]
 * Output: 76
 * Explanation:
 * You can't place any of the cuboids on the other.
 * We choose cuboid 1 and rotate it so that the 35x3 side is facing down and its height is 76.
 *
 * Example 3:
 *
 * Input: cuboids = [[7,11,17],[7,17,11],[11,7,17],[11,17,7],[17,7,11],[17,11,7]]
 * Output: 102
 * Explanation:
 * After rearranging the cuboids, you can see that all cuboids have the same dimension.
 * You can place the 11x7 side down on all cuboids so their heights are 17.
 * The maximum height of stacked cuboids is 6 * 17 = 102.
 *
 * Constraints:
 *
 * n == cuboids.length
 * 1 <= n <= 100
 * 1 <= widthi, lengthi, heighti <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1691_MaximumHeightByStackingCuboids {

    /**
     * 暴力求解.
     */
    @Answer
    public int maxHeight(int[][] cuboids) {
        final int n = cuboids.length;
        int[][] cache = new int[n][6];
        boolean[] visited = new boolean[n];
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int d = 0; d < 6; d++) {
                res = Math.max(res, dfs(cache, visited, cuboids, i, d));
            }
        }
        return res;
    }

    private int dfs(int[][] cache, boolean[] visited, int[][] cuboids, int curr, int dir) {
        if (cache[curr][dir] != 0) {
            return cache[curr][dir];
        }
        final int n = cuboids.length;
        int width = cuboids[curr][DIRECTIONS[dir][0]];
        int length = cuboids[curr][DIRECTIONS[dir][1]];
        int height = cuboids[curr][DIRECTIONS[dir][2]];
        visited[curr] = true;
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }
            for (int d = 0; d < 6; d++) {
                int nw = cuboids[i][DIRECTIONS[d][0]];
                int nl = cuboids[i][DIRECTIONS[d][1]];
                int nh = cuboids[i][DIRECTIONS[d][2]];
                if (width >= nw && length >= nl && height >= nh) {
                    res = Math.max(res, dfs(cache, visited, cuboids, i, d));
                }
            }
        }
        res += height;
        cache[curr][dir] = res;
        visited[curr] = false;
        return res;
    }

    private int[][] DIRECTIONS = new int[][]{{0, 1, 2}, {0, 2, 1}, {1, 0, 2}, {1, 2, 0}, {2, 0, 1}, {2, 1, 0}};

    /**
     * LeetCode 上比较快的方式
     */
    @Answer
    public int maxHeight2(int[][] cuboids) {
        // 将正方体根据长宽高排序
        for (int[] cuboid : cuboids) {
            Arrays.sort(cuboid);
        }
        Arrays.sort(cuboids, (a, b) -> {
            if (a[2] != b[2]) {
                return a[2] - b[2];
            }
            if (a[1] != b[1]) {
                return a[1] - b[1];
            }
            return a[0] - b[0];
        });

        // 向前找到合适的正方体
        // 这部分的证明方式可以参考网络
        final int n = cuboids.length;
        int[] dp = new int[n];
        int res = 0;
        for (int i = 0; i < n; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (cuboids[i][0] >= cuboids[j][0]
                        && cuboids[i][1] >= cuboids[j][1]
                        && cuboids[i][2] >= cuboids[j][2]) {
                    max = Math.max(dp[j], max);
                }
            }
            dp[i] = max + cuboids[i][2];
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{50, 45, 20}, {95, 37, 53}, {45, 23, 12}})
            .expect(190);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{38, 25, 45}, {76, 35, 3}})
            .expect(76);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[][]{{7, 11, 17}, {7, 17, 11}, {11, 7, 17}, {11, 17, 7}, {17, 7, 11}, {17, 11, 7}})
            .expect(102);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{{36, 46, 41}, {15, 100, 100}, {75, 91, 59}, {13, 82, 64}})
            .expect(182);

}
