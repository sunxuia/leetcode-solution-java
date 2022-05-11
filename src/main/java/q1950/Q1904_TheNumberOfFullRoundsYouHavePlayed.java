package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1904. The Number of Full Rounds You Have Played
 * https://leetcode.com/problems/the-number-of-full-rounds-you-have-played/
 *
 * You are participating in an online chess tournament. There is a chess round that starts every 15 minutes. The first
 * round of the day starts at 00:00, and after every 15 minutes, a new round starts.
 *
 * For example, the second round starts at 00:15, the fourth round starts at 00:45, and the seventh round starts at
 * 01:30.
 *
 * You are given two strings loginTime and logoutTime where:
 *
 * loginTime is the time you will login to the game, and
 * logoutTime is the time you will logout from the game.
 *
 * If logoutTime is earlier than loginTime, this means you have played from loginTime to midnight and from midnight to
 * logoutTime.
 *
 * Return the number of full chess rounds you have played in the tournament.
 *
 * Note: All the given times follow the 24-hour clock. That means the first round of the day starts at 00:00 and the
 * last round of the day starts at 23:45.
 *
 * Example 1:
 *
 * Input: loginTime = "09:31", logoutTime = "10:14"
 * Output: 1
 * Explanation: You played one full round from 09:45 to 10:00.
 * You did not play the full round from 09:30 to 09:45 because you logged in at 09:31 after it began.
 * You did not play the full round from 10:00 to 10:15 because you logged out at 10:14 before it ended.
 *
 * Example 2:
 *
 * Input: loginTime = "21:30", logoutTime = "03:00"
 * Output: 22
 * Explanation: You played 10 full rounds from 21:30 to 00:00 and 12 full rounds from 00:00 to 03:00.
 * 10 + 12 = 22.
 *
 * Constraints:
 *
 * loginTime and logoutTime are in the format hh:mm.
 * 00 <= hh <= 23
 * 00 <= mm <= 59
 * loginTime and logoutTime are not equal.
 */
@RunWith(LeetCodeRunner.class)
public class Q1904_TheNumberOfFullRoundsYouHavePlayed {

    @Answer
    public int numberOfRounds(String loginTime, String logoutTime) {
        int loginHour = (loginTime.charAt(0) - '0') * 10 + loginTime.charAt(1) - '0';
        int loginMin = (loginTime.charAt(3) - '0') * 10 + loginTime.charAt(4) - '0';
        int loginTs = loginHour * 60 + loginMin;

        int logoutHour = (logoutTime.charAt(0) - '0') * 10 + logoutTime.charAt(1) - '0';
        int logoutMin = (logoutTime.charAt(3) - '0') * 10 + logoutTime.charAt(4) - '0';
        int logoutTs = logoutHour * 60 + logoutMin;
        if (logoutTs < loginTs) {
            logoutTs += 24 * 60;
        }

        int offset = logoutTs / 15 - loginTs / 15 - (loginTs % 15 == 0 ? 0 : 1);
        return Math.max(0, offset);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("09:31", "10:14").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("21:30", "03:00").expect(22);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("00:47", "00:57").expect(0);

}
