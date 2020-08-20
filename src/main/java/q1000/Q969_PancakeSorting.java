package q1000;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 969. Pancake Sorting
 * https://leetcode.com/problems/pancake-sorting/
 *
 * Given an array of integers A, We need to sort the array performing a series of pancake flips.
 *
 * In one pancake flip we do the following steps:
 *
 * Choose an integer k where 0 <= k < A.length.
 * Reverse the sub-array A[0...k].
 *
 * For example, if A = [3,2,1,4] and we performed a pancake flip choosing k = 2, we reverse the sub-array [3,2,1], so A
 * = [1,2,3,4] after the pancake flip at k = 2.
 *
 * Return an array of the k-values of the pancake flips that should be performed in order to sort A. Any valid answer
 * that sorts the array within 10 * A.length flips will be judged as correct.
 *
 * Example 1:
 *
 * Input: A = [3,2,4,1]
 * Output: [4,2,4,3]
 * Explanation:
 * We perform 4 pancake flips, with k values 4, 2, 4, and 3.
 * Starting state: A = [3, 2, 4, 1]
 * After 1st flip (k = 4): A = [1, 4, 2, 3]
 * After 2nd flip (k = 2): A = [4, 1, 2, 3]
 * After 3rd flip (k = 4): A = [3, 2, 1, 4]
 * After 4th flip (k = 3): A = [1, 2, 3, 4], which is sorted.
 * Notice that we return an array of the chosen k values of the pancake flips.
 *
 * Example 2:
 *
 * Input: A = [1,2,3]
 * Output: []
 * Explanation: The input is already sorted, so there is no need to flip anything.
 * Note that other answers, such as [3, 3], would also be accepted.
 *
 * Constraints:
 *
 * 1 <= A.length <= 100
 * 1 <= A[i] <= A.length
 * All integers in A are unique (i.e. A is a permutation of the integers from 1 to A.length).
 */
@RunWith(LeetCodeRunner.class)
public class Q969_PancakeSorting {

    /**
     * 首先翻转一次, 将最大的值翻到第0 位, 然后再将最大的值翻到指定位置.
     * (这里使用队列+栈模拟数组翻转, 这样颠来颠去还不如直接修改数组的值快).
     */
    @Answer
    public List<Integer> pancakeSort(int[] A) {
        final int n = A.length;
        Queue<Integer> queue = new ArrayDeque<>(n);
        Deque<Integer> stack = new ArrayDeque<>(n);
        for (int a : A) {
            stack.push(a);
        }
        List<Integer> res = new ArrayList<>();
        for (int i = n; i > 1; i--) {
            while (!stack.isEmpty()) {
                queue.offer(stack.pop());
            }
            while (i != queue.peek()) {
                stack.push(queue.poll());
            }
            if (queue.size() > 1) {
                res.add(queue.size());
            }
            res.add(i);
            queue.poll();
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = createTestData(new int[]{3, 2, 4, 1});

    @TestData
    public DataExpectation example2 = createTestData(new int[]{1, 2, 3});

    @TestData
    public DataExpectation normal1 = createTestData(new int[]{3, 1, 2});

    @TestData
    public DataExpectation normal2 = createTestData(new int[]{1, 4, 2, 3});

    private DataExpectation createTestData(int[] args) {
        int[] raw = args.clone();
        return DataExpectation.builder()
                .addArgument(args)
                .assertMethod((List<Integer> res) -> {
                    Assert.assertTrue(res.size() <= raw.length * 10);
                    for (int len : res) {
                        for (int left = 0, right = len - 1;
                                left < right;
                                left++, right--) {
                            int t = raw[left];
                            raw[left] = raw[right];
                            raw[right] = t;
                        }
                    }
                    for (int i = 1; i < raw.length; i++) {
                        Assert.assertTrue(raw[i - 1] <= raw[i]);
                    }
                }).build();
    }

}
