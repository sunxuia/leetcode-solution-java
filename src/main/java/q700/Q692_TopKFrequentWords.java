package q700;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/top-k-frequent-words/
 *
 * Given a non-empty list of words, return the k most frequent elements.
 *
 * Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency, then the
 * word with the lower alphabetical order comes first.
 *
 * Example 1:
 *
 * Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
 * Output: ["i", "love"]
 * Explanation: "i" and "love" are the two most frequent words.
 * Note that "i" comes before "love" due to a lower alphabetical order.
 *
 * Example 2:
 *
 * Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
 * Output: ["the", "is", "sunny", "day"]
 * Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
 * with the number of occurrence being 4, 3, 2 and 1 respectively.
 *
 * Note:
 *
 * 1. You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 * 2. Input words contain only lowercase letters.
 *
 * Follow up:
 *
 * 1. Try to solve it in O(n log k) time and O(n) extra space.
 */
@RunWith(LeetCodeRunner.class)
public class Q692_TopKFrequentWords {

    @Answer
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> counts = new HashMap<>();
        for (String word : words) {
            counts.put(word, counts.getOrDefault(word, 0) + 1);
        }

        PriorityQueue<String> pq = new PriorityQueue<>(k + 1, (a, b) -> {
            int countA = counts.get(a);
            int countB = counts.get(b);
            return countA == countB ? b.compareTo(a) : countA - countB;
        });
        for (String word : counts.keySet()) {
            pq.offer(word);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        List<String> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            res.add(pq.poll());
        }
        for (int i = 0; i < k / 2; i++) {
            String t = res.get(i);
            res.set(i, res.get(k - i - 1));
            res.set(k - i - 1, t);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"i", "love", "leetcode", "i", "love", "coding"}, 2)
            .expect(Arrays.asList("i", "love"));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"}, 4)
            .expect(Arrays.asList("the", "is", "sunny", "day"));

}
