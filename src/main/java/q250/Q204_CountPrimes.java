package q250;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/count-primes/
 *
 * Count the number of prime numbers less than a non-negative number, n.
 *
 * Example:
 *
 * Input: 10
 * Output: 4
 * Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.
 */
@RunWith(LeetCodeRunner.class)
public class Q204_CountPrimes {

    /**
     * 判断是否素数的方式. 这种方式比较慢了.
     */
    @Answer
    public int countPrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i < n; i++) {
            if (isPrime(primes, i)) {
                primes.add(i);
            }
        }
        return primes.size();
    }

    private boolean isPrime(List<Integer> primes, int value) {
        for (int prime : primes) {
            if (value % prime == 0) {
                return false;
            }
            if (value < prime * prime) {
                return true;
            }
        }
        return true;
    }

    /**
     * LeetCode 上通过埃拉托斯特尼筛法 (标记质数的倍数) 来解决问题.
     * (https://www.cnblogs.com/grandyang/p/4462810.html)
     */
    @Answer
    public int countPrimes_SieveOfEratosthenes(int n) {
        boolean[] notPrime = new boolean[n];
        int res = 0;
        for (int i = 2; i < n; i++) {
            if (!notPrime[i]) {
                res++;
                for (int j = i; j < n; j += i) {
                    notPrime[j] = true;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(10).expect(4);

    @TestData
    public DataExpectation nIs2 = DataExpectation.create(2).expect(0);

    @TestData
    public DataExpectation nIs3 = DataExpectation.create(3).expect(1);

    @TestData
    public DataExpectation nIs20 = DataExpectation.create(20).expect(8);

    @TestData
    public DataExpectation nIs499979 = DataExpectation.create(499979).expect(41537);

}
