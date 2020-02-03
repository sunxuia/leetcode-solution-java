package q150;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/triangle/
 *
 * Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the
 * row below.
 *
 * For example, given the following triangle
 *
 * > [
 * >      [2],
 * >     [3,4],
 * >    [6,5,7],
 * >   [4,1,8,3]
 * > ]
 *
 * The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
 *
 * Note:
 *
 * Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the
 * triangle.
 */
@RunWith(LeetCodeRunner.class)
public class Q120_Triangle {

    @Answer
    public int minimumTotal(List<List<Integer>> triangle) {
        int[] sum = new int[triangle.size() + 1];
        Arrays.fill(sum, Integer.MAX_VALUE);
        sum[1] = 0;
        for (List<Integer> nums : triangle) {
            for (int i = nums.size(); i > 0; i--) {
                sum[i] = Math.min(sum[i - 1], sum[i]) + nums.get(i - 1);
            }
        }
        return Arrays.stream(sum).min().getAsInt();
    }

    // LeetCode 上最快的解法, 将从上到下改为从下到上.
    @Answer
    public int miniumTotalLeetCode(List<List<Integer>> triangle) {
        int[] sum = new int[triangle.size() + 1];
        for (int i = triangle.size() - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                sum[j] = Math.min(sum[j], sum[j + 1]) + triangle.get(i).get(j);
            }
        }
        return sum[0];
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(asList(
                    asList(2),
                    asList(3, 4),
                    asList(6, 5, 4),
                    asList(4, 1, 8, 3)
            )).expect(11);
}
