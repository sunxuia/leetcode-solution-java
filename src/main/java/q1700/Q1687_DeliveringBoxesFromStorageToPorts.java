package q1700;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1687. Delivering Boxes from Storage to Ports
 * https://leetcode.com/problems/delivering-boxes-from-storage-to-ports/
 *
 * You have the task of delivering some boxes from storage to their ports using only one ship. However, this ship has a
 * limit on the number of boxes and the total weight that it can carry.
 *
 * You are given an array boxes, where boxes[i] = [portsi, weighti], and three integers portsCount, maxBoxes, and
 * maxWeight.
 *
 * - portsi is the port where you need to deliver the ith box and weightsi is the weight of the ith box.
 * - portsCount is the number of ports.
 * - maxBoxes and maxWeight are the respective box and weight limits of the ship.
 *
 * The boxes need to be delivered in the order they are given. The ship will follow these steps:
 *
 * - The ship will take some number of boxes from the boxes queue, not violating the maxBoxes and maxWeight
 * constraints.
 * - For each loaded box in order, the ship will make a trip to the port the box needs to be delivered to and deliver
 * it. If the ship is already at the correct port, no trip is needed, and the box can immediately be delivered.
 * - The ship then makes a return trip to storage to take more boxes from the queue.
 *
 * The ship must end at storage after all the boxes have been delivered.
 *
 * Return the minimum number of trips the ship needs to make to deliver all boxes to their respective ports.
 *
 * Example 1:
 *
 * Input: boxes = [[1,1],[2,1],[1,1]], portsCount = 2, maxBoxes = 3, maxWeight = 3
 * Output: 4
 * Explanation: The optimal strategy is as follows:
 * - The ship takes all the boxes in the queue, goes to port 1, then port 2, then port 1 again, then returns to
 * storage.
 * 4 trips.
 * So the total number of trips is 4.
 * Note that the first and third boxes cannot be delivered together because the boxes need to be delivered in order
 * (i.e. the second box needs to be delivered at port 2 before the third box).
 *
 * Example 2:
 *
 * Input: boxes = [[1,2],[3,3],[3,1],[3,1],[2,4]], portsCount = 3, maxBoxes = 3, maxWeight = 6
 * Output: 6
 * Explanation: The optimal strategy is as follows:
 * - The ship takes the first box, goes to port 1, then returns to storage. 2 trips.
 * - The ship takes the second, third and fourth boxes, goes to port 3, then returns to storage. 2 trips.
 * - The ship takes the fifth box, goes to port 3, then returns to storage. 2 trips.
 * So the total number of trips is 2 + 2 + 2 = 6.
 *
 * Example 3:
 *
 * Input: boxes = [[1,4],[1,2],[2,1],[2,1],[3,2],[3,4]], portsCount = 3, maxBoxes = 6, maxWeight = 7
 * Output: 6
 * Explanation: The optimal strategy is as follows:
 * - The ship takes the first and second boxes, goes to port 1, then returns to storage. 2 trips.
 * - The ship takes the third and fourth boxes, goes to port 2, then returns to storage. 2 trips.
 * - The ship takes the fifth and sixth boxes, goes to port 3, then returns to storage. 2 trips.
 * So the total number of trips is 2 + 2 + 2 = 6.
 *
 * Example 4:
 *
 * Input: boxes = [[2,4],[2,5],[3,1],[3,2],[3,7],[3,1],[4,4],[1,3],[5,2]], portsCount = 5, maxBoxes = 5, maxWeight = 7
 * Output: 14
 * Explanation: The optimal strategy is as follows:
 * - The ship takes the first box, goes to port 2, then storage. 2 trips.
 * - The ship takes the second box, goes to port 2, then storage. 2 trips.
 * - The ship takes the third and fourth boxes, goes to port 3, then storage. 2 trips.
 * - The ship takes the fifth box, goes to port 3, then storage. 2 trips.
 * - The ship takes the sixth and seventh boxes, goes to port 3, then port 4, then storage. 3 trips.
 * - The ship takes the eighth and ninth boxes, goes to port 1, then port 5, then storage. 3 trips.
 * So the total number of trips is 2 + 2 + 2 + 2 + 3 + 3 = 14.
 *
 * Constraints:
 *
 * 1 <= boxes.length <= 10^5
 * 1 <= portsCount, maxBoxes, maxWeight <= 10^5
 * 1 <= portsi <= portsCount
 * 1 <= weightsi <= maxWeight
 */
@RunWith(LeetCodeRunner.class)
public class Q1687_DeliveringBoxesFromStorageToPorts {

