package q1250;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1237. Find Positive Integer Solution for a Given Equation
 * https://leetcode.com/problems/find-positive-integer-solution-for-a-given-equation/
 *
 * Given a function  f(x, y) and a value z, return all positive integer pairs x and y where f(x,y) == z.
 *
 * The function is constantly increasing, i.e.:
 *
 * f(x, y) < f(x + 1, y)
 * f(x, y) < f(x, y + 1)
 *
 * The function interface is defined like this:
 *
 * interface CustomFunction {
 * public:
 * // Returns positive integer f(x, y) for any given positive integer x and y.
 * int f(int x, int y);
 * };
 *
 * For custom testing purposes you're given an integer function_id and a target z as input, where function_id represent
 * one function from an secret internal list, on the examples you'll know only two functions from the list.
 *
 * You may return the solutions in any order.
 *
 * Example 1:
 *
 * Input: function_id = 1, z = 5
 * Output: [[1,4],[2,3],[3,2],[4,1]]
 * Explanation: function_id = 1 means that f(x, y) = x + y
 *
 * Example 2:
 *
 * Input: function_id = 2, z = 5
 * Output: [[1,5],[5,1]]
 * Explanation: function_id = 2 means that f(x, y) = x * y
 *
 * Constraints:
 *
 * 1 <= function_id <= 9
 * 1 <= z <= 100
 * It's guaranteed that the solutions of f(x, y) == z will be on the range 1 <= x, y <= 1000
 * It's also guaranteed that f(x, y) will fit in 32 bit signed integer if 1 <= x, y <= 1000
 *
 * 题解:
 * 这题给出一个对象CustomFunction, 这个对象的函数f 会根据输入的x 和y 输出一个结果, f 的算法是未知的,
 * 但是 f 对x 和y 都是单调递增函数, 现在给出数字z, 要求能通过f 运算的得出z 的x 和y 的组合.
 */
@RunWith(LeetCodeRunner.class)
public class Q1237_FindPositiveIntegerSolutionForAGivenEquation {

    @Answer
    public List<List<Integer>> findSolution(CustomFunction customfunction, int z) {
        List<List<Integer>> res = new ArrayList<>();
        int xLower = findXLower(customfunction, z);
        int xUpper = findXUpper(customfunction, z, xLower);
        for (int x = xLower; x <= xUpper; x++) {
            int y = findYValue(customfunction, x, z);
            if (y >= 0) {
                res.add(Arrays.asList(x, y));
            }
        }
        return res;
    }

    private int findXLower(CustomFunction customFunction, int z) {
        int start = 1, end = 1000;
        while (start < end) {
            int mid = (start + end) / 2;
            int val = customFunction.f(mid, 1000);
            if (val < z) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }

    private int findXUpper(CustomFunction customFunction, int z, int start) {
        int end = 1000;
        while (start < end) {
            int mid = (start + end) / 2;
            int val = customFunction.f(mid, 0);
            if (val < z) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }

    private int findYValue(CustomFunction customFunction, int x, int z) {
        int start = 1, end = 1000;
        while (start < end) {
            int mid = (start + end) / 2;
            int val = customFunction.f(x, mid);
            if (val < z) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        if (customFunction.f(x, start) == z) {
            return start;
        }
        return -1;
    }

    /**
     * LeetCode 上最快的解法.
     */
    @Answer
    public List<List<Integer>> findSolution2(CustomFunction customfunction, int z) {
        List<List<Integer>> res = new ArrayList<>();
        int x = 1, y = z;
        while (x <= z && y > 0) {
            int val = customfunction.f(x, y);
            if (val > z) {
                y--;
            } else if (val < z) {
                x++;
            } else {
                res.add(Arrays.asList(x++, y--));
            }
        }
        return res;
    }


    // This is the custom function interface.
    // You should not implement it, or speculate about its implementation
    private static class CustomFunction {

        final int function_id;

        public CustomFunction(int function_id) {
            this.function_id = function_id;
        }

        // Returns f(x, y) for any given positive integers x and y.
        // Note that f(x, y) is increasing with respect to both x and y.
        // i.e. f(x, y) < f(x + 1, y), f(x, y) < f(x, y + 1)
        public int f(int x, int y) {
            switch (function_id) {
                case 1:
                    return x + y;
                case 2:
                    return x * y;
                default:
                    throw new RuntimeException("Not Implement");
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new CustomFunction(1), 5)
            .expect(Arrays.asList(Arrays.asList(1, 4), Arrays.asList(2, 3), Arrays.asList(3, 2), Arrays.asList(4, 1)))
            .unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new CustomFunction(2), 5)
            .expect(Arrays.asList(Arrays.asList(1, 5), Arrays.asList(5, 1)))
            .unOrder();

}
