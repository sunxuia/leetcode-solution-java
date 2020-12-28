package q1450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1449. Form Largest Integer With Digits That Add up to Target
 * https://leetcode.com/problems/form-largest-integer-with-digits-that-add-up-to-target/
 *
 * Given an array of integers cost and an integer target. Return the maximum integer you can paint under the following
 * rules:
 *
 * The cost of painting a digit (i+1) is given by cost[i] (0 indexed).
 * The total cost used must be equal to target.
 * Integer does not have digits 0.
 *
 * Since the answer may be too large, return it as string.
 *
 * If there is no way to paint any integer given the condition, return "0".
 *
 * Example 1:
 *
 * Input: cost = [4,3,2,5,6,7,2,5,5], target = 9
 * Output: "7772"
 * Explanation:  The cost to paint the digit '7' is 2, and the digit '2' is 3. Then cost("7772") = 2*3+ 3*1 = 9. You
 * could also paint "977", but "7772" is the largest number.
 * Digit    cost
 * 1  ->   4
 * 2  ->   3
 * 3  ->   2
 * 4  ->   5
 * 5  ->   6
 * 6  ->   7
 * 7  ->   2
 * 8  ->   5
 * 9  ->   5
 *
 * Example 2:
 *
 * Input: cost = [7,6,5,5,5,6,8,7,8], target = 12
 * Output: "85"
 * Explanation: The cost to paint the digit '8' is 7, and the digit '5' is 5. Then cost("85") = 7 + 5 = 12.
 *
 * Example 3:
 *
 * Input: cost = [2,4,6,2,4,6,4,4,4], target = 5
 * Output: "0"
 * Explanation: It's not possible to paint any integer with total cost equal to target.
 *
 * Example 4:
 *
 * Input: cost = [6,10,15,40,40,40,40,40,40], target = 47
 * Output: "32211"
 *
 * Constraints:
 *
 * cost.length == 9
 * 1 <= cost[i] <= 5000
 * 1 <= target <= 5000
 */
@RunWith(LeetCodeRunner.class)
public class Q1449_FormLargestIntegerWithDigitsThatAddUpToTarget {

    /**
     * 带缓存的dfs
     */
    @Answer
    public String largestNumber(int[] cost, int target) {
        String[] cache = new String[5001];
        cache[0] = "";
        return dfs(cache, cost, target);
    }

    private String dfs(String[] cache, int[] cost, int target) {
        if (cache[target] != null) {
            return cache[target];
        }
        String res = "";
        for (int i = 1; i <= 9; i++) {
            if (cost[i - 1] <= target) {
                String str = dfs(cache, cost, target - cost[i - 1]);
                if (!"0".equals(str)) {
                    str = i + str;
                    if (res.length() < str.length()
                            || res.length() == str.length() && res.compareTo(str) < 0) {
                        res = str;
                    }
                }
            }
        }
        res = res.isEmpty() ? "0" : res;
        cache[target] = res;
        return res;
    }

    /**
     * dp 的解法.
     */
    @Answer
    public String largestNumber2(int[] cost, int target) {
        // dp[i][0] 表示target 为i 的字符串长度+1(+1 用于哨兵),
        // dp[i][1:9] 表示这个字符串中各个数字的数量.
        int[][] dp = new int[target + 1][10];
        dp[0][0] = 1;
        for (int t = 0; t < target; t++) {
            if (dp[t][0] > 0) {
                for (int v = 1; v <= 9; v++) {
                    int next = t + cost[v - 1];
                    if (next <= target) {
                        dp[t][0]++;
                        dp[t][v]++;
                        if (isLarger(dp[t], dp[next])) {
                            System.arraycopy(dp[t], 0, dp[next], 0, 10);
                        }
                        dp[t][0]--;
                        dp[t][v]--;
                    }
                }
            }
        }

        if (dp[target][0] < 2) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 9; i > 0; i--) {
            while (dp[target][i]-- > 0) {
                sb.append(i);
            }
        }
        return sb.toString();
    }

    private boolean isLarger(int[] a, int[] b) {
        if (a[0] != b[0]) {
            return a[0] > b[0];
        }
        for (int i = 9; i > 0; i--) {
            if (a[i] != b[i]) {
                return a[i] > b[i];
            }
        }
        return false;
    }

    /**
     * leetcode 上最快的结果, 有修改.
     * 是针对上面解法的优化, 不需要记录数字, 只需要记录可达到的最大位数,
     * 之后根据这个位数向前溯源.
     */
    @Answer
    public String largestNumber3(int[] cost, int target) {
        // dp[i] 表示target 为i 的最大位数+1 (+1 是哨兵).
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int t = 0; t < target; t++) {
            if (dp[t] > 0) {
                for (int i = 0; i < 9; i++) {
                    int next = t + cost[i];
                    if (next <= target) {
                        dp[next] = Math.max(dp[next], dp[t] + 1);
                    }
                }
            }
        }

        if (dp[target] < 2) {
            return "0";
        }

        // 位数从大到小, 同位向前溯源结果.
        StringBuilder sb = new StringBuilder();
        for (int i = 8; i >= 0; i--) {
            while (target >= cost[i]
                    && dp[target] == dp[target - cost[i]] + 1) {
                sb.append(i + 1);
                target -= cost[i];
            }
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{4, 3, 2, 5, 6, 7, 2, 5, 5}, 9)
            .expect("7772");

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{7, 6, 5, 5, 5, 6, 8, 7, 8}, 12)
            .expect("85");

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{2, 4, 6, 2, 4, 6, 4, 4, 4}, 5)
            .expect("0");

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{6, 10, 15, 40, 40, 40, 40, 40, 40}, 47)
            .expect("32211");

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(new int[]{1, 1, 1, 1, 1, 1, 1, 3, 2}, 10)
            .expect("7777777777");

}
