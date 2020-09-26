package q1100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1096. Brace Expansion II
 * https://leetcode.com/problems/brace-expansion-ii/
 *
 * Under a grammar given below, strings can represent a set of lowercase words.  Let's use R(expr) to denote the set of
 * words the expression represents.
 *
 * Grammar can best be understood through simple examples:
 *
 * Single letters represent a singleton set containing that word.
 *
 * R("a") = {"a"}
 * R("w") = {"w"}
 *
 * When we take a comma delimited list of 2 or more expressions, we take the union of possibilities.
 *
 * R("{a,b,c}") = {"a","b","c"}
 * R("{{a,b},{b,c}}") = {"a","b","c"} (notice the final set only contains each word at most once)
 *
 * When we concatenate two expressions, we take the set of possible concatenations between two words where the first
 * word comes from the first expression and the second word comes from the second expression.
 *
 * R("{a,b}{c,d}") = {"ac","ad","bc","bd"}
 * R("a{b,c}{d,e}f{g,h}") = {"abdfg", "abdfh", "abefg", "abefh", "acdfg", "acdfh", "acefg", "acefh"}
 *
 * Formally, the 3 rules for our grammar:
 *
 * For every lowercase letter x, we have R(x) = {x}
 * For expressions e_1, e_2, ... , e_k with k >= 2, we have R({e_1,e_2,...}) = R(e_1) ∪ R(e_2)∪ ...
 * For expressions e_1 and e_2, we have R(e_1 + e_2) = {a + b for (a, b) in R(e_1) X R(e_2)}, where + denotes
 * concatenation, and X denotes the cartesian product.
 *
 * Given an expression representing a set of words under the given grammar, return the sorted list of words that the
 * expression represents.
 *
 * Example 1:
 *
 * Input: "{a,b}{c,{d,e}}"
 * Output: ["ac","ad","ae","bc","bd","be"]
 *
 * Example 2:
 *
 * Input: "{{a,z},a{b,c},{ab,z}}"
 * Output: ["a","ab","ac","z"]
 * Explanation: Each distinct word is written only once in the final answer.
 *
 * Constraints:
 *
 * 1 <= expression.length <= 60
 * expression[i] consists of '{', '}', ','or lowercase English letters.
 * The given expression represents a set of words based on the grammar given in the description.
 */
@RunWith(LeetCodeRunner.class)
public class Q1096_BraceExpansionII {

    /**
     * 构造抽象语法树的题目
     */
    @Answer
    public List<String> braceExpansionII(String expression) {
        List<Token> tokens = getTokens(expression);
        Node root = buildProduct(tokens, 0, tokens.size() - 1);
        Set<String> set = root.words();

        List<String> res = new ArrayList<>(set);
        res.sort(String::compareTo);
        return res;
    }

