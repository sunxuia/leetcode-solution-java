package q1250;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1220. Count Vowels Permutation
 * https://leetcode.com/problems/count-vowels-permutation/
 *
 * Given an integer n, your task is to count how many strings of length n can be formed under the following rules:
 *
 * Each character is a lower case vowel ('a', 'e', 'i', 'o', 'u')
 * Each vowel 'a' may only be followed by an 'e'.
 * Each vowel 'e' may only be followed by an 'a' or an 'i'.
 * Each vowel 'i' may not be followed by another 'i'.
 * Each vowel 'o' may only be followed by an 'i' or a 'u'.
 * Each vowel 'u' may only be followed by an 'a'.
 *
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: n = 1
 * Output: 5
 * Explanation: All possible strings are: "a", "e", "i" , "o" and "u".
 *
 * Example 2:
 *
 * Input: n = 2
 * Output: 10
 * Explanation: All possible strings are: "ae", "ea", "ei", "ia", "ie", "io", "iu", "oi", "ou" and "ua".
 *
 * Example 3:
 *
 * Input: n = 5
 * Output: 68
 *
 * Constraints:
 *
 * 1 <= n <= 2 * 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1220_CountVowelsPermutation {

    @Answer
    public int countVowelPermutation(int n) {
        final int mod = 10_0000_0007;
        // 0: a, 1: e, 2: i, 3: o, 4: u
        int[][] nums = new int[2][5];
        Arrays.fill(nums[1], 1);
        for (int t = 2; t <= n; t++) {
            int[] from = nums[(t - 1) % 2], to = nums[t % 2];
            to[0] = from[1];
            to[1] = (from[0] + from[2]) % mod;
            to[2] = ((from[0] + from[1]) % mod + (from[3] + from[4]) % mod) % mod;
            to[3] = (from[2] + from[4]) % mod;
            to[4] = from[0];
        }
        int res = 0;
        for (int count : nums[n % 2]) {
            res = (res + count) % mod;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(1).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(2).expect(10);

    @TestData
    public DataExpectation example3 = DataExpectation.create(5).expect(68);

}
