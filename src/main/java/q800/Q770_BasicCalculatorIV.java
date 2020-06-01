package q800;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/basic-calculator-iv/
 *
 * Given an expression such as expression = "e + 8 - a + 5" and an evaluation map such as {"e": 1} (given in terms of
 * evalvars = ["e"] and evalints = [1]), return a list of tokens representing the simplified expression, such as
 * ["-1*a","14"]
 *
 * An expression alternates chunks and symbols, with a space separating each chunk and symbol.
 * A chunk is either an expression in parentheses, a variable, or a non-negative integer.
 * A variable is a string of lowercase letters (not including digits.) Note that variables can be multiple
 * letters, and note that variables never have a leading coefficient or unary operator like "2x" or "-x".
 *
 * Expressions are evaluated in the usual order: brackets first, then multiplication, then addition and subtraction.
 * For example, expression = "1 + 2 * 3" has an answer of ["7"].
 *
 * The format of the output is as follows:
 *
 * For each term of free variables with non-zero coefficient, we write the free variables within a term in sorted
 * order lexicographically. For example, we would never write a term like "b*a*c", only "a*b*c".
 * Terms have degree equal to the number of free variables being multiplied, counting multiplicity. (For example,
 * "a*a*b*c" has degree 4.) We write the largest degree terms of our answer first, breaking ties by lexicographic
 * order ignoring the leading coefficient of the term.
 * The leading coefficient of the term is placed directly to the left with an asterisk separating it from the
 * variables (if they exist.)  A leading coefficient of 1 is still printed.
 * An example of a well formatted answer is ["-2*a*a*a", "3*a*a*b", "3*b*b", "4*a", "5*c", "-6"]
 * Terms (including constant terms) with coefficient 0 are not included.  For example, an expression of "0" has
 * an output of [].
 *
 * Examples:
 *
 * Input: expression = "e + 8 - a + 5", evalvars = ["e"], evalints = [1]
 * Output: ["-1*a","14"]
 *
 * Input: expression = "e - 8 + temperature - pressure",
 * evalvars = ["e", "temperature"], evalints = [1, 12]
 * Output: ["-1*pressure","5"]
 *
 * Input: expression = "(e + 8) * (e - 8)", evalvars = [], evalints = []
 * Output: ["1*e*e","-64"]
 *
 * Input: expression = "7 - 7", evalvars = [], evalints = []
 * Output: []
 *
 * Input: expression = "a * b * c + b * a * c * 4", evalvars = [], evalints = []
 * Output: ["5*a*b*c"]
 *
 * Input: expression = "((a - b) * (b - c) + (c - a)) * ((a - b) + (b - c) * (c - a))",
 * evalvars = [], evalints = []
 * Output: ["-1*a*a*b*b","2*a*a*b*c","-1*a*a*c*c","1*a*b*b*b","-1*a*b*b*c","-1*a*b*c*c","1*a*c*c*c","-1*b*b*b*c",
 * "2*b*b*c*c","-1*b*c*c*c","2*a*a*b","-2*a*a*c","-2*a*b*b","2*a*c*c","1*b*b*b","-1*b*b*c","1*b*c*c","-1*c*c*c",
 * "-1*a*a","1*a*b","1*a*c","-1*b*c"]
 *
 * Note:
 *
 * 1. expression will have length in range [1, 250].
 * 2. evalvars, evalints will have equal lengths in range [0, 100].
 */
@RunWith(LeetCodeRunner.class)
public class Q770_BasicCalculatorIV {

    @Answer
    public List<String> basicCalculatorIV(String expression, String[] evalvars, int[] evalints) {
        Map<String, Integer> context = new HashMap<>();
        for (int i = 0; i < evalvars.length; i++) {
            context.put(evalvars[i], evalints[i]);
        }

        List<Term> terms = getTerms(expression);
        Expression exp = simplifyExpression(terms, context);
        return exp.toList();
    }

