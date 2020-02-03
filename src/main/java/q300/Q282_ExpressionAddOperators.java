package q300;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/expression-add-operators/
 *
 * Given a string that contains only digits 0-9 and a target value, return all possibilities to add binary operators
 * (not unary) +, -, or * between the digits so they evaluate to the target value.
 *
 * Example 1:
 *
 * Input: num = "123", target = 6
 * Output: ["1+2+3", "1*2*3"]
 *
 * Example 2:
 *
 * Input: num = "232", target = 8
 * Output: ["2*3+2", "2+3*2"]
 *
 * Example 3:
 *
 * Input: num = "105", target = 5
 * Output: ["1*0+5","10-5"]
 *
 * Example 4:
 *
 * Input: num = "00", target = 0
 * Output: ["0+0", "0-0", "0*0"]
 *
 * Example 5:
 *
 * Input: num = "3456237490", target = 9191
 * Output: []
 */
@RunWith(LeetCodeRunner.class)
public class Q282_ExpressionAddOperators {

    @Answer
    public List<String> addOperators(String num, int target) {
        final int n = num.length();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = num.charAt(i) - '0';
        }
        List<String> res = new ArrayList<>();
        dfs(res, new StringBuilder(n * 2), nums, 0, target, 0, 1);
        return res;
    }

    private void dfs(List<String> res, StringBuilder sb, int[] nums, int index, int target, long sum, long product) {
        if (index == nums.length) {
            // 结果正确, 且最后是+ (-和之前的+ 重复, *因为sum 没有加上最后的数字所以是错的)
            if (sum == target && sb.charAt(sb.length() - 1) == '+') {
                res.add(sb.substring(0, sb.length() - 1));
            }
            return;
        }

        final int oldLen = sb.length();
        final int border = nums[index] == 0 ? index + 1 : nums.length;
        long v = 0;
        for (int i = index; i < border; i++) {
            v = v * 10 + nums[i];
            sb.append(v).append('+');
            dfs(res, sb, nums, i + 1, target, sum + v * product, 1);
            sb.setCharAt(sb.length() - 1, '-');
            dfs(res, sb, nums, i + 1, target, sum + v * product, -1);
            sb.setCharAt(sb.length() - 1, '*');
            dfs(res, sb, nums, i + 1, target, sum, v * product);
            sb.setLength(oldLen);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("123")
            .addArgument(6)
            .expect(Arrays.asList("1+2+3", "1*2*3"))
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("232")
            .addArgument(8)
            .expect(Arrays.asList("2*3+2", "2+3*2"))
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument("105")
            .addArgument(5)
            .expect(Arrays.asList("1*0+5", "10-5"))
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation example4 = DataExpectation.builder()
            .addArgument("00")
            .addArgument(0)
            .expect(Arrays.asList("0+0", "0-0", "0*0"))
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation example5 = DataExpectation.builder()
            .addArgument("3456237490")
            .addArgument(9191)
            .expect(Collections.emptyList())
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument("123456789")
            .addArgument(45)
            .expect(Arrays.asList("1+2+3+4+5+6+7+8+9", "1+2+3+4+5-6*7+8*9", "1+2+3+4-5*6+7*8+9", "1+2+3+4-5*6-7+8*9",
                    "1+2+3-4*5+6*7+8+9", "1+2+3-4*5-6+7*8+9", "1+2+3-4*5-6-7+8*9", "1+2+3-45+67+8+9",
                    "1+2+3*4+5+6*7-8-9", "1+2+3*4*5+6-7-8-9", "1+2+3*45-6-78-9", "1+2-3+4*5+6*7-8-9",
                    "1+2-3+45+6-7-8+9", "1+2-3+45-6+7+8-9", "1+2-3-4-5*6+7+8*9", "1+2-3-45-6+7+89", "1+2-3*4+5*6+7+8+9",
                    "1+2-3*4-5+6*7+8+9", "1+2-3*4-5-6+7*8+9", "1+2-3*4-5-6-7+8*9", "1+2-3*4*5+6+7+89",
                    "1+2-34+5+6+7*8+9", "1+2-34+5+6-7+8*9", "1+2-34-5-6+78+9", "1+2*3+4*5-6+7+8+9", "1+2*3+4*5*6+7-89",
                    "1+2*3-4-5-6*7+89", "1+2*3*4+5*6+7-8-9", "1+2*3*4-5+6*7-8-9", "1+2*3*4*5+6+7-89",
                    "1+2*34-5*6+7+8-9", "1+23+4+5+6+7+8-9", "1+23+4-5-67+89", "1+23+4-5*6+7*8-9", "1+23-4-5+6+7+8+9",
                    "1+23-4-5-6*7+8*9", "1+23-4*5+6*7+8-9", "1+23-4*5-6+7*8-9", "1+23-45+67+8-9", "1+23*4+5-6-7*8+9",
                    "1+23*4-5-6*7+8-9", "1+23*4-56+7-8+9", "1-2+3+4-5*6+78-9", "1-2+3+45+6-7+8-9", "1-2+3-4*5-6+78-9",
                    "1-2+3-45+6-7+89", "1-2+3*4*5-6-7+8-9", "1-2-3+4-5+67-8-9", "1-2-3+45-6-7+8+9", "1-2-3*4+5+6+7*8-9",
                    "1-2-3*4-5-6+78-9", "1-2-34+5+6+78-9", "1-2-34+56+7+8+9", "1-2-34-5+6+7+8*9", "1-2*3+4+5+6*7+8-9",
                    "1-2*3+4+5-6+7*8-9", "1-2*3+4+56+7-8-9", "1-2*3+4*5+6+7+8+9", "1-2*3+4*5-6*7+8*9",
                    "1-2*3+45-67+8*9", "1-2*3-4+5*6+7+8+9", "1-2*3-4-5+6*7+8+9", "1-2*3-4-5-6+7*8+9",
                    "1-2*3-4-5-6-7+8*9", "1-2*3*4+5-6+78-9", "1-2*3*4-5-6+7+8*9", "1-2*34+5*6-7+89", "1-23+4-5+67-8+9",
                    "1-23+4*5-6*7+89", "1-23+45-67+89", "1-23-4+5+67+8-9", "1-23-4-5-6-7+89", "1-23*4+5+6*7+89",
                    "1*2+3+4+5*6+7+8-9", "1*2+3+4-5+6*7+8-9", "1*2+3+4-5-6+7*8-9", "1*2+3+45+67-8*9",
                    "1*2+3-45+6+7+8*9", "1*2+3*4-56+78+9", "1*2+34+5-6-7+8+9", "1*2+34+56-7*8+9", "1*2+34-5+6+7-8+9",
                    "1*2+34-56+7*8+9", "1*2+34-56-7+8*9", "1*2-3+4-5-6*7+89", "1*2-3-4+56-7-8+9", "1*2-3-4*5+67+8-9",
                    "1*2-3*4+5+67-8-9", "1*2-34+5*6+7*8-9", "1*2*3+4+5+6+7+8+9", "1*2*3+4+5-6*7+8*9",
                    "1*2*3+4-5*6+7*8+9", "1*2*3+4-5*6-7+8*9", "1*2*3-4*5+6*7+8+9", "1*2*3-4*5-6+7*8+9",
                    "1*2*3-4*5-6-7+8*9", "1*2*3-45+67+8+9", "1*2*3*4+5+6-7+8+9", "1*2*3*4*5-6-78+9", "1*2*34+56-7-8*9",
                    "1*2*34-5+6-7-8-9", "1*23+4*5-6+7-8+9", "1*23-4-56-7+89", "12+3+4-56-7+89", "12+3-4*5+67-8-9",
                    "12+3-45+6+78-9", "12+3*4+5+6-7+8+9", "12+3*45-6-7-89", "12+34-5-6-7+8+9", "12-3+4+56-7-8-9",
                    "12-3+4*5+6-7+8+9", "12-3-4+5*6-7+8+9", "12-3-4-56+7+89", "12-3-45-6+78+9", "12-3*4-5+67-8-9",
                    "12-3*4*5+6+78+9", "12*3+4+5+6-7-8+9", "12*3+4+5-6+7+8-9", "12*3-4-5-6+7+8+9", "12*3-4-56+78-9",
                    "12*3*4-5*6-78+9"))
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument("1111")
            .addArgument(0)
            .expect(Arrays.asList("1*1*1-1", "1*1-1*1", "1+1-1-1", "1-1*1*1", "1-1+1-1", "1-1-1+1", "11-11"))
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument("")
            .addArgument(5)
            .expect(Collections.emptyList())
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation border2 = DataExpectation.builder()
            .addArgument("2147483648")
            .addArgument(-2147483648)
            .expect(Collections.emptyList())
            .unorderResult("")
            .build();

}
