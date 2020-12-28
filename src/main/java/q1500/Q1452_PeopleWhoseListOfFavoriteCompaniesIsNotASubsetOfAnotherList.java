package q1500;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1452. People Whose List of Favorite Companies Is Not a Subset of Another List
 * https://leetcode.com/problems/people-whose-list-of-favorite-companies-is-not-a-subset-of-another-list/
 *
 * Given the array favoriteCompanies where favoriteCompanies[i] is the list of favorites companies for the ith person
 * (indexed from 0).
 *
 * Return the indices of people whose list of favorite companies is not a subset of any other list of favorites
 * companies. You must return the indices in increasing order.
 *
 * Example 1:
 *
 * Input: favoriteCompanies = [["leetcode","google","facebook"],["google","microsoft"],["google","facebook"],
 * ["google"],["amazon"]]
 * Output: [0,1,4]
 * Explanation:
 * Person with index=2 has favoriteCompanies[2]=["google","facebook"] which is a subset of
 * favoriteCompanies[0]=["leetcode","google","facebook"] corresponding to the person with index 0.
 * Person with index=3 has favoriteCompanies[3]=["google"] which is a subset of favoriteCompanies[0]=["leetcode",
 * "google","facebook"]
 * and favoriteCompanies[1]=["google","microsoft"].
 * Other lists of favorite companies are not a subset of another list, therefore, the answer is [0,1,4].
 *
 * Example 2:
 *
 * Input: favoriteCompanies = [["leetcode","google","facebook"],["leetcode","amazon"],["facebook","google"]]
 * Output: [0,1]
 * Explanation: In this case favoriteCompanies[2]=["facebook","google"] is a subset of
 * favoriteCompanies[0]=["leetcode","google","facebook"], therefore, the answer is [0,1].
 *
 * Example 3:
 *
 * Input: favoriteCompanies = [["leetcode"],["google"],["facebook"],["amazon"]]
 * Output: [0,1,2,3]
 *
 * Constraints:
 *
 * 1 <= favoriteCompanies.length <= 100
 * 1 <= favoriteCompanies[i].length <= 500
 * 1 <= favoriteCompanies[i][j].length <= 20
 * All strings in favoriteCompanies[i] are distinct.
 * All lists of favorite companies are distinct, that is, If we sort alphabetically each list then favoriteCompanies[i]
 * != favoriteCompanies[j].
 * All strings consist of lowercase English letters only.
 */
@RunWith(LeetCodeRunner.class)
public class Q1452_PeopleWhoseListOfFavoriteCompaniesIsNotASubsetOfAnotherList {

    @Answer
    public List<Integer> peopleIndexes(List<List<String>> favoriteCompanies) {
        final int n = favoriteCompanies.size();
        List<Integer> res = new ArrayList<>();
        List<Set<String>> companySets = new ArrayList<>(n);
        for (List<String> companies : favoriteCompanies) {
            companySets.add(new HashSet<>(companies));
        }

        for (int i = 0; i < n; i++) {
            List<String> subset = favoriteCompanies.get(i);
            boolean issubset = false;
            for (int j = 0; j < n && !issubset; j++) {
                Set<String> superset = companySets.get(j);
                issubset = superset.size() > subset.size()
                        && superset.containsAll(subset);
            }
            if (!issubset) {
                res.add(i);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(List
            .of(List.of("leetcode", "google", "facebook"), List.of("google", "microsoft"),
                    List.of("google", "facebook"), List.of("google"), List.of("amazon")))
            .expect(List.of(0, 1, 4));

    @TestData
    public DataExpectation example2 = DataExpectation.create(List
            .of(List.of("leetcode", "google", "facebook"), List.of("leetcode", "amazon"),
                    List.of("facebook", "google")))
            .expect(List.of(0, 1));

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(List.of(List.of("leetcode"), List.of("google"), List.of("facebook"), List.of("amazon")))
            .expect(List.of(0, 1, 2, 3));

}
