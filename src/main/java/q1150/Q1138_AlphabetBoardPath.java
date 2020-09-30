package q1150;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1138. Alphabet Board Path
 * https://leetcode.com/problems/alphabet-board-path/
 *
 * On an alphabet board, we start at position (0, 0), corresponding to character board[0][0].
 *
 * Here, board = ["abcde", "fghij", "klmno", "pqrst", "uvwxy", "z"], as shown in the diagram below.
 * <img src="Q1138_PIC.png" />
 *
 * We may make the following moves:
 *
 * 'U' moves our position up one row, if the position exists on the board;
 * 'D' moves our position down one row, if the position exists on the board;
 * 'L' moves our position left one column, if the position exists on the board;
 * 'R' moves our position right one column, if the position exists on the board;
 * '!' adds the character board[r][c] at our current position (r, c) to the answer.
 *
 * (Here, the only positions that exist on the board are positions with letters on them.)
 *
 * Return a sequence of moves that makes our answer equal to target in the minimum number of moves.  You may return any
 * path that does so.
 *
 * Example 1:
 * Input: target = "leet"
 * Output: "DDR!UURRR!!DDD!"
 * Example 2:
 * Input: target = "code"
 * Output: "RR!DDRR!UUL!R!"
 *
 * Constraints:
 *
 * 1 <= target.length <= 100
 * target consists only of English lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1138_AlphabetBoardPath {

    @Answer
    public String alphabetBoardPath(String target) {
        StringBuilder sb = new StringBuilder();
        int i = 0, j = 0;
        for (int k = 0; k < target.length(); k++) {
            int idx = target.charAt(k) - 'a';
            int ei = idx / 5, ej = idx % 5;
            while (ei != i || ej != j) {
                if (i < ei && j < BOARD[i + 1].length) {
                    sb.append('D');
                    i++;
                } else if (i > ei) {
                    sb.append('U');
                    i--;
                } else if (j < ej && j + 1 < BOARD[i].length) {
                    sb.append('R');
                    j++;
                } else {
                    // j > ej
                    sb.append('L');
                    j--;
                }
            }
            sb.append('!');
        }
        return sb.toString();
    }

    private static char[][] BOARD = new char[][]{
            {'a', 'b', 'c', 'd', 'e'},
            {'f', 'g', 'h', 'i', 'j'},
            {'k', 'l', 'm', 'n', 'o'},
            {'p', 'q', 'r', 's', 't'},
            {'u', 'v', 'w', 'x', 'y'},
            {'z'}
    };

    @TestData
    public DataExpectation example1 = createTestData("leet");

    private DataExpectation createTestData(String target) {
        return DataExpectation.builder()
                .addArgument(target)
                .assertMethod((String res) -> {
                    StringBuilder sb = new StringBuilder();
                    int i = 0, j = 0;
                    for (int k = 0; k < res.length(); k++) {
                        switch (res.charAt(k)) {
                            case 'D':
                                i++;
                                break;
                            case 'U':
                                i--;
                                break;
                            case 'L':
                                j--;
                                break;
                            case 'R':
                                j++;
                                break;
                            case '!':
                                sb.append(BOARD[i][j]);
                                break;
                            default:
                                Assert.fail("Unknown character : " + res.charAt(k));
                        }
                        Assert.assertTrue(0 <= i && i < 6);
                        Assert.assertTrue(0 <= j && j < 5);
                    }
                    Assert.assertEquals(target, sb.toString());
                }).build();
    }

    @TestData
    public DataExpectation example2 = createTestData("code");

}
