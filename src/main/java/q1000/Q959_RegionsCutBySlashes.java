package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 959. Regions Cut By Slashes
 * https://leetcode.com/problems/regions-cut-by-slashes/
 *
 * In a N x N grid composed of 1 x 1 squares, each 1 x 1 square consists of a /, \, or blank space.  These characters
 * divide the square into contiguous regions.
 *
 * (Note that backslash characters are escaped, so a \ is represented as "\\".)
 *
 * Return the number of regions.
 *
 * Example 1:
 *
 * Input:
 * [
 * " /",
 * "/ "
 * ]
 * Output: 2
 * Explanation: The 2x2 grid is as follows:
 * (图Q959_PIC1.png)
 *
 * Example 2:
 *
 * Input:
 * [
 * " /",
 * "  "
 * ]
 * Output: 1
 * Explanation: The 2x2 grid is as follows:
 * (图Q959_PIC2.png)
 *
 * Example 3:
 *
 * Input:
 * [
 * "\\/",
 * "/\\"
 * ]
 * Output: 4
 * Explanation: (Recall that because \ characters are escaped, "\\/" refers to \/, and "/\\" refers to /\.)
 * The 2x2 grid is as follows:
 * (图Q959_PIC3.png)
 *
 * Example 4:
 *
 * Input:
 * [
 * "/\\",
 * "\\/"
 * ]
 * Output: 5
 * Explanation: (Recall that because \ characters are escaped, "/\\" refers to /\, and "\\/" refers to \/.)
 * The 2x2 grid is as follows:
 * (图Q959_PIC4.png)
 *
 * Example 5:
 *
 * Input:
 * [
 * "//",
 * "/ "
 * ]
 * Output: 3
 * Explanation: The 2x2 grid is as follows:
 * (图Q959_PIC5.png)
 *
 * Note:
 *
 * 1 <= grid.length == grid[0].length <= 30
 * grid[i][j] is either '/', '\', or ' '.
 */
@RunWith(LeetCodeRunner.class)
public class Q959_RegionsCutBySlashes {

    /**
     * 按照如下方式对每个格子按对角线进行划分
     * ---------
     * | \ 1 / |
     * | 0 X 2 |
     * | / 3 \ |
     * ---------
     */
    @Answer
    public int regionsBySlashes(String[] grid) {
        final int n = grid.length;
        boolean[][] visited = new boolean[n][n * 4];
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n * 4; j++) {
                if (!visited[i][j]) {
                    dfs(visited, grid, i, j);
                    res++;
                }
            }
        }
        return res;
    }

    private void dfs(boolean[][] visited, String[] grid, int i, int j) {
        final int n = grid.length;
        if (i < 0 || j < 0 || i >= n || j >= 4 * n || visited[i][j]) {
            return;
        }
        visited[i][j] = true;
        char c = grid[i].charAt(j / 4);
        switch (j % 4) {
            case 0:
                dfs(visited, grid, i, j - 2);
                if (c != '/') {
                    dfs(visited, grid, i, j + 3);
                }
                if (c != '\\') {
                    dfs(visited, grid, i, j + 1);
                }
                break;
            case 1:
                dfs(visited, grid, i - 1, j + 2);
                if (c != '/') {
                    dfs(visited, grid, i, j + 1);
                }
                if (c != '\\') {
                    dfs(visited, grid, i, j - 1);
                }
                break;
            case 2:
                dfs(visited, grid, i, j + 2);
                if (c != '/') {
                    dfs(visited, grid, i, j - 1);
                }
                if (c != '\\') {
                    dfs(visited, grid, i, j + 1);
                }
                break;
            case 3:
                dfs(visited, grid, i + 1, j - 2);
                if (c != '/') {
                    dfs(visited, grid, i, j - 3);
                }
                if (c != '\\') {
                    dfs(visited, grid, i, j - 1);
                }
                break;
            default:
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new String[]{" /", "/ "}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new String[]{" /", "  "}).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new String[]{"\\/", "/\\"}).expect(4);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new String[]{"/\\", "\\/"}).expect(5);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new String[]{"//", "/ "}).expect(3);

}
