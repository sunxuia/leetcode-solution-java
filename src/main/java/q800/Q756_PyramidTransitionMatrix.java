package q800;

import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/pyramid-transition-matrix/
 *
 * We are stacking blocks to form a pyramid. Each block has a color which is a one letter string.
 *
 * We are allowed to place any color block C on top of two adjacent blocks of colors A and B, if and only if ABC is
 * an allowed triple.
 *
 * We start with a bottom row of bottom, represented as a single string. We also start with a list of allowed triples
 * allowed. Each allowed triple is represented as a string of length 3.
 *
 * Return true if we can build the pyramid all the way to the top, otherwise false.
 *
 * Example 1:
 *
 * Input: bottom = "BCD", allowed = ["BCG", "CDE", "GEA", "FFF"]
 * Output: true
 * Explanation:
 * We can stack the pyramid like this:
 * >     A
 * >    / \
 * >   G   E
 * >  / \ / \
 * > B   C   D
 *
 * We are allowed to place G on top of B and C because BCG is an allowed triple.  Similarly, we can place E on top of
 * C and D, then A on top of G and E.
 *
 *
 *
 * Example 2:
 *
 * Input: bottom = "AABA", allowed = ["AAA", "AAB", "ABA", "ABB", "BAC"]
 * Output: false
 * Explanation:
 * We can't stack the pyramid to the top.
 * Note that there could be allowed triples (A, B, C) and (A, B, D) with C != D.
 *
 *
 *
 * Constraints:
 *
 * bottom will be a string with length in range [2, 8].
 * allowed will have length in range [0, 200].
 * Letters in all strings will be chosen from the set {'A', 'B', 'C', 'D', 'E', 'F', 'G'}.
 */
@RunWith(LeetCodeRunner.class)
public class Q756_PyramidTransitionMatrix {

    // dfs 回溯法
    @Answer
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        boolean[][][] allows = new boolean[N][N][N];
        for (String s : allowed) {
            int a = s.charAt(0) - 'A';
            int b = s.charAt(1) - 'A';
            int c = s.charAt(2) - 'A';
            allows[a][b][c] = true;
        }

        int[] nums = new int[bottom.length()];
        for (int i = 0; i < bottom.length(); i++) {
            nums[i] = bottom.charAt(i) - 'A';
        }

        return dfs(nums, new int[nums.length - 1], 0, allows);
    }

    private static final int N = 7;

    // 这里的dfs 涉及到了 nums -> next 和 next 前后元素的两种回溯
    private boolean dfs(int[] nums, int[] next, int idx, boolean[][][] allows) {
        if (next.length == 0) {
            return true;
        }
        if (idx == next.length) {
            return dfs(next, new int[next.length - 1], 0, allows);
        }
        for (int i = 0; i < N; i++) {
            if (allows[nums[idx]][nums[idx + 1]][i]) {
                next[idx] = i;
                if (dfs(nums, next, idx + 1, allows)) {
                    return true;
                }
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("BCD", Arrays.asList("BCG", "CDE", "GEA", "FFF"))
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("AABA", Arrays.asList("AAA", "AAB", "ABA", "ABB", "BAC"))
            .expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith("ABABA", Arrays.asList("ABA", "BAA", "AAA"))
            .expect(true);

}
