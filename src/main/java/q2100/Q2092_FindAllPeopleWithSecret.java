package q2100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2092. Find All People With Secret</h3>
 * <a href="https://leetcode.com/problems/find-all-people-with-secret/">
 * https://leetcode.com/problems/find-all-people-with-secret/
 * </a><br/>
 *
 * <p>You are given an integer <code>n</code> indicating there are <code>n</code> people numbered from <code>0</code> to
 * <code>n - 1</code>. You are also given a <strong>0-indexed</strong> 2D integer array <code>meetings</code> where
 * <code>meetings[i] = [x<sub>i</sub>, y<sub>i</sub>, time<sub>i</sub>]</code> indicates that person
 * <code>x<sub>i</sub></code> and person <code>y<sub>i</sub></code> have a meeting at <code>time<sub>i</sub></code>. A
 * person may attend <strong>multiple meetings</strong> at the same time. Finally, you are given an integer
 * <code>firstPerson</code>.</p>
 *
 * <p>Person <code>0</code> has a <strong>secret</strong> and initially shares the secret with a person
 * <code>firstPerson</code> at time <code>0</code>. This secret is then shared every time a meeting takes place with a
 * person that has the secret. More formally, for every meeting, if a person <code>x<sub>i</sub></code> has the secret
 * at <code>time<sub>i</sub></code>, then they will share the secret with person <code>y<sub>i</sub></code>, and vice
 * versa.</p>
 *
 * <p>The secrets are shared <strong>instantaneously</strong>. That is, a person may receive the secret and share it
 * with people in other meetings within the same time frame.</p>
 *
 * <p>Return <em>a list of all the people that have the secret after all the meetings have taken place. </em>You may
 * return the answer in <strong>any order</strong>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> n = 6, meetings = [[1,2,5],[2,3,8],[1,5,10]], firstPerson = 1
 * <strong>Output:</strong> [0,1,2,3,5]
 * <strong>Explanation:
 * </strong>At time 0, person 0 shares the secret with person 1.
 * At time 5, person 1 shares the secret with person 2.
 * At time 8, person 2 shares the secret with person 3.
 * At time 10, person 1 shares the secret with person 5.​​​​
 * Thus, people 0, 1, 2, 3, and 5 know the secret after all the meetings.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> n = 4, meetings = [[3,1,3],[1,2,2],[0,3,3]], firstPerson = 3
 * <strong>Output:</strong> [0,1,3]
 * <strong>Explanation:</strong>
 * At time 0, person 0 shares the secret with person 3.
 * At time 2, neither person 1 nor person 2 know the secret.
 * At time 3, person 3 shares the secret with person 0 and person 1.
 * Thus, people 0, 1, and 3 know the secret after all the meetings.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> n = 5, meetings = [[3,4,2],[1,2,1],[2,3,1]], firstPerson = 1
 * <strong>Output:</strong> [0,1,2,3,4]
 * <strong>Explanation:</strong>
 * At time 0, person 0 shares the secret with person 1.
 * At time 1, person 1 shares the secret with person 2, and person 2 shares the secret with person 3.
 * Note that person 2 can share the secret at the same time as receiving it.
 * At time 2, person 3 shares the secret with person 4.
 * Thus, people 0, 1, 2, 3, and 4 know the secret after all the meetings.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>2 &lt;= n &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= meetings.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>meetings[i].length == 3</code></li>
 * 	<li><code>0 &lt;= x<sub>i</sub>, y<sub>i </sub>&lt;= n - 1</code></li>
 * 	<li><code>x<sub>i</sub> != y<sub>i</sub></code></li>
 * 	<li><code>1 &lt;= time<sub>i</sub> &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= firstPerson &lt;= n - 1</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2092_FindAllPeopleWithSecret {

    @Answer
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }
        parents[firstPerson] = 0;

        Arrays.sort(meetings, Comparator.comparingInt(m -> m[2]));
        int start = 0;
        for (int i = 0; i < meetings.length; i++) {
            int a = findRoot(parents, meetings[i][0]);
            int b = findRoot(parents, meetings[i][1]);
            // 如果可以分享秘密, 则立刻设置分享密码
            if (a == 0 || b == 0) {
                parents[a] = parents[b] = 0;
            } else {
                // 临时分配到一个组, 如果后面在同一时刻组里有一个人知道了密码, 也会立刻共享
                parents[a] = b;
            }

            if (i == meetings.length - 1 || meetings[i][2] != meetings[i + 1][2]) {
                // 遍历这一时刻的所有组, 决定是否能分享到密码.
                for (; start <= i; start++) {
                    int c = meetings[start][0];
                    int d = meetings[start][1];
                    if (findRoot(parents, c) == 0) {
                        // 能分享秘密, 都分配到 0 这个组里
                        parents[c] = parents[d] = 0;
                    } else {
                        // 分享不到秘密, 拆开这个组
                        parents[c] = c;
                        parents[d] = d;
                    }
                }
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (findRoot(parents, i) == 0) {
                res.add(i);
            }
        }
        return res;
    }

    private int findRoot(int[] parents, int i) {
        if (parents[i] == i) {
            return i;
        }
        return parents[i] = findRoot(parents, parents[i]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(6)
            .addArgument(new int[][]{{1, 2, 5}, {2, 3, 8}, {1, 5, 10}})
            .addArgument(1)
            .expect(List.of(0, 1, 2, 3, 5))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(4)
            .addArgument(new int[][]{{3, 1, 3}, {1, 2, 2}, {0, 3, 3}})
            .addArgument(3)
            .expect(List.of(0, 1, 3))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(5)
            .addArgument(new int[][]{{3, 4, 2}, {1, 2, 1}, {2, 3, 1}})
            .addArgument(1)
            .expect(List.of(0, 1, 2, 3, 4))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(6)
            .addArgument(new int[][]{{0, 2, 1}, {1, 3, 1}, {4, 5, 1}})
            .addArgument(1)
            .expect(List.of(0, 1, 2, 3))
            .unorderResult()
            .build();

}
