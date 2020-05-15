package q700;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/employee-importance/
 *
 * You are given a data structure of employee information, which includes the employee's unique id, his importance
 * value and his direct subordinates' id.
 *
 * For example, employee 1 is the leader of employee 2, and employee 2 is the leader of employee 3. They have
 * importance value 15, 10 and 5, respectively. Then employee 1 has a data structure like [1, 15, [2]], and employee
 * 2 has [2, 10, [3]], and employee 3 has [3, 5, []]. Note that although employee 3 is also a subordinate of employee
 * 1, the relationship is not direct.
 *
 * Now given the employee information of a company, and an employee id, you need to return the total importance value
 * of this employee and all his subordinates.
 *
 * Example 1:
 *
 * Input: [[1, 5, [2, 3]], [2, 3, []], [3, 3, []]], 1
 * Output: 11
 * Explanation:
 * Employee 1 has importance value 5, and he has two direct subordinates: employee 2 and employee 3. They both have
 * importance value 3. So the total importance value of employee 1 is 5 + 3 + 3 = 11.
 *
 *
 *
 * Note:
 *
 * 1. One employee has at most one direct leader and may have several subordinates.
 * 2. The maximum number of employees won't exceed 2000.
 */
@RunWith(LeetCodeRunner.class)
public class Q690_EmployeeImportance {

    // Definition for Employee.
    private static class Employee {

        public int id;
        public int importance;
        public List<Integer> subordinates;
    }

    @Answer
    public int getImportance(List<Employee> employees, int id) {
        Map<Integer, Employee> map = new HashMap<>(employees.size());
        for (Employee employee : employees) {
            map.put(employee.id, employee);
        }

        int res = 0;
        Queue<Employee> queue = new ArrayDeque<>();
        queue.offer(map.get(id));
        while (!queue.isEmpty()) {
            Employee employee = queue.poll();
            res += employee.importance;
            for (Integer subordinate : employee.subordinates) {
                queue.offer(map.get(subordinate));
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example() {
        Employee e1 = createEmployee(1, 5, 2, 3);
        Employee e2 = createEmployee(2, 3);
        Employee e3 = createEmployee(3, 3);
        return DataExpectation.createWith(Arrays.asList(e1, e2, e3), 1).expect(11);
    }

    private Employee createEmployee(int id, int importance, int... subordinate) {
        Employee employee = new Employee();
        employee.id = id;
        employee.importance = importance;
        employee.subordinates = new ArrayList<>();
        for (int i : subordinate) {
            employee.subordinates.add(i);
        }
        return employee;
    }

}
