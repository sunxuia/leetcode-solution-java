package q1500;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1496. Path Crossing
 * https://leetcode.com/problems/path-crossing/
 *
 * Given a string path, where path[i] = 'N', 'S', 'E' or 'W', each representing moving one unit north, south, east, or
 * west, respectively. You start at the origin (0, 0) on a 2D plane and walk on the path specified by path.
 *
 * Return True if the path crosses itself at any point, that is, if at any time you are on a location you've previously
 * visited. Return False otherwise.
 *
 * Example 1:
 *
 * Input: path = "NES"
 * Output: false
 * Explanation: Notice that the path doesn't cross any point more than once.
 *
 * Example 2:
 *
 * Input: path = "NESWW"
 * Output: true
 * Explanation: Notice that the path visits the origin twice.
 *
 * Constraints:
 *
 * 1 <= path.length <= 10^4
 * path will only consist of characters in {'N', 'S', 'E', 'W}
 */
@RunWith(LeetCodeRunner.class)
public class Q1496_PathCrossing {

    @Answer
    public boolean isPathCrossing(String path) {
        Map<Integer, Set<Integer>> axios = new HashMap<>();
        int y = 0, x = 0;
        axios.put(0, new HashSet<>(List.of(0)));
        for (int i = 0; i < path.length(); i++) {
            switch (path.charAt(i)) {
                case 'N':
                    y++;
                    break;
                case 'S':
                    y--;
                    break;
                case 'E':
                    x++;
                    break;
                case 'W':
                    x--;
                    break;
                default:
            }
            if (!axios.computeIfAbsent(y, k -> new HashSet<>()).add(x)) {
                return true;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("NES").expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation.create("NESWW").expect(true);

}
