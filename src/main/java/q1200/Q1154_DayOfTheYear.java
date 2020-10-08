package q1200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1154. Day of the Year
 * https://leetcode.com/problems/day-of-the-year/
 *
 * Given a string date representing a Gregorian calendar date formatted as YYYY-MM-DD, return the day number of the
 * year.
 *
 * Example 1:
 *
 * Input: date = "2019-01-09"
 * Output: 9
 * Explanation: Given date is the 9th day of the year in 2019.
 *
 * Example 2:
 *
 * Input: date = "2019-02-10"
 * Output: 41
 *
 * Example 3:
 *
 * Input: date = "2003-03-01"
 * Output: 60
 *
 * Example 4:
 *
 * Input: date = "2004-03-01"
 * Output: 61
 *
 * Constraints:
 *
 * date.length == 10
 * date[4] == date[7] == '-', and all other date[i]'s are digits
 * date represents a calendar date between Jan 1st, 1900 and Dec 31, 2019.
 */
@RunWith(LeetCodeRunner.class)
public class Q1154_DayOfTheYear {

    @Answer
    public int dayOfYear(String date) {
        String[] strs = date.split("-");
        int year = Integer.parseInt(strs[0]);
        int month = Integer.parseInt(strs[1]);
        int day = Integer.parseInt(strs[2]);
        int[] monthDays = year % 4 == 0 && year % 100 != 0 || year % 400 == 0
                ? DAYS_BEFORE_MONTH_LEAP : DAYS_BEFORE_MONTH;
        return monthDays[month - 1] + day;
    }

    /**
     * 每月前的天数 (非闰年)
     */
    private static final int[] DAYS_BEFORE_MONTH = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};

    /**
     * 闰年每月前的天数
     */
    private static final int[] DAYS_BEFORE_MONTH_LEAP = {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};

    @TestData
    public DataExpectation example1 = DataExpectation.create("2019-01-09").expect(9);

    @TestData
    public DataExpectation example2 = DataExpectation.create("2019-02-10").expect(41);

    @TestData
    public DataExpectation example3 = DataExpectation.create("2003-03-01").expect(60);

    @TestData
    public DataExpectation example4 = DataExpectation.create("2004-03-01").expect(61);

}
