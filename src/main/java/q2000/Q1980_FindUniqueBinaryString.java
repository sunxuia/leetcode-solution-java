package q2000;

import org.junit.runner.RunWith;
import util.asserthelper.AssertUtils;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1980. Find Unique Binary String
 * https://leetcode.com/problems/find-unique-binary-string/
 *
 * Given an array of strings nums containing n unique binary strings each of length n, return a binary string of length
 * n that does not appear in nums. If there are multiple answers, you may return any of them.
 *
 * Example 1:
 *
 * Input: nums = ["01","10"]
 * Output: "11"
 * Explanation: "11" does not appear in nums. "00" would also be correct.
 *
 * Example 2:
 *
 * Input: nums = ["00","01"]
 * Output: "11"
 * Explanation: "11" does not appear in nums. "10" would also be correct.
 *
 * Example 3:
 *
 * Input: nums = ["111","011","001"]
 * Output: "101"
 * Explanation: "101" does not appear in nums. "000", "010", "100", and "110" would also be correct.
 *
 * Constraints:
 *
 * n == nums.length
 * 1 <= n <= 16
 * nums[i].length == n
 * nums[i] is either '0' or '1'.
 * All the strings of nums are unique.
 */
@RunWith(LeetCodeRunner.class)
public class Q1980_FindUniqueBinaryString {

    @Answer
    public String findDifferentBinaryString(String[] nums) {
        final int n = nums[0].length();
        boolean[] bucket = new boolean[1 << n];
        for (String num : nums) {
            int index = 0;
            for (int i = 0; i < n; i++) {
                index |= (num.charAt(i) - '0') << i;
            }
            bucket[index] = true;
        }
        for (int i = 0; i < (1 << n); i++) {
            if (!bucket[i]) {
                char[] cs = new char[n];
                for (int j = 0; j < n; j++) {
                    cs[j] = (char) ('0' + (i & 1));
                    i >>= 1;
                }
                return new String(cs);
            }
        }
        return "";
    }

    // leetcode 上最快的解法.
    // 按对角线取反, 这样结果与nums 中的每个数字都至少有1 位不同.
    @Answer
    public String findDifferentBinaryString_leetcode(String[] nums) {
        final int n = nums[0].length();
        char[] cs = new char[n];
        for (int i = 0; i < n; i++) {
            cs[i] = nums[i].charAt(i) == '0' ? '1' : '0';
        }
        return new String(cs);
    }

    private DataExpectation createTestCase(String... args) {
        return DataExpectation.builder()
                .addArgument(args)
                .assertMethod((String res) -> {
                    AssertUtils.assertEquals(args[0].length(), res.length());
                    for (int i = 0; i < res.length(); i++) {
                        char c = res.charAt(i);
                        if (c != '0' && c != '1') {
                            throw new RuntimeException("Illegal return character " + c);
                        }
                    }
                    for (String arg : args) {
                        if (arg.equals(res)) {
                            throw new RuntimeException(res + "已存在");
                        }
                    }
                }).build();
    }

    @TestData
    public DataExpectation example1 = createTestCase("01", "10");

    @TestData
    public DataExpectation example2 = createTestCase("00", "01");

    @TestData
    public DataExpectation example3 = createTestCase("111", "011", "001");

}
