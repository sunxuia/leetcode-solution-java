package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1678. Goal Parser Interpretation
 * https://leetcode.com/problems/goal-parser-interpretation/
 *
 * You own a Goal Parser that can interpret a string command. The command consists of an alphabet of "G", "()" and/or
 * "(al)" in some order. The Goal Parser will interpret "G" as the string "G", "()" as the string "o", and "(al)" as the
 * string "al". The interpreted strings are then concatenated in the original order.
 *
 * Given the string command, return the Goal Parser's interpretation of command.
 *
 * Example 1:
 *
 * Input: command = "G()(al)"
 * Output: "Goal"
 * Explanation: The Goal Parser interprets the command as follows:
 * G -> G
 * () -> o
 * (al) -> al
 * The final concatenated result is "Goal".
 *
 * Example 2:
 *
 * Input: command = "G()()()()(al)"
 * Output: "Gooooal"
 *
 * Example 3:
 *
 * Input: command = "(al)G(al)()()G"
 * Output: "alGalooG"
 *
 * Constraints:
 *
 * 1 <= command.length <= 100
 * command consists of "G", "()", and/or "(al)" in some order.
 */
@RunWith(LeetCodeRunner.class)
public class Q1678_GoalParserInterpretation {

    @Answer
    public String interpret(String command) {
        StringBuilder sb = new StringBuilder();
        boolean hasA = false;
        for (int i = 0; i < command.length(); i++) {
            switch (command.charAt(i)) {
                case 'G':
                    sb.append('G');
                    break;
                case 'a':
                    hasA = true;
                    break;
                case ')':
                    sb.append(hasA ? "al" : "o");
                    hasA = false;
                    break;
                default:
            }
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("G()(al)").expect("Goal");

    @TestData
    public DataExpectation example2 = DataExpectation.create("G()()()()(al)").expect("Gooooal");

    @TestData
    public DataExpectation example3 = DataExpectation.create("(al)G(al)()()G").expect("alGalooG");

}
