package q900;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 864. Shortest Path to Get All Keys
 * https://leetcode.com/problems/shortest-path-to-get-all-keys/
 *
 * We are given a 2-dimensional grid. "." is an empty cell, "#" is a wall, "@" is the starting point, ("a", "b", ...)
 * are keys, and ("A", "B", ...) are locks.
 *
 * We start at the starting point, and one move consists of walking one space in one of the 4 cardinal directions.  We
 * cannot walk outside the grid, or walk into a wall.  If we walk over a key, we pick it up.  We can"t walk over a lock
 * unless we have the corresponding key.
 *
 * For some 1 <= K <= 6, there is exactly one lowercase and one uppercase letter of the first K letters of the English
 * alphabet in the grid.  This means that there is exactly one key for each lock, and one lock for each key; and also
 * that the letters used to represent the keys and locks were chosen in the same order as the English alphabet.
 *
 * Return the lowest number of moves to acquire all keys.  If it's impossible, return -1.
 *
 * Example 1:
 *
 * Input: ["@.a.#","###.#","b.A.B"]
 * Output: 8
 *
 * Example 2:
 *
 * Input: ["@..aA","..B#.","....b"]
 * Output: 6
 *
 * Note:
 *
 * 1 <= grid.length <= 30
 * 1 <= grid[0].length <= 30
 * grid[i][j] contains only ".", "#", "@", "a"-"f" and "A"-"F"
 * The number of keys is in [1, 6].  Each key has a different letter and opens exactly one lock.
 */
@RunWith(LeetCodeRunner.class)
public class Q864_ShortestPathToGetAllKeys {

    // bfs
    @Answer
    public int shortestPathAllKeys(String[] grid) {
        final int m = grid.length, n = grid[0].length();
        char[][] cs = new char[m][];
        int si = 0, sj = 0, expectKeys = 0;
        for (int i = 0; i < m; i++) {
            cs[i] = grid[i].toCharArray();

            for (int j = 0; j < n; j++) {
                if (cs[i][j] == '@') {
                    cs[i][j] = '.';
                    si = i;
                    sj = j;
                } else if ('a' <= cs[i][j] && cs[i][j] <= 'f') {
                    expectKeys |= 1 << (cs[i][j] - 'a');
                }
            }
        }

        // 状态数字的位数 [0, 5] 表示获取钥匙的状态, [6, 11] 表示j 坐标, [12, 17] 表示i 坐标.
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add((si << 12) + (sj << 6) + expectKeys);
        int res = 0;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                int encoded = queue.remove();
                if (!visited.add(encoded)) {
                    continue;
                }
                int i = encoded >> 12 & 0x1f;
                int j = encoded >> 6 & 0x1f;
                int keys = encoded & 0x3f;
                if (m <= i || n <= j || cs[i][j] == '#'
                        || 'A' <= cs[i][j] && cs[i][j] <= 'F' && (keys >> (cs[i][j] - 'A') & 1) == 1) {
                    continue;
                }
                if ('a' <= cs[i][j] && cs[i][j] <= 'z') {
                    keys &= 1 << (cs[i][j] - 'a') ^ 0x3f;
                }
                if (keys == 0) {
                    return res;
                }
                // m, n 的最大长度是30, +32再%32 可以将-1 变为31
                queue.add(((i + 1 + 32) % 32 << 12) + ((j + 32) % 32 << 6) + keys);
                queue.add(((i + 32) % 32 << 12) + ((j - 1 + 32) % 32 << 6) + keys);
                queue.add(((i - 1 + 32) % 32 << 12) + ((j + 32) % 32 << 6) + keys);
                queue.add(((i + 32) % 32 << 12) + ((j + 1 + 32) % 32 << 6) + keys);
            }
            res++;
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new String[]{"@.a.#", "###.#", "b.A.B"}).expect(8);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new String[]{"@..aA", "..B#.", "....b"}).expect(6);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new String[]{"@Aa"}).expect(-1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new String[]{
            ".##..##..B",
            "##...#...#",
            "..##..#...",
            ".#..#b...#",
            "#.##.a.###",
            ".#....#...",
            ".##..#.#..",
            ".....###@.",
            "..........",
            ".........A"}).expect(11);

}
