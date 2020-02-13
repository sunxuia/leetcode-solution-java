package q400;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.DataExpectationBuilder;

/**
 * https://leetcode.com/problems/mini-parser/
 *
 * Given a nested list of integers represented as a string, implement a parser to deserialize it.
 *
 * Each element is either an integer, or a list -- whose elements may also be integers or other lists.
 *
 * Note: You may assume that the string is well-formed:
 *
 * String is non-empty.
 * String does not contain white spaces.
 * String contains only digits 0-9, [, - ,, ].
 *
 * Example 1:
 *
 * Given s = "324",
 *
 * You should return a NestedInteger object which contains a single integer 324.
 *
 * Example 2:
 *
 * Given s = "[123,[456,[789]]]",
 *
 * Return a NestedInteger object containing a nested list with 2 elements:
 *
 * > 1. An integer containing value 123.
 * > 2. A nested list containing two elements:
 * >   i.  An integer containing value 456.
 * >   ii. A nested list with one element:
 * >     a. An integer containing value 789.
 */
@RunWith(LeetCodeRunner.class)
public class Q385_MiniParser {

    // This is the interface that allows for creating nested lists.
// You should not implement it, or speculate about its implementation
    private static class NestedInteger {

        private Integer value;

        private List<NestedInteger> list;

        // Constructor initializes an empty nested list.
        public NestedInteger() {

        }

        // Constructor initializes a single integer.
        public NestedInteger(int value) {
            this.value = value;
        }

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger() {
            return value != null;
        }

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger() {
            return value;
        }

        // Set this NestedInteger to hold a single integer.
        public void setInteger(int value) {
            this.value = value;
        }

        // Set this NestedInteger to hold a nested list and adds a nested integer to it.
        public void add(NestedInteger ni) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(ni);
        }

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList() {
            if (value != null) {
                return null;
            }
            if (list == null) {
                list = new ArrayList<>();
            }
            return list;
        }
    }

    @Answer
    public NestedInteger deserialize(String s) {
        ArrayDeque<NestedInteger> stack = new ArrayDeque<>();
        stack.push(new NestedInteger());
        char[] sc = s.toCharArray();
        int sign = 1, num = 0;
        for (int i = 0; i < sc.length; i++) {
            if (sc[i] == '-') {
                sign = -1;
            } else if ('0' <= sc[i] && sc[i] <= '9') {
                num = num * 10 + sc[i] - '0';
                if (i == sc.length - 1 || sc[i + 1] < '0' || sc[i + 1] > '9') {
                    stack.element().add(new NestedInteger(sign * num));
                    sign = 1;
                    num = 0;
                }
            } else if (sc[i] == '[') {
                NestedInteger list = new NestedInteger();
                stack.element().add(list);
                stack.push(list);
            } else if (sc[i] == ']') {
                stack.pop();
            } else {
                // sc[i] == ',', do nothing
            }
        }
        return stack.getFirst().getList().get(0);
    }

    @TestData
    public DataExpectation example1() {
        NestedInteger expected = new NestedInteger(324);
        DataExpectationBuilder builder = new DataExpectationBuilder();
        builder.addArgument("324");
        builder.expect(expected);
        builder.assertMethod(this::assertMethod);
        return builder.build();
    }

    private void assertMethod(NestedInteger expected, NestedInteger actual) {
        Assert.assertEquals(expected.isInteger(), actual.isInteger());
        if (expected.isInteger()) {
            Assert.assertNull(actual.getList());
            Assert.assertEquals(expected.getInteger(), actual.getInteger());
        } else {
            Assert.assertNull(actual.getInteger());
            Assert.assertEquals(expected.getList().size(), actual.getList().size());
            for (int i = 0; i < expected.getList().size(); i++) {
                assertMethod(expected.getList().get(i), actual.getList().get(i));
            }
        }
    }

    @TestData
    public DataExpectation example2() {
        NestedInteger expected = new NestedInteger();
        expected.add(new NestedInteger(123));
        NestedInteger nest1 = new NestedInteger();
        nest1.add(new NestedInteger(456));
        NestedInteger nest2 = new NestedInteger();
        nest2.add(new NestedInteger(789));
        nest1.add(nest2);
        expected.add(nest1);
        DataExpectationBuilder builder = new DataExpectationBuilder();
        builder.addArgument("[123,[456,[789]]]");
        builder.expect(expected);
        builder.assertMethod(this::assertMethod);
        return builder.build();
    }

    @TestData
    public DataExpectation border1() {
        NestedInteger expected = new NestedInteger();
        DataExpectationBuilder builder = new DataExpectationBuilder();
        builder.addArgument("[]");
        builder.expect(expected);
        builder.assertMethod(this::assertMethod);
        return builder.build();
    }

    @TestData
    public DataExpectation negative() {
        NestedInteger expected = new NestedInteger(-3);
        DataExpectationBuilder builder = new DataExpectationBuilder();
        builder.addArgument("-3");
        builder.expect(expected);
        builder.assertMethod(this::assertMethod);
        return builder.build();
    }

}
