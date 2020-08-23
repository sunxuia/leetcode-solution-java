package q1000;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * [Medium] 981. Time Based Key-Value Store
 * https://leetcode.com/problems/time-based-key-value-store/
 *
 * Create a timebased key-value store class TimeMap, that supports two operations.
 *
 * 1. set(string key, string value, int timestamp)
 *
 * Stores the key and value, along with the given timestamp.
 *
 * 2. get(string key, int timestamp)
 *
 * Returns a value such that set(key, value, timestamp_prev) was called previously, with timestamp_prev <= timestamp.
 * If there are multiple such values, it returns the one with the largest timestamp_prev.
 * If there are no values, it returns the empty string ("").
 *
 * Example 1:
 *
 * Input: inputs = ["TimeMap","set","get","get","set","get","get"], inputs = [[],["foo","bar",1],["foo",1],["foo",3],
 * ["foo","bar2",4],["foo",4],["foo",5]]
 * Output: [null,null,"bar","bar",null,"bar2","bar2"]
 * Explanation:
 * TimeMap kv;
 * kv.set("foo", "bar", 1); // store the key "foo" and value "bar" along with timestamp = 1
 * kv.get("foo", 1);  // output "bar"
 * kv.get("foo", 3); // output "bar" since there is no value corresponding to foo at timestamp 3 and timestamp 2, then
 * the only value is at timestamp 1 ie "bar"
 * kv.set("foo", "bar2", 4);
 * kv.get("foo", 4); // output "bar2"
 * kv.get("foo", 5); //output "bar2"
 *
 * Example 2:
 *
 * Input: inputs = ["TimeMap","set","set","get","get","get","get","get"], inputs =
 * [[],["love","high",10],["love","low",20],["love",5],["love",10],["love",15],["love",20],["love",25]]
 * Output: [null,null,null,"","high","high","low","low"]
 *
 * Note:
 *
 * All key/value strings are lowercase.
 * All key/value strings have length in the range [1, 100]
 * The timestamps for all TimeMap.set operations are strictly increasing.
 * 1 <= timestamp <= 10^7
 * TimeMap.set and TimeMap.get functions will be called a total of 120000 times (combined) per test case.
 */
public class Q981_TimeBasedKeyValueStore {

    private static class TimeMap {

        private final Map<String, TreeMap<Integer, String>> map = new HashMap<>();

        /**
         * Initialize your data structure here.
         */
        public TimeMap() {

        }

        public void set(String key, String value, int timestamp) {
            map.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, value);
        }

        public String get(String key, int timestamp) {
            TreeMap<Integer, String> tmap = map.get(key);
            if (tmap == null) {
                return "";
            }
            Integer floor = tmap.floorKey(timestamp);
            return floor == null ? "" : tmap.get(floor);
        }
    }

    @Test
    public void example1() {
        TimeMap tested = new TimeMap();
        tested.set("foo", "bar", 1);
        Assert.assertEquals("bar", tested.get("foo", 1));
        Assert.assertEquals("bar", tested.get("foo", 3));
        tested.set("foo", "bar2", 4);
        Assert.assertEquals("bar2", tested.get("foo", 4));
        Assert.assertEquals("bar2", tested.get("foo", 5));
    }

    @Test
    public void example2() {
        TimeMap tested = new TimeMap();
        tested.set("love", "high", 10);
        tested.set("love", "low", 20);
        Assert.assertEquals("", tested.get("foo", 5));
        Assert.assertEquals("high", tested.get("love", 10));
        Assert.assertEquals("high", tested.get("love", 15));
        Assert.assertEquals("low", tested.get("love", 20));
        Assert.assertEquals("low", tested.get("love", 25));
    }

}
