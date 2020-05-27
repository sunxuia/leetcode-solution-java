package q750;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/parse-lisp-expression/
 *
 * You are given a string expression representing a Lisp-like expression to return the integer value of.
 *
 * The syntax for these expressions is given as follows.
 *
 * An expression is either an integer, a let-expression, an add-expression, a mult-expression, or an assigned
 * variable. Expressions always evaluate to a single integer.
 *
 * (An integer could be positive or negative.)
 *
 * A let-expression takes the form (let v1 e1 v2 e2 ... vn en expr), where let is always the string "let", then there
 * are 1 or more pairs of alternating variables and expressions, meaning that the first variable v1 is assigned the
 * value of the expression e1, the second variable v2 is assigned the value of the expression e2, and so on
 * sequentially; and then the value of this let-expression is the value of the expression expr.
 *
 * An add-expression takes the form (add e1 e2) where add is always the string "add", there are always two
 * expressions e1, e2, and this expression evaluates to the addition of the evaluation of e1 and the evaluation of e2.
 *
 * A mult-expression takes the form (mult e1 e2) where mult is always the string "mult", there are always two
 * expressions e1, e2, and this expression evaluates to the multiplication of the evaluation of e1 and the evaluation
 * of e2.
 *
 * For the purposes of this question, we will use a smaller subset of variable names. A variable starts with a
 * lowercase letter, then zero or more lowercase letters or digits. Additionally for your convenience, the names
 * "add", "let", or "mult" are protected and will never be used as variable names.
 *
 * Finally, there is the concept of scope. When an expression of a variable name is evaluated, within the context of
 * that evaluation, the innermost scope (in terms of parentheses) is checked first for the value of that variable,
 * and then outer scopes are checked sequentially. It is guaranteed that every expression is legal. Please see the
 * examples for more details on scope.
 *
 * Evaluation Examples:
 *
 * Input: (add 1 2)
 * Output: 3
 *
 * Input: (mult 3 (add 2 3))
 * Output: 15
 *
 * Input: (let x 2 (mult x 5))
 * Output: 10
 *
 * Input: (let x 2 (mult x (let x 3 y 4 (add x y))))
 * Output: 14
 * Explanation: In the expression (add x y), when checking for the value of the variable x,
 * we check from the innermost scope to the outermost in the context of the variable we are trying to evaluate.
 * Since x = 3 is found first, the value of x is 3.
 *
 * Input: (let x 3 x 2 x)
 * Output: 2
 * Explanation: Assignment in let statements is processed sequentially.
 *
 * Input: (let x 1 y 2 x (add x y) (add x y))
 * Output: 5
 * Explanation: The first (add x y) evaluates as 3, and is assigned to x.
 * The second (add x y) evaluates as 3+2 = 5.
 *
 * Input: (let x 2 (add (let x 3 (let x 4 x)) x))
 * Output: 6
 * Explanation: Even though (let x 4 x) has a deeper scope, it is outside the context
 * of the final x in the add-expression.  That final x will equal 2.
 *
 * Input: (let a1 3 b2 (add a1 1) b2)
 * Output 4
 * Explanation: Variable names can contain digits after the first character.
 *
 * Note:
 * The given string expression is well formatted: There are no leading or trailing spaces, there is only a single
 * space separating different components of the string, and no space between adjacent parentheses. The expression is
 * guaranteed to be legal and evaluate to an integer.
 * The length of expression is at most 2000. (It is also non-empty, as that would not be a legal expression.)
 * The answer and all intermediate calculations of that answer are guaranteed to fit in a 32-bit integer.
 */
@RunWith(LeetCodeRunner.class)
public class Q736_ParseLispExpression {

    /**
     * 就是解释执行器, 用到了java 的解释器模式.
     * LeetCode 中比较快的解法把下面的几个步骤合起来了.
     */
    @Answer
    public int evaluate(String expression) {
        List<Term> terms = getTerms(expression);
        nextTerm = 0;
        Expression exp = getExpression(terms);
        return exp.execute(Collections.emptyMap());
    }

