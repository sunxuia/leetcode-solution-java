package q500;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/implement-rand10-using-rand7/
 *
 * Given a function rand7 which generates a uniform random integer in the range 1 to 7, write a function rand10 which
 * generates a uniform random integer in the range 1 to 10.
 *
 * Do NOT use system's Math.random().
 *
 *
 *
 * Example 1:
 *
 * Input: 1
 * Output: [7]
 *
 * Example 2:
 *
 * Input: 2
 * Output: [8,4]
 *
 * Example 3:
 *
 * Input: 3
 * Output: [8,1,10]
 *
 *
 *
 * Note:
 *
 * rand7 is predefined.
 * Each testcase has one argument: n, the number of times that rand10 is called.
 *
 *
 *
 * Follow up:
 *
 * What is the expected value for the number of calls to rand7() function?
 * Could you minimize the number of calls to rand7()?
 */
public class Q470_ImplementRand10UsingRand7 {

    private static abstract class SolBase {

        protected int rand7() {
            return new Random().nextInt(7) + 1;
        }
    }

    private static class Solution extends SolBase {

        // leetcode 中最快的方法将i 变成类变量, 这样每次调用都只需要i++ 就行了.
        public int rand10() {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += i * 7 + rand7() - 1;
            }
            return sum % 10 + 1;
        }
    }

    @Test
    public void testMethod() {
        Solution solution = new Solution();
        final int total = 20000;
        int[] count = new int[10];
        for (int i = 0; i < total; i++) {
            count[solution.rand10() - 1]++;
        }

        for (int i = 0; i < 10; i++) {
            double ratio = ((double) count[i]) / total;
            Assert.assertTrue(String.format("ratio of %d is %f", i + 1, ratio), 0.09 < ratio && ratio < 0.11);
        }
    }
}
