package q1550;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1507. Reformat Date
 * https://leetcode.com/problems/reformat-date/
 *
 * Given a date string in the form Day Month Year, where:
 *
 * Day is in the set {"1st", "2nd", "3rd", "4th", ..., "30th", "31st"}.
 * Month is in the set {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}.
 * Year is in the range [1900, 2100].
 *
 * Convert the date string to the format YYYY-MM-DD, where:
 *
 * YYYY denotes the 4 digit year.
 * MM denotes the 2 digit month.
 * DD denotes the 2 digit day.
 *
 * Example 1:
 *
 * Input: date = "20th Oct 2052"
 * Output: "2052-10-20"
 *
 * Example 2:
 *
 * Input: date = "6th Jun 1933"
 * Output: "1933-06-06"
 *
 * Example 3:
 *
 * Input: date = "26th May 1960"
 * Output: "1960-05-26"
 *
 * Constraints:
 *
 * The given dates are guaranteed to be valid, so no error handling is necessary.
 */
@RunWith(LeetCodeRunner.class)
public class Q1507_ReformatDate {

    @Answer
    public String reformatDate(String date) {
        Matcher m = DATE_FORMAT.matcher(date);
        m.find();
        String day = m.group(1);
        if (day.length() == 1) {
            day = "0" + day;
        }
        return m.group(3) + "-" + MONTHS.get(m.group(2)) + "-" + day;
    }

    private static Pattern DATE_FORMAT = Pattern.compile("^(\\d{1,2}).{2} (.{3}) (\\d{4})$");

    private static Map<String, String> MONTHS = new HashMap<>();

    static {
        MONTHS.put("Jan", "01");
        MONTHS.put("Feb", "02");
        MONTHS.put("Mar", "03");
        MONTHS.put("Apr", "04");
        MONTHS.put("May", "05");
        MONTHS.put("Jun", "06");
        MONTHS.put("Jul", "07");
        MONTHS.put("Aug", "08");
        MONTHS.put("Sep", "09");
        MONTHS.put("Oct", "10");
        MONTHS.put("Nov", "11");
        MONTHS.put("Dec", "12");
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("20th Oct 2052").expect("2052-10-20");

    @TestData
    public DataExpectation example2 = DataExpectation.create("6th Jun 1933").expect("1933-06-06");

    @TestData
    public DataExpectation example3 = DataExpectation.create("26th May 1960").expect("1960-05-26");

}
