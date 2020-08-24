package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 995. Minimum Number of K Consecutive Bit Flips
 * https://leetcode.com/problems/minimum-number-of-k-consecutive-bit-flips/
 *
 * In an array A containing only 0s and 1s, a K-bit flip consists of choosing a (contiguous) subarray of length K and
 * simultaneously changing every 0 in the subarray to 1, and every 1 in the subarray to 0.
 *
 * Return the minimum number of K-bit flips required so that there is no 0 in the array.  If it is not possible, return
 * -1.
 *
 * Example 1:
 *
 * Input: A = [0,1,0], K = 1
 * Output: 2
 * Explanation: Flip A[0], then flip A[2].
 *
 * Example 2:
 *
 * Input: A = [1,1,0], K = 2
 * Output: -1
 * Explanation: No matter how we flip subarrays of size 2, we can't make the array become [1,1,1].
 *
 * Example 3:
 *
 * Input: A = [0,0,0,1,0,1,1,0], K = 3
 * Output: 3
 * Explanation:
 * Flip A[0],A[1],A[2]: A becomes [1,1,1,1,0,1,1,0]
 * Flip A[4],A[5],A[6]: A becomes [1,1,1,1,1,0,0,0]
 * Flip A[5],A[6],A[7]: A becomes [1,1,1,1,1,1,1,1]
 *
 * Note:
 *
 * 1 <= A.length <= 30000
 * 1 <= K <= A.length
 */
@RunWith(LeetCodeRunner.class)
public class Q995_MinimumNumberOfKConsecutiveBitFlips {

    /**
     * 贪婪算法. 每次翻转最前面的数字.
     */
    @Answer
    public int minKBitFlips(int[] A, int K) {
        final int n = A.length;
        int res = 0;
        for (int i = 0; i <= n - K; i++) {
            if (A[i] == 0) {
                res++;
                // 翻转当前和后面的位
                for (int j = 0; j < K; j++) {
                    A[i + j] = A[i + j] ^ 1;
                }
            }
        }
        // 到底不能翻转了, 检查是否还有0, 如果有则无法翻转.
        for (int i = n - K; i < n; i++) {
            if (A[i] == 0) {
                return -1;
            }
        }
        return res;
    }

    /**
     * 参考LeetCode, 针对上面算法的优化
     */
    @Answer
    public int minKBitFlips2(int[] A, int K) {
        final int n = A.length;
        int res = 0;
        // flips[i] 表示在数组 A 的 i 位置上是否进行了翻转
        int[] flips = new int[n];
        // 表示当前位置的数字跟原数组相比是否被翻转了
        int flip = 0;
        for (int i = 0; i < n; i++) {
            if (K <= i) {
                // 去掉窗口之前的翻转
                flip ^= flips[i - K];
            }
            // 是否应当翻转
            if (flip == A[i]) {
                if (i + K > n) {
                    return -1;
                }
                flips[i] = 1;
                flip ^= 1;
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{0, 1, 0}, 1).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 1, 0}, 2).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{0, 0, 0, 1, 0, 1, 1, 0}, 3).expect(3);

}
