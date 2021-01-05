package q1500;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1481. Least Number of Unique Integers after K Removals
 * https://leetcode.com/problems/least-number-of-unique-integers-after-k-removals/
 *
 * Given an array of integers arr and an integer k. Find the least number of unique integers after removing exactly k
 * elements.
 *
 * Example 1:
 *
 * Input: arr = [5,5,4], k = 1
 * Output: 1
 * Explanation: Remove the single 4, only 5 is left.
 *
 * Example 2:
 *
 * Input: arr = [4,3,1,1,3,3,2], k = 3
 * Output: 2
 * Explanation: Remove 4, 2 and either one of the two 1s or three 3s. 1 and 3 will be left.
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^5
 * 1 <= arr[i] <= 10^9
 * 0 <= k <= arr.length
 */
@RunWith(LeetCodeRunner.class)
public class Q1481_LeastNumberOfUniqueIntegersAfterKRemovals {

    @Answer
    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>(map.values());
        while (k > 0) {
            k -= pq.poll();
        }
        return k == 0 ? pq.size() : pq.size() + 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{5, 5, 4}, 1).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{4, 3, 1, 1, 3, 3, 2}, 3).expect(2);

}
