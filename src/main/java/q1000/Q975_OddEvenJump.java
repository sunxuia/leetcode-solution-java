package q1000;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 975. Odd Even Jump
 * https://leetcode.com/problems/odd-even-jump/
 *
 * You are given an integer array A.  From some starting index, you can make a series of jumps.  The (1st, 3rd, 5th,
 * ...) jumps in the series are called odd numbered jumps, and the (2nd, 4th, 6th, ...) jumps in the series are called
 * even numbered jumps.
 *
 * You may from index i jump forward to index j (with i < j) in the following way:
 *
 * 1. During odd numbered jumps (ie. jumps 1, 3, 5, ...), you jump to the index j such that A[i] <= A[j] and A[j] is the
 * smallest possible value.  If there are multiple such indexes j, you can only jump to the smallest such index j.
 * 2. During even numbered jumps (ie. jumps 2, 4, 6, ...), you jump to the index j such that A[i] >= A[j] and A[j] is
 * the largest possible value.  If there are multiple such indexes j, you can only jump to the smallest such index j.
 * (It may be the case that for some index i, there are no legal jumps.)
 *
 * A starting index is good if, starting from that index, you can reach the end of the array (index A.length - 1) by
 * jumping some number of times (possibly 0 or more than once.)
 *
 * Return the number of good starting indexes.
 *
 * Example 1:
 *
 * Input: [10,13,12,14,15]
 * Output: 2
 * Explanation:
 * From starting index i = 0, we can jump to i = 2 (since A[2] is the smallest among A[1], A[2], A[3], A[4] that is
 * greater or equal to A[0]), then we can't jump any more.
 * From starting index i = 1 and i = 2, we can jump to i = 3, then we can't jump any more.
 * From starting index i = 3, we can jump to i = 4, so we've reached the end.
 * From starting index i = 4, we've reached the end already.
 * In total, there are 2 different starting indexes (i = 3, i = 4) where we can reach the end with some number of
 * jumps.
 *
 * Example 2:
 *
 * Input: [2,3,1,1,4]
 * Output: 3
 * Explanation:
 * From starting index i = 0, we make jumps to i = 1, i = 2, i = 3:
 *
 * During our 1st jump (odd numbered), we first jump to i = 1 because A[1] is the smallest value in (A[1], A[2], A[3],
 * A[4]) that is greater than or equal to A[0].
 *
 * During our 2nd jump (even numbered), we jump from i = 1 to i = 2 because A[2] is the largest value in (A[2], A[3],
 * A[4]) that is less than or equal to A[1].  A[3] is also the largest value, but 2 is a smaller index, so we can only
 * jump to i = 2 and not i = 3.
 *
 * During our 3rd jump (odd numbered), we jump from i = 2 to i = 3 because A[3] is the smallest value in (A[3], A[4])
 * that is greater than or equal to A[2].
 *
 * We can't jump from i = 3 to i = 4, so the starting index i = 0 is not good.
 *
 * In a similar manner, we can deduce that:
 * From starting index i = 1, we jump to i = 4, so we reach the end.
 * From starting index i = 2, we jump to i = 3, and then we can't jump anymore.
 * From starting index i = 3, we jump to i = 4, so we reach the end.
 * From starting index i = 4, we are already at the end.
 * In total, there are 3 different starting indexes (i = 1, i = 3, i = 4) where we can reach the end with some number
 * of
 * jumps.
 *
 * Example 3:
 *
 * Input: [5,1,3,4,2]
 * Output: 3
 * Explanation:
 * We can reach the end from starting indexes 1, 2, and 4.
 *
 * Note:
 *
 * 1 <= A.length <= 20000
 * 0 <= A[i] < 100000
 *
 * 题目大意:
 * 第x 次跳跃(x 从1 开始) 的跳跃规则: (假设此时在i, 将要跳到的下标是j)
 * 奇数次: j 对应 (i, n) 范围内 A[i] <= A[j] 的最小的元素, 如果有多个最小值, 则j 是其中下标最小的.
 * 偶数次: j 对应 (i, n) 范围内 A[i] >= A[j] 的最大的元素, 如果有多个最大值, 则j 是其中下标最小的.
 * 可以从任意位置出发, 题目问能到达 n-1 位置的出发位置数量(n-1 位置出发也算).
 */
@RunWith(LeetCodeRunner.class)
public class Q975_OddEvenJump {

    // LeetCode 上还有一个更简单的也是使用 TreeMap 的解法.
    @Answer
    public int oddEvenJumps(int[] A) {
        final int n = A.length;

        // 计算偶数次(prevs[0])/ 奇数次(prevs[1])能跳到当前下标的下标
        TreeMap<Integer, Integer> map = new TreeMap<>();
        List<Integer>[][] prevs = new List[2][n];
        for (int i = n - 1; i >= 0; i--) {
            Map.Entry<Integer, Integer> floor = map.floorEntry(A[i]);
            if (floor != null) {
                prevs[0][floor.getValue()].add(i);
            }
            Map.Entry<Integer, Integer> ceil = map.ceilingEntry(A[i]);
            if (ceil != null) {
                prevs[1][ceil.getValue()].add(i);
            }
            map.put(A[i], i);
            prevs[0][i] = new ArrayList<>();
            prevs[1][i] = new ArrayList<>();
        }

        // 通过队列从后往前遍历
        // 因为总是奇偶交换往前跳, 所以不需要去重
        int res = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(n - 1);
        queue.add(1);
        queue.add(n - 1);
        queue.add(0);
        while (!queue.isEmpty()) {
            // 当前跳的起跳下标
            int index = queue.poll();
            // 状态: 0 : 此跳为偶数跳, 1 : 此跳为奇数跳
            int state = queue.poll();
            res += state;
            // 将上一跳加入队列
            int prevState = (state + 1) % 2;
            for (int prev : prevs[prevState][index]) {
                queue.add(prev);
                queue.add(prevState);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{10, 13, 12, 14, 15}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 3, 1, 1, 4}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{5, 1, 3, 4, 2}).expect(3);

}
