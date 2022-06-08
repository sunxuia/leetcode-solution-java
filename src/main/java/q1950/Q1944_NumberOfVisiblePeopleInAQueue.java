package q1950;

import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1944. Number of Visible People in a Queue
 * https://leetcode.com/problems/number-of-visible-people-in-a-queue/
 *
 * There are n people standing in a queue, and they numbered from 0 to n - 1 in left to right order. You are given an
 * array heights of distinct integers where heights[i] represents the height of the ith person.
 *
 * A person can see another person to their right in the queue if everybody in between is shorter than both of them.
 * More formally, the ith person can see the jth person if i < j and min(heights[i], heights[j]) > max(heights[i+1],
 * heights[i+2], ..., heights[j-1]).
 *
 * Return an array answer of length n where answer[i] is the number of people the ith person can see to their right in
 * the queue.
 *
 * Example 1:
 * (图Q1944_PIC.png)
 * Input: heights = [10,6,8,5,11,9]
 * Output: [3,1,2,1,1,0]
 * Explanation:
 * Person 0 can see person 1, 2, and 4.
 * Person 1 can see person 2.
 * Person 2 can see person 3 and 4.
 * Person 3 can see person 4.
 * Person 4 can see person 5.
 * Person 5 can see no one since nobody is to the right of them.
 *
 * Example 2:
 *
 * Input: heights = [5,1,2,3,10]
 * Output: [4,1,1,1,0]
 *
 * Constraints:
 *
 * n == heights.length
 * 1 <= n <= 10^5
 * 1 <= heights[i] <= 10^5
 * All the values of heights are unique.
 */
@RunWith(LeetCodeRunner.class)
public class Q1944_NumberOfVisiblePeopleInAQueue {

    /**
     * 简单的解法, 时间复杂度 O(N^2)
     */
    @Answer
    public int[] canSeePersonsCount2(int[] heights) {
        final int n = heights.length;
        int[] res = new int[n];
        Stack<Integer> minStack = new Stack<>();
        minStack.push(heights[n - 1]);
        for (int i = n - 2; i >= 0; i--) {
            while (!minStack.isEmpty() && heights[i] >= minStack.peek()) {
                res[i]++;
                minStack.pop();
            }
            if (!minStack.isEmpty()) {
                res[i]++;
            }
            minStack.push(heights[i]);
        }
        return res;
    }

    /**
     * 用数组替换 Stack, 可以将时间复杂度降低到 O(NlogN)
     */
    @Answer
    public int[] canSeePersonsCount(int[] heights) {
        final int n = heights.length;
        int[] res = new int[n];
        int[] stack = new int[n];
        int stackTop = n;
        for (int i = n - 2; i >= 0; i--) {
            stack[--stackTop] = heights[i + 1];
            if (heights[i] >= stack[n - 1]) {
                res[i] = n - stackTop;
                stackTop = n;
            } else {
                // 二分查找找出大于heights[i] 的最小元素下标
                int start = stackTop, end = n - 1;
                while (start < end) {
                    int mid = (start + end) / 2;
                    if (stack[mid] <= heights[i]) {
                        start = mid + 1;
                    } else {
                        end = mid;
                    }
                }
                // +1 是因为这个比heights[i] 高的最矮元素也能被看到
                res[i] = start - stackTop + 1;
                stackTop = start;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{10, 6, 8, 5, 11, 9})
            .expect(new int[]{3, 1, 2, 1, 1, 0});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{5, 1, 2, 3, 10})
            .expect(new int[]{4, 1, 1, 1, 0});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[]{4, 3, 2, 1})
            .expect(new int[]{1, 1, 1, 0});

}
