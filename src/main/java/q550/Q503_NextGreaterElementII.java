package q550;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;
import org.junit.runner.RunWith;
import q500.Q496_NextGreaterElementI;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/next-greater-element-ii/
 *
 * Given a circular array (the next element of the last element is the first element of the array), print the Next
 * Greater Number for every element. The Next Greater Number of a number x is the first greater number to its
 * traversing-order next in the array, which means you could search circularly to find its next greater number. If
 * it doesn't exist, output -1 for this number.
 *
 * Example 1:
 *
 * Input: [1,2,1]
 * Output: [2,-1,2]
 * Explanation: The first 1's next greater number is 2;
 * The number 2 can't find next greater number;
 * The second 1's next greater number needs to search circularly, which is also 2.
 *
 * Note: The length of given array won't exceed 10000.
 *
 * 上一题 {@link Q496_NextGreaterElementI}
 */
@RunWith(LeetCodeRunner.class)
public class Q503_NextGreaterElementII {

    // 和上一题差不多的解法, 这个比较慢
    @Answer
    public int[] nextGreaterElements(int[] nums) {
        final int n = nums.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            int val = -1;
            for (int j = i + 1; j < n + i; j++) {
                if (nums[i] < nums[j % n]) {
                    val = nums[j % n];
                    break;
                }
            }
            res[i] = val;
        }
        return res;
    }

    /**
     * LeetCode 上比较快的解法, 核心思想是构建单调栈/队列
     */
    @Answer
    public int[] nextGreaterElements2(int[] nums) {
        final int n = nums.length;
        // fq 是从nums[0] 开始的递增的单调队列
        Deque<Integer> fq = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (fq.isEmpty() || fq.peekLast() < nums[i]) {
                fq.offerLast(nums[i]);
            }
        }

        int[] res = new int[n];
        // bs 是以 nums[n-1] 为底部从上往下是递减的单调栈.
        // 下面的循环中还要确保bs 的底部(bs 中最小的元素) 要大于 fq 队头的元素(fq 中最大的元素)
        Stack<Integer> bs = new Stack<>();
        // 从尾巴开始找, 因为fq 是从0 开始的
        for (int i = n - 1; i >= 0; i--) {
            // 移除栈顶中<= 当前元素的元素, 这样确保单调栈从上往下是递减的.
            while (!bs.isEmpty() && bs.peek() <= nums[i]) {
                bs.pop();
            }

            if (bs.isEmpty()) {
                // 从单调队列的开头中去掉 <= nums[i] 的元素.
                while (!fq.isEmpty() && fq.peekFirst() <= nums[i]) {
                    fq.pollFirst();
                }
                // 剩下的单调队列的开头就是比 nums[i] 大的元素.
                res[i] = ((fq.isEmpty()) ? -1 : fq.peekFirst());
            } else {
                res[i] = bs.peek();
            }
            bs.push(nums[i]);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{1, 2, 1}).expect(new int[]{2, -1, 2});

}
