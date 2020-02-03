package q350;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/top-k-frequent-elements/
 *
 * Given a non-empty array of integers, return the k most frequent elements.
 *
 * Example 1:
 *
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 *
 * Example 2:
 *
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 * Note:
 *
 * You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 * Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */
@RunWith(LeetCodeRunner.class)
public class Q347_TopKFrequentElements {

    // 时间复杂度 O(NlogN), 这个比较慢.
    @Answer
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, 1 + map.getOrDefault(num, 0));
        }
        List<Integer> res = new ArrayList<>(k + 1);
        map.forEach((num, count) -> {
            int i = 0;
            while (i < res.size() && count < map.get(res.get(i))) {
                i++;
            }
            res.add(i, num);
            if (res.size() > k) {
                res.remove(k);
            }
        });
        return res;
    }

    // 相比上一个方法在选择前n 大的时候使用优先队列, 这样会快点
    @Answer
    public List<Integer> topKFrequent2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, 1 + map.getOrDefault(num, 0));
        }
        PriorityQueue<Map.Entry<Integer, Integer>> priorityQueue =
                new PriorityQueue<>(map.size(), (a, b) -> b.getValue() - a.getValue());
        priorityQueue.addAll(map.entrySet());
        List<Integer> res = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            res.add(priorityQueue.remove().getKey());
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 1, 1, 2, 2, 3}, 2)
            .expect(new int[]{1, 2});

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1}, 1).expect(new int[]{1});

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{1, 2})
            .addArgument(2)
            .expect(new int[]{1, 2})
            .unorderResult()
            .build();

}
