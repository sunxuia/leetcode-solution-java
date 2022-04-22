package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Hard] 1840. Maximum Building Height
 * https://leetcode.com/problems/maximum-building-height/
 *
 * You want to build n new buildings in a city. The new buildings will be built in a line and are labeled from 1 to n.
 *
 * However, there are city restrictions on the heights of the new buildings:
 *
 * 1. The height of each building must be a non-negative integer.
 * 2. The height of the first building must be 0.
 * 3. The height difference between any two adjacent buildings cannot exceed 1.
 *
 * Additionally, there are city restrictions on the maximum height of specific buildings. These restrictions are given
 * as a 2D integer array restrictions where restrictions[i] = [idi, maxHeighti] indicates that building idi must have a
 * height less than or equal to maxHeighti.
 *
 * It is guaranteed that each building will appear at most once in restrictions, and building 1 will not be in
 * restrictions.
 *
 * Return the maximum possible height of the tallest building.
 *
 * Example 1:
 * (图Q1840_PIC1.png)
 * Input: n = 5, restrictions = [[2,1],[4,1]]
 * Output: 2
 * Explanation: The green area in the image indicates the maximum allowed height for each building.
 * We can build the buildings with heights [0,1,2,1,2], and the tallest building has a height of 2.
 *
 * Example 2:
 * (图Q1840_PIC2.png)
 * Input: n = 6, restrictions = []
 * Output: 5
 * Explanation: The green area in the image indicates the maximum allowed height for each building.
 * We can build the buildings with heights [0,1,2,3,4,5], and the tallest building has a height of 5.
 *
 * Example 3:
 * (图Q1840_PIC3.png)
 * Input: n = 10, restrictions = [[5,3],[2,5],[7,4],[10,3]]
 * Output: 5
 * Explanation: The green area in the image indicates the maximum allowed height for each building.
 * We can build the buildings with heights [0,1,2,3,3,4,4,5,4,3], and the tallest building has a height of 5.
 *
 * Constraints:
 *
 * 2 <= n <= 10^9
 * 0 <= restrictions.length <= min(n - 1, 10^5)
 * 2 <= idi <= n
 * idi is unique.
 * 0 <= maxHeighti <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1840_MaximumBuildingHeight {

    /**
     * 从最矮的限制开始, 时间复杂度 O(NlogN), leetcode 上这种做法比较慢
     */
    @Answer
    public int maxBuilding(int n, int[][] restrictions) {
        Arrays.sort(restrictions, (a, b) -> a[1] == b[1] ? a[0] - b[0] : a[1] - b[1]);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(1, 0);
        for (int[] restriction : restrictions) {
            int index = restriction[0];
            int limit = restriction[1];
            Integer left = map.floorKey(index - 1);
            if (left != null) {
                int spread = map.get(left) + index - left;
                if (limit > spread) {
                    limit = spread;
                }
            }
            Integer right = map.ceilingKey(index + 1);
            if (right != null) {
                int spread = map.get(right) + right - index;
                if (limit > spread) {
                    limit = spread;
                }
            }
            map.put(index, limit);
        }
        if (!map.containsKey(n)) {
            int prev = map.floorKey(n);
            map.put(n, map.get(prev) + n - prev);
        }

        int res = 0, left = 0, leftLimit = 0;
        for (int index : map.keySet()) {
            int limit = map.get(index);
            int peek;
            if (leftLimit < limit) {
                peek = ((index - left) + (limit - leftLimit)) / 2 + leftLimit;
            } else {
                peek = ((index - left) + (leftLimit - limit)) / 2 + limit;
            }
            res = Math.max(res, peek);
            left = index;
            leftLimit = limit;
        }
        return res;
    }

    /**
     * leetcode 上最快的做法.
     */
    @Answer
    public int maxBuilding2(int n, int[][] restrictions) {
        // 按照从前往后的顺序排序, 并从后往前初步更新最高限制
        Arrays.sort(restrictions, Comparator.comparingInt(a -> a[0]));
        for (int i = restrictions.length - 2; i >= 0; i--) {
            int index = restrictions[i][0];
            int limit = restrictions[i][1];
            int rightIndex = restrictions[i + 1][0];
            int rightLimit = restrictions[i + 1][1];
            int spread = rightLimit + (rightIndex - index);
            if (limit > spread) {
                restrictions[i][1] = spread;
            }
        }

        // 从前往后寻找最高值
        int res = 0, left = 1, leftLimit = 0;
        for (int[] restriction : restrictions) {
            int index = restriction[0];
            int limit = restriction[1];
            int peek;
            if (limit >= leftLimit + index - left) {
                peek = leftLimit + index - left;
                leftLimit = peek;
            } else {
                peek = (leftLimit + index - left + limit) / 2;
                leftLimit = limit;
            }
            left = index;
            res = Math.max(res, peek);
        }
        if (left != n) {
            res = Math.max(res, leftLimit + n - left);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(5, new int[][]{{2, 1}, {4, 1}})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(6, new int[][]{})
            .expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(10, new int[][]{{5, 3}, {2, 5}, {7, 4}, {10, 3}})
            .expect(5);

}
