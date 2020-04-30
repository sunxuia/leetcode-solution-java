package q600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/optimal-division/
 *
 * Given a list of positive integers, the adjacent integers will perform the float division. For example, [2,3,4] ->
 * 2 / 3 / 4.
 *
 * However, you can add any number of parenthesis at any position to change the priority of operations. You should
 * find out how to add parenthesis to get the maximum result, and return the corresponding expression in string
 * format. Your expression should NOT contain redundant parenthesis.
 *
 * Example:
 *
 * Input: [1000,100,10,2]
 * Output: "1000/(100/10/2)"
 * Explanation:
 * 1000/(100/10/2) = 1000/((100/10)/2) = 200
 * However, the bold parenthesis in "1000/((100/10)/2)" are redundant,
 * since they don't influence the operation priority. So you should return "1000/(100/10/2)".
 *
 * Other cases:
 * 1000/(100/10)/2 = 50
 * 1000/(100/(10/2)) = 50
 * 1000/100/10/2 = 0.5
 * 1000/100/(10/2) = 2
 *
 * Note:
 *
 * The length of the input array is [1, 10].
 * Elements in the given array will be in range [2, 1000].
 * There is only one optimal division for each test case.
 */
@RunWith(LeetCodeRunner.class)
public class Q553_OptimalDivision {

    // Solution 中的做法, 知道了规律就简单了, 另外可以使用dfs
    @Answer
    public String optimalDivision(int[] nums) {
        if (nums.length == 1) {
            return String.valueOf(nums[0]);
        }
        if (nums.length == 2) {
            return nums[0] + "/" + nums[1];
        }
        StringBuilder res = new StringBuilder();
        res.append(nums[0]).append("/(").append(nums[1]);
        for (int i = 2; i < nums.length; i++) {
            res.append("/").append(nums[i]);
        }
        res.append(")");
        return res.toString();
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{1000, 100, 10, 2}).expect("1000/(100/10/2)");

}
