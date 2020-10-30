package q1250;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1233. Remove Sub-Folders from the Filesystem
 * https://leetcode.com/problems/remove-sub-folders-from-the-filesystem/
 *
 * Given a list of folders, remove all sub-folders in those folders and return in any order the folders after removing.
 *
 * If a folder[i] is located within another folder[j], it is called a sub-folder of it.
 *
 * The format of a path is one or more concatenated strings of the form: / followed by one or more lowercase English
 * letters. For example, /leetcode and /leetcode/problems are valid paths while an empty string and / are not.
 *
 * Example 1:
 *
 * Input: folder = ["/a","/a/b","/c/d","/c/d/e","/c/f"]
 * Output: ["/a","/c/d","/c/f"]
 * Explanation: Folders "/a/b/" is a subfolder of "/a" and "/c/d/e" is inside of folder "/c/d" in our filesystem.
 *
 * Example 2:
 *
 * Input: folder = ["/a","/a/b/c","/a/b/d"]
 * Output: ["/a"]
 * Explanation: Folders "/a/b/c" and "/a/b/d/" will be removed because they are subfolders of "/a".
 *
 * Example 3:
 *
 * Input: folder = ["/a/b/c","/a/b/ca","/a/b/d"]
 * Output: ["/a/b/c","/a/b/ca","/a/b/d"]
 *
 * Constraints:
 *
 * 1 <= folder.length <= 4 * 10^4
 * 2 <= folder[i].length <= 100
 * folder[i] contains only lowercase letters and '/'
 * folder[i] always starts with character '/'
 * Each folder name is unique.
 */
@RunWith(LeetCodeRunner.class)
public class Q1233_RemoveSubFoldersFromTheFilesystem {

    /**
     * 排序的解法. 时间复杂度 O(NlogN)
     */
    @Answer
    public List<String> removeSubfolders(String[] folder) {
        Arrays.sort(folder);
        List<String> res = new ArrayList<>();
        String prev = " ";
        for (String path : folder) {
            if (!path.startsWith(prev)) {
                res.add(path);
                prev = path + "/";
            }
        }
        return res;
    }

    /**
     * 字典树的解法. 时间复杂度 O(m), m 是folder 表示的所有目录.
     * (不过在LeetCode 中反而更慢.)
     */
    @Answer
    public List<String> removeSubfolders2(String[] folder) {
        Node root = new Node("");
        for (String path : folder) {
            if ("/".equals(path)) {
                return Arrays.asList("/");
            }
            String[] strs = path.substring(1).split("/");
            setNode(strs, 0, root);
        }
        List<String> res = new ArrayList<>();
        search(res, new StringBuilder(), root);
        return res;
    }

    private static class Node extends HashMap<String, Node> {

        final String path;

        boolean isFinal;

        Node(String path) {
            this.path = path;
        }
    }

    private void setNode(String[] path, int i, Node parent) {
        if (parent.isFinal) {
            return;
        }
        if (i == path.length) {
            parent.isFinal = true;
            parent.clear();
            return;
        }
        Node child = parent.computeIfAbsent(
                path[i], k -> new Node("/" + path[i]));
        setNode(path, i + 1, child);
    }

    private void search(List<String> res, StringBuilder path, Node node) {
        path.append(node.path);
        if (node.isFinal) {
            res.add(path.toString());
        } else {
            for (Node child : node.values()) {
                search(res, path, child);
            }
        }
        path.setLength(path.length() - node.path.length());
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new String[]{"/a", "/a/b", "/c/d", "/c/d/e", "/c/f"})
            .expect(Arrays.asList("/a", "/c/d", "/c/f"))
            .unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new String[]{"/a", "/a/b/c", "/a/b/d"})
            .expect(Arrays.asList("/a"));

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new String[]{"/a/b/c", "/a/b/ca", "/a/b/d"})
            .expect(Arrays.asList("/a/b/c", "/a/b/ca", "/a/b/d"))
            .unOrder();

}
