package q550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/beautiful-arrangement/
 *
 * Suppose you have N integers from 1 to N. We define a beautiful arrangement as an array that is constructed by
 * these N numbers successfully if one of the following is true for the ith position (1 <= i <= N) in this array:
 *
 * The number at the ith position is divisible by i.
 * i is divisible by the number at the ith position.
 *
 *
 *
 * Now given N, how many beautiful arrangements can you construct?
 *
 * Example 1:
 *
 * Input: 2
 * Output: 2
 * Explanation:
 *
 * The first beautiful arrangement is [1, 2]:
 *
 * Number at the 1st position (i=1) is 1, and 1 is divisible by i (i=1).
 *
 * Number at the 2nd position (i=2) is 2, and 2 is divisible by i (i=2).
 *
 * The second beautiful arrangement is [2, 1]:
 *
 * Number at the 1st position (i=1) is 2, and 2 is divisible by i (i=1).
 *
 * Number at the 2nd position (i=2) is 1, and i (i=2) is divisible by 1.
 *
 *
 *
 * Note:
 *
 * N is a positive integer and will not exceed 15.
 */
@RunWith(LeetCodeRunner.class)
public class Q526_BeautifulArrangement {

    /**
     * 没什么思路, 参考 Solution 中的做法, 使用回溯法, 一个一个试过去, 也比较简单.
     * LeetCode 上最快的方式也是基于此做了一些改进.
     */
    @Answer
    public int countArrangement(int N) {
        return calculate(N, 1, new boolean[N + 1]);
    }

    /**
     * pos 表示要设置的下标.
     * visited 表示下标对应的数字是否已经被设置过了.
     */
    public int calculate(int N, int pos, boolean[] visited) {
        if (pos > N) {
            return 1;
        }
        int res = 0;
        for (int i = 1; i <= N; i++) {
            if (!visited[i] && (pos % i == 0 || i % pos == 0)) {
                visited[i] = true;
                res += calculate(N, pos + 1, visited);
                visited[i] = false;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation exmple = DataExpectation.create(2).expect(2);

}
