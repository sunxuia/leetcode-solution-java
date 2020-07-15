package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 860. Lemonade Change
 * https://leetcode.com/problems/lemonade-change/
 *
 * At a lemonade stand, each lemonade costs $5.
 *
 * Customers are standing in a queue to buy from you, and order one at a time (in the order specified by bills).
 *
 * Each customer will only buy one lemonade and pay with either a $5, $10, or $20 bill.  You must provide the correct
 * change to each customer, so that the net transaction is that the customer pays $5.
 *
 * Note that you don't have any change in hand at first.
 *
 * Return true if and only if you can provide every customer with correct change.
 *
 * Example 1:
 *
 * Input: [5,5,5,10,20]
 * Output: true
 * Explanation:
 * From the first 3 customers, we collect three $5 bills in order.
 * From the fourth customer, we collect a $10 bill and give back a $5.
 * From the fifth customer, we give a $10 bill and a $5 bill.
 * Since all customers got correct change, we output true.
 *
 * Example 2:
 *
 * Input: [5,5,10]
 * Output: true
 *
 * Example 3:
 *
 * Input: [10,10]
 * Output: false
 *
 * Example 4:
 *
 * Input: [5,5,10,10,20]
 * Output: false
 * Explanation:
 * From the first two customers in order, we collect two $5 bills.
 * For the next two customers in order, we collect a $10 bill and give back a $5 bill.
 * For the last customer, we can't give change of $15 back because we only have two $10 bills.
 * Since not every customer received correct change, the answer is false.
 *
 * Note:
 *
 * 0 <= bills.length <= 10000
 * bills[i] will be either 5, 10, or 20.
 */
@RunWith(LeetCodeRunner.class)
public class Q860_LemonadeChange {

    @Answer
    public boolean lemonadeChange(int[] bills) {
        int count5 = 0, count10 = 0;
        for (int bill : bills) {
            switch (bill) {
                case 5:
                    count5++;
                    break;
                case 10:
                    if (count5 == 0) {
                        return false;
                    }
                    count5--;
                    count10++;
                    break;
                case 20:
                    if (count10 >= 1 && count5 >= 1) {
                        count10--;
                        count5--;
                    } else if (count5 >= 3) {
                        count5 -= 3;
                    } else {
                        return false;
                    }
                    break;
                default:
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{5, 5, 5, 10, 20}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{5, 5, 10}).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{10, 10}).expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{5, 5, 10, 10, 20}).expect(false);

}
