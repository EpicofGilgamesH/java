package eight.practice;

/**
 * 基于链表实现栈 无界栈
 *
 * @Author lizzy
 * @Date 2020/6/3 13:00
 * @Version 1.0
 */
public class LinkedStack {

    private Node head = null;

    static class Node {
        Node next = null;
        String data;

        public Node(String data) {
            this.data = data;
        }
    }

    /**
     * 入栈操作 每次入栈的元素即成为链表的head
     *
     * @return
     */
    public boolean push(String data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node next = head;
            head = newNode;
            head.next = next;
        }
        return true;
    }

    /**
     * 出栈操作 将head弹出
     */
    public String pop() {
        String reslut = head.data;
        head = head.next;
        return reslut;
    }

    /**
     * 查看栈顶的数据
     *
     * @return
     */
    public String get() {
        String reslut = head.data;
        return reslut;
    }

    /**
     * 打印链表
     */
    public void print() {
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    /**
     * 获取节点长度
     *
     * @return
     */
    public int size() {
        Node temp = head;
        int size = 0;
        while (temp != null) {
            size++;
            temp = temp.next;
        }
        return size;
    }

    /**
     * 删除指定节点
     *
     * @param t
     */
    public void delete(int index) {
        if (head == null) {
            return;
        }
        if (index > 0 && index < size()) {
            if (index == 1) {
                head = head.next;
                return;
            }
            Node preNode = head;
            Node currentNode = preNode.next;
            int currentIndex = 1;
            while (currentNode != null) {
                if (currentIndex == index) {
                    preNode.next = currentNode.next;
                }
                preNode = currentNode;
                currentNode = currentNode.next;
                currentIndex++;
            }
        }
    }

    /**
     * 清空单向链表  清空链表的更好思路??
     */
    public void clear() {
        int length = size();
        if (length > 0) {
            while (size() > 0) {
                delete(0);
            }
        }
    }

    public static void main(String[] args) {
        LinkedStack linkedStack = new LinkedStack();
        linkedStack.push("a");
        linkedStack.push("b");
        linkedStack.push("c");
        linkedStack.push("d");
        linkedStack.push("e");
        linkedStack.push("f");
        linkedStack.push("g");
        linkedStack.push("h");

        while (linkedStack.head != null) {
            System.out.println(linkedStack.pop());
        }
        System.out.println();
        linkedStack.print();
    }

}
