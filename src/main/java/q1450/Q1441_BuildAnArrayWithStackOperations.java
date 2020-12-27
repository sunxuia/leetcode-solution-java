package q1450;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1441. Build an Array With Stack Operations
 * https://leetcode.com/problems/build-an-array-with-stack-operations/
 *
 * Given an array target and an integer n. In each iteration, you will read a number from  list = {1,2,3..., n}.
 *
 * Build the target array using the following operations:
 *
 * Push: Read a new element from the beginning list, and push it in the array.
 * Pop: delete the last element of the array.
 * If the target array is already built, stop reading more elements.
 *
 * Return the operations to build the target array. You are guaranteed that the answer is unique.
 *
 * Example 1:
 *
 * Input: target = [1,3], n = 3
 * Output: ["Push","Push","Pop","Push"]
 * Explanation:
 * Read number 1 and automatically push in the array -> [1]
 * Read number 2 and automatically push in the array then Pop it -> [1]
 * Read number 3 and automatically push in the array -> [1,3]
 *
 * Example 2:
 *
 * Input: target = [1,2,3], n = 3
 * Output: ["Push","Push","Push"]
 *
 * Example 3:
 *
 * Input: target = [1,2], n = 4
 * Output: ["Push","Push"]
 * Explanation: You only need to read the first 2 numbers and stop.
 *
 * Example 4:
 *
 * Input: target = [2,3,4], n = 4
 * Output: ["Push","Pop","Push","Push","Push"]
 *
 * Constraints:
 *
 * 1 <= target.length <= 100
 * 1 <= target[i] <= n
 * 1 <= n <= 100
 * target is strictly increasing.
 */
@RunWith(LeetCodeRunner.class)
public class Q1441_BuildAnArrayWithStackOperations {

    @Answer
    public List<String> buildArray(int[] target, int n) {
        List<String> res = new ArrayList<>();
        int num = 1;
        for (int expect : target) {
            while (num < expect) {
                res.add("Push");
                res.add("Pop");
                num++;
            }
            res.add("Push");
            num++;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 3}, 3)
            .expect(List.of("Push", "Push", "Pop", "Push"));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2, 3}, 3)
            .expect(List.of("Push", "Push", "Push"));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 2}, 4)
            .expect(List.of("Push", "Push"));

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{2, 3, 4}, 4)
            .expect(List.of("Push", "Pop", "Push", "Push", "Push"));

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{2, 3, 4, 5, 8, 9, 10}, 10)
            .expect(List.of("Push", "Pop", "Push", "Push", "Push", "Push",
                    "Push", "Pop", "Push", "Pop", "Push", "Push", "Push"));

}
