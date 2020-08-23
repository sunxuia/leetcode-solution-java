package q1000;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 976. Largest Perimeter Triangle
 * https://leetcode.com/problems/largest-perimeter-triangle/
 *
 * Given an array A of positive lengths, return the largest perimeter of a triangle with non-zero area, formed from 3 of
 * these lengths.
 *
 * If it is impossible to form any triangle of non-zero area, return 0.
 *
 * Example 1:
 *
 * Input: [2,1,2]
 * Output: 5
 *
 * Example 2:
 *
 * Input: [1,2,1]
 * Output: 0
 *
 * Example 3:
 *
 * Input: [3,2,3,4]
 * Output: 10
 *
 * Example 4:
 *
 * Input: [3,6,2,3]
 * Output: 8
 *
 * Note:
 *
 * 3 <= A.length <= 10000
 * 1 <= A[i] <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q976_LargestPerimeterTriangle {

    /**
     * 参考文档 https://blog.csdn.net/fuxuemingzhu/article/details/86426988
     * 为了构成最长的三角形变长, 只需要对排序后连续的3 个数字判断是否能构成三角形即可.
     */
    @Answer
    public int largestPerimeter(int[] A) {
        Arrays.sort(A);
        for (int i = A.length - 3; i >= 0; i--) {
            if (A[i] + A[i + 1] > A[i + 2]) {
                return A[i] + A[i + 1] + A[i + 2];
            }
        }
        return 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 1, 2}).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 1}).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{3, 2, 3, 4}).expect(10);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{3, 6, 2, 3}).expect(8);

}
