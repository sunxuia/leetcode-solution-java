package q450;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-right-interval/
 *
 * Given a set of intervals, for each of the interval i, check if there exists an interval j whose start point is
 * bigger than or equal to the end point of the interval i, which can be called that j is on the "right" of i.
 *
 * For any interval i, you need to store the minimum interval j's index, which means that the interval j has the
 * minimum start point to build the "right" relationship for interval i. If the interval j doesn't exist, store -1
 * for the interval i. Finally, you need output the stored value of each interval as an array.
 *
 * Note:
 *
 * 1. You may assume the interval's end point is always bigger than its start point.
 * 2. You may assume none of these intervals have the same start point.
 *
 *
 *
 * Example 1:
 *
 * Input: [ [1,2] ]
 *
 * Output: [-1]
 *
 * Explanation: There is only one interval in the collection, so it outputs -1.
 *
 *
 *
 * Example 2:
 *
 * Input: [ [3,4], [2,3], [1,2] ]
 *
 * Output: [-1, 0, 1]
 *
 * Explanation: There is no satisfied "right" interval for [3,4].
 * For [2,3], the interval [3,4] has minimum-"right" start point;
 * For [1,2], the interval [2,3] has minimum-"right" start point.
 *
 *
 *
 * Example 3:
 *
 * Input: [ [1,4], [2,3], [3,4] ]
 *
 * Output: [-1, 2, -1]
 *
 * Explanation: There is no satisfied "right" interval for [1,4] and [3,4].
 * For [2,3], the interval [3,4] has minimum-"right" start point.
 *
 * NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method
 * signature.
 */
@RunWith(LeetCodeRunner.class)
public class Q436_FindRightInterval {

    @Answer
    public int[] findRightInterval(int[][] intervals) {
        final int len = intervals.length;
        Integer[] heads = new Integer[len];
        for (int i = 0; i < len; i++) {
            heads[i] = i;
        }
        Arrays.sort(heads, Comparator.comparingInt(a -> intervals[a][0]));

        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            int target = intervals[i][1];
            int start = 0, end = len;
            while (start < end) {
                int middle = (start + end) / 2;
                if (intervals[heads[middle]][0] < target) {
                    start = middle + 1;
                } else {
                    end = middle;
                }
            }
            res[i] = end == len ? -1 : heads[end];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{1, 2}}).expect(new int[]{-1});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{3, 4}, {2, 3}, {1, 2}})
            .expect(new int[]{-1, 0, 1});

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[][]{{1, 4}, {2, 3}, {3, 4}})
            .expect(new int[]{-1, 2, -1});

}
