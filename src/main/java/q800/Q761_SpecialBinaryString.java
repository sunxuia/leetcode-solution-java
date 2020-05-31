package q800;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/special-binary-string/
 *
 * Special binary strings are binary strings with the following two properties:
 *
 * The number of 0's is equal to the number of 1's.
 * Every prefix of the binary string has at least as many 1's as 0's.
 *
 * Given a special string S, a move consists of choosing two consecutive, non-empty, special substrings of S, and
 * swapping them. (Two strings are consecutive if the last character of the first string is exactly one index before
 * the first character of the second string.)
 *
 * At the end of any number of moves, what is the lexicographically largest resulting string possible?
 *
 * Example 1:
 *
 * Input: S = "11011000"
 * Output: "11100100"
 * Explanation:
 * The strings "10" [occuring at S[1]] and "1100" [at S[3]] are swapped.
 * This is the lexicographically largest string possible after some number of swaps.
 *
 * Note:
 *
 * 1. S has length at most 50.
 * 2. S is guaranteed to be a special binary string as defined above.
 */
@RunWith(LeetCodeRunner.class)
public class Q761_SpecialBinaryString {

    /**
     * 根据 https://www.cnblogs.com/grandyang/p/8606024.html 中的提示, 这题的 1 和 0
     * 可以理解为括号的 "(" 和 ")" 符号, 1和0 的数目相等, 且任意前缀中的1 的数量要 >= 0 的数量.
     *
     * 现在题目要求交换子字符串, 生成字母顺序最大的特殊字符串, 注意这里交换的子字符串必须是首尾相接的非空特殊字符串.
     * 理解了题目意思之后就变得简单了.
     */
    @Answer
    public String makeLargestSpecial(String S) {
        List<String> children = new ArrayList<>();

        // 对子区间排序
        int count = 0, last = -1;
        for (int i = 0; i < S.length(); i++) {
            count += S.charAt(i) == '1' ? 1 : -1;
            if (count == 0) {
                children.add("1" + makeLargestSpecial(S.substring(last + 2, i)) + "0");
                last = i;
            }
        }

        // 对当前区间冒泡排序
        for (int i = 0; i < children.size(); i++) {
            for (int j = children.size() - 1; j > i; j--) {
                String before = children.get(j - 1);
                String curr = children.get(j);
                if ((before + curr).compareTo(curr + before) < 0) {
                    children.set(j - 1, curr);
                    children.set(j, before);
                }
            }
        }

        // 组合结果
        StringBuilder sb = new StringBuilder();
        for (String child : children) {
            sb.append(child);
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example = DataExpectation.create("11011000").expect("11100100");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("1010101100").expect("1100101010");

    @TestData
    public DataExpectation normal2 = DataExpectation.create("101100101100").expect("110011001010");

    @TestData
    public DataExpectation normal3 = DataExpectation.create("1011100100101100").expect("1110010011001010");

}
