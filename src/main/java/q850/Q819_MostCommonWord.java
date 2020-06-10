package q850;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/most-common-word/
 *
 * Given a paragraph and a list of banned words, return the most frequent word that is not in the list of banned
 * words.  It is guaranteed there is at least one word that isn't banned, and that the answer is unique.
 *
 * Words in the list of banned words are given in lowercase, and free of punctuation.  Words in the paragraph are not
 * case sensitive.  The answer is in lowercase.
 *
 *
 *
 * Example:
 *
 * Input:
 * paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
 * banned = ["hit"]
 * Output: "ball"
 * Explanation:
 * "hit" occurs 3 times, but it is a banned word.
 * "ball" occurs twice (and no other word does), so it is the most frequent non-banned word in the paragraph.
 * Note that words in the paragraph are not case sensitive,
 * that punctuation is ignored (even if adjacent to words, such as "ball,"),
 * and that "hit" isn't the answer even though it occurs more because it is banned.
 *
 *
 *
 * Note:
 *
 * 1. 1 <= paragraph.length <= 1000.
 * 2. 0 <= banned.length <= 100.
 * 3. 1 <= banned[i].length <= 10.
 * 4. The answer is unique, and written in lowercase (even if its occurrences in paragraph may have uppercase
 * symbols, and even if it is a proper noun.)
 * 5. paragraph only consists of letters, spaces, or the punctuation symbols !?',;.
 * 6. There are no hyphens or hyphenated words.
 * 7. Words only consist of letters, never apostrophes or other punctuation symbols.
 */
@RunWith(LeetCodeRunner.class)
public class Q819_MostCommonWord {

    @Answer
    public String mostCommonWord(String paragraph, String[] banned) {
        Set<String> banSet = new HashSet<>(Arrays.asList(banned));
        Map<String, Integer> times = new HashMap<>();
        Matcher matcher = PATTERN.matcher(paragraph);
        while (matcher.find()) {
            String word = matcher.group(0).toLowerCase();
            if (!banSet.contains(word)) {
                times.put(word, times.getOrDefault(word, 0) + 1);
            }
        }
        return times.entrySet().stream()
                .max(Comparator.comparingInt(Entry::getValue))
                .map(Entry::getKey)
                .orElse("");
    }

    private static final Pattern PATTERN = Pattern.compile("[A-Za-z]+");

    @TestData
    public DataExpectation example = DataExpectation
            .createWith("Bob hit a ball, the hit BALL flew far after it was hit.", new String[]{"hit"})
            .expect("ball");

}
