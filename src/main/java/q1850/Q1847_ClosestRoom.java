package q1850;

import javax.management.Query;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Hard] 1847. Closest Room
 * https://leetcode.com/problems/closest-room/
 *
 * There is a hotel with n rooms. The rooms are represented by a 2D integer array rooms where rooms[i] = [roomIdi,
 * sizei] denotes that there is a room with room number roomIdi and size equal to sizei. Each roomIdi is guaranteed to
 * be unique.
 *
 * You are also given k queries in a 2D array queries where queries[j] = [preferredj, minSizej]. The answer to the jth
 * query is the room number id of a room such that:
 *
 * The room has a size of at least minSizej, and
 * abs(id - preferredj) is minimized, where abs(x) is the absolute value of x.
 *
 * If there is a tie in the absolute difference, then use the room with the smallest such id. If there is no such room,
 * the answer is -1.
 *
 * Return an array answer of length k where answer[j] contains the answer to the jth query.
 *
 * Example 1:
 *
 * Input: rooms = [[2,2],[1,2],[3,2]], queries = [[3,1],[3,3],[5,2]]
 * Output: [3,-1,3]
 * Explanation: The answers to the queries are as follows:
 * Query = [3,1]: Room number 3 is the closest as abs(3 - 3) = 0, and its size of 2 is at least 1. The answer is 3.
 * Query = [3,3]: There are no rooms with a size of at least 3, so the answer is -1.
 * Query = [5,2]: Room number 3 is the closest as abs(3 - 5) = 2, and its size of 2 is at least 2. The answer is 3.
 *
 * Example 2:
 *
 * Input: rooms = [[1,4],[2,3],[3,5],[4,1],[5,2]], queries = [[2,3],[2,4],[2,5]]
 * Output: [2,1,3]
 * Explanation: The answers to the queries are as follows:
 * Query = [2,3]: Room number 2 is the closest as abs(2 - 2) = 0, and its size of 3 is at least 3. The answer is 2.
 * Query = [2,4]: Room numbers 1 and 3 both have sizes of at least 4. The answer is 1 since it is smaller.
 * Query = [2,5]: Room number 3 is the only room with a size of at least 5. The answer is 3.
 *
 * Constraints:
 *
 * n == rooms.length
 * 1 <= n <= 10^5
 * k == queries.length
 * 1 <= k <= 10^4
 * 1 <= roomIdi, preferredj <= 10^7
 * 1 <= sizei, minSizej <= 10^7
 */
@RunWith(LeetCodeRunner.class)
public class Q1847_ClosestRoom {

    /**
     * 时间复杂度 O(logN)+O(longM)
     *
     * @param rooms [[roomId, size]]
     * @param queries [[preferred, minSize]]
     * @return [[roomId]]
     */
    @Answer
    public int[] closestRoom(int[][] rooms, int[][] queries) {
        final int n = rooms.length, m = queries.length;
        // 包装一下, 把对应下标放进去
        Query[] wrapped = new Query[m];
        for (int i = 0; i < m; i++) {
            wrapped[i] = new Query(i, queries[i][0], queries[i][1]);
        }
        // 按照要求的房间尺寸降序排列
        Arrays.sort(wrapped, (a, b) -> b.minSize - a.minSize);

        // 按照房间尺寸降序排列
        Arrays.sort(rooms, (a, b) -> b[1] - a[1]);

        int[] res = new int[m];
        // 保存查询到某个位置时候此时可用的房间号
        TreeSet<Integer> availableRooms = new TreeSet<>();
        int roomIndex = 0;
        for (Query query : wrapped) {
            // 把符合要求的房间都加进来
            while (roomIndex < n
                    && rooms[roomIndex][1] >= query.minSize) {
                availableRooms.add(rooms[roomIndex][0]);
                roomIndex++;
            }
            // 找左右两个房间, 然后比较
            Integer floor = availableRooms.floor(query.preferred);
            Integer higher = availableRooms.higher(query.preferred);
            Integer choose = floor;
            if (floor == null || higher != null
                    && query.preferred - floor > higher - query.preferred) {
                choose = higher;
            }
            res[query.index] = choose == null ? -1 : choose;
        }
        return res;
    }

    private static class Query {

        final int index, preferred, minSize;

        Query(int index, int preferred, int minSize) {
            this.index = index;
            this.preferred = preferred;
            this.minSize = minSize;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{2, 2}, {1, 2}, {3, 2}}, new int[][]{{3, 1}, {3, 3}, {5, 2}})
            .expect(new int[]{3, -1, 3});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{1, 4}, {2, 3}, {3, 5}, {4, 1}, {5, 2}}, new int[][]{{2, 3}, {2, 4}, {2, 5}})
            .expect(new int[]{2, 1, 3});

}
