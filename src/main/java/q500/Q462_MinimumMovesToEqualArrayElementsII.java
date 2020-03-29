package q500;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/minimum-moves-to-equal-array-elements-ii/
 *
 * Given a non-empty integer array, find the minimum number of moves required to make all array elements equal, where
 * a move is incrementing a selected element by 1 or decrementing a selected element by 1.
 *
 * You may assume the array's length is at most 10,000.
 *
 * Example:
 *
 * Input:
 * [1,2,3]
 *
 * Output:
 * 2
 *
 * Explanation:
 * Only two moves are needed (remember each move increments or decrements one element):
 *
 * [1,2,3]  =>  [2,2,3]  =>  [2,2,2]
 *
 * 上一题 {@link Q453_MinimumMovesToEqualArrayElements}
 */
@RunWith(LeetCodeRunner.class)
public class Q462_MinimumMovesToEqualArrayElementsII {

    // https://www.cnblogs.com/grandyang/p/6089060.html
    // 是个找中位数的题目
    @Answer
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int median = nums[nums.length / 2];
        int res = 0;
        for (int num : nums) {
            res += Math.abs(num - median);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{1, 2, 3}).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 0, 0, 8, 6}).expect(14);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{1, 2}).expect(1);

}
