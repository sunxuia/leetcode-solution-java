package q600;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/tag-validator/
 *
 * Given a string representing a code snippet, you need to implement a tag validator to parse the code and return
 * whether it is valid. A code snippet is valid if all the following rules hold:
 *
 * 1. The code must be wrapped in a valid closed tag. Otherwise, the code is invalid.
 * 2. A closed tag (not necessarily valid) has exactly the following format : <TAG_NAME>TAG_CONTENT</TAG_NAME>.
 * Among them, <TAG_NAME> is the start tag, and </TAG_NAME> is the end tag. The TAG_NAME in start and end tags
 * should be the same. A closed tag is valid if and only if the TAG_NAME and TAG_CONTENT are valid.
 * A valid TAG_NAME only contain upper-case letters, and has length in range [1,9]. Otherwise, the TAG_NAME is
 * invalid.
 * 3. A valid TAG_CONTENT may contain other valid closed tags, cdata and any characters (see note1) EXCEPT unmatched
 * <, unmatched start and end tag, and unmatched or closed tags with invalid TAG_NAME. Otherwise, the TAG_CONTENT
 * is invalid.
 * 5. A start tag is unmatched if no end tag exists with the same TAG_NAME, and vice versa. However, you also need
 * to consider the issue of unbalanced when tags are nested.
 * 6. A < is unmatched if you cannot find a subsequent >. And when you find a < or </, all the subsequent characters
 * until the next > should be parsed as TAG_NAME (not necessarily valid).
 * 7. The cdata has the following format : <![CDATA[CDATA_CONTENT]]>. The range of CDATA_CONTENT is defined as the
 * characters between <![CDATA[ and the first subsequent ]]>.
 * 8. CDATA_CONTENT may contain any characters. The function of cdata is to forbid the validator to parse
 * CDATA_CONTENT, so even it has some characters that can be parsed as tag (no matter valid or invalid), you
 * should treat it as regular characters.
 *
 * Valid Code Examples:
 *
 * Input: "<DIV>This is the first line <![CDATA[<div>]]></DIV>"
 *
 * Output: True
 *
 * Explanation:
 *
 * The code is wrapped in a closed tag : <DIV> and </DIV>.
 *
 * The TAG_NAME is valid, the TAG_CONTENT consists of some characters and cdata.
 *
 * Although CDATA_CONTENT has unmatched start tag with invalid TAG_NAME, it should be considered as plain text, not
 * parsed as tag.
 *
 * So TAG_CONTENT is valid, and then the code is valid. Thus return true.
 *
 *
 * Input: "<DIV>>>  ![cdata[]] <![CDATA[<div>]>]]>]]>>]</DIV>"
 *
 * Output: True
 *
 * Explanation:
 *
 * We first separate the code into : start_tag|tag_content|end_tag.
 *
 * start_tag -> "<DIV>"
 *
 * end_tag -> "</DIV>"
 *
 * tag_content could also be separated into : text1|cdata|text2.
 *
 * text1 -> ">>  ![cdata[]] "
 *
 * cdata -> "<![CDATA[<div>]>]]>", where the CDATA_CONTENT is "<div>]>"
 *
 * text2 -> "]]>>]"
 *
 *
 * The reason why start_tag is NOT "<DIV>>>" is because of the rule 6.
 * The reason why cdata is NOT "<![CDATA[<div>]>]]>]]>" is because of the rule 7.
 *
 * Invalid Code Examples:
 *
 * Input: "<A>  <B> </A>   </B>"
 * Output: False
 * Explanation: Unbalanced. If "<A>" is closed, then "<B>" must be unmatched, and vice versa.
 *
 * Input: "<DIV>  div tag is not closed  <DIV>"
 * Output: False
 *
 * Input: "<DIV>  unmatched <  </DIV>"
 * Output: False
 *
 * Input: "<DIV> closed tags with invalid tag name  <b>123</b> </DIV>"
 * Output: False
 *
 * Input: "<DIV> unmatched tags with invalid tag name  </1234567890> and <CDATA[[]]>  </DIV>"
 * Output: False
 *
 * Input: "<DIV>  unmatched start tag <B>  and unmatched end tag </C>  </DIV>"
 * Output: False
 *
 * Note:
 *
 * 1. For simplicity, you could assume the input code (including the any characters mentioned above) only contain
 * letters, digits, '<','>','/','!','[',']' and ' '.
 */
@RunWith(LeetCodeRunner.class)
public class Q591_TagValidator {

