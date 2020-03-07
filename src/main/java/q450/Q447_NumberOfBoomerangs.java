package q450;

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
 * https://leetcode.com/problems/number-of-boomerangs/
 *
 * Given n points in the plane that are all pairwise distinct, a "boomerang" is a tuple of points (i, j, k) such that
 * the distance between i and j equals the distance between i and k (the order of the tuple matters).
 *
 * Find the number of boomerangs. You may assume that n will be at most 500 and coordinates of points are all in the
 * range [-10000, 10000] (inclusive).
 *
 * Example:
 *
 * Input:
 * [[0,0],[1,0],[2,0]]
 *
 * Output:
 * 2
 *
 * Explanation:
 * The two boomerangs are [[1,0],[0,0],[2,0]] and [[1,0],[2,0],[0,0]]
 */
@RunWith(LeetCodeRunner.class)
public class Q447_NumberOfBoomerangs {

    // 这种做法记录了每个点与其它点的距离和具体的点的数据, 比较低效.
    @Answer
    public int numberOfBoomerangs(int[][] points) {
        final int len = points.length;
        Map<Double, Set<Integer>>[] distances = new Map[len];
        for (int i = 0; i < len; i++) {
            distances[i] = new HashMap<>();
        }
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                double dist = Math.sqrt(Math.pow(points[i][0] - points[j][0], 2)
                        + Math.pow(points[i][1] - points[j][1], 2));
                distances[i].computeIfAbsent(dist, k -> new HashSet<>()).add(j);
                distances[j].computeIfAbsent(dist, k -> new HashSet<>()).add(i);
            }
        }
        int res = 0;
        for (int i = 0; i < len; i++) {
            for (Set<Integer> wing : distances[i].values()) {
                int size = wing.size();
                if (size > 0) {
                    res += size * (size - 1);
                }
            }
        }
        return res;
    }

    /**
     * 对上面解法的改进
     */
    @Answer
    public int numberOfBoomerangs2(int[][] points) {
        int res = 0;
        // map 保存从中心点出发的边的长度和这些边的数量.
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            map.clear();
            for (int j = 0; j < points.length; j++) {
                int dist = (points[i][0] - points[j][0]) * (points[i][0] - points[j][0]) +
                        (points[i][1] - points[j][1]) * (points[i][1] - points[j][1]);
                int count = map.getOrDefault(dist, 0) + 1;
                map.put(dist, count);
                // res += count * (count - 1) - (count - 1) * (count - 2) 简化后得
                res += 2 * (count - 1);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[][]{{0, 0}, {1, 0}, {2, 0}}).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{{0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}})
            .expect(20);

}
