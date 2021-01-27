package q1650;

import org.junit.runner.RunWith;
import q1600.Q1595_MinimumCostToConnectTwoGroupsOfPoints;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1601. Maximum Number of Achievable Transfer Requests
 * https://leetcode.com/problems/maximum-number-of-achievable-transfer-requests/
 *
 * We have n buildings numbered from 0 to n - 1. Each building has a number of employees. It's transfer season, and some
 * employees want to change the building they reside in.
 *
 * You are given an array requests where requests[i] = [fromi, toi] represents an employee's request to transfer from
 * building fromi to building toi.
 *
 * All buildings are full, so a list of requests is achievable only if for each building, the net change in employee
 * transfers is zero. This means the number of employees leaving is equal to the number of employees moving in. For
 * example if n = 3 and two employees are leaving building 0, one is leaving building 1, and one is leaving building 2,
 * there should be two employees moving to building 0, one employee moving to building 1, and one employee moving to
 * building 2.
 *
 * Return the maximum number of achievable requests.
 *
 * Example 1:
 * <img src="./!1601_PIC1.png">
 * Input: n = 5, requests = [[0,1],[1,0],[0,1],[1,2],[2,0],[3,4]]
 * Output: 5
 * Explantion: Let's see the requests:
 * From building 0 we have employees x and y and both want to move to building 1.
 * From building 1 we have employees a and b and they want to move to buildings 2 and 0 respectively.
 * From building 2 we have employee z and they want to move to building 0.
 * From building 3 we have employee c and they want to move to building 4.
 * From building 4 we don't have any requests.
 * We can achieve the requests of users x and b by swapping their places.
 * We can achieve the requests of users y, a and z by swapping the places in the 3 buildings.
 *
 * Example 2:
 * <img src="./!1601_PIC2.png">
 * Input: n = 3, requests = [[0,0],[1,2],[2,1]]
 * Output: 3
 * Explantion: Let's see the requests:
 * From building 0 we have employee x and they want to stay in the same building 0.
 * From building 1 we have employee y and they want to move to building 2.
 * From building 2 we have employee z and they want to move to building 1.
 * We can achieve all the requests.
 *
 * Example 3:
 *
 * Input: n = 4, requests = [[0,3],[3,1],[1,2],[2,0]]
 * Output: 4
 *
 * Constraints:
 *
 * 1 <= n <= 20
 * 1 <= requests.length <= 16
 * requests[i].length == 2
 * 0 <= fromi, toi < n
 */
@RunWith(LeetCodeRunner.class)
public class Q1601_MaximumNumberOfAchievableTransferRequests {

    /**
     * 位运算暴力破解.
     * 这个和 {@link Q1595_MinimumCostToConnectTwoGroupsOfPoints} 做法比较像
     */
    @Answer
    public int maximumRequests(int n, int[][] requests) {
        final int len = requests.length;
        int res = 0;
        loop:
        for (int mask = 1; mask < (1 << len); mask++) {
            int req = 0;
            int[] counts = new int[n];
            for (int j = 0; j < len; j++) {
                if ((mask >> j & 1) == 1) {
                    counts[requests[j][0]]--;
                    counts[requests[j][1]]++;
                    req++;
                }
            }
            for (int i = 0; i < n; i++) {
                if (counts[i] != 0) {
                    continue loop;
                }
            }
            res = Math.max(res, req);
        }
        return res;
    }

    /**
     * 与上面等效的dfs 的解法, 更快些.
     */
    @Answer
    public int maximumRequests2(int n, int[][] requests) {
        dfs(requests, new int[n], 0, 0);
        return res;
    }

    private int res;

    private void dfs(int[][] requests, int[] counts, int ri, int req) {
        if (ri == requests.length) {
            if (res < req) {
                for (int count : counts) {
                    if (count != 0) {
                        return;
                    }
                }
                res = req;
            }
            return;
        }
        int from = requests[ri][0], to = requests[ri][1];
        counts[from]++;
        counts[to]--;
        dfs(requests, counts, ri + 1, req + 1);
        counts[from]--;
        counts[to]++;

        // 这种情况放在下面, 减少上面对counts 的循环
        dfs(requests, counts, ri + 1, req);
    }


    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(5, new int[][]{{0, 1}, {1, 0}, {0, 1}, {1, 2}, {2, 0}, {3, 4}})
            .expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(3, new int[][]{{0, 0}, {1, 2}, {2, 1}})
            .expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(4, new int[][]{{0, 3}, {3, 1}, {1, 2}, {2, 0}})
            .expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(3, new int[][]{{1, 2}, {1, 2}, {2, 2}, {0, 2}, {2, 1}, {1, 1}, {1, 2}})
            .expect(4);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(3, new int[][]{{1, 1}, {1, 0}, {0, 1}, {0, 0}, {0, 0}, {0, 1},
                    {0, 1}, {1, 0}, {1, 0}, {1, 1}, {0, 0}, {1, 0}})
            .expect(11);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(3, new int[][]{{1, 2}, {2, 2}, {0, 0}, {1, 1}, {0, 2}, {0, 0},
                    {2, 1}, {0, 1}, {1, 0}, {2, 2}, {0, 1}, {2, 0}, {2, 2}})
            .expect(12);

}
