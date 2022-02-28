package q1800;

import java.util.Arrays;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import q900.Q853_CarFleet;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1776. Car Fleet II
 * https://leetcode.com/problems/car-fleet-ii/
 *
 * There are n cars traveling at different speeds in the same direction along a one-lane road. You are given an array
 * cars of length n, where cars[i] = [positioni, speedi] represents:
 *
 * positioni is the distance between the ith car and the beginning of the road in meters. It is guaranteed that
 * positioni < positioni+1.
 * speedi is the initial speed of the ith car in meters per second.
 *
 * For simplicity, cars can be considered as points moving along the number line. Two cars collide when they occupy the
 * same position. Once a car collides with another car, they unite and form a single car fleet. The cars in the formed
 * fleet will have the same position and the same speed, which is the initial speed of the slowest car in the fleet.
 *
 * Return an array answer, where answer[i] is the time, in seconds, at which the ith car collides with the next car, or
 * -1 if the car does not collide with the next car. Answers within 10^-5 of the actual answers are accepted.
 *
 * Example 1:
 *
 * Input: cars = [[1,2],[2,1],[4,3],[7,2]]
 * Output: [1.00000,-1.00000,3.00000,-1.00000]
 * Explanation: After exactly one second, the first car will collide with the second car, and form a car fleet with
 * speed 1 m/s. After exactly 3 seconds, the third car will collide with the fourth car, and form a car fleet with speed
 * 2 m/s.
 *
 * Example 2:
 *
 * Input: cars = [[3,4],[5,4],[6,3],[9,1]]
 * Output: [2.00000,1.00000,1.50000,-1.00000]
 *
 * Constraints:
 *
 * 1 <= cars.length <= 10^5
 * 1 <= positioni, speedi <= 10^6
 * positioni < positioni+1
 *
 * 上一题 {@link Q853_CarFleet}
 */
@RunWith(LeetCodeRunner.class)
public class Q1776_CarFleetII {

    /**
     * 使用优先队列给追尾事件排序, 时间复杂度 O(nlogn), 这个做法比较慢
     */
    @Answer
    public double[] getCollisionTimes(int[][] cars) {
        final int n = cars.length;
        // 表示汽车队和车队右边的车队编号(以最左边车的下标为编号)
        int[] fleets = new int[n];
        int[] prevFleets = new int[n];
        for (int i = 0; i < n; i++) {
            fleets[i] = i;
            prevFleets[i] = i - 1;
        }
        // 优先队列入队初始追尾事件
        PriorityQueue<Event> pq = new PriorityQueue<>();
        for (int i = 0; i < n - 1; i++) {
            if (cars[i][1] > cars[i + 1][1]) {
                pq.offer(new Event(i, i + 1, cars));
            }
        }

        double[] res = new double[n];
        Arrays.fill(res, -1);
        while (!pq.isEmpty()) {
            Event event = pq.poll();
            int from = event.from, to = event.to;
            if (res[from] == -1) {
                // 如果from 在此之前已已经追尾了, 说明该车队已经不存在, 事件是过时的
                if (findRoot(fleets, to) == to) {
                    // 如果要追尾的车队还是原来的车队, 则表示可以追尾
                    res[from] = event.time;
                    // 更新车队
                    fleets[from] = to;
                    // 更新车队右边的车队, 如果右边的车队速度高于该车队则添加追尾事件
                    prevFleets[to] = prevFleets[from];
                    if (prevFleets[to] != -1) {
                        if (cars[prevFleets[to]][1] > cars[to][1]) {
                            pq.offer(new Event(prevFleets[to], to, cars));
                        }
                    }
                } else {
                    // 如果要追尾的车队已经在此之前追尾过了, 则事件过时, 添加新的追尾事件
                    // (由于后车队车速肯定快于前车队, 因此直接添加新的追尾事件)
                    pq.offer(new Event(from, findRoot(fleets, to), cars));
                }
            }
        }
        return res;
    }

    private int findRoot(int[] parents, int i) {
        return parents[i] == i ? i : (parents[i] = findRoot(parents, parents[i]));
    }

    private static class Event implements Comparable<Event> {

        final int from, to;

        final double time;

        Event(int from, int to, int[][] cars) {
            this.from = from;
            this.to = to;
            this.time = (double) (cars[to][0] - cars[from][0]) / (cars[from][1] - cars[to][1]);
        }

        @Override
        public int compareTo(Event o) {
            return Double.compare(time, o.time);
        }

        @Override
        public String toString() {
            return String.format("%d->%d (%f)", from, to, time);
        }
    }

    /**
     * leetcode 上比较快的解法, 重点在于从后向前遍历
     */
    @Answer
    public double[] getCollisionTimes2(int[][] cars) {
        final int n = cars.length;
        double[] res = new double[n];
        // crashPos[i] 表示i 这个车会和哪个车追尾
        int[] crashPos = new int[n];
        int minSpeed = 1000000;
        for (int i = n - 1; i >= 0; i--) {
            if (cars[i][1] <= minSpeed) {
                // 如果车速<= 已知最小车速, 则不会碰撞
                minSpeed = cars[i][1];
                crashPos[i] = i;
                res[i] = -1.0;
            } else {
                // 向前查找可能会追尾的车并计算时间
                for (int j = i + 1; ; j = crashPos[j]) {
                    double time = getCrashTime(cars, i, j);
                    if (res[j] == -1 || time < res[j]) {
                        // j车不会追尾(即最小车速)或i 在j 之前追尾,
                        // 则说明会和这个车追尾
                        res[i] = time;
                        crashPos[i] = j;
                        break;
                    }
                }
            }
        }
        return res;
    }

    private double getCrashTime(int[][] cars, int left, int right) {
        int diff = cars[left][1] - cars[right][1];
        if (diff <= 0) {
            return Double.MAX_VALUE;
        }
        return (cars[right][0] - cars[left][0] * 1.0) / (diff);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{1, 2}, {2, 1}, {4, 3}, {7, 2}})
            .expect(new double[]{1.00000, -1.00000, 3.00000, -1.00000});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{3, 4}, {5, 4}, {6, 3}, {9, 1}})
            .expect(new double[]{2.00000, 1.00000, 1.50000, -1.00000});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{{6, 3}, {8, 5}, {13, 3}, {18, 2}, {19, 3}, {20, 5}})
            .expect(new double[]{12.00000, 2.50000, 5.00000, -1.00000, -1.00000, -1.00000});

}
