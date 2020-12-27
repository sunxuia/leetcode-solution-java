package q1450;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1431. Kids With the Greatest Number of Candies
 * https://leetcode.com/problems/kids-with-the-greatest-number-of-candies/
 *
 * Given the array candies and the integer extraCandies, where candies[i] represents the number of candies that the ith
 * kid has.
 *
 * For each kid check if there is a way to distribute extraCandies among the kids such that he or she can have the
 * greatest number of candies among them. Notice that multiple kids can have the greatest number of candies.
 *
 * Example 1:
 *
 * Input: candies = [2,3,5,1,3], extraCandies = 3
 * Output: [true,true,true,false,true]
 * Explanation:
 * Kid 1 has 2 candies and if he or she receives all extra candies (3) will have 5 candies --- the greatest number of
 * candies among the kids.
 * Kid 2 has 3 candies and if he or she receives at least 2 extra candies will have the greatest number of candies among
 * the kids.
 * Kid 3 has 5 candies and this is already the greatest number of candies among the kids.
 * Kid 4 has 1 candy and even if he or she receives all extra candies will only have 4 candies.
 * Kid 5 has 3 candies and if he or she receives at least 2 extra candies will have the greatest number of candies among
 * the kids.
 *
 * Example 2:
 *
 * Input: candies = [4,2,1,1,2], extraCandies = 1
 * Output: [true,false,false,false,false]
 * Explanation: There is only 1 extra candy, therefore only kid 1 will have the greatest number of candies among the
 * kids regardless of who takes the extra candy.
 *
 * Example 3:
 *
 * Input: candies = [12,1,12], extraCandies = 10
 * Output: [true,false,true]
 *
 * Constraints:
 *
 * 2 <= candies.length <= 100
 * 1 <= candies[i] <= 100
 * 1 <= extraCandies <= 50
 */
@RunWith(LeetCodeRunner.class)
public class Q1431_KidsWithTheGreatestNumberOfCandies {

    @Answer
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        List<Boolean> res = new ArrayList<>();
        int max = Arrays.stream(candies).max().orElse(0);
        for (int candy : candies) {
            res.add(candy + extraCandies >= max);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{2, 3, 5, 1, 3}, 3)
            .expect(List.of(true, true, true, false, true));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{4, 2, 1, 1, 2}, 1)
            .expect(List.of(true, false, false, false, false));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{12, 1, 12}, 10)
            .expect(List.of(true, false, true));

}
