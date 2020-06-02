package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/swap-adjacent-in-lr-string/
 *
 * In a string composed of 'L', 'R', and 'X' characters, like "RXXLRXRXL", a move consists of either replacing one
 * occurrence of "XL" with "LX", or replacing one occurrence of "RX" with "XR". Given the starting string start and
 * the ending string end, return True if and only if there exists a sequence of moves to transform one string to the
 * other.
 *
 * Example:
 *
 * Input: start = "RXXLRXRXL", end = "XRLXXRRLX"
 * Output: True
 * Explanation:
 * We can transform start to end following these steps:
 * RXXLRXRXL ->
 * XRXLRXRXL ->
 * XRLXRXRXL ->
 * XRLXXRRXL ->
 * XRLXXRRLX
 *
 *
 *
 * Constraints:
 *
 * 1 <= len(start) == len(end) <= 10000.
 * Both start and end will only consist of characters in {'L', 'R', 'X'}.
 */
@RunWith(LeetCodeRunner.class)
public class Q777_SwapAdjacentInLRString {

    /**
     * 简单的BFS 之类的方式会超时.
     * 参考 https://www.cnblogs.com/grandyang/p/9001474.html
     * 根据题目, 可以知道L 可以一直往左走, 直到开头或L/R, R 可以一直往右走, 直到结尾 L/R.
     * 也就是说L 和 R 之间的相对位置是固定的, 那么如果start 与end 之间的L/R 相对位置不固定, 则结果为false.
     * 再由于L 只能往前, R 只能往后走, 所以如果start 中的R 对应到了end 中靠前的位置则为false, 同理start 中
     * 的L 对应到了end 中靠后的位置也为false.
     */
    @Answer
    public boolean canTransform(String start, String end) {
        final int n = start.length();
        char[] sc = start.toCharArray();
        char[] ec = end.toCharArray();
        int i = 0, j = 0;
        while (true) {
            while (i < n && sc[i] == 'X') {
                i++;
            }
            while (j < n && ec[j] == 'X') {
                j++;
            }
            if (i == n || j == n) {
                return i == j;
            }
            if (sc[i] != ec[j]) {
                return false;
            }
            if (sc[i] == 'R' && i > j || sc[i] == 'L' && i < j) {
                return false;
            }
            i++;
            j++;
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith("RXXLRXRXL", "XRLXXRRLX").expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith("XXRXXRXLXLXXRXRXLXXRXXLXXRXXLXXLXLRXLXRX", "XRXRXLXLXXXRXRXXXLRLXXXXRXLXXXLXLXXXXRLR")
            .expect(false);

}
