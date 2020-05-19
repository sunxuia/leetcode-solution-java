package q750;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/remove-comments/
 *
 * (题目描述涉及到注释字符)
 */
@RunWith(LeetCodeRunner.class)
public class Q722_RemoveComments {

    @Answer
    public List<String> removeComments(String[] source) {
        List<String> res = new ArrayList<>();
        boolean blockComment = false;
        StringBuilder sb = new StringBuilder();
        for (String line : source) {
            char prev = ' ';
            boolean lineComment = false;
            for (char c : line.toCharArray()) {
                if (lineComment) {
                    break;
                } else if (blockComment) {
                    if (prev == '*' && c == '/') {
                        blockComment = false;
                        prev = ' ';
                    } else {
                        prev = c;
                    }
                } else if (prev == '/' && c == '/') {
                    lineComment = true;
                    sb.setLength(sb.length() - 1);
                    prev = ' ';
                } else if (prev == '/' && c == '*') {
                    blockComment = true;
                    sb.setLength(sb.length() - 1);
                    prev = ' ';
                } else {
                    sb.append(c);
                    prev = c;
                }
            }

            if (!blockComment && sb.length() > 0) {
                res.add(sb.toString());
                sb.setLength(0);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new String[]{
            "/*Test program */",
            "int main()",
            "{ ",
            "  // variable declaration ",
            "int a, b, c;",
            "/* This is a test",
            "   multiline  ",
            "   comment for ",
            "   testing */",
            "a = b + c;", "}"
    }).expect(new String[]{
            "int main()",
            "{ ",
            "  ",
            "int a, b, c;",
            "a = b + c;",
            "}"
    });

    @TestData
    public DataExpectation example2 = DataExpectation.create(new String[]{
            "a/*comment",
            "line",
            "more_comment*/b"
    }).expect(new String[]{"ab"});

}
