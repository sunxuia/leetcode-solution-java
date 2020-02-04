package q400;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-k-pairs-with-smallest-sums/
 *
 * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
 *
 * Define a pair (u,v) which consists of one element from the first array and one element from the second array.
 *
 * Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.
 *
 * Example 1:
 *
 * Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
 * Output: [[1,2],[1,4],[1,6]]
 * Explanation: The first 3 pairs are returned from the sequence:
 * [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
 *
 * Example 2:
 *
 * Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
 * Output: [1,1],[1,1]
 * Explanation: The first 2 pairs are returned from the sequence:
 * [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
 *
 * Example 3:
 *
 * Input: nums1 = [1,2], nums2 = [3], k = 3
 * Output: [1,3],[2,3]
 * Explanation: All possible pairs are returned from the sequence: [1,3],[2,3]
 */
@RunWith(LeetCodeRunner.class)
public class Q373_FindKPairsWithSmallestSums {

    // LeetCode 上也全是用优先队列做的, 大同小异
    @Answer
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        if (nums1.length == 0 || nums2.length == 0 || k == 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> res = new ArrayList<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(n -> nums1[n[0]] + nums2[n[1]]));
        pq.add(new int[]{0, 0});
        int next1 = 1;
        while (k-- > 0 && !pq.isEmpty()) {
            int[] min = pq.remove();
            res.add(Arrays.asList(nums1[min[0]], nums2[min[1]]));
            if (min[1] == 0 && next1 < nums1.length) {
                pq.add(new int[]{next1++, 0});
            }
            if (min[1] < nums2.length - 1) {
                min[1]++;
                pq.add(min);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 7, 11}, new int[]{2, 4, 6}, 3)
            .expect(new int[][]{{1, 2}, {1, 4}, {1, 6}});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 1, 2}, new int[]{1, 2, 3}, 2)
            .expect(new int[][]{{1, 1}, {1, 1}});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 2}, new int[]{3}, 3)
            .expect(new int[][]{{1, 3}, {2, 3}});

    @TestData
    public DataExpectation border1 = DataExpectation.createWith(new int[1], new int[0], 1).expect(new int[0]);

}
