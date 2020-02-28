package q450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/reconstruct-original-digits-from-english/
 *
 * Given a non-empty string containing an out-of-order English representation of digits 0-9, output the digits in
 * ascending order.
 *
 * Note:
 *
 * Input contains only lowercase English letters.
 * Input is guaranteed to be valid and can be transformed to its original digits. That means invalid inputs such
 * as "abc" or "zerone" are not permitted.
 * Input length is less than 50,000.
 *
 * Example 1:
 *
 * Input: "owoztneoer"
 *
 * Output: "012"
 *
 * Example 2:
 *
 * Input: "fviefuro"
 *
 * Output: "45"
 *
 * 题目的意思: 表示数字的英文单词 ("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"),
 * 组合在一起表示一串阿拉伯数字, 但是英文字符的组合顺序被打乱了, 要求从被打乱的字符串s 中恢复出其所表示的几个数字(升序排列).
 */
@RunWith(LeetCodeRunner.class)
public class Q423_ReconstructOriginalDigitsFromEnglish {

    /**
     * 这题统计一下 0-9 的英文对应的字母就知道了: 找出只有这个数字有的字符, 减去整个单词的计数, 然后继续找就好了
     * >   a b c d e f g h i j k l m n o p q r s t u v w x y z
     * > 0         1                   1     1               1
     * > 1         1                 1 1
     * > 2                             1         1     1
     * > 3         2     1                   1   1
     * > 4           1                 1     1     1
     * > 5         1 1     1                         1
     * > 6                 1                   1         1
     * > 7         2                 1         1     1
     * > 8         1   1 1 1                     1
     * > 9         1       1         2
     * >   a b c d e f g h i j k l m n o p q r s t u v w x y z
     */
    @Answer
    public String originalDigits(String s) {
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
        }
        int[] nums = new int[10];

        minusCount(nums, 0, counts, 'z', "zero");
        minusCount(nums, 2, counts, 'w', "two");
        minusCount(nums, 4, counts, 'u', "four");
        minusCount(nums, 6, counts, 'x', "six");
        minusCount(nums, 1, counts, 'o', "one");
        minusCount(nums, 3, counts, 'r', "three");
        minusCount(nums, 7, counts, 's', "seven");
        minusCount(nums, 5, counts, 'v', "five");
        minusCount(nums, 8, counts, 't', "eight");
        minusCount(nums, 9, counts, 'e', "nine");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            while (nums[i]-- > 0) {
                sb.append(i);
            }
        }
        return sb.toString();
    }

    private void minusCount(int[] nums, int num, int[] counts, char symbol, String word) {
        int count = counts[symbol - 'a'];
        nums[num] += count;
        for (int i = 0; i < word.length(); i++) {
            counts[word.charAt(i) - 'a'] -= count;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("owoztneoer").expect("012");

    @TestData
    public DataExpectation example2 = DataExpectation.create("fviefuro").expect("45");

}