    /**
     * 这题的难点在于复杂的题目要求, 其实就是构造一个BNF 验证输入是否符合要求.
     * 这题可以画一个状态转移图, 然后构造跳跃表.
     * LeetCode 中最快的方式是通过代码判断状态配合栈来做的, 比较复杂, 耗时一样.
     */
    @Answer
    public boolean isValid(String code) {
        int status = 0, tagStart = -1;
        Deque<String> tags = new ArrayDeque<>();
        for (int i = 0, prev = 1; i < code.length(); i++, prev = status) {
            char c = code.charAt(i);
            status = JUMP[status][c];
            if (status == 0) {
                return false;
            }
            if ((prev == 2 || prev == 25) && status == 3
                    || prev == 4 && 16 <= status && status <= 24) {
                tagStart = i;
            } else if (prev == 3 && status == 1) {
                tags.push(code.substring(tagStart, i));
            } else if (16 <= prev && prev <= 24 && status == 1) {
                String endTag = code.substring(tagStart, i);
                if (tags.isEmpty() || !endTag.equals(tags.pop())) {
                    return false;
                }
                if (tags.isEmpty() && i < code.length() - 1) {
                    return false;
                }
            }
        }
        return status == 1 && tags.isEmpty();
    }

    private static final int[][] JUMP = new int[26][128];

    private static void fillAllCharacters(int[] jump, int next) {
        Arrays.fill(jump, '0', '9' + 1, next);
        Arrays.fill(jump, 'A', 'Z' + 1, next);
        Arrays.fill(jump, 'a', 'z' + 1, next);
        jump['<'] = next;
        jump['>'] = next;
        jump['/'] = next;
        jump['!'] = next;
        jump['['] = next;
        jump[']'] = next;
        jump[' '] = next;
    }

    private static void fillNameCharacters(int[] jump, int next) {
        Arrays.fill(jump, 'A', 'Z' + 1, next);
    }

    // 由于后期修改的原因, JUMP 中有部分元素没用到, 顺序也可以调整一下
    static {
        JUMP[0]['<'] = 25;
        fillNameCharacters(JUMP[25], 3);

        fillAllCharacters(JUMP[1], 1);
        JUMP[1]['<'] = 2;

        fillNameCharacters(JUMP[2], 3);
        JUMP[2]['/'] = 4;
        JUMP[2]['!'] = 6;

        fillNameCharacters(JUMP[3], 3);
        JUMP[3]['>'] = 1;

        fillNameCharacters(JUMP[4], 16);
        for (int i = 16; i < 24; i++) {
            fillNameCharacters(JUMP[i], i + 1);
            JUMP[i]['>'] = 1;
        }
        JUMP[24]['>'] = 1;

        JUMP[6]['['] = 7;
        JUMP[7]['C'] = 8;
        JUMP[8]['D'] = 9;
        JUMP[9]['A'] = 10;
        JUMP[10]['T'] = 11;
        JUMP[11]['A'] = 12;
        JUMP[12]['['] = 13;
        fillAllCharacters(JUMP[13], 13);
        JUMP[13][']'] = 14;
        fillAllCharacters(JUMP[14], 13);
        JUMP[14][']'] = 15;
        fillAllCharacters(JUMP[15], 13);
        JUMP[15]['>'] = 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create("<DIV>This is the first line <![CDATA[<div>]]></DIV>").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create("<DIV>>>  ![cdata[]] <![CDATA[<div>]>]]>]]>>]</DIV>").expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create("<A>  <B> </A>   </B>").expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.create("<DIV>  div tag is not closed  <DIV>").expect(false);

    @TestData
    public DataExpectation example5 = DataExpectation.create("<DIV>  unmatched <  </DIV>").expect(false);

    @TestData
    public DataExpectation example6 = DataExpectation
            .create("<DIV> closed tags with invalid tag name  <b>123</b> </DIV>").expect(false);

    @TestData
    public DataExpectation example7 = DataExpectation
            .create("<DIV> unmatched tags with invalid tag name  </1234567890> and <CDATA[[]]>  </DIV>").expect(false);

    @TestData
    public DataExpectation example8 = DataExpectation
            .create("<DIV>  unmatched start tag <B>  and unmatched end tag </C>  </DIV>").expect(false);

    @TestData
    public DataExpectation border = DataExpectation.create("").expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("1").expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("<DIV></DIV>").expect(true);

    @TestData
    public DataExpectation normal3 = DataExpectation.create("<div></div>").expect(false);

    @TestData
    public DataExpectation normal4 = DataExpectation.create("<DIV123456789></DIV123456789>").expect(false);

    @TestData
    public DataExpectation normal5 = DataExpectation.create("<>v</>").expect(false);

    @TestData
    public DataExpectation normal6 = DataExpectation.create("<A><![CDATA[]]></A>").expect(true);

    @TestData
    public DataExpectation normal7 = DataExpectation.create("<A>abc<B>def</B>ghi</A>").expect(true);

    @TestData
    public DataExpectation normal8 = DataExpectation.create("<A><![CDATA[ <![CDATA[]] ]]></A>").expect(true);

    @TestData
    public DataExpectation normal9 = DataExpectation.create("<A><![CDATA[1]]><![CDATA[2]]></A>").expect(true);

    @TestData
    public DataExpectation normal10 = DataExpectation.create("<![CDATA[wahaha]]]><![CDATA[]> wahaha]]>").expect(false);

    @TestData
    public DataExpectation normal11 = DataExpectation.create("<A></A><B></B>").expect(false);

}
