package q1400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1388. Pizza With 3n Slices
 * https://leetcode.com/problems/pizza-with-3n-slices/
 *
 * There is a pizza with 3n slices of varying size, you and your friends will take slices of pizza as follows:
 *
 * You will pick any pizza slice.
 * Your friend Alice will pick next slice in anti clockwise direction of your pick.
 * Your friend Bob will pick next slice in clockwise direction of your pick.
 * Repeat until there are no more slices of pizzas.
 *
 * Sizes of Pizza slices is represented by circular array slices in clockwise direction.
 *
 * Return the maximum possible sum of slice sizes which you can have.
 *
 * Example 1:
 * <img src="./Q1388_PIC1.png">
 * Input: slices = [1,2,3,4,5,6]
 * Output: 10
 * Explanation: Pick pizza slice of size 4, Alice and Bob will pick slices with size 3 and 5 respectively. Then Pick
 * slices with size 6, finally Alice and Bob will pick slice of size 2 and 1 respectively. Total = 4 + 6.
 *
 * Example 2:
 * <img src="./Q1388_PIC2.png">
 * Input: slices = [8,9,8,6,1,1]
 * Output: 16
 * Output: Pick pizza slice of size 8 in each turn. If you pick slice with size 9 your partners will pick slices of size
 * 8.
 *
 * Example 3:
 *
 * Input: slices = [4,1,2,5,8,3,1,9,7]
 * Output: 21
 *
 * Example 4:
 *
 * Input: slices = [3,1,2]
 * Output: 3
 *
 * Constraints:
 *
 * 1 <= slices.length <= 500
 * slices.length % 3 == 0
 * 1 <= slices[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1388_PizzaWith3nSlices {

    /**
     * 根据 hint 中的提示, 这题可以等效为从 3n 的slices 中找出 n 个互不相邻的元素, 求这些元素的最大的和.
     */
    @Answer
    public int maxSizeSlices(int[] slices) {
        final int m = slices.length, n = m / 3;
        // dp[i][j] 表示总共选择了i 个元素, 选择到了 slice[j] 的位置.
        int[][] dp = new int[n + 1][m];
        // 不选择第1 个, 最后1 个可以被选择
        for (int i = 1; i <= n; i++) {
            dp[i][1] = slices[1];
            for (int j = 2; j < m; j++) {
                // 选择当前, 还是不选择
                dp[i][j] = Math.max(dp[i - 1][j - 2] + slices[j], dp[i][j - 1]);
            }
        }
        // 选择第1 个, 最后1 个不可以被选择
        for (int i = 1; i <= n; i++) {
            dp[i][0] = dp[i][1] = slices[0];
            // 因为最后1 个不可以被选择, 所以是 m-1
            for (int j = 2; j < m - 1; j++) {
                dp[i][j] = Math.max(dp[i - 1][j - 2] + slices[j], dp[i][j - 1]);
            }
        }
        return Math.max(dp[n][m - 1], dp[n][m - 2]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 4, 5, 6}).expect(10);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{8, 9, 8, 6, 1, 1}).expect(16);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{4, 1, 2, 5, 8, 3, 1, 9, 7}).expect(21);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{3, 1, 2}).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(
            new int[]{6, 3, 1, 2, 6, 2, 4, 3, 10, 4, 1, 4, 6, 5, 5, 3, 4, 7, 6, 5, 8, 7, 3, 8, 8, 1, 7, 1, 7, 8})
            .expect(70);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{4, 2, 9, 3, 4, 7}).expect(16);

}
