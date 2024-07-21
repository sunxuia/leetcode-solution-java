package q2100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2056. Number of Valid Move Combinations On Chessboard</h3>
 * <a href="https://leetcode.com/problems/number-of-valid-move-combinations-on-chessboard/">
 * https://leetcode.com/problems/number-of-valid-move-combinations-on-chessboard/
 * </a><br/>
 *
 * <p>There is an <code>8 x 8</code> chessboard containing <code>n</code> pieces (rooks, queens, or bishops). You are
 * given a string array <code>pieces</code> of length <code>n</code>, where <code>pieces[i]</code> describes the type
 * (rook, queen, or bishop) of the <code>i<sup>th</sup></code> piece. In addition, you are given a 2D integer array
 * <code>positions</code> also of length <code>n</code>, where <code>positions[i] = [r<sub>i</sub>,
 * c<sub>i</sub>]</code> indicates that the <code>i<sup>th</sup></code> piece is currently at the
 * <strong>1-based</strong> coordinate <code>(r<sub>i</sub>, c<sub>i</sub>)</code> on the chessboard.</p>
 *
 * <p>When making a <strong>move</strong> for a piece, you choose a <strong>destination</strong> square that the piece
 * will travel toward and stop on.</p>
 *
 * <ul>
 * 	<li>A rook can only travel <strong>horizontally or vertically</strong> from <code>(r, c)</code> to the direction
 * 	of <code>(r+1, c)</code>, <code>(r-1, c)</code>, <code>(r, c+1)</code>, or <code>(r, c-1)</code>.</li>
 * 	<li>A queen can only travel <strong>horizontally, vertically, or diagonally</strong> from <code>(r, c)</code> to
 * 	the direction of <code>(r+1, c)</code>, <code>(r-1, c)</code>, <code>(r, c+1)</code>, <code>(r, c-1)</code>,
 * 	<code>(r+1, c+1)</code>, <code>(r+1, c-1)</code>, <code>(r-1, c+1)</code>, <code>(r-1, c-1)</code>.</li>
 * 	<li>A bishop can only travel <strong>diagonally</strong> from <code>(r, c)</code> to the direction of <code>(r+1,
 * 	c+1)</code>, <code>(r+1, c-1)</code>, <code>(r-1, c+1)</code>, <code>(r-1, c-1)</code>.</li>
 * </ul>
 *
 * <p>You must make a <strong>move</strong> for every piece on the board simultaneously. A <strong>move
 * combination</strong> consists of all the <strong>moves</strong> performed on all the given pieces. Every second,
 * each piece will instantaneously travel <strong>one square</strong> towards their destination if they are not
 * already at it. All pieces start traveling at the <code>0<sup>th</sup></code> second. A move combination is
 * <strong>invalid</strong> if, at a given time, <strong>two or more</strong> pieces occupy the same square.</p>
 *
 * <p>Return <em>the number of <strong>valid</strong> move combinations</em>​​​​​.</p>
 *
 * <p><strong>Notes:</strong></p>
 *
 * <ul>
 * 	<li><strong>No two pieces</strong> will start in the<strong> same</strong> square.</li>
 * 	<li>You may choose the square a piece is already on as its <strong>destination</strong>.</li>
 * 	<li>If two pieces are <strong>directly adjacent</strong> to each other, it is valid for them to <strong>move past
 * 	each other</strong> and swap positions in one second.</li>
 * </ul>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/09/23/a1.png" style="width: 215px; height: 215px;" />
 * <pre>
 * <strong>Input:</strong> pieces = [&quot;rook&quot;], positions = [[1,1]]
 * <strong>Output:</strong> 15
 * <strong>Explanation:</strong> The image above shows the possible squares the piece can move to.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/09/23/a2.png" style="width: 215px; height: 215px;" />
 * <pre>
 * <strong>Input:</strong> pieces = [&quot;queen&quot;], positions = [[1,1]]
 * <strong>Output:</strong> 22
 * <strong>Explanation:</strong> The image above shows the possible squares the piece can move to.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/09/23/a3.png" style="width: 214px; height: 215px;" />
 * <pre>
 * <strong>Input:</strong> pieces = [&quot;bishop&quot;], positions = [[4,3]]
 * <strong>Output:</strong> 12
 * <strong>Explanation:</strong> The image above shows the possible squares the piece can move to.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>n == pieces.length </code></li>
 * 	<li><code>n == positions.length</code></li>
 * 	<li><code>1 &lt;= n &lt;= 4</code></li>
 * 	<li><code>pieces</code> only contains the strings <code>&quot;rook&quot;</code>, <code>&quot;queen&quot;</code>,
 * 	and <code>&quot;bishop&quot;</code>.</li>
 * 	<li>There will be at most one queen on the chessboard.</li>
 * 	<li><code>1 &lt;= x<sub>i</sub>, y<sub>i</sub> &lt;= 8</code></li>
 * 	<li>Each <code>positions[i]</code> is distinct.</li>
 * </ul>
 *
 * <pre>
 * 题解 : (这题不推荐) 难度在于理解题目上, 这题看中文的说明比较容易理解
 * 所有的棋子选定一个方向(和棋子类型相关)与距离(可以为0), 然后按照每秒一格的速度开始移动,
 * 直到此位置被边界或其他棋子阻挡(特例: 如果两个棋子相向而行且相邻, 可以同时互相越过彼此).
 *
 * 中文题目:
 * https://leetcode.cn/problems/number-of-valid-move-combinations-on-chessboard/description/
 * </pre>
 */
