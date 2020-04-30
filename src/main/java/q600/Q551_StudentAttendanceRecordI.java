package q600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/student-attendance-record-i/
 *
 * You are given a string representing an attendance record for a student. The record only contains the following
 * three characters:
 *
 * 'A' : Absent.
 * 'L' : Late.
 * 'P' : Present.
 *
 * A student could be rewarded if his attendance record doesn't contain more than one 'A' (absent) or more than two
 * continuous 'L' (late).
 *
 * You need to return whether the student could be rewarded according to his attendance record.
 *
 * Example 1:
 *
 * Input: "PPALLP"
 * Output: True
 *
 * Example 2:
 *
 * Input: "PPALLL"
 * Output: False
 */
@RunWith(LeetCodeRunner.class)
public class Q551_StudentAttendanceRecordI {

    @Answer
    public boolean checkRecord(String s) {
        int aCount = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'A') {
                if (++aCount == 2) {
                    return false;
                }
            } else if (c == 'L') {
                if (i >= 2 && s.charAt(i - 2) == c && s.charAt(i - 1) == c) {
                    return false;
                }
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("PPALLP").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create("PPALLL").expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("LALL").expect(true);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("LLL").expect(false);

}
