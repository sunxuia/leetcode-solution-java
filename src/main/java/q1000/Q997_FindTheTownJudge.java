package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 997. Find the Town Judge
 * https://leetcode.com/problems/find-the-town-judge/
 *
 * In a town, there are N people labelled from 1 to N.  There is a rumor that one of these people is secretly the town
 * judge.
 *
 * If the town judge exists, then:
 *
 * The town judge trusts nobody.
 * Everybody (except for the town judge) trusts the town judge.
 * There is exactly one person that satisfies properties 1 and 2.
 *
 * You are given trust, an array of pairs trust[i] = [a, b] representing that the person labelled a trusts the person
 * labelled b.
 *
 * If the town judge exists and can be identified, return the label of the town judge.  Otherwise, return -1.
 *
 * Example 1:
 * Input: N = 2, trust = [[1,2]]
 * Output: 2
 * Example 2:
 * Input: N = 3, trust = [[1,3],[2,3]]
 * Output: 3
 * Example 3:
 * Input: N = 3, trust = [[1,3],[2,3],[3,1]]
 * Output: -1
 * Example 4:
 * Input: N = 3, trust = [[1,2],[2,3]]
 * Output: -1
 * Example 5:
 * Input: N = 4, trust = [[1,3],[1,4],[2,3],[2,4],[4,3]]
 * Output: 3
 *
 * Constraints:
 *
 * 1 <= N <= 1000
 * 0 <= trust.length <= 10^4
 * trust[i].length == 2
 * trust[i] are all different
 * trust[i][0] != trust[i][1]
 * 1 <= trust[i][0], trust[i][1] <= N
 */
@RunWith(LeetCodeRunner.class)
public class Q997_FindTheTownJudge {

    @Answer
    public int findJudge(int N, int[][] trust) {
        int[] counts = new int[N + 1];
        for (int[] pair : trust) {
            counts[pair[0]] = -N;
            counts[pair[1]]++;
        }
        for (int i = 1; i <= N; i++) {
            if (counts[i] == N - 1) {
                return i;
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, new int[][]{{1, 2}}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(3, new int[][]{{1, 3}, {2, 3}}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(3, new int[][]{{1, 3}, {2, 3}, {3, 1}}).expect(-1);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(3, new int[][]{{1, 2}, {2, 3}}).expect(-1);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(4, new int[][]{{1, 3}, {1, 4}, {2, 3}, {2, 4}, {4, 3}})
            .expect(3);

}
