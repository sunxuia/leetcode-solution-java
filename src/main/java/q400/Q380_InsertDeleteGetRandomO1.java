package q400;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/insert-delete-getrandom-o1/
 *
 * Design a data structure that supports all following operations in average O(1) time.
 *
 * insert(val): Inserts an item val to the set if not already present.
 * remove(val): Removes an item val from the set if present.
 * getRandom: Returns a random element from current set of elements. Each element must have the same probability
 * of being returned.
 *
 * Example:
 *
 * // Init an empty set.
 * RandomizedSet randomSet = new RandomizedSet();
 *
 * // Inserts 1 to the set. Returns true as 1 was inserted successfully.
 * randomSet.insert(1);
 *
 * // Returns false as 2 does not exist in the set.
 * randomSet.remove(2);
 *
 * // Inserts 2 to the set, returns true. Set now contains [1,2].
 * randomSet.insert(2);
 *
 * // getRandom should return either 1 or 2 randomly.
 * randomSet.getRandom();
 *
 * // Removes 1 from the set, returns true. Set now contains [2].
 * randomSet.remove(1);
 *
 * // 2 was already in the set, so return false.
 * randomSet.insert(2);
 *
 * // Since 2 is the only number in the set, getRandom always return 2.
 * randomSet.getRandom();
 */
public class Q380_InsertDeleteGetRandomO1 {

    private static class RandomizedSet {

        private List<Integer> list = new ArrayList<>();

        private Map<Integer, Integer> map = new HashMap<>();

        /**
         * Initialize your data structure here.
         */
        public RandomizedSet() {

        }

        /**
         * Inserts a value to the set. Returns true if the set did not already contain the specified element.
         */
        public boolean insert(int val) {
            if (map.containsKey(val)) {
                return false;
            }
            map.put(val, list.size());
            list.add(val);
            return true;
        }

        /**
         * Removes a value from the set. Returns true if the set contained the specified element.
         */
        public boolean remove(int val) {
            if (!map.containsKey(val)) {
                return false;
            }
            int index = map.remove(val);
            if (index < list.size() - 1) {
                int tail = list.get(list.size() - 1);
                map.put(tail, index);
                list.set(index, tail);
            }
            list.remove(list.size() - 1);
            return true;
        }

        /**
         * Get a random element from the set.
         */
        public int getRandom() {
            return list.get(new Random().nextInt(list.size()));
        }
    }

    @Test
    public void testMethod() {
        // Init an empty set.
        RandomizedSet randomSet = new RandomizedSet();
        boolean boolRes;
        int randomRes;

        // Inserts 1 to the set. Returns true as 1 was inserted successfully.
        boolRes = randomSet.insert(1);
        Assert.assertTrue(boolRes);

        // Returns false as 2 does not exist in the set.
        boolRes = randomSet.remove(2);
        Assert.assertFalse(boolRes);

        // Inserts 2 to the set, returns true. Set now contains [1,2].
        boolRes = randomSet.insert(2);
        Assert.assertTrue(boolRes);

        // getRandom should return either 1 or 2 randomly.
        randomRes = randomSet.getRandom();
        Assert.assertTrue(randomRes == 1 || randomRes == 2);

        // Removes 1 from the set, returns true. Set now contains [2].
        boolRes = randomSet.remove(1);
        Assert.assertTrue(boolRes);

        // 2 was already in the set, so return false.
        boolRes = randomSet.insert(2);
        Assert.assertFalse(boolRes);

        // Since 2 is the only number in the set, getRandom always return 2.
        randomRes = randomSet.getRandom();
        Assert.assertEquals(2, randomRes);
    }

}
