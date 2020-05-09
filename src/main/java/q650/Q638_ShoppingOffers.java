package q650;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/shopping-offers/
 *
 * In LeetCode Store, there are some kinds of items to sell. Each item has a price.
 *
 * However, there are some special offers, and a special offer consists of one or more different kinds of items with
 * a sale price.
 *
 * You are given the each item's price, a set of special offers, and the number we need to buy for each item. The job
 * is to output the lowest price you have to pay for exactly certain items as given, where you could make optimal use
 * of the special offers.
 *
 * Each special offer is represented in the form of an array, the last number represents the price you need to pay
 * for this special offer, other numbers represents how many specific items you could get if you buy this offer.
 *
 * You could use any of special offers as many times as you want.
 *
 * Example 1:
 *
 * Input: [2,5], [[3,0,5],[1,2,10]], [3,2]
 * Output: 14
 * Explanation:
 * There are two kinds of items, A and B. Their prices are $2 and $5 respectively.
 * In special offer 1, you can pay $5 for 3A and 0B
 * In special offer 2, you can pay $10 for 1A and 2B.
 * You need to buy 3A and 2B, so you may pay $10 for 1A and 2B (special offer #2), and $4 for 2A.
 *
 * Example 2:
 *
 * Input: [2,3,4], [[1,1,0,4],[2,2,1,9]], [1,2,1]
 * Output: 11
 * Explanation:
 * The price of A is $2, and $3 for B, $4 for C.
 * You may pay $4 for 1A and 1B, and $9 for 2A ,2B and 1C.
 * You need to buy 1A ,2B and 1C, so you may pay $4 for 1A and 1B (special offer #1), and $3 for 1B, $4 for 1C.
 * You cannot add more items, though only $9 for 2A ,2B and 1C.
 *
 * Note:
 *
 * 1. There are at most 6 kinds of items, 100 special offers.
 * 2. For each item, you need to buy at most 6 of them.
 * 3. You are not allowed to buy more items than you want, even if that would lower the overall price.
 */
@RunWith(LeetCodeRunner.class)
public class Q638_ShoppingOffers {

    // 不会做, 参考Solution 给出的解答.
    // 递归解法:
    @Answer
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        final int n = price.size();
        int res = 0;
        for (int i = 0; i < n; i++) {
            res += price.get(i) * needs.get(i);
        }
        for (List<Integer> s : special) {
            // 使用一次s 这个组合优惠
            List<Integer> clone = new ArrayList<>(needs);
            int i;
            for (i = 0; i < n; i++) {
                int diff = clone.get(i) - s.get(i);
                if (diff < 0) {
                    break;
                }
                clone.set(i, diff);
            }
            if (i == n) {
                res = Math.min(res, s.get(i) + shoppingOffers(price, special, clone));
            }
        }
        return res;
    }

    // 带缓存的递归, 这个会快点.
    @Answer
    public int shoppingOffers2(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        // 因为每次买的数量 <= 6, n <= 6, 所以可以用 int 来作为key, 替代 needs
        // (用 needs 这个List 做key 也可以, 不过速度会慢一点)
        int key = 0;
        for (int i = 0; i < price.size(); i++) {
            key += needs.get(i) << i * 3;
        }
        return recursion2(price, special, key, new HashMap<>());
    }

    private int recursion2(List<Integer> price, List<List<Integer>> special, int key, Map<Integer, Integer> cache) {
        final int n = price.size();
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            res += price.get(i) * ((key >> i * 3) & 7);
        }
        for (List<Integer> s : special) {
            int i, val = key;
            for (i = 0; i < n; i++) {
                if (((val >> i * 3) & 7) < s.get(i)) {
                    break;
                }
                val -= (s.get(i) << i * 3);
            }
            if (i == n) {
                res = Math.min(res, s.get(i) + recursion2(price, special, val, cache));
            }
        }
        cache.put(key, res);
        return res;
    }

    // 这个其实是一个背包问题(完全背包) 的高维版本(重量变为多个价格).
    // Discussion 中给出的一个解答如下, 比上面的带缓存的递归要慢.
    @Answer
    public int shoppingOffers3(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        int[] priceArray = new int[6];
        int[] needsArray = new int[6];
        for (int i = 0; i < price.size(); i++) {
            priceArray[i] = price.get(i);
            needsArray[i] = needs.get(i);
        }

        int[][][][][][] dp = new int[needsArray[0] + 1][needsArray[1] + 1][needsArray[2] + 1]
                [needsArray[3] + 1][needsArray[4] + 1][needsArray[5] + 1];

        // 不用special 时候的价格
        for (int a = 0; a <= needsArray[0]; a++) {
            for (int b = 0; b <= needsArray[1]; b++) {
                for (int c = 0; c <= needsArray[2]; c++) {
                    for (int d = 0; d <= needsArray[3]; d++) {
                        for (int e = 0; e <= needsArray[4]; e++) {
                            for (int f = 0; f <= needsArray[5]; f++) {
                                dp[a][b][c][d][e][f] = priceArray[0] * a + priceArray[1] * b
                                        + priceArray[2] * c + priceArray[3] * d
                                        + priceArray[4] * e + priceArray[5] * f;
                            }
                        }
                    }
                }
            }
        }

        // 计算使用special 时的价格
        for (List<Integer> sp : special) {
            int size = sp.size();
            int a1 = size >= 2 ? sp.get(0) : 0;
            int b1 = size >= 3 ? sp.get(1) : 0;
            int c1 = size >= 4 ? sp.get(2) : 0;
            int d1 = size >= 5 ? sp.get(3) : 0;
            int e1 = size >= 6 ? sp.get(4) : 0;
            int f1 = size >= 7 ? sp.get(5) : 0;
            for (int a = a1; a <= needsArray[0]; a++) {
                for (int b = b1; b <= needsArray[1]; b++) {
                    for (int c = c1; c <= needsArray[2]; c++) {
                        for (int d = d1; d <= needsArray[3]; d++) {
                            for (int e = e1; e <= needsArray[4]; e++) {
                                for (int f = f1; f <= needsArray[5]; f++) {
                                    dp[a][b][c][d][e][f] = Math.min(dp[a][b][c][d][e][f],
                                            dp[a - a1][b - b1][c - c1][d - d1][e - e1][f - f1] + sp.get(size - 1));
                                }
                            }
                        }
                    }
                }
            }
        }

        return dp[needsArray[0]][needsArray[1]][needsArray[2]][needsArray[3]][needsArray[4]][needsArray[5]];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(
            Arrays.asList(2, 5),
            Arrays.asList(Arrays.asList(3, 0, 5), Arrays.asList(1, 2, 10)),
            Arrays.asList(3, 2)
    ).expect(14);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(
            Arrays.asList(2, 3, 4),
            Arrays.asList(Arrays.asList(1, 1, 0, 4), Arrays.asList(2, 2, 1, 9)),
            Arrays.asList(1, 2, 1)
    ).expect(11);

}
