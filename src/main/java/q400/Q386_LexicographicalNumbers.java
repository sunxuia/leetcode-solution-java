package q400;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import q450.Q440_KthSmallestInLexicographicalOrder;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/lexicographical-numbers/
 *
 * Given an integer n, return 1 - n in lexicographical order.
 *
 * For example, given 13, return: [1,10,11,12,13,2,3,4,5,6,7,8,9].
 *
 * Please optimize your algorithm to use less time and space. The input size may be as large as 5,000,000.
 *
 * 相关题目 {@link Q440_KthSmallestInLexicographicalOrder}
 */
@RunWith(LeetCodeRunner.class)
public class Q386_LexicographicalNumbers {

    @Answer
    public List<Integer> lexicalOrder(int n) {
        List<Integer> res = new ArrayList<>(n);
        int i = 1;
        do {
            res.add(i);
            // 尝试下一个字典值
            i *= 10;
            // 如果下一个字典值不存在, 就需要跑到上一位 +1.
            while (i > n) {
                // 如果已有的字典值末尾已经是9, 则下一个字典值就需要跑到上一位+1 了.
                do {
                    i /= 10;
                } while (i % 10 == 9);
                i++;
            }

        } while (i > 1);
        return res;
    }

    // LeetCode 上比较快的方式, 通过dfs 的方式, 思路与上面类似.
    @Answer
    public List<Integer> lexicalOrder2(int n) {
        List<Integer> res = new ArrayList<>(n);
        for (int i = 1; i <= 9; i++) {
            dfs(res, n, i);
        }
        return res;
    }

    private void dfs(List<Integer> res, int n, int num) {
        if (num > n) {
            return;
        }
        res.add(num);
        // 尝试下一位的字典值
        for (int i = 0; i < 10; i++) {
            if ((num * 10) + i > n) {
                return;
            }
            dfs(res, n, (num * 10) + i);
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.create(13)
            .expect(Arrays.asList(1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9));

}
