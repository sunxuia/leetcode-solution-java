package q1300;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1300. Sum of Mutated Array Closest to Target
 * https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/
 *
 * Given an integer array arr and a target value target, return the integer value such that when we change all the
 * integers larger than value in the given array to be equal to value, the sum of the array gets as close as possible
 * (in absolute difference) to target.
 *
 * In case of a tie, return the minimum such integer.
 *
 * Notice that the answer is not neccesarilly a number from arr.
 *
 * Example 1:
 *
 * Input: arr = [4,9,3], target = 10
 * Output: 3
 * Explanation: When using 3 arr converts to [3, 3, 3] which sums 9 and that's the optimal answer.
 *
 * Example 2:
 *
 * Input: arr = [2,3,5], target = 10
 * Output: 5
 *
 * Example 3:
 *
 * Input: arr = [60864,25176,27249,21296,20204], target = 56803
 * Output: 11361
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^4
 * 1 <= arr[i], target <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1300_SumOfMutatedArrayClosestToTarget {

    @Answer
    public int findBestValue(int[] arr, int target) {
        final int n = arr.length;
        Arrays.sort(arr);
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + arr[i];
        }
        int start = 0, end = arr[n - 1];
        while (start < end) {
            int mid = (start + end + 1) / 2;
            int sum = getSum(arr, sums, mid);
            if (sum > target) {
                end = mid - 1;
            } else {
                start = mid;
            }
        }
        int lower = Math.abs(target - getSum(arr, sums, start));
        int higher = Math.abs(target - getSum(arr, sums, start + 1));
        return higher < lower ? start + 1 : start;
    }

    private int getSum(int[] arr, int[] sums, int target) {
        final int n = arr.length;
        int idx = Arrays.binarySearch(arr, target);
        idx = idx >= 0 ? idx : -idx - 2;
        return sums[idx + 1] + (n - 1 - idx) * target;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{4, 9, 3}, 10).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{2, 3, 5}, 10).expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{60864, 25176, 27249, 21296, 20204}, 56803).expect(11361);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{2, 3, 5}, 11).expect(5);

    // 1万个数据
    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(TestDataFileHelper.readIntegerArray("Q1300_TestData"), 4203).expect(0);

}
