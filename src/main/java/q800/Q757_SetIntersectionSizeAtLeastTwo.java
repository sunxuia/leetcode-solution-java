package q800;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/set-intersection-size-at-least-two/
 *
 * An integer interval [a, b] (for integers a < b) is a set of all consecutive integers from a to b, including a and b.
 *
 * Find the minimum size of a set S such that for every integer interval A in intervals, the intersection of S with A
 * has size at least 2.
 *
 * Example 1:
 *
 * Input: intervals = [[1, 3], [1, 4], [2, 5], [3, 5]]
 * Output: 3
 * Explanation:
 * Consider the set S = {2, 3, 4}.  For each interval, there are at least 2 elements from S in the interval.
 * Also, there isn't a smaller size set that fulfills the above condition.
 * Thus, we output the size of this set, which is 3.
 *
 * Example 2:
 *
 * Input: intervals = [[1, 2], [2, 3], [2, 4], [4, 5]]
 * Output: 5
 * Explanation:
 * An example of a minimum sized set is {1, 2, 3, 4, 5}.
 *
 * Note:
 *
 * 1. intervals will have length in range [1, 3000].
 * 2. intervals[i] will have length 2, representing some integer interval.
 * 3. intervals[i][j] will be an integer in [0, 10^8].
 */
@RunWith(LeetCodeRunner.class)
public class Q757_SetIntersectionSizeAtLeastTwo {

    /**
     * 参考文档 https://www.cnblogs.com/grandyang/p/8503476.html
     * 这题可以使用贪婪算法, 结果S 不一定要连续, 只要满足题意即可.
     * 首先将区间排序, 然后针对集合S 和当前遍历的区间的重叠情况分为3 种处理:
     * S 与区间有 >= 2 个重叠点: 当前区间不做处理.
     * S 与区间有1 个重叠点: 这个重叠点一定是当前区间的起始点, 那么可以取区间的结束点放入S
     * S 与区间没有重叠点: 将当前区间内最大的2 个数字加入S. (取大的数字是为了和后面的区间重合.)
     */
    @Answer
    public int intersectionSizeTwo(int[][] intervals) {
        // 按照起点排序, 相同起点长的在前面(可以覆盖后面的短的)
        Arrays.sort(intervals, (a, b) -> a[1] == b[1] ? b[0] - a[0] : a[1] - b[1]);

        // prev2, prev1 分别表示S 中的最后2 个数字
        int res = 0, prev2 = -1, prev1 = -1;
        for (int[] interval : intervals) {
            int start = interval[0], end = interval[1];
            if (start <= prev2) {
                // S 与当前区间已经有2 个重叠点了.
                continue;
            }
            if (start > prev1) {
                // S 与当前区间没有重叠点.
                res++;
                prev1 = end - 1;
            }
            res++;
            prev2 = prev1;
            prev1 = end;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{1, 3}, {1, 4}, {2, 5}, {3, 5}}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{1, 2}, {2, 3}, {2, 4}, {4, 5}}).expect(5);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{{1, 15}, {0, 8}, {13, 14}}).expect(4);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[][]{
            {2, 10}, {3, 7}, {3, 15}, {4, 11}, {6, 12}, {6, 16}, {7, 8}, {7, 11}, {7, 15}, {11, 12}
    }).expect(5);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new int[][]{{0, 2}, {2, 3}, {1, 3}}).expect(3);

    @TestData
    public DataExpectation normal4 = DataExpectation
            .create(new int[][]{{6, 21}, {7, 15}, {9, 24}, {1, 12}, {5, 23}})
            .expect(2);

}
