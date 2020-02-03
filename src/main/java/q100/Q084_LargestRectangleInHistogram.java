package q100;

import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/largest-rectangle-in-histogram/
 *
 * Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the
 * area of largest rectangle in the histogram.
 *
 *
 *
 *
 * Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].
 *
 *
 *
 *
 * The largest rectangle is shown in the shaded area, which has area = 10 unit.
 */
@RunWith(LeetCodeRunner.class)
public class Q084_LargestRectangleInHistogram {

    // dfs, 这个比较慢.
    @Answer
    public int largestRectangleArea(int[] heights) {
        return dfs(heights, 0, heights.length - 1);
    }

    private int dfs(int[] heights, int start, int end) {
        if (start > end) {
            return 0;
        }
        // 在这里找最低高度让时间复杂度变成了 O(n^2)
        int min = start;
        for (int i = start + 1; i <= end; i++) {
            if (heights[min] > heights[i]) {
                min = i;
            }
        }
        int area = heights[min] * (end - start + 1);
        area = Math.max(area, dfs(heights, start, min - 1));
        area = Math.max(area, dfs(heights, min + 1, end));
        return area;
    }

    /**
     * 使用栈来保存高度, 可以保证时间复杂度为 O(n)
     * 使用一个栈, 并保证从栈底到栈顶的高度是从小到大的, 表示矩形的左边, 以遍历的点作为矩形的右边:
     * 1) 如果 左边 < 右边, 则说明短板在左边, 可以继续向右查找比当前矩形大的矩形 (高度不变长度更长), 所以跳过;
     * 2) 如果 左边 > 右边, 则说明短板在右边,
     * >>>> 这个右边将作为以后左边的限制条件加入栈中, 并从栈中不断出栈比这个边长的边, 因为从以后的边到这些边的高度已经被当前的边限制了.
     * >>>> 在出栈过程中, 会计算矩形的面积, 出栈的边作为矩形的右边, 和当前遍历的边的前一个边组成矩形, 因为前一个边之前也经历过这个流程,
     * >>>> 所以遍历边的前一个边>出栈边, 矩形的面积就是以出栈边为高计算出来.
     * 最后将当前边加入栈, 作为可能的矩形左边, 经过上述的步骤, 当前边肯定是要比栈中的边高的.
     */
    @Answer
    public int stack(int[] heights) {
        // 左右加入0 的边, 避免边界情况
        int[] workHeights = new int[heights.length + 2];
        System.arraycopy(heights, 0, workHeights, 1, heights.length);
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        int res = 0;
        for (int i = 0; i < workHeights.length; i++) {
            while (workHeights[stack.peek()] > workHeights[i]) {
                int height = workHeights[stack.pop()];
                res = Math.max(res, (i - stack.peek() - 1) * height);
            }
            stack.push(i);
        }
        return res;
    }

    /**
     * leetcode 上最快的解法. 找出以这个边为高度的最大矩形, 找到比这个边矮的左右边, 形成矩形.
     */
    @Answer
    public int array(int[] heights) {
        final int length = heights.length;
        if (length == 0) {
            return 0;
        }
        // 表示 heights[i] 为高的矩形的左边的前一个下标j, heights[j] < heights[i], 且j 与 i 之间的heights都 >= heights[i]
        // less 表示的就是比 heights[i] 小的最近的左边一条边.
        int[] leftLess = new int[length];
        // 表示heights[i] 为高的矩形的右边的后一个下标j, heights[i] > heights[j], 且 i 与 j 之间的heights 都 >= heights[i]
        int[] rightLess = new int[length];

        // 初始化左右边
        leftLess[0] = -1;
        for (int i = 1; i < length; i++) {
            int prev = i - 1;
            // 这种跳跃方式让这个for 循环的时间复杂度降低到了 O(n)
            while (prev >= 0 && heights[prev] >= heights[i]) {
                prev = leftLess[prev];
            }
            leftLess[i] = prev;
        }
        rightLess[length - 1] = length;
        for (int i = length - 2; i >= 0; i--) {
            int prev = i + 1;
            while (prev <= length - 1 && heights[i] <= heights[prev]) {
                prev = rightLess[prev];
            }
            rightLess[i] = prev;
        }

        int res = 0;
        for (int i = 0; i < length; i++) {
            // 矩形的宽度是: (rightBorder[i] - 1) - (leftBorder[i] + 1) + 1
            int area = heights[i] * (rightLess[i] - leftLess[i] - 1);
            res = Math.max(res, area);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(new int[]{2, 1, 5, 6, 2, 3}).expect(10);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[]{4, 2}).expect(4);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[]{5, 5, 1, 7, 1, 1, 5, 2, 7, 6}).expect(12);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(new int[]{2, 1, 2}).expect(3);

    @TestData
    public DataExpectation border1 = DataExpectation
            .create(new int[0]).expect(0);

}
