package q1300;

import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1276. Number of Burgers with No Waste of Ingredients
 * https://leetcode.com/problems/number-of-burgers-with-no-waste-of-ingredients/
 *
 * Given two integers tomatoSlices and cheeseSlices. The ingredients of different burgers are as follows:
 *
 * Jumbo Burger: 4 tomato slices and 1 cheese slice.
 * Small Burger: 2 Tomato slices and 1 cheese slice.
 *
 * Return [total_jumbo, total_small] so that the number of remaining tomatoSlices equal to 0 and the number of remaining
 * cheeseSlices equal to 0. If it is not possible to make the remaining tomatoSlices and cheeseSlices equal to 0 return
 * [].
 *
 * Example 1:
 *
 * Input: tomatoSlices = 16, cheeseSlices = 7
 * Output: [1,6]
 * Explantion: To make one jumbo burger and 6 small burgers we need 4*1 + 2*6 = 16 tomato and 1 + 6 = 7 cheese. There
 * will be no remaining ingredients.
 *
 * Example 2:
 *
 * Input: tomatoSlices = 17, cheeseSlices = 4
 * Output: []
 * Explantion: There will be no way to use all ingredients to make small and jumbo burgers.
 *
 * Example 3:
 *
 * Input: tomatoSlices = 4, cheeseSlices = 17
 * Output: []
 * Explantion: Making 1 jumbo burger there will be 16 cheese remaining and making 2 small burgers there will be 15
 * cheese remaining.
 *
 * Example 4:
 *
 * Input: tomatoSlices = 0, cheeseSlices = 0
 * Output: [0,0]
 *
 * Example 5:
 *
 * Input: tomatoSlices = 2, cheeseSlices = 1
 * Output: [0,1]
 *
 * Constraints:
 *
 * 0 <= tomatoSlices <= 10^7
 * 0 <= cheeseSlices <= 10^7
 */
@RunWith(LeetCodeRunner.class)
public class Q1276_NumberOfBurgersWithNoWasteOfIngredients {

    /**
     * 求二元一次方程, 设Jumbo 的数量为x, Small 的数量为y, 则可得如下方程:
     * 4x + 2y = tomatoSlices
     * x + y = cheeseSlices
     * 由此得
     * x = tomatoSlices/2 - cheeseSlices
     * y = 2*cheeseSlices - tomatoSlices/2
     */
    @Answer
    public List<Integer> numOfBurgers(int tomatoSlices, int cheeseSlices) {
        int x = tomatoSlices / 2 - cheeseSlices;
        int y = 2 * cheeseSlices - tomatoSlices / 2;
        if (tomatoSlices % 2 == 1 || x < 0 || y < 0) {
            return List.of();
        }
        return List.of(x, y);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(16, 7).expect(List.of(1, 6));

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(17, 4).expect(List.of());

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(4, 17).expect(List.of());

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(0, 0).expect(List.of(0, 0));

    @TestData
    public DataExpectation example5 = DataExpectation.createWith(2, 1).expect(List.of(0, 1));

}
