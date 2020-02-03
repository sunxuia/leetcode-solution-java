package q150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/surrounded-regions/
 *
 * Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.
 *
 * A region is captured by flipping all 'O's into 'X's in that surrounded region.
 *
 * Example:
 *
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 *
 * After running your function, the board should be:
 *
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 *
 * Explanation:
 *
 * Surrounded regions shouldn’t be on the border, which means that any 'O' on the border of the board are not flipped
 * to 'X'. Any 'O' that is not on the border and it is not connected to an 'O' on the border will be flipped to 'X'.
 * Two cells are connected if they are adjacent cells connected horizontally or vertically.
 */
@RunWith(LeetCodeRunner.class)
public class Q130_SurroundedRegions {

    // 将边界的O 标记为其它字符, 并扩展到里面, 之后剩下的O 就是需要转换的了.
    @Answer
    public void solve(char[][] board) {
        if (board.length < 3 || board[0].length < 3) {
            return;
        }
        final int height = board.length, width = board[0].length;
        // 边界的'O' 标记为'P'
        for (int i = 0; i < height; i++) {
            markP(board, i, 0);
            markP(board, i, width - 1);
        }
        for (int i = 0; i < width; i++) {
            markP(board, 0, i);
            markP(board, height - 1, i);
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (board[i][j]) {
                    case 'O':
                        board[i][j] = 'X';
                        break;
                    case 'P':
                        board[i][j] = 'O';
                        break;
                }
            }
        }
    }

    private void markP(char[][] board, int i, int j) {
        final int height = board.length, width = board[0].length;
        if (i < 0 || i >= height
                || j < 0 || j >= width
                || board[i][j] != 'O') {
            return;
        }
        board[i][j] = 'P';
        markP(board, i - 1, j);
        markP(board, i, j + 1);
        markP(board, i + 1, j);
        markP(board, i, j - 1);
    }

    /**
     * 这题有一种通用的算法: union find, 不过对这题来说就比较慢了.
     * union find 的思路是:
     * 图中的不同节点(比如 a, b) 如果有关联, 则将其连接起来 (b -> a), 其它节点的关联也是如此 (比如 c -> a, a -> d, e -> d),
     * 那么查找c 和e 之间是否关联就可以通过分别找出c 和d 的根节点 (c -> a -> d, e -> d) 来判断.
     */
    @Answer
    public void solve_UnionFind(char[][] board) {
        if (board.length < 3 || board[0].length < 3) {
            return;
        }
        final int height = board.length, width = board[0].length;
        UnionFind uf = new UnionFind(height * width);
        // 让边界的节点连接到 (0, 0) 上.
        for (int i = 0; i < height; i++) {
            tryUnion(board, uf, 0, 0, i, 0);
            tryUnion(board, uf, 0, 0, i, width - 1);
        }
        for (int i = 0; i < width; i++) {
            tryUnion(board, uf, 0, 0, 0, i);
            tryUnion(board, uf, 0, 0, height - 1, i);
        }
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                if (board[i][j] == 'O') {
                    tryUnion(board, uf, i, j, i - 1, j);
                    tryUnion(board, uf, i, j, i, j + 1);
                    tryUnion(board, uf, i, j, i + 1, j);
                    tryUnion(board, uf, i, j, i, j - 1);
                }
            }
        }
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                if (board[i][j] == 'O' && !uf.isConnected(0, i * width + j)) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void tryUnion(char[][] board, UnionFind uf, int i, int j, int pi, int pj) {
        if (board[pi][pj] == 'O') {
            final int width = board[0].length;
            uf.union(i * width + j, pi * width + pj);
        }
    }

    // 使用加权路径压缩(weights) 的 union find
    private static class UnionFind {

        // 节点的 id 对应的上级节点, 下标是节点id, 值是连接的节点的id.
        // 如果 parent[id] = id, 那么就表示 id 节点是根节点.
        private final int[] id;

        // 路径权重. 下标是节点id, 值是连接到这个节点的子节点的总数.
        private final int[] weight;

        public UnionFind(int n) {
            id = new int[n];
            weight = new int[n];
            // 初始化让所有节点自己连到自己(都是根节点)
            for (int i = 0; i < n; i++) {
                id[i] = i;
                weight[i] = 1;
            }
        }

        // 连接2 个节点, 首先找出它们的根节点, 如果这2 个节点不通 (根节点不同), 则连接2 个根节点.
        // 使用weights 来让小权重小的节点连接到权重大的节点上, 来减端树的路径长度.
        public void union(int a, int b) {
            int p = find(a);
            int q = find(b);
            if (p != q) {
                if (weight[q] > weight[p]) {
                    int moveWeight = weight[p];
                    weight[id[p]] -= moveWeight;
                    weight[q] += moveWeight;

                    id[p] = q;
                } else {
                    int moveWeight = weight[q];
                    weight[id[q]] -= moveWeight;
                    weight[p] += moveWeight;

                    id[q] = p;
                }
            }
        }

        // 查找节点的对应根节点, 并在查找的过程中压缩路径.
        private int find(int cur) {
            int p = id[cur];
            while (p != id[p]) {
                weight[p] -= weight[cur];
                weight[id[p]] += weight[cur];
                id[cur] = id[p];
                cur = id[p];
                p = id[cur];
            }
            return p;
        }

        private boolean isConnected(int a, int b) {
            return find(a) == find(b);
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new char[][]{
                    {'X', 'X', 'X', 'X'},
                    {'X', 'O', 'O', 'X'},
                    {'X', 'X', 'O', 'X'},
                    {'X', 'O', 'X', 'X'},
            })
            .expectArgument(0, new char[][]{
                    {'X', 'X', 'X', 'X'},
                    {'X', 'X', 'X', 'X'},
                    {'X', 'X', 'X', 'X'},
                    {'X', 'O', 'X', 'X'},
            }).build();

    @TestData
    public DataExpectation border = DataExpectation.builder()
            .addArgument(new char[0][0])
            .expectArgument(0, new char[0][0])
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new char[][]{
                    {'X', 'O', 'X'},
                    {'X', 'O', 'X'},
                    {'X', 'O', 'X'},
            })
            .expectArgument(0, new char[][]{
                    {'X', 'O', 'X'},
                    {'X', 'O', 'X'},
                    {'X', 'O', 'X'},
            }).build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new char[][]{
                    {'O', 'X', 'O', 'O', 'O', 'X'},
                    {'O', 'O', 'X', 'X', 'X', 'O'},
                    {'X', 'X', 'X', 'X', 'X', 'O'},
                    {'O', 'O', 'O', 'O', 'X', 'X'},
                    {'X', 'X', 'O', 'O', 'X', 'O'},
                    {'O', 'O', 'X', 'X', 'X', 'X'}
            })
            .expectArgument(0, new char[][]{
                    {'O', 'X', 'O', 'O', 'O', 'X'},
                    {'O', 'O', 'X', 'X', 'X', 'O'},
                    {'X', 'X', 'X', 'X', 'X', 'O'},
                    {'O', 'O', 'O', 'O', 'X', 'X'},
                    {'X', 'X', 'O', 'O', 'X', 'O'},
                    {'O', 'O', 'X', 'X', 'X', 'X'}
            }).build();

    @TestData
    public DataExpectation normal3 = DataExpectation.builder()
            .addArgument(new char[][]{
                    {'X', 'O', 'O', 'X', 'X', 'X', 'O', 'X', 'O', 'O'},
                    {'X', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                    {'X', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'X', 'X'},
                    {'X', 'O', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'O'},
                    {'O', 'X', 'X', 'X', 'O', 'X', 'O', 'X', 'O', 'X'},
                    {'X', 'X', 'O', 'X', 'X', 'O', 'O', 'X', 'X', 'X'},
                    {'O', 'X', 'X', 'O', 'O', 'X', 'O', 'X', 'X', 'O'},
                    {'O', 'X', 'X', 'X', 'X', 'X', 'O', 'X', 'X', 'X'},
                    {'X', 'O', 'O', 'X', 'X', 'O', 'X', 'X', 'O', 'O'},
                    {'X', 'X', 'X', 'O', 'O', 'X', 'O', 'X', 'X', 'O'}
            }).expectArgument(0, new char[][]{
                    {'X', 'O', 'O', 'X', 'X', 'X', 'O', 'X', 'O', 'O'},
                    {'X', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                    {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                    {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'O'},
                    {'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                    {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                    {'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'O'},
                    {'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                    {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'O'},
                    {'X', 'X', 'X', 'O', 'O', 'X', 'O', 'X', 'X', 'O'}
            }).build();


}
