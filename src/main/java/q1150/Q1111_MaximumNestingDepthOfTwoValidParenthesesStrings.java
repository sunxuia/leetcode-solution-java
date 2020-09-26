package q1150;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1111. Maximum Nesting Depth of Two Valid Parentheses Strings
 * https://leetcode.com/problems/maximum-nesting-depth-of-two-valid-parentheses-strings/
 *
 * A string is a valid parentheses string (denoted VPS) if and only if it consists of "(" and ")" characters only, and:
 *
 * It is the empty string, or
 * It can be written as AB (A concatenated with B), where A and B are VPS's, or
 * It can be written as (A), where A is a VPS.
 *
 * We can similarly define the nesting depth depth(S) of any VPS S as follows:
 *
 * depth("") = 0
 * depth(A + B) = max(depth(A), depth(B)), where A and B are VPS's
 * depth("(" + A + ")") = 1 + depth(A), where A is a VPS.
 *
 * For example,  "", "()()", and "()(()())" are VPS's (with nesting depths 0, 1, and 2), and ")(" and "(()" are not
 * VPS's.
 *
 * Given a VPS seq, split it into two disjoint subsequences A and B, such that A and B are VPS's (and A.length +
 * B.length = seq.length).
 *
 * Now choose any such A and B such that max(depth(A), depth(B)) is the minimum possible value.
 *
 * Return an answer array (of length seq.length) that encodes such a choice of A and B:  answer[i] = 0 if seq[i] is part
 * of A, else answer[i] = 1.  Note that even though multiple answers may exist, you may return any of them.
 *
 * Example 1:
 *
 * Input: seq = "(()())"
 * Output: [0,1,1,1,1,0]
 *
 * Example 2:
 *
 * Input: seq = "()(())()"
 * Output: [0,0,0,1,1,0,1,1]
 *
 * Constraints:
 *
 * 1 <= seq.size <= 10000
 *
 * 题解: 这题更友好的解释参见
 * https://leetcode-cn.com/problems/maximum-nesting-depth-of-two-valid-parentheses-strings/
 */
@RunWith(LeetCodeRunner.class)
public class Q1111_MaximumNestingDepthOfTwoValidParenthesesStrings {

    /**
     * 这题就是要找出2 个深度是 depth(seq) / 2 的子序列.
     */
    @Answer
    public int[] maxDepthAfterSplit(String seq) {
        final char[] sc = seq.toCharArray();
        final int n = sc.length;

        // 求 seq 的最大深度
        int maxDepth = 0, depth = 0;
        for (int i = 0; i < n; i++) {
            depth += sc[i] == '(' ? 1 : -1;
            maxDepth = Math.max(maxDepth, depth);
        }

        // 分割 seq, A 的深度为 maxDepth 的一半即可.
        int halfDepth = maxDepth / 2 + maxDepth % 2;
        int[] res = new int[n];
        int depthA = 0, depthB = 0;
        for (int i = 0; i < n; i++) {
            if (sc[i] == '(') {
                if (depthA < halfDepth) {
                    res[i] = 0;
                    depthA++;
                } else {
                    res[i] = 1;
                    depthB++;
                }
            } else {
                if (depthB > 0) {
                    res[i] = 1;
                    depthB--;
                } else {
                    res[i] = 0;
                    depthA--;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = createTestData("(()())", 2);

    private DataExpectation createTestData(String seq, int maxDepth) {
        return DataExpectation.builder()
                .addArgument(seq)
                .assertMethod((int[] res) -> {
                    final int n = seq.length();
                    Assert.assertEquals(n, res.length);
                    int depthA = 0, depthB = 0;
                    for (int i = 0; i < n; i++) {
                        Assert.assertTrue(res[i] == 0 || res[i] == 1);
                        if (seq.charAt(i) == '(') {
                            if (res[i] == 0) {
                                depthA++;
                            } else {
                                depthB++;
                            }
                        } else {
                            if (res[i] == 0) {
                                depthA--;
                            } else {
                                depthB--;
                            }
                        }
                        Assert.assertTrue(0 <= depthA && depthA <= maxDepth / 2);
                        Assert.assertTrue(0 <= depthB && depthB <= maxDepth / 2);
                    }
                }).build();
    }

    @TestData
    public DataExpectation example2 = createTestData("()(())()", 2);

}
