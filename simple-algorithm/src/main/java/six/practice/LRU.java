package six.practice;

/**
 * 习题-实现LRU缓存淘汰算法 Least Recently Used(最近最少使用策略)
 *
 * @Author lizzy
 * @Date 2020/6/2 9:09
 * @Version 1.0
 */
public class LRU {

    //思路
    //1.使用链表结构,当有一个新的数据被访问时,我们遍历链表
    //2.如果此数据已经存在于链表了,则将其从原来的位置删除,然后将它存放到链表的头部
    //3.如果此数据不存在于链表,1) 链表未满,将数据存放在链表的头部; 2)链表已满,将尾部数据删除,将此数据存放在链表的头部;

    int length = 10;
    MyLinked<Integer> myLinked = new MyLinked<>();

    public void access(Integer integer) {

        int index = myLinked.find(integer);
        if (index >= 0) {
            myLinked.delete(index);
            myLinked.putHead(integer);
        } else {
            if (myLinked.size() <= length) {
                myLinked.putHead(integer);
            } else {
                myLinked.delete(myLinked.size() - 1);
                myLinked.putHead(integer);
            }
        }
    }

    public static void main(String[] args) {
        LRU lru = new LRU();
        lru.access(1);
        lru.access(2);
        lru.access(3);
        lru.access(4);
        lru.access(5);
        lru.access(6);
        lru.access(7);
        lru.access(8);
        lru.access(9);
        lru.access(10);
        lru.access(4);
        lru.access(5);
        lru.access(6);
        lru.access(7);

        lru.myLinked.printLinkedList();
    }

}

