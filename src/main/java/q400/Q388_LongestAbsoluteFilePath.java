package q400;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/longest-absolute-file-path/
 *
 * Suppose we abstract our file system by a string in the following manner:
 *
 * The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:
 *
 * >  dir
 * >      subdir1
 * >      subdir2
 * >          file.ext
 *
 * The directory dir contains an empty sub-directory subdir1 and a sub-directory subdir2 containing a file file.ext.
 *
 * The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" represents:
 *
 * >  dir
 * >      subdir1
 * >          file1.ext
 * >          subsubdir1
 * >      subdir2
 * >          subsubdir2
 * >              file2.ext
 *
 * The directory dir contains two sub-directories subdir1 and subdir2. subdir1 contains a file file1.ext and an empty
 * second-level sub-directory subsubdir1. subdir2 contains a second-level sub-directory subsubdir2 containing a file
 * file2.ext.
 *
 * We are interested in finding the longest (number of characters) absolute path to a file within our file system.
 * For example, in the second example above, the longest absolute path is "dir/subdir2/subsubdir2/file2.ext", and its
 * length is 32 (not including the double quotes).
 *
 * Given a string representing the file system in the above format, return the length of the longest absolute path to
 * file in the abstracted file system. If there is no file in the system, return 0.
 *
 * Note:
 *
 * The name of a file contains at least a . and an extension.
 * The name of a directory or sub-directory will not contain a ..
 *
 * Time complexity required: O(n) where n is the size of the input string.
 *
 * Notice that a/aa/aaa/file1.txt is not the longest file path, if there is another path aaaaaaaaaaaaaaaaaaaaa/sth.png.
 */
@RunWith(LeetCodeRunner.class)
public class Q388_LongestAbsoluteFilePath {

    // 这题不好, 纯粹的烦人
    @Answer
    public int lengthLongestPath(String input) {
        // 针对4 个空格是缩进还是\t 是缩进分别进行处理.
        String[] paths = input.split("\n");
        int[] depths = new int[paths.length];
        for (int i = 1; i < paths.length; i++) {
            String path = paths[i];
            int count = 0;
            if (path.charAt(0) == ' ') {
                while (path.charAt(count) == ' ') {
                    count++;
                }
                if (count / 4 > depths[i - 1] + 1) {
                    count = (depths[i - 1] + 1) * 4;
                }
                depths[i] = count / 4;
                paths[i] = path.substring(count);
            } else if (path.charAt(0) == '\t') {
                while (path.charAt(count) == '\t' && count < depths[i - 1] + 2) {
                    count++;
                }
                depths[i] = count;
                paths[i] = path.substring(count);
            }
        }

        // 按顺序寻找最长的路径.
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        int res = 0;
        for (int i = 0; i < paths.length; i++) {
            int depth = depths[i], prevDepth = i == 0 ? -1 : depths[i - 1];
            while (depth++ <= prevDepth) {
                stack.pop();
            }

            stack.push(stack.peek() + 1 + paths[i].length());
            if (paths[i].contains(".")) {
                res = Math.max(res, stack.peek());
            }
        }
        return res;
    }

    /**
     * LeetCode 上的做法:
     * 应该是我理解错了测试用例的例子: "dir\n    file.txt" 不是4 个空格为缩进, 而是"dir" 和 "    file.txt" 2 个同级文件.
     * 不过正好LeetCode 上的测试用例能够通过上面的代码.
     */
    @Answer
    public int lengthLongestPath2(String input) {
        String[] inputs = input.split("\n");
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);
        int res = 0;
        for (String in : inputs) {
            int level = in.lastIndexOf("\t") + 1;
            while (level + 1 < stack.size()) {
                stack.pop();
            }
            int len = stack.peek() + in.length() - level + 1;
            if (in.contains(".")) {
                res = Math.max(res, len - 1);
            }
            stack.push(len);
        }
        return res;

    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create("dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext")
            .expect(20);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create("dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext")
            .expect(32);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create("dir\n    file.txt")
            .expect(12);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create("dir\n        file.txt")
            .expect(16);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create("dir\n\t        file.txt\n\tfile2.txt")
            .expect(20);

    @TestData
    public DataExpectation normal4 = DataExpectation
            .create("a\n\taa\n\t\taaa\n\t\t\tfile1.txt\naaaaaaaaaaaaaaaaaaaaa\n\tsth.png")
            .expect(29);

    @TestData
    public DataExpectation normal5 = DataExpectation
            .create("dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext")
            .expect(20);

}
