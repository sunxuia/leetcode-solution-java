package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.
 *
 * Example 1:
 *
 * Input: 121
 * Output: true
 * Example 2:
 *
 * Input: -121
 * Output: false
 * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a
 * palindrome.
 * Example 3:
 *
 * Input: 10
 * Output: false
 * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
 * Follow up:
 *
 * Coud you solve it without converting the integer to a string?
 *
 * 解析: 判断数字是否是回文, 且不能转换为字符串判断.
 */
@RunWith(LeetCodeRunner.class)
public class Q009_PalindromeNumber {

    /**
     * 按照每位进行判断, 这个运行速度不够快(14ms), 主要是Math.pow 的运算耗时.
     */
    @Answer
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        // 回文位数
        final int digit = (int) Math.log10(x) + 1;
        // 对于"aba" 型回文, 左边的坐标 -1, "aa" 型回文不需要.
        // 也可以针对这2 种类情况分开写, 这里为了方便就写一起了.
        final int offset = digit % 2;
        // 回文的中心位置
        final int id = (digit - 1) / 2;
        for (int i = 0; i < digit / 2; i++) {
            // 左边位数的值
            int left = x / ((int) Math.pow(10, id - i - offset)) % 10;
            // 右边位数的值
            int right = x / ((int) Math.pow(10, id + i + 1)) % 10;
            if (left != right) {
                return false;
            }
        }
        return true;
    }

    /**
     * 针对上面的问题, 不从中间, 从两边判断是否回文. 这个只要 8ms.
     */
    @Answer
    public boolean isPalindrome2(int x) {
        if (x < 0) {
            return false;
        }
        final int digit = (int) Math.log10(x) + 1;
        int a = 1, b = (int) Math.pow(10, digit - 1);
        for (int i = 0; i < digit / 2; i++) {
            int left = x / a % 10;
            int right = x / b % 10;
            if (left != right) {
                return false;
            }
            a *= 10;
            b /= 10;
        }
        return true;
    }

    /**
     * leetcode 中的解题思路 (6ms).
     * 提取x 的下半部分, 和上半部分相比, 如果相同则是回文, 如果不同就不是.
     * 这样就不涉及Math 的计算了.
     */
    @Answer
    public boolean isPalindrome3(int x) {
        // 负数, 末尾是0 的肯定不是回文
        if (x < 0 || x != 0 && x % 10 == 0) {
            return false;
        }
        int rest = 0;
        // "aa" 型则会在x="a", rest="a" 位置停止.
        // "aba" 型则会在x="a", rest="ba" 位置停止.
        while (x > rest) {
            rest = rest * 10 + x % 10;
            x /= 10;
        }
        return rest == x || x == rest / 10;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(121)
            .expect(true)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(-121)
            .expect(false)
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(10)
            .expect(false)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(1)
            .expect(true)
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(11)
            .expect(true)
            .build();

    @TestData
    public DataExpectation normal3 = DataExpectation.builder()
            .addArgument(1410110141)
            .expect(true)
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument(Integer.MAX_VALUE)
            .expect(false)
            .build();

    @TestData
    public DataExpectation border2 = DataExpectation.builder()
            .addArgument(0)
            .expect(true)
            .build();
}
