package q150;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/pascals-triangle-ii/
 *
 * Given a non-negative index k where k ≤ 33, return the kth index row of the Pascal's triangle.
 *
 * Note that the row index starts from 0.
 *
 * (图见 Q119_PIC.fig)
 *
 * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 *
 * Example:
 *
 * Input: 3
 * Output: [1,3,3,1]
 *
 * Follow up:
 *
 * Could you optimize your algorithm to use only O(k) extra space?
 */
@RunWith(LeetCodeRunner.class)
public class Q119_PascalsTriangleII {

    // 注意第1 行的rowIndex = 0
    @Answer
    public List<Integer> getRow(int rowIndex) {
        List<Integer> res = new ArrayList<>(rowIndex + 1);
        while (rowIndex-- >= 0) {
            for (int i = res.size() - 1; i > 0; i--) {
                res.set(i, res.get(i - 1) + res.get(i));
            }
            res.add(1);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(3).expect(new int[]{1, 3, 3, 1});

}