    private List<Token> getTokens(String expression) {
        List<Token> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (!PUNCTUATIONS.containsKey(c)) {
                sb.append(c);
                continue;
            }
            if (sb.length() > 0) {
                res.add(new Token(TokenType.WORD, sb.toString()));
                sb.setLength(0);
            }
            res.add(PUNCTUATIONS.get(c));
        }
        if (sb.length() > 0) {
            res.add(new Token(TokenType.WORD, sb.toString()));
        }
        return res;
    }

    private static class Token {

        final TokenType type;

        final String word;

        private Token(TokenType type, String word) {
            this.type = type;
            this.word = word;
        }
    }

    private enum TokenType {
        LEFT_BRACE,
        RIGHT_BRACE,
        COMMA,
        WORD,
    }

    private static Map<Character, Token> PUNCTUATIONS = new HashMap<>() {{
        put('{', new Token(TokenType.LEFT_BRACE, "{"));
        put('}', new Token(TokenType.RIGHT_BRACE, "}"));
        put(',', new Token(TokenType.COMMA, ","));
    }};

    /**
     * 计算乘积 (递归方式)
     */
    private Node buildProduct(List<Token> tokens, int start, int end) {
        Node res = null, node;
        int leftBrace = 0, braceCount = 0;
        for (int i = start; i <= end; i++) {
            Token token = tokens.get(i);
            switch (token.type) {
                case LEFT_BRACE:
                    if (++braceCount == 1) {
                        leftBrace = i;
                    }
                    break;
                case RIGHT_BRACE:
                    if (--braceCount == 0) {
                        node = buildUnion(tokens, leftBrace, i);
                        res = res == null ? node : new ProductNode(res, node);
                    }
                    break;
                case WORD:
                    if (braceCount == 0) {
                        node = new WordNode(token.word);
                        res = res == null ? node : new ProductNode(res, node);
                    }
                    break;
                default:
            }
        }
        return res == null ? EMPTY_NODE : res;
    }

    /**
     * 计算并集 (递归方式)
     */
    private Node buildUnion(List<Token> tokens, int start, int end) {
        Node res = EMPTY_NODE;
        int braceCount = 0;
        for (int i = start + 1, prev = i; i <= end; i++) {
            Token token = tokens.get(i);
            TokenType type = i == end ? TokenType.COMMA : token.type;
            switch (type) {
                case LEFT_BRACE:
                    braceCount++;
                    break;
                case RIGHT_BRACE:
                    braceCount--;
                    break;
                case COMMA:
                    if (braceCount == 0) {
                        Node right = buildProduct(tokens, prev, i - 1);
                        res = new UnionNode(res, right);
                        prev = i + 1;
                    }
                    break;
                default:
            }
        }
        return res;
    }

    private interface Node {

        Set<String> words();

    }

    private static final Node EMPTY_NODE = Collections::emptySet;

    private static class WordNode implements Node {

        final String word;

        public WordNode(String word) {
            this.word = word;
        }

        @Override
        public Set<String> words() {
            return Collections.singleton(word);
        }

        @Override
        public String toString() {
            return word;
        }
    }

    private static class UnionNode implements Node {

        final Node left, right;

        public UnionNode(Node left, Node right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public Set<String> words() {
            Set<String> res = new HashSet<>();
            res.addAll(left.words());
            res.addAll(right.words());
            return res;
        }

        @Override
        public String toString() {
            return "(" + left + " + " + right + ")";
        }
    }

    private static class ProductNode implements Node {

        final Node left, right;

        public ProductNode(Node left, Node right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public Set<String> words() {
            Set<String> leftWords = left.words();
            Set<String> rightWords = right.words();
            Set<String> res = new HashSet<>();
            for (String leftWord : leftWords) {
                for (String rightWord : rightWords) {
                    res.add(leftWord + rightWord);
                }
            }
            return res;
        }

        @Override
        public String toString() {
            return "(" + left + " * " + right + ")";
        }
    }

    /**
     * 使用栈的方式求抽象语法树
     */
    @Answer
    public List<String> braceExpansionII2(String expression) {
        List<Token> tokens = getTokens(expression);
        Node root = buildAst(tokens);
        Set<String> set = root.words();

        List<String> res = new ArrayList<>(set);
        res.sort(String::compareTo);
        return res;
    }

    /**
     * 求抽象语法树 (栈方式)
     */
    private Node buildAst(List<Token> tokens) {
        Stack<Node> nodes = new Stack<>();
        Stack<Character> opers = new Stack<>();
        // 哨兵
        opers.push('{');
        for (Token token : tokens) {
            Node node;
            switch (token.type) {
                case LEFT_BRACE:
                    opers.push('{');
                    break;
                case WORD:
                    node = new WordNode(token.word);
                    if (opers.peek() == '*') {
                        // 与前面的数相乘
                        opers.pop();
                        node = new ProductNode(nodes.pop(), node);
                    }
                    nodes.push(node);
                    opers.push('*');
                    break;
                case COMMA:
                    // 逗号分隔则前面2 个元素相加
                    opers.pop();
                    node = nodes.pop();
                    if (opers.pop() != '{') {
                        node = new UnionNode(nodes.pop(), node);
                    }
                    nodes.push(node);
                    opers.push('+');
                    break;
                case RIGHT_BRACE:
                    // 首先将集合中的多个元素相加 (类似comma 中的操作)
                    if (opers.pop() == '{') {
                        // 空集合, 否则栈顶应该是 '*'
                        node = EMPTY_NODE;
                    } else if (opers.pop() == '{') {
                        // 单元素集合, 否则栈顶应该是 '+'
                        node = nodes.pop();
                    } else {
                        // 末尾元素与之前元素相加
                        node = nodes.pop();
                        node = new UnionNode(nodes.pop(), node);
                    }
                    if (opers.peek() == '*') {
                        // 集合前面的操作符是 *
                        opers.pop();
                        node = new ProductNode(nodes.pop(), node);
                    }
                    nodes.push(node);
                    opers.push('*');
                    break;
                default:
            }
        }
        return nodes.peek();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("{a,b}{c,{d,e}}")
            .expect(Arrays.asList("ac", "ad", "ae", "bc", "bd", "be"));

    @TestData
    public DataExpectation example2 = DataExpectation.create("{{a,z},a{b,c},{ab,z}}")
            .expect(Arrays.asList("a", "ab", "ac", "z"));

    @TestData
    public DataExpectation normal1 = DataExpectation.create("ab").expect(Collections.singletonList("ab"));

    @TestData
    public DataExpectation normal2 = DataExpectation.create("ab{}").expect(Collections.emptyList());

    @TestData
    public DataExpectation normal3 = DataExpectation.create("n{{c,g},{h,j},l}a{{a,{x,ia,o},w},er,a{x,ia,o}w}n")
            .expect(Arrays.asList(
                    "ncaaiawn", "ncaan", "ncaaown", "ncaaxwn", "ncaern", "ncaian", "ncaon", "ncawn", "ncaxn",
                    "ngaaiawn", "ngaan", "ngaaown", "ngaaxwn", "ngaern", "ngaian", "ngaon", "ngawn", "ngaxn",
                    "nhaaiawn", "nhaan", "nhaaown", "nhaaxwn", "nhaern", "nhaian", "nhaon", "nhawn", "nhaxn",
                    "njaaiawn", "njaan", "njaaown", "njaaxwn", "njaern", "njaian", "njaon", "njawn", "njaxn",
                    "nlaaiawn", "nlaan", "nlaaown", "nlaaxwn", "nlaern", "nlaian", "nlaon", "nlawn", "nlaxn"));

}
