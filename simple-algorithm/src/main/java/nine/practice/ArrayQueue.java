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
     * 入队操作 从tail的后一个位置进行入队,tail往后移动一位
     *
     * @param item
     * @return
     */
    public boolean enqueue(String value) {
        if (tail != length) {
            if (tail == 0) {
                //空队列插入第一个数据
                this.item[tail] = value;
            } else {
                this.item[tail + 1] = value;
                tail++;
            }
            return true;
        } else {
            if (head == 0) {
                System.out.println("队列已满");
                return false;
            }
            for (int i = head; i < tail; i++) {
                item[i - head] = item[i];
            }
            tail -= head;
            head = 0;
        }

        return false;
    }

    /**
     * 出队操作 从head处进行出队,head向后移动一位
     * 当head>=tail时,
     *
     * @return
     */
    public String dequeue() {
        if (head > tail) {
            System.out.println("队列已空");
            return null;
        }
        String reslut = item[head];
        item[head] = null;
        return reslut;
    }


}
