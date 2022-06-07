package q1950;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1928. Minimum Cost to Reach Destination in Time
 * https://leetcode.com/problems/minimum-cost-to-reach-destination-in-time/
 *
 * There is a country of n cities numbered from 0 to n - 1 where all the cities are connected by bi-directional roads.
 * The roads are represented as a 2D integer array edges where edges[i] = [xi, yi, timei] denotes a road between cities
 * xi and yi that takes timei minutes to travel. There may be multiple roads of differing travel times connecting the
 * same two cities, but no road connects a city to itself.
 *
 * Each time you pass through a city, you must pay a passing fee. This is represented as a 0-indexed integer array
 * passingFees of length n where passingFees[j] is the amount of dollars you must pay when you pass through city j.
 *
 * In the beginning, you are at city 0 and want to reach city n - 1 in maxTime minutes or less. The cost of your journey
 * is the summation of passing fees for each city that you passed through at some moment of your journey (including the
 * source and destination cities).
 *
 * Given maxTime, edges, and passingFees, return the minimum cost to complete your journey, or -1 if you cannot complete
 * it within maxTime minutes.
 *
 * Example 1:
 * (图Q1928_PIC1.png)
 * Input: maxTime = 30, edges = [[0,1,10],[1,2,10],[2,5,10],[0,3,1],[3,4,10],[4,5,15]], passingFees = [5,1,2,20,20,3]
 * Output: 11
 * Explanation: The path to take is 0 -> 1 -> 2 -> 5, which takes 30 minutes and has $11 worth of passing fees.
 *
 * Example 2:
 * (图Q1928_PIC2.png)
 * Input: maxTime = 29, edges = [[0,1,10],[1,2,10],[2,5,10],[0,3,1],[3,4,10],[4,5,15]], passingFees = [5,1,2,20,20,3]
 * Output: 48
 * Explanation: The path to take is 0 -> 3 -> 4 -> 5, which takes 26 minutes and has $48 worth of passing fees.
 * You cannot take path 0 -> 1 -> 2 -> 5 since it would take too long.
 *
 * Example 3:
 *
 * Input: maxTime = 25, edges = [[0,1,10],[1,2,10],[2,5,10],[0,3,1],[3,4,10],[4,5,15]], passingFees = [5,1,2,20,20,3]
 * Output: -1
 * Explanation: There is no way to reach city 5 from city 0 within 25 minutes.
 *
 * Constraints:
 *
 * 1 <= maxTime <= 1000
 * n == passingFees.length
 * 2 <= n <= 1000
 * n - 1 <= edges.length <= 1000
 * 0 <= xi, yi <= n - 1
 * 1 <= timei <= 1000
 * 1 <= passingFees[j] <= 1000
 * The graph may contain multiple edges between two nodes.
 * The graph does not contain self loops.
 */
@RunWith(LeetCodeRunner.class)
public class Q1928_MinimumCostToReachDestinationInTime {

    @Answer
    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
        final int n = passingFees.length;
        City[] cities = new City[n];
        for (int i = 0; i < n; i++) {
            cities[i] = new City();
            cities[i].no = i;
            cities[i].passingFee = passingFees[i];
        }
        for (int[] edge : edges) {
            City a = cities[edge[0]];
            City b = cities[edge[1]];
            int costTime = edge[2];
            Integer time = a.roads.get(b);
            if (time == null || time > costTime) {
                a.roads.put(b, costTime);
                b.roads.put(a, costTime);
            }
        }

        // (迪杰斯特拉算法)计算各节点到 n-1 节点至少需要多久 (用于下面的剪枝)
        Set<City> unvisited = new HashSet<>();
        unvisited.add(cities[n - 1]);
        cities[n - 1].minRemainTime = 0;
        while (!unvisited.isEmpty()) {
            City min = null;
            for (City city : unvisited) {
                if (min == null || min.minRemainTime > city.minRemainTime) {
                    min = city;
                }
            }
            unvisited.remove(min);
            for (var pair : min.roads.entrySet()) {
                City city = pair.getKey();
                int reachTime = min.minRemainTime + pair.getValue();
                if (reachTime <= maxTime && city.minRemainTime > reachTime) {
                    city.minRemainTime = reachTime;
                    unvisited.add(city);
                }
            }
        }
        if (cities[0].minRemainTime == Integer.MAX_VALUE) {
            return -1;
        }

