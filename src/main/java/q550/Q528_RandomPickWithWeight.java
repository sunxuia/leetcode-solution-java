package q550;

import java.util.Arrays;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import q500.Q497_RandomPointInNonOverlappingRectangles;

/**
 * https://leetcode.com/problems/random-pick-with-weight/
 *
 * Given an array w of positive integers, where w[i] describes the weight of index i, write a function pickIndex
 * which randomly picks an index in proportion to its weight.
 *
 * Note:
 *
 * 1 <= w.length <= 10000
 * 1 <= w[i] <= 10^5
 * pickIndex will be called at most 10000 times.
 *
 * Example 1:
 *
 * Input:
 * ["Solution","pickIndex"]
 * [[[1]],[]]
 * Output: [null,0]
 *
 * Example 2:
 *
 * Input:
 * ["Solution","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex"]
 * [[[1,3]],[],[],[],[],[]]
 * Output: [null,0,1,1,1,0]
 *
 * Explanation of Input Syntax:
 *
 * The input is two lists: the subroutines called and their arguments. Solution's constructor has one argument, the
 * array w. pickIndex has no arguments. Arguments are always wrapped with a list, even if there aren't any.
 */
public class Q528_RandomPickWithWeight {

    /**
     * 这题的解法类似于 {@link Q497_RandomPointInNonOverlappingRectangles}
     */
    private static class Solution {

        int[] sums;

        int total;

        Random random = new Random();

        public Solution(int[] w) {
            sums = new int[w.length];
            for (int i = 0; i < w.length; i++) {
                total += w[i];
                sums[i] = total;
            }
        }

        public int pickIndex() {
            int r = random.nextInt(total);
            int i = Arrays.binarySearch(sums, r);
            return i >= 0 ? i + 1 : -i - 1;
        }
    }

    @Test
    public void testMethod() {
        testCase(new int[]{1}, 3);
        testCase(new int[]{1, 3}, 1000);
    }

    private void testCase(int[] w, int pickTimes) {
        Solution solution = new Solution(w);
        int[] times = new int[w.length];
        for (int i = 0; i < pickTimes; i++) {
            times[solution.pickIndex()]++;
        }
        double total = 0;
        for (int weight : w) {
            total += weight;
        }
        for (int i = 0; i < w.length; i++) {
            double actualRatio = (double) times[i] / pickTimes;
            double expectRatio = w[i] / total;
            Assert.assertTrue(String.format("%d的概率%.3f不等于期望概率%.3f", i, actualRatio, expectRatio),
                    expectRatio - 0.1 < actualRatio && actualRatio < expectRatio + 0.1);
        }
    }

}
