package q150;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/pascals-triangle/
 *
 * Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
 *
 * ( 图 Q118_PIC.gif)
 *
 * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 *
 * Example:
 *
 * Input: 5
 * Output:
 * > [
 * >      [1],
 * >     [1,1],
 * >    [1,2,1],
 * >   [1,3,3,1],
 * >  [1,4,6,4,1]
 * > ]
 */
@RunWith(LeetCodeRunner.class)
public class Q118_PascalsTriangle {

    @Answer
    public List<List<Integer>> generate(int numRows) {
        if (numRows == 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> res = new ArrayList<>(numRows);
        res.add(Collections.singletonList(1));
        for (int i = 2; i <= numRows; i++) {
            List<Integer> newList = new ArrayList<>(i);
            newList.add(1);
            List<Integer> prev = res.get(i - 2);
            for (int j = 1; j < prev.size(); j++) {
                newList.add(prev.get(j - 1) + prev.get(j));
            }
            newList.add(1);
            res.add(newList);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(5)
            .expect(new int[][]{
                    {1},
                    {1, 1},
                    {1, 2, 1},
                    {1, 3, 3, 1},
                    {1, 4, 6, 4, 1}
            });
}
