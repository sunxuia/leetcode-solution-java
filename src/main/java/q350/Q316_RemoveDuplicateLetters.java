package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/remove-duplicate-letters/
 *
 * Given a string which contains only lowercase letters, remove duplicate letters so that every letter appears once
 * and only once. You must make sure your result is the smallest in lexicographical order among all possible results.
 *
 * Example 1:
 *
 * Input: "bcabc"
 * Output: "abc"
 *
 * Example 2:
 *
 * Input: "cbacdcbc"
 * Output: "acdb"
 *
 * 题解: 参数 s 由全小写字母组成, 现在要删除s 中的重复字符, 并让返回的字符按照字典顺序要最小 (所有可能的删除方案中最小的)
 */
@RunWith(LeetCodeRunner.class)
public class Q316_RemoveDuplicateLetters {

    /**
     * https://www.cnblogs.com/grandyang/p/5085379.html
     */
    @Answer
    public String removeDuplicateLetters(String s) {
        // 每个字符出现的次数
        int[] count = new int[128];
        char[] cs = s.toCharArray();
        for (char c : cs) {
            count[c]++;
        }
        // 结果, '0' 是 < s 中所有字母的哨兵
        StringBuilder sb = new StringBuilder("0");
        // 是否已经在结果sb 中了
        boolean[] visited = new boolean[128];
        for (char c : cs) {
            count[c]--;
            if (visited[c]) {
                continue;
            }
            while (true) {
                // 拿sb 中的末尾字符和当前字符比较
                char last = sb.charAt(sb.length() - 1);
                // c >= last: 末尾字符应该在当前字符前面,
                // count[last] == 0: 末尾字符无法移动
                if (c >= last || count[last] == 0) {
                    break;
                }
                // 移除末尾字符, 这个字符将在之后遇到的时候添加
                visited[last] = false;
                sb.setLength(sb.length() - 1);
            }
            sb.append(c);
            visited[c] = true;
        }
        return sb.substring(1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("bcabc").expect("abc");

    @TestData
    public DataExpectation example2 = DataExpectation.create("cbacdcbc").expect("acdb");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("ccacbaba").expect("acb");

    @TestData
    public DataExpectation normal2 = DataExpectation.create("abaaa").expect("ab");

    @TestData
    public DataExpectation normal3 = DataExpectation.create("cbaddabaa").expect("cadb");

    @TestData
    public DataExpectation normal4 = DataExpectation.create("abacb").expect("abc");

    @TestData
    public DataExpectation normal5 = DataExpectation
            .create("thesqtitxyetpxloeevdeqifkz")
            .expect("hesitxyplovdqfkz");

}
