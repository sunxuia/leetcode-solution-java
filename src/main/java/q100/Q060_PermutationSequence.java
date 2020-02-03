package q100;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/permutation-sequence/
 *
 * The set [1,2,3,...,n] contains a total of n! unique permutations.
 *
 * By listing and labeling all of the permutations in order, we get the following sequence for n = 3:
 *
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * Given n and k, return the kth permutation sequence.
 *
 * Note:
 *
 * Given n will be between 1 and 9 inclusive.
 * Given k will be between 1 and n! inclusive.
 * Example 1:
 *
 * Input: n = 3, k = 3
 * Output: "213"
 * Example 2:
 *
 * Input: n = 4, k = 9
 * Output: "2314"
 */
@RunWith(LeetCodeRunner.class)
public class Q060_PermutationSequence {

    /**
     * 根据字典在第n 位的变动来得出.
     * n 位数字的第 0 位会连续出现 n 个不同的连续值区段, 每个区段长度 (n-1)!, 第 k 次出现的值就是当前序列 k/(n-1)! 下标对应的值.
     * 第 1 位则会连续出现 n - 1 个不同的连续值区段(去掉第0 位确定的数字), 每个区段长度 (n-2)!, 第 k 次出现的值就是
     * (k%(n-1)!)/(n-2)!, k%(n-1)! 表示第 0 位确定后, 从 第 1 位开始的序列的循环的次数.
     * 比如"123" 的第0 位变动就是"112233", k = 2 时, 第 0 位就是 2/2! = 1, 对应的就是"2";
     * 剩下的数字是 "23", 此时剩下的循环次数是 2 % 2! = 0, 第 1 位的数字就是 0%1! = 0 对应的 "2", 最后剩下一个数字3.
     *
     * 注意题目中的k 是从1 开始的, 上面设的k 是从1 开始的.
     */
    @Answer
    public String getPermutation(int n, int k) {
        List<Character> candidates = new ArrayList<>(n);
        int factor = 1;
        for (int i = 1; i <= n; i++) {
            candidates.add((char) (i + '0'));
            factor *= i;
        }
        k--;
        StringBuilder sb = new StringBuilder(n);
        while (n > 0) {
            factor = factor / n;
            sb.append(candidates.remove(k / factor));
            k = k % factor;
            n--;
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(3, 3).expect("213");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(4, 9).expect("2314");
}
