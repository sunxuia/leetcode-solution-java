package q1950;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1942. The Number of the Smallest Unoccupied Chair
 * https://leetcode.com/problems/the-number-of-the-smallest-unoccupied-chair/
 *
 * There is a party where n friends numbered from 0 to n - 1 are attending. There is an infinite number of chairs in
 * this party that are numbered from 0 to infinity. When a friend arrives at the party, they sit on the unoccupied chair
 * with the smallest number.
 *
 * For example, if chairs 0, 1, and 5 are occupied when a friend comes, they will sit on chair number 2.
 *
 * When a friend leaves the party, their chair becomes unoccupied at the moment they leave. If another friend arrives at
 * that same moment, they can sit in that chair.
 *
 * You are given a 0-indexed 2D integer array times where times[i] = [arrivali, leavingi], indicating the arrival and
 * leaving times of the ith friend respectively, and an integer targetFriend. All arrival times are distinct.
 *
 * Return the chair number that the friend numbered targetFriend will sit on.
 *
 * Example 1:
 *
 * Input: times = [[1,4],[2,3],[4,6]], targetFriend = 1
 * Output: 1
 * Explanation:
 * - Friend 0 arrives at time 1 and sits on chair 0.
 * - Friend 1 arrives at time 2 and sits on chair 1.
 * - Friend 1 leaves at time 3 and chair 1 becomes empty.
 * - Friend 0 leaves at time 4 and chair 0 becomes empty.
 * - Friend 2 arrives at time 4 and sits on chair 0.
 * Since friend 1 sat on chair 1, we return 1.
 *
 * Example 2:
 *
 * Input: times = [[3,10],[1,5],[2,6]], targetFriend = 0
 * Output: 2
 * Explanation:
 * - Friend 1 arrives at time 1 and sits on chair 0.
 * - Friend 2 arrives at time 2 and sits on chair 1.
 * - Friend 0 arrives at time 3 and sits on chair 2.
 * - Friend 1 leaves at time 5 and chair 0 becomes empty.
 * - Friend 2 leaves at time 6 and chair 1 becomes empty.
 * - Friend 0 leaves at time 10 and chair 2 becomes empty.
 * Since friend 0 sat on chair 2, we return 2.
 *
 * Constraints:
 *
 * n == times.length
 * 2 <= n <= 10^4
 * times[i].length == 2
 * 1 <= arrivali < leavingi <= 10^5
 * 0 <= targetFriend <= n - 1
 * Each arrivali time is distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1942_TheNumberOfTheSmallestUnoccupiedChair {

    @Answer
    public int smallestChair(int[][] times, int targetFriend) {
        final int n = times.length;
        Friend[] friends = new Friend[n];
        for (int i = 0; i < n; i++) {
            friends[i] = new Friend(i, times[i][0], times[i][1]);
        }
        Arrays.sort(friends, Comparator.comparing(f -> f.arrival));

        PriorityQueue<Friend> queue = new PriorityQueue<>(Comparator.comparing(f -> f.leaving));
        PriorityQueue<Integer> emptyChairs = new PriorityQueue<>();
        int unreservedChair = 0;
        for (Friend friend : friends) {
            while (!queue.isEmpty()
                    && queue.peek().leaving <= friend.arrival) {
                emptyChairs.add(queue.poll().chair);
            }
            if (emptyChairs.isEmpty()) {
                friend.chair = unreservedChair++;
            } else {
                friend.chair = emptyChairs.poll();
            }
            if (friend.no == targetFriend) {
                return friend.chair;
            }
            queue.offer(friend);
        }
        return -1;
    }

    private static final class Friend {

        final int no, arrival, leaving;

        int chair;

        Friend(int no, int arrival, int leaving) {
            this.no = no;
            this.arrival = arrival;
            this.leaving = leaving;
        }

        @Override
        public String toString() {
            return String.format("%d(%d)", no, chair);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{1, 4}, {2, 3}, {4, 6}}, 1)
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{3, 10}, {1, 5}, {2, 6}}, 0)
            .expect(2);

}
