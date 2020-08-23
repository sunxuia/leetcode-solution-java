package q1000;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 982. Triples with Bitwise AND Equal To Zero
 * https://leetcode.com/problems/triples-with-bitwise-and-equal-to-zero/
 *
 * Given an array of integers A, find the number of triples of indices (i, j, k) such that:
 *
 * 0 <= i < A.length
 * 0 <= j < A.length
 * 0 <= k < A.length
 * A[i] & A[j] & A[k] == 0, where & represents the bitwise-AND operator.
 *
 * Example 1:
 *
 * Input: [2,1,3]
 * Output: 12
 * Explanation: We could choose the following i, j, k triples:
 * (i=0, j=0, k=1) : 2 & 2 & 1
 * (i=0, j=1, k=0) : 2 & 1 & 2
 * (i=0, j=1, k=1) : 2 & 1 & 1
 * (i=0, j=1, k=2) : 2 & 1 & 3
 * (i=0, j=2, k=1) : 2 & 3 & 1
 * (i=1, j=0, k=0) : 1 & 2 & 2
 * (i=1, j=0, k=1) : 1 & 2 & 1
 * (i=1, j=0, k=2) : 1 & 2 & 3
 * (i=1, j=1, k=0) : 1 & 1 & 2
 * (i=1, j=2, k=0) : 1 & 3 & 2
 * (i=2, j=0, k=1) : 3 & 2 & 1
 * (i=2, j=1, k=0) : 3 & 1 & 2
 *
 * Note:
 *
 * 1 <= A.length <= 1000
 * 0 <= A[i] < 2^16
 */
@RunWith(LeetCodeRunner.class)
public class Q982_TriplesWithBitwiseAndEqualToZero {

    /**
     * 记忆化的方式
     * 参考文档 http://www.noteanddata.com/leetcode-982-Triples-with-Bitwise-AND-Equal-To-Zero-java-solution-note.html
     * 首先将两两相与的结果缓存, 然后再计算第3 个数.
     */
    @Answer
    public int countTriplets(int[] A) {
        final int n = A.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = A[i] & A[j];
                map.put(val, map.getOrDefault(val, 0) + 1);
            }
        }

        int res = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            for (int i = 0; i < n; i++) {
                if ((A[i] & entry.getKey()) == 0) {
                    res += entry.getValue();
                }
            }
        }
        return res;
    }

    /**
     * dp 的方式, 参考文档同上. 这个是缓存每次与的结果.
     */
    @Answer
    public int countTriplets_dp(int[] A) {
        // 1 << 16 == 65536
        final int n = A.length, m = 1 << 16;
        // dp[i][j] 表示第i 次与得到结果为j 的组合方式.
        int[][] dp = new int[3][m];
        for (int a : A) {
            dp[0][a]++;
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < m; j++) {
                // 之前的组合结果再和A 相与
                for (int a : A) {
                    dp[i + 1][a & j] += dp[i][j];
                }
            }
        }
        return dp[2][0];
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{2, 1, 3}).expect(12);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{0, 0, 0}).expect(27);

}
