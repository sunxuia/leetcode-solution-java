package q400;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/russian-doll-envelopes/
 *
 * You have a number of envelopes with widths and heights given as a pair of integers (w, h). One envelope can fit
 * into another if and only if both the width and height of one envelope is greater than the width and height of the
 * other envelope.
 *
 * What is the maximum number of envelopes can you Russian doll? (put one inside other)
 *
 * Note:
 * Rotation is not allowed.
 *
 * Example:
 *
 * Input: [[5,4],[6,4],[6,7],[2,3]]
 * Output: 3
 * Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
 */
@RunWith(LeetCodeRunner.class)
public class Q353_RussianDollEnvelopes {

    // dfs 的这种方式比较慢
    @Answer
    public int maxEnvelopes(int[][] envelopes) {
        int res = 0;
        int[] max = new int[envelopes.length];
        boolean[] used = new boolean[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            res = Math.max(res, dfs(envelopes, max, used, i));
        }
        return res;
    }

    private int dfs(int[][] envelopes, int[] max, boolean[] used, int cur) {
        if (max[cur] > 0) {
            return max[cur];
        }
        used[cur] = true;
        int res = 0;
        for (int i = 0; i < envelopes.length; i++) {
            if (!used[i] && envelopes[i][0] < envelopes[cur][0]
                    && envelopes[i][1] < envelopes[cur][1]) {
                res = Math.max(res, dfs(envelopes, max, used, i));
            }
        }
        used[cur] = false;
        max[cur] = ++res;
        return res;
    }

    // LeetCode 上比较快的解法:
    // 将信封按照宽度升序排序, 相同宽度按照高度降序排列, 然后通过找出比它小的高度
    @Answer
    public int maxEnvelopes2(int[][] envelopes) {
        Arrays.sort(envelopes, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        int[] heights = Stream.of(envelopes).mapToInt(i -> i[1]).toArray();

        int[] dp = new int[envelopes.length];
        int len = 0;
        for (int height : heights) {
            // 找出这个高度应该插入的位置
            int found = Arrays.binarySearch(dp, 0, len, height);
            // found >= 0 表示该信封宽度 >= 之前某信封, 但是高度则 == 之前某信封, 则该信封的最大嵌套数量 <= 之前某信封.
            // found < 0 表示没有相同元素, 元素可以插入在 insert = -found - 1 的位置.
            // 如果insert == len 表示该信封宽度 >= 之前信封, 且高度 >= 之前信封, 所以该信封的最大嵌套数量就是之前最大嵌套数量 + 1.
            // 如果insert < len 表示该信封宽度 >= 之前某信封, 但高度 < 之前某信封, 将高度更新为当前信封高度,
            // 之后的信封宽度肯定 >= 该信封, 只要高度 > 该信封, 则可以进行嵌套.
            if (found < 0) {
                int insert = -found - 1;
                dp[insert] = height;
                if (insert == len) {
                    len++;
                }
            }
        }
        return len;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[][]{
            {5, 4}, {6, 4}, {6, 7}, {2, 3}
    }).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {4, 5}, {4, 6}, {6, 7}, {2, 3}, {1, 1}
    }).expect(4);

}
