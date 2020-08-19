package q1000;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 962. Maximum Width Ramp
 * https://leetcode.com/problems/maximum-width-ramp/
 *
 * Given an array A of integers, a ramp is a tuple (i, j) for which i < j and A[i] <= A[j].
 * The width of such a ramp is j - i.
 *
 * Find the maximum width of a ramp in A.  If one doesn't exist, return 0.
 *
 * Example 1:
 *
 * Input: [6,0,8,2,1,5]
 * Output: 4
 * Explanation:
 * The maximum width ramp is achieved at (i, j) = (1, 5): A[1] = 0 and A[5] = 5.
 *
 * Example 2:
 *
 * Input: [9,8,1,0,1,9,4,0,4,1]
 * Output: 7
 * Explanation:
 * The maximum width ramp is achieved at (i, j) = (2, 9): A[2] = 1 and A[9] = 1.
 *
 * Note:
 *
 * 2 <= A.length <= 50000
 * 0 <= A[i] <= 50000
 */
@RunWith(LeetCodeRunner.class)
public class Q962_MaximumWidthRamp {

    @Answer
    public int maxWidthRamp(int[] A) {
        int res = 0;
        List<Integer> lower = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            // (这段可以优化为二分查找)
            int j = lower.size();
            while (0 < j && A[lower.get(j - 1)] <= A[i]) {
                j--;
            }
            if (j == lower.size()) {
                lower.add(i);
            } else {
                res = Math.max(res, i - lower.get(j));
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{6, 0, 8, 2, 1, 5}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{9, 8, 1, 0, 1, 9, 4, 0, 4, 1}).expect(7);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{3, 4, 1, 2}).expect(1);

}
