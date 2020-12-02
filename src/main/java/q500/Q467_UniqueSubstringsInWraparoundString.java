package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/unique-substrings-in-wraparound-string/
 *
 * Consider the string s to be the infinite wraparound string of "abcdefghijklmnopqrstuvwxyz", so s will look like
 * this: "...zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd....".
 *
 * Now we have another string p. Your job is to find out how many unique non-empty substrings of p are present in s.
 * In particular, your input is the string p and you need to output the number of different non-empty substrings of p
 * in the string s.
 *
 * Note: p consists of only lowercase English letters and the size of p might be over 10000.
 *
 * Example 1:
 *
 * Input: "a"
 * Output: 1
 *
 * Explanation: Only the substring "a" of string "a" is in the string s.
 *
 * Example 2:
 *
 * Input: "cac"
 * Output: 2
 * Explanation: There are two substrings "a", "c" of string "cac" in the string s.
 *
 * Example 3:
 *
 * Input: "zab"
 * Output: 6
 * Explanation: There are six substrings "z", "a", "b", "za", "ab", "zab" of string "zab" in the string s.
 */
@RunWith(LeetCodeRunner.class)
public class Q467_UniqueSubstringsInWraparoundString {

    // 使用set 的记录子字符串的方式会发生超时
    @Answer
    public int findSubstringInWraproundString(String p) {
        // 以此字符结尾的最长子字符串的长度
        int[] sub = new int[26];
        int res = 0, // 结果
                count = 1, // 当前连续的字符数
                prev = -2; // 上一个字符对应的数字值
        for (int i = 0; i < p.length(); i++) {
            int num = p.charAt(i) - 'a';
            count = num == (prev + 1) % 26 ? count + 1 : 1;
            if (sub[num] < count) {
                res += count - sub[num];
                sub[num] = count;
            }
            prev = num;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("a").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create("cac").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create("zab").expect(6);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("abaab").expect(3);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read("Q467_TestData", String.class))
            .expect(34151);

}
