package q1400;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1360. Number of Days Between Two Dates
 * https://leetcode.com/problems/number-of-days-between-two-dates/
 *
 * Write a program to count the number of days between two dates.
 *
 * The two dates are given as strings, their format is YYYY-MM-DD as shown in the examples.
 *
 * Example 1:
 * Input: date1 = "2019-06-29", date2 = "2019-06-30"
 * Output: 1
 * Example 2:
 * Input: date1 = "2020-01-15", date2 = "2019-12-31"
 * Output: 15
 *
 * Constraints:
 *
 * The given dates are valid dates between the years 1971 and 2100.
 */
@RunWith(LeetCodeRunner.class)
public class Q1360_NumberOfDaysBetweenTwoDates {

    @Answer
    public int daysBetweenDates(String date1, String date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date a = dateFormat.parse(date1);
            Date b = dateFormat.parse(date2);
            long diff = Math.abs(a.getTime() - b.getTime());
            return (int) (diff / (24 * 60 * 60 * 1000));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("2019-06-29", "2019-06-30").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("2020-01-15", "2019-12-31").expect(15);

}
