package q1350;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataAssertMethod;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1317. Convert Integer to the Sum of Two No-Zero Integers
 * https://leetcode.com/problems/convert-integer-to-the-sum-of-two-no-zero-integers/
 *
 * Given an integer n. No-Zero integer is a positive integer which doesn't contain any 0 in its decimal representation.
 *
 * Return a list of two integers [A, B] where:
 *
 * A and B are No-Zero integers.
 * A + B = n
 *
 * It's guarateed that there is at least one valid solution. If there are many valid solutions you can return any of
 * them.
 *
 * Example 1:
 *
 * Input: n = 2
 * Output: [1,1]
 * Explanation: A = 1, B = 1. A + B = n and both A and B don't contain any 0 in their decimal representation.
 *
 * Example 2:
 *
 * Input: n = 11
 * Output: [2,9]
 *
 * Example 3:
 *
 * Input: n = 10000
 * Output: [1,9999]
 *
 * Example 4:
 *
 * Input: n = 69
 * Output: [1,68]
 *
 * Example 5:
 *
 * Input: n = 1010
 * Output: [11,999]
 *
 * Constraints:
 *
 * 2 <= n <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1317_ConvertIntegerToTheSumOfTwoNoZeroIntegers {

    @Answer
    public int[] getNoZeroIntegers(int n) {
        for (int i = 1; i < n; i++) {
            if (!hasZero(i) && !hasZero(n - i)) {
                return new int[]{i, n - i};
            }
        }
        return null;
    }

    private boolean hasZero(int val) {
        while (val > 0) {
            if (val % 10 == 0) {
                return true;
            }
            val /= 10;
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = createTestData(2);

    private DataExpectation createTestData(int n) {
        return DataExpectation.create(n)
                .expect(null)
                .assertMethod((DataAssertMethod<int[]>) (expect, res, originAssertMethod) -> {
                    Assert.assertEquals(2, res.length);
                    Assert.assertEquals(n, res[0] + res[1]);
                    for (int val : res) {
                        Assert.assertTrue(val > 0);
                        while (val > 0) {
                            Assert.assertNotEquals(0, val % 10);
                            val /= 10;
                        }
                    }
                });
    }

    @TestData
    public DataExpectation example2 = createTestData(11);

    @TestData
    public DataExpectation example3 = createTestData(10000);

    @TestData
    public DataExpectation example4 = createTestData(69);

    @TestData
    public DataExpectation example5 = createTestData(1010);

}
