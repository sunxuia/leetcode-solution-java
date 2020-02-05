package q400;

import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/guess-number-higher-or-lower/
 *
 * We are playing the Guess Game. The game is as follows:
 *
 * I pick a number from 1 to n. You have to guess which number I picked.
 *
 * Every time you guess wrong, I'll tell you whether the number is higher or lower.
 *
 * You call a pre-defined API guess(int num) which returns 3 possible results (-1, 1, or 0):
 *
 * -1 : My number is lower
 * 1 : My number is higher
 * 0 : Congrats! You got it!
 *
 * Example :
 *
 * Input: n = 10, pick = 6
 * Output: 6
 */
public class Q374_GuessNumberHigherOrLower {

    // 一个难以理解的题目, 其它倒还好
    private static class GuessGame {

        private int val;

        void pick(int newVal) {
            val = newVal;
        }

        int guess(int n) {
            return Integer.compare(val, n);
        }
    }

    private static class Solution extends GuessGame {

        public int guessNumber(int n) {
            int start = 1, end = n;
            while (start < end) {
                int middle = start + (end - start) / 2;
                int c = guess(middle);
                if (c > 0) {
                    start = middle + 1;
                } else if (c == 0) {
                    return middle;
                } else {
                    end = middle - 1;
                }
            }
            return start;
        }
    }

    @Test
    public void testMethod() {
        assertMethod(10, 6);
        assertMethod(2126753390, 1702766719);
    }

    private void assertMethod(int n, int pick) {
        Solution sol = new Solution();
        sol.pick(pick);
        int res = sol.guessNumber(n);
        Assert.assertEquals(pick, res);
    }

}
