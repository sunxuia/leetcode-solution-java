package util.provided;

import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;

public class ListNode {

    public int val;

    public ListNode next;

    public ListNode(int x) {
        this.val = x;
    }

    @Override
    public String toString() {
        if (next == null) {
            return String.valueOf(val);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(val);
        Set<ListNode> nodes = new HashSet<>();
        nodes.add(this);
        ListNode node = next;
        while (node != null) {
            if (!nodes.add(node)) {
                sb.append("-><<" + node.val + "(循环)");
                break;
            }
            sb.append("->").append(node.val);
            node = node.next;
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ListNode)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ListNode node = (ListNode) obj;
        ListNode thisNode = this;
        while (node != null && thisNode != null && node.val == thisNode.val) {
            node = node.next;
            thisNode = thisNode.next;
        }
        return node == null && thisNode == null;
    }

    public static ListNode createListNode(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode node = dummy;
        for (int val : vals) {
            node.next = new ListNode(val);
            node = node.next;
        }
        return dummy.next;
    }

    public static void assertEquals(ListNode expected, ListNode actual) {
        while (expected != null) {
            Assert.assertNotNull(actual);
            Assert.assertEquals(expected.val, actual.val);
            expected = expected.next;
            actual = actual.next;
        }
        Assert.assertNull(actual);
    }
}
