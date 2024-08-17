package q2100;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2076. Process Restricted Friend Requests</h3>
 * <a href="https://leetcode.com/problems/process-restricted-friend-requests/">
 * https://leetcode.com/problems/process-restricted-friend-requests/
 * </a><br/>
 *
 * <p>You are given an integer <code>n</code> indicating the number of people in a network. Each person is labeled from
 * <code>0</code> to <code>n - 1</code>.</p>
 *
 * <p>You are also given a <strong>0-indexed</strong> 2D integer array <code>restrictions</code>, where
 * <code>restrictions[i] = [x<sub>i</sub>, y<sub>i</sub>]</code> means that person <code>x<sub>i</sub></code> and
 * person
 * <code>y<sub>i</sub></code> <strong>cannot </strong>become <strong>friends</strong>,<strong> </strong>either
 * <strong>directly</strong> or <strong>indirectly</strong> through other people.</p>
 *
 * <p>Initially, no one is friends with each other. You are given a list of friend requests as a
 * <strong>0-indexed</strong> 2D integer array <code>requests</code>, where <code>requests[j] = [u<sub>j</sub>,
 * v<sub>j</sub>]</code> is a friend request between person <code>u<sub>j</sub></code> and person
 * <code>v<sub>j</sub></code>.</p>
 *
 * <p>A friend request is <strong>successful </strong>if <code>u<sub>j</sub></code> and <code>v<sub>j</sub></code> can
 * be <strong>friends</strong>. Each friend request is processed in the given order (i.e., <code>requests[j]</code>
 * occurs before <code>requests[j + 1]</code>), and upon a successful request, <code>u<sub>j</sub></code> and
 * <code>v<sub>j</sub></code> <strong>become direct friends</strong> for all future friend requests.</p>
 *
 * <p>Return <em>a <strong>boolean array</strong> </em><code>result</code>,<em> where each
 * </em><code>result[j]</code><em> is </em><code>true</code><em> if the </em><code>j<sup>th</sup></code><em> friend
 * request is <strong>successful</strong> or </em><code>false</code><em> if it is not</em>.</p>
 *
 * <p><strong>Note:</strong> If <code>u<sub>j</sub></code> and <code>v<sub>j</sub></code> are already direct friends,
 * the request is still <strong>successful</strong>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> n = 3, restrictions = [[0,1]], requests = [[0,2],[2,1]]
 * <strong>Output:</strong> [true,false]
 * <strong>Explanation:
 * </strong>Request 0: Person 0 and person 2 can be friends, so they become direct friends.
 * Request 1: Person 2 and person 1 cannot be friends since person 0 and person 1 would be indirect friends (1--2--0).
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> n = 3, restrictions = [[0,1]], requests = [[1,2],[0,2]]
 * <strong>Output:</strong> [true,false]
 * <strong>Explanation:
 * </strong>Request 0: Person 1 and person 2 can be friends, so they become direct friends.
 * Request 1: Person 0 and person 2 cannot be friends since person 0 and person 1 would be indirect friends (0--2--1).
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> n = 5, restrictions = [[0,1],[1,2],[2,3]], requests = [[0,4],[1,2],[3,1],[3,4]]
 * <strong>Output:</strong> [true,false,true,false]
 * <strong>Explanation:
 * </strong>Request 0: Person 0 and person 4 can be friends, so they become direct friends.
 * Request 1: Person 1 and person 2 cannot be friends since they are directly restricted.
 * Request 2: Person 3 and person 1 can be friends, so they become direct friends.
 * Request 3: Person 3 and person 4 cannot be friends since person 0 and person 1 would be indirect friends (0--4--3--1).
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>2 &lt;= n &lt;= 1000</code></li>
 * 	<li><code>0 &lt;= restrictions.length &lt;= 1000</code></li>
 * 	<li><code>restrictions[i].length == 2</code></li>
 * 	<li><code>0 &lt;= x<sub>i</sub>, y<sub>i</sub> &lt;= n - 1</code></li>
 * 	<li><code>x<sub>i</sub> != y<sub>i</sub></code></li>
 * 	<li><code>1 &lt;= requests.length &lt;= 1000</code></li>
 * 	<li><code>requests[j].length == 2</code></li>
 * 	<li><code>0 &lt;= u<sub>j</sub>, v<sub>j</sub> &lt;= n - 1</code></li>
 * 	<li><code>u<sub>j</sub> != v<sub>j</sub></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2076_ProcessRestrictedFriendRequests {

    // union find
    @Answer
    public boolean[] friendRequests(final int n, int[][] restrictions, int[][] requests) {
        Set<Integer>[] enemies = new Set[n];
        for (int i = 0; i < n; i++) {
            enemies[i] = new HashSet<>();
        }
        for (int[] restriction : restrictions) {
            int a = restriction[0];
            int b = restriction[1];
            enemies[a].add(b);
            enemies[b].add(a);
        }

        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }

        boolean[] res = new boolean[requests.length];
        for (int i = 0; i < requests.length; i++) {
            int ra = findRoot(parents, requests[i][0]);
            int rb = findRoot(parents, requests[i][1]);
            if (enemies[ra].contains(rb) || enemies[rb].contains(ra)) {
                continue;
            }
            if (ra != rb) {
                for (int enemy : enemies[rb]) {
                    enemies[ra].add(findRoot(parents, enemy));
                }
                parents[rb] = ra;
            }
            res[i] = true;
        }
        return res;
    }

    private int findRoot(int[] parent, int i) {
        if (parent[i] == i) {
            return i;
        }
        return parent[i] = findRoot(parent, parent[i]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(3, new int[][]{{0, 1}}, new int[][]{{0, 2}, {2, 1}})
            .expect(new boolean[]{true, false});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(3, new int[][]{{0, 1}}, new int[][]{{1, 2}, {0, 2}})
            .expect(new boolean[]{true, false});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(5, new int[][]{{0, 1}, {1, 2}, {2, 3}}, new int[][]{{0, 4}, {1, 2}, {3, 1}, {3, 4}})
            .expect(new boolean[]{true, false, true, false});

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(8,
                    new int[][]{{6, 4}, {7, 5}, {2, 6}, {1, 5}, {6, 7}, {6, 5}, {0, 3}, {5, 4}, {0, 4}, {2, 7}, {0, 2}},
                    new int[][]{{6, 3}, {0, 2}, {0, 5}, {0, 3}, {6, 4}, {2, 4}, {1, 0}, {2, 1}, {2, 5}, {6, 7}, {7, 0},
                            {3, 2}, {3, 5}, {2, 1}, {1, 6}, {7, 4}, {6, 3}, {1, 3}, {6, 5}, {3, 7}, {7, 0}, {6, 5},
                            {0, 5}, {0, 4}, {7, 5}, {7, 0}, {7, 0}, {1, 3}})
            .expect(new boolean[]{true, false, true, false, false, true, false, true, false, false, false, false, false,
                    true, false, false, true, false, false, false, false, false, true, false, false, false, false,
                    false});

}
