package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1537. Get the Maximum Score
 * https://leetcode.com/problems/get-the-maximum-score/
 *
 * You are given two sorted arrays of distinct integers nums1 and nums2.
 *
 * A valid path is defined as follows:
 *
 * Choose array nums1 or nums2 to traverse (from index-0).
 * Traverse the current array from left to right.
 * If you are reading any value that is present in nums1 and nums2 you are allowed to change your path to the other
 * array. (Only one repeated value is considered in the valid path).
 *
 * Score is defined as the sum of uniques values in a valid path.
 *
 * Return the maximum score you can obtain of all possible valid paths.
 *
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * Example 1:
 * <img src="./Q1537_PIC.png">
 * Input: nums1 = [2,4,5,8,10], nums2 = [4,6,8,9]
 * Output: 30
 * Explanation: Valid paths:
 * [2,4,5,8,10], [2,4,5,8,9], [2,4,6,8,9], [2,4,6,8,10],  (starting from nums1)
 * [4,6,8,9], [4,5,8,10], [4,5,8,9], [4,6,8,10]    (starting from nums2)
 * The maximum is obtained with the path in green [2,4,6,8,10].
 *
 * Example 2:
 *
 * Input: nums1 = [1,3,5,7,9], nums2 = [3,5,100]
 * Output: 109
 * Explanation: Maximum sum is obtained with the path [1,3,5,100].
 *
 * Example 3:
 *
 * Input: nums1 = [1,2,3,4,5], nums2 = [6,7,8,9,10]
 * Output: 40
 * Explanation: There are no common elements between nums1 and nums2.
 * Maximum sum is obtained with the path [6,7,8,9,10].
 *
 * Example 4:
 *
 * Input: nums1 = [1,4,5,8,9,11,19], nums2 = [2,3,4,11,12]
 * Output: 61
 *
 * Constraints:
 *
 * 1 <= nums1.length <= 10^5
 * 1 <= nums2.length <= 10^5
 * 1 <= nums1[i], nums2[i] <= 10^7
 * nums1 and nums2 are strictly increasing.
 */
@RunWith(LeetCodeRunner.class)
public class Q1537_GetTheMaximumScore {

    @Answer
    public int maxSum(int[] nums1, int[] nums2) {
        final int mod = 10_0000_0007;
        final int m = nums1.length, n = nums2.length;
        long[] dp1 = new long[m + 1];
        long[] dp2 = new long[n + 1];
        int i = 0, j = 0;
        while (i < m && j < n) {
            if (nums1[i] == nums2[j]) {
                dp1[i + 1] = dp2[j + 1] =
                        nums1[i] + Math.max(dp1[i], dp2[j]);
                i++;
                j++;
            } else if (nums1[i] < nums2[j]) {
                dp1[i + 1] = dp1[i] + nums1[i];
                i++;
            } else {
                dp2[j + 1] = dp2[j] + nums2[j];
                j++;
            }
        }
        for (; i < m; i++) {
            dp1[i + 1] = dp1[i] + nums1[i];
        }
        for (; j < n; j++) {
            dp2[j + 1] = dp2[j] + nums2[j];
        }
        return (int) (Math.max(dp1[m], dp2[n]) % mod);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{2, 4, 5, 8, 10}, new int[]{4, 6, 8, 9})
            .expect(30);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 3, 5, 7, 9}, new int[]{3, 5, 100})
            .expect(109);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5}, new int[]{6, 7, 8, 9, 10})
            .expect(40);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{1, 4, 5, 8, 9, 11, 19}, new int[]{2, 3, 4, 11, 12})
            .expect(61);

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(TestDataFileHelper.read(testDataFile, 1, int[].class),
                    TestDataFileHelper.read(testDataFile, 2, int[].class))
            .expect(458051);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(TestDataFileHelper.read(testDataFile, 3, int[].class),
                    TestDataFileHelper.read(testDataFile, 4, int[].class))
            .expect(324280886);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(TestDataFileHelper.read(testDataFile, 5, int[].class),
                    TestDataFileHelper.read(testDataFile, 6, int[].class))
            .expect(127813386);

}
