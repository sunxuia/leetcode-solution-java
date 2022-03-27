package q1850;

import java.util.stream.Stream;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1805. Number of Different Integers in a String
 * https://leetcode.com/problems/number-of-different-integers-in-a-string/
 *
 * You are given a string word that consists of digits and lowercase English letters.
 *
 * You will replace every non-digit character with a space. For example, "a123bc34d8ef34" will become " 123  34 8  34".
 * Notice that you are left with some integers that are separated by at least one space: "123", "34", "8", and "34".
 *
 * Return the number of different integers after performing the replacement operations on word.
 *
 * Two integers are considered different if their decimal representations without any leading zeros are different.
 *
 * Example 1:
 *
 * Input: word = "a123bc34d8ef34"
 * Output: 3
 * Explanation: The three different integers are "123", "34", and "8". Notice that "34" is only counted once.
 *
 * Example 2:
 *
 * Input: word = "leet1234code234"
 * Output: 2
 *
 * Example 3:
 *
 * Input: word = "a1b01c001"
 * Output: 1
 * Explanation: The three integers "1", "01", and "001" all represent the same integer because
 * the leading zeros are ignored when comparing their decimal values.
 *
 * Constraints:
 *
 * 1 <= word.length <= 1000
 * word consists of digits and lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1805_NumberOfDifferentIntegersInAString {

    @Answer
    public int numDifferentIntegers(String word) {
        return (int) Stream.of(word.split("[a-z]+"))
                .map(str -> str.replaceFirst("^0+(.)", "$1"))
                .filter(str -> !str.isEmpty())
                .distinct()
                .count();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("a123bc34d8ef34").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create("leet1234code234").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create("a1b01c001").expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("0a0").expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("0i00e").expect(1);

}
