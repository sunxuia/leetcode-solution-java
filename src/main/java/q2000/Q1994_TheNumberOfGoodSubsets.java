package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1994. The Number of Good Subsets
 * https://leetcode.com/problems/the-number-of-good-subsets/
 *
 * You are given an integer array nums. We call a subset of nums good if its product can be represented as a product of
 * one or more distinct prime numbers.
 *
 * For example, if nums = [1, 2, 3, 4]:
 *
 * - [2, 3], [1, 2, 3], and [1, 3] are good subsets with products 6 = 2*3, 6 = 2*3, and 3 = 3 respectively.
 * - [1, 4] and [4] are not good subsets with products 4 = 2*2 and 4 = 2*2 respectively.
 *
 *
 *
 * Return the number of different good subsets in nums modulo 10^9 + 7.
 *
 * A subset of nums is any array that can be obtained by deleting some (possibly none or all) elements from nums. Two
 * subsets are different if and only if the chosen indices to delete are different.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4]
 * Output: 6
 * Explanation: The good subsets are:
 * - [1,2]: product is 2, which is the product of distinct prime 2.
 * - [1,2,3]: product is 6, which is the product of distinct primes 2 and 3.
 * - [1,3]: product is 3, which is the product of distinct prime 3.
 * - [2]: product is 2, which is the product of distinct prime 2.
 * - [2,3]: product is 6, which is the product of distinct primes 2 and 3.
 * - [3]: product is 3, which is the product of distinct prime 3.
 *
 * Example 2:
 *
 * Input: nums = [4,2,3,15]
 * Output: 5
 * Explanation: The good subsets are:
 * - [2]: product is 2, which is the product of distinct prime 2.
 * - [2,3]: product is 6, which is the product of distinct primes 2 and 3.
 * - [2,15]: product is 30, which is the product of distinct primes 2, 3, and 5.
 * - [3]: product is 3, which is the product of distinct prime 3.
 * - [15]: product is 15, which is the product of distinct primes 3 and 5.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 30
 */
@RunWith(LeetCodeRunner.class)
public class Q1994_TheNumberOfGoodSubsets {

    @Answer
    public int numberOfGoodSubsets(int[] nums) {
        int[] counts = new int[31];
        for (int num : nums) {
            if (MASKS[num] == 0) {
                if (num == 1) {
                    counts[1]++;
                }
            } else {
                counts[num]++;
            }
        }
        // 1 的组合数量
        int one = 1;
        for (int i = 0; i < counts[1]; i++) {
            one = one * 2 % MOD;
        }
        return (int) (dfs(counts, 2, 0) * one % MOD);
    }

    private long dfs(int[] counts, int i, int mask) {
        if (i == 31) {
            return 0;
        }
        if (counts[i] == 0 || (mask & MASKS[i]) != 0) {
            return dfs(counts, i + 1, mask);
        }
        // 只有自己数字的组合数
        long only = counts[i];
        // 没有自己数字的组合数
        long noUse = dfs(counts, i + 1, mask);
        // 包括自己数字在内后的后面数字的组合数
        long use = only * dfs(counts, i + 1, mask | MASKS[i]);
        return (only + noUse + use) % MOD;
    }

    private static final int MOD = 10_0000_0007;

    /**
     * 表示不同数字的唯一质数组合掩码(不合题意则为0)
     */
    private static final int[] MASKS = new int[31];

    static {
        final int[] primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        loop:
        for (int num = 2; num <= 30; num++) {
            int mask = 0;
            for (int i = 0; i < primes.length; i++) {
                if (num % primes[i] == 0) {
                    if (num / primes[i] % primes[i] == 0) {
                        continue loop;
                    }
                    mask |= 1 << i;
                }
            }
            MASKS[num] = mask;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 4}).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{4, 2, 3, 15}).expect(5);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{6, 8, 1, 8, 6, 5, 6, 11, 17}).expect(62);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[]{10, 11, 5, 1, 10, 1, 3, 1, 26, 11, 6, 1, 1, 15, 1, 7, 22, 1, 1, 1, 1, 1, 23, 1, 29, 5, 6,
                    1, 1, 29, 1, 1, 21, 19, 1, 1, 1, 2, 1, 11, 1, 15, 1, 22, 14, 1, 1, 1, 1, 6, 7, 1, 14, 3, 5, 1, 22,
                    1, 1, 1, 17, 1, 29, 2, 1, 15, 10, 1, 5, 7, 1, 1, 1, 30, 1, 30, 1, 21, 10, 1, 1, 1, 1, 1, 2, 6, 5, 7,
                    3, 1, 1, 19, 29, 1, 7, 13, 14, 1, 5, 26, 19, 11, 1, 1, 1, 1, 1, 1, 1, 1, 22, 15, 1, 1, 13, 1, 17, 1,
                    1, 1, 13, 6, 1, 10, 1, 1, 17, 1, 1, 3, 14, 7, 17, 1, 13, 1, 1, 1, 1, 1, 11, 1, 1, 6, 1, 1, 1, 1, 1,
                    2, 1, 30, 2, 26, 1, 1, 14, 1, 26, 29, 30, 1, 13, 21, 1, 1, 14, 21, 1, 23, 1, 15, 23, 21, 1, 30, 19,
                    19, 1, 10, 23, 3, 3, 17, 22, 2, 26, 1, 11, 1, 23, 1, 1, 1, 15, 1, 1, 13, 1, 1})
            .expect(520317213);

}
