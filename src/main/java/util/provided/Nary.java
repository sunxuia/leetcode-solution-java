package util.provided;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * N-ary node
 * 表示 N元子节点的树
 */
public class Nary {

    // Definition for a Node.
    public static class Node {

        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * 构造测试 Node 数据.
     * 每层每层地写, 每个父节点的子节点都以一组数字和末尾的null 结束, 如果没有子节点, 那么就留一个null.
     */
    public static Node createNodeByLevel(Integer... vals) {
        Queue<Node> queue = new ArrayDeque<>();
        Node dummy = new Node(0, new ArrayList<>());
        queue.add(dummy);
        int idx = 0;
        while (!queue.isEmpty() && idx < vals.length) {
            Node node = queue.remove();
            while (idx < vals.length && vals[idx] != null) {
                Node child = new Node(vals[idx], new ArrayList<>());
                node.children.add(child);
                queue.add(child);
                idx++;
            }
            idx++;
        }
        return dummy.children.isEmpty() ? null : dummy.children.get(0);
    }

}
