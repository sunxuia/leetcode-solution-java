package q950;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 932. Beautiful Array
 * https://leetcode.com/problems/beautiful-array/
 *
 * For some fixed N, an array A is beautiful if it is a permutation of the integers 1, 2, ..., N, such that:
 *
 * For every i < j, there is no k with i < k < j such that A[k] * 2 = A[i] + A[j].
 *
 * Given N, return any beautiful array A.  (It is guaranteed that one exists.)
 *
 * Example 1:
 *
 * Input: 4
 * Output: [2,1,4,3]
 *
 * Example 2:
 *
 * Input: 5
 * Output: [3,1,2,5,4]
 *
 * Note:
 *
 * 1 <= N <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q932_BeautifulArray {

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(4)
            .expect(new int[]{2, 1, 4, 3})
            .orExpect(new int[]{1, 3, 2, 4})
            .build();
    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(5)
            .expect(new int[]{3, 1, 2, 5, 4})
            .orExpect(new int[]{1, 5, 3, 2, 4})
            .build();

    /**
     * 分治法.
     * 参考文档 https://www.cnblogs.com/grandyang/p/12287146.html
     */
    @Answer
    public int[] beautifulArray(int N) {
        List<Integer> list = Collections.singletonList(1);
        while (list.size() < N) {
            List<Integer> temp = new ArrayList<>();
            for (int num : list) {
                if (num * 2 - 1 <= N) {
                    temp.add(num * 2 - 1);
                }
            }
            for (int num : list) {
                if (num * 2 <= N) {
                    temp.add(num * 2);
                }
            }
            list = temp;
        }

        int[] res = new int[N];
        for (int i = 0; i < N; i++) {
            res[i] = list.get(i);
        }
        return res;
    }

}
