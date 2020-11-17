package q1350;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;
import util.asserthelper.AssertUtils;

/**
 * [Medium] 1348. Tweet Counts Per Frequency
 * https://leetcode.com/problems/tweet-counts-per-frequency/
 *
 * Implement the class TweetCounts that supports two methods:
 *
 * 1. recordTweet(string tweetName, int time)
 *
 * Stores the tweetName at the recorded time (in seconds).
 *
 * 2. getTweetCountsPerFrequency(string freq, string tweetName, int startTime, int endTime)
 *
 * Returns the total number of occurrences for the given tweetName per minute, hour, or day (depending on freq) starting
 * from the startTime (in seconds) and ending at the endTime (in seconds).
 * freq is always minute, hour or day, representing the time interval to get the total number of occurrences for the
 * given tweetName.
 * The first time interval always starts from the startTime, so the time intervals are [startTime, startTime + delta*1>,
 * [startTime + delta*1, startTime + delta*2>, [startTime + delta*2, startTime + delta*3>, ... , [startTime + delta*i,
 * min(startTime + delta*(i+1), endTime + 1)> for some non-negative number i and delta (which depends on freq).
 *
 * Example:
 *
 * Input
 * ["TweetCounts","recordTweet","recordTweet","recordTweet","getTweetCountsPerFrequency",
 * "getTweetCountsPerFrequency","recordTweet","getTweetCountsPerFrequency"]
 * [[],["tweet3",0],["tweet3",60],["tweet3",10],["minute","tweet3",0,59],["minute","tweet3",0,60],["tweet3",120],
 * ["hour","tweet3",0,210]]
 *
 * Output
 * [null,null,null,null,[2],[2,1],null,[4]]
 *
 * Explanation
 * TweetCounts tweetCounts = new TweetCounts();
 * tweetCounts.recordTweet("tweet3", 0);
 * tweetCounts.recordTweet("tweet3", 60);
 * tweetCounts.recordTweet("tweet3", 10);                             // All tweets correspond to "tweet3" with recorded
 * times at 0, 10 and 60.
 * tweetCounts.getTweetCountsPerFrequency("minute", "tweet3", 0, 59); // return [2]. The frequency is per minute (60
 * seconds), so there is one interval of time: 1) [0, 60> - > 2 tweets.
 * tweetCounts.getTweetCountsPerFrequency("minute", "tweet3", 0, 60); // return [2, 1]. The frequency is per minute (60
 * seconds), so there are two intervals of time: 1) [0, 60> - > 2 tweets, and 2) [60,61> - > 1 tweet.
 * tweetCounts.recordTweet("tweet3", 120);                            // All tweets correspond to "tweet3" with recorded
 * times at 0, 10, 60 and 120.
 * tweetCounts.getTweetCountsPerFrequency("hour", "tweet3", 0, 210);  // return [4]. The frequency is per hour (3600
 * seconds), so there is one interval of time: 1) [0, 211> - > 4 tweets.
 *
 * Constraints:
 *
 * There will be at most 10000 operations considering both recordTweet and getTweetCountsPerFrequency.
 * 0 <= time, startTime, endTime <= 10^9
 * 0 <= endTime - startTime <= 10^4
 */
public class Q1348_TweetCountsPerFrequency {

    private static class TweetCounts {

        public TweetCounts() {

        }

        private Map<String, TreeMap<Integer, Integer>> map = new HashMap<>();

        public void recordTweet(String tweetName, int time) {
            TreeMap<Integer, Integer> treeMap = map.computeIfAbsent(tweetName, k -> new TreeMap<>());
            treeMap.put(time, treeMap.getOrDefault(time, 0) + 1);
        }

        public List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) {
            TreeMap<Integer, Integer> tree = map.get(tweetName);
            if (tree == null) {
                return Collections.emptyList();
            }
            int range = "minute".equals(freq) ? 60 : "hour".equals(freq) ? 60 * 60 : 24 * 60 * 60;
            List<Integer> res = new ArrayList<>();
            for (int i = startTime; i <= endTime; i += range) {
                int count = 0;
                int end = Math.min(i + range, endTime + 1);
                Integer key = tree.ceilingKey(i);
                while (key != null && key < end) {
                    count += tree.get(key);
                    key = tree.higherKey(key);
                }
                res.add(count);
            }
            return res;
        }
    }

    @Test
    public void testMethod() {
        List<Integer> frequency;
        TweetCounts tweetCounts = new TweetCounts();
        tweetCounts.recordTweet("tweet3", 0);
        tweetCounts.recordTweet("tweet3", 60);
        tweetCounts.recordTweet("tweet3", 10);
        frequency = tweetCounts.getTweetCountsPerFrequency("minute", "tweet3", 0, 59);
        AssertUtils.assertEquals(List.of(2), frequency);
        frequency = tweetCounts.getTweetCountsPerFrequency("minute", "tweet3", 0, 60);
        AssertUtils.assertEquals(List.of(2, 1), frequency);
        tweetCounts.recordTweet("tweet3", 120);
        frequency = tweetCounts.getTweetCountsPerFrequency("hour", "tweet3", 0, 210);
        AssertUtils.assertEquals(List.of(4), frequency);
    }

}