    // 词法解析
    private List<Term> getTerms(String expression) {
        List<Term> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= expression.length(); i++) {
            char c = i == expression.length() ? ' ' : expression.charAt(i);
            Term symbol = Term.SYMBOLS.get(c);
            if (symbol == null && c != ' ') {
                sb.append(c);
                continue;
            }
            if (sb.length() > 0) {
                String value = sb.toString();
                if ('a' <= value.charAt(0) && value.charAt(0) <= 'z') {
                    res.add(new Term(TermType.VARIABLE, value));
                } else {
                    res.add(new Term(TermType.NUMBER, value));
                }
                sb.setLength(0);
            }
            if (symbol != null) {
                res.add(symbol);
            }
        }
        return res;
    }

    private static class Term {

        final TermType type;
        final String value;

        public Term(TermType type, String value) {
            this.type = type;
            this.value = value;
        }

        static Map<Character, Term> SYMBOLS = new HashMap<>();

        static {
            SYMBOLS.put('(', new Term(TermType.LEFT_BRACKET, "("));
            SYMBOLS.put(')', new Term(TermType.RIGHT_BRACKET, ")"));
            SYMBOLS.put('+', new Term(TermType.PLUS, "+"));
            SYMBOLS.put('-', new Term(TermType.MINUS, "-"));
            SYMBOLS.put('*', new Term(TermType.MULTIPLE, "*"));
        }
    }

    private enum TermType {
        VARIABLE,
        NUMBER,
        LEFT_BRACKET,
        RIGHT_BRACKET,
        PLUS,
        MINUS,
        MULTIPLE
    }

    // 语法解析并计算
    private Expression simplifyExpression(List<Term> terms, Map<String, Integer> context) {
        Deque<Expression> expressions = new ArrayDeque<>();
        Deque<TermType> operators = new ArrayDeque<>();

        for (int i = 0; i <= terms.size(); i++) {
            Term term = i == terms.size() ? Term.SYMBOLS.get('+') : terms.get(i);
            switch (term.type) {
                case NUMBER:
                    expressions.push(new Expression(Integer.parseInt(term.value)));
                    break;
                case VARIABLE:
                    Integer num = context.get(term.value);
                    if (num == null) {
                        expressions.push(new Expression(term.value));
                    } else {
                        expressions.push(new Expression(num));
                    }
                    break;
                case LEFT_BRACKET:
                    operators.push(term.type);
                    break;
                case MULTIPLE:
                    if (operators.isEmpty() || operators.peek() != TermType.MULTIPLE) {
                        operators.push(term.type);
                        break;
                    }
                case RIGHT_BRACKET:
                case PLUS:
                case MINUS:
                    if (operators.isEmpty() || operators.peek() == TermType.LEFT_BRACKET) {
                        if (term.type == TermType.RIGHT_BRACKET) {
                            operators.pop();
                        } else {
                            operators.push(term.type);
                        }
                    } else {
                        Expression right = expressions.pop();
                        Expression left = expressions.pop();
                        TermType operator = operators.pop();
                        switch (operator) {
                            case PLUS:
                                left = left.plus(right);
                                break;
                            case MULTIPLE:
                                left = left.multiply(right);
                                break;
                            case MINUS:
                                left = left.plus(MINUS_ONE.multiply(right));
                                break;
                            default:
                        }
                        expressions.push(left);
                        i--;
                    }
                    break;
                default:
            }
        }
        return expressions.getFirst();
    }

    // 表达式
    private static class Expression {

        // 保存变量和对应的系数
        private Map<ArrayList<String>, Integer> variables = new HashMap<>();

        Expression() {}

        Expression(int number) {
            if (number != 0) {
                variables.put(new ArrayList<>(), number);
            }
        }

        Expression(String variable) {
            ArrayList<String> list = new ArrayList<>();
            list.add(variable);
            variables.put(list, 1);
        }

        // this * other
        Expression multiply(Expression other) {
            Expression res = new Expression();
            Map<ArrayList<String>, Integer> vs = res.variables;
            for (ArrayList<String> thisKey : this.variables.keySet()) {
                for (ArrayList<String> thatKey : other.variables.keySet()) {
                    ArrayList<String> key = new ArrayList<>();
                    key.addAll(thisKey);
                    key.addAll(thatKey);
                    key.sort(Comparator.naturalOrder());
                    int num = vs.getOrDefault(key, 0)
                            + this.variables.get(thisKey) * other.variables.get(thatKey);
                    if (num != 0) {
                        vs.put(key, num);
                    } else {
                        vs.remove(key);
                    }
                }
            }
            return res;
        }

        // this + other
        Expression plus(Expression other) {
            Expression res = new Expression();
            res.variables = new HashMap<>(this.variables);
            for (ArrayList<String> key : other.variables.keySet()) {
                int num = res.variables.getOrDefault(key, 0)
                        + other.variables.get(key);
                if (num != 0) {
                    res.variables.put(key, num);
                } else {
                    res.variables.remove(key);
                }
            }
            return res;
        }

        List<String> toList() {
            List<ArrayList<String>> keys = new ArrayList<>(variables.keySet());
            keys.sort((a, b) -> {
                if (a.size() != b.size()) {
                    return b.size() - a.size();
                }
                for (int i = 0; i < a.size(); i++) {
                    if (a.get(i).compareTo(b.get(i)) != 0) {
                        return a.get(i).compareTo(b.get(i));
                    }
                }
                return variables.get(a) - variables.get(b);
            });

            List<String> res = new ArrayList<>();
            for (ArrayList<String> key : keys) {
                StringBuilder sb = new StringBuilder();
                sb.append(variables.get(key));
                for (String variableName : key) {
                    sb.append('*').append(variableName);
                }
                res.add(sb.toString());
            }
            return res;
        }
    }

    private static final Expression MINUS_ONE = new Expression(-1);

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("e + 8 - a + 5", new String[]{"e"}, new int[]{1})
            .expect(Arrays.asList("-1*a", "14"));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("e - 8 + temperature - pressure", new String[]{"e", "temperature"}, new int[]{1, 12})
            .expect(Arrays.asList("-1*pressure", "5"));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith("(e + 8) * (e - 8)", new String[0], new int[0])
            .expect(Arrays.asList("1*e*e", "-64"));

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith("7 - 7", new String[0], new int[0])
            .expect(Collections.emptyList());

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith("a * b * c + b * a * c * 4", new String[0], new int[0])
            .expect(Arrays.asList("5*a*b*c"));

    @TestData
    public DataExpectation example6 = DataExpectation
            .createWith("((a - b) * (b - c) + (c - a)) * ((a - b) + (b - c) * (c - a))", new String[0], new int[0])
            .expect(Arrays.asList("-1*a*a*b*b", "2*a*a*b*c", "-1*a*a*c*c", "1*a*b*b*b", "-1*a*b*b*c", "-1*a*b*c*c",
                    "1*a*c*c*c", "-1*b*b*b*c", "2*b*b*c*c", "-1*b*c*c*c", "2*a*a*b", "-2*a*a*c", "-2*a*b*b", "2*a*c*c",
                    "1*b*b*b", "-1*b*b*c", "1*b*c*c", "-1*c*c*c", "-1*a*a", "1*a*b", "1*a*c", "-1*b*c"));

}
