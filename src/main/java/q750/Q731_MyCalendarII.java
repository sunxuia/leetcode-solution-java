package q750;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.ClassDataExpectation;
import util.runner.data.ClassDataExpectationBuilder;

/**
 * https://leetcode.com/problems/my-calendar-ii/
 *
 * Implement a MyCalendarTwo class to store your events. A new event can be added if adding the event will not cause
 * a triple booking.
 *
 * Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open
 * interval [start, end), the range of real numbers x such that start <= x < end.
 *
 * A triple booking happens when three events have some non-empty intersection (ie., there is some time that is
 * common to all 3 events.)
 *
 * For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully
 * without causing a triple booking. Otherwise, return false and do not add the event to the calendar.
 * Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
 *
 * Example 1:
 *
 * MyCalendar();
 * MyCalendar.book(10, 20); // returns true
 * MyCalendar.book(50, 60); // returns true
 * MyCalendar.book(10, 40); // returns true
 * MyCalendar.book(5, 15); // returns false
 * MyCalendar.book(5, 10); // returns true
 * MyCalendar.book(25, 55); // returns true
 * Explanation:
 * The first two events can be booked.  The third event can be double booked.
 * The fourth event (5, 15) can't be booked, because it would result in a triple booking.
 * The fifth event (5, 10) can be booked, as it does not use time 10 which is already double booked.
 * The sixth event (25, 55) can be booked, as the time in [25, 40) will be double booked with the third event;
 * the time [40, 50) will be single booked, and the time [50, 55) will be double booked with the second event.
 *
 *
 *
 * Note:
 *
 * The number of calls to MyCalendar.book per test case will be at most 1000.
 * In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].
 */
@RunWith(LeetCodeRunner.class)
public class Q731_MyCalendarII {

    @Answer
    public static class MyCalendarTwo {

        // 表示预定的 [key, value) 区域, 如果有重叠则value 为负数
        TreeMap<Integer, Integer> map = new TreeMap<>();

        public MyCalendarTwo() {

        }

        public boolean book(int start, int end) {
            Entry<Integer, Integer> entry = map.lowerEntry(start);
            if (entry == null || Math.abs(entry.getValue()) <= start) {
                entry = map.ceilingEntry(start);
            }
            while (entry != null && entry.getKey() < end) {
                if (entry.getValue() < 0) {
                    return false;
                }
                entry = map.ceilingEntry(entry.getValue());
            }

            // 前面
            entry = map.lowerEntry(start);
            if (entry != null && Math.abs(entry.getValue()) > start) {
                map.put(entry.getKey(), start);
                map.put(start, entry.getValue());
            }

            // 中间
            entry = map.ceilingEntry(start);
            while (entry != null && Math.abs(entry.getValue()) <= end) {
                if (start < entry.getKey()) {
                    map.put(start, entry.getKey());
                }
                map.put(entry.getKey(), -entry.getValue());
                start = entry.getValue();
                entry = map.ceilingEntry(entry.getValue());
            }

            // 结尾
            if (entry != null && entry.getKey() < end) {
                map.put(entry.getKey(), -end);
                map.put(end, entry.getValue());
                end = entry.getKey();
            }
            if (start < end) {
                map.put(start, end);
            }

            return true;
        }
    }

    // 网上的另一种解法, map 中开始位置+1和结束位置-1, 然后遍历map, 记录总的次数, 看有没有超过2
    // 这种做法比上面的要慢.
    @Answer
    public static class MyCalendarTwoII {

        private TreeMap<Integer, Integer> map = new TreeMap<>();

        public boolean book(int start, int end) {
            map.put(start, map.getOrDefault(start, 0) + 1);
            map.put(end, map.getOrDefault(end, 0) - 1);

            int count = 0;
            for (int value : map.values()) {
                count += value;
                if (count == 3) {
                    map.put(start, map.get(start) - 1);
                    map.put(end, map.get(end) + 1);
                    return false;
                }
            }
            return true;
        }

    }

    @TestData
    public Map<String, ClassDataExpectation> testDatas = ClassDataExpectationBuilder
            .readFromFile(MyCalendarTwo.class, "Q731_TestData");

}
