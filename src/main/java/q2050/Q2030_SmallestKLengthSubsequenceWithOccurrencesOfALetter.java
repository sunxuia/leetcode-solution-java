package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 2030. Smallest K-Length Subsequence With Occurrences of a Letter
 * https://leetcode.com/problems/smallest-k-length-subsequence-with-occurrences-of-a-letter/
 *
 * You are given a string s, an integer k, a letter letter, and an integer repetition.
 *
 * Return the lexicographically smallest subsequence of s of length k that has the letter letter appear at least
 * repetition times. The test cases are generated so that the letter appears in s at least repetition times.
 *
 * A subsequence is a string that can be derived from another string by deleting some or no characters without changing
 * the order of the remaining characters.
 *
 * A string a is lexicographically smaller than a string b if in the first position where a and b differ, string a has a
 * letter that appears earlier in the alphabet than the corresponding letter in b.
 *
 * Example 1:
 *
 * Input: s = "leet", k = 3, letter = "e", repetition = 1
 * Output: "eet"
 * Explanation: There are four subsequences of length 3 that have the letter 'e' appear at least 1 time:
 * - "lee" (from "leet")
 * - "let" (from "leet")
 * - "let" (from "leet")
 * - "eet" (from "leet")
 * The lexicographically smallest subsequence among them is "eet".
 *
 * Example 2:
 * (图Q2030_PIC.png)
 * Input: s = "leetcode", k = 4, letter = "e", repetition = 2
 * Output: "ecde"
 * Explanation: "ecde" is the lexicographically smallest subsequence of length 4 that has the letter "e" appear at least
 * 2 times.
 *
 * Example 3:
 *
 * Input: s = "bb", k = 2, letter = "b", repetition = 2
 * Output: "bb"
 * Explanation: "bb" is the only subsequence of length 2 that has the letter "b" appear at least 2 times.
 *
 * Constraints:
 *
 * 1 <= repetition <= k <= s.length <= 5 * 10^4
 * s consists of lowercase English letters.
 * letter is a lowercase English letter, and appears in s at least repetition times.
 */
@RunWith(LeetCodeRunner.class)
public class Q2030_SmallestKLengthSubsequenceWithOccurrencesOfALetter {

    /**
     * 根据 hint 可以用单调最小栈来做
     */
    @Answer
    public String smallestSubsequence(String s, int k, char letter, int repetition) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;

        // 表示在下面遍历中未被放弃的letter 的数量
        int letterCount = 0;
        for (int i = 0; i < n; i++) {
            if (cs[i] == letter) {
                letterCount++;
            }
        }
        // 表示在下面的遍历中组成最小子字符串还需要的letter 的数量
        int requireLetter = repetition;

        char[] stack = new char[k + 1];
        stack[0] = 'A';
        int top = 0;

        for (int i = 0; i < n; i++) {
            boolean pop, push;
            do {
                // 决定操作
                pop = push = false;
                if (top + n - i == k) {
                    // 长度不够了, 所有后面的字符都加入
                    push = true;
                } else if (cs[i] == letter) {
                    // 当前字符是 letter
                    if (stack[top] == letter) {
                        // 上一个也是letter, 只需要看字符串够不够长
                        push = top < k;
                    } else {
                        // 上一个不是letter, 需要确定是否要pop 栈顶, 当前 (cs[i] = letter) 是否可以入栈
                        pop = stack[top] > cs[i];
                        // letterCount == repetition 说明没有额外letter, cs[i] 必须入栈
                        // top + Math.max(0, requireLetter - 1) < k 表示扣除为letter 预留的空位(-1 表示当前cs[i])后还有其他空位
                        push = letterCount == repetition || top + Math.max(0, requireLetter - 1) < k;
                    }
                } else {
                    // 当前字符不是letter, 需要确定是否要pop 栈顶, 当前letter 是否可以入栈
                    // letterCount == repetition && stack[top] == letter 表示stack[top] 的letter 是必须的.
                    pop = stack[top] > cs[i] && !(letterCount == repetition && stack[top] == letter);
                    // 表示扣除为letter 预留的空位后是否还有其他空位为 cs[i] 预留
                    push = top + Math.max(0, requireLetter) < k;
                }

                // 执行操作
                if (pop) {
                    // 出栈操作
                    if (stack[top] == letter) {
                        requireLetter++;
                        letterCount--;
                    }
                    top--;
                } else if (push) {
                    // 入栈操作
                    stack[++top] = cs[i];
                    if (cs[i] == letter) {
                        requireLetter--;
                    }
                } else if (cs[i] == letter) {
                    // 当前cs[i] 被放弃
                    letterCount--;
                }
            } while (pop);
        }
        return new String(stack, 1, k);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("leet", 3, 'e', 1).expect("eet");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("leetcode", 4, 'e', 2).expect("ecde");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("bb", 2, 'b', 2).expect("bb");

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith("aaabbbcccddd", 3, 'b', 2)
            .expect("abb");

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith("hjjhhhmhhwhz", 6, 'h', 5)
            .expect("hhhhhh");

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith("xwvvsrvvihvvvebvavfhivvo", 13, 'v', 11)
            .expect("vvrvvhvvvvvvv");

    @TestData
    public DataExpectation normal4 = DataExpectation.createWith("bba", 1, 'b', 1).expect("b");

}
