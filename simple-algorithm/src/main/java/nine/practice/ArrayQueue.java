package nine.practice;

/**
 * 基于数组实现队列
 * 1.队列的出队和入队分别为容器的两端,遵循FIFO原则
 * 2.在数组中用连个标记字段分别表示出队头和队尾
 *
 * @Author lizzy
 * @Date 2020/6/3 13:57
 * @Version 1.0
 */
public class ArrayQueue {
    private String[] item;
    private int length = 0;
    private int head = 0;
    private int tail = 0;

    public ArrayQueue(int capacity) {
        this.item = new String[capacity];
        length = capacity;
    }

    /**
     * 入队操作 从tail的位置进行入队,tail往后移动一位
     * head为出栈位置序列  tail为入栈位置序列
     *
     * @param item
     * @return
     */
    public boolean enqueue(String value) {

        if (tail == length) {
            //队列已满
            if (head == 0) {
                System.out.println("队列完全满了,无法搬迁");
                return false;
            }
            //数据搬迁
            for (int i = 0; i < tail - head; i++) {
                item[i] = item[head + i];
                //搬移完成后,数组中被移动的数据要清除
                item[head + i] = null;
            }
            System.out.println("队列完成搬迁");
            //head,tail 初始化
            tail -= head;
            head = 0;
        }
        item[tail] = value;
        System.out.println("入队序列:" + tail + "，值:" + value);
        ++tail;
        return true;
    }

    /**
     * 出队操作 从head处进行出队,head向后移动一位
     * 当head>=tail时,
     *
     * @return
     */
    public String dequeue() {
        if (head == tail) {
            System.out.println("队列已空");
            return null;
        }
        String reslut = item[head];
        item[head] = null;
        ++head;
        System.out.println("出队序列:" + head + "，值:" + reslut);
        return reslut;
    }

    /**
     * 打印队列成员
     */
    public void print() {
        for (int i = 0; i < length; i++) {
            if (item[i] != null) {
                System.out.println(item[i]);
            }
        }
    }

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(10);
        arrayQueue.enqueue("a");
        arrayQueue.enqueue("b");
        arrayQueue.enqueue("c");
        arrayQueue.enqueue("d");
        arrayQueue.enqueue("e");
        arrayQueue.enqueue("f");
        arrayQueue.enqueue("g");
        arrayQueue.enqueue("h");
        arrayQueue.enqueue("i");
        arrayQueue.enqueue("j");
        arrayQueue.enqueue("k");
        arrayQueue.enqueue("l");
        arrayQueue.enqueue("m");

        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.dequeue();

        arrayQueue.enqueue("111");

        arrayQueue.print();


        //数组按照序列删除元素
        String[] aaa = new String[5];
        aaa[0] = "123";
    }


}
