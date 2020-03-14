package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/minimum-moves-to-equal-array-elements/
 *
 * Given a non-empty integer array of size n, find the minimum number of moves required to make all array elements
 * equal, where a move is incrementing n - 1 elements by 1.
 *
 * Example:
 *
 * Input:
 * [1,2,3]
 *
 * Output:
 * 3
 *
 * Explanation:
 * Only three moves are needed (remember each move increments two elements):
 *
 * [1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]
 */
@RunWith(LeetCodeRunner.class)
public class Q453_MinimumMovesToEqualArrayElements {

    // n-1 个数字+1, 等效于1 个数字-1, 找出最小值, 让所有数字都到这个最小值就好
    @Answer
    public int minMoves(int[] nums) {
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
        }
        int res = 0;
        for (int num : nums) {
            res += num - min;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{1, 2, 3}).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 1, Integer.MAX_VALUE})
            .expect(Integer.MAX_VALUE - 1);

}
