package q750;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/design-hashset/
 *
 * Design a HashSet without using any built-in hash table libraries.
 *
 * To be specific, your design should include these functions:
 *
 * add(value): Insert a value into the HashSet.
 * contains(value) : Return whether the value exists in the HashSet or not.
 * remove(value): Remove a value in the HashSet. If the value does not exist in the HashSet, do nothing.
 *
 *
 * Example:
 *
 * MyHashSet hashSet = new MyHashSet();
 * hashSet.add(1);
 * hashSet.add(2);
 * hashSet.contains(1);    // returns true
 * hashSet.contains(3);    // returns false (not found)
 * hashSet.add(2);
 * hashSet.contains(2);    // returns true
 * hashSet.remove(2);
 * hashSet.contains(2);    // returns false (already removed)
 *
 *
 * Note:
 *
 * All values will be in the range of [0, 1000000].
 * The number of operations will be in the range of [1, 10000].
 * Please do not use the built-in HashSet library.
 */
public class Q705_DesignHashSet {

    /**
     * 这题就是要实现 HashSet, 这里采用布谷鸟hash.
     * LeetCode 中比较多的还是 boolean[100_0000] 这样的算法.
     */
    private static class MyHashSet {

        private int size = 0, capacity = 16;

        private int[] table1 = new int[capacity];

        private int[] table2 = new int[capacity];

        /**
         * Initialize your data structure here.
         */
        public MyHashSet() {
            Arrays.fill(table1, -1);
            Arrays.fill(table2, -1);
        }

        public void add(int key) {
            // 保证足够的hash 空间, 避免互相剔除的次数变大
            if (size == capacity / 2) {
                resize(capacity * 2);
            }
            int hash1 = hash1(key), hash2 = hash2(key);
            if (table1[hash1] == key || table2[hash2] == key) {
                return;
            }
            size++;

            // 大多数情况下这2 个if 可以处理, 如果删掉会增加挺多的耗时
            if (table1[hash1] == -1) {
                table1[hash1] = key;
                return;
            }
            if (table2[hash2] == -1) {
                table2[hash2] = key;
                return;
            }
            while (true) {
                int max = 10;
                while (max-- > 0) {
                    hash1 = hash1(key);
                    if (table1[hash1] == -1) {
                        table1[hash1] = key;
                        return;
                    } else {
                        int t = key;
                        key = table1[hash1];
                        table1[hash1] = t;
                    }
                    hash2 = hash2(key);
                    if (table2[hash2] == -1) {
                        table2[hash2] = key;
                        return;
                    } else {
                        int t = key;
                        key = table2[hash2];
                        table2[hash2] = t;
                    }
                }
                // 重试超过一地次数后重新扩张, 避免出现死循环的情况
                resize(capacity * 2);
            }
        }

        private void resize(int newSize) {
            int[] oldTable1 = table1, oldTable2 = table2;

            capacity = newSize;
            table1 = new int[capacity];
            Arrays.fill(table1, -1);
            table2 = new int[capacity];
            Arrays.fill(table2, -1);
            size = 0;

            for (int i : oldTable1) {
                if (i > -1) {
                    add(i);
                }
            }
            for (int i : oldTable2) {
                if (i > -1) {
                    add(i);
                }
            }
        }

        private int hash1(int key) {
            return key % capacity;
        }

        private int hash2(int key) {
            return (key + 7) % capacity;
        }

        public void remove(int key) {
            int hash1 = hash1(key);
            if (table1[hash1] == key) {
                table1[hash1] = -1;
                // 如果在这里减小尺寸会造成耗时增加
                size--;
                return;
            }
            int hash2 = hash2(key);
            if (table2[hash2] == key) {
                table2[hash2] = -1;
                size--;
                return;
            }
        }

        /**
         * Returns true if this set contains the specified element
         */
        public boolean contains(int key) {
            return table1[hash1(key)] == key
                    || table2[hash2(key)] == key;
        }
    }

