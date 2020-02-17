package q450;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/binary-watch/
 *
 * A binary watch has 4 LEDs on the top which represent the hours (0-11), and the 6 LEDs on the bottom represent the
 * minutes (0-59).
 *
 * Each LED represents a zero or one, with the least significant bit on the right.
 *
 * For example, the above binary watch reads "3:25".
 *
 * Given a non-negative integer n which represents the number of LEDs that are currently on, return all possible
 * times the watch could represent.
 *
 * Example:
 *
 * (图 Q401_PIC1.jfif)
 *
 * Input: n = 1
 * Return: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
 *
 * Note:
 *
 * The order of output does not matter.
 * The hour must not contain a leading zero, for example "01:00" is not valid, it should be "1:00".
 * The minute must be consist of two digits and may contain a leading zero, for example "10:2" is not valid, it
 * should be "10:02".
 */
@RunWith(LeetCodeRunner.class)
public class Q401_BinaryWatch {

    @Answer
    public List<String> readBinaryWatch(int num) {
        List<String> res = new ArrayList<>();
        dfs(res, 0, 0, 0, num);
        return res;
    }

    private final int[] nums = new int[]{1, 2, 4, 8, 1, 2, 4, 8, 16, 32};

    private void dfs(List<String> res, int hour, int minute, int index, int remains) {
        if (hour > 11 || minute > 59) {
            return;
        }
        if (remains == 0) {
            res.add(hour + ":" + (minute < 10 ? "0" : "") + minute);
            return;
        }
        if (index + remains > nums.length) {
            return;
        }
        dfs(res, hour, minute, index + 1, remains);
        if (index < 4) {
            dfs(res, hour + nums[index], minute, index + 1, remains - 1);
        } else {
            dfs(res, hour, minute + nums[index], index + 1, remains - 1);
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(1)
            .expect(Arrays.asList("1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"))
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(2)
            .expect(Arrays
                    .asList("0:03", "0:05", "0:06", "0:09", "0:10", "0:12", "0:17", "0:18", "0:20", "0:24", "0:33",
                            "0:34", "0:36", "0:40", "0:48", "1:01", "1:02", "1:04", "1:08", "1:16", "1:32", "2:01",
                            "2:02", "2:04", "2:08", "2:16", "2:32", "3:00", "4:01", "4:02", "4:04", "4:08", "4:16",
                            "4:32", "5:00", "6:00", "8:01", "8:02", "8:04", "8:08", "8:16", "8:32", "9:00", "10:00"))
            .unorderResult("")
            .build();

}
