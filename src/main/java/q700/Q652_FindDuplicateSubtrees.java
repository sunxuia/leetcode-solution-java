package q700;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-duplicate-subtrees/
 *
 * Given a binary tree, return all duplicate subtrees. For each kind of duplicate subtrees, you only need to return
 * the root node of any one of them.
 *
 * Two trees are duplicate if they have the same structure with same node values.
 *
 * Example 1:
 *
 * >         1
 * >        / \
 * >       2   3
 * >      /   / \
 * >     4   2   4
 * >        /
 * >       4
 *
 * The following are two duplicate subtrees:
 *
 * >       2
 * >      /
 * >     4
 *
 * and
 *
 * >     4
 *
 * Therefore, you need to return above trees' root in the form of a list.
 */
@RunWith(LeetCodeRunner.class)
public class Q652_FindDuplicateSubtrees {

    // 通过将遍历结果转成 String 类型来判断重复
    @Answer
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        Map<String, TreeNode> duplicates = new HashMap<>();
        preOrder(root, new HashMap<>(), duplicates);
        return new ArrayList<>(duplicates.values());
    }

    private String preOrder(TreeNode node, Map<String, TreeNode> map, Map<String, TreeNode> duplicates) {
        if (node == null) {
            return "null";
        }
        String left = preOrder(node.left, map, duplicates);
        String right = preOrder(node.right, map, duplicates);
        String key = node.val + "-" + left + "-" + right;
        if (map.containsKey(key)) {
            if (!duplicates.containsKey(key)) {
                duplicates.put(key, node);
            }
        } else {
            map.put(key, node);
        }
        return key;
    }

    // 参考 LeetCode 中的其他答案, 针对上面做法的一点优化, 通过将遍历结果进行编号的方式可以提升速度.
    @Answer
    public List<TreeNode> findDuplicateSubtrees2(TreeNode root) {
        nextNo = 1;
        nos = new HashMap<>();
        duplicates = new HashMap<>();

        preOrder(root);
        return new ArrayList<>(duplicates.values());
    }

    // 遍历字符串的编号
    private Map<String, Integer> nos;

    // 用于分配的编号
    private int nextNo;

    // 保存重复对象的 map
    private Map<Integer, TreeNode> duplicates;

    private int preOrder(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = preOrder(node.left);
        int right = preOrder(node.right);
        // 通过使用int 作为键的方式, 避免了这里的key 过大导致的时延
        String key = node.val + "-" + left + "-" + right;
        Integer keyNo = nos.get(key);
        if (keyNo == null) {
            keyNo = nextNo++;
            nos.put(key, keyNo);
        } else {
            if (!duplicates.containsKey(keyNo)) {
                duplicates.put(keyNo, node);
            }
        }
        return keyNo;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(TreeNode.createByLevel(1, 2, 3, 4, null, 2, 4, null, null, 4))
            .expect(Arrays.asList(TreeNode.createByLevel(2, 4), TreeNode.createByLevel(4)))
            .unorderResult("")
            .build();

}
