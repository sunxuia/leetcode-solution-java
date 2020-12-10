package q1450;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1410. HTML Entity Parser
 * https://leetcode.com/problems/html-entity-parser/
 *
 * HTML entity parser is the parser that takes HTML code as input and replace all the entities of the special characters
 * by the characters itself.
 *
 * The special characters and their entities for HTML are:
 *
 * Quotation Mark: the entity is &quot; and symbol character is ".
 * Single Quote Mark: the entity is &apos; and symbol character is '.
 * Ampersand: the entity is &amp; and symbol character is &.
 * Greater Than Sign: the entity is &gt; and symbol character is >.
 * Less Than Sign: the entity is &lt; and symbol character is <.
 * Slash: the entity is &frasl; and symbol character is /.
 *
 * Given the input text string to the HTML parser, you have to implement the entity parser.
 *
 * Return the text after replacing the entities by the special characters. *
 *
 * Example 1:
 *
 * Input: text = "&amp; is an HTML entity but &ambassador; is not."
 * Output: "& is an HTML entity but &ambassador; is not."
 * Explanation: The parser will replace the &amp; entity by &
 *
 * Example 2:
 *
 * Input: text = "and I quote: &quot;...&quot;"
 * Output: "and I quote: \"...\""
 *
 * Example 3:
 *
 * Input: text = "Stay home! Practice on Leetcode :)"
 * Output: "Stay home! Practice on Leetcode :)"
 *
 * Example 4:
 *
 * Input: text = "x &gt; y &amp;&amp; x &lt; y is always false"
 * Output: "x > y && x < y is always false"
 *
 * Example 5:
 *
 * Input: text = "leetcode.com&frasl;problemset&frasl;all"
 * Output: "leetcode.com/problemset/all"
 *
 *
 *
 * Constraints:
 *
 * 1 <= text.length <= 10^5
 * The string may contain any possible characters out of all the 256 ASCII characters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1410_HtmlEntityParser {

    @Answer
    public String entityParser(String text) {
        StringBuilder sb = new StringBuilder();
        int and = -1;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            sb.append(c);
            if (c == '&') {
                and = sb.length() - 1;
            } else if (c == ';' && and >= 0) {
                String escape = ESCAPE_MAP.get(sb.substring(and));
                if (escape != null) {
                    sb.setLength(and);
                    sb.append(escape);
                    and = -1;
                }
            }
        }
        return sb.toString();
    }

    private static final Map<String, String> ESCAPE_MAP = new HashMap<>() {{
        put("&quot;", "\"");
        put("&apos;", "'");
        put("&amp;", "&");
        put("&gt;", ">");
        put("&lt;", "<");
        put("&frasl;", "/");
    }};

    /**
     * 简单的做法
     */
    @Answer
    public String entityParser2(String text) {
        return text.replaceAll("&quot;", "\"")
                .replaceAll("&apos;", "'")
                .replaceAll("&gt;", ">")
                .replaceAll("&lt;", "<")
                .replaceAll("&frasl;", "/")
                .replaceAll("&amp;", "&");
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create("&amp; is an HTML entity but &ambassador; is not.")
            .expect("& is an HTML entity but &ambassador; is not.");

    @TestData
    public DataExpectation example2 = DataExpectation
            .create("and I quote: &quot;...&quot;")
            .expect("and I quote: \"...\"");

    @TestData
    public DataExpectation example3 = DataExpectation
            .create("Stay home! Practice on Leetcode :)")
            .expect("Stay home! Practice on Leetcode :)");

    @TestData
    public DataExpectation example4 = DataExpectation
            .create("x &gt; y &amp;&amp; x &lt; y is always false")
            .expect("x > y && x < y is always false");

    @TestData
    public DataExpectation example5 = DataExpectation
            .create("leetcode.com&frasl;problemset&frasl;all")
            .expect("leetcode.com/problemset/all");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("&amp;gt;").expect("&gt;");

}
