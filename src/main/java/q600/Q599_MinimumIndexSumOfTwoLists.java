package q600;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/minimum-index-sum-of-two-lists/
 *
 * Suppose Andy and Doris want to choose a restaurant for dinner, and they both have a list of favorite restaurants
 * represented by strings.
 *
 * You need to help them find out their common interest with the least list index sum. If there is a choice tie
 * between answers, output all of them with no order requirement. You could assume there always exists an answer.
 *
 * Example 1:
 *
 * Input:
 * ["Shogun", "Tapioca Express", "Burger King", "KFC"]
 * ["Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"]
 * Output: ["Shogun"]
 * Explanation: The only restaurant they both like is "Shogun".
 *
 * Example 2:
 *
 * Input:
 * ["Shogun", "Tapioca Express", "Burger King", "KFC"]
 * ["KFC", "Shogun", "Burger King"]
 * Output: ["Shogun"]
 * Explanation: The restaurant they both like and have the least index sum is "Shogun" with index sum 1 (0+1).
 *
 * Note:
 *
 * 1. The length of both lists will be in the range of [1, 1000].
 * 2. The length of strings in both lists will be in the range of [1, 30].
 * 3. The index is starting from 0 to the list length minus 1.
 * 4. No duplicates in both lists.
 */
@RunWith(LeetCodeRunner.class)
public class Q599_MinimumIndexSumOfTwoLists {

    @Answer
    public String[] findRestaurant(String[] list1, String[] list2) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < list1.length; i++) {
            map.put(list1[i], i);
        }
        int sum = Integer.MAX_VALUE;
        List<String> resList = new ArrayList<>();
        for (int i = 0; i < list2.length; i++) {
            if (map.containsKey(list2[i])) {
                int curSum = map.get(list2[i]) + i;
                if (sum > curSum) {
                    sum = curSum;
                    resList.clear();
                }
                if (sum == curSum) {
                    resList.add(list2[i]);
                }
            }
        }
        return resList.toArray(new String[0]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new String[]{"Shogun", "Tapioca Express", "Burger King", "KFC"})
            .addArgument(new String[]{"Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"})
            .expect(new String[]{"Shogun"})
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new String[]{"Shogun", "Tapioca Express", "Burger King", "KFC"})
            .addArgument(new String[]{"KFC", "Shogun", "Burger King"})
            .expect(new String[]{"Shogun"})
            .unorderResult("")
            .build();

}
