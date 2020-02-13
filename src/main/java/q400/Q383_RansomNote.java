package q400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/ransom-note/
 *
 * Given an arbitrary ransom note string and another string containing letters from all the magazines, write a
 * function that will return true if the ransom note can be constructed from the magazines ; otherwise, it will
 * return false.
 *
 * Each letter in the magazine string can only be used once in your ransom note.
 *
 * Note:
 * You may assume that both strings contain only lowercase letters.
 *
 * canConstruct("a", "b") -> false
 * canConstruct("aa", "ab") -> false
 * canConstruct("aa", "aab") -> true
 *
 * 题解:
 * 题目的意思: 字符串magazine 表示一本杂志的内容, 现在绑匪想要从杂志中找出各个单词, 去拼成勒索信的内容 ransomNote.
 * 问输入的magazine 是否能拼出ransomNote 中的内容.
 */
@RunWith(LeetCodeRunner.class)
public class Q383_RansomNote {

    @Answer
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] count = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            count[magazine.charAt(i) - 'a']++;
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            if (--count[ransomNote.charAt(i) - 'a'] == -1) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("a", "b").expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("aa", "bb").expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("aa", "aab").expect(true);

}