    private List<Term> getTerms(String expression) {
        List<Term> terms = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = expression.length(); i <= len; i++) {
            char c = i == len ? ' ' : expression.charAt(i);
            if (Term.STOP_CHARS.containsKey(c)) {
                String value = sb.toString();
                if (!value.isEmpty()) {
                    if (Term.KEY_WORDS.containsKey(value)) {
                        terms.add(Term.KEY_WORDS.get(value));
                    } else if ('a' <= value.charAt(0) && value.charAt(0) <= 'z') {
                        terms.add(new Term(TermType.VARIABLE, value));
                    } else {
                        terms.add(new Term(TermType.NUMBER, value));
                    }
                    sb.setLength(0);
                }
                Term stopTerm = Term.STOP_CHARS.get(c);
                if (stopTerm != null) {
                    terms.add(stopTerm);
                }
            } else {
                sb.append(c);
            }
        }
        return terms;
    }

    private enum TermType {
        KEYWORD,
        LEFT_PARENTHESES,
        RIGHT_PARENTHESES,
        NUMBER,
        VARIABLE
    }

    private static class Term {

        final TermType type;
        final String value;

        public Term(TermType type, String value) {
            this.type = type;
            this.value = value;
        }

        static Map<Character, Term> STOP_CHARS = new HashMap<>();

        static Map<String, Term> KEY_WORDS = new HashMap<>();

        static {
            STOP_CHARS.put('(', new Term(TermType.LEFT_PARENTHESES, "("));
            STOP_CHARS.put(')', new Term(TermType.RIGHT_PARENTHESES, ")"));
            STOP_CHARS.put(' ', null);

            KEY_WORDS.put("let", new Term(TermType.KEYWORD, "let"));
            KEY_WORDS.put("add", new Term(TermType.KEYWORD, "add"));
            KEY_WORDS.put("mult", new Term(TermType.KEYWORD, "mult"));
        }
    }

    private Expression getExpression(List<Term> terms) {
        Term term = terms.get(nextTerm++);
        switch (term.type) {
            case NUMBER:
                return new ConstNumberExpression(term.value);
            case LEFT_PARENTHESES:
                ScriptExpression comExp = new ScriptExpression(new ArrayList<>());
                while (terms.get(nextTerm).type != TermType.RIGHT_PARENTHESES) {
                    comExp.expressions.add(getExpression(terms));
                }
                nextTerm++;
                return comExp;
            case RIGHT_PARENTHESES:
                nextTerm--;
                return null;
            case KEYWORD:
                switch (term.value) {
                    case "let":
                        LetExpression letExpression = new LetExpression(new ArrayList<>(), new ArrayList<>());
                        while (true) {
                            Term next = terms.get(nextTerm);
                            if (next.type != TermType.VARIABLE) {
                                return letExpression;
                            }
                            nextTerm++;
                            Expression exp = getExpression(terms);
                            if (exp == null) {
                                // 是左括号
                                nextTerm--;
                                return letExpression;
                            }
                            letExpression.names.add(next.value);
                            letExpression.expressions.add(exp);
                        }
                    case "add":
                        Expression addA = getExpression(terms);
                        Expression addB = getExpression(terms);
                        return new AddExpression(addA, addB);
                    default:
                        // mult
                        Expression multA = getExpression(terms);
                        Expression multB = getExpression(terms);
                        return new MultExpression(multA, multB);
                }
            default:
                // variable
                return new VariableExpression(term.value);
        }
    }

    private int nextTerm = 0;

    private interface Expression {

        int execute(Map<String, Integer> context);

    }

    private static class ConstNumberExpression implements Expression {

        final int value;

        ConstNumberExpression(String str) {
            value = Integer.parseInt(str);
        }

        @Override
        public int execute(Map<String, Integer> context) {
            return value;
        }
    }

    private static class VariableExpression implements Expression {

        final String name;

        VariableExpression(String name) {
            this.name = name;
        }

        @Override
        public int execute(Map<String, Integer> context) {
            return context.get(name);
        }
    }

    private static class LetExpression implements Expression {

        final List<String> names;

        final List<Expression> expressions;

        LetExpression(List<String> names, List<Expression> expressions) {
            this.names = names;
            this.expressions = expressions;
        }

        @Override
        public int execute(Map<String, Integer> context) {
            for (int i = 0; i < names.size(); i++) {
                context.put(names.get(i), expressions.get(i).execute(context));
            }
            return 0;
        }
    }

    private static class AddExpression implements Expression {

        final Expression a, b;

        AddExpression(Expression a, Expression b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public int execute(Map<String, Integer> context) {
            return a.execute(context) + b.execute(context);
        }
    }

    private static class MultExpression implements Expression {

        final Expression a, b;

        private MultExpression(Expression a, Expression b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public int execute(Map<String, Integer> context) {
            return a.execute(context) * b.execute(context);
        }
    }

    private static class ScriptExpression implements Expression {

        final List<Expression> expressions;


        ScriptExpression(List<Expression> expressions) {
            this.expressions = expressions;
        }

        @Override
        public int execute(Map<String, Integer> context) {
            context = new HashMap<>(context);
            int res = 0;
            for (Expression expression : expressions) {
                res = expression.execute(context);
            }
            return res;
        }
    }


    @TestData
    public DataExpectation example1 = DataExpectation.create("(add 1 2)").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create("(mult 3 (add 2 3))").expect(15);

    @TestData
    public DataExpectation example3 = DataExpectation.create("(let x 2 (mult x 5))").expect(10);

    @TestData
    public DataExpectation example4 = DataExpectation.create("(let x 2 (mult x (let x 3 y 4 (add x y))))").expect(14);

    @TestData
    public DataExpectation example5 = DataExpectation.create("(let x 3 x 2 x)").expect(2);

    @TestData
    public DataExpectation example6 = DataExpectation.create("(let x 1 y 2 x (add x y) (add x y))").expect(5);

    @TestData
    public DataExpectation example7 = DataExpectation.create("(let x 2 (add (let x 3 (let x 4 x)) x))").expect(6);

    @TestData
    public DataExpectation example8 = DataExpectation.create("(let a1 3 b2 (add a1 1) b2)").expect(4);

}
