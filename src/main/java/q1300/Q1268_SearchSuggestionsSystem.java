package q1300;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.common.json.JsonTypeWrapper;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1268. Search Suggestions System
 * https://leetcode.com/problems/search-suggestions-system/
 *
 * Given an array of strings products and a string searchWord. We want to design a system that suggests at most three
 * product names from products after each character of searchWord is typed. Suggested products should have common prefix
 * with the searchWord. If there are more than three products with a common prefix return the three lexicographically
 * minimums products.
 *
 * Return list of lists of the suggested products after each character of searchWord is typed.
 *
 * Example 1:
 *
 * Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
 * Output: [
 * ["mobile","moneypot","monitor"],
 * ["mobile","moneypot","monitor"],
 * ["mouse","mousepad"],
 * ["mouse","mousepad"],
 * ["mouse","mousepad"]
 * ]
 * Explanation: products sorted lexicographically = ["mobile","moneypot","monitor","mouse","mousepad"]
 * After typing m and mo all products match and we show user ["mobile","moneypot","monitor"]
 * After typing mou, mous and mouse the system suggests ["mouse","mousepad"]
 *
 * Example 2:
 *
 * Input: products = ["havana"], searchWord = "havana"
 * Output: [["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]
 *
 * Example 3:
 *
 * Input: products = ["bags","baggage","banner","box","cloths"], searchWord = "bags"
 * Output: [["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]
 *
 * Example 4:
 *
 * Input: products = ["havana"], searchWord = "tatiana"
 * Output: [[],[],[],[],[],[],[]]
 *
 * Constraints:
 *
 * 1 <= products.length <= 1000
 * There are no repeated elements in products.
 * 1 <= &Sigma; products[i].length <= 2 * 10^4
 * All characters of products[i] are lower-case English letters.
 * 1 <= searchWord.length <= 1000
 * All characters of searchWord are lower-case English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1268_SearchSuggestionsSystem {

    @Answer
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        int start = 0, end = products.length - 1;
        List<List<String>> res = new ArrayList<>();
        for (int i = 0; i < searchWord.length(); i++) {
            char c = searchWord.charAt(i);
            while (start <= end && (products[start].length() <= i || products[start].charAt(i) < c)) {
                start++;
            }
            while (start <= end && (products[end].length() <= i || products[end].charAt(i) > c)) {
                end--;
            }
            res.add(Arrays.asList(Arrays.copyOfRange(products, start, Math.min(start + 3, end + 1))));
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"mobile", "mouse", "moneypot", "monitor", "mousepad"}, "mouse")
            .expect(List.of(
                    List.of("mobile", "moneypot", "monitor"),
                    List.of("mobile", "moneypot", "monitor"),
                    List.of("mouse", "mousepad"),
                    List.of("mouse", "mousepad"),
                    List.of("mouse", "mousepad")));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"havana"}, "havana")
            .expect(List.of(List.of("havana"), List.of("havana"), List.of("havana"),
                    List.of("havana"), List.of("havana"), List.of("havana")));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{"bags", "baggage", "banner", "box", "cloths"}, "bags")
            .expect(List.of(List.of("baggage", "bags", "banner"), List.of("baggage", "bags", "banner"),
                    List.of("baggage", "bags"), List.of("bags")));

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new String[]{"havana"}, "tatiana")
            .expect(List.of(List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of()));

    @TestData
    public DataExpectation normal1() {
        TestDataFile testDataFile = new TestDataFile();
        return DataExpectation.createWith(
                TestDataFileHelper.read(testDataFile, 1, String[].class),
                TestDataFileHelper.read(testDataFile, 2, String.class)
        ).expect(TestDataFileHelper.read(testDataFile, 3, new JsonTypeWrapper<List<List<String>>>() {}));
    }

}
