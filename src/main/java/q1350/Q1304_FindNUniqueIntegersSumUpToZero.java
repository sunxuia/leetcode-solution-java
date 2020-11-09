package q1350;

import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataAssertMethod;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1304. Find N Unique Integers Sum up to Zero
 * https://leetcode.com/problems/find-n-unique-integers-sum-up-to-zero/
 *
 * Given an integer n, return any array containing n unique integers such that they add up to 0.
 *
 * Example 1:
 *
 * Input: n = 5
 * Output: [-7,-1,1,3,4]
 * Explanation: These arrays also are accepted [-5,-1,1,2,3] , [-3,-1,2,-2,4].
 *
 * Example 2:
 *
 * Input: n = 3
 * Output: [-1,0,1]
 *
 * Example 3:
 *
 * Input: n = 1
 * Output: [0]
 *
 * Constraints:
 *
 * 1 <= n <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1304_FindNUniqueIntegersSumUpToZero {

    @Answer
    public int[] sumZero(int n) {
        int[] res = new int[n];
        for (int i = n % 2; i <= n - 2; i += 2) {
            res[i] = (i + 2) / 2;
            res[i + 1] = -(i + 2) / 2;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = createTestData(5);

    private DataExpectation createTestData(int n) {
        return DataExpectation.create(n)
                .expect(null)
                .assertMethod((DataAssertMethod<int[]>) (expect, res, originAssertMethod) -> {
                    Assert.assertEquals(n, res.length);
                    int sum = 0;
                    Set<Integer> set = new HashSet<>();
                    for (int val : res) {
                        sum += val;
                        Assert.assertTrue(set.add(val));
                    }
                    Assert.assertEquals(0, sum);
                });
    }

    @TestData
    public DataExpectation example2 = createTestData(3);

    @TestData
    public DataExpectation example3 = createTestData(1);

    @TestData
    public DataExpectation val2 = createTestData(2);

}
