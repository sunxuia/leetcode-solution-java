package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/preimage-size-of-factorial-zeroes-function/
 *
 * Let f(x) be the number of zeroes at the end of x!. (Recall that x! = 1 * 2 * 3 * ... * x, and by convention, 0! = 1.)
 *
 * For example, f(3) = 0 because 3! = 6 has no zeroes at the end, while f(11) = 2 because 11! = 39916800 has 2 zeroes
 * at the end. Given K, find how many non-negative integers x have the property that f(x) = K.
 *
 * Example 1:
 * Input: K = 0
 * Output: 5
 * Explanation: 0!, 1!, 2!, 3!, and 4! end with K = 0 zeroes.
 *
 * Example 2:
 * Input: K = 5
 * Output: 0
 * Explanation: There is no x such that x! ends in K = 5 zeroes.
 *
 * Note:
 *
 * K will be an integer in the range [0, 10^9].
 */
@RunWith(LeetCodeRunner.class)
public class Q793_PreimageSizeOfFactorialZeroesFunction {

    // https://www.cnblogs.com/grandyang/p/9214055.html
    @Answer
    public int preimageSizeFZF(int K) {
        long left = 0, right = 5L * (K + 1);
        while (left < right) {
            long mid = left + (right - left) / 2;
            long count = numOfTrailingZeros(mid);
            if (count == K) {
                return 5;
            } else if (count < K) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return 0;
    }

    private long numOfTrailingZeros(long x) {
        long res = 0;
        while (x > 0) {
            res += x /= 5;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(0).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(5).expect(0);

}
