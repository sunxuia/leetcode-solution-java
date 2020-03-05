package q450;

import org.junit.runner.RunWith;
import q400.Q386_LexicographicalNumbers;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/k-th-smallest-in-lexicographical-order/
 *
 * Given integers n and k, find the lexicographically k-th smallest integer in the range from 1 to n.
 *
 * Note: 1 ≤ k ≤ n ≤ 10^9.
 *
 * Example:
 *
 * Input:
 * n: 13   k: 2
 *
 * Output:
 * 10
 *
 * Explanation:
 * The lexicographical order is [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9], so the second smallest number is 10.
 *
 * 相关题目: {@link Q386_LexicographicalNumbers}
 */
@RunWith(LeetCodeRunner.class)
public class Q440_KthSmallestInLexicographicalOrder {

    /**
     * (386 题那种做法会超时)
     * https://www.cnblogs.com/grandyang/p/6031787.html
     * 这个链接提供了一种检索 10节点的树的思路.
     */
    @Answer
    public int findKthNumber(int n, int k) {
        int cur = 1;
        --k;
        while (k > 0) {
            long step = 0, first = cur, last = cur + 1;
            while (first <= n) {
                step += Math.min((long) n + 1, last) - first;
                first *= 10;
                last *= 10;
            }
            if (step <= k) {
                cur++;
                k -= step;
            } else {
                cur *= 10;
                k++;
            }
        }
        return cur;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(13, 2).expect(10);

}
