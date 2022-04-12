package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Hard] 1819. Number of Different Subsequences GCDs
 * https://leetcode.com/problems/number-of-different-subsequences-gcds/
 *
 * You are given an array nums that consists of positive integers.
 *
 * The GCD of a sequence of numbers is defined as the greatest integer that divides all the numbers in the sequence
 * evenly.
 *
 * For example, the GCD of the sequence [4,6,16] is 2.
 *
 * A subsequence of an array is a sequence that can be formed by removing some elements (possibly none) of the array.
 *
 * For example, [2,5,10] is a subsequence of [1,2,1,2,4,1,5,10].
 *
 * Return the number of different GCDs among all non-empty subsequences of nums.
 *
 * Example 1:
 * (图Q1819_PIC.png)
 * Input: nums = [6,10,3]
 * Output: 5
 * Explanation: The figure shows all the non-empty subsequences and their GCDs.
 * The different GCDs are 6, 10, 3, 2, and 1.
 *
 * Example 2:
 *
 * Input: nums = [5,15,40,5,6]
 * Output: 7
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 2 * 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1819_NumberOfDifferentSubsequencesGcds {

    /**
     * 参考文档
     * @formatter:off
     * https://leetcode.com/problems/number-of-different-subsequences-gcds/discuss/1141309/Simple-explanation-or-O(n-*-sqrt-n)
     * @formatter:on
     * 关键在于如果对于因数f1 如果存在一个子序列, 并且他们的最大公约数g1 大于f1,  则他们的任何子序列的最大公约数就至少是g1, 而不是f1.
     */
    @Answer
    public int countDifferentSubsequenceGCDs(int[] nums) {
        // factors[f] 表示对于因数f,
        int[] factors = new int[200001];
        for (int num : nums) {
            for (int i = 1; i * i <= num; i++) {
                if (num % i == 0) {
                    int f1 = i, f2 = num / i;
                    factors[f1] = gcd(factors[f1], num);
                    factors[f2] = gcd(factors[f2], num);
                }
            }
        }

        int res = 0;
        for (int i = 1; i <= 200000; i++) {
            if (factors[i] == i) {
                res++;
            }
        }
        return res;
    }

    private int gcd(int x, int y) {
        return x == 0 ? y : gcd(y % x, x);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{6, 10, 3}).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{5, 15, 40, 5, 6}).expect(7);

}
