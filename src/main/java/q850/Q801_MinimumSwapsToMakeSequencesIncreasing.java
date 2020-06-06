package q850;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/minimum-swaps-to-make-sequences-increasing/
 *
 * We have two integer sequences A and B of the same non-zero length.
 *
 * We are allowed to swap elements A[i] and B[i].  Note that both elements are in the same index position in their
 * respective sequences.
 *
 * At the end of some number of swaps, A and B are both strictly increasing.  (A sequence is strictly increasing if
 * and only if A[0] < A[1] < A[2] < ... < A[A.length - 1].)
 *
 * Given A and B, return the minimum number of swaps to make both sequences strictly increasing.  It is guaranteed
 * that the given input always makes it possible.
 *
 * Example:
 * Input: A = [1,3,5,4], B = [1,2,3,7]
 * Output: 1
 * Explanation:
 * Swap A[3] and B[3].  Then the sequences are:
 * A = [1, 3, 5, 7] and B = [1, 2, 3, 4]
 * which are both strictly increasing.
 *
 * Note:
 *
 * A, B are arrays with the same length, and that length will be in the range [1, 1000].
 * A[i], B[i] are integer values in the range [0, 2000].
 */
@RunWith(LeetCodeRunner.class)
public class Q801_MinimumSwapsToMakeSequencesIncreasing {

    /**
     * https://www.cnblogs.com/grandyang/p/9311385.html
     */
    @Answer
    public int minSwap(int[] A, int[] B) {
        final int n = A.length;
        int[] swap = new int[n];
        int[] noSwap = new int[n];
        Arrays.fill(swap, n);
        Arrays.fill(noSwap, n);
        swap[0] = 1;
        noSwap[0] = 0;
        for (int i = 1; i < n; ++i) {
            if (A[i] > A[i - 1] && B[i] > B[i - 1]) {
                swap[i] = swap[i - 1] + 1;
                noSwap[i] = noSwap[i - 1];
            }
            if (A[i] > B[i - 1] && B[i] > A[i - 1]) {
                swap[i] = Math.min(swap[i], noSwap[i - 1] + 1);
                noSwap[i] = Math.min(noSwap[i], swap[i - 1]);
            }
        }
        return Math.min(swap[n - 1], noSwap[n - 1]);
    }

    /**
     * Solution 中给出的解法. 对上面解法做了一些改进.
     */
    @Answer
    public int minSwap2(int[] A, int[] B) {
        // n: natural, s: swapped
        int n1 = 0, s1 = 1;
        for (int i = 1; i < A.length; i++) {
            int n2 = Integer.MAX_VALUE, s2 = Integer.MAX_VALUE;
            if (A[i - 1] < A[i] && B[i - 1] < B[i]) {
                n2 = Math.min(n2, n1);
                s2 = Math.min(s2, s1 + 1);
            }
            if (A[i - 1] < B[i] && B[i - 1] < A[i]) {
                n2 = Math.min(n2, s1);
                s2 = Math.min(s2, n1 + 1);
            }
            n1 = n2;
            s1 = s2;
        }
        return Math.min(n1, s1);
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{1, 3, 5, 4}, new int[]{1, 2, 3, 7}).expect(1);

}
