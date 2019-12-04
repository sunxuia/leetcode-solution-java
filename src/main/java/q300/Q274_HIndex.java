package q300;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/h-index/
 *
 * Given an array of citations (each citation is a non-negative integer) of a researcher, write a function to compute
 * the researcher's h-index.
 *
 * According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N papers have at least
 * h citations each, and the other N − h papers have no more than h citations each."
 *
 * Example:
 *
 * Input: citations = [3,0,6,1,5]
 * Output: 3
 * Explanation: [3,0,6,1,5] means the researcher has 5 papers in total and each of them had
 * received 3, 0, 6, 1, 5 citations respectively.
 * Since the researcher has 3 papers with at least 3 citations each and the remaining
 * two with no more than 3 citations each, her h-index is 3.
 * Note: If there are several possible values for h, the maximum one is taken as the h-index.
 */
@RunWith(LeetCodeRunner.class)
public class Q274_HIndex {

    // 一般的想法, 通过排序来解决.
    @Answer
    public int hIndex(int[] citations) {
        final int n = citations.length;
        Arrays.sort(citations);
        for (int i = 0; i < n; i++) {
            if (n - i <= citations[i]) {
                return n - i;
            }
        }
        return 0;
    }

    /**
     * leetCode 上最快的解法. 通过2 次循环计算得到结果.
     * 第一次循环: 计算引用i 次的文章次数 ( >=n 次则因为文章就这么多, 所以最多就是n)
     * 第二次循环: 从高位开始计算引用的次数, 按照定义得到结果.
     */
    @Answer
    public int hIndex_leetCode(int[] citations) {
        final int n = citations.length;
        int[] hs = new int[n + 1];
        for (int c : citations) {
            if (c >= n) {
                hs[n]++;
            } else {
                hs[c]++;
            }
        }
        int count = 0;
        for (int i = n; i >= 0; i--) {
            count += hs[i];
            if (count >= i) {
                return i;
            }
        }
        return 0;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{3, 0, 6, 1, 5}).expect(3);

}
