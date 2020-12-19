package q1450;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1414. Find the Minimum Number of Fibonacci Numbers Whose Sum Is K
 * https://leetcode.com/problems/find-the-minimum-number-of-fibonacci-numbers-whose-sum-is-k/
 *
 * Given an integer k, return the minimum number of Fibonacci numbers whose sum is equal to k. The same Fibonacci number
 * can be used multiple times.
 *
 * The Fibonacci numbers are defined as:
 *
 * F1 = 1
 * F2 = 1
 * Fn = Fn-1 + Fn-2 for n > 2.
 *
 * It is guaranteed that for the given constraints we can always find such Fibonacci numbers that sum up to k.
 *
 * Example 1:
 *
 * Input: k = 7
 * Output: 2
 * Explanation: The Fibonacci numbers are: 1, 1, 2, 3, 5, 8, 13, ...
 * For k = 7 we can use 2 + 5 = 7.
 *
 * Example 2:
 *
 * Input: k = 10
 * Output: 2
 * Explanation: For k = 10 we can use 2 + 8 = 10.
 *
 * Example 3:
 *
 * Input: k = 19
 * Output: 3
 * Explanation: For k = 19 we can use 1 + 5 + 13 = 19.
 *
 * Constraints:
 *
 * 1 <= k <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1414_FindTheMinimumNumberOfFibonacciNumbersWhoseSumIsK {

    /**
     * 贪婪算法直接从大到小找
     */
    @Answer
    public int findMinFibonacciNumbers(int k) {
        int idx = Collections.binarySearch(FIBONACCIS, k);
        return idx >= 0 ? 1 :
                1 + findMinFibonacciNumbers(k - FIBONACCIS.get(-idx - 2));
    }

    private static final List<Integer> FIBONACCIS = new ArrayList<>();

    static {
        FIBONACCIS.add(1);
        FIBONACCIS.add(1);
        int num = 2;
        while (num <= 10_0000_0000) {
            FIBONACCIS.add(num);
            num += FIBONACCIS.get(FIBONACCIS.size() - 2);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(7).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(10).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(19).expect(3);

}
