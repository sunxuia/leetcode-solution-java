package q1750;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1743. Restore the Array From Adjacent Pairs
 * https://leetcode.com/problems/restore-the-array-from-adjacent-pairs/
 *
 * There is an integer array nums that consists of n unique elements, but you have forgotten it. However, you do
 * remember every pair of adjacent elements in nums.
 *
 * You are given a 2D integer array adjacentPairs of size n - 1 where each adjacentPairs[i] = [ui, vi] indicates that
 * the elements ui and vi are adjacent in nums.
 *
 * It is guaranteed that every adjacent pair of elements nums[i] and nums[i+1] will exist in adjacentPairs, either as
 * [nums[i], nums[i+1]] or [nums[i+1], nums[i]]. The pairs can appear in any order.
 *
 * Return the original array nums. If there are multiple solutions, return any of them.
 *
 * Example 1:
 *
 * Input: adjacentPairs = [[2,1],[3,4],[3,2]]
 * Output: [1,2,3,4]
 * Explanation: This array has all its adjacent pairs in adjacentPairs.
 * Notice that adjacentPairs[i] may not be in left-to-right order.
 *
 * Example 2:
 *
 * Input: adjacentPairs = [[4,-2],[1,4],[-3,1]]
 * Output: [-2,4,1,-3]
 * Explanation: There can be negative numbers.
 * Another solution is [-3,1,4,-2], which would also be accepted.
 *
 * Example 3:
 *
 * Input: adjacentPairs = [[100000,-100000]]
 * Output: [100000,-100000]
 *
 * Constraints:
 *
 * nums.length == n
 * adjacentPairs.length == n - 1
 * adjacentPairs[i].length == 2
 * 2 <= n <= 105
 * -10^5 <= nums[i], ui, vi <= 10^5
 * There exists some nums that has adjacentPairs as its pairs.
 */
@RunWith(LeetCodeRunner.class)
public class Q1743_RestoreTheArrayFromAdjacentPairs {

    @Answer
    public int[] restoreArray(int[][] adjacentPairs) {
        final int guard = Integer.MIN_VALUE;
        // 保存数字的左右邻居
        Map<Integer, int[]> sides = new HashMap<>();
        for (int[] adjacentPair : adjacentPairs) {
            int u = adjacentPair[0], v = adjacentPair[1];
            if (sides.containsKey(u)) {
                sides.get(u)[1] = v;
            } else {
                sides.put(u, new int[]{v, guard});
            }
            if (sides.containsKey(v)) {
                sides.get(v)[1] = u;
            } else {
                sides.put(v, new int[]{u, guard});
            }
        }

        // 找出数组的左边界的数字, 这个数字的特点是只有 1 个邻居
        int[] res = new int[sides.size()];
        for (var entry : sides.entrySet()) {
            if (entry.getValue()[1] == guard) {
                res[0] = entry.getKey();
                break;
            }
        }

        // 从左边开始还原
        int val = sides.remove(res[0])[0];
        for (int i = 1; i < res.length; i++) {
            res[i] = val;
            int[] adjust = sides.remove(val);
            val = adjust[0] == res[i - 1] ? adjust[1] : adjust[0];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = createTestData(new int[][]{{2, 1}, {3, 4}, {3, 2}});

    private DataExpectation createTestData(int[][] adjacentPairs) {
        return DataExpectation.create(adjacentPairs)
                .assertResult((int[] res) -> {
                    Map<Integer, Integer> indexes = new HashMap<>();
                    for (int i = 0; i < res.length; i++) {
                        indexes.put(res[i], i);
                    }
                    for (int[] adjacentPair : adjacentPairs) {
                        int u = adjacentPair[0], v = adjacentPair[1];
                        int ui = indexes.get(u), vi = indexes.get(v);
                        Assert.assertTrue(ui == vi + 1 || ui == vi - 1);
                    }
                });
    }

    @TestData
    public DataExpectation example2 = createTestData(new int[][]{{4, -2}, {1, 4}, {-3, 1}});

    @TestData
    public DataExpectation example3 = createTestData(new int[][]{{100000, -100000}});

}
