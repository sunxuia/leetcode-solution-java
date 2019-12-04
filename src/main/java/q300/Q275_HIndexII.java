package q300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/h-index-ii/
 *
 * Given an array of citations sorted in ascending order (each citation is a non-negative integer) of a researcher,
 * write a function to compute the researcher's h-index.
 *
 * According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N papers have at
 * least h citations each, and the other N − h papers have no more than h citations each."
 *
 * Example:
 *
 * Input: citations = [0,1,3,5,6]
 * Output: 3
 * Explanation: [0,1,3,5,6] means the researcher has 5 papers in total and each of them had
 * received 0, 1, 3, 5, 6 citations respectively.
 * Since the researcher has 3 papers with at least 3 citations each and the remaining
 * two with no more than 3 citations each, her h-index is 3.
 *
 * Note:
 *
 * If there are several possible values for h, the maximum one is taken as the h-index.
 *
 * Follow up:
 *
 * This is a follow up problem to H-Index, where citations is now guaranteed to be sorted in ascending order.
 * Could you solve it in logarithmic time complexity?
 *
 * 题解: 和上一题相比唯一的区别就是参数 citations 变成有序的了, 所以题目要求的时间复杂度是 O(logN)
 */
@RunWith(LeetCodeRunner.class)
public class Q275_HIndexII {

    @Answer
    public int hIndex(int[] citations) {
        final int n = citations.length;
        if (n == 0 || citations[n - 1] == 0) {
            return 0;
        }
        int start = 0, end = n - 1;
        while (start < end) {
            int middle = (start + end) / 2;
            if (n - middle <= citations[middle]) {
                end = middle;
            } else {
                start = middle + 1;
            }
        }
        return n - start;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{0, 1, 3, 5, 6}).expect(3);

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[0]).expect(0);

    @TestData
    public DataExpectation border2 = DataExpectation.create(new int[]{0}).expect(0);

    @TestData
    public DataExpectation border3 = DataExpectation.create(new int[]{0, 0}).expect(0);

}
