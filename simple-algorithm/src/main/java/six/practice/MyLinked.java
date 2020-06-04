package six.practice;

/**
 * 单项链表类结构
 * 成员:头结点
 * 方法:遍历,添加节点,删除节点,修改节点,打印节点,节点长度
 */
public class MyLinked<T> {


    /**
     * 头结点
     */
    Node<T> head = null;

    Node<T> tail = null;

    /**
     * 节点数据,下一个节点指针
     *
     * @param <T>
     */
    class Node<T> {

        /**
         * 节点数据
         */
        T data;

        /**
         * 指向下一个节点
         */
        Node<T> next = null;

        public Node(T t) {
            this.data = t;
        }
    }

    /**
     * 添加节点
     *
     * @param t
     */
    public void add(T t) {
        Node<T> node = new Node<>(t);
        if (head == null) {
            head = node;
        } else {
            //遍历找到尾节点
            Node<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
           /* //规定出尾节点
            Node<T> temp = tail;
            temp.next = node;
            //规定新的尾节点
            tail = node;*/
        }
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
            Node<T> preNode = head;
            Node<T> currentNode = preNode.next;
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
     * 打印单向链表
     */
    public void printLinkedList() {
        Node<T> temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }

    /**
     * 查找数据 返回index
     *
     * @param data
     * @return
     */
    public int find(T data) {
        int notExistIndex = -1;
        if (head == null) {
            return notExistIndex;
        } else {
            Node<T> currentNode = head;
            int index = 0;
            boolean flag = false;
            while (currentNode != null) {
                if (currentNode.data.equals(data)) {
                    flag = true;
                    return index;
                }
                currentNode = currentNode.next;
                index++;
            }
            return flag == true ? index : notExistIndex;
        }
    }

    public void putHead(T t) {
        Node<T> node = new Node<>(t);
        node.next = head;
        head = node;
    }

    public Node<T> inverseLinkList(Node p, boolean deletHead) {
        Node next;
        Node pre = head;
        Node current = head.next;
        int i = 0;
        while (current != p) {
            if (i == 0) {
                pre.next = null;
            }
            next = current.next;
            head = current;
            head.next = pre;
            pre = current;
            current = next;
            i++;
        }
        return deletHead ? head.next : head;
    }

    public boolean compare(Node left, Node right) {
        Node l = left;
        Node r = right;

        boolean flag = true;
        System.out.println("left_:" + l.data);
        System.out.println("right_:" + r.data);
        while (l != null && r != null) {
            if (l.data.equals(r.data)) {
                l = l.next;
                r = r.next;
                continue;
            } else {
                flag = false;
                break;
            }

        }

        System.out.println("什么结果");
        return flag;
       /* if (l==null && r==null){
           System.out.println("什么结果");
           return true;
        }else{
           return false;
        }*/
    }


    /**
     * 获取节点长度
     *
     * @return
     */
    public int size() {
        Node<T> temp = head;
        int size = 0;
        while (temp != null) {
            size++;
            temp = temp.next;
        }
        return size;
    }


}
