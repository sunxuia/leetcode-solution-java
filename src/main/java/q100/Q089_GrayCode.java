package q100;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.DataExpectationBuilder;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/gray-code/
 *
 * The gray code is a binary numeral system where two successive values differ in only one bit.
 *
 * Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code.
 * A gray code sequence must begin with 0.
 *
 * Example 1:
 *
 * Input: 2
 * Output: [0,1,3,2]
 * Explanation:
 * 00 - 0
 * 01 - 1
 * 11 - 3
 * 10 - 2
 *
 * For a given n, a gray code sequence may not be uniquely defined.
 * For example, [0,2,3,1] is also a valid gray code sequence.
 *
 * 00 - 0
 * 10 - 2
 * 11 - 3
 * 01 - 1
 *
 * Example 2:
 *
 * Input: 0
 * Output: [0]
 * Explanation: We define the gray code sequence to begin with 0.
 * A gray code sequence of n has size = 2n, which for n = 0 the size is 20 = 1.
 * Therefore, for n = 0 the gray code sequence is [0].
 *
 * 题解: 求gray 码的生成序列, 从0开始, 序列的前后2 个数组都只有1 个元素不同.
 */
@RunWith(LeetCodeRunner.class)
public class Q089_GrayCode {

    @Answer
    public List<Integer> grayCode(int n) {
        int[] nums = new int[n];
        List<Integer> res = new ArrayList<>((int) Math.pow(2, n));
        dfs(res, nums, 0);
        return res;
    }

    private void dfs(List<Integer> res, int[] nums, int i) {
        if (i == nums.length) {
            int num = 0;
            for (int n : nums) {
                num = num * 2 + n;
            }
            res.add(num);
            return;
        }
        dfs(res, nums, i + 1);
        nums[i] = 1 - nums[i];
        dfs(res, nums, i + 1);
    }

    /**
     * LeetCode 上针对上面过程的改进, 采用数字来保存变量, 采用异或来翻转位, 采用循环来替代dfs
     */
    @Answer
    public List<Integer> leetCode(int n) {
        List<Integer> res = new ArrayList<>((int) Math.pow(2, n));
        res.add(0);
        for (int i = 1; i <= n; i++) {
            for (int j = res.size() - 1; j >= 0; j--) {
                res.add(res.get(j) | (1 << (i - 1)));
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(2)
            .expect(asList(asList(0, 1, 3, 2), asList(0, 2, 3, 1)))
            .assertMethod(DataExpectationBuilder.orExpectAssertMethod)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(0)
            .expect(asList(0))
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(3)
            .expect(asList(0, 1, 3, 2, 6, 7, 5, 4))
            .build();

}
