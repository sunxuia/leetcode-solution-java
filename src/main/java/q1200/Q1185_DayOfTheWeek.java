package q1200;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1185. Day of the Week
 * https://leetcode.com/problems/day-of-the-week/
 *
 * Given a date, return the corresponding day of the week for that date.
 *
 * The input is given as three integers representing the day, month and year respectively.
 *
 * Return the answer as one of the following values {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
 * "Saturday"}.
 *
 * Example 1:
 *
 * Input: day = 31, month = 8, year = 2019
 * Output: "Saturday"
 *
 * Example 2:
 *
 * Input: day = 18, month = 7, year = 1999
 * Output: "Sunday"
 *
 * Example 3:
 *
 * Input: day = 15, month = 8, year = 1993
 * Output: "Sunday"
 *
 * Constraints:
 *
 * The given dates are valid dates between the years 1971 and 2100.
 */
@RunWith(LeetCodeRunner.class)
public class Q1185_DayOfTheWeek {

    /**
     * 这种做法比较慢
     */
    @Answer
    public String dayOfTheWeek(int day, int month, int year) {
        LocalDate date = LocalDate.of(year, month, day);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.US);
    }

    @Answer
    public String dayOfTheWeek2(int day, int month, int year) {
        // 1971-1-1 是星期五, 前一天是星期四
        int weekDay = 4;
        for (int i = 1971; i < year; i++) {
            weekDay += i % 4 == 0 && i % 100 != 0 || i % 400 == 0 ? 366 : 365;
        }
        weekDay += (year % 4 == 0 && year % 100 != 0 || year % 400 == 0
                ? DAYS_BEFORE_MONTH_LEAP : DAYS_BEFORE_MONTH)[month - 1];
        weekDay += day;
        return WEEK_DAYS[weekDay % 7];
    }

    private static final int[] DAYS_BEFORE_MONTH = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};

    private static final int[] DAYS_BEFORE_MONTH_LEAP = {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};

    private static final String[] WEEK_DAYS =
            {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    /**
     * LeetCode 上比较快的方法, O(1)的时间复杂度.
     */
    @Answer
    public String dayOfTheWeek3(int day, int month, int year) {
        if (month <= 2) {
            year--;
        }
        // 年份 + 闰年数 + 月份周数的日期偏移(相对于1971年) + 当月天数
        return WEEK_DAYS[(year + (year / 4 - year / 100 + year / 400)
                + MONTH_DAYS_MOD7[month - 1] + day) % 7];
    }

    private static final int[] MONTH_DAYS_MOD7 = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(31, 8, 2019).expect("Saturday");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(18, 7, 1999).expect("Sunday");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(15, 8, 1993).expect("Sunday");

    @TestData
    public DataExpectation border0 = DataExpectation.createWith(1, 1, 1971).expect("Friday");

    @TestData
    public DataExpectation border1 = DataExpectation.createWith(31, 12, 2100).expect("Friday");

}
