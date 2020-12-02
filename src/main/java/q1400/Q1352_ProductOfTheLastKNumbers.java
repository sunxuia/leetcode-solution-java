package q1400;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * [Medium] 1352. Product of the Last K Numbers
 * https://leetcode.com/problems/product-of-the-last-k-numbers/
 *
 * Implement the class ProductOfNumbers that supports two methods:
 *
 * 1. add(int num)
 *
 * Adds the number num to the back of the current list of numbers.
 *
 * 2. getProduct(int k)
 *
 * Returns the product of the last k numbers in the current list.
 * You can assume that always the current list has at least k numbers.
 *
 * At any time, the product of any contiguous sequence of numbers will fit into a single 32-bit integer without
 * overflowing.
 *
 * Example:
 *
 * Input
 * ["ProductOfNumbers","add","add","add","add","add","getProduct","getProduct","getProduct","add","getProduct"]
 * [[],[3],[0],[2],[5],[4],[2],[3],[4],[8],[2]]
 *
 * Output
 * [null,null,null,null,null,null,20,40,0,null,32]
 *
 * Explanation
 * ProductOfNumbers productOfNumbers = new ProductOfNumbers();
 * productOfNumbers.add(3);        // [3]
 * productOfNumbers.add(0);        // [3,0]
 * productOfNumbers.add(2);        // [3,0,2]
 * productOfNumbers.add(5);        // [3,0,2,5]
 * productOfNumbers.add(4);        // [3,0,2,5,4]
 * productOfNumbers.getProduct(2); // return 20. The product of the last 2 numbers is 5 * 4 = 20
 * productOfNumbers.getProduct(3); // return 40. The product of the last 3 numbers is 2 * 5 * 4 = 40
 * productOfNumbers.getProduct(4); // return 0. The product of the last 4 numbers is 0 * 2 * 5 * 4 = 0
 * productOfNumbers.add(8);        // [3,0,2,5,4,8]
 * productOfNumbers.getProduct(2); // return 32. The product of the last 2 numbers is 4 * 8 = 32
 *
 * Constraints:
 *
 * There will be at most 40000 operations considering both add and getProduct.
 * 0 <= num <= 100
 * 1 <= k <= 40000
 */
public class Q1352_ProductOfTheLastKNumbers {

    private static class ProductOfNumbers {

        /**
         * 题目保证getProduct 的结果不会超过Integer.MAX_VALUE,
         * 因此对于之前加入的超过范围的数字可以忽略掉.
         */
        private List<Integer> curr = new ArrayList<>();

        private List<Integer> prev = new ArrayList<>();

        public ProductOfNumbers() {
            curr.add(1);
            prev.add(1);
        }

        public void add(int num) {
            if (num == 0) {
                curr.clear();
                prev.clear();
                curr.add(1);
                prev.add(1);
                return;
            }

            long product = curr.get(curr.size() - 1).longValue() * num;
            if (product <= Integer.MAX_VALUE) {
                curr.add((int) product);
                return;
            }

            // curr 中保存的数字要超过限制, 因此将curr 放到prev 中,
            // curr 从新开始保存数字, prev 中的数字被放弃掉.
            List<Integer> t = prev;
            prev = curr;
            curr = t;
            curr.clear();
            curr.add(1);
            curr.add(num);
        }

        public int getProduct(int k) {
            if (curr.size() + prev.size() - 2 < k) {
                return 0;
            }
            int product = curr.get(curr.size() - 1);
            if (curr.size() > k) {
                int divisor = curr.get(curr.size() - 1 - k);
                return product / divisor;
            } else {
                int divisor = prev.get(curr.size() + curr.size() - 2 - k);
                int dividend = prev.get(prev.size() - 1);
                return dividend / divisor * product;
            }
        }
    }

    /**
     * Your ProductOfNumbers object will be instantiated and called as such:
     * ProductOfNumbers obj = new ProductOfNumbers();
     * obj.add(num);
     * int param_2 = obj.getProduct(k);
     */

    @Test
    public void testMethod() {
        ProductOfNumbers productOfNumbers = new ProductOfNumbers();
        productOfNumbers.add(3);
        productOfNumbers.add(0);
        productOfNumbers.add(2);
        productOfNumbers.add(5);
        productOfNumbers.add(4);
        Assert.assertEquals(20, productOfNumbers.getProduct(2));
        Assert.assertEquals(40, productOfNumbers.getProduct(3));
        Assert.assertEquals(0, productOfNumbers.getProduct(4));
        productOfNumbers.add(8);
        Assert.assertEquals(32, productOfNumbers.getProduct(2));
    }

}
