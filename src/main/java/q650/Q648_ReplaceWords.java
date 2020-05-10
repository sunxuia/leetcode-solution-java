package q650;

import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/replace-words/
 *
 * In English, we have a concept called root, which can be followed by some other words to form another longer word -
 * let's call this word successor. For example, the root an, followed by other, which can form another word another.
 *
 * Now, given a dictionary consisting of many roots and a sentence. You need to replace all the successor in the
 * sentence with the root forming it. If a successor has many roots can form it, replace it with the root with the
 * shortest length.
 *
 * You need to output the sentence after the replacement.
 *
 *
 *
 * Example 1:
 *
 * Input: dict = ["cat","bat","rat"], sentence = "the cattle was rattled by the battery"
 * Output: "the cat was rat by the bat"
 *
 *
 *
 * Constraints:
 *
 * The input will only have lower-case letters.
 * 1 <= dict.length <= 1000
 * 1 <= dict[i].length <= 100
 * 1 <= sentence words number <= 1000
 * 1 <= sentence words length <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q648_ReplaceWords {

    // 字典树的题目
    @Answer
    public String replaceWords(List<String> dict, String sentence) {
        Node root = new Node();
        for (String str : dict) {
            Node node = root;
            for (int i = 0; i < str.length(); i++) {
                if (node.isChild) {
                    break;
                }
                int idx = str.charAt(i) - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new Node();
                }
                node = node.children[idx];
            }
            node.isChild = true;
        }

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for (String str : sentence.split(" ")) {
            Node node = root;
            temp.setLength(0);
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (node.children[c - 'a'] == null) {
                    break;
                }
                node = node.children[c - 'a'];
                temp.append(c);
                if (node.isChild) {
                    break;
                }
            }
            sb.append(node.isChild ? temp : str).append(' ');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private static class Node {

        boolean isChild;

        Node[] children = new Node[26];
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(Arrays.asList("cat", "bat", "rat"), "the cattle was rattled by the battery")
            .expect("the cat was rat by the bat");

}
