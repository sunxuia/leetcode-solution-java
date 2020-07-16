package q900;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 870. Advantage Shuffle
 * https://leetcode.com/problems/advantage-shuffle/
 *
 * Given two arrays A and B of equal size, the advantage of A with respect to B is the number of indices i for which
 * A[i] > B[i].
 *
 * Return any permutation of A that maximizes its advantage with respect to B.
 *
 * Example 1:
 *
 * Input: A = [2,7,11,15], B = [1,10,4,11]
 * Output: [2,11,7,15]
 *
 * Example 2:
 *
 * Input: A = [12,24,8,32], B = [13,25,32,11]
 * Output: [24,32,8,12]
 *
 * Note:
 *
 * 1 <= A.length = B.length <= 10000
 * 0 <= A[i] <= 10^9
 * 0 <= B[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q870_AdvantageShuffle {

    // 田忌赛马
    @Answer
    public int[] advantageCount(int[] A, int[] B) {
        final int n = A.length;
        Arrays.sort(A);
        for (int i = 0; i < n; i++) {
            int idx = binarySearch(A, i, n, B[i]);
            if (i < idx && idx < n) {
                int val = A[idx];
                System.arraycopy(A, i, A, i + 1, idx - i);
                A[i] = val;
            }
        }
        return A;
    }

    private int binarySearch(int[] arr, int start, int endE, int target) {
        while (start < endE) {
            int mid = (start + endE) / 2;
            if (target < arr[mid]) {
                endE = mid;
            } else {
                start = mid + 1;
            }
        }
        return endE;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{2, 7, 11, 15}, new int[]{1, 10, 4, 11})
            .expect(new int[]{2, 11, 7, 15});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{12, 24, 8, 32}, new int[]{13, 25, 32, 11})
            .expect(new int[]{24, 32, 8, 12});

}
