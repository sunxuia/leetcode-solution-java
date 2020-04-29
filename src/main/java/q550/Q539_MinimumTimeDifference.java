package q550;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/minimum-time-difference/
 *
 * Given a list of 24-hour clock time points in "Hour:Minutes" format, find the minimum minutes difference between
 * any two time points in the list.
 *
 * Example 1:
 *
 * Input: ["23:59","00:00"]
 * Output: 1
 *
 * Note:
 *
 * The number of time points in the given list is at least 2 and won't exceed 20000.
 * The input time is legal and ranges from 00:00 to 23:59.
 */
@RunWith(LeetCodeRunner.class)
public class Q539_MinimumTimeDifference {

    @Answer
    public int findMinDifference(List<String> timePoints) {
        final int length = timePoints.size();
        int[] times = new int[length];
        for (int i = 0; i < length; i++) {
            String[] strs = timePoints.get(i).split(":");
            times[i] = Integer.parseInt(strs[0]) * 60 + Integer.parseInt(strs[1]);
        }
        Arrays.sort(times);
        int res = times[0] + 24 * 60 - times[length - 1];
        for (int i = 1; i < length; i++) {
            res = Math.min(res, times[i] - times[i - 1]);
        }
        return res;
    }

    // LeetCode 上如下这种遍历所有分钟的桶排序方式是最快的
    @Answer
    public int findMinDifference2(List<String> timePoints) {
        BitSet mark = new BitSet();
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (String time : timePoints) {
            String[] strs = time.split(":");
            int minutes = Integer.parseInt(strs[0]) * 60 + Integer.parseInt(strs[1]);
            if (mark.get(minutes)) {
                return 0;
            }
            mark.set(minutes);
            min = Math.min(min, minutes);
            max = Math.max(max, minutes);
        }

        int res = min + 24 * 60 - max, prev = min;
        for (int i = min + 1; i <= max; i++) {
            if (mark.get(i)) {
                res = Math.min(res, i - prev);
                prev = i;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation exmaple = DataExpectation.create(Arrays.asList("23:59", "00:00")).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(Arrays.asList("01:01", "02:01", "03:00")).expect(59);

}
