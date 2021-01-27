package q1650;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1604. Alert Using Same Key-Card Three or More Times in a One Hour Period
 * https://leetcode.com/problems/alert-using-same-key-card-three-or-more-times-in-a-one-hour-period/
 *
 * LeetCode company workers use key-cards to unlock office doors. Each time a worker uses their key-card, the security
 * system saves the worker's name and the time when it was used. The system emits an alert if any worker uses the
 * key-card three or more times in a one-hour period.
 *
 * You are given a list of strings keyName and keyTime where [keyName[i], keyTime[i]] corresponds to a person's name and
 * the time when their key-card was used in a single day.
 *
 * Access times are given in the 24-hour time format "HH:MM", such as "23:51" and "09:49".
 *
 * Return a list of unique worker names who received an alert for frequent keycard use. Sort the names in ascending
 * order alphabetically.
 *
 * Notice that "10:00" - "11:00" is considered to be within a one-hour period, while "22:51" - "23:52" is not considered
 * to be within a one-hour period.
 *
 * Example 1:
 *
 * Input: keyName = ["daniel","daniel","daniel","luis","luis","luis","luis"], keyTime =
 * ["10:00","10:40","11:00","09:00","11:00","13:00","15:00"]
 * Output: ["daniel"]
 * Explanation: "daniel" used the keycard 3 times in a one-hour period ("10:00","10:40", "11:00").
 *
 * Example 2:
 *
 * Input: keyName = ["alice","alice","alice","bob","bob","bob","bob"], keyTime = ["12:01","12:00","18:00","21:00",
 * "21:20","21:30","23:00"]
 * Output: ["bob"]
 * Explanation: "bob" used the keycard 3 times in a one-hour period ("21:00","21:20", "21:30").
 *
 * Example 3:
 *
 * Input: keyName = ["john","john","john"], keyTime = ["23:58","23:59","00:01"]
 * Output: []
 *
 * Example 4:
 *
 * Input: keyName = ["leslie","leslie","leslie","clare","clare","clare","clare"], keyTime =
 * ["13:00","13:20","14:00","18:00","18:51","19:30","19:49"]
 * Output: ["clare","leslie"]
 *
 * Constraints:
 *
 * 1 <= keyName.length, keyTime.length <= 10^5
 * keyName.length == keyTime.length
 * keyTime[i] is in the format "HH:MM".
 * [keyName[i], keyTime[i]] is unique.
 * 1 <= keyName[i].length <= 10
 * keyName[i] contains only lowercase English letters.
 *
 * 题解: 这题给出的keyTime 都是一天内的刷卡时间, 没有保证是按照时间顺序排列, leetcode-cn 上的题目描述是有问题的.
 */
@RunWith(LeetCodeRunner.class)
public class Q1604_AlertUsingSameKeyCardThreeOrMoreTimesInAOneHourPeriod {

    @Answer
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        final int n = keyName.length;
        Map<String, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            List<Integer> times = map.computeIfAbsent(keyName[i], k -> new ArrayList<>());
            int curr = ((keyTime[i].charAt(0) - '0') * 10 + (keyTime[i].charAt(1) - '0')) * 60
                    + (keyTime[i].charAt(3) - '0') * 10 + (keyTime[i].charAt(4) - '0');
            times.add(curr);
        }

        List<String> res = new ArrayList<>();
        for (var entry : map.entrySet()) {
            List<Integer> times = entry.getValue();
            Collections.sort(times);
            for (int i = 2; i < times.size(); i++) {
                if (times.get(i - 2) + 60 >= times.get(i)) {
                    res.add(entry.getKey());
                    break;
                }
            }
        }
        Collections.sort(res);
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(
            new String[]{"daniel", "daniel", "daniel", "luis", "luis", "luis", "luis"},
            new String[]{"10:00", "10:40", "11:00", "09:00", "11:00", "13:00", "15:00"}
    ).expect(List.of("daniel"));

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(
            new String[]{"alice", "alice", "alice", "bob", "bob", "bob", "bob"},
            new String[]{"12:01", "12:00", "18:00", "21:00", "21:20", "21:30", "23:00"}
    ).expect(List.of("bob"));

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(
            new String[]{"john", "john", "john"},
            new String[]{"23:58", "23:59", "00:01"}
    ).expect(List.of());

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(
            new String[]{"leslie", "leslie", "leslie", "clare", "clare", "clare", "clare"},
            new String[]{"13:00", "13:20", "14:00", "18:00", "18:51", "19:30", "19:49"}
    ).expect(List.of("clare", "leslie"));

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(
            new String[]{"a", "a", "a", "a", "a", "a", "b", "b", "b", "b", "b"},
            new String[]{"23:27", "03:14", "12:57", "13:35", "13:18", "21:58", "22:39", "10:49", "19:37", "14:14",
                    "10:41"}
    ).expect(List.of("a"));

}
