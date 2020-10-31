package q1300;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1253. Reconstruct a 2-Row Binary Matrix
 * https://leetcode.com/problems/reconstruct-a-2-row-binary-matrix/
 *
 * Given the following details of a matrix with n columns and 2 rows :
 *
 * The matrix is a binary matrix, which means each element in the matrix can be 0 or 1.
 * The sum of elements of the 0-th(upper) row is given as upper.
 * The sum of elements of the 1-st(lower) row is given as lower.
 * The sum of elements in the i-th column(0-indexed) is colsum[i], where colsum is given as an integer array with length
 * n.
 *
 * Your task is to reconstruct the matrix with upper, lower and colsum.
 *
 * Return it as a 2-D integer array.
 *
 * If there are more than one valid solution, any of them will be accepted.
 *
 * If no valid solution exists, return an empty 2-D array.
 *
 * Example 1:
 *
 * Input: upper = 2, lower = 1, colsum = [1,1,1]
 * Output: [[1,1,0],[0,0,1]]
 * Explanation: [[1,0,1],[0,1,0]], and [[0,1,1],[1,0,0]] are also correct answers.
 *
 * Example 2:
 *
 * Input: upper = 2, lower = 3, colsum = [2,2,1,1]
 * Output: []
 *
 * Example 3:
 *
 * Input: upper = 5, lower = 5, colsum = [2,1,2,0,1,0,1,2,0,1]
 * Output: [[1,1,1,0,1,0,0,1,0,0],[1,0,1,0,0,0,1,1,0,1]]
 *
 * Constraints:
 *
 * 1 <= colsum.length <= 10^5
 * 0 <= upper, lower <= colsum.length
 * 0 <= colsum[i] <= 2
 */
@RunWith(LeetCodeRunner.class)
public class Q1253_ReconstructA2RowBinaryMatrix {

    @Answer
    public List<List<Integer>> reconstructMatrix(int upper, int lower, int[] colsum) {
        final int n = colsum.length;
        int oneCount = 0;
        for (int i = 0; i < n; i++) {
            if (colsum[i] == 1) {
                oneCount++;
            } else if (colsum[i] == 2) {
                upper--;
                lower--;
            }
        }
        if (upper < 0 || lower < 0 || upper + lower != oneCount) {
            return Collections.emptyList();
        }
        Integer[] row0 = new Integer[n];
        Integer[] row1 = new Integer[n];
        for (int i = 0; i < n; i++) {
            switch (colsum[i]) {
                case 0:
                    row0[i] = row1[i] = 0;
                    break;
                case 1:
                    if (upper > 0) {
                        row0[i] = 1;
                        row1[i] = 0;
                        upper--;
                    } else {
                        row0[i] = 0;
                        row1[i] = 1;
                    }
                    break;
                case 2:
                    row0[i] = row1[i] = 1;
                    break;
                default:
            }
        }
        return Arrays.asList(Arrays.asList(row0), Arrays.asList(row1));
    }

    @TestData
    public DataExpectation example1 = createTestData(2, 1, new int[]{1, 1, 1});

    private DataExpectation createTestData(int upper, int lower, int[] colsum) {
        return DataExpectation.createWith(upper, lower, colsum)
                .expect(null)
                .assertMethod(((expect, actual, originAssertMethod) -> {
                    List<List<Integer>> res = (List<List<Integer>>) actual;
                    final int n = colsum.length;
                    Assert.assertEquals(2, res.size());
                    Assert.assertEquals(n, res.get(0).size());
                    Assert.assertEquals(n, res.get(1).size());
                    int upperSum = 0, lowerSum = 0;
                    for (int i = 0; i < n; i++) {
                        Integer cell0 = res.get(0).get(i);
                        Integer cell1 = res.get(1).get(i);
                        Assert.assertNotNull(cell0);
                        Assert.assertNotNull(cell1);
                        upperSum += cell0;
                        lowerSum += cell1;
                        Assert.assertEquals(colsum[i], cell0 + cell1);
                    }
                    Assert.assertEquals(upper, upperSum);
                    Assert.assertEquals(lower, lowerSum);
                }));
    }

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, 3, new int[]{2, 2, 1, 1})
            .expect(Collections.emptyList());

    @TestData
    public DataExpectation example3 = createTestData(5, 5, new int[]{2, 1, 2, 0, 1, 0, 1, 2, 0, 1});

}
