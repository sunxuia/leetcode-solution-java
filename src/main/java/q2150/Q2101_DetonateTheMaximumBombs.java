package q2150;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2101. Detonate the Maximum Bombs</h3>
 * <a href="https://leetcode.com/problems/detonate-the-maximum-bombs/">
 * https://leetcode.com/problems/detonate-the-maximum-bombs/
 * </a><br/>
 *
 * <p>You are given a list of bombs. The <strong>range</strong> of a bomb is defined as the area where its effect can
 * be
 * felt. This area is in the shape of a <strong>circle</strong> with the center as the location of the bomb.</p>
 *
 * <p>The bombs are represented by a <strong>0-indexed</strong> 2D integer array <code>bombs</code> where
 * <code>bombs[i]
 * = [x<sub>i</sub>, y<sub>i</sub>, r<sub>i</sub>]</code>. <code>x<sub>i</sub></code> and <code>y<sub>i</sub></code>
 * denote the X-coordinate and Y-coordinate of the location of the <code>i<sup>th</sup></code> bomb, whereas
 * <code>r<sub>i</sub></code> denotes the <strong>radius</strong> of its range.</p>
 *
 * <p>You may choose to detonate a <strong>single</strong> bomb. When a bomb is detonated, it will detonate <strong>all
 * bombs</strong> that lie in its range. These bombs will further detonate the bombs that lie in their ranges.</p>
 *
 * <p>Given the list of <code>bombs</code>, return <em>the <strong>maximum</strong> number of bombs that can be
 * detonated if you are allowed to detonate <strong>only one</strong> bomb</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/06/desmos-eg-3.png" style="width: 300px; height:
 * 300px;"
 * />
 * <pre>
 * <strong>Input:</strong> bombs = [[2,1,3],[6,1,4]]
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong>
 * The above figure shows the positions and ranges of the 2 bombs.
 * If we detonate the left bomb, the right bomb will not be affected.
 * But if we detonate the right bomb, both bombs will be detonated.
 * So the maximum bombs that can be detonated is max(1, 2) = 2.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/06/desmos-eg-2.png" style="width: 300px; height:
 * 300px;"
 * />
 * <pre>
 * <strong>Input:</strong> bombs = [[1,1,5],[10,10,5]]
 * <strong>Output:</strong> 1
 * <strong>Explanation:
 * </strong>Detonating either bomb will not detonate the other bomb, so the maximum number of bombs that can be detonated is 1.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/07/desmos-eg1.png" style="width: 300px; height: 300px;"
 * />
 * <pre>
 * <strong>Input:</strong> bombs = [[1,2,3],[2,3,1],[3,4,2],[4,5,3],[5,6,4]]
 * <strong>Output:</strong> 5
 * <strong>Explanation:</strong>
 * The best bomb to detonate is bomb 0 because:
 * - Bomb 0 detonates bombs 1 and 2. The red circle denotes the range of bomb 0.
 * - Bomb 2 detonates bomb 3. The blue circle denotes the range of bomb 2.
 * - Bomb 3 detonates bomb 4. The green circle denotes the range of bomb 3.
 * Thus all 5 bombs are detonated.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= bombs.length&nbsp;&lt;= 100</code></li>
 * 	<li><code>bombs[i].length == 3</code></li>
 * 	<li><code>1 &lt;= x<sub>i</sub>, y<sub>i</sub>, r<sub>i</sub> &lt;= 10<sup>5</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2101_DetonateTheMaximumBombs {

    // dfs 解法
    @Answer
    public int maximumDetonation(int[][] bombs) {
        final int n = bombs.length;
        boolean[][] detonates = getDetonates(bombs);

        int res = 0;
        boolean[] invoked = new boolean[n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(invoked, false);
            int num = dfs(i, invoked, detonates);
            res = Math.max(res, num);
        }
        return res;
    }

    private boolean[][] getDetonates(int[][] bombs) {
        final int n = bombs.length;
        boolean[][] detonates = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                long dy = bombs[i][0] - bombs[j][0];
                long dx = bombs[i][1] - bombs[j][1];
                long dist = dy * dy + dx * dx;
                if (dist <= (long) bombs[i][2] * bombs[i][2]) {
                    detonates[i][j] = true;
                }
            }
        }
        return detonates;
    }

    // (这里如果优化下, 换成稀疏矩阵会快一些)
    private int dfs(int curr, boolean[] invoked, boolean[][] detonates) {
        final int n = invoked.length;
        if (invoked[curr]) {
            return 0;
        }
        invoked[curr] = true;
        int res = 1;
        for (int i = 0; i < n; i++) {
            if (detonates[curr][i]) {
                res += dfs(i, invoked, detonates);
            }
        }
        return res;
    }

    // bfs 解法
    @Answer
    public int maximumDetonation2(int[][] bombs) {
        final int n = bombs.length;
        boolean[][] detonates = getDetonates(bombs);

        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] invoked = new boolean[n];
        int res = 0;
        for (int i = 0; i < n; i++) {
            Arrays.fill(invoked, false);
            queue.add(i);
            invoked[i] = true;
            int sum = 1;
            while (!queue.isEmpty()) {
                int curr = queue.poll();
                for (int j = 0; j < n; j++) {
                    if (detonates[curr][j] && !invoked[j]) {
                        queue.add(j);
                        invoked[j] = true;
                        sum++;
                    }
                }
            }
            res = Math.max(res, sum);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{2, 1, 3}, {6, 1, 4}}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{1, 1, 5}, {10, 10, 5}}).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[][]{{1, 2, 3}, {2, 3, 1}, {3, 4, 2}, {4, 5, 3}, {5, 6, 4}}).expect(5);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{{1, 1, 100000}, {100000, 100000, 1}}).expect(1);

}
