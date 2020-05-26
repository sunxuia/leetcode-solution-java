package q750;

import java.util.TreeMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/my-calendar-iii/
 *
 * Implement a MyCalendarThree class to store your events. A new event can always be added.
 *
 * Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open
 * interval [start, end), the range of real numbers x such that start <= x < end.
 *
 * A K-booking happens when K events have some non-empty intersection (ie., there is some time that is common to all
 * K events.)
 *
 * For each call to the method MyCalendar.book, return an integer K representing the largest integer such that there
 * exists a K-booking in the calendar.
 * Your class will be called like this: MyCalendarThree cal = new MyCalendarThree(); MyCalendarThree.book(start, end)
 *
 * Example 1:
 *
 * MyCalendarThree();
 * MyCalendarThree.book(10, 20); // returns 1
 * MyCalendarThree.book(50, 60); // returns 1
 * MyCalendarThree.book(10, 40); // returns 2
 * MyCalendarThree.book(5, 15); // returns 3
 * MyCalendarThree.book(5, 10); // returns 3
 * MyCalendarThree.book(25, 55); // returns 3
 * Explanation:
 * The first two events can be booked and are disjoint, so the maximum K-booking is a 1-booking.
 * The third event [10, 40) intersects the first event, and the maximum K-booking is a 2-booking.
 * The remaining events cause the maximum K-booking to be only a 3-booking.
 * Note that the last event locally causes a 2-booking, but the answer is still 3 because
 * eg. [10, 20), [10, 40), and [5, 15) are still triple booked.
 *
 *
 *
 * Note:
 *
 * The number of calls to MyCalendarThree.book per test case will be at most 400.
 * In calls to MyCalendarThree.book(start, end), start and end are integers in the range [0, 10^9].
 */
public class Q732_MyCalendarIII {

    /**
     * 和上一题差不多的解法, 需要注意的是book 的返回值是最大重叠的数量.
     */
    public static class MyCalendarThree {

        TreeMap<Integer, Integer> map = new TreeMap<>();

        public MyCalendarThree() {

        }

        public int book(int start, int end) {
            map.put(start, map.getOrDefault(start, 0) + 1);
            map.put(end, map.getOrDefault(end, 0) - 1);
            int count = 0, res = 0;
            for (int value : map.values()) {
                count += value;
                res = Math.max(res, count);
            }
            return res;
        }
    }

    @Test
    public void testMethod() {
        MyCalendarThree mct = new MyCalendarThree();
        Assert.assertEquals(1, mct.book(10, 20));
        Assert.assertEquals(1, mct.book(50, 60));
        Assert.assertEquals(2, mct.book(10, 40));
        Assert.assertEquals(3, mct.book(5, 15));
        Assert.assertEquals(3, mct.book(5, 10));
        Assert.assertEquals(3, mct.book(25, 55));
    }

}
