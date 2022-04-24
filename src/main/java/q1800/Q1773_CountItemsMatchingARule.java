package q1800;

import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1773. Count Items Matching a Rule
 * https://leetcode.com/problems/count-items-matching-a-rule/
 *
 * You are given an array items, where each items[i] = [typei, colori, namei] describes the type, color, and name of the
 * ith item. You are also given a rule represented by two strings, ruleKey and ruleValue.
 *
 * The ith item is said to match the rule if one of the following is true:
 *
 * ruleKey == "type" and ruleValue == typei.
 * ruleKey == "color" and ruleValue == colori.
 * ruleKey == "name" and ruleValue == namei.
 *
 * Return the number of items that match the given rule.
 *
 * Example 1:
 *
 * Input: items = [["phone","blue","pixel"],["computer","silver","lenovo"],["phone","gold","iphone"]], ruleKey =
 * "color", ruleValue = "silver"
 * Output: 1
 * Explanation: There is only one item matching the given rule, which is ["computer","silver","lenovo"].
 *
 * Example 2:
 *
 * Input: items = [["phone","blue","pixel"],["computer","silver","phone"],["phone","gold","iphone"]], ruleKey = "type",
 * ruleValue = "phone"
 * Output: 2
 * Explanation: There are only two items matching the given rule, which are ["phone","blue","pixel"] and
 * ["phone","gold","iphone"]. Note that the item ["computer","silver","phone"] does not match.
 *
 * Constraints:
 *
 * 1 <= items.length <= 10^4
 * 1 <= typei.length, colori.length, namei.length, ruleValue.length <= 10
 * ruleKey is equal to either "type", "color", or "name".
 * All strings consist only of lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1773_CountItemsMatchingARule {

    @Answer
    public int countMatches(List<List<String>> items, String ruleKey, String ruleValue) {
        int index = "type".equals(ruleKey) ? 0 : "color".equals(ruleKey) ? 1 : 2;
        int res = 0;
        for (List<String> item : items) {
            if (ruleValue.equals(item.get(index))) {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(
                    List.of(List.of("phone", "blue", "pixel"),
                            List.of("computer", "silver", "lenovo"),
                            List.of("phone", "gold", "iphone")),
                    "color", "silver")
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(
                    List.of(List.of("phone", "blue", "pixel"),
                            List.of("computer", "silver", "phone"),
                            List.of("phone", "gold", "iphone")),
                    "type", "phone")
            .expect(2);

}