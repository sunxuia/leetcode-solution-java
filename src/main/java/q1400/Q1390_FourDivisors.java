package q1400;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1390. Four Divisors
 * https://leetcode.com/problems/four-divisors/
 *
 * Given an integer array nums, return the sum of divisors of the integers in that array that have exactly four
 * divisors.
 *
 * If there is no such integer in the array, return 0.
 *
 * Example 1:
 *
 * Input: nums = [21,4,7]
 * Output: 32
 * Explanation:
 * 21 has 4 divisors: 1, 3, 7, 21
 * 4 has 3 divisors: 1, 2, 4
 * 7 has 2 divisors: 1, 7
 * The answer is the sum of divisors of 21 only.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^4
 * 1 <= nums[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1390_FourDivisors {

    /**
     * 能被正好4 个数字整除, 扣掉1 和自身,
     * 则这个数字需要是2 个不同质数的积, 或者3 个相同质数的积.
     */
    @Answer
    public int sumFourDivisors(int[] nums) {
        int res = 0;
        for (int num : nums) {
            if (FOURS[num] > 0) {
                res += 1 + num + FOURS[num] + num / FOURS[num];
            }
        }
        return res;
    }

    private static final int[] FOURS = new int[10_0001];

    static {
        List<Integer> primes = new ArrayList<>();
        loop:
        for (int i = 2; i < 5_0000; i++) {
            for (int prime : primes) {
                if (i % prime == 0) {
                    continue loop;
                }
            }
            primes.add(i);
        }

        for (int i = 0; i < primes.size(); i++) {
            long a = primes.get(i);
            if (a * a * a < 10_0000) {
                FOURS[(int) (a * a * a)] = (int) a;
            }
            for (int j = i + 1; j < primes.size(); j++) {
                long b = primes.get(j);
                if (a * b > 10_0000) {
                    break;
                }
                FOURS[(int) (a * b)] = (int) a;
            }
        }
    }

    /**
     * leetcode 上最快的解法.
     */
    @Answer
    public int sumFourDivisors2(int[] nums) {
        int res = 0;
        for (int num : nums) {
            // last 表示上一个可以被num 整除的数字
            int last = 0;
            for (int i = 2; i * i <= num; i++) {
                if (num % i != 0) {
                    continue;
                }
                if (last == 0) {
                    last = i;
                } else {
                    // <sqrt(num) 且可被多个数字整除, 不符合要求
                    last = 0;
                    break;
                }
            }
            // 避免 num = last * last 的情况
            if (last > 0 && last != num / last) {
                res += 1 + num + last + num / last;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{21, 4, 7}).expect(32);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}).expect(45);

}