    /**
     * 会超时的dp 解法, 时间复杂度 O(N^2)
     */
//    @Answer
    public int boxDelivering(int[][] boxes, int portsCount, int maxBoxes, int maxWeight) {
        final int n = boxes.length;
        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            dp[i + 1] = Integer.MAX_VALUE;
            // 向前遍历, 找到最小的路程
            int path = 1, prevPort = -1, weight = 0;
            for (int j = i; j >= 0 && j > i - maxBoxes; j--) {
                int port = boxes[j][0];
                weight += boxes[j][1];
                if (weight > maxWeight) {
                    break;
                }
                if (port != prevPort) {
                    path++;
                    prevPort = port;
                }
                dp[i + 1] = Math.min(dp[i + 1], path + dp[j]);
            }
        }
        return dp[n];
    }

    /**
     * 根据上面优化而来的解答, 使用递增队列, 时间复杂度 O(N)
     */
    @Answer
    public int boxDelivering2(int[][] boxes, int portsCount, int maxBoxes, int maxWeight) {
        final int n = boxes.length;
        int[] weights = new int[n + 1];
        for (int i = 0; i < n; i++) {
            weights[i + 1] = weights[i] + boxes[i][1];
        }
        int[] ports = new int[n];
        for (int i = n - 2; i >= 0; i--) {
            ports[i] = ports[i + 1] + (boxes[i][0] == boxes[i + 1][0] ? 0 : 1);
        }

        int[] dp = new int[n + 1];
        // 递增的双端队列
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // 删除超过最大箱子/ 重量限制的前面的箱子
            while (!queue.isEmpty()) {
                int first = queue.peekFirst();
                if (i - first >= maxBoxes) {
                    queue.pollFirst();
                    continue;
                }
                if (weights[i + 1] - weights[first] > maxWeight) {
                    queue.pollFirst();
                    continue;
                }
                break;
            }
            if (queue.isEmpty()) {
                dp[i + 1] = dp[i] + 2;
            } else {
                int first = queue.peekFirst();
                dp[i + 1] = Math.min(dp[i] + 2, dp[first] + ports[first] - ports[i] + 2);
            }
            // 删除队列后面比 i 大的元素
            while (!queue.isEmpty()) {
                int last = queue.peekLast();
                if (dp[last] + ports[last] >= dp[i] + ports[i]) {
                    queue.pollLast();
                    continue;
                }
                break;
            }
            queue.offerLast(i);
        }
        return dp[n];
    }

    /**
     * LeetCode 上比较快的解法, 滑动窗口的做法, 时间复杂度 O(N)
     */
    @Answer
    public int boxDelivering3(int[][] boxes, int portsCount, int maxBoxes, int maxWeight) {
        final int n = boxes.length;
        int[] dp = new int[n + 1];
        // 表示汽车在一次运输中需要走的路
        int trips = 2;
        // 当前一次运送的盒子数和总重
        int box = 0, weight = 0;
        // 从前往后遍历向右扩大窗口
        for (int left = 0, right = 0; right < n; right++) {
            box++;
            weight += boxes[right][1];
            // 和上次不是同一个港口则路程 +1
            if (right > 0 && boxes[right][0] != boxes[right - 1][0]) {
                trips++;
            }

            // 左边缩小窗口
            while (box > maxBoxes || weight > maxWeight) {
                box--;
                weight -= boxes[left][1];
                if (boxes[left][0] != boxes[left + 1][0]) {
                    trips--;
                }
                left++;
            }

            // 如果可以在同一个港口内同时卸货则尽量让它们同时卸货
            while (left < right && dp[left] == dp[left + 1]) {
                box--;
                weight -= boxes[left][1];
                if (boxes[left][0] != boxes[left + 1][0]) {
                    trips--;
                }
                left++;
            }
            dp[right + 1] = dp[left] + trips;
        }
        return dp[n];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{1, 1}, {2, 1}, {1, 1}}, 2, 3, 3)
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{1, 2}, {3, 3}, {3, 1}, {3, 1}, {2, 4}}, 3, 3, 6)
            .expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{{1, 4}, {1, 2}, {2, 1}, {2, 1}, {3, 2}, {3, 4}}, 3, 6, 7)
            .expect(6);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[][]{{2, 4}, {2, 5}, {3, 1}, {3, 2}, {3, 7}, {3, 1}, {4, 4}, {1, 3}, {5, 2}}, 5, 5, 7)
            .expect(14);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[][]{{1, 4}, {1, 2}, {2, 1}, {2, 1}, {3, 2}}, 3, 6, 7)
            .expect(5);

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(TestDataFileHelper.read(testDataFile, 1, int[][].class), 64, 125, 42502)
            .expect(431);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.read(testDataFile, 2, int[][].class), 100000, 60000, 100000)
            .expect(18455);

}