@RunWith(LeetCodeRunner.class)
public class Q2056_NumberOfValidMoveCombinationsOnChessboard {

    // 因为 n 比较小, 所以可以暴力
    @Answer
    public int countCombinations(String[] pieces, int[][] positions) {
        // 将字符串转换为下标
        int[] types = new int[pieces.length];
        for (int i = 0; i < pieces.length; i++) {
            char c = pieces[i].charAt(0);
            types[i] = c == 'r' ? 1 : c == 'b' ? 2 : 3;
        }

        // chessboard[row][column] 的int 类型分成 4 个8 位数: { 第 3 个棋子, 第 2 个棋子, 第 1 个棋子, 第 0 个棋子 },
        // 每 8 位数字分别表示: 4位棋子方向 (0b1111 表示占领了这个位置), 4位棋子到达时间.
        // 按题目的要求(包括特例的特别声明), 这个方向位其实没用处, 仅用来表示占领.
        int[][] chessboard = new int[SIZE][SIZE];

        // 遍历
        return dfs(types, positions, 0, chessboard);
    }

    private int dfs(int[] types, int[][] positions, int index, int[][] chessboard) {
        if (index == types.length) {
            return 1;
        }
        int type = types[index];
        int row = positions[index][0] - 1;
        int column = positions[index][1] - 1;
        int res = 0;

        // 如果初始位置未被占领, 且未有其他棋子经过(不会挡住别人), 则可以原地不动
        if (chessboard[row][column] == 0) {
            chessboard[row][column] = 0xf0 << (8 * index);
            res += dfs(types, positions, index + 1, chessboard);
            chessboard[row][column] = 0;
        }

        // 向不同方向前进不同的步数
        for (int[] direction : DIRECTIONS[type]) {
            int step = 0;
            while (true) {
                step++;
                int r = row + direction[0] * step;
                int c = column + direction[1] * step;
                if (r < 0 || c < 0 || r == SIZE || c == SIZE) {
                    // 超过边界
                    break;
                }
                int s = chessboard[r][c];
                // 0 : 停止循环, 1 : 跳过这一格, 2 : 占领这一格
                int next = 2;
                for (int t = s; t != 0 && next != 0; t >>= 8) {
                    // 如果有其他棋子会经过这个点/在这个点
                    if ((t & 0xff) != 0) {
                        int td = t & 0xf0;
                        int ts = t & 0x0f;

                        if (step == ts) {
                            // 不可以与另一个棋子同时到达
                            next = 0;
                        } else if (td == 0xf0) {
                            // 另一个棋子占领了这个格子, 则必须在这个棋子到达之前路过这儿, 否则无法通过
                            next = step < ts ? 1 : 0;
                        } else {
                            // 另一个棋子路过这个格子, 所以只能在路过之后占领
                            next = Math.min(next, step > ts ? 2 : 1);
                        }
                    }
                }

                if (next >= 2) {
                    // 占领格子
                    chessboard[r][c] = s | ((0xf0 + step) << (8 * index));
                    int ret = dfs(types, positions, index + 1, chessboard);
                    res += ret;
                }
                if (next >= 1) {
                    // 路过格子
                    chessboard[r][c] = s | ((direction[2] + step) << (8 * index));
                } else {
                    // 此格子不允许路过
                    break;
                }
            }
            // 取消路过的变更
            for (int i = 1; i < step; i++) {
                int r = row + direction[0] * i;
                int c = column + direction[1] * i;
                chessboard[r][c] = chessboard[r][c] & ~(0xff << 8 * index);
            }
        }

        return res;
    }

    private static final int SIZE = 8;

    // 行走方向, {r, c, d} 分别表示行方向、列方向、路径方向(1行、2列、3行列、4反行列)
    private static final int[][][] DIRECTIONS = new int[][][]{
            null,
            // rooks
            {{1, 0, 1 << 4}, {-1, 0, 2 << 4}, {0, 1, 3 << 4}, {0, -1, 4 << 4}},
            // bishops
            {{1, 1, 5 << 4}, {-1, -1, 6 << 4}, {-1, 1, 7 << 4}, {1, -1, 8 << 4}},
            // queens
            {{1, 0, 1 << 4}, {-1, 0, 2 << 4}, {0, 1, 3 << 4}, {0, -1, 4 << 4},
                    {1, 1, 5 << 4}, {-1, -1, 6 << 4}, {-1, 1, 7 << 4}, {1, -1, 8 << 4}},
    };

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"rook"}, new int[][]{{1, 1}}).expect(15);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"queen"}, new int[][]{{1, 1}}).expect(22);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{"bishop"}, new int[][]{{4, 3}})
            .expect(12);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new String[]{"bishop", "rook"}, new int[][]{{8, 5}, {7, 7}})
            .expect(96);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new String[]{"queen", "rook"}, new int[][]{{1, 2}, {7, 2}})
            .expect(293);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(new String[]{"queen", "bishop", "rook"}, new int[][]{{6, 2}, {6, 8}, {2, 8}})
            .expect(2464);

}
