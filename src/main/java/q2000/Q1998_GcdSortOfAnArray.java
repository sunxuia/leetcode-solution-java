package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1998. GCD Sort of an Array
 * https://leetcode.com/problems/gcd-sort-of-an-array/
 *
 * You are given an integer array nums, and you can perform the following operation any number of times on nums:
 *
 * Swap the positions of two elements nums[i] and nums[j] if gcd(nums[i], nums[j]) > 1 where gcd(nums[i], nums[j]) is
 * the greatest common divisor of nums[i] and nums[j].
 *
 * Return true if it is possible to sort nums in non-decreasing order using the above swap method, or false otherwise.
 *
 * Example 1:
 *
 * Input: nums = [7,21,3]
 * Output: true
 * Explanation: We can sort [7,21,3] by performing the following operations:
 * - Swap 7 and 21 because gcd(7,21) = 7. nums = [21,7,3]
 * - Swap 21 and 3 because gcd(21,3) = 3. nums = [3,7,21]
 *
 * Example 2:
 *
 * Input: nums = [5,2,6,2]
 * Output: false
 * Explanation: It is impossible to sort the array because 5 cannot be swapped with any other element.
 *
 * Example 3:
 *
 * Input: nums = [10,5,9,3,15]
 * Output: true
 * We can sort [10,5,9,3,15] by performing the following operations:
 * - Swap 10 and 15 because gcd(10,15) = 5. nums = [15,5,9,3,10]
 * - Swap 15 and 3 because gcd(15,3) = 3. nums = [3,5,9,15,10]
 * - Swap 10 and 15 because gcd(10,15) = 5. nums = [3,5,9,10,15]
 *
 * Constraints:
 *
 * 1 <= nums.length <= 3 * 10^4
 * 2 <= nums[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1998_GcdSortOfAnArray {

    /**
     * 按照相关话题的提示, 可以使用并查集, 如果多个数字两两之间都 gcd>1, 则多个数字之间的位置就可以自由调换(进行排序).
     * 这个解法会超时
     */
//    @Answer
    public boolean gcdSort(int[] nums) {
        final int n = nums.length;
        int[] roots = new int[n];
        for (int i = 0; i < n; i++) {
            roots[i] = i;
        }
        // 合并
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (gcd(nums[i], nums[j]) > 1) {
                    roots[findRoot(roots, i)] = findRoot(roots, j);
                }
            }
        }

        // 选择排序, 不同分组内部排序
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (findRoot(roots, i) == findRoot(roots, j)
                        && nums[min] > nums[j]) {
                    min = j;
                }
            }
            int t = nums[i];
            nums[i] = nums[min];
            nums[min] = t;
        }

        // 检查排序后的结果
        for (int i = 1; i < n; i++) {
            if (nums[i - 1] > nums[i]) {
                return false;
            }
        }
        return true;
    }

    private int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    private int findRoot(int[] roots, int i) {
        return roots[i] == i ? i : (roots[i] = findRoot(roots, roots[i]));
    }

    /**
     * 与上面算法类似, 但是优化gcd 为质因数分解.
     * 主要的优化点其实会卡在这里, 看怎么更有效率地进行质因数分解, 时间复杂度上一样, 过程挺无聊的.
     */
    @Answer
    public boolean gcdSort2(int[] nums) {
        final int n = nums.length;
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
        }

        int[] buckets = new int[max + 1];
        for (int num : nums) {
            buckets[num]++;
        }

        int[] roots = new int[max + 1];
        for (int i = 0; i <= max; i++) {
            roots[i] = i;
        }
        for (int num = 2; num <= max; num++) {
            if (buckets[num] == 0) {
                continue;
            }
            int v = num;
            for (int p : PRIMES) {
                if (v < IS_PRIME.length && IS_PRIME[v]) {
                    roots[findRoot(roots, num)] = findRoot(roots, v);
                    break;
                }
                if (p * p > v) {
                    break;
                }
                if (v % p == 0) {
                    roots[findRoot(roots, num)] = findRoot(roots, p);
                    do {
                        v /= p;
                    } while (v % p == 0);
                }
            }
        }

        for (int i = 0, num = 0; i < n; i++) {
            while (buckets[num] == 0) {
                num++;
            }
            if (num != nums[i] && findRoot(roots, num) != findRoot(roots, nums[i])) {
                return false;
            }
            buckets[num]--;
        }
        return true;
    }

    // 表示<33333 的质数, 最大的质因数分解的结果m 为 2*m=3*m 的组合, >m 的质数与其他数字之间的gcd=1, 因此不需要考虑.
    private static final int[] PRIMES = new int[3570];

    // 表示是否是质数
    private static final boolean[] IS_PRIME = new boolean[33333];

    static {
        // 初始化 PRIMES
        int len = 0;
        loop:
        for (int num = 2; num < 33333; num++) {
            for (int i = 0; i < len; i++) {
                if (num % PRIMES[i] == 0) {
                    continue loop;
                }
            }
            PRIMES[len++] = num;
            IS_PRIME[num] = true;
        }
        // 最后一位是哨兵
        PRIMES[len] = 10_0001;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{7, 21, 3}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{5, 2, 6, 2}).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{10, 5, 9, 3, 15}).expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{8, 7, 7, 9, 12, 4, 7, 21, 5, 10}).expect(true);

    // 3万个数字
    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read(int[].class))
            .expect(true);

}
