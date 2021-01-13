package q1500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1497. Check If Array Pairs Are Divisible by k
 * https://leetcode.com/problems/check-if-array-pairs-are-divisible-by-k/
 *
 * Given an array of integers arr of even length n and an integer k.
 *
 * We want to divide the array into exactly n / 2 pairs such that the sum of each pair is divisible by k.
 *
 * Return True If you can find a way to do that or False otherwise.
 *
 * Example 1:
 *
 * Input: arr = [1,2,3,4,5,10,6,7,8,9], k = 5
 * Output: true
 * Explanation: Pairs are (1,9),(2,8),(3,7),(4,6) and (5,10).
 *
 * Example 2:
 *
 * Input: arr = [1,2,3,4,5,6], k = 7
 * Output: true
 * Explanation: Pairs are (1,6),(2,5) and(3,4).
 *
 * Example 3:
 *
 * Input: arr = [1,2,3,4,5,6], k = 10
 * Output: false
 * Explanation: You can try all possible pairs to see that there is no way to divide arr into 3 pairs each with sum
 * divisible by 10.
 *
 * Example 4:
 *
 * Input: arr = [-10,10], k = 2
 * Output: true
 *
 * Example 5:
 *
 * Input: arr = [-1,1,-2,2,-3,3,-4,4], k = 3
 * Output: true
 *
 * Constraints:
 *
 * arr.length == n
 * 1 <= n <= 10^5
 * n is even.
 * -10^9 <= arr[i] <= 10^9
 * 1 <= k <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1497_CheckIfArrayPairsAreDivisibleByK {

    @Answer
    public boolean canArrange(int[] arr, int k) {
        int[] counts = new int[k];
        for (int num : arr) {
            counts[(num % k + k) % k]++;
        }
        if (k % 2 == 0 && counts[k / 2] % 2 != 0) {
            return false;
        }
        for (int i = 1; i <= k / 2; i++) {
            if (counts[i] != counts[k - i]) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5, 10, 6, 7, 8, 9}, 5)
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5, 6}, 7)
            .expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5, 6}, 10)
            .expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{-10, 10}, 2)
            .expect(true);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(new int[]{-1, 1, -2, 2, -3, 3, -4, 4}, 3)
            .expect(true);

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(TestDataFileHelper.read(testDataFile, 1, int[].class), 2)
            .expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{-1, -1, -1, -1, 2, 2, -2, -2}, 3)
            .expect(false);

}
