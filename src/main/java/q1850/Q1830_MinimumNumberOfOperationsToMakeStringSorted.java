package q1850;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1830. Minimum Number of Operations to Make String Sorted
 * https://leetcode.com/problems/minimum-number-of-operations-to-make-string-sorted/
 *
 * You are given a string s (0-indexed). You are asked to perform the following operation on s until you get a sorted
 * string:
 *
 * 1. Find the largest index i such that 1 <= i < s.length and s[i] < s[i - 1].
 * 2. Find the largest index j such that i <= j < s.length and s[k] < s[i - 1] for all the possible values of k in the
 * range [i, j] inclusive.
 * 3. Swap the two characters at indices i - 1 and j.
 * 4. Reverse the suffix starting at index i.
 *
 * Return the number of operations needed to make the string sorted. Since the answer can be too large, return it modulo
 * 10^9 + 7.
 *
 * Example 1:
 *
 * Input: s = "cba"
 * Output: 5
 * Explanation: The simulation goes as follows:
 * Operation 1: i=2, j=2. Swap s[1] and s[2] to get s="cab", then reverse the suffix starting at 2. Now, s="cab".
 * Operation 2: i=1, j=2. Swap s[0] and s[2] to get s="bac", then reverse the suffix starting at 1. Now, s="bca".
 * Operation 3: i=2, j=2. Swap s[1] and s[2] to get s="bac", then reverse the suffix starting at 2. Now, s="bac".
 * Operation 4: i=1, j=1. Swap s[0] and s[1] to get s="abc", then reverse the suffix starting at 1. Now, s="acb".
 * Operation 5: i=2, j=2. Swap s[1] and s[2] to get s="abc", then reverse the suffix starting at 2. Now, s="abc".
 *
 * Example 2:
 *
 * Input: s = "aabaa"
 * Output: 2
 * Explanation: The simulation goes as follows:
 * Operation 1: i=3, j=4. Swap s[2] and s[4] to get s="aaaab", then reverse the substring starting at 3. Now,
 * s="aaaba".
 * Operation 2: i=4, j=4. Swap s[3] and s[4] to get s="aaaab", then reverse the substring starting at 4. Now,
 * s="aaaab".
 *
 * Constraints:
 *
 * 1 <= s.length <= 3000
 * s consists only of lowercase English letters.
 *
 * 题解: 根据hint, 题中每次对字符串s 执行的操作, 就是将其变为由当前字母组成的前一字典序的字符串.
 * 因此求最少操作次数等价于求解s 在由当前字母组成的所有排列中的字典序(从0 开始).
 */
@RunWith(LeetCodeRunner.class)
public class Q1830_MinimumNumberOfOperationsToMakeStringSorted {

    private static final int MOD = 10_0000_0007;

    /**
     * 计算字典序顺序的方式如下:
     * 比如组合aacb 的字典序是0, 那么计算caba 的字典序的方式就是计算在这个排列之前的排列数量:
     * 第1 位: 计算 c___ 之前的排列数量, c 前面有a, b 两个字符, c___ 的序号肯定是 a___和 b___ 之后,
     * 那么计算 a___ + b___ 的数量, 计算方法就是候选字符排除分别排除首位的a 和b 后剩下的字符排列组合数量的计算,
     * 比如对于 b___, 就是计算 aac 三个字符的排列组合数量(3个);
     * 第2 位: 第1 位的c 的顺序固定后, 就需要计算 ca__ 之前的排列数量, 因为c___ 的数量已经确定,
     * 则只要计算 [c___, ca__) 之间的排列组合数量, 然后加上 c___ 的数量, 就得出 ca__ 之前的排列组合数量了,
     * 因为第2 位的a 之前没有更小的数字了, 因此这个数量是0;
     * 第3 位: 计算 [ca__, cab_) 之间的排列组合数量, 也就是计算 a_ (ab两个字符) 的数量(1个);
     * 第4 位: 只剩下caba 了.
     *
     * 下面的这种解法就是上面的方式了, 不过会超时所以需要优化计算方式.
     */

//    @Answer
    public int makeStringSorted_overTime(String s) {
        final char[] cs = s.toCharArray();
        int[] counts = new int[26];
        for (char c : cs) {
            counts[c - 'a']++;
        }

        int res = 0, total = cs.length;
        for (char c : cs) {
            total--;
            for (int i = 0; i != c - 'a'; i++) {
                if (counts[i] > 0) {
                    counts[i]--;
                    int p = permutation(total, counts);
                    res = (res + p) % MOD;
                    counts[i]++;
                }
            }
            counts[c - 'a']--;
        }
        return res;
    }

