package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/reach-a-number/
 *
 * You are standing at position 0 on an infinite number line. There is a goal at position target.
 *
 * On each move, you can either go left or right. During the n-th move (starting from 1), you take n steps.
 *
 * Return the minimum number of steps required to reach the destination.
 *
 * Example 1:
 *
 * Input: target = 3
 * Output: 2
 * Explanation:
 * On the first move we step from 0 to 1.
 * On the second step we step from 1 to 3.
 *
 * Example 2:
 *
 * Input: target = 2
 * Output: 3
 * Explanation:
 * On the first move we step from 0 to 1.
 * On the second move we step  from 1 to -1.
 * On the third move we step from -1 to 2.
 *
 * Note:
 * target will be a non-zero integer in the range [-10^9, 10^9].
 */
@RunWith(LeetCodeRunner.class)
public class Q754_ReachANumber {

    /**
     * 涉及到了一些数字的数学规律.
     * https://www.cnblogs.com/grandyang/p/8456022.html
     */
    @Answer
    public int reachNumber(int target) {
        target = Math.abs(target);
        int n = (int) Math.ceil((-1.0 + Math.sqrt(1 + 8.0 * target)) / 2);
        int sum = n * (n + 1) / 2;
        if (sum == target || (sum - target & 1) == 0) {
            return n;
        }
        return n + (n % 2 == 1 ? 2 : 1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(3).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(2).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(-2).expect(3);

    @TestData
    public DataExpectation overTime = DataExpectation.create(-1000000000).expect(44723);

}