    @Test
    public void testMethod() {
        MyHashSet hashSet = new MyHashSet();
        hashSet.add(1);
        hashSet.add(2);
        Assert.assertTrue(hashSet.contains(1));
        Assert.assertFalse(hashSet.contains(3));
        hashSet.add(2);
        Assert.assertTrue(hashSet.contains(2));
        hashSet.remove(2);
        Assert.assertFalse(hashSet.contains(2));
    }

    @Test
    public void testMethod2() {
        MyHashSet hashSet = new MyHashSet();
        Assert.assertFalse(hashSet.contains(72));
        hashSet.remove(91);
        hashSet.add(48);
        hashSet.add(41);
        Assert.assertFalse(hashSet.contains(96));
        hashSet.remove(87);
        Assert.assertTrue(hashSet.contains(48));
        Assert.assertFalse(hashSet.contains(49));
        hashSet.add(84);
        hashSet.add(82);
        hashSet.add(24);
        hashSet.add(7);
        hashSet.remove(56);
        hashSet.add(87);
        hashSet.add(81);
        hashSet.add(55);
        hashSet.add(19);
        hashSet.add(40);
        hashSet.add(68);
        hashSet.add(23);
        hashSet.add(80);
        hashSet.add(53);
        hashSet.add(76);
        Assert.assertFalse(hashSet.contains(93));
        hashSet.add(95);
        Assert.assertTrue(hashSet.contains(95));
        hashSet.add(67);
        hashSet.add(31);
        Assert.assertTrue(hashSet.contains(80));
        hashSet.add(62);
        hashSet.add(73);
        hashSet.remove(97);
        hashSet.add(33);
        hashSet.add(28);
        hashSet.add(62);
        hashSet.add(81);
        hashSet.add(57);
        Assert.assertTrue(hashSet.contains(40));
        hashSet.add(11);
        hashSet.add(89);
        hashSet.add(28);
        hashSet.remove(97);
        Assert.assertFalse(hashSet.contains(86));
        hashSet.add(20);
        Assert.assertFalse(hashSet.contains(5));
        hashSet.add(77);
        hashSet.add(52);
        hashSet.add(57);
        hashSet.add(88);
        hashSet.add(20);
        Assert.assertTrue(hashSet.contains(48));
        hashSet.remove(42);
        hashSet.remove(86);
        hashSet.add(49);
        hashSet.remove(62);
        Assert.assertTrue(hashSet.contains(53));
        hashSet.add(43);
        hashSet.remove(98);
        hashSet.add(32);
        hashSet.add(15);
        hashSet.add(42);
        hashSet.add(50);
        Assert.assertTrue(hashSet.contains(19));
        Assert.assertTrue(hashSet.contains(32));
        hashSet.add(67);
        hashSet.remove(84);
        hashSet.remove(60);
        hashSet.remove(8);
        hashSet.remove(85);
        hashSet.add(43);
        hashSet.add(59);
        Assert.assertFalse(hashSet.contains(65));
        hashSet.add(40);
        hashSet.add(81);
        hashSet.remove(55);
        hashSet.add(56);
        hashSet.add(54);
        hashSet.add(59);
        hashSet.add(78);
        hashSet.add(53);
        hashSet.add(0);
        hashSet.add(24);
        hashSet.add(7);
        hashSet.remove(53);
        hashSet.add(33);
        hashSet.remove(69);
        hashSet.remove(86);
        hashSet.add(7);
        hashSet.remove(1);
        hashSet.add(16);
        hashSet.remove(58);
        hashSet.add(61);
        hashSet.add(34);
        hashSet.add(53);
        hashSet.remove(84);
        hashSet.remove(21);
        hashSet.remove(58);
        hashSet.add(25);
        Assert.assertFalse(hashSet.contains(45));
        hashSet.add(3);
    }

    @Test
    public void testMethod3() {
        MyHashSet hashSet = new MyHashSet();
        hashSet.add(9);
        hashSet.remove(19);
        hashSet.add(14);
        hashSet.remove(19);
        hashSet.remove(9);
        hashSet.add(0);
        hashSet.add(3);
        hashSet.add(4);
        hashSet.add(0);
        hashSet.remove(9);
    }

}
