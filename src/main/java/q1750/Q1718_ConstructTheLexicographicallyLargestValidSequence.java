package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1718. Construct the Lexicographically Largest Valid Sequence
 * https://leetcode.com/problems/construct-the-lexicographically-largest-valid-sequence/
 *
 * Given an integer n, find a sequence that satisfies all of the following:
 *
 * The integer 1 occurs once in the sequence.
 * Each integer between 2 and n occurs twice in the sequence.
 * For every integer i between 2 and n, the distance between the two occurrences of i is exactly i.
 *
 * The distance between two numbers on the sequence, a[i] and a[j], is the absolute difference of their indices, |j -
 * i|.
 *
 * Return the lexicographically largest sequence. It is guaranteed that under the given constraints, there is always a
 * solution.
 *
 * A sequence a is lexicographically larger than a sequence b (of the same length) if in the first position where a and
 * b differ, sequence a has a number greater than the corresponding number in b. For example, [0,1,9,0] is
 * lexicographically larger than [0,1,5,6] because the first position they differ is at the third number, and 9 is
 * greater than 5.
 *
 * Example 1:
 *
 * Input: n = 3
 * Output: [3,1,2,3,2]
 * Explanation: [2,3,2,1,3] is also a valid sequence, but [3,1,2,3,2] is the lexicographically largest valid sequence.
 *
 * Example 2:
 *
 * Input: n = 5
 * Output: [5,3,1,4,3,5,2,4,2]
 *
 * Constraints:
 *
 * 1 <= n <= 20
 */
@RunWith(LeetCodeRunner.class)
public class Q1718_ConstructTheLexicographicallyLargestValidSequence {

    /**
     * 这题没什么特殊规律, 直接用回溯法解决.
     */
    @Answer
    public int[] constructDistancedSequence(int n) {
        int[] res = new int[n * 2 - 1];
        boolean[] visited = new boolean[n + 1];
        dfs(res, visited, 0);
        return res;
    }

    private boolean dfs(int[] res, boolean[] visited, int index) {
        if (index == res.length) {
            return true;
        }
        if (res[index] != 0) {
            return dfs(res, visited, index + 1);
        }
        for (int v = visited.length - 1; v > 1; v--) {
            if (!visited[v]
                    && index + v < res.length
                    && res[index + v] == 0) {
                visited[v] = true;
                res[index] = res[index + v] = v;
                if (dfs(res, visited, index + 1)) {
                    return true;
                }
                res[index] = res[index + v] = 0;
                visited[v] = false;
            }
        }
        if (!visited[1]) {
            visited[1] = true;
            res[index] = 1;
            if (dfs(res, visited, index + 1)) {
                return true;
            }
            res[index] = 0;
            visited[1] = false;
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(3).expect(new int[]{3, 1, 2, 3, 2});

    @TestData
    public DataExpectation example2 = DataExpectation.create(5).expect(new int[]{5, 3, 1, 4, 3, 5, 2, 4, 2});

    @TestData
    public DataExpectation normal1 = DataExpectation.create(2).expect(new int[]{2, 1, 2});

    @TestData
    public DataExpectation normal2 = DataExpectation.create(4).expect(new int[]{4, 2, 3, 2, 4, 3, 1});

    @TestData
    public DataExpectation normal3 = DataExpectation.create(7).expect(new int[]{7, 5, 3, 6, 4, 3, 5, 7, 4, 6, 2, 1, 2});

}
