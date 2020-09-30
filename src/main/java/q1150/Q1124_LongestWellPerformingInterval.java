package q1150;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1124. Longest Well-Performing Interval
 * https://leetcode.com/problems/longest-well-performing-interval/
 *
 * We are given hours, a list of the number of hours worked per day for a given employee.
 *
 * A day is considered to be a tiring day if and only if the number of hours worked is (strictly) greater than 8.
 *
 * A well-performing interval is an interval of days for which the number of tiring days is strictly larger than the
 * number of non-tiring days.
 *
 * Return the length of the longest well-performing interval.
 *
 * Example 1:
 *
 * Input: hours = [9,9,6,0,6,6,9]
 * Output: 3
 * Explanation: The longest well-performing interval is [9,9,6].
 *
 * Constraints:
 *
 * 1 <= hours.length <= 10000
 * 0 <= hours[i] <= 16
 */
@RunWith(LeetCodeRunner.class)
public class Q1124_LongestWellPerformingInterval {

    @Answer
    public int longestWPI(int[] hours) {
        final int n = hours.length;
        // lowers的元素{下标, tiring}, tiring 递减
        List<int[]> lowers = new ArrayList<>();
        lowers.add(new int[]{-1, 0});
        // tiring 表示 [>8 小时的天数] - [<=8 小时的天数], 是1 个相对值
        int res = 0, tiring = 0, j = 0;
        for (int i = 0; i < n; i++) {
            tiring += hours[i] > 8 ? 1 : -1;
            if (tiring < lowers.get(lowers.size() - 1)[1]) {
                lowers.add(new int[]{i, tiring});
            }
            if (0 < j && lowers.get(j - 1)[1] < tiring) {
                j--;
            } else if (j < lowers.size() - 1 && lowers.get(j)[1] >= tiring) {
                j++;
            }
            if (lowers.get(j)[1] < tiring) {
                res = Math.max(res, i - lowers.get(j)[0]);
            }
        }
        return res;
    }

    /**
     * LeetCode 上比较快的解法. 与上面思路类似, 不过使用 map 而不是List 来保存 tiring.
     */
    @Answer
    public int longestWPI2(int[] hours) {
        Map<Integer, Integer> map = new HashMap<>();
        int res = 0, tiring = 0;
        for (int i = 0; i < hours.length; i++) {
            tiring += hours[i] > 8 ? 1 : -1;
            if (tiring > 0) {
                res = i + 1;
            } else {
                if (map.containsKey(tiring - 1)) {
                    res = Math.max(res, i - map.get(tiring - 1));
                }
                map.putIfAbsent(tiring, i);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{9, 9, 6, 0, 6, 6, 9}).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{6, 9, 9}).expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{6, 6, 9}).expect(1);

}
