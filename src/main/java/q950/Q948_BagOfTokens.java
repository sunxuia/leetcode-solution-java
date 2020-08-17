package q950;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 948. Bag of Tokens
 * https://leetcode.com/problems/bag-of-tokens/
 *
 * You have an initial power P, an initial score of 0 points, and a bag of tokens.
 *
 * Each token can be used at most once, has a value token[i], and has potentially two ways to use it.
 *
 * If we have at least token[i] power, we may play the token face up, losing token[i] power, and gaining 1 point.
 * If we have at least 1 point, we may play the token face down, gaining token[i] power, and losing 1 point.
 *
 * Return the largest number of points we can have after playing any number of tokens.
 *
 * Example 1:
 *
 * Input: tokens = [100], P = 50
 * Output: 0
 *
 * Example 2:
 *
 * Input: tokens = [100,200], P = 150
 * Output: 1
 *
 * Example 3:
 *
 * Input: tokens = [100,200,300,400], P = 200
 * Output: 2
 *
 * Note:
 *
 * tokens.length <= 1000
 * 0 <= tokens[i] < 10000
 * 0 <= P < 10000
 *
 * 题解:
 * 初始有P 点数, 0 分数, 经过tokens.length 轮交易, 每次交易可以执行如下操作:
 * 1. 什么都不做.
 * 2. 拥有的点数 >= tokens[i] 时, 减去tokens[i] 点, 分数+1
 * 3. 拥有的分数 >= 1 时, 减去 1 分, 点数+ tokens[i]
 * 问最大的分数结果.
 * 注意这里的tokens 是无序的, 即不需要按照顺序从前往后取用tokens 的值, 这是一个隐含条件.
 */
@RunWith(LeetCodeRunner.class)
public class Q948_BagOfTokens {

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{100}, 50).expect(0);
    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{100, 200}, 150).expect(1);
    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{100, 200, 300, 400}, 200).expect(2);
    @TestData
    public DataExpectation border = DataExpectation.createWith(new int[0], 85).expect(0);
    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{26}, 51).expect(1);
    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(new int[]{71, 55, 82}, 54).expect(0);

    /**
     * 按照 https://www.cnblogs.com/grandyang/p/13092555.html 中的提示
     * 才知道有一个tokens 是无序的隐含条件, 否则是无法像related topics 中提示的
     * 那样使用贪婪算法去解的.
     * 使用贪婪算法就是尽量在tokens[i] 小的时候卖出, tokens[i] 大的时候买入.
     */
    @Answer
    public int bagOfTokensScore(int[] tokens, int P) {
        Arrays.sort(tokens);
        int res = 0, left = 0, right = tokens.length - 1;
        while (left <= right) {
            while (left <= right && P >= tokens[left]) {
                P -= tokens[left++];
                res++;
            }
            if (left < right && res > tokens.length - 1 - right) {
                P += tokens[right--];
                res--;
            } else {
                left++;
            }
        }
        return res;
    }

}