    // 总共total 个元素的排列组合的数量, 这里只能使用BigDecimal, 否则都会超过精度.
    // 这里的计算可以化简为 total!/Π(counts[i]!), 以此在下面的解答中进行优化.
    private int permutation(int total, int[] counts) {
        BigDecimal res = new BigDecimal("1");
        for (int count : counts) {
            int end = total - count;
            while (total > end) {
                res = res.multiply(new BigDecimal(total--));
            }
            for (int i = 2; i <= count; i++) {
                res = res.divide(new BigDecimal(i));
            }
        }
        return res.remainder(new BigDecimal(MOD)).intValue();
    }

    /**
     * 对上面算法的优化, 从后往前算, 避免了重复计算permutation 中的时间.
     * (全部新建 BigInteger 也会超时)
     */
    @Answer
    public int makeStringSorted(String s) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        int res = 0;
        BigInteger p = BI_CACHE[1];
        int[] counts = new int[26];
        for (int i = n - 1; i >= 0; i--) {
            int idx = cs[i] - 'a';
            counts[idx]++;
            int lessChars = 0;
            for (int j = 0; j < idx; j++) {
                lessChars += counts[j];
            }
            int lessCount = p.multiply(BI_CACHE[lessChars])
                    .divide(BI_CACHE[counts[idx]])
                    .remainder(BI_MOD)
                    .intValue();
            res = (res + lessCount) % MOD;
            p = p.multiply(BI_CACHE[n - i])
                    .divide(BI_CACHE[counts[idx]]);
        }
        return res;
    }

    private static final BigInteger BI_MOD = new BigInteger(String.valueOf(MOD));

    private static final BigInteger[] BI_CACHE = new BigInteger[3001];

    static {
        for (int i = 0; i < BI_CACHE.length; i++) {
            BI_CACHE[i] = new BigInteger(String.valueOf(i));
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("cba").expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create("aabaa").expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("caab").expect(9);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("bba").expect(2);

    @TestData
    public DataExpectation normal3 = DataExpectation.create("leetcode").expect(4660);

    @TestData
    public DataExpectation normal4 = DataExpectation.create("leetcodeleetcodeleetcode").expect(982157772);

    @TestData
    public DataExpectation normal5 = DataExpectation.create("jensgfyynhtwlgnoxkkkiguizadmz").expect(612956436);

    @TestData
    public DataExpectation normal6 = DataExpectation
            .create("fbefskzvhfdclkwavtmejwmxavhrhidpiwdjjyrxqvjjkalqqjbmklwlmhjmuzrlbsyn")
            .expect(857325869);

    @TestData
    public DataExpectation normal7 = DataExpectation
            .create("mvbuibhaaeylbwvlntycbfegpwsxkxzqrppthmuibecipmuimzbeolrnbxjwkfeuikyadepanwxigievibdrxittluatoiqzlwsczjgtnxqiu")
            .expect(868399800);

    @TestData
    public DataExpectation normal8 = DataExpectation
            .create("atsotvsqxgrqxocsinteujermrpzxbmbqogtpufsbnrxluukeukyexrunsqsllrklpgfvrdjcqhspyxmxpdxawvuilax"
                    + "eihhykswuvjvridqmwvxnmokzhpsjicoxvakzhmgmlwjtvhrohmpyzwbiwhjwimqpizookpfzabddaejkhaofskpuniv"
                    + "tfivfclfmkusoqllsctfqfvhwmfmjjfaestneinqlflsqulzoszqdszoannxgsxtzrxihdvbcilwjiggtfmlvxwf")
            .expect(742386588);

    @TestData
    public DataExpectation overTime = DataExpectation.create(TestDataFileHelper.read(String.class)).expect(552344897);

}
