package q100;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/simplify-path/
 *
 * Given an absolute path for a file (Unix-style), simplify it. Or in other words, convert it to the canonical path.
 *
 * In a UNIX-style file system, a period . refers to the current directory. Furthermore, a double period .. moves the
 * directory up a level. For more information, see: Absolute path vs relative path in Linux/Unix
 *
 * Note that the returned canonical path must always begin with a slash /, and there must be only a single slash /
 * between two directory names. The last directory name (if it exists) must not end with a trailing /. Also, the
 * canonical path must be the shortest string representing the absolute path.
 *
 *
 *
 * Example 1:
 *
 * Input: "/home/"
 * Output: "/home"
 * Explanation: Note that there is no trailing slash after the last directory name.
 * Example 2:
 *
 * Input: "/../"
 * Output: "/"
 * Explanation: Going one level up from the root directory is a no-op, as the root level is the highest level you can
 * go.
 * Example 3:
 *
 * Input: "/home//foo/"
 * Output: "/home/foo"
 * Explanation: In the canonical path, multiple consecutive slashes are replaced by a single one.
 * Example 4:
 *
 * Input: "/a/./b/../../c/"
 * Output: "/c"
 * Example 5:
 *
 * Input: "/a/../../b/../c//.//"
 * Output: "/c"
 * Example 6:
 *
 * Input: "/a//b////c/d//././/.."
 * Output: "/a/b/c"
 */
@RunWith(LeetCodeRunner.class)
public class Q071_SimplifyPath {

    @Answer
    public String simplifyPath(String path) {
        if (!path.endsWith("/")) {
            path = path + "/";
        }
        List<String> array = new ArrayList<>();
        for (int i = 1, start = 1; i < path.length(); i++) {
            if (path.charAt(i) == '/') {
                if (start == i - 1 && path.charAt(start) == '.') {
                    // . 匹配, 什么都不做
                } else if (start == i - 2 && path.charAt(start) == '.' && path.charAt(start + 1) == '.') {
                    // .. 匹配
                    if (!array.isEmpty()) {
                        array.remove(array.size() - 1);
                    }
                } else if (start < i) {
                    // 目录名匹配
                    array.add(path.substring(start, i));
                }
                start = i + 1;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String str : array) {
            sb.append('/').append(str);
        }
        return sb.length() == 0 ? "/" : sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("/home/").expect("/home");

    @TestData
    public DataExpectation example2 = DataExpectation.create("/../").expect("/");

    @TestData
    public DataExpectation example3 = DataExpectation.create("/home//foo/").expect("/home/foo");

    @TestData
    public DataExpectation example4 = DataExpectation.create("/a/./b/../../c/").expect("/c");

    @TestData
    public DataExpectation example5 = DataExpectation.create("/a/../../b/../c//.//").expect("/c");

    @TestData
    public DataExpectation example6 = DataExpectation.create("/a//b////c/d//././/..").expect("/a/b/c");

}
