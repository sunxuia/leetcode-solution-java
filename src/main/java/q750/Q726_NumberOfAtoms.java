package q750;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/number-of-atoms/
 *
 * Given a chemical formula (given as a string), return the count of each atom.
 *
 * An atomic element always starts with an uppercase character, then zero or more lowercase letters, representing the
 * name.
 *
 * 1 or more digits representing the count of that element may follow if the count is greater than 1. If the count is
 * 1, no digits will follow. For example, H2O and H2O2 are possible, but H1O2 is impossible.
 *
 * Two formulas concatenated together produce another formula. For example, H2O2He3Mg4 is also a formula.
 *
 * A formula placed in parentheses, and a count (optionally added) is also a formula. For example, (H2O2) and (H2O2)3
 * are formulas.
 *
 * Given a formula, output the count of all elements as a string in the following form: the first name (in sorted
 * order), followed by its count (if that count is more than 1), followed by the second name (in sorted order),
 * followed by its count (if that count is more than 1), and so on.
 *
 * Example 1:
 *
 * Input:
 * formula = "H2O"
 * Output: "H2O"
 * Explanation:
 * The count of elements are {'H': 2, 'O': 1}.
 *
 * Example 2:
 *
 * Input:
 * formula = "Mg(OH)2"
 * Output: "H2MgO2"
 * Explanation:
 * The count of elements are {'H': 2, 'Mg': 1, 'O': 2}.
 *
 * Example 3:
 *
 * Input:
 * formula = "K4(ON(SO3)2)2"
 * Output: "K4N2O14S4"
 * Explanation:
 * The count of elements are {'K': 4, 'N': 2, 'O': 14, 'S': 4}.
 *
 * Note:
 * All atom names consist of lowercase letters, except for the first character which is uppercase.
 * The length of formula will be in the range [1, 1000].
 * formula will only consist of letters, digits, and round parentheses, and is a valid formula as defined in the
 * problem.
 */
@RunWith(LeetCodeRunner.class)
public class Q726_NumberOfAtoms {

    @Answer
    public String countOfAtoms(String formula) {
        Map<String, Integer> counts = new HashMap<>();
        dfs(formula.toCharArray(), 0, formula.length() - 1, counts, 1);

        // 组装结果
        counts.remove("");
        List<String> elements = new ArrayList<>(counts.keySet());
        elements.sort(String::compareTo);
        StringBuilder sb = new StringBuilder();
        for (String element : elements) {
            sb.append(element);
            int count = counts.get(element);
            if (count > 1) {
                sb.append(count);
            }
        }
        return sb.toString();
    }

    // 计算一个括号内的元素数量
    // times 表示这个元素表示有多少个(括号外的数字, 如果有多层则是多层数字的积)
    private void dfs(char[] formular, int start, int end, Map<String, Integer> counts, int times) {
        while (start <= end) {
            int i = start;
            while (i <= end && formular[i] != '(') {
                i++;
            }
            doCount(formular, start, i - 1, counts, times);
            if (i == end + 1) {
                return;
            }

            int parStart = ++i;
            for (int count = 1; count > 0; i++) {
                if (formular[i] == '(') {
                    count++;
                } else if (formular[i] == ')') {
                    count--;
                }
            }
            int parEnd = i - 2;
            int number = 0;
            while (i <= end && '0' <= formular[i] && formular[i] <= '9') {
                number = number * 10 + formular[i] - '0';
                i++;
            }
            dfs(formular, parStart, parEnd, counts, times * number);
            start = i;
        }
    }

    // 统计元素的数量. [start, end] 范围内没有左右括号
    private void doCount(char[] formular, int start, int end, Map<String, Integer> counts, int times) {
        String element = "";
        int number = 0;
        for (int i = start; i <= end; i++) {
            char c = formular[i];
            if ('a' <= c && c <= 'z') {
                element += c;
            } else if ('0' <= c && c <= '9') {
                number = number * 10 + c - '0';
            } else {
                number = number > 0 ? number : 1;
                counts.put(element, counts.getOrDefault(element, 0) + number * times);
                element = String.valueOf(c);
                number = 0;
            }
        }
        number = number > 0 ? number : 1;
        counts.put(element, counts.getOrDefault(element, 0) + number * times);
    }

    // LeetCode 上比较快的方法, 和上面基本类似.
    @Answer
    public String countOfAtoms2(String formula) {
        int n = formula.length();
        char[] sc = formula.toCharArray();

        Stack<Map<String, Integer>> stack = new Stack<>();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < n; ) {
            if (sc[i] == '(') {
                // 左括号入栈
                stack.push(map);
                map = new HashMap<>();
                i++;
            } else if (sc[i] == ')') {
                // 右括号出栈, 将括号内的结果乘以括号后的数字
                int number = 0;
                i++;
                while (i < n && Character.isDigit(sc[i])) {
                    number = number * 10 + sc[i++] - '0';
                }
                number = number > 0 ? number : 1;

                Map<String, Integer> temp = map;
                map = stack.pop();
                for (String element : temp.keySet()) {
                    map.put(element, temp.get(element) * number
                            + map.getOrDefault(element, 0));
                }
            } else {
                // 非括号, 则计算元素和数量
                int start = i++;
                if (i < n && Character.isLowerCase(sc[i])) {
                    i++;
                }
                String element = new String(sc, start, i - start);
                int number = 0;
                while (i < n && Character.isDigit(sc[i])) {
                    number = number * 10 + sc[i++] - '0';
                }
                number = number > 0 ? number : 1;
                map.put(element, map.getOrDefault(element, 0) + number);
            }
        }

        // 组装结果
        List<String> list = new ArrayList<>(map.keySet());
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for (String key : list) {
            sb.append(key);
            int val = map.get(key);
            if (val > 1) {
                sb.append(map.get(key));
            }
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("H2O").expect("H2O");

    @TestData
    public DataExpectation example2 = DataExpectation.create("Mg(OH)2").expect("H2MgO2");

    @TestData
    public DataExpectation example3 = DataExpectation.create("K4(ON(SO3)2)2").expect("K4N2O14S4");

}
