package q800;

import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/k-th-smallest-prime-fraction/
 *
 * A sorted list A contains 1, plus some number of primes.  Then, for every p < q in the list, we consider the
 * fraction p/q.
 *
 * What is the K-th smallest fraction considered?  Return your answer as an array of ints, where answer[0] = p and
 * answer[1] = q.
 *
 * Examples:
 * Input: A = [1, 2, 3, 5], K = 3
 * Output: [2, 5]
 * Explanation:
 * The fractions to be considered in sorted order are:
 * 1/5, 1/3, 2/5, 1/2, 3/5, 2/3.
 * The third fraction is 2/5.
 *
 * Input: A = [1, 7], K = 1
 * Output: [1, 7]
 *
 * Note:
 *
 * A will have length between 2 and 2000.
 * Each A[i] will be between 1 and 30000.
 * K will be between 1 and A.length * (A.length - 1) / 2.
 */
@RunWith(LeetCodeRunner.class)
public class Q786_KthSmallestPrimeFraction {

    // 优先队列的做法, 这个比较慢
    @Answer
    public int[] kthSmallestPrimeFraction(int[] A, int K) {
        final int n = A.length;
        // pos[i] 表示在pq 中的分数 A[pos[i]] / A[i]
        // 因为 A[j] / A[i] < A[j+1] / A[i], 所以可以使用pq 来解答.
        int[] pos = new int[n];
        PriorityQueue<Integer> pq = new PriorityQueue<>(n,
                (a, b) -> A[b] * A[pos[a]] - A[a] * A[pos[b]]);
        for (int i = 0; i < n; i++) {
            pq.add(i);
        }

        while (--K > 0) {
            int p = pq.poll();
            if (++pos[p] < n) {
                pq.offer(p);
            }
        }
        return new int[]{A[pos[pq.peek()]], A[pq.peek()]};
    }

    /**
     * LeetCode 上比较快的解法.
     * 通过二分查找的方式来查找解, 找出结果的上下界, 然后统计小于middle 的个数, 以此来逼近结果.
     * 参考 https://www.cnblogs.com/grandyang/p/9135156.html
     */
    @Answer
    public int[] kthSmallestPrimeFraction2(int[] A, int K) {
        final int n = A.length;
        double left = 0, right = 1;
        int p = 0, q = 1;
        while (true) {
            double mid = left + (right - left) / 2.0;
            int count = 0;

            p = 0;
            // 在以A[i] 为分母组成的二维数组中寻找大于mid 的结果
            for (int i = 0, j = 0; i < n; i++) {
                while (j < n && A[i] > mid * A[j]) {
                    j++;
                }
                count += n - j;
                if (j < n && p * A[j] < q * A[i]) {
                    p = A[i];
                    q = A[j];
                }
            }
            if (count == K) {
                return new int[]{p, q};
            } else if (count < K) {
                left = mid;
            } else {
                right = mid;
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 3, 5}, 3).expect(new int[]{2, 5});

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 7}, 1).expect(new int[]{1, 7});

}
