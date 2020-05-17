package q750;

import org.junit.Assert;
import org.junit.Test;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/design-hashmap/
 *
 * Design a HashMap without using any built-in hash table libraries.
 *
 * To be specific, your design should include these functions:
 *
 * put(key, value) : Insert a (key, value) pair into the HashMap. If the value already exists in the HashMap,
 * update the value.
 * get(key): Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for
 * the key.
 * remove(key) : Remove the mapping for the value key if this map contains the mapping for the key.
 *
 *
 * Example:
 *
 * MyHashMap hashMap = new MyHashMap();
 * hashMap.put(1, 1);
 * hashMap.put(2, 2);
 * hashMap.get(1);            // returns 1
 * hashMap.get(3);            // returns -1 (not found)
 * hashMap.put(2, 1);          // update the existing value
 * hashMap.get(2);            // returns 1
 * hashMap.remove(2);          // remove the mapping for 2
 * hashMap.get(2);            // returns -1 (not found)
 *
 *
 * Note:
 *
 * All keys and values will be in the range of [0, 1000000].
 * The number of operations will be in the range of [1, 10000].
 * Please do not use the built-in HashMap library.
 */
public class Q706_DesignHashMap {

    /**
     * 这题用了和上一题不一样的做法, 这题如果有碰撞就直接放到数组后面去.
     * LeetCode 中主要还使用桶或桶+链表的方式
     */
    private static class MyHashMap {

        final static int INITIAL_SIZE = 16;

        int size, capacity = INITIAL_SIZE;

        int[][] table = new int[capacity][];

        /**
         * Initialize your data structure here.
         */
        public MyHashMap() {

        }

        /**
         * value will always be non-negative.
         */
        public void put(int key, int value) {
            if (size == capacity / 2) {
                resize(capacity * 2);
            }
            put(new int[]{key, value});
        }

        private void resize(int newSize) {
            int[][] old = table;
            capacity = newSize;
            table = new int[capacity][];
            size = 0;
            for (int[] kv : old) {
                if (kv != null) {
                    put(kv);
                }
            }
        }

        private void put(int[] kv) {
            int h = hash(kv[0]);
            while (table[h] != null && table[h][0] != kv[0]) {
                h = (h + 1) % capacity;
            }
            size += table[h] == null ? 1 : 0;
            table[h] = kv;
        }

        private int hash(int key) {
            return key * 7 % capacity;
        }

        /**
         * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
         */
        public int get(int key) {
            int h = hash(key);
            while (table[h] != null && table[h][0] != key) {
                h = (h + 1) % capacity;
            }
            return table[h] == null ? -1 : table[h][1];
        }

        /**
         * Removes the mapping of the specified value key if this map contains a mapping for the key
         */
        public void remove(int key) {
            int h = hash(key);
            while (table[h] != null && table[h][0] != key) {
                h = (h + 1) % capacity;
            }
            if (table[h] == null) {
                return;
            }
            size--;
            table[h] = null;
            if (size > INITIAL_SIZE && size < capacity / 8) {
                resize(size / 2);
            } else {
                for (int i = (h + 1) % capacity;
                        table[i] != null;
                        i = (i + 1) % capacity) {
                    int[] kv = table[i];
                    table[i] = null;
                    size--;
                    put(kv);
                }
            }
        }
    }

    @Test
    public void testMethod() {
        MyHashMap hashMap = new MyHashMap();
        hashMap.put(1, 1);
        hashMap.put(2, 2);
        Assert.assertEquals(1, hashMap.get(1));
        Assert.assertEquals(-1, hashMap.get(3));
        hashMap.put(2, 1);
        Assert.assertEquals(1, hashMap.get(2));
        hashMap.remove(2);
        Assert.assertEquals(-1, hashMap.get(2));
    }

    @Test
    public void testMethod2() {
        String testData = TestDataFileHelper.readString("Q706_TestData").get();
        String[] strs = testData.split("\n");
        String[] methods = strs[0].split(","), argsStrs = strs[1].split("\\],\\[");

        String expectData = TestDataFileHelper.readString("Q706_TestData_Result").get();
        String[] expects = expectData.split(",");

        MyHashMap hashMap = new MyHashMap();
        for (int i = 0; i < methods.length; i++) {
            if (argsStrs[i].startsWith("[")) {
                argsStrs[i] = argsStrs[i].substring(1);
            }
            if (argsStrs[i].endsWith("]")) {
                argsStrs[i] = argsStrs[i].substring(0, argsStrs[i].length() - 1);
            }
            String[] argStrs = argsStrs[i].split(",");
            int[] arg = new int[argStrs.length];
            if (!argsStrs[i].isEmpty()) {
                for (int j = 0; j < argStrs.length; j++) {
                    arg[j] = Integer.parseInt(argStrs[j]);
                }
            }
            String method = methods[i].replaceAll("\"", "");
            int expect = "null".equals(expects[i]) ? 0 : Integer.parseInt(expects[i]);

            switch (method) {
                case "get":
                    Assert.assertEquals(expect, hashMap.get(arg[0]));
                    break;
                case "put":
                    hashMap.put(arg[0], arg[1]);
                    break;
                case "remove":
                    hashMap.remove(arg[0]);
                    break;
                default:
            }
        }
    }

}
