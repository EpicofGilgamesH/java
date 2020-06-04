package nine.practice;

/**
 * 基于环形数组实现队列
 *
 * @Author lizzy
 * @Date 2020/6/4 13:58
 * @Version 1.0
 */
public class CircleArrayQueue {

    private String[] items;
    private int count;
    private int head = 0;
    private int tail = 0;

    public CircleArrayQueue(int capacity) {
        this.items = new String[capacity];
        this.count = capacity;
    }

    /**
     * 入队操作,注意队满
     */
    public boolean enqueue(String val) {
        if ((tail + 1) % count == head) {
            System.out.println("队列已满");
            return false;
        }
        items[tail] = val;
        tail = (tail + 1) % count;
        System.out.println("入队成功,序列为" + tail + "，值为:" + val);
        return true;
    }

    /**
     * 出队操作,注意队空
     *
     * @return
     */
    public String dequeue() {
        if (tail == head) {
            System.out.println("队列已空");
            return null;
        }
        String result = items[head];
        items[head] = null;
        head = (head + 1) % count;
        System.out.println("出队成功,序列为" + head + ",值为:" + result);
        return result;
    }

    public static void main(String[] args) {
        CircleArrayQueue circleArrayQueue = new CircleArrayQueue(5);
        circleArrayQueue.enqueue("a");
        circleArrayQueue.enqueue("b");
        circleArrayQueue.enqueue("c");
        circleArrayQueue.enqueue("d");
        circleArrayQueue.enqueue("e");
        circleArrayQueue.enqueue("f");
        circleArrayQueue.enqueue("g");

        circleArrayQueue.dequeue();
        circleArrayQueue.dequeue();
        circleArrayQueue.dequeue();
        circleArrayQueue.dequeue();
        circleArrayQueue.dequeue();
        circleArrayQueue.dequeue();
        circleArrayQueue.dequeue();

    }

}
