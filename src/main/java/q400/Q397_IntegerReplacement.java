package q400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/integer-replacement/
 *
 * Given a positive integer n and you can do operations as follow:
 *
 * If n is even, replace n with n/2.
 * If n is odd, you can replace n with either n + 1 or n - 1.
 *
 * What is the minimum number of replacements needed for n to become 1?
 *
 * Example 1:
 *
 * Input:
 * 8
 *
 * Output:
 * 3
 *
 * Explanation:
 * 8 -> 4 -> 2 -> 1
 *
 * Example 2:
 *
 * Input:
 * 7
 *
 * Output:
 * 4
 *
 * Explanation:
 * 7 -> 8 -> 4 -> 2 -> 1
 * or
 * 7 -> 6 -> 3 -> 2 -> 1
 */
@RunWith(LeetCodeRunner.class)
public class Q397_IntegerReplacement {

    // 这种方式比较慢
    @Answer
    public int integerReplacement(int n) {
        return dfs(n);
    }

    private int dfs(long n) {
        if (n == 1) {
            return 0;
        }
        if (n % 2 == 1) {
            return 1 + Math.min(dfs(n + 1), dfs(n - 1));
        } else {
            return 1 + dfs(n / 2);
        }
    }

    // https://www.cnblogs.com/grandyang/p/5873525.html
    // 通过观察, 除了3, 所有+1 就变成4 的倍数的奇数, 适合+1 运算, 其它适合-1运算 (7加1减1 步骤一样).
    @Answer
    public int integerReplacement2(int n) {
        // 针对 Integer.MAX_VALUE 会在下面自增的情况
        long val = n;
        int res = 0;
        while (val > 1) {
            res++;
            if ((val & 1) == 1) {
                // 第0 位是1, 如果第1 位也是1, 则+1 后就是4 的倍数
                if (val > 3 && (val & 2) > 1) {
                    val++;
                } else {
                    val--;
                }
            } else {
                val >>= 1;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(8).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(7).expect(4);

    @TestData
    public DataExpectation boder = DataExpectation.create(Integer.MAX_VALUE).expect(32);

}
