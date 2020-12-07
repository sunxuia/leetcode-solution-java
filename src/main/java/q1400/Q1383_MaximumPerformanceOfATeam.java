package q1400;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1383. Maximum Performance of a Team
 * https://leetcode.com/problems/maximum-performance-of-a-team/
 *
 * There are n engineers numbered from 1 to n and two arrays: speed and efficiency, where speed[i] and efficiency[i]
 * represent the speed and efficiency for the i-th engineer respectively. Return the maximum performance of a team
 * composed of at most k engineers, since the answer can be a huge number, return this modulo 10^9 + 7.
 *
 * The performance of a team is the sum of their engineers' speeds multiplied by the minimum efficiency among their
 * engineers.
 *
 * Example 1:
 *
 * Input: n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 2
 * Output: 60
 * Explanation:
 * We have the maximum performance of the team by selecting engineer 2 (with speed=10 and efficiency=4) and engineer 5
 * (with speed=5 and efficiency=7). That is, performance = (10 + 5) * min(4, 7) = 60.
 *
 * Example 2:
 *
 * Input: n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 3
 * Output: 68
 * Explanation:
 * This is the same example as the first but k = 3. We can select engineer 1, engineer 2 and engineer 5 to get the
 * maximum performance of the team. That is, performance = (2 + 10 + 5) * min(5, 4, 7) = 68.
 *
 * Example 3:
 *
 * Input: n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 4
 * Output: 72
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 * speed.length == n
 * efficiency.length == n
 * 1 <= speed[i] <= 10^5
 * 1 <= efficiency[i] <= 10^8
 * 1 <= k <= n
 */
@RunWith(LeetCodeRunner.class)
public class Q1383_MaximumPerformanceOfATeam {

    /**
     * 根据hint 中的提示, 按照efficiency 从高到低排列, 然后遍历, 每次都取当前engineer, 则最大结果就是
     * [0, i-1] 中最大的k-1 个speed + engineer.speed, 再乘以engineer.efficiency 的结果.
     */
    @Answer
    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        Engineer[] engineers = new Engineer[n];
        for (int i = 0; i < n; i++) {
            engineers[i] = new Engineer(speed[i], efficiency[i]);
        }
        Arrays.sort(engineers, (a, b) -> b.efficiency - a.efficiency);
        long res = 0, sum = 0;
        PriorityQueue<Engineer> pq = new PriorityQueue<>(k, Comparator.comparingInt(a -> a.speed));
        for (Engineer engineer : engineers) {
            sum += engineer.speed;
            if (pq.size() == k) {
                sum -= pq.poll().speed;
            }
            pq.offer(engineer);
            res = Math.max(res, sum * engineer.efficiency);
        }
        return (int) (res % 10_0000_0007);
    }

    private static class Engineer {

        final int speed, efficiency;

        Engineer(int speed, int efficiency) {
            this.speed = speed;
            this.efficiency = efficiency;
        }

        @Override
        public String toString() {
            return speed + " * " + efficiency;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(6, new int[]{2, 10, 3, 1, 5, 8}, new int[]{5, 4, 3, 9, 7, 2}, 2)
            .expect(60);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(6, new int[]{2, 10, 3, 1, 5, 8}, new int[]{5, 4, 3, 9, 7, 2}, 3)
            .expect(68);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(6, new int[]{2, 10, 3, 1, 5, 8}, new int[]{5, 4, 3, 9, 7, 2}, 4)
            .expect(72);

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation overflow = DataExpectation.createWith(
            TestDataFileHelper.read(testDataFile, 1, int.class),
            TestDataFileHelper.read(testDataFile, 2, int[].class),
            TestDataFileHelper.read(testDataFile, 3, int[].class),
            TestDataFileHelper.read(testDataFile, 4, int.class))
            .expect(755645132);

}
