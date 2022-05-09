package q1900;

import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1896. Minimum Cost to Change the Final Value of Expression
 * https://leetcode.com/problems/minimum-cost-to-change-the-final-value-of-expression/
 *
 * You are given a valid boolean expression as a string expression consisting of the characters '1','0','&' (bitwise AND
 * operator),'|' (bitwise OR operator),'(', and ')'.
 *
 * For example, "()1|1" and "(1)&()" are not valid while "1", "(((1))|(0))", and "1|(0&(1))" are valid expressions.
 *
 * Return the minimum cost to change the final value of the expression.
 *
 * For example, if expression = "1|1|(0&0)&1", its value is 1|1|(0&0)&1 = 1|1|0&1 = 1|0&1 = 1&1 = 1. We want to apply
 * operations so that the new expression evaluates to 0.
 *
 * The cost of changing the final value of an expression is the number of operations performed on the expression. The
 * types of operations are described as follows:
 *
 * Turn a '1' into a '0'.
 * Turn a '0' into a '1'.
 * Turn a '&' into a '|'.
 * Turn a '|' into a '&'.
 *
 * Note: '&' does not take precedence over '|' in the order of calculation. Evaluate parentheses first, then in
 * left-to-right order.
 *
 * Example 1:
 *
 * Input: expression = "1&(0|1)"
 * Output: 1
 * Explanation: We can turn "1&(0|1)" into "1&(0&1)" by changing the '|' to a '&' using 1 operation.
 * The new expression evaluates to 0.
 *
 * Example 2:
 *
 * Input: expression = "(0&0)&(0&0&0)"
 * Output: 3
 * Explanation: We can turn "(0&0)&(0&0&0)" into "(0|1)|(0&0&0)" using 3 operations.
 * The new expression evaluates to 1.
 *
 * Example 3:
 *
 * Input: expression = "(0|(1|0&1))"
 * Output: 1
 * Explanation: We can turn "(0|(1|0&1))" into "(0|(0|0&1))" using 1 operation.
 * The new expression evaluates to 0.
 *
 * Constraints:
 *
 * 1 <= expression.length <= 10^5
 * expression only contains '1','0','&','|','(', and ')'
 * All parentheses are properly matched.
 * There will be no empty parentheses (i.e: "()" is not a substring of expression).
 */
@RunWith(LeetCodeRunner.class)
public class Q1896_MinimumCostToChangeTheFinalValueOfExpression {

    @Answer
    public int minOperationsToFlip(String expression) {
        Node root = constructTree(expression);
        root.initialValue();
        return root.minCost();
    }

    private static abstract class Node {

        // 节点的值
        int value;

        // 节点的左右分支
        Node left, right;

        // 初始化节点的值
        abstract int initialValue();

        // 计算最小开销
        abstract int minCost();

    }

    private static class ValueNode extends Node {

        ValueNode(int value) {
            this.value = value;
        }

        @Override
        int initialValue() {
            return value;
        }

        @Override
        int minCost() {
            return 1;
        }
    }

    private static class AndNode extends Node {

        @Override
        int initialValue() {
            value = left.initialValue() & right.initialValue();
            return value;
        }

        @Override
        int minCost() {
            if (value == 1) {
                return Math.min(left.minCost(), right.minCost());
            } else if (left.value == 0 && right.value == 0) {
                return 1 + Math.min(left.minCost(), right.minCost());
            } else {
                return 1;
            }
        }
    }

    private static class OrNode extends Node {

        @Override
        int initialValue() {
            value = left.initialValue() | right.initialValue();
            return value;
        }

        @Override
        int minCost() {
            if (value == 0) {
                return Math.min(left.minCost(), right.minCost());
            } else if (left.value == 1 && right.value == 1) {
                return 1 + Math.min(left.minCost(), right.minCost());
            } else {
                return 1;
            }
        }
    }

    private Node constructTree(String expression) {
        Stack<Node> stack = new Stack<>();
        // 哨兵
        stack.push(null);
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            Node prev = stack.peek();
            Node node;
            switch (c) {
                case '0':
                case '1':
                    node = new ValueNode(c - '0');
                    if (prev == null) {
                        stack.push(node);
                    } else {
                        prev.right = node;
                    }
                    break;
                case '&':
                    node = new AndNode();
                    node.left = prev;
                    stack.pop();
                    stack.push(node);
                    break;
                case '|':
                    node = new OrNode();
                    node.left = prev;
                    stack.pop();
                    stack.push(node);
                    break;
                case '(':
                    stack.push(null);
                    break;
                case ')':
                    // () 中的节点
                    stack.pop();
                    // ( 对应的null
                    stack.pop();
                    node = prev;
                    prev = stack.peek();
                    if (prev == null) {
                        stack.push(node);
                    } else {
                        prev.right = node;
                    }
                    break;
                default:
            }
        }
        return stack.peek();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("1&(0|1)").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create("(0&0)&(0&0&0)").expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create("(0|(1|0&1))").expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("0|((0|(0&0)))").expect(1);

}
