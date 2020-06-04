package nine.practice;

/**
 * 基于链表实现队列
 * 链表实现栈只需要一个head指针,而实现队列需要head指针和tail指针
 * head指针指向链表头部,tail指针指向链表的尾部
 * head指针指向的node出列,tail指针的next node入列
 * (数组实现队列时,tail指向为空的序列,便于数组直接从tail处插入,更加方便判断队空和队满)
 *
 * @Author lizzy
 * @Date 2020/6/4 13:00
 * @Version 1.0
 */
public class LinkedQueue {

    Node head = null;
    Node tail = null;

    class Node {
        String data;
        Node next = null;

        public Node(String str) {
            this.data = str;
        }
    }

    /**
     * 入队操作
     *
     * @param val
     * @return
     */
    public boolean enqueue(String val) {
        //链尾为null,说明链为null
        Node newNode = new Node(val);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = tail.next;
        }
        System.out.println("入列值" + val);
        return true;
    }

    /**
     * 出队操作
     *
     * @return
     */
    public String dequeue() {
        if (head == null) {
            System.out.println("队列为空");
            return null;
        } else {
            String result = head.data;
            head = head.next;
            System.out.println("出列值:" + result);
            return result;
        }
    }

    /**
     * 打印队列
     */
    public void print() {
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }

    }


    public static void main(String[] args) {
        LinkedQueue linkedQueue = new LinkedQueue();
        linkedQueue.enqueue("a");
        linkedQueue.enqueue("b");
        linkedQueue.enqueue("c");
        linkedQueue.enqueue("d");
        linkedQueue.enqueue("e");
        linkedQueue.enqueue("f");
        linkedQueue.enqueue("g");
        linkedQueue.enqueue("h");
        linkedQueue.enqueue("i");
        linkedQueue.enqueue("j");

        linkedQueue.dequeue();
        linkedQueue.dequeue();
        linkedQueue.dequeue();
        linkedQueue.dequeue();

        linkedQueue.enqueue("1");
        linkedQueue.enqueue("2");
        linkedQueue.enqueue("3");

        linkedQueue.print();

        System.out.println("计算值:" + (7 + 1) % 11);
    }


}
