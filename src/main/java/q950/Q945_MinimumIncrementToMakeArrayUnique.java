package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 945. Minimum Increment to Make Array Unique
 * https://leetcode.com/problems/minimum-increment-to-make-array-unique/
 *
 * Given an array of integers A, a move consists of choosing any A[i], and incrementing it by 1.
 *
 * Return the least number of moves to make every value in A unique.
 *
 * Example 1:
 *
 * Input: [1,2,2]
 * Output: 1
 * Explanation:  After 1 move, the array could be [1, 2, 3].
 *
 * Example 2:
 *
 * Input: [3,2,1,2,1,7]
 * Output: 6
 * Explanation:  After 6 moves, the array could be [3, 4, 1, 2, 5, 7].
 * It can be shown with 5 or less moves that it is impossible for the array to have all unique values.
 *
 * Note:
 *
 * 0 <= A.length <= 40000
 * 0 <= A[i] < 40000
 */
@RunWith(LeetCodeRunner.class)
public class Q945_MinimumIncrementToMakeArrayUnique {

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 2}).expect(1);
    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 2, 1, 2, 1, 7}).expect(6);

    @Answer
    public int minIncrementForUnique(int[] A) {
        int[] counts = new int[80000];
        int max = 0;
        for (int i : A) {
            counts[i]++;
            max = Math.max(max, i);
        }
        int res = 0, remain = 0;
        for (int i = 0; i < 80000; i++) {
            res += remain;
            remain = Math.max(remain + counts[i] - 1, 0);
        }
        return res;
    }

}
