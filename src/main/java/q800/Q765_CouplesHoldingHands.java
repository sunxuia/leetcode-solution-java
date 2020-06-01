package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/couples-holding-hands/
 *
 * N couples sit in 2N seats arranged in a row and want to hold hands. We want to know the minimum number of swaps
 * so that every couple is sitting side by side. A swap consists of choosing any two people, then they stand up and
 * switch seats.
 *
 * The people and seats are represented by an integer from 0 to 2N-1, the couples are numbered in order, the first
 * couple being (0, 1), the second couple being (2, 3), and so on with the last couple being (2N-2, 2N-1).
 *
 * The couples' initial seating is given by row[i] being the value of the person who is initially sitting in the i-th
 * seat.
 *
 * Example 1:
 *
 * Input: row = [0, 2, 1, 3]
 * Output: 1
 * Explanation: We only need to swap the second (row[1]) and third (row[2]) person.
 *
 * Example 2:
 *
 * Input: row = [3, 2, 0, 1]
 * Output: 0
 * Explanation: All couples are already seated side by side.
 *
 * Note:
 *
 * 1. len(row) is even and in the range of [4, 60].
 * 2. row is guaranteed to be a permutation of 0...len(row)-1.
 */
@RunWith(LeetCodeRunner.class)
public class Q765_CouplesHoldingHands {

    /**
     * https://www.cnblogs.com/grandyang/p/8716597.html
     * 贪婪算法, 从开头开始进行配对, 如果不匹配就从后面找出一个值来与其匹配.
     * 一对数字可以前面的不动, 也可以后面的不动, 这样得到的结果就是最终结果.
     */
    @Answer
    public int minSwapsCouples(int[] row) {
        int res = 0;
        for (int i = 0; i < row.length; i += 2) {
            // a^1 的结果就是和a 配对的数字
            if ((row[i] ^ 1) == row[i + 1]) {
                // 已经配对
                continue;
            }
            res++;
            for (int j = i + 1; j < row.length; j++) {
                if (row[j] == (row[i] ^ 1)) {
                    // 交换
                    row[j] = row[i + 1];
                    row[i + 1] = row[i] ^ 1;
                    break;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{0, 2, 1, 3}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 2, 0, 1}).expect(0);

}
