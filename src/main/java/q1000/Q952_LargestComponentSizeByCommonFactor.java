package q1000;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 952. Largest Component Size by Common Factor
 * https://leetcode.com/problems/largest-component-size-by-common-factor/
 *
 * Given a non-empty array of unique positive integers A, consider the following graph:
 *
 * - There are A.length nodes, labelled A[0] to A[A.length - 1];
 * - There is an edge between A[i] and A[j] if and only if A[i] and A[j] share a common factor greater than 1.
 *
 * Return the size of the largest connected component in the graph.
 *
 * Example 1:
 *
 * Input: [4,6,15,35]
 * Output: 4
 * (图 Q952_PIC1.png)
 *
 * Example 2:
 *
 * Input: [20,50,9,63]
 * Output: 2
 * (图 Q952_PIC2.png)
 *
 * Example 3:
 *
 * Input: [2,3,6,7,4,12,21,39]
 * Output: 8
 * (图 Q952_PIC3.png)
 *
 * Note:
 *
 * 1 <= A.length <= 20000
 * 1 <= A[i] <= 100000
 */
@RunWith(LeetCodeRunner.class)
public class Q952_LargestComponentSizeByCommonFactor {

    /**
     * gcd 求2 个数字之间最大公约数的方式会超时.
     * 参考 https://www.cnblogs.com/grandyang/p/13253468.html
     * 可以使用数字值作为并查集, 使用A 中元素的约数来作为不同数字之间的联系.
     */
    @Answer
    public int largestComponentSize(int[] A) {
        int max = Arrays.stream(A).max().orElse(0);
        int[] roots = new int[max + 1];
        for (int i = 0; i <= max; i++) {
            roots[i] = i;
        }
        for (int a : A) {
            for (int i = (int) Math.sqrt(a); i > 1; i--) {
                if (i * i > a) {
                    break;
                }
                if (a % i == 0) {
                    roots[getRoot(roots, i)] = getRoot(roots, a);
                    roots[getRoot(roots, a / i)] = getRoot(roots, a);
                }
            }
        }

        int[] times = new int[max + 1];
        for (int a : A) {
            times[getRoot(roots, a)]++;
        }
        int res = 0;
        for (int time : times) {
            res = Math.max(res, time);
        }
        return res;
    }

    private int getRoot(int[] roots, int i) {
        return roots[i] == i ? i : (roots[i] = getRoot(roots, roots[i]));
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{4, 6, 15, 35}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{20, 50, 9, 63}).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{2, 3, 6, 7, 4, 12, 21, 39}).expect(8);

    // 18176 个数字
    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read(int[].class))
            .expect(16998);

}