        // 按照费用排序, 每次都找出到每个节点的最小费用.
        PriorityQueue<Arrival> pq = new PriorityQueue<>();
        pq.offer(new Arrival(cities[0], cities[0].passingFee, 0));
        while (true) {
            Arrival arr = pq.poll();
            if (arr.city.no == n - 1) {
                return arr.fee;
            }
            for (var pair : arr.city.roads.entrySet()) {
                City city = pair.getKey();
                int time = arr.time + pair.getValue();
                int fee = arr.fee + city.passingFee;
                // 因为pq是按照费用排序的, 所以之前的费用肯定比它小,
                // 如果此时花费时间还多的话, 那么就没必要检查了.
                if (time + city.minRemainTime <= maxTime
                        && time < city.minArriveTime) {
                    city.minArriveTime = time;
                    pq.offer(new Arrival(city, fee, time));
                }
            }
        }
    }

    private static class City {

        // 编号
        int no;

        // 过路费
        int passingFee;

        // 从该节点到达节点n-1 需要花费的时间
        int minRemainTime = Integer.MAX_VALUE;

        // 从0 节点到该节点的最小到达时间
        int minArriveTime = Integer.MAX_VALUE;

        // 到其他城市的道路, value 是花费时间
        Map<City, Integer> roads = new HashMap<>();

        @Override
        public String toString() {
            return String.format("city %d", no);
        }
    }

    private static final class Arrival implements Comparable<Arrival> {

        final City city;

        final int fee, time;

        public Arrival(City city, int fee, int time) {
            this.city = city;
            this.fee = fee;
            this.time = time;
        }

        @Override
        public int compareTo(Arrival o) {
            return fee == o.fee ?
                    time - o.time : fee - o.fee;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(30, new int[][]{{0, 1, 10}, {1, 2, 10}, {2, 5, 10}, {0, 3, 1}, {3, 4, 10}, {4, 5, 15}},
                    new int[]{5, 1, 2, 20, 20, 3})
            .expect(11);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(29, new int[][]{{0, 1, 10}, {1, 2, 10}, {2, 5, 10}, {0, 3, 1}, {3, 4, 10}, {4, 5, 15}},
                    new int[]{5, 1, 2, 20, 20, 3})
            .expect(48);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(25, new int[][]{{0, 1, 10}, {1, 2, 10}, {2, 5, 10}, {0, 3, 1}, {3, 4, 10}, {4, 5, 15}},
                    new int[]{5, 1, 2, 20, 20, 3})
            .expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(10,
                    new int[][]{{0, 1, 2}, {0, 2, 1}, {2, 3, 2}, {1, 3, 3}, {3, 4, 2}, {3, 5, 3}, {4, 6, 3}, {5, 6, 4}},
                    new int[]{1, 3, 6, 1, 98, 18, 1})
            .expect(27);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(500,
                    new int[][]{{9, 7, 18}, {26, 3, 12}, {28, 45, 33}, {47, 10, 27}, {34, 18, 38}, {32, 13, 39},
                            {32, 26, 32}, {12, 0, 2}, {4, 1, 7}, {5, 3, 2}, {39, 25, 27}, {45, 10, 34}, {3, 19, 5},
                            {25, 32, 23}, {30, 10, 47}, {37, 2, 31}, {10, 32, 15}, {23, 14, 19}, {22, 6, 14},
                            {45, 39, 38}, {39, 21, 30}, {42, 17, 42}, {20, 17, 15}, {24, 0, 27}, {2, 46, 11},
                            {2, 24, 13}, {36, 22, 30}, {2, 1, 31}, {41, 35, 45}, {4, 19, 20}, {32, 27, 33}, {38, 46, 1},
                            {21, 11, 15}, {33, 41, 2}, {45, 18, 30}, {8, 33, 50}, {37, 11, 6}, {25, 17, 42},
                            {45, 39, 33}, {7, 4, 49}, {17, 42, 36}, {36, 16, 9}, {46, 25, 24}, {43, 4, 6}, {35, 13, 28},
                            {1, 28, 1}, {34, 35, 15}, {38, 1, 15}, {16, 6, 28}, {13, 0, 42}, {3, 30, 24}, {43, 27, 35},
                            {8, 0, 45}, {27, 20, 47}, {6, 16, 47}, {0, 34, 35}, {0, 35, 3}, {40, 11, 24}, {1, 0, 49},
                            {44, 20, 32}, {26, 12, 17}, {3, 2, 25}, {37, 25, 42}, {27, 1, 15}, {36, 25, 38},
                            {24, 47, 33}, {33, 28, 15}, {25, 43, 37}, {47, 31, 47}, {29, 10, 50}, {11, 1, 21},
                            {29, 3, 48}, {1, 25, 10}, {48, 17, 16}, {19, 24, 22}, {30, 7, 2}, {11, 22, 19},
                            {20, 42, 41}, {27, 3, 48}, {17, 0, 34}, {19, 14, 32}, {49, 2, 20}, {10, 3, 38}, {0, 49, 13},
                            {6, 3, 28}, {42, 23, 6}, {14, 8, 1}, {35, 16, 3}, {17, 7, 40}, {18, 7, 49}, {36, 35, 13},
                            {14, 40, 45}, {16, 33, 11}, {31, 22, 33}, {38, 15, 48}, {15, 14, 25}, {37, 13, 37},
                            {44, 32, 7}, {48, 1, 31}, {33, 12, 20}, {22, 26, 23}, {4, 10, 11}, {43, 28, 43},
                            {19, 8, 14}, {35, 31, 33}, {28, 27, 19}, {40, 11, 36}, {36, 43, 28}, {22, 21, 15}},
                    new int[]{199, 505, 107, 961, 682, 400, 304, 517, 512, 18, 334, 627, 893, 412, 922, 289, 19, 161,
                            206, 879, 336, 831, 577, 802, 139, 348, 440, 219, 273, 691, 99, 858, 389, 955, 561, 353,
                            937, 904, 858, 704, 548, 497, 787, 546, 241, 67, 743, 42, 87, 137})
            .expect(336);

}
