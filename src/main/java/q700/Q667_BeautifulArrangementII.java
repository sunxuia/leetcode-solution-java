package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/beautiful-arrangement-ii/
 *
 * Given two integers n and k, you need to construct a list which contains n different positive integers ranging
 * from 1 to n and obeys the following requirement:
 * Suppose this list is [a1, a2, a3, ... , an], then the list [|a1 - a2|, |a2 - a3|, |a3 - a4|, ... , |an-1 - an|]
 * has exactly k distinct integers.
 *
 * If there are multiple answers, print any of them.
 *
 * Example 1:
 *
 * Input: n = 3, k = 1
 * Output: [1, 2, 3]
 * Explanation: The [1, 2, 3] has three different positive integers ranging from 1 to 3, and the [1, 1] has exactly 1
 * distinct integer: 1.
 *
 * Example 2:
 *
 * Input: n = 3, k = 2
 * Output: [1, 3, 2]
 * Explanation: The [1, 3, 2] has three different positive integers ranging from 1 to 3, and the [2, 1] has exactly 2
 * distinct integers: 1 and 2.
 *
 * Note:
 *
 * The n and k are in the range 1 <= k < n <= 10^4.
 */
@RunWith(LeetCodeRunner.class)
public class Q667_BeautifulArrangementII {

    /**
     * 可以在纸上画一画, 根据规律, 只需要反序部分数字即可得到最大的数字差.
     * 在反序的数字里面继续反序可以得到更小的数字差.
     */
    @Answer
    public int[] constructArray(int n, int k) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = i + 1;
        }
        // 每次对 [i, k] 中的元素进行反序操作, 得出一个数字差.
        // (这样效率比较低)
        for (int i = 1; i < k; i++) {
            int left = i, right = k;
            while (left < right) {
                int t = res[left];
                res[left] = res[right];
                res[right] = t;
                left++;
                right--;
            }
        }
        return res;
    }

    // Solution 中给出的解法. 与上面思路类似, 做了优化.
    @Answer
    public int[] constructArray2(int n, int k) {
        int[] res = new int[n];
        int v = 0;
        // 前n - k 个元素保持原样递增
        for (int i = 1; i < n - k; i++) {
            res[v++] = i;
        }
        // 后面的元素根据反序的规律填写对应的值即可
        for (int i = 0; i <= k; i++) {
            res[v++] = (i % 2 == 0) ? (n - k + i / 2) : (n - i / 2);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(3, 1).expect(new int[]{1, 2, 3});

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(3)
            .addArgument(2)
            .expect(new int[]{1, 3, 2})
            .orExpect(new int[]{3, 1, 2})
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(5)
            .addArgument(2)
            .expect(new int[]{1, 3, 2, 4, 5})
            .orExpect(new int[]{1, 2, 3, 5, 4})
            .build();

}
