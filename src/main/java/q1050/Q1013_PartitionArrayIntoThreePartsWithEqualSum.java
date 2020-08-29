package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1013. Partition Array Into Three Parts With Equal Sum
 * https://leetcode.com/problems/partition-array-into-three-parts-with-equal-sum/
 *
 * Given an array A of integers, return true if and only if we can partition the array into three non-empty parts with
 * equal sums.
 *
 * Formally, we can partition the array if we can find indexes i+1 < j with (A[0] + A[1] + ... + A[i] == A[i+1] + A[i+2]
 * + ... + A[j-1] == A[j] + A[j-1] + ... + A[A.length - 1])
 *
 * Example 1:
 *
 * Input: A = [0,2,1,-6,6,-7,9,1,2,0,1]
 * Output: true
 * Explanation: 0 + 2 + 1 = -6 + 6 - 7 + 9 + 1 = 2 + 0 + 1
 *
 * Example 2:
 *
 * Input: A = [0,2,1,-6,6,7,9,-1,2,0,1]
 * Output: false
 *
 * Example 3:
 *
 * Input: A = [3,3,6,5,-2,2,5,1,-9,4]
 * Output: true
 * Explanation: 3 + 3 = 6 = 5 - 2 + 2 + 5 + 1 - 9 + 4
 *
 * Constraints:
 *
 * 3 <= A.length <= 50000
 * -10^4 <= A[i] <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1013_PartitionArrayIntoThreePartsWithEqualSum {

    @Answer
    public boolean canThreePartsEqualSum(int[] A) {
        int sum = 0;
        for (int a : A) {
            sum += a;
        }
        if (sum % 3 != 0) {
            return false;
        }

        int total = 0, count = 0;
        for (int a : A) {
            total += a;
            if (total == sum / 3) {
                count++;
                total = 0;
            }
        }
        return count >= 3;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{0, 2, 1, -6, 6, -7, 9, 1, 2, 0, 1})
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{0, 2, 1, -6, 6, 7, 9, -1, 2, 0, 1})
            .expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[]{3, 3, 6, 5, -2, 2, 5, 1, -9, 4})
            .expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[]{10, -10, 10, -10, 10, -10, 10, -10})
            .expect(true);

}
