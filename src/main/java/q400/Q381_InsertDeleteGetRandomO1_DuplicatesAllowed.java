package q400;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed
 *
 * Design a data structure that supports all following operations in average O(1) time.
 * Note: Duplicate elements are allowed.
 *
 * insert(val): Inserts an item val to the collection.
 * remove(val): Removes an item val from the collection if present.
 * getRandom: Returns a random element from current collection of elements. The probability of each element being
 * returned is linearly related to the number of same value the collection contains.
 *
 * Example:
 *
 * // Init an empty collection.
 * RandomizedCollection collection = new RandomizedCollection();
 *
 * // Inserts 1 to the collection. Returns true as the collection did not contain 1.
 * collection.insert(1);
 *
 * // Inserts another 1 to the collection. Returns false as the collection contained 1. Collection now contains [1,1].
 * collection.insert(1);
 *
 * // Inserts 2 to the collection, returns true. Collection now contains [1,1,2].
 * collection.insert(2);
 *
 * // getRandom should return 1 with the probability 2/3, and returns 2 with the probability 1/3.
 * collection.getRandom();
 *
 * // Removes 1 from the collection, returns true. Collection now contains [1,2].
 * collection.remove(1);
 *
 * // getRandom should return 1 and 2 both equally likely.
 * collection.getRandom();
 */
public class Q381_InsertDeleteGetRandomO1_DuplicatesAllowed {

    private static class RandomizedCollection {

        private Map<Integer, Set<Integer>> map = new HashMap<>();

        private List<Integer> list = new ArrayList<>();

        /**
         * Initialize your data structure here.
         */
        public RandomizedCollection() {

        }

        /**
         * Inserts a value to the collection. Returns true if the collection did not already contain the specified
         * element.
         */
        public boolean insert(int val) {
            Set<Integer> valSet = map.get(val);
            boolean notExist = valSet == null;
            if (notExist) {
                valSet = new HashSet<>();
                map.put(val, valSet);
            }
            valSet.add(list.size());
            list.add(val);
            return notExist;
        }

        /**
         * Removes a value from the collection. Returns true if the collection contained the specified element.
         */
        public boolean remove(int val) {
            Set<Integer> valSet = map.get(val);
            boolean exist = valSet != null;
            if (exist) {
                Iterator<Integer> iterator = valSet.iterator();
                int valIndex = iterator.next();
                iterator.remove();
                if (valSet.isEmpty()) {
                    map.remove(val);
                }
                int lastIndex = list.size() - 1;
                if (valIndex < lastIndex) {
                    int lastVal = list.get(lastIndex);
                    Set<Integer> lastValSet = map.get(lastVal);
                    lastValSet.remove(lastIndex);
                    lastValSet.add(valIndex);
                    list.set(valIndex, lastVal);
                }
                list.remove(lastIndex);
            }
            return exist;
        }

        /**
         * Get a random element from the collection.
         */
        public int getRandom() {
            return list.get(new Random().nextInt(list.size()));
        }
    }

    @Test
    public void testMethod() {
        // Init an empty collection.
        RandomizedCollection collection = new RandomizedCollection();
        boolean booleanRes;
        int randomRes;

        // Inserts 1 to the collection. Returns true as the collection did not contain 1.
        booleanRes = collection.insert(1);
        Assert.assertTrue(booleanRes);

        // Inserts another 1 to the collection. Returns false as the collection contained 1. Collection now contains
        // [1,1].
        booleanRes = collection.insert(1);
        Assert.assertFalse(booleanRes);

        // Inserts 2 to the collection, returns true. Collection now contains [1,1,2].
        booleanRes = collection.insert(2);
        Assert.assertTrue(booleanRes);

        // getRandom should return 1 with the probability 2/3, and returns 2 with the probability 1/3.
        randomRes = collection.getRandom();
        Assert.assertTrue(randomRes == 1 || randomRes == 2);

        // Removes 1 from the collection, returns true. Collection now contains [1,2].
        booleanRes = collection.remove(1);
        Assert.assertTrue(booleanRes);

        // getRandom should return 1 and 2 both equally likely.
        randomRes = collection.getRandom();
        Assert.assertTrue(randomRes == 1 || randomRes == 2);
    }
}
